package com.darkona.adventurebackpack.init.recipes;

import com.darkona.adventurebackpack.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 20/10/2014
 *
 * @author Darkona
 */
public class BackpackRecipes
{

    public List<BackpackRecipe> recipes;

    public BackpackRecipes()
    {
        String[] covered = {"XXX", "XaX", "XXX"};
        Black = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 15),
                'a', ModItems.adventureBackpack
        );

        Blaze = reviewRecipe(
                "BFB",
                "BaB",
                "PLP",
                'B', Items.BLAZE_ROD,
                'F', Items.FIRE_CHARGE,
                'a', ModItems.adventureBackpack,
                'P', Items.BLAZE_POWDER,
                'L', Items.LAVA_BUCKET
        );

        Blue = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 11),
                'a', ModItems.adventureBackpack
        );

        Bookshelf = reviewRecipe(
                "BDB",
                "BaB",
                "bbb",
                'B', Blocks.BOOKSHELF,
                'a', ModItems.adventureBackpack,
                'b', Items.BOOK
        );

        Brown = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 12),
                'a', ModItems.adventureBackpack
        );

        BrownMushroom = reviewRecipe(covered,
                'X', Blocks.BROWN_MUSHROOM,
                'a', ModItems.adventureBackpack
        );

        Cactus = reviewRecipe(
                "CGC",
                "CaC",
                "SSS",
                'C', Blocks.CACTUS,
                'G', new ItemStack(Items.DYE, 1, 2),
                'a', ModItems.adventureBackpack,
                'S', Blocks.SAND
        );

        Cake = reviewRecipe(
                "ECE",
                "WaW",
                "SmS",
                'a', ModItems.adventureBackpack,
                'E', Items.EGG,
                'C', Items.CAKE,
                'W', Items.WHEAT,
                'S', Items.SUGAR,
                'm', Items.MILK_BUCKET
        );

        ModdedNetwork = reviewRecipe(
                "EEE",
                "DaD",
                "DDD",
                'a', ModItems.adventureBackpack,
                'E', Items.EMERALD,
                'D', Items.DIAMOND
        );

        Chest = reviewRecipe(
                "CWC",
                "WaW",
                "CWC",
                'C', Blocks.CHEST,
                'W', Blocks.PLANKS,
                'a', ModItems.adventureBackpack
        );

        Chicken = reviewRecipe(
                "FnF",
                "FaF",
                "nEn",
                'F', Items.FEATHER,
                'n', Items.GOLD_NUGGET,
                'a', ModItems.adventureBackpack,
                'E', Items.EGG
        );

        Coal = reviewRecipe(
                "cCc",
                "CaC",
                "ccc",
                'c', Items.COAL,
                'C', Blocks.COAL_BLOCK,
                'a', ModItems.adventureBackpack
        );

        Cookie = reviewRecipe(
                "cCc",
                "WaW",
                "ccc",
                'c', Items.COOKIE,
                'C', new ItemStack(Items.DYE, 1, 3),
                'W', Items.WHEAT,
                'a', ModItems.adventureBackpack
        );

        Cow = reviewRecipe(
                "BLB",
                "BaB",
                "LML",
                'B', Items.BEEF,
                'a', ModItems.adventureBackpack,
                'L', Items.LEATHER,
                'M', Items.MILK_BUCKET
        );

        Creeper = reviewRecipe(
                "GHG",
                "GaG",
                "TNT",//see what I did there? ;D
                'G', Items.GUNPOWDER,
                'H', new ItemStack(Items.SKULL, 1, 4), //Creeper Skull
                'a', ModItems.adventureBackpack,
                'T', Blocks.TNT,
                'N', Blocks.TNT
        );

        Cyan = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 9),
                'a', ModItems.adventureBackpack
        );

        Diamond = reviewRecipe(
                "GDG",
                "GaG",
                "GdG",
                'G', Blocks.GLASS,
                'D', Blocks.DIAMOND_BLOCK,
                'a', ModItems.adventureBackpack,
                'd', Items.DIAMOND
        );

        Dragon = reviewRecipe(
                "EDE",
                "OaO",
                "POP",
                'E', Blocks.END_STONE,
                'D', new ItemStack(Blocks.DRAGON_EGG, 1),
                'O', Blocks.OBSIDIAN,
                'a', ModItems.adventureBackpack,
                'P', Items.ENDER_PEARL
        );

        Egg = reviewRecipe(covered,
                'X', Items.EGG,
                'a', ModItems.adventureBackpack
        );

        Emerald = reviewRecipe(
                "GEG",
                "GaG",
                "eGe",
                'G', Blocks.GLASS,
                'E', Blocks.EMERALD_BLOCK,
                'a', ModItems.adventureBackpack,
                'e', Items.EMERALD
        );

        End = reviewRecipe(
                "eEe",
                "EaE",
                "eEe",
                'E', Blocks.END_STONE,
                'e', Items.ENDER_EYE,
                'a', ModItems.adventureBackpack
        );

        Enderman = reviewRecipe(
                "PXP",
                "XaX",
                "PXP",
                'X', new ItemStack(Blocks.WOOL, 1, 15),
                'P', Items.ENDER_PEARL,
                'a', ModItems.adventureBackpack
        );

        Ghast = reviewRecipe(
                "GFG",
                "TaT",
                "GTG",
                'G', Items.GHAST_TEAR,
                'F', Items.FIRE_CHARGE,
                'T', Items.GUNPOWDER,
                'a', ModItems.adventureBackpack
        );

        Glowstone = reviewRecipe(
                "GgG",
                "GaG",
                "GgG",
                'G', Blocks.GLOWSTONE,
                'g', Items.GLOWSTONE_DUST,
                'a', ModItems.adventureBackpack
        );

        Gold = reviewRecipe(
                "FGF",
                "FaF",
                "gFg",
                'F', Blocks.GLASS,
                'G', Blocks.GOLD_BLOCK,
                'a', ModItems.adventureBackpack,
                'g', Items.GOLD_INGOT
        );

        Gray = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 7),
                'a', ModItems.adventureBackpack
        );

        Green = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 13),
                'a', ModItems.adventureBackpack
        );

        Haybale = reviewRecipe(covered,
                'X', Blocks.HAY_BLOCK,
                'a', ModItems.adventureBackpack
        );

        Iron = reviewRecipe(
                "GIG",
                "GaG",
                "iGi",
                'G', Blocks.GLASS,
                'I', Blocks.IRON_BLOCK,
                'a', ModItems.adventureBackpack,
                'i', Items.IRON_INGOT
        );

        Lapis = reviewRecipe(
                "GLG",
                "GaG",
                "lGl",
                'G', Blocks.GLASS,
                'L', Blocks.LAPIS_BLOCK,
                'l', new ItemStack(Items.DYE, 1, 4),
                'a', ModItems.adventureBackpack
        );

        Leather = reviewRecipe(covered,
                'X', Items.LEATHER,
                'a', ModItems.adventureBackpack
        );

        LightBlue = reviewRecipe(
                "XXX",
                "XaX",
                "XXX",
                'X', new ItemStack(Blocks.WOOL, 1, 3),
                'a', ModItems.adventureBackpack
        );

        LightGray = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 8),
                'a', ModItems.adventureBackpack
        );

        Lime = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 5),
                'a', ModItems.adventureBackpack
        );

        Magenta = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 2),
                'a', ModItems.adventureBackpack
        );

        MagmaCube = reviewRecipe(
                "MLM",
                "MaM",
                "MLM",
                'M', Items.MAGMA_CREAM,
                'a', ModItems.adventureBackpack,
                'L', Items.LAVA_BUCKET
        );

        Melon = reviewRecipe(
                "mMm",
                "mam",
                "msm",
                'm', Items.MELON,
                'M', Blocks.MELON_BLOCK,
                'a', ModItems.adventureBackpack,
                's', Items.MELON_SEEDS
        );

        Mooshroom = reviewRecipe(
                "SRL",
                "BaB",
                "LRS",
                'R', Blocks.RED_MUSHROOM,
                'B', Blocks.BROWN_MUSHROOM,
                'a', ModItems.adventureBackpack,
                'S', Items.MUSHROOM_STEW,
                'L', Blocks.MYCELIUM
        );

        Nether = reviewRecipe(
                "QwQ",
                "NaN",
                "QLQ",
                'Q', Items.QUARTZ,
                'N', Blocks.NETHERRACK,
                'w', Items.NETHER_WART,
                'L', Items.LAVA_BUCKET,
                'a', ModItems.adventureBackpack
        );

        Obsidian = reviewRecipe(
                covered,
                'X', Blocks.OBSIDIAN,
                'a', ModItems.adventureBackpack
        );

        Ocelot = reviewRecipe(
                "FYF",
                "YaY",
                "FYF",
                'F', Items.FISH,
                'Y', new ItemStack(Blocks.WOOL, 1, 4),
                'a', ModItems.adventureBackpack
        );

        Orange = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 1),
                'a', ModItems.adventureBackpack
        );

        Pig = reviewRecipe(covered,
                'X', Items.PORKCHOP,
                'a', ModItems.adventureBackpack
        );

        Pink = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 6),
                'a', ModItems.adventureBackpack
        );

        Pumpkin = reviewRecipe(
                "PPP",
                "PaP",
                "PsP",
                'P', Blocks.PUMPKIN,
                'a', ModItems.adventureBackpack,
                's', Items.PUMPKIN_SEEDS
        );

        Purple = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 10),
                'a', ModItems.adventureBackpack
        );

        Quartz = reviewRecipe(
                "QqQ",
                "qaq",
                "QqQ",
                'Q', Blocks.QUARTZ_BLOCK,
                'q', Items.QUARTZ,
                'a', ModItems.adventureBackpack
        );

        Rainbow = reviewRecipe(
                "RCP",
                "OaB",
                "YGF",
                'R', new ItemStack(Items.DYE, 1, 1), //RED
                'O', new ItemStack(Items.DYE, 1, 14),//ORANGE
                'Y', new ItemStack(Items.DYE, 1, 11),//YELLOW
                'G', new ItemStack(Items.DYE, 1, 10),//LIME
                'F', new ItemStack(Items.DYE, 1, 6),//CYAN
                'B', new ItemStack(Items.DYE, 1, 4),//BLUE
                'P', new ItemStack(Items.DYE, 1, 5),//PURPLE
                'C', Items.RECORD_CAT,
                'a', ModItems.adventureBackpack
        );

        Red = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 14),
                'a', ModItems.adventureBackpack
        );

        RedMushroom = reviewRecipe(covered,
                'X', Blocks.RED_MUSHROOM,
                'a', ModItems.adventureBackpack
        );

        Redstone = reviewRecipe(
                "rRr",
                "RaR",
                "rRr",
                'R', Blocks.REDSTONE_BLOCK,
                'r', Items.REDSTONE,
                'a', ModItems.adventureBackpack
        );

        Sandstone = reviewRecipe(
                "CSC",
                "SaS",
                "CSC",
                'S', new ItemStack(Blocks.SANDSTONE, 1, 0),
                'C', new ItemStack(Blocks.SANDSTONE, 1, 1),
                'a', ModItems.adventureBackpack
        );

        Sheep = reviewRecipe(
                "WPW",
                "WaW",
                "WWW",
                'W', new ItemStack(Blocks.WOOL, 1, 0),
                'P', new ItemStack(Blocks.WOOL, 1, 6),
                'a', ModItems.adventureBackpack
        );

        Skeleton = reviewRecipe(
                "BSB",
                "bab",
                "BAB",
                'B', Items.BONE,
                'S', new ItemStack(Items.SKULL, 1, 0),//Skeleton skull
                'b', Items.BOW,
                'A', Items.ARROW,
                'a', ModItems.adventureBackpack
        );

        Slime = reviewRecipe(covered,
                'X', Items.SLIME_BALL,
                'a', ModItems.adventureBackpack
        );

        Snow = reviewRecipe(
                "sSs",
                "SaS",
                "sSs",
                'S', Blocks.SNOW,
                's', Items.SNOWBALL,
                'a', ModItems.adventureBackpack
        );

        Spider = reviewRecipe(
                "ESE",
                "LaL",
                "ESE",
                'E', Items.SPIDER_EYE,
                'S', Items.STRING,
                'L', Blocks.LADDER,
                'a', ModItems.adventureBackpack
        );

        White = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 0),
                'a', ModItems.adventureBackpack
        );

        Wither = reviewRecipe(
                "SSS",
                "sas",
                "NsD",
                'S', new ItemStack(Items.SKULL, 1, 1),//WitherSkelleton Skull
                's', Blocks.SOUL_SAND,
                'N', Items.NETHER_STAR,
                'D', Items.DIAMOND,
                'a', ModItems.adventureBackpack
        );

        WitherSkeleton = reviewRecipe(
                "BsB",
                "SaS",
                "CBC",
                'B', Items.BONE,
                'S', Items.STONE_SWORD,
                'a', ModItems.adventureBackpack,
                'C', Items.COAL,
                's', new ItemStack(Items.SKULL, 1, 1)
        );

        Wolf = reviewRecipe(
                "BWB",
                "WaW",
                "BWB",
                'B', Items.BONE,
                'W', new ItemStack(Blocks.WOOL, 1, 0),
                'a', ModItems.adventureBackpack
        );

        Yellow = reviewRecipe(covered,
                'X', new ItemStack(Blocks.WOOL, 1, 4),
                'a', ModItems.adventureBackpack
        );

        Zombie = reviewRecipe(
                "FSF",
                "FaF",
                "FFF",
                'F', Items.ROTTEN_FLESH,
                'S', new ItemStack(Items.SKULL, 1, 2),
                'a', ModItems.adventureBackpack
        );
    }

    public final ItemStack[] Black;
    public final ItemStack[] Blaze;
    public final ItemStack[] Blue;
    public final ItemStack[] Bookshelf;
    public final ItemStack[] Brown;
    public final ItemStack[] BrownMushroom;
    public final ItemStack[] Cactus;
    public final ItemStack[] Cake;
    public final ItemStack[] Chicken;
    public final ItemStack[] Chest;
    public final ItemStack[] Coal;
    public final ItemStack[] Cookie;
    public final ItemStack[] Cow;
    public final ItemStack[] Creeper;
    public final ItemStack[] Cyan;
    public final ItemStack[] Diamond;
    public final ItemStack[] Dragon;
    public final ItemStack[] Egg;
    public final ItemStack[] Emerald;
    public final ItemStack[] End;
    public final ItemStack[] Enderman;
    public final ItemStack[] Ghast;
    public final ItemStack[] Glowstone;
    public final ItemStack[] Gold;
    public final ItemStack[] Gray;
    public final ItemStack[] Green;
    public final ItemStack[] Haybale;
    public final ItemStack[] Iron;
    public final ItemStack[] Lapis;
    public final ItemStack[] Leather;
    public final ItemStack[] LightBlue;
    public final ItemStack[] LightGray;
    public final ItemStack[] Lime;
    public final ItemStack[] Magenta;
    public final ItemStack[] MagmaCube;
    public final ItemStack[] Melon;
    public final ItemStack[] Mooshroom;
    public final ItemStack[] Nether;
    public final ItemStack[] Obsidian;
    public final ItemStack[] Ocelot;
    public final ItemStack[] Orange;
    public final ItemStack[] Pig;
    public final ItemStack[] Pink;
    public final ItemStack[] Pumpkin;
    public final ItemStack[] Purple;
    public final ItemStack[] Quartz;
    public final ItemStack[] Rainbow;
    public final ItemStack[] Red;
    public final ItemStack[] RedMushroom;
    public final ItemStack[] Redstone;
    public final ItemStack[] Sandstone;
    public final ItemStack[] Sheep;
    public final ItemStack[] Skeleton;
    public final ItemStack[] Slime;
    public final ItemStack[] Snow;
    public final ItemStack[] Spider;
    //public final ItemStack[] Sponge;
    public final ItemStack[] White;
    public final ItemStack[] Wither;
    public final ItemStack[] WitherSkeleton;
    public final ItemStack[] Wolf;
    public final ItemStack[] Yellow;
    public final ItemStack[] Zombie;
    public final ItemStack[] ModdedNetwork;


    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static ItemStack[] reviewRecipe(Object... objects)
    {
        String s = "";
        //BackpackRecipe recipe = new BackpackRecipe();
        int i = 0;
        int j = 0;
        int k = 0;
        /*if(objects[i] instanceof String)
        {
            recipe.name = (String)objects[i];
            i++;
        }*/
        if (objects[i] instanceof String[])
        {
            String[] astring = (String[]) ((String[]) objects[i++]);

            for (int l = 0; l < astring.length; ++l)
            {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else
        {
            while (objects[i] instanceof String)
            {
                String s2 = (String) objects[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < objects.length; i += 2)
        {
            Character character = (Character) objects[i];
            ItemStack itemstack1 = null;

            if (objects[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item) objects[i + 1]);
            } else if (objects[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block) objects[i + 1], 1);
            } else if (objects[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack) objects[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = ((ItemStack) hashmap.get(Character.valueOf(c0))).copy();
            } else
            {
                aitemstack[i1] = null;
            }
        }
        //recipe.array = aitemstack;
        return aitemstack;
    }

}
