package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 11/10/2014
 *
 * @author Darkona
 */
public class ItemAdventureSuit extends ArmorAB
{
    public ItemAdventureSuit()
    {
        super(1, EntityEquipmentSlot.CHEST);
        setMaxDamage(Items.LEATHER_CHESTPLATE.getMaxDamage() + 70);
        setUnlocalizedName("adventureSuit");
        this.setRegistryName(ModInfo.MODID, "adventure_suit");
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
