package com.darkona.adventurebackpack.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

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
    public ModelBiped getArmourModel(EntityLivingBase entity, ItemStack itemstack, EntityEquipmentSlot armourSlot, ModelBiped defaultModel)
    {
        if (itemStack != null)
        {
            if (stack.getItem() instance of ArmorAB)
            {
                entityEquipmentSlot type = ((ArmorAB) stack.getItem()).armourType;
                ModelBiped armourModel = null;
                switch(type)
                {
                    case HEAD:
                        armourModel = AdventureBackpack.proxy.getArmourModel(0);
                    break;
                    default:
                    break;
                }
                
                armourModel.bipedHead.showModel = armourSlot == EnitityEquipmentSlot.HEAD;
                
                return armourModel;
            }
        }
        return null;
    }

}
