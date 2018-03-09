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
public class ItemAdventurePants extends ArmorAB
{
    public ItemAdventurePants()
    {
        super(2, EntityEquipmentSlot.LEGS);
        setMaxDamage(Items.LEATHER_LEGGINGS.getMaxDamage() + 75);
        setUnlocalizedName("adventurePants");
        this.setRegistryName(ModInfo.MODID, "adventure_pants");
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
