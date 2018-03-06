package com.darkona.adventurebackpack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 11/10/2014.
 *
 * @author Javier Darkona
 */
public class CreativeTabAB
{
    public static final CreativeTabs TAB_AB = new CreativeTabs(ModInfo.MOD_ID)
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.MACHETE, 1, 0);
        }

        @Override
        public String getTranslatedTabLabel()
        {
            return super.getTranslatedTabLabel();
        }

        @Override
        public String getTabLabel()
        {
            return ModInfo.MOD_ID;
        }
    };
}
