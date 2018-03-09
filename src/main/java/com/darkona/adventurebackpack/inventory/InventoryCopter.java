package com.darkona.adventurebackpack.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.item.ItemCopter;

import static com.darkona.adventurebackpack.common.Constants.Copter.BUCKET_IN;
import static com.darkona.adventurebackpack.common.Constants.Copter.BUCKET_OUT;
import static com.darkona.adventurebackpack.common.Constants.Copter.TAG_FUEL_TANK;
import static com.darkona.adventurebackpack.common.Constants.Copter.TAG_STATUS;
import static com.darkona.adventurebackpack.common.Constants.TAG_INVENTORY;
import static com.darkona.adventurebackpack.common.Constants.TAG_WEARABLE_COMPOUND;

/**
 * Created on 02/01/2015
 *
 * @author Darkona
 */
public class InventoryCopter extends InventoryAdventure
{
    private FluidTank fuelTank = new FluidTank(Constants.Copter.FUEL_CAPACITY);

    private byte status = ItemCopter.OFF_MODE;
    private int tickCounter = 0;

    public InventoryCopter(ItemStack copter)
    {
        super(copter, Constants.Copter.INVENTORY_SIZE);
        loadFromNBT(containerStack.getTagCompound());
    }

    @Override
    public ItemStack[] getInventory()
    {
        return inventory;
    }

    public FluidTank getFuelTank()
    {
        return fuelTank;
    }

    @Override
    public FluidTank[] getTanksArray()
    {
        return new FluidTank[]{fuelTank};
    }

    @Override
    public int[] getSlotsOnClosing()
    {
        return new int[]{BUCKET_IN, BUCKET_OUT};
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound)
    {
        NBTTagCompound copterTag = compound.getCompoundTag(TAG_WEARABLE_COMPOUND);
        setInventoryFromTagList(copterTag.getTagList(TAG_INVENTORY, NBT.TAG_COMPOUND));
        fuelTank.readFromNBT(copterTag.getCompoundTag(TAG_FUEL_TANK));
        status = copterTag.getByte(TAG_STATUS);
        tickCounter = copterTag.getInteger("tickCounter");
    }

    @Override
    public void saveToNBT(NBTTagCompound compound)
    {
        NBTTagCompound copterTag = new NBTTagCompound();
        copterTag.setTag(TAG_INVENTORY, getInventoryTagList());
        copterTag.setTag(TAG_FUEL_TANK, fuelTank.writeToNBT(new NBTTagCompound()));
        copterTag.setByte(TAG_STATUS, status);
        copterTag.setInteger("tickCounter", this.tickCounter);

        compound.setTag(TAG_WEARABLE_COMPOUND, copterTag);
    }

    @Override
    public boolean updateTankSlots()
    {
        boolean changesMade = false;
        while (InventoryActions.transferContainerTank(this, getFuelTank(), BUCKET_IN))
            changesMade = true;
        return changesMade;
    }

    @Override
    public void dirtyTanks()
    {
        getWearableCompound().setTag(TAG_FUEL_TANK, fuelTank.writeToNBT(new NBTTagCompound()));
    }

    public void dirtyStatus()
    {
        getWearableCompound().setByte(TAG_STATUS, status);
    }

    public boolean canConsumeFuel(int quantity)
    {
        FluidStack drain = fuelTank.drain(quantity, false);
        return drain != null && drain.amount > 0;
    }

    public void consumeFuel(int quantity)
    {
        fuelTank.drain(quantity, true);
        dirtyTanks();
    }

    public byte getStatus()
    {
        return status;
    }

    public void setStatus(byte status)
    {
        this.status = status;
    }

    public int getTickCounter()
    {
        return tickCounter;
    }

    public void setTickCounter(int ticks)
    {
        this.tickCounter = ticks;
    }
}
