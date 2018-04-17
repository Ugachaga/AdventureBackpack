package com.darkona.adventurebackpack.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.client.models.ModelAdventureHat;

public class ItemAdventureHat extends AdventureArmor
{
    public ItemAdventureHat(String name)
    {
        super(name, 2, EntityEquipmentSlot.HEAD);

        setMaxDamage(Items.LEATHER_HELMET.getMaxDamage() + 45);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel)
    {
        if (entity instanceof EntityArmorStand)
            GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);

        return ModelAdventureHat.INSTANCE;
    }
}
