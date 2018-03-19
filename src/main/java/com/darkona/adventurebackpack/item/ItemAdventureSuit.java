package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemAdventureSuit extends AdventureArmor
{
    public ItemAdventureSuit(String name)
    {
        super(name,1, EntityEquipmentSlot.CHEST);

        setMaxDamage(Items.LEATHER_CHESTPLATE.getMaxDamage() + 70);
    }
}
