package com.darkona.adventurebackpack.fluids.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import adventurebackpack.api.FluidEffect;

public class MushroomStewEffect extends FluidEffect
{
    public MushroomStewEffect()
    {
        super(FluidRegistry.getFluid("mushroomstew"));
    }

    @Override
    public void affectDrinker(World world, Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            ((EntityPlayer) entity).getFoodStats().addStats(6, 0.6F);
        }
    }
}
