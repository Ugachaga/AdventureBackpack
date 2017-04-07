package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.proxy.CommonProxy;
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
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER));
    }

    //TODO: confirm this
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot armourSlot, ModelBiped defaultModel)
    {
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ArmorAB)
            {
                EntityEquipmentSlot type = ((ArmorAB) itemStack.getItem()).armorType;
                ModelBiped armourModel = null;
                switch(type)
                {
                    case HEAD:
                        armourModel = new ModelAdventureHat();
                    break;
                    default:
                    break;
                }

                armourModel.bipedHead.showModel = armourSlot == EntityEquipmentSlot.HEAD;

                return armourModel;
            }
        }
        return null;
    }


}
