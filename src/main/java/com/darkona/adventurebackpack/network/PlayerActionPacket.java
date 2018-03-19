package com.darkona.adventurebackpack.network;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.inventory.ContainerBackpack;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.util.Wearing;

public class PlayerActionPacket implements IMessageHandler<PlayerActionPacket.ActionMessage, IMessage>
{
    public static final byte SPIDER_JUMP = 0;
    public static final byte JETPACK_IN_USE = 1;
    public static final byte JETPACK_NOT_IN_USE = 2;
    public static final byte GUI_HOLDING_SPACE = 3;
    public static final byte GUI_NOT_HOLDING_SPACE = 4;

    @Override
    public IMessage onMessage(ActionMessage message, MessageContext ctx)
    {
        if (ctx.side.isServer())
        {
            EntityPlayerMP player = ctx.getServerHandler().player;

            if (player != null)
            {
                if (message.type == SPIDER_JUMP)
                {
                    if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityFriendlySpider)
                    {
                        ((EntityFriendlySpider) player.getRidingEntity()).setJumping(true);
                    }
                }

                if (message.type == JETPACK_IN_USE || message.type == JETPACK_NOT_IN_USE)
                {
                    if (Wearing.isWearingJetpack(player))
                    {
                        InventoryJetpack inv = new InventoryJetpack(Wearing.getWearingJetpack(player));
                        inv.setInUse(message.type == JETPACK_IN_USE);
                        inv.markDirty();
                    }
                }

                if (message.type == GUI_HOLDING_SPACE || message.type == GUI_NOT_HOLDING_SPACE)
                {
                    if (player.openContainer instanceof ContainerBackpack)
                    {
                        IInventoryBackpack inv = ((ContainerBackpack) player.openContainer).getInventoryBackpack();

                        if (message.type == GUI_HOLDING_SPACE)
                        {
                            inv.getExtendedProperties().setBoolean(Constants.TAG_HOLDING_SPACE, true);
                        }
                        else if (message.type == GUI_NOT_HOLDING_SPACE)
                        {
                            inv.getExtendedProperties().removeTag(Constants.TAG_HOLDING_SPACE);
                        }
                    }
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
