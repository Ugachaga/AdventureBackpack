package com.darkona.adventurebackpack.inventory;

import com.darkona.adventurebackpack.item.ItemCopterPack;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.util.FluidUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created on 02/01/2015
 *
 * @author Darkona
 */
public class InventoryCopterPack implements IInventoryTanks
{
    private ItemStack containerStack;
    public FluidTank fuelTank = new FluidTank(6000);
    public int tickCounter = 0;
    public byte status = ItemCopterPack.OFF_MODE;
    private ItemStack[] inventory = new ItemStack[2];

    public InventoryCopterPack(ItemStack copterPack)
    {
        status = ItemCopterPack.OFF_MODE;
        containerStack = copterPack;
        if (!copterPack.hasTagCompound())
        {
            copterPack.setTagCompound(new NBTTagCompound());
            saveToNBT(copterPack.getTagCompound());
        }
        //openInventory();
    }

    public FluidTank getFuelTank()
    {
        return fuelTank;
    }

    public void consumeFuel(int quantity)
    {
        fuelTank.drain(quantity, true);
        dirtyTanks();
    }

    public boolean canConsumeFuel(int quantity)
    {
        return fuelTank.drain(quantity, false) != null && fuelTank.drain(quantity, false).amount > 0;
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int slot, int quantity)
    {
        ItemStack itemstack = getStackInSlot(slot);

        if (itemstack != null)
        {
            if (itemstack.stackSize <= quantity)
            {
                setInventorySlotContents(slot, null);
            } else
            {
                itemstack = itemstack.splitStack(quantity);
            }
        }
        return itemstack;
    }

    //@Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }
        if (FluidContainerRegistry.isFilledContainer(stack) && GeneralReference.isValidFuel(FluidContainerRegistry.getFluidForFilledItem(stack).getFluid()))
        {
            InventoryActions.transferContainerTank(this, fuelTank, 0);
        } else if (FluidContainerRegistry.isEmptyContainer(stack) && fuelTank.getFluid() != null && FluidUtils.isContainerForFluid(stack, fuelTank.getFluid().getFluid()))
        {
            InventoryActions.transferContainerTank(this, fuelTank, 0);
        }
        dirtyTanks();
        dirtyInventory();
    }

    @Override
    public String getName()
    {
        return "Copter Pack";
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentString(getName());
    }

    @Override
    public boolean hasCustomName()
    {
        return true;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
        containerStack.getTagCompound().setTag("fuelTank", fuelTank.writeToNBT(new NBTTagCompound()));
        containerStack.getTagCompound().setByte("status", status);
        containerStack.getTagCompound().setInteger("tickCounter", this.tickCounter);
    }

    @Override
    public void dirtyTanks()
    {
        containerStack.getTagCompound().setTag("fuelTank", fuelTank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void dirtyInventory()
    {

    }

    @Override
    public int getFieldCount()
    {
        return inventory.length;
    }

    @Override
    public void setField(int id, int value)
    {
        //TODO: implement this
    }

    @Override
    public int getField(int id)
    {
        //TODO: implement this
        return 0;
    }

    public void dirtyCounter()
    {
        containerStack.getTagCompound().setInteger("tickCounter", this.tickCounter);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        loadFromNBT(containerStack.getTagCompound());
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        saveToNBT(containerStack.getTagCompound());
    }

    public void closeInventoryNoStatus()
    {
        containerStack.getTagCompound().setTag("fuelTank", this.fuelTank.writeToNBT(new NBTTagCompound()));
        containerStack.getTagCompound().setInteger("tickCounter", this.tickCounter);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack)
    {
        return false;
    }

    @Override
    public void clear()
    {
        //TODO: implement clear
    }

    public void onInventoryChanged()
    {

        @SuppressWarnings("unused")
        ItemStack container = getStackInSlot(0);

        // /closeInventory();
    }

    @Override
    public void setInventorySlotContentsNoSave(int slot, ItemStack stack)
    {
        if (slot > inventory.length) return;
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack i = inventory[index];
        inventory[index] = null;
        return i;
    }

    @Override
    public ItemStack decrStackSizeNoSave(int slot, int amount)
    {
        if (slot < inventory.length && inventory[slot] != null)
        {
            if (inventory[slot].stackSize > amount)
            {
                ItemStack result = inventory[slot].splitStack(amount);
                return result;
            }
            ItemStack stack = inventory[slot];
            setInventorySlotContentsNoSave(slot, null);
            return stack;
        }
        return null;
    }

    public ItemStack getParentItemStack()
    {
        return this.containerStack;
    }

    public int getTickCounter()
    {
        return tickCounter;
    }

    public void setTickCounter(int ticks)
    {
        this.tickCounter = ticks;
    }

    public byte getStatus()
    {
        return status;
    }

    @Override
    public boolean updateTankSlots()
    {
        return false;
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound)
    {
        fuelTank.readFromNBT(compound.getCompoundTag("fuelTank"));
        status = compound.getByte("status");
        tickCounter = compound.getInteger("tickCounter");
    }

    @Override
    public void saveToNBT(NBTTagCompound compound)
    {
        compound.setTag("fuelTank", fuelTank.writeToNBT(new NBTTagCompound()));
        compound.setByte("status", status);
        compound.setInteger("tickCounter", this.tickCounter);
    }

    @Override
    public FluidTank[] getTanksArray()
    {
        FluidTank[] tanks = { fuelTank };
        return tanks;
    }

    public void setStatus(byte status)
    {
        this.status = status;
    }

    public void dirtyStatus()
    {
        containerStack.getTagCompound().setByte("status", status);
    }

    public void setContainerStack(ItemStack containerStack)
    {
        this.containerStack = containerStack;
    }
}
