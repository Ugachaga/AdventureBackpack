package com.darkona.adventurebackpack.handlers;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.item.ItemAdventureBackpack;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.network.CycleToolPacket;
import com.darkona.adventurebackpack.network.GUIPacket;
import com.darkona.adventurebackpack.network.PlayerActionPacket;
import com.darkona.adventurebackpack.network.SyncPropertiesPacket;
import com.darkona.adventurebackpack.network.WearableModePacket;
import com.darkona.adventurebackpack.reference.Key;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Darkona on 11/10/2014.
 */

public class KeybindHandler
{
    private static Key getPressedKeyBinding()
    {
        if (Keybindings.openBackpack.isPressed())
        {
            return Key.INVENTORY_KEY;
        }
        if (Keybindings.toggleHose.isPressed())
        {
            return Key.TOGGLE_KEY;
        }

        if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed())
        {
            return Key.JUMP;
        }
        return Key.UNKNOWN;
    }

    private static final String[] NIGHTVISION_BACKPACKS = { "Bat", "Squid", "Dragon" };

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Key keypressed = getPressedKeyBinding();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player != null)
        {
            if (keypressed == Key.INVENTORY_KEY)
            {
                if (mc.inGameHasFocus)
                {
                    if (player.isSneaking() && (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemAdventureBackpack))
                    {
                        ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.BACKPACK_GUI, GUIPacket.FROM_HOLDING));
                    } else
                    {
                        ModNetwork.net.sendToServer(new SyncPropertiesPacket.Message());
                        if (Wearing.isWearingBackpack(player))
                        {
                            ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.BACKPACK_GUI, GUIPacket.FROM_KEYBIND));
                        } else if ((player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemAdventureBackpack))
                        {
                            ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.BACKPACK_GUI, GUIPacket.FROM_HOLDING));
                        }
                        if (Wearing.isWearingCopter(player))
                        {
                            ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.COPTER_GUI, GUIPacket.FROM_KEYBIND));
                        }
                        if (Wearing.isWearingJetpack(player))
                        {
                            ModNetwork.net.sendToServer(new GUIPacket.GUImessage(GUIPacket.JETPACK_GUI, GUIPacket.FROM_KEYBIND));
                        }
                    }
                }
            }

            if (keypressed == Key.TOGGLE_KEY)
            {
                if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemHose)
                {
                    ModNetwork.net.sendToServer(new CycleToolPacket.CycleToolMessage(0, (player).inventory.currentItem, CycleToolPacket.TOGGLE_HOSE_TANK));
                    ServerActions.switchHose(player, ServerActions.HOSE_TOGGLE, 0, (player).inventory.currentItem);
                } else if (Wearing.isWearingBackpack(player))
                {
                    if (player.isSneaking())
                    {
                        for (String valid : NIGHTVISION_BACKPACKS)
                        {
                            if (Wearing.getBackpackInv(player, true).getColorName().equals(valid))
                            {
                                ModNetwork.net.sendToServer(new WearableModePacket.Message(WearableModePacket.NIGHTVISION_ON_OFF, ""));
                                ServerActions.toggleNightVision(player, Wearing.getWearingBackpack(player), WearableModePacket.NIGHTVISION_ON_OFF);
                            }
                        }
                    } else
                    {
                        ModNetwork.net.sendToServer(new WearableModePacket.Message(WearableModePacket.CYCLING_ON_OFF, ""));
                        ServerActions.toggleToolCycling(player, Wearing.getWearingBackpack(player), WearableModePacket.CYCLING_ON_OFF);
                    }
                }
                if (Wearing.isWearingCopter(player))
                {
                    if (!player.isSneaking())
                    {
                        ModNetwork.net.sendToServer(new WearableModePacket.Message(WearableModePacket.COPTER_TOGGLE, ""));
                        ServerActions.toggleCopterPack(player, Wearing.getWearingCopter(player), WearableModePacket.COPTER_TOGGLE);
                    } else
                    {
                        ModNetwork.net.sendToServer(new WearableModePacket.Message(WearableModePacket.COPTER_ON_OFF, ""));
                        ServerActions.toggleCopterPack(player, Wearing.getWearingCopter(player), WearableModePacket.COPTER_ON_OFF);
                    }
                }
                if (Wearing.isWearingJetpack(player))
                {
                    if (player.isSneaking())
                    {
                        ModNetwork.net.sendToServer(new WearableModePacket.Message(WearableModePacket.JETPACK_ON_OFF, ""));
                        ServerActions.toggleCoalJetpack(player, Wearing.getWearingJetpack(player), WearableModePacket.JETPACK_ON_OFF);
                    }
                }
            }

            if (keypressed == Key.JUMP)
            {
                if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityFriendlySpider)
                {
                    ModNetwork.net.sendToServer(new PlayerActionPacket.ActionMessage(PlayerActionPacket.spiderJump));
                    ((EntityFriendlySpider) player.getRidingEntity()).setJumping(true);
                }
            }
        }
    }
}
