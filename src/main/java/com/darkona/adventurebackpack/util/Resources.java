package com.darkona.adventurebackpack.util;

import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.init.ModDates;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.ModInfo;

public class Resources
{
    public  static final String RESOURCE_PREFIX = ModInfo.MODID + ":";

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
        return new ResourceLocation(TEXTURE_LOCATION, "textures/backpack/" + type.getName() + ".png");
    }

    public static ResourceLocation guiTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/gui/" + name + ".png");
    }

    public static ResourceLocation modelTextures(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/models/" + name + ".png");
    }

}
