package com.darkona.adventurebackpack.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 09/01/2015
 *
 * @author Darkona
 */
public class BackpackRemovals
{
    public void itemBat(EntityPlayer player, World world, ItemStack backpack)
    {
        PotionEffect potion;
        if (player.isPotionActive(MobEffects.NIGHT_VISION))
        {
            potion = player.getActivePotionEffect(MobEffects.NIGHT_VISION);

            if (potion != null && potion.getAmplifier() == -1)
            {
                player.removePotionEffect(MobEffects.NIGHT_VISION);
            }
        }
    }

    public void itemSquid(EntityPlayer player, World world, ItemStack backpack)
    {
        itemBat(player, world, backpack);
        PotionEffect potion;
        if (player.isPotionActive(MobEffects.WATER_BREATHING))
        {
            potion = player.getActivePotionEffect(MobEffects.WATER_BREATHING);

            if (potion != null && potion.getAmplifier() == -1)
                player.removePotionEffect(MobEffects.WATER_BREATHING);
        }
    }

    public void itemPigman(EntityPlayer player, World world, ItemStack backpack)
    {
        PotionEffect potion;
        if (player.isPotionActive(MobEffects.FIRE_RESISTANCE))
        {
            potion = player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE);

            if (potion != null && potion.getAmplifier() == -1)
                player.removePotionEffect(MobEffects.FIRE_RESISTANCE);
        }
    }

    public void itemDragon(EntityPlayer player, World world, ItemStack backpack)
    {
        itemBat(player, world, backpack);
        itemPigman(player, world, backpack);
        PotionEffect potion;
        if (player.isPotionActive(MobEffects.STRENGTH))
        {
            potion = player.getActivePotionEffect(MobEffects.STRENGTH);
            if (potion != null && potion.getAmplifier() == ConfigHandler.dragonBackpackDamage - 1)
                player.removePotionEffect(MobEffects.STRENGTH);
        }
        potion = null;
        if (player.isPotionActive(MobEffects.REGENERATION))
        {
            potion = player.getActivePotionEffect(MobEffects.REGENERATION);

            if (potion != null && potion.getAmplifier() == ConfigHandler.dragonBackpackRegen - 1)
                player.removePotionEffect(MobEffects.REGENERATION);
        }
    }

    public void itemRainbow(EntityPlayer player, World world, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        if (inv.getLastTime() > 0) return;
        inv.setLastTime(0);
        inv.dirtyTime();
        PotionEffect potion;
        if (player.isPotionActive(MobEffects.SPEED))
        {
            potion = player.getActivePotionEffect(MobEffects.SPEED);
            if (potion != null && potion.getAmplifier() == ConfigHandler.rainbowBackpackSpeed - 1)
                player.removePotionEffect(MobEffects.SPEED);
        }
    }
}
