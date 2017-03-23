package com.darkona.adventurebackpack.network;

import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.inventory.InventoryCoalJetpack;
import com.darkona.adventurebackpack.util.Wearing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by Darkona on 12/10/2014.
 */
public class PlayerActionPacket implements IMessageHandler<PlayerActionPacket.ActionMessage, IMessage>
{
    public static final byte spiderJump = 0;
    public static final byte JETPACK_IN_USE = 1;
    public static final byte JETPACK_NOT_IN_USE = 2;

    @Override
    public IMessage onMessage(ActionMessage message, MessageContext ctx)
    {
        if (ctx.side.isServer())
        {
            if(message.type == spiderJump){
                if(ctx.getServerHandler().playerEntity.getRidingEntity() != null && ctx.getServerHandler().playerEntity.getRidingEntity() instanceof EntityFriendlySpider)
                {
                    ((EntityFriendlySpider)ctx.getServerHandler().playerEntity.getRidingEntity()).setJumping(true);
                }
            }

            if(message.type == JETPACK_IN_USE || message.type == JETPACK_NOT_IN_USE)
            {
                if(Wearing.isWearingJetpack(ctx.getServerHandler().playerEntity))
                {
                    InventoryCoalJetpack inv = new InventoryCoalJetpack(Wearing.getWearingJetpack(ctx.getServerHandler().playerEntity));
                    inv.setInUse(message.type == JETPACK_IN_USE);
                    inv.markDirty();
                }
            }

        }
        return null;
    }

    public static class ActionMessage implements IMessage
    {

        private byte type;

        public ActionMessage()
        {
        }

        public ActionMessage(byte type)
        {
            this.type = type;

        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            this.type = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeByte(type);
        }
    }
}
