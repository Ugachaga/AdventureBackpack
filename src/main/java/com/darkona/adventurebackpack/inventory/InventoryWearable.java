package com.darkona.adventurebackpack.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import com.darkona.adventurebackpack.util.BackpackUtils;

import static com.darkona.adventurebackpack.common.Constants.TAG_INVENTORY;
import static com.darkona.adventurebackpack.common.Constants.TAG_SLOT;

@SuppressWarnings("WeakerAccess")
abstract class InventoryWearable implements IInventoryTanks
{
    // when porting to java 8+ most this methods should move to IInventoryTanks

    protected final ItemStack containerStack;
    protected final ItemStack[] inventory;

    @Override
    public String getName()
    {
        return "";
    }

    @Override
    public boolean hasCustomName()
    {
        return getName().isEmpty();
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentString(this.getName());
    }

    protected InventoryWearable(ItemStack container, int inventorySize)
    {
        this.containerStack = container;
        this.inventory = new ItemStack[inventorySize];
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot] == null ? ItemStack.EMPTY : inventory[slot]; //TODO why null here is ever possible?
    }

    @Override
    public ItemStack decrStackSize(int slot, int quantity)
    {
        ItemStack stack = getStackInSlot(slot);
        if (!stack.isEmpty())
        {
            if (stack.getCount() <= quantity)
                setInventorySlotContents(slot, ItemStack.EMPTY);
            else
                stack = stack.splitStack(quantity);
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) //TODO wrong behavior
    {
        for (int s : getSlotsOnClosing())
            if (slot == s)
                return inventory[slot];

        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        setInventorySlotContentsNoSave(slot, stack);
        dirtyInventory();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
        saveToNBT(containerStack.getTagCompound());
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

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return false; //TODO
    }

    @Override
    public int getField(int id)
    {
        return 0; //TODO
    }

    @Override
    public void setField(int id, int value)
    {
        //TODO
    }

    @Override
    public int getFieldCount()
    {
        return 0; //TODO
    }

    @Override
    public void clear()
    {
        //TODO
    }

    @Override
    public ItemStack decrStackSizeNoSave(int slot, int quantity)
    {
        ItemStack stack = getStackInSlot(slot);
        if (!stack.isEmpty())
        {
            if (stack.getCount() <= quantity)
                setInventorySlotContentsNoSave(slot, ItemStack.EMPTY);
            else
                stack = stack.splitStack(quantity);
        }
        return stack;
    }

    @Override
    public void setInventorySlotContentsNoSave(int slot, ItemStack stack)
    {
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit())
            stack.setCount(getInventoryStackLimit());

        inventory[slot] = stack;
    }

    @Override
    public void dirtyInventory()
    {
        if (updateTankSlots()) //TODO this can be generalized too
            dirtyTanks();      //TODO and also this

        getWearableCompound().setTag(TAG_INVENTORY, getInventoryTagList());
    }

    protected NBTTagCompound getWearableCompound()
    {
        return BackpackUtils.getWearableCompound(containerStack);
    }

    protected void setInventoryFromTagList(NBTTagList items)
    {
        for (int i = 0; i < items.tagCount(); i++)
        {
            NBTTagCompound item = items.getCompoundTagAt(i);
            byte slot = item.getByte(TAG_SLOT);
            if (slot >= 0 && slot < getSizeInventory())
            {
                inventory[slot] = new ItemStack(item);
            }
        }
    }

    protected NBTTagList getInventoryTagList()
    {
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = inventory[i];
            if (stack != null && !stack.isEmpty()) //TODO where does null come from?
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte(TAG_SLOT, (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }
        return items;
    }
}
