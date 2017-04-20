package com.darkona.adventurebackpack.fluids;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created on 12/10/2014.
 * @author Javier Darkona
 */
public class FluidMilk extends Fluid
{

    public FluidMilk()
    {
        super("milk", new ResourceLocation("modid", "fluid.milkStill.png"), new ResourceLocation("modid", "fluid.milk.png"));
        setDensity(1200);
        setViscosity(1200);
        setLuminosity(0);
    }

    /**
     * TODO: rendering
    @Override
    public IIcon getStillIcon()
    {
        return Icons.milkStill;
    }

    @Override
    public IIcon getIcon()
    {
        return Icons.milkStill;
    }

    @Override
    public IIcon getFlowingIcon()
    {
        return Icons.milkStill;
    }
    */

    @Override
    public int getColor(FluidStack stack)
    {
        return 0xffffff;
    }

    @Override
    public boolean isGaseous(World world, BlockPos pos)
    {
        return false;
    }


}