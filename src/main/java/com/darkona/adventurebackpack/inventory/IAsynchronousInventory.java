package com.darkona.adventurebackpack.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IAsynchronousInventory extends IInventory
{
    void setInventorySlotContentsNoSave(int slot, ItemStack stack);

    ItemStack decrStackSizeNoSave(int slot, int amount);
}
