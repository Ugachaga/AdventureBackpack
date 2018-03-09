package com.darkona.adventurebackpack.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 11/01/2015
 *
 * @author Darkona
 */
public class FluidMushroomStew extends Fluid
{
    public FluidMushroomStew()
    {
        super("mushroomStew", new ResourceLocation(ModInfo.MODID, "fluid.melonJuiceStill.png"), new ResourceLocation(ModInfo.MODID, "fluid.melonJuiceFlowing.png"));
        setDensity(1200);
        setViscosity(1200);
    }

    //TODO fluid rendering
//    @Override
//    public IIcon getStillIcon()
//    {
//        return Icons.mushRoomStewStill;
//    }
//
//    @Override
//    public IIcon getIcon()
//    {
//        return Icons.mushRoomStewStill;
//    }
//
//    @Override
//    public IIcon getFlowingIcon()
//    {
//        return Icons.mushRoomStewFlowing;
//    }

    @Override
    public int getColor(FluidStack stack)
    {
        //Color color1 = new Color(205,140,111);
        return 0xcd8c6f;
    }
}
