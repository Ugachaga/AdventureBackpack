package com.darkona.adventurebackpack.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 11/10/2014
 *
 * @author Darkona
 */
public class ModMaterials
{
    public static Item.ToolMaterial ruggedIron = EnumHelper.addToolMaterial("RUGGED_IRON", 2, 350, 6.5F, 5.2F, 10);
    public static ItemArmor.ArmorMaterial ruggedLeather = EnumHelper.addArmorMaterial("RUGGED_LEATHER",  ModInfo.MOD_ID + ":adventureSuit", 15, new int[] { 2, 5, 4, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0f);
}
