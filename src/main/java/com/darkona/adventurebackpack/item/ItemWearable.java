package com.darkona.adventurebackpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.inventory.ContainerWearable;
import com.darkona.adventurebackpack.util.EnchUtils;

@SuppressWarnings("WeakerAccess")
public abstract class ItemWearable extends AdventureItem implements IBackWearableItem
{
    public ItemWearable(String name)
    {
        super(name);
        this.setMaxStackSize(1);
        this.setFull3D();
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player)
    {
        if (stack != null && player instanceof EntityPlayerMP && player.openContainer instanceof ContainerWearable)
            player.closeScreen();

        return super.onDroppedByPlayer(stack, player);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return EnchUtils.isSoulBook(book);
    }

}
