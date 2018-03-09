package com.darkona.adventurebackpack.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.network.CycleToolPacket;
import com.darkona.adventurebackpack.network.GuiPacket;
import com.darkona.adventurebackpack.network.PlayerActionPacket;
import com.darkona.adventurebackpack.network.SyncPropertiesPacket;
import com.darkona.adventurebackpack.network.WearableModePacket;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created by Darkona on 11/10/2014.
 */

public class KeyInputEventHandler
{
    public enum Key
    {
        UNKNOWN, INVENTORY, ACTION, JUMP
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Key pressedKey = getPressedKeyBinding();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (pressedKey == Key.UNKNOWN || player == null)
            return;

        if (pressedKey == Key.INVENTORY && mc.inGameHasFocus)
        {
            sendSyncPropertiesPacket();

            if (player.isSneaking())
            {
                if (Wearing.isHoldingBackpack(player))
                {
                    sendGUIPacket(GuiPacket.GUI_BACKPACK, GuiPacket.FROM_HOLDING);
                }
            }
            else
            {
                if (Wearing.isWearingBackpack(player))
                {
                    sendGUIPacket(GuiPacket.GUI_BACKPACK, GuiPacket.FROM_WEARING);
                }
                else if (Wearing.isHoldingBackpack(player))
                {
                    sendGUIPacket(GuiPacket.GUI_BACKPACK, GuiPacket.FROM_HOLDING);
                }

                if (Wearing.isWearingCopter(player))
                {
                    sendGUIPacket(GuiPacket.GUI_COPTER, GuiPacket.FROM_WEARING);
                }

                if (Wearing.isWearingJetpack(player))
                {
                    sendGUIPacket(GuiPacket.GUI_JETPACK, GuiPacket.FROM_WEARING);
                }
            }
        }

        if (pressedKey == Key.ACTION)
        {
            if (Wearing.isHoldingHose(player))
            {
                sendCycleToolPacket(player.inventory.currentItem, CycleToolPacket.TOGGLE_HOSE_TANK);
                ServerActions.switchHose(player, false, ServerActions.HOSE_TOGGLE);
            }
            else if (Wearing.isWearingBackpack(player))
            {
                if (player.isSneaking())
                {
                    if (BackpackTypes.isNightVision(Wearing.getWearingBackpackInv(player).getType()))
                    {
                        sendWearableModePacket(WearableModePacket.NIGHTVISION_ON_OFF);
                        ServerActions.toggleNightVision(player, Wearing.getWearingBackpack(player));
                    }
                }
                else
                {
                    sendWearableModePacket(WearableModePacket.CYCLING_ON_OFF);
                    ServerActions.toggleToolCycling(player, Wearing.getWearingBackpack(player));
                }
            }
            if (Wearing.isWearingCopter(player))
            {
                if (player.isSneaking())
                {
                    sendWearableModePacket(WearableModePacket.COPTER_ON_OFF);
                    ServerActions.toggleCopter(player, Wearing.getWearingCopter(player), WearableModePacket.COPTER_ON_OFF);
                }
                else
                {
                    sendWearableModePacket(WearableModePacket.COPTER_TOGGLE);
                    ServerActions.toggleCopter(player, Wearing.getWearingCopter(player), WearableModePacket.COPTER_TOGGLE);
                }
            }
            if (Wearing.isWearingJetpack(player))
            {
                if (player.isSneaking())
                {
                    sendWearableModePacket(WearableModePacket.JETPACK_ON_OFF);
                    ServerActions.toggleJetpack(player, Wearing.getWearingJetpack(player));
                }
            }
        }

        if (pressedKey == Key.JUMP)
        {
            if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityFriendlySpider)
            {
                sendPlayerActionPacket(PlayerActionPacket.SPIDER_JUMP);
                ((EntityFriendlySpider) player.getRidingEntity()).setJumping(true);
            }
        }
    }

    private static Key getPressedKeyBinding()
    {
        if (Keybindings.openInventory.isPressed())
        {
            return Key.INVENTORY;
        }
        if (Keybindings.toggleActions.isPressed())
        {
            return Key.ACTION;
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed())
        {
            return Key.JUMP;
        }
        return Key.UNKNOWN;
    }

    private void sendSyncPropertiesPacket()
    {
        ModNetwork.INSTANCE.sendToServer(new SyncPropertiesPacket.Message());
    }

    private void sendGUIPacket(byte type, byte from)
    {
        ModNetwork.INSTANCE.sendToServer(new GuiPacket.GuiMessage(type, from));
    }

    private void sendWearableModePacket(byte type)
    {
        ModNetwork.INSTANCE.sendToServer(new WearableModePacket.Message(type, "")); //TODO playerID?
    }

    private void sendCycleToolPacket(int slot, byte type)
    {
        ModNetwork.INSTANCE.sendToServer(new CycleToolPacket.CycleToolMessage(false, slot, type));
    }

    private void sendPlayerActionPacket(byte type)
    {
        ModNetwork.INSTANCE.sendToServer(new PlayerActionPacket.ActionMessage(type));
    }
}
