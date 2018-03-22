package com.darkona.adventurebackpack.network;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.common.ServerActions;

public class CycleToolPacket implements IMessageHandler<CycleToolPacket.Message, IMessage>
{
    public static final byte TOGGLE_HOSE_TANK = 0;
    public static final byte SWITCH_HOSE_ACTION = 1;
    public static final byte CYCLE_TOOL_ACTION = 2;

    @Nullable
    @Override
    public IMessage onMessage(Message message, MessageContext ctx)
    {
        if (ctx.side.isServer())
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            switch (message.typeOfAction)
            {
                case CYCLE_TOOL_ACTION:
                    ServerActions.cycleTool(player, message.isWheelUp, message.slot);
                    break;
                case TOGGLE_HOSE_TANK:
                    ServerActions.switchHose(player, message.isWheelUp, ServerActions.HOSE_TOGGLE);
                    break;
                case SWITCH_HOSE_ACTION:
                    ServerActions.switchHose(player, message.isWheelUp, ServerActions.HOSE_SWITCH);
                    break;
            }
        }
        return null;
    }

    public static class Message implements IMessage
    {
        private byte typeOfAction;
        private boolean isWheelUp;
        private int slot;

        public Message() {}

        public Message(boolean isWheelUp, int slot, byte typeOfAction)
        {
            this.typeOfAction = typeOfAction;
            this.isWheelUp = isWheelUp;
            this.slot = slot;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            this.typeOfAction = buf.readByte();
            this.isWheelUp = buf.readBoolean();
            this.slot = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeByte(typeOfAction);
            buf.writeBoolean(isWheelUp);
            buf.writeInt(slot);
        }
    }
}
