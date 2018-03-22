package com.darkona.adventurebackpack.network.messages;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.client.ClientActions;
import com.darkona.adventurebackpack.init.ModNetwork;

public class EntitySoundPacket implements IMessageHandler<EntitySoundPacket.Message, EntitySoundPacket.Message>
{
    public static final byte NYAN_SOUND = 0;
    public static final byte COPTER_SOUND = 1;
    public static final byte JETPACK_FIZZ = 2;
    public static final byte BOILING_BUBBLES = 3;
    public static final byte LEAKING_STEAM = 4;

    @Nullable
    @Override
    public Message onMessage(Message message, MessageContext ctx)
    {
        if (ctx.side.isClient())
        {
            ClientActions.playSoundAtEntity(Minecraft.getMinecraft().world.getEntityByID(message.entityID), message.soundCode);
        }
        else
        {
            EntityPlayer player = ctx.getServerHandler().player;
            ModNetwork.sendToNearby(message, player);
        }
        return null;
    }

    public static class Message implements IMessage
    {
        private byte soundCode;
        private int entityID;

        public Message() {}

        public Message(byte soundCode, Entity entity)
        {
            this.soundCode = soundCode;
            this.entityID = entity.getEntityId();
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            soundCode = buf.readByte();
            entityID = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeByte(soundCode);
            buf.writeInt(entityID);
        }
    }
}