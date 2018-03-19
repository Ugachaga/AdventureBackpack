package com.darkona.adventurebackpack.reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;

public class ModInfo
{
    public static final String MODID = "adventurebackpack";
    public static final String NAME = "Adventure Backpack";
    public static final String VERSION = "@VERSION@";
    public static final String GUI_FACTORY_CLASS = "com.darkona.adventurebackpack.client.gui.GuiFactory";
    public static final String MOD_CLIENT_PROXY = "com.darkona.adventurebackpack.proxy.ClientProxy";
    public static final String MOD_SERVER_PROXY = "com.darkona.adventurebackpack.proxy.ServerProxy";

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID)
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.MACHETE, 1, 0);
        }

        @Override
        public String getTabLabel()
        {
            return MODID;
        }
    };
}

