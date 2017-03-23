package com.darkona.adventurebackpack.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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

}
