package com.darkona.adventurebackpack.common;

import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Created on 09/01/2015
 *
 * @author Darkona
 */
public class BackpackRemovals
{
    public void itemBat(EntityPlayer player, World world, ItemStack backpack)
    {
        PotionEffect potion = null;
        if (player.isPotionActive(Potion.getPotionById(PotionType.getID(PotionTypes.NIGHT_VISION))))
        {
            potion = player.getActivePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.NIGHT_VISION)));

            if (potion != null && potion.getAmplifier() == -1)
            {
                player.removePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.NIGHT_VISION)));
            }
        }
    }

    public void itemSquid(EntityPlayer player, World world, ItemStack backpack)
    {
        itemBat(player, world, backpack);
        PotionEffect potion = null;
        if (player.isPotionActive(Potion.getPotionById(PotionType.getID(PotionTypes.WATER_BREATHING))))
        {
            potion = player.getActivePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.WATER_BREATHING)));

            if (potion != null && potion.getAmplifier() == -1)
            {
                player.removePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.WATER_BREATHING)));
            }
        }
    }

    public void itemPigman(EntityPlayer player, World world, ItemStack backpack)
    {
        PotionEffect potion = null;
        if (player.isPotionActive(Potion.getPotionById(PotionType.getID(PotionTypes.FIRE_RESISTANCE))))
        {
            potion = player.getActivePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.FIRE_RESISTANCE)));

            if (potion != null && potion.getAmplifier() == -1)
            {
                player.removePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.FIRE_RESISTANCE)));
            }
        }
    }

    public void itemDragon(EntityPlayer player, World world, ItemStack backpack)
    {
        itemBat(player, world, backpack);
        itemPigman(player, world, backpack);
        PotionEffect potion = null;
        if (player.isPotionActive(Potion.getPotionById(PotionType.getID(PotionTypes.STRENGTH))))
        {
            potion = player.getActivePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.STRENGTH)));
            if (potion != null && potion.getAmplifier() == ConfigHandler.dragonBackpackDamage - 1)
            {
                player.removePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.STRENGTH)));
            }
        }
        potion = null;
        if (player.isPotionActive(Potion.getPotionById(PotionType.getID(PotionTypes.REGENERATION))))
        {
            potion = player.getActivePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.REGENERATION)));

            if (potion != null && potion.getAmplifier() == ConfigHandler.dragonBackpackRegen - 1)
            {
                player.removePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.REGENERATION)));
            }
        }
    }

    public void itemRainbow(EntityPlayer player, World world, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        if (inv.getLastTime() > 0) return;
        inv.setLastTime(0);
        inv.dirtyTime();
        PotionEffect potion = null;
        if (player.isPotionActive(Potion.getPotionById(PotionType.getID(PotionTypes.SWIFTNESS))))
        {
            potion = player.getActivePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.SWIFTNESS)));
            if (potion != null && potion.getAmplifier() == ConfigHandler.rainbowBackpackSpeed - 1)
            {
                player.removePotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.SWIFTNESS)));
            }
        }
        /*potion = null;
        if (player.isPotionActive(Potion.jump.id)) {
            potion = player.getActivePotionEffect(Potion.jump);
            if (potion != null && potion.getAmplifier() == 1)
            {
                player.removePotionEffect(Potion.jump.id);
            }
        }*/
    }
}
