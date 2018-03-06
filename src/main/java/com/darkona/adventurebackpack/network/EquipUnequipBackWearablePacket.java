package com.darkona.adventurebackpack.network;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 08/01/2015
 *
 * @author Darkona
 */
public class EquipUnequipBackWearablePacket implements IMessageHandler<EquipUnequipBackWearablePacket.Message, EquipUnequipBackWearablePacket.Message>
{
    public static final byte EQUIP_WEARABLE = 0;
    public static final byte UNEQUIP_WEARABLE = 1;

    @Override
    public Message onMessage(Message message, MessageContext ctx)
    {

        if (ctx.side.isServer())
        {
            EntityPlayer player = ctx.getServerHandler().player;
            if (message.action == EQUIP_WEARABLE)
            {
                if (Wearing.isHoldingWearable(player))
                {
                    if (Wearing.isWearingWearable(player))
                    {
                        Wearing.WearableType wtype = Wearing.getWearingWearableType(player);
                        if (wtype != Wearing.WearableType.UNKNOWN)
                            player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.already.equipped." + wtype.name().toLowerCase()));
                    }
                    else
                    {
                        if (BackpackUtils.equipWearable(player.getHeldItemMainhand(), player) == BackpackUtils.Reasons.SUCCESSFUL)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            player.inventoryContainer.detectAndSendChanges();
                        }
                    }
                }
            }
            if (message.action == UNEQUIP_WEARABLE)
            {
                BackpackUtils.unequipWearable(player);
            }
        }
        return null;
    }

    public static class Message implements IMessage
    {
        private byte action;

        public Message()
        {

        }

        public Message(byte action)
        {
            this.action = action;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            action = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeByte(action);
        }
    }
}
