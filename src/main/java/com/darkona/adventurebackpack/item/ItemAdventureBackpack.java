package com.darkona.adventurebackpack.item;

import java.util.List;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.block.BlockAdventureBackpack;
import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpackArmor;
import com.darkona.adventurebackpack.common.BackpackAbilities;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.events.WearableEvent;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.network.GUIPacket;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.proxy.ClientProxy;
import com.darkona.adventurebackpack.reference.BackpackNames;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumActionResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.EnumFacing;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */
public class ItemAdventureBackpack extends ItemAB implements IBackWearableItem
{

    public ItemAdventureBackpack()
    {
        super();
        setUnlocalizedName("adventureBackpack");
        setFull3D();
        setMaxStackSize(1);
    }

    public static Item getItemFromBlock(Block block)
    {
        return block instanceof BlockAdventureBackpack ? ModItems.adventureBackpack : null;
    }

    /**
     * Return the enchantability factor of the item, most of the timeInSeconds is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return (Utils.isSoulBook(book));
    }

    /**
     * Return whether this item is repairable in an anvil.
     *
     * @param p_82789_1_
     * @param p_82789_2_
     */
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
        return false;
    }

    /**
     * Determines if the durability bar should be rendered for this item.
     * Defaults to vanilla stack.isDamaged behavior.
     * But modders can use this for any data they wish.
     *
     * @param stack The current Item Stack
     * @return True if it should render the 'durability' bar.
     */
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return false;
    }

    /**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     *
     * @param stack The current ItemStack
     * @return 1.0 for 100% 0 for 0%
     */
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return "Adventure Backpack";// + stack.getTagCompound().getString("colorName");
    }

    @Override
    public void onCreated(ItemStack stack, World par2World, EntityPlayer par3EntityPlayer)
    {

        super.onCreated(stack, par2World, par3EntityPlayer);
        BackpackNames.setBackpackColorNameFromDamage(stack, stack.getItemDamage());
    }

    public boolean placeBackpack(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, boolean from)
    {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        if (!player.canPlayerEdit(pos, side, stack)) return false;
        if (!stack.getTagCompound().hasKey("colorName") || stack.getTagCompound().getString("colorName").isEmpty())
        {
            stack.getTagCompound().setString("colorName", "Standard");
        }

        // world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
        BlockAdventureBackpack backpack = ModBlocks.blockBackpack;

        if (pos.getY() <= 0 || pos.getY() >= world.getHeight())
        {
            return false;
        }
        if (backpack.canPlaceBlockOnSide(world, pos, side))
        {
            if (world.getBlockState(pos).getMaterial().isSolid())
            {
                switch (side)
                {
                    case DOWN:
                        pos.down();
                        break;
                    case UP:
                        pos.up();
                        break;
                    case SOUTH:
                        pos.south();
                        break;
                    case EAST:
                        pos.east();
                        break;
                    case WEST:
                        pos.west();
                        break;
                    case NORTH:
                        pos.north();
                        break;
                }
            }
            if (pos.getY() <= 0 || pos.getY() >= world.getHeight())
            {
                return false;
            }
            if (backpack.canPlaceBlockAt(world, pos))
            {
                if (world.setBlockState(pos, ModBlocks.blockBackpack.getDefaultState()))
                {
                    backpack.onBlockPlacedBy(world, pos, ModBlocks.blockBackpack.getDefaultState(), player, stack);
                    player.playSound(SoundEvents.BLOCK_CLOTH_PLACE, 0.5f, 1.0f);
                    ((TileAdventureBackpack) world.getTileEntity(pos)).loadFromNBT(stack.getTagCompound());
                    if (from)
                    {
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    } else
                    {
                        BackpackProperty.get(player).setWearable(null);
                    }
                    WearableEvent event = new WearableEvent(player, stack);
                    MinecraftForge.EVENT_BUS.post(event);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        return (player.canPlayerEdit(pos, side, stack) && placeBackpack(stack, player, world, pos, side, true)) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer player, EnumHand hand)
    {
        RayTraceResult mop = rayTrace(world, player, true);
        if (mop == null || mop.typeOfHit == RayTraceResult.Type.ENTITY)
        {
            if (world.isRemote)
            {
                ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.BACKPACK_GUI, GUIPacket.FROM_HOLDING));
            }
        }
       return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
        return new ModelBackpackArmor();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
            return ModInfo.MOD_ID + ":"  +"textures/models/armor/adventureHat.png";

    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        NBTTagCompound backpackData = BackpackUtils.getBackpackData(stack);
        if (backpackData != null)
        {
            if (backpackData.hasKey("colorName"))
            {
                list.add(backpackData.getString("colorName"));
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List subItems)
    {
        for (int i = 0; i < BackpackNames.backpackNames.length; i++)
        {
            ItemStack bp = new ItemStack(this, 1, 0);
            bp.setItemDamage(i);
            NBTTagCompound c = new NBTTagCompound();
            c.setString("colorName", BackpackNames.backpackNames[i]);
            BackpackUtils.setBackpackData(bp, c);
            subItems.add(bp);
        }
    }

    @Override
    public void onEquippedUpdate(World world, EntityPlayer player, ItemStack stack)
    {

        if (!ConfigHandler.backpackAbilities) return;
        if (world == null || player == null || stack == null) return;

        if (BackpackAbilities.hasAbility(BackpackNames.getBackpackColorName(stack)))
        {
            BackpackAbilities.backpackAbilities.executeAbility(player, world, stack);
        }
    }

    @Override
    public void onPlayerDeath(World world, EntityPlayer player, ItemStack stack)
    {
        //onUnequipped(world, player, stack);

        if (world.isRemote) return;

        if (Wearing.isWearingTheRightBackpack(player, "Creeper"))
        {
            player.world.createExplosion(player, player.posX, player.posY, player.posZ, 4.0F, false);
        }

        if (Utils.isSoulBounded(stack))
            return;

        if (ConfigHandler.backpackDeathPlace)
        {
            if (!tryPlace(world, player, stack))
            {
                player.dropItem(stack, false);
            }
        }

        BackpackProperty.get(player).setWearable(null);
    }

    private boolean tryPlace(World world, EntityPlayer player, ItemStack backpack)
    {
        int X = (int) player.posX;
        if (player.posX < 0) X--;
        int Z = (int) player.posZ;
        if (player.posZ < 0) Z--;
        int Y = (int) player.posY;
        if (Y < 1) Y = 1;

        int positions[] = { 0, -1, 1, -2, 2, -3, 3, -4, 4, -5, 5, -6, 6 };

        for (int shiftY : positions)
        {
            if (Y + shiftY >= 1)
            {
                ChunkPos spawn = Utils.getNearestEmptyChunkCoordinatesSpiral(world, X, Z, X, Y + shiftY, Z, 6, true, 1, (byte) 0, false);
                if (spawn != null)
                {
                    return placeBackpack(backpack, player, world, new BlockPos(spawn.getXCenter(), Y, spawn.getZCenter()), EnumFacing.UP, false);
                }
            }
        }
        return false;
    }

    @Override
    public void onEquipped(World world, EntityPlayer player, ItemStack stack)
    {

    }

    @Override
    public void onUnequipped(World world, EntityPlayer player, ItemStack stack)
    {
        if (BackpackAbilities.hasRemoval(BackpackNames.getBackpackColorName(stack)))
        {
            BackpackAbilities.backpackAbilities.executeRemoval(player, world, stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getWearableModel(ItemStack wearable)
    {
        return ClientProxy.modelAdventureBackpack.setWearable(wearable);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getWearableTexture(ItemStack wearable)
    {

        ResourceLocation modelTexture;

        if (BackpackNames.getBackpackColorName(wearable).equals("Standard"))
        {
            modelTexture = Resources.backpackTextureFromString(AdventureBackpack.instance.Holiday);
        } else
        {
            modelTexture = Resources.backpackTextureFromString(BackpackNames.getBackpackColorName(wearable));
        }
        return modelTexture;
    }



}
