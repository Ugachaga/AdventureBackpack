package com.darkona.adventurebackpack.fluids.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import com.darkona.adventurebackpack.util.Utils;
import adventurebackpack.api.FluidEffect;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */

public class FuelEffect extends FluidEffect
{
    public FuelEffect()
    {
        super(FluidRegistry.getFluid("fuel"), 20);
    }

    @Override
    public void affectDrinker(World world, Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, this.timeInTicks, 2));
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, this.timeInTicks, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, Utils.secondsToTicks(8), 0));
        }
    }
}
