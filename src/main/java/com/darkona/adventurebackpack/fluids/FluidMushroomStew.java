package com.darkona.adventurebackpack.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created on 11/01/2015
 *
 * @author Darkona
 */
public class FluidMushroomStew extends Fluid
{
    public FluidMushroomStew()
    {
        super("mushroomStew", new ResourceLocation("modid", "fluid.melonJuiceStill.png"), new ResourceLocation("modid", "fluid.melonJuiceFlowing.png"));
        setDensity(1200);
        setViscosity(1200);
        setLuminosity(0);
    }

    /**
     * TODO: rendering
    @Override
    public IIcon getStillIcon()
    {
        return Icons.mushRoomStewStill;
    }

    @Override
    public IIcon getIcon()
    {
        return Icons.mushRoomStewStill;
    }

    @Override
    public IIcon getFlowingIcon()
    {
        return Icons.mushRoomStewFlowing;
    }
    **/

    @Override
    public int getColor(FluidStack stack)
    {
        //Color color1 = new Color(205,140,111);
        return 0xcd8c6f;
    }

    @Override
    public boolean isGaseous(World world, BlockPos pos)
    {
        return false;
    }
}
