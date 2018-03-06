package com.darkona.adventurebackpack.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.init.ModMaterials;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 11/10/2014.
 *
 * @author Javier Darkona
 */
public class ArmorAB extends ItemArmor
{
    ArmorAB(int renderIndex, EntityEquipmentSlot slot)
    {
        super(ModMaterials.ruggedLeather, renderIndex, slot);
        setCreativeTab(CreativeTabAB.TAB_AB);
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return getUnlocalizedName();
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", ModInfo.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public void registerItemModel()
    {
        AdventureBackpack.proxy.registerItemRenderer(this, 0, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public void setCustomModelResourceLocation()
    {
        AdventureBackpack.proxy.setCustomModelResourceLocation(this, 0, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

}
