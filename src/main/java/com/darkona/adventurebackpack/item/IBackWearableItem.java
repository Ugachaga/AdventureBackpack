package com.darkona.adventurebackpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IBackWearableItem
{
    void onEquippedUpdate(World world, EntityPlayer player, ItemStack stack);

    void onPlayerDeath(World world, EntityPlayer player, ItemStack stack);

    void onEquipped(World world, EntityPlayer player, ItemStack stack);

    void onUnequipped(World world, EntityPlayer player, ItemStack stack);
}
