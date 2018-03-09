package com.darkona.adventurebackpack.util;

import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.init.ModDates;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 10/10/2014
 *
 * @author Darkona
 */
public class Resources
{
    private static final String TEXTURE_LOCATION = ModInfo.MODID;

    public static ResourceLocation getBackpackTexture(BackpackTypes type)
    {
        return type == BackpackTypes.STANDARD
               ? backpackTextureFromString(ModDates.getHoliday())
               : backpackTextureFromType(type);
    }

    private static ResourceLocation backpackTextureFromString(String color)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/backpack/" + color + ".png");
    }

    private static ResourceLocation backpackTextureFromType(BackpackTypes type)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/backpack/" + BackpackTypes.getSkinName(type) + ".png");
    }

    public static String modelTextureResourceString(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/models/" + name).toString();
    }

    public static ResourceLocation guiTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/gui/" + name + ".png");
    }

    public static ResourceLocation itemTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, name);
    }

    public static ResourceLocation blockTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, name);
    }

    public static ResourceLocation fluidTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/blocks/fluid." + name + ".png");
    }

    public static ResourceLocation modelTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/models/" + name + ".png");
    }

    public static String getIconString(String name)
    {
        return TEXTURE_LOCATION + ":" + name;
    }

}
