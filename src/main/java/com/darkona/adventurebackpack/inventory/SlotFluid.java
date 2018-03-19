package com.darkona.adventurebackpack.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.util.Utils;

public class SlotFluid extends Slot
{
    SlotFluid(IInventory inventory, int slotIndex, int posX, int posY)
    {
        super(inventory, slotIndex, posX, posY);
    }

    static boolean isContainer(ItemStack stack)
    {
        return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    }


    //TODO fluid container utils
    static boolean isEmpty(ItemStack stack)
    {
        return false;
        //return FluidContainerRegistry.isEmptyContainer(stack);
    }

    static boolean isEmpty(FluidTank tank)
    {
        return tank.getFluidAmount() == 0;
    }

    static boolean isFilled(ItemStack stack)
    {
        return false;
        //return FluidContainerRegistry.isFilledContainer(stack);
    }

    static boolean isBucket(ItemStack stack)
    {
        return false;
        //return FluidContainerRegistry.isBucket(stack);
    }

    static boolean isEmptyBucket(ItemStack stack)
    {
        return false;
        //return FluidContainerRegistry.isBucket(stack) && isEmpty(stack);
    }

    static boolean isFilledBucket(ItemStack stack)
    {
        return false;
        //return FluidContainerRegistry.isBucket(stack) && isFilled(stack);
    }

    static boolean isEqualFluid(ItemStack container, FluidTank tank)
    {
        return getFluidID(container) == getFluidID(tank);
    }

    static String getFluidName(ItemStack stack)
    {
        if (stack == null || isEmpty(stack))
            return "";
        return "name";
        //return FluidContainerRegistry.getFluidForFilledItem(stack).getFluid().getName();
    }

    static String getFluidName(FluidTank tank)
    {
        if (tank == null || tank.getFluidAmount() <= 0)
            return "";
        return tank.getFluid().getFluid().getName();
    }

    static int getFluidID(ItemStack stack)
    {
        if (stack == null || isEmpty(stack))
            return -1;
        return -1;
        //return FluidContainerRegistry.getFluidForFilledItem(stack).getFluid().getID();
    }

    static int getFluidID(FluidTank tank)
    {
        if (tank == null || tank.getFluidAmount() <= 0)
            return -1;
        return -1;
        //return tank.getFluid().getFluid().getID();
    }

    static Fluid getFluid(ItemStack stack)
    {
        if (stack == null || isEmpty(stack))
            return null;
        return null;
        //return FluidContainerRegistry.getFluidForFilledItem(stack).getFluid();
    }

    static int getCapacity(ItemStack stack)
    {
        return -1;
        //return FluidContainerRegistry.getContainerCapacity(stack);
    }

    static boolean canFitToTank(ItemStack container, FluidTank tank)
    {
        return tank.getFluidAmount() + getCapacity(container) <= tank.getCapacity();
    }

    static boolean isEqualAndCanFit(ItemStack container, FluidTank tank)
    {
        return isEqualFluid(container, tank) && canFitToTank(container, tank);
    }

    static ItemStack getEmptyContainer(ItemStack container)
    {
        return ItemStack.EMPTY;
        //return FluidContainerRegistry.drainFluidContainer(container);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack != null && (isContainer(stack) || stack.getItem() instanceof ItemHose);
    }

    @Override
    public void onSlotChanged()
    {
        if (Utils.inServer())
        {
            if (inventory instanceof IInventoryTanks)
            {
                ((IInventoryTanks) this.inventory).updateTankSlots();
            }
        }
        super.onSlotChanged();
    }

    @Override
    public int getSlotStackLimit()
    {
        return Constants.BASIC_TANK_CAPACITY / Constants.BUCKET;
    }
}
