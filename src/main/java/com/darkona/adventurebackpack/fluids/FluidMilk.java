package com.darkona.adventurebackpack.fluids;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 12/10/2014.
 *
 * @author Javier Darkona
 */
public class FluidMilk extends Fluid
{
    public FluidMilk()
    {
        super("milk", new ResourceLocation(ModInfo.MOD_ID, "fluid.milkStill.png"), new ResourceLocation(ModInfo.MOD_ID, "fluid.milk.png"));
        setDensity(1200);
        setViscosity(1200);
    }

    //TODO fluid rendering
//    @Override
//    public IIcon getStillIcon()
//    {
//        return Icons.milkStill;
//    }
//
//    @Override
//    public IIcon getIcon()
//    {
//        return Icons.milkStill;
//    }
//
//    @Override
//    public IIcon getFlowingIcon()
//    {
//        return Icons.milkStill;
//    }

    @Override
    public int getColor(FluidStack stack)
    {
        return 0xffffff;
    }

}