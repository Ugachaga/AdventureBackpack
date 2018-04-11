package com.darkona.adventurebackpack.item;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.block.BlockBackpack;
import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.common.BackpackAbilities;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.events.WearableEvent;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.network.GuiPacket;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.proxy.ClientProxy;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.CoordsUtils;
import com.darkona.adventurebackpack.util.EnchUtils;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.TipUtils;
import com.darkona.adventurebackpack.util.Utils;

import static com.darkona.adventurebackpack.common.Constants.BASIC_TANK_CAPACITY;
import static com.darkona.adventurebackpack.common.Constants.TAG_DISABLE_CYCLING;
import static com.darkona.adventurebackpack.common.Constants.TAG_DISABLE_NVISION;
import static com.darkona.adventurebackpack.common.Constants.TAG_INVENTORY;
import static com.darkona.adventurebackpack.common.Constants.TAG_LEFT_TANK;
import static com.darkona.adventurebackpack.common.Constants.TAG_RIGHT_TANK;
import static com.darkona.adventurebackpack.common.Constants.TAG_TYPE;
import static com.darkona.adventurebackpack.util.TipUtils.l10n;

public class ItemBackpack extends ItemWearable
{
    public ItemBackpack(String name)
    {
        super(name);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (isInCreativeTab(tab))
        {
            List<ItemStack> backpacks = Stream.of(BackpackTypes.values())
                    .filter(type -> type != BackpackTypes.UNKNOWN)
                    .map(BackpackUtils::createBackpackStack)
                    .collect(Collectors.toList());

            items.addAll(backpacks);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (world == null) return;

        NBTTagCompound backpackTag = BackpackUtils.getWearableCompound(stack);

        BackpackTypes type = BackpackTypes.getType(backpackTag.getInteger(TAG_TYPE));
        tooltip.add(Utils.getColoredSkinName(type));

        FluidTank tank = new FluidTank(BASIC_TANK_CAPACITY);

        if (GuiScreen.isShiftKeyDown())
        {
            NBTTagList itemList = backpackTag.getTagList(TAG_INVENTORY, NBT.TAG_COMPOUND);
            tooltip.add(l10n("backpack.slots.used") + ": " + TipUtils.inventoryTooltip(itemList));

            tank.readFromNBT(backpackTag.getCompoundTag(TAG_LEFT_TANK));
            tooltip.add(l10n("backpack.tank.left") + ": " + TipUtils.tankTooltip(tank));

            tank.readFromNBT(backpackTag.getCompoundTag(TAG_RIGHT_TANK));
            tooltip.add(l10n("backpack.tank.right") + ": " + TipUtils.tankTooltip(tank));

            TipUtils.shiftFooter(tooltip);
        }
        else if (!GuiScreen.isCtrlKeyDown())
        {
            tooltip.add(TipUtils.holdShift());
        }

        if (GuiScreen.isCtrlKeyDown())
        {
            {
                boolean cycling = !backpackTag.getBoolean(TAG_DISABLE_CYCLING);
                tooltip.add(l10n("backpack.cycling") + ": " + TipUtils.switchTooltip(cycling, true));
                tooltip.add(TipUtils.pressKeyFormat(TipUtils.actionKeyFormat()) + l10n("backpack.cycling.key1"));
                tooltip.add(l10n("backpack.cycling.key2") + " " + TipUtils.switchTooltip(!cycling, false));
            }

            if (type.isNightVision())
            {
                boolean vision = !backpackTag.getBoolean(TAG_DISABLE_NVISION);
                tooltip.add(l10n("backpack.vision") + ": " + TipUtils.switchTooltip(vision, true));
                tooltip.add(TipUtils.pressShiftKeyFormat(TipUtils.actionKeyFormat()) + l10n("backpack.vision.key1"));
                tooltip.add(l10n("backpack.vision.key2") + " " + TipUtils.switchTooltip(!vision, false));
            }
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        super.onCreated(stack, world, player);
        BackpackUtils.setBackpackType(stack, BackpackTypes.getType(stack.getItemDamage()));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack held = player.getHeldItem(hand);
        return player.canPlayerEdit(pos, side, held) && placeBackpack(held, player, world, pos, side, true)
               ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        RayTraceResult trace = rayTrace(world, player, true);
        if (trace == null || trace.typeOfHit == RayTraceResult.Type.ENTITY)
        {
            if (world.isRemote)
            {
                ModNetwork.INSTANCE.sendToServer(new GuiPacket.Message(GuiPacket.GUI_BACKPACK, GuiPacket.FROM_HOLDING));
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public void onPlayerDeath(World world, EntityPlayer player, ItemStack stack)
    {
        if (world.isRemote || !ConfigHandler.backpackDeathPlace || EnchUtils.isSoulBounded(stack)
                || player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
        {
            return;
        }

        if (!tryPlace(world, player, stack))
        {
            player.dropItem(stack, false);
        }

        BackpackProperty.get(player).setWearable(null);
    }

    private boolean tryPlace(World world, EntityPlayer player, ItemStack backpack) //TODO extract behavior to CoordsUtils
    {
        int x = MathHelper.floor(player.posX);
        int z = MathHelper.floor(player.posZ);
        int Y = MathHelper.floor(player.posY);
        if (Y < 1) Y = 1;

        int positions[] = {0, -1, 1, -2, 2, -3, 3, -4, 4, -5, 5, -6, 6};

        for (int shiftY : positions)
        {
            if (Y + shiftY >= 1)
            {
                BlockPos spawn = CoordsUtils.getNearestEmptyChunkCoordinatesSpiral(world, x, z, x, Y + shiftY, z, 6, true, 1, (byte) 0, false);
                if (spawn != null)
                {
                    return placeBackpack(backpack, player, world, spawn, EnumFacing.UP, false);
                }
            }
        }
        return false;
    }

    private boolean placeBackpack(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, boolean from)
    {
        if (stack.isEmpty() || !CoordsUtils.isValidHeight(world, pos)
                || !player.canPlayerEdit(pos, side, stack))
            return false;

        BlockBackpack backpack = ModBlocks.BACKPACK_BLOCK;

        if (backpack.canPlaceBlockOnSide(world, pos, side) && world.getBlockState(pos).getMaterial().isSolid())
        {
            pos = pos.offset(side);

            if (!CoordsUtils.isValidHeight(world, pos))
                return false;

            if (backpack.canPlaceBlockAt(world, pos))
            {
                if (world.setBlockState(pos, backpack.getDefaultState()))
                {
                    IBlockState state = backpack.getDefaultState().withProperty(BlockBackpack.FACING, player.getHorizontalFacing().getOpposite());
                    backpack.onBlockPlacedBy(world, pos, state, player, stack);
                    player.playSound(SoundEvents.BLOCK_CLOTH_PLACE, 0.5f, 1.0f);
                    ((TileBackpack) world.getTileEntity(pos)).loadFromNBT(stack.getTagCompound());
                    if (from)
                    {
                        stack.setCount(0);
                    }
                    else
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
    public void onEquipped(World world, EntityPlayer player, ItemStack stack)
    {

    }

    @Override
    public void onEquippedUpdate(World world, EntityPlayer player, ItemStack stack)
    {
        if (!ConfigHandler.backpackAbilities || world == null || player == null || stack == null)
            return;

        if (BackpackTypes.getType(stack).isSpecial())
        {
            BackpackAbilities.backpackAbilities.executeAbility(player, world, stack);
        }
    }

    @Override
    public void onUnequipped(World world, EntityPlayer player, ItemStack stack)
    {
        if (BackpackTypes.getType(stack).hasProperty(BackpackTypes.Props.REMOVAL))
        {
            BackpackAbilities.backpackAbilities.executeRemoval(player, world, stack);
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double) getItemCount(stack) / Constants.INVENTORY_MAIN_SIZE;
    }

    private int getItemCount(ItemStack backpack)
    {
        NBTTagList itemList = BackpackUtils.getWearableInventory(backpack);
        int itemCount = itemList.tagCount();
        for (int i = itemCount - 1; i >= 0; i--)
        {
            int slotAtI = itemList.getCompoundTagAt(i).getInteger(Constants.TAG_SLOT);
            if (slotAtI < Constants.INVENTORY_MAIN_SIZE)
                break;
            itemCount--;
        }
        return itemCount;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return ConfigHandler.enableFullnessBar && getItemCount(stack) > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getWearableModel(ItemStack wearable)
    {
        return ClientProxy.modelBackpack.setWearable(wearable);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getWearableTexture(ItemStack wearable)
    {
        return Resources.getBackpackTexture(BackpackTypes.getType(wearable));
    }
}
