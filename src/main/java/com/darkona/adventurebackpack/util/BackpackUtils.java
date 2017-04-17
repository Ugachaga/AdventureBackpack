package com.darkona.adventurebackpack.util;

import java.util.Timer;
import java.util.TimerTask;

import com.darkona.adventurebackpack.events.WearableEvent;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created on 08/01/2015
 *
 * @author Darkona
 */
public class BackpackUtils
{
    private static Timer timer = new Timer();
    private static TimerTask unequipTask;

    public enum reasons
    {
        SUCCESFUL, ALREADY_EQUIPPED
    }

    public static reasons equipWearable(ItemStack backpack, EntityPlayer player)
    {
        BackpackProperty prop = BackpackProperty.get(player);
        if (prop.getWearable() == null && player != null)
        {
            player.openContainer.onContainerClosed(player);
            prop.setWearable(backpack.copy());
            BackpackProperty.get(player).executeWearableEquipProtocol();
            backpack.stackSize--;
            WearableEvent event = new WearableEvent.EquipWearableEvent(player, prop.getWearable());
            MinecraftForge.EVENT_BUS.post(event);
            BackpackProperty.sync(player);
            return reasons.SUCCESFUL;
        } else
        {
            return reasons.ALREADY_EQUIPPED;
        }
    }

    public static void unequipWearable(EntityPlayer player)
    {
        unequipTask = new DelayUnequipTask(player);
        timer.schedule(unequipTask, 200);
    }

    public static NBTTagCompound getBackpackData(ItemStack backpack)
    {
        if (backpack.hasTagCompound() && backpack.getTagCompound().hasKey("backpackData"))
        {
            return backpack.getTagCompound().getCompoundTag("backpackData");
        }
        return null;
    }

    public static void setBackpackData(ItemStack stack, NBTTagCompound compound)
    {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag("backpackData", compound);
    }

    private static class DelayUnequipTask extends TimerTask
    {
        private EntityPlayer player;

        DelayUnequipTask(EntityPlayer player)
        {
            this.player = player;
        }

        @Override
        public void run()
        {
            BackpackProperty prop = BackpackProperty.get(player);
            if (prop.getWearable() != null)
            {
                player.openContainer.onContainerClosed(player);
                ItemStack gimme = prop.getWearable().copy();
                BackpackProperty.get(player).executeWearableUnequipProtocol();
                prop.setWearable(null);
                if (!player.inventory.addItemStackToInventory(gimme))
                {
                    player.dropItem(gimme, false);
                }
                WearableEvent event = new WearableEvent.UnequipWearableEvent(player, gimme);
                MinecraftForge.EVENT_BUS.post(event);
                BackpackProperty.sync(player);
            } else
            {
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.already.impossibru"));
            }
        }
    }
}