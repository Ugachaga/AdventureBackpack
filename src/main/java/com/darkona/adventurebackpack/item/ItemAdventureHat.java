package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Created by Darkona on 11/10/2014.
 */
public class ItemAdventureHat extends ArmorAB
{

    public ItemAdventureHat()
    {
        super(2, EntityEquipmentSlot.HEAD);
        setMaxDamage(Items.LEATHER_HELMET.getMaxDamage() + 45);
        setUnlocalizedName("adventureHat");
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER));
    }

}
