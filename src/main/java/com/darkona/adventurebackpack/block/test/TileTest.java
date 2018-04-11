package com.darkona.adventurebackpack.block.test;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import com.darkona.adventurebackpack.capablities.BedrollHandler;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.item.ItemBackpack;
import com.darkona.adventurebackpack.network.updated.PacketRequestUpdateTest;
import com.darkona.adventurebackpack.network.updated.PacketUpdateTest;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.BackpackUtils;

public class TileTest extends TileEntity
{
    public long lastChangeTime;
    public BackpackTypes type = BackpackTypes.UNKNOWN;


    //TODO see https://github.com/Ellpeck/ActuallyAdditions/blob/master/src/main/java/de/ellpeck/actuallyadditions/mod/tile/TileEntityBase.java

    public ItemStackHandler inventory = new ItemStackHandler(Constants.INVENTORY_SIZE)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            if (!world.isRemote)
            {
                lastChangeTime = world.getTotalWorldTime();
                ModNetwork.INSTANCE.sendToAllAround(new PacketUpdateTest(TileTest.this),
                        new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }
    };

    public BedrollHandler bedroll = new BedrollHandler();


    @Override
    public void onLoad()
    {
        if (world.isRemote)
        {
            ModNetwork.INSTANCE.sendToServer(new PacketRequestUpdateTest(this));
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        if (!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(0).getItem() instanceof ItemBackpack)
        {
            type = BackpackTypes.getType(BackpackUtils.getWearableCompound(inventory.getStackInSlot(0)).getInteger("type"));
        }
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setTag("bedroll", bedroll.serializeNBT());
        compound.setLong("lastChangeTime", lastChangeTime);
        compound.setInteger("type", type.getMeta());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        bedroll.deserializeNBT(compound.getCompoundTag("bedroll"));
        lastChangeTime = compound.getLong("lastChangeTime");
        type = BackpackTypes.getType(compound.getInteger("type"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
               ? (T) inventory
               : super.getCapability(capability, facing);
    }
}
