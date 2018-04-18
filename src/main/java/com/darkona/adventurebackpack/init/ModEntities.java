package com.darkona.adventurebackpack.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.entity.EntityInflatableBoat;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.getNull;

@GameRegistry.ObjectHolder(ModInfo.MODID)
public class ModEntities
{
    public static final EntityEntry FRIENDLY_SPIDER = getNull();
    public static final EntityEntry INFLATABLE_BOAT = getNull();

    @Mod.EventBusSubscriber(modid = ModInfo.MODID)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
        {
            EntityEntry[] entries = {
                    createBuilder("friendly_spider")
                            .entity(EntityFriendlySpider.class)
                            .tracker(80, 2, true)
                            .egg(0x342D27, 0xA80E0E)
                            .build(),

                    createBuilder("inflatable_boat")
                            .entity(EntityInflatableBoat.class)
                            .tracker(64, 2, true)
                            .build(),
            };

            event.getRegistry().registerAll(entries);
        }

        private static int entityID = 0;

        private static <E extends Entity> EntityEntryBuilder<E> createBuilder(String name)
        {
            EntityEntryBuilder<E> builder = EntityEntryBuilder.create();
            ResourceLocation registryName = new ResourceLocation(ModInfo.MODID, name);
            return builder.id(registryName, entityID++).name(registryName.toString());
        }
    }

}
