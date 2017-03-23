package com.darkona.adventurebackpack.item;

import com.darkona.adventurebackpack.config.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Created by Darkona on 11/10/2014.
 */
public class ItemPistonBoots extends ArmorAB
{

    public ItemPistonBoots()
    {
        super(2, EntityEquipmentSlot.FEET);
        setMaxDamage(Items.IRON_BOOTS.getMaxDamage() + 55);
        setUnlocalizedName("pistonBoots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if (ConfigHandler.pistonBootsAutoStep)
            player.stepHeight = 1.001F;
        if (ConfigHandler.pistonBootsSprintBoost !=0 && player.isSprinting())
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(PotionType.getID(PotionTypes.SWIFTNESS)), 1, ConfigHandler.pistonBootsSprintBoost - 1));
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
