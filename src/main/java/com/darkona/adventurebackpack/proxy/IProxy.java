package com.darkona.adventurebackpack.proxy;

import net.minecraft.entity.player.EntityPlayer;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;

public interface IProxy
{
    void init();

    void displayBackpackGUI(EntityPlayer player, IInventoryBackpack inv, Constants.Source source);

    void displayCopterGUI(EntityPlayer player, InventoryCopter inv, Constants.Source source);

    void displayJetpackGUI(EntityPlayer player, InventoryJetpack inv, Constants.Source source);

}
