package com.darkona.adventurebackpack.init.recipes;

import com.darkona.adventurebackpack.init.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created on 24/12/2014
 *
 * @author Darkona
 */
public class AbstractBackpackRecipeTwo implements IRecipe
{
    private ItemStack result;
    private ItemStack[] recipe;
    private String name;

    public AbstractBackpackRecipeTwo(String name, ItemStack[] recipe)
    {
        this.recipe = recipe;
        this.name = name;
        this.result = makeBackpack(new ItemStack(ModItems.adventureBackpack), this.name);
    }

    public boolean compareStacks(ItemStack stack1, ItemStack stack2)
    {

        if (stack1 == null && stack2 == null)
        {
            return true;
        } else if (stack1 != null && stack2 != null)
        {
            if (stack1.getItem().equals(stack2.getItem()))
            {
                /*if(stack1.getItem() instanceof ItemAdventureBackpack)
                {
                    return stack1.stackTagCompound.getString("colorName").equals("Standard");
                }*/
                return ((stack1.getItemDamage() == stack2.getItemDamage()));
            }
        }
        return false;
    }

    public static ItemStack makeBackpack(ItemStack backpackIn, String colorName)
    {
        if (backpackIn == null) return null;
        if (backpackIn.getTagCompound() == null)
        {
            backpackIn.setTagCompound(new NBTTagCompound());
            backpackIn.getTagCompound().setString("colorName", colorName);
        }
        ItemStack newBackpack = backpackIn.copy();
        NBTTagCompound compound = (NBTTagCompound) backpackIn.getTagCompound().copy();
        newBackpack.setTagCompound(compound);
        newBackpack.getTagCompound().setString("colorName", colorName);
        return newBackpack;
    }

    @Override
    public boolean matches(InventoryCrafting invC, World world)
    {
        //LogHelper.info("Matching recipe");
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

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invC)
    {
        if (matches(invC, null))
        {
            return makeBackpack(invC.getStackInSlot(4), this.name);
        }
        return null;
    }

    @Override
    public int getRecipeSize()
    {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return result;
    }

        public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return aitemstack;
    }
}
