package com.darkona.adventurebackpack.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;

/**
 * Created on 14/10/2014
 *
 * @author Darkona
 */
public interface IProxy
{
    void displayBackpackGUI(EntityPlayer player, IInventoryBackpack inv, Constants.Source source);

    void displayCopterGUI(EntityPlayer player, InventoryCopter inv, Constants.Source source);

    void displayJetpackGUI(EntityPlayer player, InventoryJetpack inv, Constants.Source source);

    void init();

    void registerKeybindings();

    void synchronizePlayer(int id, NBTTagCompound compound);

    void registerItemRenderer(Item item, int meta, String id);

    void setCustomModelResourceLocation(Item item, int meta, String id);
}
