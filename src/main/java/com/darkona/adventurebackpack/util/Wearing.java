package com.darkona.adventurebackpack.util;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.inventory.IInventoryTanks;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.item.IBackWearableItem;
import com.darkona.adventurebackpack.item.ItemBackpack;
import com.darkona.adventurebackpack.item.ItemAdventureHat;
import com.darkona.adventurebackpack.item.ItemJetpack;
import com.darkona.adventurebackpack.item.ItemCopter;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.item.ItemPistonBoots;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.reference.BackpackTypes;

/**
 * Created on 11/10/2014
 *
 * @author Darkona
 */
public class Wearing
{
    public enum WearableType
    {
        BACKPACK, COPTER, JETPACK, UNKNOWN;

        public static WearableType get(@Nonnull ItemStack stack)
        {
            Item item = stack.getItem();
            if (item == ModItems.ADVENTURE_BACKPACK)
                return BACKPACK;
            if (item == ModItems.COPTER_PACK)
                return COPTER;
            if (item == ModItems.STEAM_JETPACK)
                return JETPACK;
            return UNKNOWN;
        }
    }

    // Wearable
    public static boolean isWearingWearable(EntityPlayer player)
    {
        return false;
        //return BackpackProperty.get(player).getWearable() != null && BackpackProperty.get(player).getWearable().getItem() instanceof IBackWearableItem;
    }

    public static ItemStack getWearingWearable(EntityPlayer player)
    {
        return isWearingWearable(player) ? BackpackProperty.get(player).getWearable() : null;
    }

    public static WearableType getWearingWearableType(EntityPlayer player)
    {
        return isWearingWearable(player) ? WearableType.get(BackpackProperty.get(player).getWearable()) : WearableType.UNKNOWN;
    }

    public static IInventoryTanks getWearingWearableInv(EntityPlayer player)
    {
        ItemStack wearable = Wearing.getWearingWearable(player);
        if (wearable.getItem() instanceof ItemBackpack)
            return new InventoryBackpack(wearable);
        if (wearable.getItem() instanceof ItemJetpack)
            return new InventoryJetpack(wearable);
        if (wearable.getItem() instanceof ItemCopter)
            return new InventoryCopter(wearable);
        return null;
    }

    public static boolean isHoldingWearable(EntityPlayer player)
    {
        return !player.inventory.getCurrentItem().isEmpty() && player.inventory.getCurrentItem().getItem() instanceof IBackWearableItem;
    }

    // Backpack
    public static boolean isWearingBackpack(EntityPlayer player)
    {
        return BackpackProperty.get(player).getWearable() != null && BackpackProperty.get(player).getWearable().getItem() instanceof ItemBackpack;
    }

    public static boolean isWearingTheRightBackpack(EntityPlayer player, BackpackTypes... backpacks)
    {
        if (Wearing.isWearingBackpack(player))
        {
            for (BackpackTypes type : backpacks)
            {
                if (BackpackTypes.getType(Wearing.getWearingBackpack(player)) == type)
                    return true;
            }
        }
        return false;
    }

    public static ItemStack getWearingBackpack(EntityPlayer player)
    {
        return isWearingBackpack(player) ? BackpackProperty.get(player).getWearable() : null;
    }

    public static InventoryBackpack getWearingBackpackInv(EntityPlayer player)
    {
        return new InventoryBackpack(BackpackProperty.get(player).getWearable());
    }

    public static boolean isHoldingBackpack(EntityPlayer player)
    {
        return !player.inventory.getCurrentItem().isEmpty() && player.inventory.getCurrentItem().getItem() instanceof ItemBackpack;
    }

    public static ItemStack getHoldingBackpack(EntityPlayer player)
    {
        return isHoldingBackpack(player) ? player.inventory.getCurrentItem() : null;
    }

    public static InventoryBackpack getHoldingBackpackInv(EntityPlayer player)
    {
        return new InventoryBackpack(player.getHeldItemMainhand());
    }

    // Copter
    public static boolean isWearingCopter(EntityPlayer player)
    {
        return BackpackProperty.get(player).getWearable() != null && BackpackProperty.get(player).getWearable().getItem() instanceof ItemCopter;
    }

    public static ItemStack getWearingCopter(EntityPlayer player)
    {
        return isWearingCopter(player) ? BackpackProperty.get(player).getWearable() : null;
    }

    public static boolean isHoldingCopter(EntityPlayer player)
    {
        return !player.inventory.getCurrentItem().isEmpty() && player.inventory.getCurrentItem().getItem() instanceof ItemCopter;
    }

    public static ItemStack getHoldingCopter(EntityPlayer player)
    {
        return isHoldingCopter(player) ? player.inventory.getCurrentItem() : null;
    }

    // Jetpack
    public static boolean isWearingJetpack(EntityPlayer player)
    {
        return BackpackProperty.get(player).getWearable() != null && BackpackProperty.get(player).getWearable().getItem() instanceof ItemJetpack;
    }

    public static ItemStack getWearingJetpack(EntityPlayer player)
    {
        return isWearingJetpack(player) ? BackpackProperty.get(player).getWearable() : null;
    }

    public static boolean isHoldingJetpack(EntityPlayer player)
    {
        return !player.inventory.getCurrentItem().isEmpty()
                && player.inventory.getCurrentItem().getItem() instanceof ItemJetpack;
    }

    public static ItemStack getHoldingJetpack(EntityPlayer player)
    {
        return isHoldingJetpack(player) ? player.inventory.getCurrentItem() : null;
    }

    // Hose
    public static boolean isHoldingHose(EntityPlayer player)
    {
        return !player.inventory.getCurrentItem().isEmpty()
                && player.inventory.getCurrentItem().getItem() instanceof ItemHose;
    }

    public static ItemStack getHoldingHose(EntityPlayer player)
    {
        return isHoldingHose(player) ? player.inventory.getCurrentItem() : null;
    }

    // Armor
    public static boolean isWearingHat(EntityPlayer player)
    {
        return player.inventory.armorInventory.get(3) != ItemStack.EMPTY
                && player.inventory.armorInventory.get(3).getItem() instanceof ItemAdventureHat;
    }

    public static ItemStack getWearingHat(EntityPlayer player)
    {
        return isWearingHat(player) ? player.inventory.armorInventory.get(3) : ItemStack.EMPTY;
    }

    public static boolean isWearingBoots(EntityPlayer player)
    {
        return player.inventory.armorInventory.get(0) != ItemStack.EMPTY
                && player.inventory.armorInventory.get(0).getItem() instanceof ItemPistonBoots;
    }

    public static ItemStack getWearingBoots(EntityPlayer player)
    {
        return isWearingBoots(player) ? player.inventory.armorInventory.get(0) : ItemStack.EMPTY;
    }
}
