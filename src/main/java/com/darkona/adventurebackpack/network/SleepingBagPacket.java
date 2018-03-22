package com.darkona.adventurebackpack.network;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;

public class SleepingBagPacket implements IMessageHandler<SleepingBagPacket.Message, IMessage>
{
    @Nullable
    @Override
    public IMessage onMessage(Message message, MessageContext ctx)
    {
        if (!ctx.side.isServer())
            return null;

        if (message.isTile || ConfigHandler.portableSleepingBag) // serverside check
        {
            ServerActions.toggleSleepingBag(ctx.getServerHandler().player, message.isTile, message.cX, message.cY, message.cZ);
        }
        return null;
    }

    public static class Message implements IMessage
    {
        private boolean isTile;
        private int cX;
        private int cY;
        private int cZ;

        public Message() {}

        public Message(boolean isTile, int cX, int cY, int cZ)
        {
            this.isTile = isTile;
            this.cX = cX;
            this.cY = cY;
            this.cZ = cZ;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            isTile = buf.readBoolean();
            cX = buf.readInt();
            cY = buf.readInt();
            cZ = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeBoolean(isTile);
            buf.writeInt(cX);
            buf.writeInt(cY);
            buf.writeInt(cZ);
        }
    }
}
