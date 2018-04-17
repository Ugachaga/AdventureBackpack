package com.darkona.adventurebackpack.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.Resources;

public class AdventureArmor extends ItemArmor
{
    private final String texture;

    AdventureArmor(String name, int renderIndex, EntityEquipmentSlot slot)
    {
        super(ModItems.Materials.ARMOR_RUGGED_LEATHER, renderIndex, slot);
        AdventureItem.setItemName(this, name);

        this.texture = Resources.getModelTexture(name).toString();
        this.setCreativeTab(ModInfo.CREATIVE_TAB);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.isItemEqual(new ItemStack(Items.LEATHER));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return texture;
    }
}
