package com.darkona.adventurebackpack.init;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.darkona.adventurebackpack.network.CowAbilityPacket;
import com.darkona.adventurebackpack.network.CycleToolPacket;
import com.darkona.adventurebackpack.network.EquipUnequipBackWearablePacket;
import com.darkona.adventurebackpack.network.GuiPacket;
import com.darkona.adventurebackpack.network.PlayerActionPacket;
import com.darkona.adventurebackpack.network.SleepingBagPacket;
import com.darkona.adventurebackpack.network.SyncPropertiesPacket;
import com.darkona.adventurebackpack.network.WearableModePacket;
import com.darkona.adventurebackpack.network.messages.EntityParticlePacket;
import com.darkona.adventurebackpack.network.messages.EntitySoundPacket;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.reference.ModInfo;

public class ModNetwork
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);

    private static int messageID = 1;

    public static void init()
    {
        registerMessage(SyncPropertiesPacket.class, SyncPropertiesPacket.Message.class);
        registerMessage(EntityParticlePacket.class, EntityParticlePacket.Message.class);
        registerMessage(EntitySoundPacket.class, EntitySoundPacket.Message.class);

        registerMessage(WearableModePacket.class, WearableModePacket.Message.class);
        registerMessage(CycleToolPacket.class, CycleToolPacket.CycleToolMessage.class);
        //registerMessage(GuiPacket.class, GuiPacket.GuiMessage.class);
        registerServerSide(GuiPacket.class, GuiPacket.GuiMessage.class);
        registerMessage(SleepingBagPacket.class, SleepingBagPacket.SleepingBagMessage.class);
        registerMessage(CowAbilityPacket.class, CowAbilityPacket.CowAbilityMessage.class);
        registerMessage(PlayerActionPacket.class, PlayerActionPacket.ActionMessage.class);
        registerMessage(EquipUnequipBackWearablePacket.class, EquipUnequipBackWearablePacket.Message.class);
    }

    @SuppressWarnings("unchecked")
    public static void registerClientSide(Class handler, Class message)
    {
        INSTANCE.registerMessage(handler, message, messageID, Side.CLIENT);
        messageID++;
    }

    @SuppressWarnings("unchecked")
    public static void registerServerSide(Class handler, Class message)
    {
        INSTANCE.registerMessage(handler, message, messageID, Side.SERVER);
        messageID++;
    }

    @SuppressWarnings("unchecked")
    private static void registerMessage(Class handler, Class message)
    {
        INSTANCE.registerMessage(handler, message, messageID, Side.CLIENT);
        INSTANCE.registerMessage(handler, message, messageID, Side.SERVER);
        messageID++;
    }

    public static void sendToNearby(IMessage message, EntityPlayer player)
    {
        if (player != null && player.world instanceof WorldServer)
        {
            try
            {
                ((WorldServer) player.world).getEntityTracker().sendToTracking(player, ModNetwork.INSTANCE.getPacketFrom(message));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static void sendToDimension(IMessage message, EntityPlayer player)
    {
        INSTANCE.sendToDimension(message, player.dimension);
        BackpackProperty.sync(player);
    }
}
