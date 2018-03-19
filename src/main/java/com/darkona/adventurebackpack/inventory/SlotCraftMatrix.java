package com.darkona.adventurebackpack.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCraftMatrix extends Slot
{
    SlotCraftMatrix(IInventory craftMatrix, int slotIndex, int posX, int posY)
    {
        super(craftMatrix, slotIndex, posX, posY);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        return false;
    }
}