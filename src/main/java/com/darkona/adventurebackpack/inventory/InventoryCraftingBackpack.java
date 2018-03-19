package com.darkona.adventurebackpack.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingBackpack extends InventoryCrafting
{
    public InventoryCraftingBackpack(Container eventHandler, int columns, int rows)
    {
        super(eventHandler, columns, rows);
    }

    public void setInventorySlotContentsNoUpdate(int slotID, ItemStack stack)
    {
        stackList.set(slotID, stack);
    }
}