package com.darkona.adventurebackpack.inventory;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IAsynchronousInventory extends IInventory
{
    void setInventorySlotContentsNoSave(int slot, @Nullable ItemStack stack);

    ItemStack decrStackSizeNoSave(int slot, int amount);
}
