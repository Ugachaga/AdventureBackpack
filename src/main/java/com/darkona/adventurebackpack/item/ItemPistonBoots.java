package com.darkona.adventurebackpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.config.ConfigHandler;

public class ItemPistonBoots extends AdventureArmor
{
    public ItemPistonBoots(String name)
    {
        super(name, 2, EntityEquipmentSlot.FEET);

        setMaxDamage(Items.IRON_BOOTS.getMaxDamage() + 55);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if (ConfigHandler.pistonBootsAutoStep)
            player.stepHeight = 1.001F;
        if (ConfigHandler.pistonBootsSprintBoost != 0 && player.isSprinting())
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, ConfigHandler.pistonBootsSprintBoost - 1));
    }
}
