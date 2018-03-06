package com.darkona.adventurebackpack.proxy;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 14/10/2014
 *
 * @author Darkona
 */
public interface IProxy
{
    void init();

    void registerKeybindings();

    void initNetwork();

    void synchronizePlayer(int id, NBTTagCompound compound);

    void registerItemRenderer(Item item, int meta, String id);

    void setCustomModelResourceLocation(Item item, int meta, String id);
}
