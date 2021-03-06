package com.darkona.adventurebackpack.network;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.util.Wearing;

public class GuiPacket implements IMessageHandler<GuiPacket.Message, IMessage>
{
    public static final byte FROM_WEARING = 0;
    public static final byte FROM_HOLDING = 1;

    public static final byte GUI_BACKPACK = 1;
    public static final byte GUI_COPTER = 2;
    public static final byte GUI_JETPACK = 3;

    @Nullable
    @Override
    public IMessage onMessage(Message message, MessageContext ctx)
    {
        if (!ctx.side.isServer())
            return null;

        EntityPlayerMP player = ctx.getServerHandler().player;

//        if (player == null)
//            return null;

        if (message.type == GUI_COPTER)
        {
            if (message.from == FROM_WEARING)
            {
                if (Wearing.isWearingCopter(player))
                {
                    player.getServerWorld().addScheduledTask(()
                            -> AdventureBackpack.PROXY.displayCopterGUI(player,
                            new InventoryCopter(Wearing.getWearingCopter(player)), Constants.Source.WEARING));
                    return null;
                }
            }
            else if (message.from == FROM_HOLDING)
            {
                if (Wearing.isHoldingCopter(player))
                {
                    player.getServerWorld().addScheduledTask(()
                            -> AdventureBackpack.PROXY.displayCopterGUI(player,
                            new InventoryCopter(Wearing.getHoldingCopter(player)), Constants.Source.HOLDING));
                    return null;
                }
            }
        }
        else if (message.type == GUI_JETPACK)
        {
            if (message.from == FROM_WEARING)
            {
                if (Wearing.isWearingJetpack(player))
                {
                    player.getServerWorld().addScheduledTask(()
                            -> AdventureBackpack.PROXY.displayJetpackGUI(player,
                            new InventoryJetpack(Wearing.getWearingJetpack(player)), Constants.Source.WEARING));
                    return null;
                }
            }
            else if (message.from == FROM_HOLDING)
            {
                if (Wearing.isHoldingJetpack(player))
                {
                    player.getServerWorld().addScheduledTask(()
                            -> AdventureBackpack.PROXY.displayJetpackGUI(player,
                            new InventoryJetpack(Wearing.getHoldingJetpack(player)), Constants.Source.HOLDING));
                    return null;
                }
            }
        }
        else if (message.type == GUI_BACKPACK)
        {
            if (!GeneralReference.isDimensionAllowed(player))
                return null;

            if (message.from == FROM_WEARING)
            {
                if (Wearing.isWearingBackpack(player))
                {
                    player.getServerWorld().addScheduledTask(()
                            -> AdventureBackpack.PROXY.displayBackpackGUI(player,
                            new InventoryBackpack(Wearing.getWearingBackpack(player)), Constants.Source.WEARING));
                    return null;
                }
            }
            else if (message.from == FROM_HOLDING)
            {
                if (Wearing.isHoldingBackpack(player))
                {
                    player.getServerWorld().addScheduledTask(()
                            -> AdventureBackpack.PROXY.displayBackpackGUI(player,
                            new InventoryBackpack(Wearing.getHoldingBackpack(player)), Constants.Source.HOLDING));
                    return null;
                }
            }
        }
        return null;
    }

    public static class Message implements IMessage
    {
        private byte type;
        private byte from;

        public Message() {}

        public Message(byte type, byte from)
        {
            this.type = type;
            this.from = from;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            this.type = buf.readByte();
            this.from = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeByte(type);
            buf.writeByte(from);
        }
    }
}
