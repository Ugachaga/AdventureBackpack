package com.darkona.adventurebackpack.fluids.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.init.ModFluids;
import adventurebackpack.api.FluidEffect;

public class MushroomStewEffect extends FluidEffect
{
    public MushroomStewEffect()
    {
        super(ModFluids.MUSHROOM_STEW);
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
