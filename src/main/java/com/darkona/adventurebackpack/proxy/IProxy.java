package com.darkona.adventurebackpack.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item;

/**
 * Created on 14/10/2014
 *
 * @author Darkona
 */
public interface IProxy
{

    public void init();

    public void registerKeybindings();

    void initNetwork();

    public void joinPlayer(EntityPlayer player);

    public void synchronizePlayer(int id, NBTTagCompound compound);

    void registerHandlers();

    void init(FMLInitializationEvent event);

    public void registerRenderInformation();

    public void registerItemRenderer(Item item, int meta, String id);

    public void setCustomModelResourceLocation(Item item, int meta, String id);

}