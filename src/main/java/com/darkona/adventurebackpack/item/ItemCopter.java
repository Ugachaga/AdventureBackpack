package com.darkona.adventurebackpack.item;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.network.GuiPacket;
import com.darkona.adventurebackpack.network.messages.EntityParticlePacket;
import com.darkona.adventurebackpack.proxy.ClientProxy;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.TipUtils;
import com.darkona.adventurebackpack.util.Wearing;

import static com.darkona.adventurebackpack.common.Constants.Copter.FUEL_CAPACITY;
import static com.darkona.adventurebackpack.common.Constants.Copter.TAG_FUEL_TANK;
import static com.darkona.adventurebackpack.common.Constants.Copter.TAG_STATUS;
import static com.darkona.adventurebackpack.util.TipUtils.l10n;

public class ItemCopter extends ItemWearable
{
    public static final byte OFF_MODE = 0;
    public static final byte NORMAL_MODE = 1;
    public static final byte HOVER_MODE = 2;

    private static final int MAX_ALTITUDE = 250;

    private float fuelSpent;

    public ItemCopter(String name)
    {
        super(name);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (isInCreativeTab(tab))
        {
            items.add(BackpackUtils.createCopterStack());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (world == null) return;

        FluidTank fuelTank = new FluidTank(FUEL_CAPACITY);
        NBTTagCompound copterTag = BackpackUtils.getWearableCompound(stack);

        if (GuiScreen.isShiftKeyDown())
        {
            fuelTank.readFromNBT(copterTag.getCompoundTag(TAG_FUEL_TANK));
            tooltip.add(l10n("copter.tank.fuel") + ": " + TipUtils.tankTooltip(fuelTank));
            tooltip.add(l10n("copter.rate.fuel") + ": " + TipUtils.fuelConsumptionTooltip(fuelTank));

            TipUtils.shiftFooter(tooltip);
        }
        else if (!GuiScreen.isCtrlKeyDown())
        {
            tooltip.add(TipUtils.holdShift());
        }

        if (GuiScreen.isCtrlKeyDown())
        {
            tooltip.add(l10n("max.altitude") + ": " + TipUtils.whiteFormat(MAX_ALTITUDE + " ") + l10n("meters"));
            tooltip.add(TipUtils.pressShiftKeyFormat(TipUtils.actionKeyFormat()) + l10n("copter.key.onoff1"));
            tooltip.add(l10n("copter.key.onoff2") + " " + l10n("on"));

            tooltip.add(TipUtils.pressKeyFormat(TipUtils.actionKeyFormat()) + l10n("copter.key.hover1"));
            tooltip.add(l10n("copter.key.hover2"));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrent)
    {

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (world.isRemote)
        {
            ModNetwork.INSTANCE.sendToServer(new GuiPacket.GuiMessage(GuiPacket.GUI_COPTER, GuiPacket.FROM_HOLDING));
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public void onEquipped(World world, EntityPlayer player, ItemStack stack)
    {
        BackpackUtils.getWearableCompound(stack).setByte(TAG_STATUS, OFF_MODE);
    }

    @Override
    public void onEquippedUpdate(World world, EntityPlayer player, ItemStack stack) //TODO extract behavior to separate class
    {
        InventoryCopter inv = new InventoryCopter(Wearing.getWearingCopter(player));
        inv.openInventory(player);
        boolean canElevate = true;
        float fuelConsumption = 0.0f;
        if (inv.getStatus() != OFF_MODE)
        {
            if (player.isInWater())
            {
                inv.setStatus(OFF_MODE);
                inv.dirtyStatus();
                if (!world.isRemote)
                {
                    player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.copter.cantwater"));
                }
                return;
            }
            if (inv.getFuelTank().getFluidAmount() == 0)
            {
                canElevate = false;
                if (player.onGround)
                {
                    inv.setStatus(OFF_MODE);
                    inv.dirtyStatus();
                    if (!world.isRemote)
                    {
                        player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.copter.off"));
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
                        player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.copter.outoffuel"));
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
                }
                else
                {
                    fuelConsumption *= 2;
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
                player.travel(player.moveStrafing * factor, 0f, player.moveForward * factor); //TODO usage of factor?
                //player.moveFlying(player.moveStrafing, player.moveForward, factor);
            }
            else
            {
                pushEntities(world, player, factor + 0.4f);
            }

            //Elevation clientside
            if (world.isRemote)
            {
                if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
                {
                    if (inv.canConsumeFuel((int) Math.ceil(fuelConsumption * 2)) && canElevate)
                    {
                        elevate(player, stack);
                    }
                }
            }

            //Elevation serverside
            if (!player.onGround && player.motionY > 0)
            {
                fuelConsumption *= 2;
            }
            int ticks = inv.getTickCounter() - 1;
            FluidTank tank = inv.getFuelTank();
            if (tank.getFluid() != null && GeneralReference.isValidFuel(tank.getFluid().getFluid().getName()))
            {
                fuelConsumption = fuelConsumption * GeneralReference.getFuelRate(tank.getFluid().getFluid().getName());
            }
            if (ticks <= 0)
            {
                inv.setTickCounter(3);
                inv.consumeFuel(getFuelSpent(fuelConsumption));
                inv.dirtyTanks();
            }
            else
            {
                inv.setTickCounter(ticks);
            }
        }
        inv.closeInventory(player);
    }

    private int getFuelSpent(float f)
    {
        f += fuelSpent;
        fuelSpent = f % 1;
        return (int) (f - fuelSpent);
    }

    private static void elevate(EntityPlayer player, ItemStack copter)
    {
        if (player.posY < 100)
            player.motionY = Math.max(player.motionY, 0.18);
        else if (player.posY < MAX_ALTITUDE)
            player.motionY = 0.18 - (player.posY - 100) / 1000;
        else if (player.posY >= MAX_ALTITUDE)
            player.motionY += 0;
    }

    @SuppressWarnings("unchecked")
    private void pushEntities(World world, EntityPlayer player, float speed)
    {
        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        List<EntityItem> groundItems = world.getEntitiesWithinAABB(
                EntityItem.class, new AxisAlignedBB(
                        posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(4.0D, 4.0D, 4.0D));

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
    public void onUnequipped(World world, EntityPlayer player, ItemStack stack)
    {
        BackpackUtils.getWearableCompound(stack).setByte(TAG_STATUS, OFF_MODE);
    }

    @Override
    public void onPlayerDeath(World world, EntityPlayer player, ItemStack stack)
    {
        onUnequipped(world, player, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getWearableModel(ItemStack wearable)
    {
        return ClientProxy.modelCopter.setWearable(wearable);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getWearableTexture(ItemStack wearable)
    {
        return Resources.modelTextures("copterPack");
    }

}