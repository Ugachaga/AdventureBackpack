package com.darkona.adventurebackpack.item;

import java.util.Set;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.reference.ModInfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/**
 * Created by Darkona on 11/10/2014.
 */
public class ToolAB extends ItemTool
{

    public ToolAB(ToolMaterial material, @SuppressWarnings("rawtypes") Set breakableBlocks)
    {
        super(material, breakableBlocks);
        //setCreativeTab(CreativeTabAB.ADVENTURE_BACKPACK_CREATIVE_TAB);
    }

    @Override
    public Item setCreativeTab(CreativeTabs tab)
    {
        return super.setCreativeTab(tab);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return String.format("item.%s%s", ModInfo.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", ModInfo.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    public void registerItemModel() {
		AdventureBackpack.proxy.registerItemRenderer(this, 0, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

}
