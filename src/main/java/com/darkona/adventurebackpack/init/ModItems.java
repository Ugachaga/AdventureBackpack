package com.darkona.adventurebackpack.init;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.darkona.adventurebackpack.item.ArmorAB;
import com.darkona.adventurebackpack.item.ItemAdventureHat;
import com.darkona.adventurebackpack.item.ItemAdventurePants;
import com.darkona.adventurebackpack.item.ItemAdventureSuit;
import com.darkona.adventurebackpack.item.ItemBackpack;
import com.darkona.adventurebackpack.item.ItemComponent;
import com.darkona.adventurebackpack.item.ItemCopter;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.item.ItemJetpack;
import com.darkona.adventurebackpack.item.ItemJuiceBottle;
import com.darkona.adventurebackpack.item.ItemMachete;
import com.darkona.adventurebackpack.item.ItemPistonBoots;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.getNull;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@GameRegistry.ObjectHolder(ModInfo.MODID)
public class ModItems
{
    @Nonnull public static final ItemMachete MACHETE = getNull();
    @Nonnull public static final ArmorAB ADVENTURE_HAT = getNull();
    @Nonnull public static final ArmorAB ADVENTURE_SUIT = getNull();
    @Nonnull public static final ArmorAB ADVENTURE_PANTS = getNull();
    @Nonnull public static final ArmorAB PISTON_BOOTS = getNull();
    @Nonnull public static final ItemBackpack ADVENTURE_BACKPACK = getNull();
    @Nonnull public static final ItemCopter COPTER_PACK = getNull();
    @Nonnull public static final ItemJetpack STEAM_JETPACK = getNull();
    @Nonnull public static final ItemComponent COMPONENT = getNull();
    @Nonnull public static final ItemHose HOSE = getNull();
    @Nonnull public static final ItemJuiceBottle MELON_JUICE_BOTTLE = getNull();

    @Mod.EventBusSubscriber(modid = ModInfo.MODID)
    public static class RegistrationHandler
    {
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            final Item[] items = {
                    new ItemMachete(),
                    new ItemAdventureHat(),
                    new ItemAdventureSuit(),
                    new ItemAdventurePants(),
                    new ItemPistonBoots(),
                    new ItemBackpack(),
                    new ItemCopter(),
                    new ItemJetpack(),
                    new ItemComponent(),
                    new ItemHose(),
                    new ItemJuiceBottle(),
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items)
            {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }

    public static void init()
    {
//        MACHETE.registerItemModel();
//        ADVENTURE_HAT.setCustomModelResourceLocation();
//        ADVENTURE_SUIT.registerItemModel();
//        ADVENTURE_PANTS.registerItemModel();
//        PISTON_BOOTS.registerItemModel();
//        COPTER_PACK.registerItemModel();
//        STEAM_JETPACK.registerItemModel();
//        ADVENTURE_BACKPACK.registerItemModel();
//        COMPONENT.registerItemModel();
//        HOSE.registerItemModel();
//        MELON_JUICE_BOTTLE.registerItemModel();
    }
}
