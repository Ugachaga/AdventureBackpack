package com.darkona.adventurebackpack.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.darkona.adventurebackpack.reference.ModInfo;

public class ConfigGuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public boolean hasConfigGui()
    {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parent)
    {
        return new ModGuiConfig(parent);
    }

    @Nullable
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    public static class ModGuiConfig extends GuiConfig
    {
        ModGuiConfig(GuiScreen parent)
        {
            super(parent, getConfigElements(), ModInfo.MODID, false, false, ModInfo.NAME);
        }

        private static List<IConfigElement> getConfigElements()
        {
            List<IConfigElement> configElements = new ArrayList<>();

            for (TopCategories topCategory : TopCategories.values())
            {
                ConfigCategory category = ConfigHandler.config.getCategory(topCategory.name().toLowerCase());
                configElements.add(new ConfigElement(category));
            }
            return configElements;
        }

        private enum TopCategories
        {
            GAMEPLAY,
            GRAPHICS,
            SOUND,
            ITEMS,
            WORLDGEN,
            EXPERIMENTAL,
        }
    }

}