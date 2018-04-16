package com.darkona.adventurebackpack.network;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;

public class SyncPropertiesPacket implements IMessageHandler<SyncPropertiesPacket.Message, SyncPropertiesPacket.Message>
{
    public static final byte DATA_ONLY = 0; //TODO del?
    public static final byte TANKS_ONLY = 1;
    public static final byte INVENTORY_ONLY = 2;
    public static final byte FULL_DATA = 3;

    @Nullable
    @Override
    public Message onMessage(Message message, MessageContext ctx)
    {
        if (ctx.side.isClient() && message.properties != null)
        {
            if (Minecraft.getMinecraft().world == null)
            {
                ModNetwork.INSTANCE.sendToServer(new SyncPropertiesPacket.Message());
            }
            else
            {
                //AdventureBackpack.PROXY.synchronizePlayer(message.ID, message.properties);
            }
        }
        if (ctx.side.isServer())
        {
            BackpackProperty.sync(ctx.getServerHandler().player);
        }
        return null;
    }

    public static class Message implements IMessage
    {
        private int ID;
        private byte type;
        private NBTTagCompound properties;

        public Message() {}

        public Message(int id, NBTTagCompound props)
        {
            ID = id;
            properties = props;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            ID = buf.readInt();
            properties = ByteBufUtils.readTag(buf);
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeInt(ID);
            ByteBufUtils.writeTag(buf, properties);
        }
    }
}