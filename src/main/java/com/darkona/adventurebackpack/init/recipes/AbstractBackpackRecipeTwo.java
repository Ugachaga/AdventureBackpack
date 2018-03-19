package com.darkona.adventurebackpack.init.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.BackpackTypes;

import static com.darkona.adventurebackpack.common.Constants.TAG_TYPE;

public class AbstractBackpackRecipeTwo //implements IRecipe //TODO unused, implement or delete
{
    private ItemStack result;
    private ItemStack[] recipe;
    private BackpackTypes type;

    public AbstractBackpackRecipeTwo(BackpackTypes type, ItemStack[] recipe)
    {
        this.recipe = recipe;
        this.type = type;
        this.result = makeBackpack(new ItemStack(ModItems.ADVENTURE_BACKPACK), this.type);
    }

    private boolean compareStacks(ItemStack stack1, ItemStack stack2)
    {
        if (stack1 == null && stack2 == null)
        {
            return true;
        }
        else if (stack1 != null && stack2 != null)
        {
            if (stack1.getItem().equals(stack2.getItem()))
            {
                return ((stack1.getItemDamage() == stack2.getItemDamage()));
            }
        }
        return false;
    }

    private static ItemStack makeBackpack(ItemStack backpackIn, BackpackTypes type)
    {
        if (backpackIn == null)
            return null;

        if (backpackIn.getTagCompound() == null)
        {
            backpackIn.setTagCompound(new NBTTagCompound());
            backpackIn.getTagCompound().setByte(TAG_TYPE, type.getMeta());
        }
        ItemStack newBackpack = backpackIn.copy();
        NBTTagCompound compound = backpackIn.getTagCompound().copy();
        newBackpack.setTagCompound(compound);
        newBackpack.getTagCompound().setByte(TAG_TYPE, type.getMeta());
        return newBackpack;
    }

    //@Override
    public boolean matches(InventoryCrafting invC, World world)
    {
        if (this.recipe == null || invC == null) return false;
        for (int i = 0; i < invC.getSizeInventory(); i++)
        {
            if (!compareStacks(this.recipe[i], invC.getStackInSlot(i)))
            {
                return false;
            }
        }
        return true;
    }

    //@Override
    public ItemStack getCraftingResult(InventoryCrafting invC)
    {
        if (matches(invC, null))
        {
            return makeBackpack(invC.getStackInSlot(4), this.type);
        }
        return null;
    }

    //@Override
    public ItemStack getRecipeOutput()
    {
        return result;
    }
}
