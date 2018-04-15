package com.darkona.adventurebackpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBackWearableItem
{
    void onEquippedUpdate(World world, EntityPlayer player, ItemStack stack);

    void onPlayerDeath(World world, EntityPlayer player, ItemStack stack);

    void onEquipped(World world, EntityPlayer player, ItemStack stack);

    void onUnequipped(World world, EntityPlayer player, ItemStack stack);

//    @SideOnly(Side.CLIENT)
//    ModelBiped getWearableModel(ItemStack wearable);

    @SideOnly(Side.CLIENT)
    ResourceLocation getWearableTexture(ItemStack wearable);
}
