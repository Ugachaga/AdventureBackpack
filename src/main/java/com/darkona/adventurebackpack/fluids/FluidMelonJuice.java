package com.darkona.adventurebackpack.fluids;

import com.darkona.adventurebackpack.client.Icons;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Darkona on 12/10/2014.
 */
public class FluidMelonJuice extends Fluid
{

    public FluidMelonJuice()
    {
        super("melonJuice", new ResourceLocation("modid", "fluid.mushroomStewStill.png"), new ResourceLocation("modid", "fluid.mushroomStewFlowing.png"));
        setUnlocalizedName("melonJuice");
    }


    /**
     * TODO: rendering

    @Override
    public IIcon getStillIcon()
    {
        return Icons.melonJuiceStill;
    }

    @Override
    public IIcon getIcon()
    {
        return Icons.melonJuiceStill;
    }

    @Override
    public IIcon getFlowingIcon()
    {
        return Icons.melonJuiceFlowing;
    }
    **/

    @Override
    public int getColor(FluidStack stack)
    {
        return 0xc31d08;
    }

    @Override
    public boolean isGaseous(World world, BlockPos pos)
    {
        return false;
    }
}
