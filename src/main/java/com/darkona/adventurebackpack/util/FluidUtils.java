package com.darkona.adventurebackpack.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class FluidUtils
{
    public static boolean isContainerForFluid(ItemStack container, Fluid fluid)
    {
        //TODO see net.minecraftforge.fluids.capability.ItemFluidContainer

//        if (container != null && fluid != null)
//        {
//            for (FluidContainerRegistry.FluidContainerData data : ItemFluidContainer FluidContainerRegistry.getRegisteredFluidContainerData())
//            {
//                if (data.fluid != null && data.fluid.getFluid() != null
//                        && data.fluid.getFluid().getID() == fluid.getID()
//                        && (data.emptyContainer.getItem().equals(container.getItem())
//                        || data.filledContainer.getItem().equals(container.getItem())))
//                {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    public static boolean isEmptyContainerForFluid(ItemStack container, Fluid fluid)
    {
        //TODO see net.minecraftforge.fluids.capability.ItemFluidContainer

//        if (container != null && fluid != null)
//        {
//            for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData())
//            {
//                if (data.fluid != null && data.fluid.getFluid() != null
//                        && data.fluid.getFluid().getID() == fluid.getID()
//                        && data.emptyContainer.getItem().equals(container.getItem()))
//                {
//                    return true;
//                }
//            }
//        }
        return false;
    }
}
