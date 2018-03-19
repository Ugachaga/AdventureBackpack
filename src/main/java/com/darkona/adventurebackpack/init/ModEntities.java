package com.darkona.adventurebackpack.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.entity.EntityInflatableBoat;
import com.darkona.adventurebackpack.reference.ModInfo;

public class ModEntities
{
    private static final String NAME_BOAT = "inflatableBoat";
    private static final String NAME_SPIDER = "rideableSpider";

    public static void init()
    {
        int counter = 0;
        EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MODID, NAME_BOAT), EntityInflatableBoat.class,
                NAME_BOAT, counter++, AdventureBackpack.instance, 64, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MODID, NAME_SPIDER), EntityFriendlySpider.class,
                NAME_SPIDER, counter++, AdventureBackpack.instance, 64, 2, true);
    }
}
