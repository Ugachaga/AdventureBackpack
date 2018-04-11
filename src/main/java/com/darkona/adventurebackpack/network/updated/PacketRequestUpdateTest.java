package com.darkona.adventurebackpack.network.updated;

import io.netty.buffer.ByteBuf;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.block.test.TileTest;

public class PacketRequestUpdateTest implements IMessage
{
    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateTest(BlockPos pos, int dimension)
    {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateTest(TileTest te)
    {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateTest() {}

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateTest, PacketUpdateTest>
    {
        @Override
        public PacketUpdateTest onMessage(PacketRequestUpdateTest message, MessageContext ctx)
        {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileTest te = (TileTest) world.getTileEntity(message.pos);
            if (te != null)
            {
                return new PacketUpdateTest(te);
            }
            else
            {
                return null;
            }
        }
    }
}
