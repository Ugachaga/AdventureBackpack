package com.darkona.adventurebackpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created by Darkona on 11/10/2014.
 */
public class ItemPistonBoots extends ArmorAB
{
    public ItemPistonBoots()
    {
        super(2, EntityEquipmentSlot.FEET);
        setMaxDamage(Items.IRON_BOOTS.getMaxDamage() + 55);
        setUnlocalizedName("pistonBoots");
        this.setRegistryName(ModInfo.MODID, "piston_boots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if (ConfigHandler.pistonBootsAutoStep)
            player.stepHeight = 1.001F;
        if (ConfigHandler.pistonBootsSprintBoost != 0 && player.isSprinting())
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, ConfigHandler.pistonBootsSprintBoost - 1));
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
