package com.darkona.adventurebackpack.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.inventory.ContainerBackpack;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;

public class CowAbilityPacket implements IMessageHandler<CowAbilityPacket.CowAbilityMessage, IMessage>
{
    public static final byte CONSUME_WHEAT = 0;

    @Override
    public IMessage onMessage(CowAbilityMessage message, MessageContext ctx)
    {
        if (ctx.side.isClient())
        {
            EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(UUID.fromString(message.playerID));

            if (player.openContainer instanceof ContainerBackpack)
            {
                ContainerBackpack cont = ((ContainerBackpack) player.openContainer);
                cont.detectAndSendChanges();
                IInventoryBackpack inv = cont.getInventoryBackpack();
                switch (message.action)
                {
                    case CONSUME_WHEAT:
                        inv.consumeInventoryItem(Items.WHEAT);
                }
            }
        }
        return null;
    }

    public static class CowAbilityMessage implements IMessage
    {

        private byte action;
        private String playerID;

        public CowAbilityMessage()
        {
        }

        public CowAbilityMessage(String playerID, byte action)
        {
            this.playerID = playerID;
            this.action = action;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {

            playerID = ByteBufUtils.readUTF8String(buf);
            action = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            ByteBufUtils.writeUTF8String(buf, playerID);
            buf.writeByte(action);
        }
    }
}
