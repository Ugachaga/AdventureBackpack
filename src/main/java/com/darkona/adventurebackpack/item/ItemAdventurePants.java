package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemAdventurePants extends AdventureArmor
{
    public ItemAdventurePants(String name)
    {
        super(name, 2, EntityEquipmentSlot.LEGS);

        setMaxDamage(Items.LEATHER_LEGGINGS.getMaxDamage() + 75);
    }
}
