package com.darkona.adventurebackpack.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.config.ConfigHandler;

/**
 * Created by Darkona on 12/10/2014.
 */
public class SlotBackpack extends SlotAdventure
{
    private static final String[] FORBIDDEN_CLASSES = { //TODO need update
            // Adventure Backpack
            "com.darkona.adventurebackpack.item.ItemBackpack",
            // Backpack Mod
            "de.eydamos.backpack.item.ItemBackpack",
            "de.eydamos.backpack.item.ItemWorkbenchBackpack",
            // Blue Power Canvas Bags
            "com.bluepowermod.item.ItemCanvasBag",
            // Extra Utilities Golden Bag of Holding
            "com.rwtema.extrautils.item.ItemGoldenBag",
            // Forestry Backpacks +addons
            "forestry.storage.items.ItemBackpack",
            "forestry.storage.items.ItemBackpackNaturalist",
            // Jabba Dolly
            "mcp.mobius.betterbarrels.common.items.dolly.ItemBarrelMover",
            "mcp.mobius.betterbarrels.common.items.dolly.ItemDiamondMover",
            // Project Red Exploration Backpacks
            "mrtjp.projectred.exploration.ItemBackpack",};

    SlotBackpack(IInventoryBackpack inventory, int slotIndex, int posX, int posY)
    {
        super(inventory, slotIndex, posX, posY);
    }

    public static boolean isValidItem(ItemStack stack)
    {
        if (stack == ItemStack.EMPTY)
            return false;

        Item itemCurrent = stack.getItem();

        for (String classDisallowed : FORBIDDEN_CLASSES)
        {
            if (itemCurrent.getClass().getName().equals(classDisallowed)) return false;
        }
        for (String itemDisallowed : ConfigHandler.itemBlacklist)
        {
            //TODO check it
            //if (Item.itemRegistry.getNameForObject(itemCurrent).equals(itemDisallowed)) return false;
            if (Item.REGISTRY.getNameForObject(itemCurrent).toString().equals(itemDisallowed)) return false;
        }
        return true;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return isValidItem(stack);
    }
}
