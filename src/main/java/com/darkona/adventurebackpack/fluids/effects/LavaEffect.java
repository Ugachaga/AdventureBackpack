package com.darkona.adventurebackpack.fluids.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import adventurebackpack.api.FluidEffect;

public class LavaEffect extends FluidEffect
{
    public LavaEffect()
    {
        super(FluidRegistry.LAVA, 15);
    }

    @Override
    public void affectDrinker(World world, Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            player.setFire(timeInSeconds);
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, timeInSeconds * 20 * 6, 2, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, timeInSeconds * 20 * 6, 0, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, timeInSeconds * 20 * 6, 3, false, false));
        }
    }
}
