package com.darkona.adventurebackpack.reference;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;

@JEIPlugin
public class JEIPluginAdventureBackpack implements IModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        //blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.BACKPACK_BLOCK));
        //blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.SLEEPING_BAG_BLOCK));
    }
}
