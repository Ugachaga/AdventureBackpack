package com.darkona.adventurebackpack.init;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.darkona.adventurebackpack.item.ArmorAB;
import com.darkona.adventurebackpack.item.ItemAdventureBackpack;
import com.darkona.adventurebackpack.item.ItemAdventureHat;
import com.darkona.adventurebackpack.item.ItemAdventurePants;
import com.darkona.adventurebackpack.item.ItemAdventureSuit;
import com.darkona.adventurebackpack.item.ItemComponent;
import com.darkona.adventurebackpack.item.ItemCopterPack;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.item.ItemJuiceBottle;
import com.darkona.adventurebackpack.item.ItemMachete;
import com.darkona.adventurebackpack.item.ItemPistonBoots;
import com.darkona.adventurebackpack.item.ItemSteamJetpack;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.Null;

/**
 * Created on 10/10/2014
 *
 * @author Darkona
 */
@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
public class ModItems
{
    public static final ItemMachete MACHETE = Null();
    public static final ArmorAB ADVENTURE_HAT = Null();
    public static final ArmorAB ADVENTURE_SUIT = Null();
    public static final ArmorAB ADVENTURE_PANTS = Null();
    public static final ArmorAB PISTON_BOOTS = Null();
    public static final ItemCopterPack COPTER_PACK = Null();
    public static final ItemSteamJetpack STEAM_JETPACK = Null();
    public static final ItemAdventureBackpack ADVENTURE_BACKPACK = Null();
    public static final ItemComponent COMPONENT = Null();
    public static final ItemHose HOSE = Null();
    public static final ItemJuiceBottle MELON_JUICE_BOTTLE = Null();


    //TODO see https://github.com/Choonster-Minecraft-Mods/TestMod3/tree/1.12.2/src/main/java/choonster/testmod3/init
    @Mod.EventBusSubscriber(modid = ModInfo.MOD_ID)
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
                    new ItemCopterPack(),
                    new ItemSteamJetpack(),
                    new ItemAdventureBackpack(),
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
        // Sequence affect location in NEI and CreativeTab, so keep it logically grouped
//        GameRegistry.registerItem(MACHETE, "machete");
//        GameRegistry.registerItem(ADVENTURE_HAT, "adventureHat");
//        GameRegistry.registerItem(ADVENTURE_SUIT, "adventureSuit");
//        GameRegistry.registerItem(ADVENTURE_PANTS, "adventurePants");
//        GameRegistry.registerItem(PISTON_BOOTS, "pistonBoots");
//        GameRegistry.registerItem(COPTER_PACK, "copterPack");
//        GameRegistry.registerItem(STEAM_JETPACK, "steamJetpack");
//        GameRegistry.registerItem(ADVENTURE_BACKPACK, "adventureBackpack");
//        GameRegistry.registerItem(COMPONENT, "backpackComponent");
//        GameRegistry.registerItem(HOSE, "backpackHose");
//        GameRegistry.registerItem(MELON_JUICE_BOTTLE, "melonJuiceBottle");

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
