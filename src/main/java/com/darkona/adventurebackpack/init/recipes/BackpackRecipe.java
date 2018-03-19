package com.darkona.adventurebackpack.init.recipes;

import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.reference.BackpackTypes;

public class BackpackRecipe
{
    public ItemStack[] array;
    public BackpackTypes type;

    BackpackRecipe()
    {

    }

    BackpackRecipe(BackpackTypes type, ItemStack[] array)
    {
        this.type = type;
        this.array = array;
    }
}
