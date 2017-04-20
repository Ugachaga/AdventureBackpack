package com.darkona.adventurebackpack.handlers;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.item.ItemAdventureBackpack;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class ClientEventHandler
{
    /**
     * Makes the tool tips of the backpacks have the Tank information displayed below.
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void toolTips(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof ItemAdventureBackpack)
        {
            NBTTagCompound compound = event.getItemStack().getTagCompound();
            FluidTank tank = new FluidTank(Constants.basicTankCapacity);
            String tankInfo = "";
            if (compound != null)
            {
                if (compound.hasKey("leftTank"))
                {
                    tank.readFromNBT(compound.getCompoundTag("leftTank"));
                    String name = tank.getFluid() == null ? "" : tank.getFluid().getLocalizedName();
                    tankInfo = TextFormatting.BLUE + "Left Tank: " + tank.getFluidAmount() + "/" + tank.getCapacity() + " " + name;

                    event.getToolTip().add(tankInfo);
                }
                if (compound.hasKey("rightTank"))
                {
                    tank.readFromNBT(compound.getCompoundTag("rightTank"));
                    String name = tank.getFluid() == null ? "" : tank.getFluid().getLocalizedName();
                    tankInfo = TextFormatting.RED + "Right Tank: " + tank.getFluidAmount() + "/" + tank.getCapacity() + " " + name;

                    event.getToolTip().add(tankInfo);
                }
            }
        }
    }

    /**
     * @param event
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void mouseWheelDetect(MouseEvent event)
    {
        /*Special thanks go to MachineMuse, both for inspiration and the event. God bless you girl.*/
        //Minecraft mc = Minecraft.getMinecraft();
        int dWheel = event.getDwheel();
        if (dWheel != 0)
        {
            //LogHelper.debug("Mouse Wheel moving");

            /**
             * TODO: why is this erroring?

            EntityPlayer player = mc.thePlayer;
            if (player != null && !player.isDead && player.isSneaking())
            {
                ItemStack backpack = Wearing.getWearingBackpack(player);
                if (backpack != null && backpack.getItem() instanceof ItemAdventureBackpack)
                {
                    if (player.getHeldItemMainhand() != null)
                    {
                        int slot = player.inventory.currentItem;
                        ItemStack heldItem = player.inventory.getStackInSlot(slot);
                        Item theItem = heldItem.getItem();

                        if ((ConfigHandler.enableToolsCycling && !Wearing.getBackpackInv(player, true).getDisableCycling() && SlotTool.isValidTool(heldItem))
                                || ((BackpackNames.getBackpackColorName(backpack).equals("Skeleton") && theItem.equals(Items.BOW))))
                        {
                            ModNetwork.net.sendToServer(new CycleToolPacket.CycleToolMessage(dWheel, slot, CycleToolPacket.CYCLE_TOOL_ACTION));
                            ServerActions.cycleTool(player, dWheel, slot);
                            event.setCanceled(true);
                        }

                        if (theItem instanceof ItemHose)
                        {
                            ModNetwork.net.sendToServer(new CycleToolPacket.CycleToolMessage(dWheel, slot, CycleToolPacket.SWITCH_HOSE_ACTION));
                            ServerActions.switchHose(player, ServerActions.HOSE_SWITCH, dWheel, slot);
                            event.setCanceled(true);
                        }
                    }
                }
            }
            **/
        }
    }
}