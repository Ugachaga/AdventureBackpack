package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

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
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
