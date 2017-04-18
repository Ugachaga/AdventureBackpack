package com.darkona.adventurebackpack.item;

import java.util.List;

import com.darkona.adventurebackpack.client.models.ModelCopterPack;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryCopterPack;
import com.darkona.adventurebackpack.network.GUIPacket;
import com.darkona.adventurebackpack.network.messages.EntityParticlePacket;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.proxy.ClientProxy;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.world.World;

/**
 * Created on 31/12/2014
 *
 * @author Darkona
 */
public class ItemCopterPack extends ItemAB implements IBackWearableItem
{

    public ItemCopterPack()
    {
        super();
        setUnlocalizedName("copterPack");
        setFull3D();
        setMaxStackSize(1);
    }

    public static byte OFF_MODE = 0;
    public static byte NORMAL_MODE = 1;
    public static byte HOVER_MODE = 2;

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

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer playerIn, EnumHand hand)
    {
        if (world.isRemote)
        {
            ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.COPTER_GUI, GUIPacket.FROM_HOLDING));
        }
        return new ActionResult(EnumActionResult.PASS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrent)
    {
    }

    @SuppressWarnings(value = "unchecked")
    public void pushEntities(World world, EntityPlayer player, float speed)
    {
        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        List<EntityItem> groundItems = world.getEntitiesWithinAABB(
                EntityItem.class, new AxisAlignedBB(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(4.0D, 4.0D, 4.0D));

        for (EntityItem groundItem : groundItems)
        {
            if (!groundItem.isInWater())
            {
                if (groundItem.posX > posX)
                {
                    groundItem.motionX = speed;
                }
                if (groundItem.posX < posX)
                {
                    groundItem.motionX = -speed;
                }

                if (groundItem.posZ > posZ)
                {
                    groundItem.motionZ = speed;
                }
                if (groundItem.posZ < posZ)
                {
                    groundItem.motionZ = -speed;
                }

                if (groundItem.posY < posY)
                {
                    groundItem.motionY -= speed;
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
        return ModelCopterPack.instance.setWearable(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        String modelTexture;
        modelTexture = Resources.modelTextures("copterPack").toString();

        return modelTexture;
    }

    public static void elevate(EntityPlayer player, ItemStack copter)
    {
        if (player.posY < 256) player.motionY = Math.max(player.motionY, 0.18);
        if (player.posY > 256) player.motionY = 0.18 - ((player.posY % 256) / 256);
    }

    @Override
    public void onEquippedUpdate(World world, EntityPlayer player, ItemStack stack)
    {
        InventoryCopterPack inv = new InventoryCopterPack(Wearing.getWearingCopter(player));
        inv.openInventory(player);
        boolean canElevate = true;
        int fuelConsumption = 0;
        if (inv.getStatus() != OFF_MODE)
        {
            if (player.isInWater())
            {
                inv.setStatus(OFF_MODE);
                inv.dirtyStatus();
                if (!world.isRemote)
                {
                    player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.copterpack.cantwater"));
                }
                return;
            }
            if (inv.fuelTank.getFluidAmount() == 0)
            {
                canElevate = false;
                if (player.onGround)
                {
                    inv.setStatus(OFF_MODE);
                    inv.dirtyStatus();
                    if (!world.isRemote)
                    {
                        player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.copterpack.off"));
                    }
                    return;
                    //TODO play "backpackOff" sound
                }
                if (inv.getStatus() == HOVER_MODE)
                {

                    inv.setStatus(NORMAL_MODE);
                    inv.dirtyStatus();
                    if (!world.isRemote)
                    {
                        player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.copterpack.outoffuel"));
                    }
                    return;
                    //TODO play "outofFuel" sound
                }
            }
        }

        if (inv.getStatus() != OFF_MODE)
        {
            fuelConsumption++;
            if (inv.getStatus() == NORMAL_MODE)
            {
                if (!player.onGround && !player.isSneaking() && player.motionY < 0.0D)
                {
                    player.motionY = player.motionY * 0.6;
                }
                if (player.isSneaking())
                {
                    player.motionY = -0.3;
                }
            }

            if (inv.getStatus() == HOVER_MODE)
            {
                if (player.isSneaking())
                {
                    player.motionY = -0.3;
                } else
                {
                    fuelConsumption += 2;
                    player.motionY = 0.0f;
                }
            }
            player.fallDistance = 0;

            //Smoke
            if (!world.isRemote)
            {
                ModNetwork.sendToNearby(new EntityParticlePacket.Message(EntityParticlePacket.COPTER_PARTICLE, player), player);
            }
            //Sound

            float factor = 0.05f;
            if (!player.onGround)
            {
                //Airwave
                pushEntities(world, player, 0.2f);
                //movement boost
                player.moveEntityWithHeading(player.moveStrafing, player.moveForward);
            } else
            {
                pushEntities(world, player, factor + 0.4f);
            }

            //Elevation clientside
            if (world.isRemote)
            {
                if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
                {
                    if (inv.canConsumeFuel(fuelConsumption + 2) && canElevate)
                    {
                        elevate(player, stack);
                    }
                }
            }

            //Elevation serverside
            if (!player.onGround && player.motionY > 0)
            {
                fuelConsumption += 2;
            }
            int ticks = inv.tickCounter - 1;
            if (inv.fuelTank.getFluid() != null)
            {
                if (GeneralReference.isValidFuel(inv.getFuelTank().getFluid().getFluid()))
                {
                    fuelConsumption = (int) Math.floor(fuelConsumption * GeneralReference.liquidFuels.get(inv.getFuelTank().getFluid().getFluid().getName()));
                }
            }
            if (ticks <= 0)
            {
                inv.tickCounter = 3;
                inv.consumeFuel(fuelConsumption);
                inv.dirtyTanks();
            } else
            {
                inv.tickCounter = ticks;
            }
        }
        inv.closeInventory(player);
    }

    @Override
    public void onPlayerDeath(World world, EntityPlayer player, ItemStack stack)
    {
        onUnequipped(world, player, stack);
        if (!Utils.isSoulBounded(stack)) BackpackProperty.get(player).setWearable(null);
    }

    @Override
    public void onEquipped(World world, EntityPlayer player, ItemStack stack)
    {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setByte("status", OFF_MODE);
    }

    @Override
    public void onUnequipped(World world, EntityPlayer player, ItemStack stack)
    {
        stack.getTagCompound().setByte("status", OFF_MODE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getWearableModel(ItemStack wearable)
    {
        return ClientProxy.modelCopterPack.setWearable(wearable);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getWearableTexture(ItemStack wearable)
    {
        return Resources.modelTextures("copterPack");
    }

}