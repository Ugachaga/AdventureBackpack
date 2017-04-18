package com.darkona.adventurebackpack.init.recipes;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.BackpackNames;
import com.darkona.adventurebackpack.util.LogHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 20/10/2014
 *
 * @author Darkona
 */
public class AbstractBackpackRecipe implements IRecipe
{

    final BackpackRecipes br = new BackpackRecipes();
    private ItemStack result;
    @SuppressWarnings("unused")
	private ItemStack[] recipe;

    public boolean compareStacksForColor(ItemStack stack1, ItemStack stack2)
    {

        if (stack1 == null && stack2 == null)
        {
            return true;
        } else if (stack1 != null && stack2 != null)
        {
            if (stack1.getItem() != stack2.getItem())
            {
                return false;
            } else
            {
                return (stack1.getItemDamage() == stack2.getItemDamage());
            }
        }
        return false;
    }


    public AbstractBackpackRecipe()
    {
        recipes = new HashMap<String, ItemStack[]>();
        int i = 0;
        for (Field field : BackpackRecipes.class.getFields())
        {
            try
            {
                if (field.getType() == ItemStack[].class)
                {
                    recipes.put(field.getName(), (ItemStack[]) field.get(br));
                    i++;
                }
            } catch (Exception oops)
            {
                LogHelper.error("Huge mistake during reflection. Some bad things might happen.");
            }
        }
        LogHelper.info("Loaded " + i + " recipes for backpack coloration.");
    }

    public HashMap<String, ItemStack[]> recipes;

    public ItemStack makeBackpack(ItemStack backpackIn, String colorName)
    {
        if (backpackIn == null) return null;

        ItemStack newBackpack = backpackIn.copy(); //new ItemStack(ModItems.adventureBackpack,1);
        if (backpackIn.getTagCompound() == null)
        {
            backpackIn.setTagCompound(new NBTTagCompound());
            backpackIn.getTagCompound().setString("colorName", "Standard");
        }
        NBTTagCompound compound = (NBTTagCompound) backpackIn.getTagCompound().copy();
        newBackpack.setTagCompound(compound);
        newBackpack.getTagCompound().setString("colorName", colorName);
        return newBackpack;
    }


    public boolean match(ItemStack[] model, InventoryCrafting invC)
    {
        if (model == null || invC == null) return false;
        for (int i = 0; i < invC.getSizeInventory(); i++)
        {
            ItemStack m = model[i];
            ItemStack c = invC.getStackInSlot(i);
            if (!compareStacksForColor(m, c))
            {
                return false;
            }
            if (i == 4)
            {
                if (!BackpackNames.getBackpackColorName(c).equals("Standard"))
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean matches(InventoryCrafting invC, World world)
    {
        result = null;
        if (invC != null)
        {
            for (Map.Entry<String, ItemStack[]> recipe : recipes.entrySet())
            {
                if (match(recipe.getValue(), invC))
                {
                    result = makeBackpack(invC.getStackInSlot(4), recipe.getKey());
                    break;
                }
            }
        }
        return result != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting invC)
    {
        //result = null;
        if (invC != null)
        {
            for (Map.Entry<String, ItemStack[]> recipe : recipes.entrySet())
            {
                if (match(recipe.getValue(), invC))
                {
                    return makeBackpack(invC.getStackInSlot(4), recipe.getKey());
                }
            }
        }
        return this.result;
        // return result.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        ItemStack backpack = new ItemStack(ModItems.adventureBackpack, 1);
        backpack.setTagCompound(new NBTTagCompound());
        backpack.getTagCompound().setString("colorName", "Standard");
        return backpack;
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
