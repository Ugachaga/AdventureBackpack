package com.darkona.adventurebackpack.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created by Darkona on 10/10/2014.
 */
public class ItemAB extends Item
{
    ItemAB()
    {
        super();
        setCreativeTab(CreativeTabAB.TAB_AB);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return String.format("item.%s%s", ModInfo.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", ModInfo.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public String getUnlocalizedName(String name)
    {
        return String.format("item.%s%s", ModInfo.MOD_ID + ":", name);
    }


    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    public void registerItemModel()
    {
        AdventureBackpack.proxy.registerItemRenderer(this, 0, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
}
