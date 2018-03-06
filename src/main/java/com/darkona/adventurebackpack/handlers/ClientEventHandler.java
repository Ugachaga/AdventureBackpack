package com.darkona.adventurebackpack.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.SlotTool;
import com.darkona.adventurebackpack.item.ItemAdventureBackpack;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.network.CycleToolPacket;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class ClientEventHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void mouseWheelDetect(MouseEvent event)
    {
        /*Special thanks go to MachineMuse, both for inspiration and the event. God bless you girl.*/
        Minecraft mc = Minecraft.getMinecraft();
        if (event.getDwheel() != 0)
        {
            boolean isWheelUp = event.getDwheel() > 0;
            EntityPlayerSP player = mc.player;
            if (player != null && !player.isDead && player.isSneaking())
            {
                ItemStack backpack = Wearing.getWearingBackpack(player);
                if (backpack != null && backpack.getItem() instanceof ItemAdventureBackpack)
                {
                    if (player.getHeldItemMainhand() != ItemStack.EMPTY)
                    {
                        int slot = player.inventory.currentItem;
                        ItemStack heldItem = player.inventory.getStackInSlot(slot);
                        Item theItem = heldItem.getItem();

                        if ((ConfigHandler.enableToolsCycling && !Wearing.getWearingBackpackInv(player).getDisableCycling() && SlotTool.isValidTool(heldItem))
                                || (BackpackTypes.getType(backpack) == BackpackTypes.SKELETON && theItem.equals(Items.BOW)))
                        {
                            ModNetwork.net.sendToServer(new CycleToolPacket.CycleToolMessage(isWheelUp, slot, CycleToolPacket.CYCLE_TOOL_ACTION));
                            ServerActions.cycleTool(player, isWheelUp, slot);
                            event.setCanceled(true);
                        }
                        else if (theItem instanceof ItemHose)
                        {
                            ModNetwork.net.sendToServer(new CycleToolPacket.CycleToolMessage(isWheelUp, slot, CycleToolPacket.SWITCH_HOSE_ACTION));
                            ServerActions.switchHose(player, isWheelUp, ServerActions.HOSE_SWITCH);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }
}