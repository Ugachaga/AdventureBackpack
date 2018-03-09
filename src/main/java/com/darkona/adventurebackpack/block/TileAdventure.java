package com.darkona.adventurebackpack.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import com.darkona.adventurebackpack.inventory.IInventoryTanks;

import static com.darkona.adventurebackpack.common.Constants.TAG_SLOT;

/**
 * Created on 26.02.2018
 *
 * @author Ugachaga
 */
@SuppressWarnings("WeakerAccess")
abstract class TileAdventure extends TileEntity implements IInventoryTanks
{
    // when porting to java 8+ most this methods should move to IInventoryTanks

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


    protected final ItemStack[] inventory;

    protected TileAdventure(int inventorySize)
    {
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
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int quantity)
    {
        ItemStack stack = getStackInSlot(slot);
        if (stack != ItemStack.EMPTY)
        {
            if (stack.getCount() <= quantity)
            {
                setInventorySlotContents(slot, ItemStack.EMPTY);
            }
            else
            {
                stack = stack.splitStack(quantity);
                dirtyInventory();
            }
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot)
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

    // we have to inherit markDirty() implemented in TileEntity.class

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return world.getTileEntity(pos) == this
                && player.getDistanceSq(new BlockPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        //
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        dirtyInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return false; // override when automation is allowed
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
        if (stack != ItemStack.EMPTY)
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
        if (slot >= getSizeInventory())
            return;

        if (stack != ItemStack.EMPTY)
        {
            if (stack.getCount() > getInventoryStackLimit())
                stack.setCount(getInventoryStackLimit());

            if (stack.getCount() == 0)
                stack = ItemStack.EMPTY;
        }

        inventory[slot] = stack;
    }

    @Override
    public void dirtyInventory()
    {
        updateTankSlots();
        markDirty();
    }

    @Deprecated
    @Override
    public void dirtyTanks()
    {
        // if we really want to use it, we have to re-implement it, more efficient way
        dirtyInventory();
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
            if (stack != null && stack != ItemStack.EMPTY)
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
