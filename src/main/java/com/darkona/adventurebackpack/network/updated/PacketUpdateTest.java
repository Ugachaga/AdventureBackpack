package com.darkona.adventurebackpack.network.updated;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.block.test.TileTest;
import com.darkona.adventurebackpack.reference.BackpackTypes;

public class PacketUpdateTest implements IMessage
{
    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;
    private BackpackTypes type;

    public PacketUpdateTest(BackpackTypes type, BlockPos pos, ItemStack stack, long lastChangeTime)
    {
        this.type = type;
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdateTest(TileTest te)
    {
        this(te.type, te.getPos(), te.inventory.getStackInSlot(0), te.lastChangeTime);
    }

    public PacketUpdateTest() {}


    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(type.getMeta());
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        type = BackpackTypes.getType(buf.readInt());
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }


    public static class Handler implements IMessageHandler<PacketUpdateTest, IMessage>
    {
        @Override
        public IMessage onMessage(PacketUpdateTest message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileTest te = (TileTest) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                te.type = message.type;
                te.inventory.setStackInSlot(0, message.stack);
                te.lastChangeTime = message.lastChangeTime;
            });
            return null;
        }

    }
}