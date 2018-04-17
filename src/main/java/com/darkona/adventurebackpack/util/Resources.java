package com.darkona.adventurebackpack.util;

import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.init.ModDates;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.ModInfo;

public class Resources
{
    public static final String RESOURCE_PREFIX = ModInfo.MODID + ":";

    private static final String TEXTURE_LOCATION = ModInfo.MODID;

    public static ResourceLocation getBackpackTexture(BackpackTypes type)
    {
        return type == BackpackTypes.STANDARD
               ? getBackpackTexture(ModDates.getHoliday())
               : getBackpackTexture(type.name());
    }

    private static ResourceLocation getBackpackTexture(String skin)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/backpack/" + skin + ".png");
    }

    public static ResourceLocation getGuiTexture(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/gui/" + name + ".png");
    }

    public static ResourceLocation getModelTexture(String name)
    {
        return new ResourceLocation(TEXTURE_LOCATION, "textures/models/" + name + ".png");
    }
}
