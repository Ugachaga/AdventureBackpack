package com.darkona.adventurebackpack.init;

import com.darkona.adventurebackpack.fluids.effects.FuelEffect;
import com.darkona.adventurebackpack.fluids.effects.OilEffect;
import com.darkona.adventurebackpack.reference.LoadedMods;
import adventurebackpack.api.FluidEffect;

public class ConditionalFluidEffect
{
    public static FluidEffect oilEffect;
    public static FluidEffect fuelEffect;

    public static void init()
    {
        if (LoadedMods.BUILDCRAFT)
        {
            oilEffect = new OilEffect();
            fuelEffect = new FuelEffect();
        }
    }
}
