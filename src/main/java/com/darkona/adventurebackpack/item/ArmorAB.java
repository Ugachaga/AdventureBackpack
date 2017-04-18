package com.darkona.adventurebackpack.item;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.init.ModMaterials;
import com.darkona.adventurebackpack.reference.ModInfo;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Created on 11/10/2014.
 * @author Javier Darkona
 */
public class ArmorAB extends ItemArmor
{

    /**
     * @param type        2 Chain
     * @param renderIndex 0 Helmet, 1 Plate, 2 Pants, 3 Boots
     */
    public ArmorAB(int renderIndex, EntityEquipmentSlot slot)
    {
        super(ModMaterials.ruggedLeather, renderIndex, slot);
        setCreativeTab(CreativeTabAB.ADVENTURE_BACKPACK_CREATIVE_TAB);
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
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
        return String.format("item.%s%s", ModInfo.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public void registerItemModel() {
		AdventureBackpack.proxy.registerItemRenderer(this, 0, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

    public void setCustomModelResourceLocation()
    {
        AdventureBackpack.proxy.setCustomModelResourceLocation(this, 0, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

}
