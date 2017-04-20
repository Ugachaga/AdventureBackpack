package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.client.models.ModelAdventureHat;
/**
 * Created by Darkona on 11/10/2014.
 */
public class ItemAdventureHat extends ArmorAB
{

    public ItemAdventureHat()
    {
        super(2, EntityEquipmentSlot.HEAD);
        setMaxDamage(Items.LEATHER_HELMET.getMaxDamage() + 45);
        setUnlocalizedName("adventureHat");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel)
    {
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ArmorAB)
            {
                EntityEquipmentSlot type = ((ArmorAB) itemStack.getItem()).armorType;
                ModelBiped armorModel = null;
                switch(type)
                {
                    case HEAD:
                        armorModel = new ModelAdventureHat();
                    break;
                    default:
                    break;
                }

                armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST)
						|| (armorSlot == EntityEquipmentSlot.CHEST);
				armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)
						|| (armorSlot == EntityEquipmentSlot.FEET);
				armorModel.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)
						|| (armorSlot == EntityEquipmentSlot.FEET);

				armorModel.isSneak = defaultModel.isSneak;
				armorModel.isRiding = defaultModel.isRiding;
				armorModel.isChild = defaultModel.isChild;
				armorModel.rightArmPose = defaultModel.rightArmPose;
				armorModel.leftArmPose = defaultModel.leftArmPose;

                return armorModel;
            }
        }
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
            return ModInfo.MOD_ID + ":"  +"textures/models/armor/adventureHat.png";

    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER));
    }
}
