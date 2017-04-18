package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

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
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
