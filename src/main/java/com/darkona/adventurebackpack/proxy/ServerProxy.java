package com.darkona.adventurebackpack.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.util.LogHelper;

public class ServerProxy implements IProxy
{
    @Override
    public void preInit()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }

    @Override
    public void displayBackpackGUI(EntityPlayer player, IInventoryBackpack inv, Constants.Source source)
    {
        throw new IllegalArgumentException("Tried to open the GUI on the server");
    }

    @Override
    public void displayCopterGUI(EntityPlayer player, InventoryCopter inv, Constants.Source source)
    {
        throw new IllegalArgumentException("Tried to open the GUI on the server");
    }

    @Override
    public void displayJetpackGUI(EntityPlayer player, InventoryJetpack inv, Constants.Source source)
    {
        throw new IllegalArgumentException("Tried to open the GUI on the server");
    }


    //---

    private static final Map<UUID, NBTTagCompound> extendedEntityData = new HashMap<>();

    public static void storePlayerProps(EntityPlayer player)
    {
        try
        {
            NBTTagCompound data = BackpackProperty.get(player).getData();
            if (data.hasKey("wearable"))
            {
                ItemStack stack = new ItemStack(data.getCompoundTag("wearable"));
                LogHelper.info("Storing wearable: " + stack.getDisplayName());
            }
            extendedEntityData.put(player.getUniqueID(), data);
            LogHelper.info("Stored player properties for dead player");
        }
        catch (Exception ex)
        {
            LogHelper.error("Something went wrong while saving player properties: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static NBTTagCompound extractPlayerProps(UUID playerID)
    {
        return extendedEntityData.remove(playerID);
    }
}