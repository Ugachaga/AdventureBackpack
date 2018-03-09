package com.darkona.adventurebackpack.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

import com.darkona.adventurebackpack.reference.ModInfo;

public class RecipeHelper
{
    private static int j = 0;
    private static final String MODID = ModInfo.MODID;
    private static final String MODNAME = ModInfo.NAME;


    public static void convertToShaped(Item output, Object ... input)
    {
        convertToShaped(new ItemStack(output), input);
    }

    public static void convertToShaped(Block output, Object ... input)
    {
        convertToShaped(new ItemStack(output), input);
    }

    public static /*ShapedRecipes*/ void convertToShaped(ItemStack output, Object ... input)
    {
        String recipe = "";
        int i = 0;    // input[i] counter
        int cols = 0; // count columns in LAST row
        int rows = 0; // count of rows

        if (input[i] instanceof String[])
        {
            String[] astring = (String[]) input[i++];

            for (String str : astring)
            {
                rows++;
                cols = str.length();
                recipe += str;
            }
        }
        else
        {
            while (input[i] instanceof String)
            {
                String str = (String)input[i++];
                rows++;
                cols = str.length();
                recipe += str;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < input.length; i += 2)
        {
            Character ch = (Character) input[i];
            ItemStack stack = null;

            if (input[i + 1] instanceof Item)
            {
                stack = new ItemStack((Item) input[i + 1]);
            }
            else if (input[i + 1] instanceof Block)
            {
                stack = new ItemStack((Block) input[i + 1], 1, 32767);
            }
            else if (input[i + 1] instanceof ItemStack)
            {
                stack = (ItemStack) input[i + 1];
            }

            hashmap.put(ch, stack);
        }

        ItemStack[] finalRecipe = new ItemStack[cols * rows];

        for (int l = 0; l < cols * rows; l++)
        {
            char ch = recipe.charAt(l);

            if (hashmap.containsKey(ch))
            {
                finalRecipe[l] = ((ItemStack) hashmap.get(ch)).copy();
            }
            else
            {
                finalRecipe[l] = null;
            }
        }

        addShaped(output, cols, rows, (Object[]) finalRecipe);

//        ShapedRecipes shapedrecipes = new ShapedRecipes(cols, rows, finalRecipe, output);
//        this.recipes.add(shapedrecipes);
//        return shapedrecipes;
    }








    /*
     * This needs to be looped through and passed in a RegistryEvent.Register<IRecipe>, it should also be populated
     * during that event.
     */
    public static final List<IRecipe> RECIPES = new ArrayList<IRecipe>();

    /*
     * This adds the recipe to the list of crafting recipes.  Since who cares about names, it adds it as recipesX,
     * where X is the current recipe you are adding.
     */
    public static void addRecipe(int j, IRecipe rec)
    {
        if (rec.getRegistryName() == null)
            RECIPES.add(rec.setRegistryName(new ResourceLocation(MODID, "recipes" + j)));
        else
            RECIPES.add(rec);
    }

    /*
     * This adds the recipe to the list of crafting recipes.  Cares about names.
     */
    public static void addRecipe(String name, IRecipe rec)
    {
        if (rec.getRegistryName() == null)
            RECIPES.add(rec.setRegistryName(new ResourceLocation(MODID, name)));
        else
            RECIPES.add(rec);
    }

    /*
     * Adds a shapeless recipe with X output using an array of inputs. Use Strings for OreDictionary support.
     * This array is not ordered.
     */
    public static void addShapeless(ItemStack output, Object... inputs)
    {
        addRecipe(j++, new ShapelessRecipes(MODID + ":" + j, output, createInput(inputs)));
    }

    public static void addShapeless(Item output, Object... inputs)
    {
        addShapeless(new ItemStack(output), inputs);
    }

    public static void addShapeless(Block output, Object... inputs)
    {
        addShapeless(new ItemStack(output), inputs);
    }

    /*
     * Adds a shapeless recipe with X output using an array of inputs. Use Strings for OreDictionary support.
     * This array is not ordered.  This has a custom group.
     */
    public static void addShapeless(String group, ItemStack output, Object... inputs)
    {
        addRecipe(j++, new ShapelessRecipes(MODID + ":" + group, output, createInput(inputs)));
    }

    public static void addShapeless(String group, Item output, Object... inputs)
    {
        addShapeless(group, new ItemStack(output), inputs);
    }

    public static void addShapeless(String group, Block output, Object... inputs)
    {
        addShapeless(group, new ItemStack(output), inputs);
    }

    /*
     * Adds a shapeless recipe with X output on a crafting grid that is W x H, using an array of inputs.
     * Use null for nothing, use Strings for OreDictionary support, this array must have a length of width * height.
     * This array is ordered, and items must follow from left to right, top to bottom of the crafting grid.
     */
    public static void addShaped(ItemStack output, int width, int height, Object... input)
    {
        addRecipe(j++, genShaped(output, width, height, input));
    }

    public static void addShaped(ItemStack output, Object... input)
    {
        int len = input.length;
        if (len == 9)
            addShaped(output, 3, 3, input);
        else if (len == 4)
            addShaped(output, 2, 2, input);
        else
            throw new IllegalArgumentException("wrong input length: " + len);
        //addShaped(output, 3, 3, input);
    }

    public static void addShaped(Item output, int width, int height, Object... input)
    {
        addShaped(new ItemStack(output), width, height, input);
    }

    public static void addShaped(Item output, Object... input)
    {
        int len = input.length;
        if (len == 9)
            addShaped(output, 3, 3, input);
        else if (len == 4)
            addShaped(output, 2, 2, input);
        else
            throw new IllegalArgumentException("wrong input length: " + len);
    }

    public static void addShaped(Block output, int width, int height, Object... input)
    {
        addShaped(new ItemStack(output), width, height, input);
    }

    public static void addShaped(Block output, Object... input)
    {
        int len = input.length;
        if (len == 9)
            addShaped(output, 3, 3, input);
        else if (len == 4)
            addShaped(output, 3, 3, input);
        else
            throw new IllegalArgumentException("wrong input length: " + len);
    }

    /*
     * Adds a shapeless recipe with X output on a crafting grid that is W x H, using an array of inputs.  Use null
     * for nothing, use Strings for OreDictionary support, this array must have a length of width * height. This array
     * is ordered, and items must follow from left to right, top to bottom of the crafting grid. This has a custom group.
     */
    public static void addShaped(String group, ItemStack output, int width, int height, Object... input)
    {
        addRecipe(j++, genShaped(MODID + ":" + group, output, width, height, input));
    }

    public static void addShaped(String group, Item output, int width, int height, Object... input)
    {
        addShaped(group, new ItemStack(output), width, height, input);
    }

    public static void addShaped(String group, Block output, int width, int height, Object... input)
    {
        addShaped(group, new ItemStack(output), width, height, input);
    }

    // public static void addShapedBackpack() //TODO

    public static ShapedRecipes genShaped(ItemStack output, int l, int w, Object[] input)
    {
        return genShaped(MODID + ":" + j, output, l, w, input);
    }

    public static ShapedRecipes genShaped(String group, ItemStack output, int l, int w, Object[] input)
    {
        if (input[0] instanceof List)
            input = ((List<?>) input[0]).toArray();
        else if (input[0] instanceof Object[])
            input = (Object[]) input[0];
        if (l * w != input.length)
            throw new UnsupportedOperationException(
                    "Attempted to add invalid shaped recipe. Input length: " + input.length + "   " + Arrays.toString(input));
        NonNullList<Ingredient> inputL = NonNullList.create();
        for (int i = 0; i < input.length; i++)
        {
            Object k = input[i];
            if (k instanceof String)
            {
                inputL.add(i, new OreIngredient((String) k));
            }
            else if (k instanceof ItemStack && !((ItemStack) k).isEmpty())
            {
                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
            }
            else if (k instanceof Item)
            {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
            }
            else if (k instanceof Block)
            {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
            }
            else
            {
                inputL.add(i, Ingredient.EMPTY);
            }
        }

        return new ShapedRecipes(group, l, w, inputL, output);
    }

    public static NonNullList<Ingredient> createInput(Object[] input)
    {
        if (input[0] instanceof List)
            input = ((List<?>) input[0]).toArray();
        else if (input[0] instanceof Object[])
            input = (Object[]) input[0];
        NonNullList<Ingredient> inputL = NonNullList.create();
        for (int i = 0; i < input.length; i++)
        {
            Object k = input[i];
            if (k instanceof String)
            {
                inputL.add(i, new OreIngredient((String) k));
            }
            else if (k instanceof ItemStack)
            {
                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
            }
            else if (k instanceof Item)
            {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
            }
            else if (k instanceof Block)
            {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
            }
            else
            {
                throw new UnsupportedOperationException(
                        "Attempted to add invalid shapeless recipe. Complain to the author of " + MODNAME);
            }
        }
        return inputL;
    }
}
