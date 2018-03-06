package com.darkona.adventurebackpack.init;

import com.darkona.adventurebackpack.fluids.FluidMelonJuice;
import com.darkona.adventurebackpack.fluids.FluidMilk;
import com.darkona.adventurebackpack.fluids.FluidMushroomStew;

/**
 * Created on 12/10/2014.
 *
 * @author Javier Darkona
 */
public class ModFluids
{
    public static FluidMilk milk;
    public static FluidMelonJuice melonJuice;
    public static FluidMushroomStew mushroomStew;

    public static void init()
    {
        milk = new FluidMilk();
        melonJuice = new FluidMelonJuice();
        mushroomStew = new FluidMushroomStew();

        //TODO FluidContainerRegistry removed. Create an item like {@link net.minecraftforge.fluids.capability.ItemFluidContainer}
//        FluidRegistry.registerFluid(milk);
//        FluidContainerRegistry.registerFluidContainer(milk, new ItemStack(Items.MILK_BUCKET), FluidContainerRegistry.EMPTY_BUCKET);
//
//        FluidRegistry.registerFluid(melonJuice);
//        FluidContainerRegistry.registerFluidContainer(melonJuice, new ItemStack(ModItems.MELON_JUICE_BOTTLE), FluidContainerRegistry.EMPTY_BOTTLE);
//
//        FluidRegistry.registerFluid(mushroomStew);
//        FluidContainerRegistry.registerFluidContainer(mushroomStew, new ItemStack(Items.MUSHROOM_STEW), new ItemStack(Items.BOWL));
    }
}
