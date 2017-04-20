package com.darkona.adventurebackpack.init;

import com.darkona.adventurebackpack.item.ArmorAB;
import com.darkona.adventurebackpack.item.ItemAdventureBackpack;
import com.darkona.adventurebackpack.item.ItemAdventureHat;
import com.darkona.adventurebackpack.item.ItemAdventurePants;
import com.darkona.adventurebackpack.item.ItemAdventureSuit;
import com.darkona.adventurebackpack.item.ItemCoalJetpack;
import com.darkona.adventurebackpack.item.ItemComponent;
import com.darkona.adventurebackpack.item.ItemCopterPack;
import com.darkona.adventurebackpack.item.ItemCrossbow;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.item.ItemJuiceBottle;
import com.darkona.adventurebackpack.item.ItemMachete;
import com.darkona.adventurebackpack.item.ItemPistonBoots;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created on 10/10/2014
 * @author Darkona
 *
 */
public class ModItems
{

    public static final ItemMachete machete = new ItemMachete();
    public static final ItemCrossbow cwxbow = new ItemCrossbow();
    public static final ItemAdventureHat adventureHat = new ItemAdventureHat();
    public static final ArmorAB adventureSuit = new ItemAdventureSuit();
    public static final ArmorAB adventurePants = new ItemAdventurePants();
    public static final ArmorAB pistonBoots = new ItemPistonBoots();
    public static final ItemCopterPack copterPack = new ItemCopterPack();
    public static final ItemCoalJetpack CoalJetpack = new ItemCoalJetpack();
    public static final ItemAdventureBackpack adventureBackpack = new ItemAdventureBackpack();
    public static final ItemComponent component = new ItemComponent();
    public static final ItemHose hose = new ItemHose();
    public static final ItemJuiceBottle MELON_JUICE_BOTTLE = new ItemJuiceBottle();

    public static void init()
    {
        GameRegistry.registerItem(adventureBackpack, "adventureBackpack");
        GameRegistry.registerItem(adventureHat, "adventureHat");
        GameRegistry.registerItem(adventurePants, "adventurePants");
        GameRegistry.registerItem(adventureSuit, "adventureSuit");
        GameRegistry.registerItem(component, "backpackComponent");
        GameRegistry.registerItem(hose, "backpackHose");
        GameRegistry.registerItem(cwxbow, "clockworkCrossbow");
        GameRegistry.registerItem(CoalJetpack, "CoalJetpack");
        GameRegistry.registerItem(copterPack, "copterPack");
        GameRegistry.registerItem(machete, "machete");
        GameRegistry.registerItem(pistonBoots, "pistonBoots");
        GameRegistry.registerItem(MELON_JUICE_BOTTLE, "melonJuiceBottle");


        machete.registerItemModel();
        cwxbow.registerItemModel();
        adventureHat.setCustomModelResourceLocation();
        adventureSuit.registerItemModel();
        adventurePants.registerItemModel();
        pistonBoots.registerItemModel();
        copterPack.registerItemModel();
        CoalJetpack.registerItemModel();
        adventureBackpack.registerItemModel();
        component.registerItemModel();
        hose.registerItemModel();
        MELON_JUICE_BOTTLE.registerItemModel();

    }

}
