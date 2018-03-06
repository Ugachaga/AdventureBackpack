package com.darkona.adventurebackpack.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.util.LogHelper;

/**
 * Created on 22/12/2014
 *
 * @author Darkona
 */
public class ServerProxy implements IProxy
{
    private static final Map<UUID, NBTTagCompound> extendedEntityData = new HashMap<>();

    @Override
    public void init()
    {

    }

    @Override
    public void registerKeybindings()
    {

    }

    @Override
    public void initNetwork()
    {

    }

    @Override
    public void synchronizePlayer(int id, NBTTagCompound compound)
    {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }

    @Override
    public void setCustomModelResourceLocation(Item item, int meta, String id)
    {

    }

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