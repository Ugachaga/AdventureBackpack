package com.darkona.adventurebackpack.init;

import java.lang.reflect.Field;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.init.recipes.BackpackRecipesList;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.RecipeHelper;

/**
 * Created on 20/10/2014
 *
 * @author Darkona
 */
public class ModRecipes
{
    public static void init()
    {
        if (ConfigHandler.recipeSaddle)
        {
            RecipeHelper.convertToShaped(new ItemStack(Items.SADDLE),
                    "LLL",
                    "L L",
                    "I I",
                    'L', Items.LEATHER,
                    'I', Items.IRON_INGOT);
        }

        // CampFire
        RecipeHelper.convertToShaped(ModBlocks.BLOCK_CAMPFIRE,
                " S ",
                "SxS",
                "ccc",
                'S', Items.STICK,
                'x', Items.COAL,
                'c', Blocks.COBBLESTONE);

        // Copter Pack
        RecipeHelper.convertToShaped(BackpackUtils.createCopterStack(),
                "WBW",
                "TEI",
                "CDI",
                'W', Blocks.PLANKS,
                'B', new ItemStack(ModItems.COMPONENT, 1, 6),
                'T', new ItemStack(ModItems.COMPONENT, 1, 2),
                'E', new ItemStack(ModItems.COMPONENT, 1, 5),
                'C', new ItemStack(Items.DYE, 1, 2),
                'D', Items.DIAMOND,
                'I', Items.IRON_INGOT);

        // Steam Jetpack
        RecipeHelper.convertToShaped(BackpackUtils.createJetpackStack(),
                "SWT",
                "GIG",
                "FWS",
                'W', Blocks.PLANKS,
                'G', Items.GOLD_INGOT,
                'I', Items.IRON_INGOT,
                'S', Blocks.STONE,
                'F', Blocks.FURNACE,
                'T', new ItemStack(ModItems.COMPONENT, 1, 2));

        // Machete Handle
        RecipeHelper.convertToShaped(new ItemStack(ModItems.COMPONENT, 1, 4),
                "YIY",
                "BSB",
                "RbR",
                'Y', new ItemStack(Items.DYE, 1, 11),
                'B', new ItemStack(Items.DYE, 1, 4),
                'R', new ItemStack(Items.DYE, 1, 1),
                'b', new ItemStack(Items.DYE, 1, 0),
                'I', Items.IRON_INGOT,
                'S', Items.STICK);

        // Machete
        RecipeHelper.convertToShaped(new ItemStack(ModItems.MACHETE),
                " I ",
                " I ",
                " H ",
                'I', Items.IRON_INGOT,
                'H', new ItemStack(ModItems.COMPONENT, 1, 4));

        // Adventure Hat
        RecipeHelper.convertToShaped(new ItemStack(ModItems.ADVENTURE_HAT),
                "   ",
                "nC ",
                "LLL",
                'n', Items.GOLD_NUGGET,
                'C', Items.LEATHER_HELMET,
                'L', Items.LEATHER);

        // Adventure Suit
        RecipeHelper.convertToShaped(new ItemStack(ModItems.ADVENTURE_SUIT),
                "LWL",
                "LVL",
                "   ",
                'V', Items.LEATHER_CHESTPLATE,
                'W', Blocks.WOOL,
                'L', Items.LEATHER);

        // Adventure Pants
        RecipeHelper.convertToShaped(new ItemStack(ModItems.ADVENTURE_PANTS),
                "LVL",
                "LWL",
                "   ",
                'V', Items.LEATHER_LEGGINGS,
                'W', Blocks.WOOL,
                'L', Items.LEATHER);

        // Piston Boots
        RecipeHelper.convertToShaped(new ItemStack(ModItems.PISTON_BOOTS),
                " B ",
                "PSP",
                'B', Items.LEATHER_BOOTS,
                'P', Blocks.PISTON,
                'S', Items.SLIME_BALL);

        // Melon Juice Bottle
        RecipeHelper.addShapeless(new ItemStack(ModItems.MELON_JUICE_BOTTLE), Items.MELON, Items.POTIONITEM);

        // Hose Nozzle
        RecipeHelper.convertToShaped(new ItemStack(ModItems.COMPONENT, 1, 3),
                " G ",
                "ILI",
                "   ",
                'G', Items.GOLD_INGOT,
                'I', Items.IRON_INGOT,
                'L', Blocks.LEVER);

        // Hose
        RecipeHelper.convertToShaped(new ItemStack(ModItems.HOSE),
                "NGG",
                "  G",
                'N', new ItemStack(ModItems.COMPONENT, 1, 3),
                'G', new ItemStack(Items.DYE, 1, 2));

        // Sleeping Bag
        RecipeHelper.convertToShaped(new ItemStack(ModItems.COMPONENT, 1, 1),
                "  X",
                "CCC",
                'X', Blocks.WOOL,
                'C', Blocks.CARPET);

        //Backpack Tank
        RecipeHelper.convertToShaped(new ItemStack(ModItems.COMPONENT, 1, 2),
                "GIG",
                "GGG",
                "GIG",
                'G', Blocks.GLASS,
                'I', Items.IRON_INGOT);

        // Copter Engine
        RecipeHelper.convertToShaped(new ItemStack(ModItems.COMPONENT, 1, 5),
                "IGI",
                "PCP",
                "FOF",
                'I', Items.IRON_INGOT,
                'G', Items.GOLD_INGOT,
                'P', Blocks.PISTON,
                'F', Blocks.FURNACE,
                'C', Items.CAULDRON,
                'O', Blocks.OBSIDIAN);

        // Copter Blades
        RecipeHelper.convertToShaped(new ItemStack(ModItems.COMPONENT, 1, 6),
                "III",
                " F ",
                " F ",
                'I', Items.IRON_INGOT,
                'F', Blocks.OAK_FENCE); //TODO change to oredict fence


//        // Inflatable Boat
//        RecipeHelper.addShaped(new ItemStack(ModItems.COMPONENT, 1, 7),
//                "   ",
//                "w w",
//                "sws",
//                'w', Blocks.WOOL,
//                's', Blocks.SAND);
//
//        // Inflatable Boat (Motorized)
//        RecipeHelper.addShaped(new ItemStack(ModItems.COMPONENT, 1, 8),
//                " B ",
//                " E ",
//                " H ",
//                'B', new ItemStack(ModItems.COMPONENT, 1, 7),
//                'H', new ItemStack(ModItems.COMPONENT, 1, 9),
//                'E', new ItemStack(ModItems.COMPONENT, 1, 5));
//
//        // Hydro Blades
//        RecipeHelper.addShaped(new ItemStack(ModItems.COMPONENT, 1, 9),
//                " F ",
//                " F ",
//                "III",
//                'I', Items.IRON_INGOT,
//                'F', Blocks.OAK_FENCE);

        BackpackRecipesList br = new BackpackRecipesList();
        int counter = 0;
        for (BackpackTypes type : BackpackTypes.values())
        {
            for (Field field : BackpackRecipesList.class.getFields())
            {
                try
                {
                    if (field.getName().equals(BackpackTypes.getSkinName(type)))
                    {
                        //TODO add ShapedBackpackRecipe support
                        RecipeHelper.convertToShaped(BackpackUtils.createBackpackStack(type), (Object[]) field.get(br));
                        counter++;
                    }
                }
                catch (Exception oops)
                {
                    LogHelper.error("Huge mistake during reflection. Some bad things might happen: " + oops.getClass().getName());
                    oops.printStackTrace();
                }
            }

        }
        LogHelper.info("Loaded " + counter + " backpack recipes.");
    }

    /*public static void conditionalInit()
    {
        if (LoadedMods.BUILDCRAFT)
        {

        }
    }*/
}