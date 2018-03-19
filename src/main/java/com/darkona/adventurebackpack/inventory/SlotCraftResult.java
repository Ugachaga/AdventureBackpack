package com.darkona.adventurebackpack.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotCraftResult extends SlotCrafting
{
    private ContainerBackpack eventHandler;

    public SlotCraftResult(ContainerBackpack container, EntityPlayer player, InventoryCrafting craftMatrix, IInventory inventory, int slotIndex, int posX, int posY)
    {
        super(player, craftMatrix, inventory, slotIndex, posX, posY);
        this.eventHandler = container;
    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack)
    {
        eventHandler.syncCraftMatrixWithInventory(true); // pre craft sync
        ItemStack result = super.onTake(player, stack);
        eventHandler.syncCraftMatrixWithInventory(false); // post craft sync
        return result;
    }
}
