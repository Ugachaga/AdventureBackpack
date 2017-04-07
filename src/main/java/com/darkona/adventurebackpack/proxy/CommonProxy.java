package com.darkona.adventurebackpack.proxy;

import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.client.model.ModelBiped;


public abstract class CommonProxy implements IProxy
{
    private EntityPlayerMP player;

    @Override
    public void registerHandlers()
    {

        Object eventHandler = null;
        MinecraftForge.EVENT_BUS.register(eventHandler);
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    public void serverTick(PlayerTickEvent event)
    {
        {
            if (event.side == Side.SERVER)
            {
                setPlayer((EntityPlayerMP) event.player);
            }
        }

    }

    public EntityPlayerMP getPlayer()
    {
        return player;
    }

    public void setPlayer(EntityPlayerMP player)
    {
        this.player = player;
    }

    public void cape()
    {
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

}