package com.darkona.adventurebackpack.init.recipes;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.BackpackUtils;

public class BackpackRecipesList
{
    public List<BackpackRecipe> recipes;

    public BackpackRecipesList()
    {
        String[] covered = {"XXX", "XaX", "XXX"};
        ItemStack backpack = BackpackUtils.createBackpackStack(BackpackTypes.STANDARD);

        ItemStack woolWhite = new ItemStack(Blocks.WOOL, 1, 0);
        ItemStack woolOrange = new ItemStack(Blocks.WOOL, 1, 1);
        ItemStack woolMagenta = new ItemStack(Blocks.WOOL, 1, 2);
        ItemStack woolLightBlue = new ItemStack(Blocks.WOOL, 1, 3);
        ItemStack woolYellow = new ItemStack(Blocks.WOOL, 1, 4);
        ItemStack woolLime = new ItemStack(Blocks.WOOL, 1, 5);
        ItemStack woolPink = new ItemStack(Blocks.WOOL, 1, 6);
        ItemStack woolGray = new ItemStack(Blocks.WOOL, 1, 7);
        ItemStack woolLightGray = new ItemStack(Blocks.WOOL, 1, 8);
        ItemStack woolCyan = new ItemStack(Blocks.WOOL, 1, 9);
        ItemStack woolPurple = new ItemStack(Blocks.WOOL, 1, 10);
        ItemStack woolBlue = new ItemStack(Blocks.WOOL, 1, 11);
        ItemStack woolBrown = new ItemStack(Blocks.WOOL, 1, 12);
        ItemStack woolGreen = new ItemStack(Blocks.WOOL, 1, 13);
        ItemStack woolRed = new ItemStack(Blocks.WOOL, 1, 14);
        ItemStack woolBlack = new ItemStack(Blocks.WOOL, 1, 15);

        Standard = reviewRecipe(
                "LGL",
                "TCT",
                "LSL",
                'L', Items.LEATHER,
                'G', Items.GOLD_INGOT,
                'T', new ItemStack(ModItems.COMPONENT, 1, 2),
                'S', new ItemStack(ModItems.COMPONENT, 1, 1),
                'C', Blocks.CHEST
        );

        Black = reviewRecipe(covered,
                'X', woolBlack,
                'a', backpack
        );

        Blaze = reviewRecipe(
                "BFB",
                "BaB",
                "PLP",
                'B', Items.BLAZE_ROD,
                'F', Items.FIRE_CHARGE,
                'a', backpack,
                'P', Items.BLAZE_POWDER,
                'L', Items.LAVA_BUCKET
        );

        Blue = reviewRecipe(covered,
                'X', woolBlue,
                'a', backpack
        );

        Bookshelf = reviewRecipe(
                "BDB",
                "BaB",
                "bbb",
                'B', Blocks.BOOKSHELF,
                'a', backpack,
                'b', Items.BOOK
        );

        Brown = reviewRecipe(covered,
                'X', woolBrown,
                'a', backpack
        );

        BrownMushroom = reviewRecipe(covered,
                'X', Blocks.BROWN_MUSHROOM,
                'a', backpack
        );

        Cactus = reviewRecipe(
                "CGC",
                "CaC",
                "SSS",
                'C', Blocks.CACTUS,
                'G', new ItemStack(Items.DYE, 1, 2),
                'a', backpack,
                'S', Blocks.SAND
        );

        Cake = reviewRecipe(
                "ECE",
                "WaW",
                "SmS",
                'a', backpack,
                'E', Items.EGG,
                'C', Items.CAKE,
                'W', Items.WHEAT,
                'S', Items.SUGAR,
                'm', Items.MILK_BUCKET
        );

        Chest = reviewRecipe(
                "CWC",
                "WaW",
                "CWC",
                'C', Blocks.CHEST,
                'W', Blocks.PLANKS,
                'a', backpack
        );

        Chicken = reviewRecipe(
                "FnF",
                "FaF",
                "nEn",
                'F', Items.FEATHER,
                'n', Items.GOLD_NUGGET,
                'a', backpack,
                'E', Items.EGG
        );

        Coal = reviewRecipe(
                "cCc",
                "CaC",
                "ccc",
                'c', Items.COAL,
                'C', Blocks.COAL_BLOCK,
                'a', backpack
        );

        Cookie = reviewRecipe(
                "cCc",
                "WaW",
                "ccc",
                'c', Items.COOKIE,
                'C', new ItemStack(Items.DYE, 1, 3),//Chocolate
                'W', Items.WHEAT,
                'a', backpack
        );

        Cow = reviewRecipe(
                "BLB",
                "BaB",
                "LML",
                'B', Items.BEEF,
                'a', backpack,
                'L', Items.LEATHER,
                'M', Items.MILK_BUCKET
        );

        Creeper = reviewRecipe(
                "GHG",
                "GaG",
                "TNT",//see what I did there? ;D
                'G', Items.GUNPOWDER,
                'H', new ItemStack(Items.SKULL, 1, 4), //Creeper Skull
                'a', backpack,
                'T', Blocks.TNT,
                'N', Blocks.TNT
        );

        Cyan = reviewRecipe(covered,
                'X', woolCyan,
                'a', backpack
        );

        Diamond = reviewRecipe(
                "GDG",
                "GaG",
                "GdG",
                'G', Blocks.GLASS,
                'D', Blocks.DIAMOND_BLOCK,
                'a', backpack,
                'd', Items.DIAMOND
        );

        Dragon = reviewRecipe(
                "EDE",
                "OaO",
                "POP",
                'E', Blocks.END_STONE,
                'D', new ItemStack(Blocks.DRAGON_EGG, 1),
                'O', Blocks.OBSIDIAN,
                'a', backpack,
                'P', Items.ENDER_PEARL
        );

        Egg = reviewRecipe(covered,
                'X', Items.EGG,
                'a', backpack
        );

        Emerald = reviewRecipe(
                "GEG",
                "GaG",
                "eGe",
                'G', Blocks.GLASS,
                'E', Blocks.EMERALD_BLOCK,
                'a', backpack,
                'e', Items.EMERALD
        );

        End = reviewRecipe(
                "eEe",
                "EaE",
                "eEe",
                'E', Blocks.END_STONE,
                'e', Items.ENDER_EYE,
                'a', backpack
        );

        Enderman = reviewRecipe(
                "PXP",
                "XaX",
                "PXP",
                'X', woolBlack,
                'P', Items.ENDER_PEARL,
                'a', backpack
        );

        Ghast = reviewRecipe(
                "GFG",
                "TaT",
                "GTG",
                'G', Items.GHAST_TEAR,
                'F', Items.FIRE_CHARGE,
                'T', Items.GUNPOWDER,
                'a', backpack
        );

        Glowstone = reviewRecipe(
                "GgG",
                "GaG",
                "GgG",
                'G', Blocks.GLOWSTONE,
                'g', Items.GLOWSTONE_DUST,
                'a', backpack
        );

        Gold = reviewRecipe(
                "FGF",
                "FaF",
                "gFg",
                'F', Blocks.GLASS,
                'G', Blocks.GOLD_BLOCK,
                'a', backpack,
                'g', Items.GOLD_INGOT
        );

        Gray = reviewRecipe(covered,
                'X', woolGray,
                'a', backpack
        );

        Green = reviewRecipe(covered,
                'X', woolGreen,
                'a', backpack
        );

        Haybale = reviewRecipe(covered,
                'X', Blocks.HAY_BLOCK,
                'a', backpack
        );

        Iron = reviewRecipe(
                "GIG",
                "GaG",
                "iGi",
                'G', Blocks.GLASS,
                'I', Blocks.IRON_BLOCK,
                'a', backpack,
                'i', Items.IRON_INGOT
        );

        Lapis = reviewRecipe(
                "GLG",
                "GaG",
                "lGl",
                'G', Blocks.GLASS,
                'L', Blocks.LAPIS_BLOCK,
                'l', new ItemStack(Items.DYE, 1, 4),
                'a', backpack
        );

        Leather = reviewRecipe(covered,
                'X', Items.LEATHER,
                'a', backpack
        );

        LightBlue = reviewRecipe(covered,
                'X', woolLightBlue,
                'a', backpack
        );

        LightGray = reviewRecipe(covered,
                'X', woolLightGray,
                'a', backpack
        );

        Lime = reviewRecipe(covered,
                'X', woolLime,
                'a', backpack
        );

        Magenta = reviewRecipe(covered,
                'X', woolMagenta,
                'a', backpack
        );

        MagmaCube = reviewRecipe(
                "MLM",
                "MaM",
                "MLM",
                'M', Items.MAGMA_CREAM,
                'a', backpack,
                'L', Items.LAVA_BUCKET
        );

        Melon = reviewRecipe(
                "mMm",
                "mam",
                "msm",
                'm', Items.MELON,
                'M', Blocks.MELON_BLOCK,
                'a', backpack,
                's', Items.MELON_SEEDS
        );

        Mooshroom = reviewRecipe(
                "SRL",
                "BaB",
                "LRS",
                'R', Blocks.RED_MUSHROOM,
                'B', Blocks.BROWN_MUSHROOM,
                'a', backpack,
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
                'a', backpack
        );

        Obsidian = reviewRecipe(
                covered,
                'X', Blocks.OBSIDIAN,
                'a', backpack
        );

        Ocelot = reviewRecipe(
                "FYF",
                "YaY",
                "FYF",
                'F', Items.FISH,
                'Y', woolYellow,
                'a', backpack
        );

        Orange = reviewRecipe(covered,
                'X', woolOrange,
                'a', backpack
        );

        Pig = reviewRecipe(covered,
                'X', Items.PORKCHOP,
                'a', backpack
        );

        Pink = reviewRecipe(covered,
                'X', woolPink,
                'a', backpack
        );

        Pumpkin = reviewRecipe(
                "PPP",
                "PaP",
                "PsP",
                'P', Blocks.PUMPKIN,
                'a', backpack,
                's', Items.PUMPKIN_SEEDS
        );

        Purple = reviewRecipe(covered,
                'X', woolPurple,
                'a', backpack
        );

        Quartz = reviewRecipe(
                "QqQ",
                "qaq",
                "QqQ",
                'Q', Blocks.QUARTZ_BLOCK,
                'q', Items.QUARTZ,
                'a', backpack
        );

        Rainbow = reviewRecipe(
                "RCP",
                "OaB",
                "YGF",
                'R', new ItemStack(Items.DYE, 1, 1), // RED //TODO check colors
                'O', new ItemStack(Items.DYE, 1, 14), //ORANGE
                'Y', new ItemStack(Items.DYE, 1, 11), //YELLOW
                'G', new ItemStack(Items.DYE, 1, 10), //LIME
                'F', new ItemStack(Items.DYE, 1, 6), //CYAN
                'B', new ItemStack(Items.DYE, 1, 4), //BLUE
                'P', new ItemStack(Items.DYE, 1, 5), //PURPLE
                'C', Items.RECORD_CAT,
                'a', backpack
        );

        Red = reviewRecipe(covered,
                'X', woolRed,
                'a', backpack
        );

        RedMushroom = reviewRecipe(covered,
                'X', Blocks.RED_MUSHROOM,
                'a', backpack
        );

        Redstone = reviewRecipe(
                "rRr",
                "RaR",
                "rRr",
                'R', Blocks.REDSTONE_BLOCK,
                'r', Items.REDSTONE,
                'a', backpack
        );

        Sandstone = reviewRecipe(
                "CSC",
                "SaS",
                "CSC",
                'S', new ItemStack(Blocks.SANDSTONE, 1, 0),
                'C', new ItemStack(Blocks.SANDSTONE, 1, 1),
                'a', backpack
        );

        Sheep = reviewRecipe(
                "WPW",
                "WaW",
                "WWW",
                'W', woolWhite,
                'P', woolPink,
                'a', backpack
        );

        Skeleton = reviewRecipe(
                "BSB",
                "bab",
                "BAB",
                'B', Items.BONE,
                'S', new ItemStack(Items.SKULL, 1, 0), //Skeleton skull
                'b', Items.BOW,
                'A', Items.ARROW,
                'a', backpack
        );

        Slime = reviewRecipe(covered,
                'X', Items.SLIME_BALL,
                'a', backpack
        );

        Snow = reviewRecipe(
                "III",
                "SaS",
                "sSs",
                'I', Blocks.ICE,
                'S', Blocks.SNOW,
                's', Items.SNOWBALL,
                'a', backpack
        );

        Spider = reviewRecipe(
                "ESE",
                "LaL",
                "ESE",
                'E', Items.SPIDER_EYE,
                'S', Items.STRING,
                'L', Blocks.LADDER,
                'a', backpack
        );

        White = reviewRecipe(covered,
                'X', woolWhite,
                'a', backpack
        );

        Wither = reviewRecipe(
                "SSS",
                "sas",
                "NsD",
                'S', new ItemStack(Items.SKULL, 1, 1), //WitherSkelleton Skull
                's', Blocks.SOUL_SAND,
                'N', Items.NETHER_STAR,
                'D', Items.DIAMOND,
                'a', backpack
        );

        WitherSkeleton = reviewRecipe(
                "BsB",
                "SaS",
                "CBC",
                'B', Items.BONE,
                'S', Items.STONE_SWORD,
                'a', backpack,
                'C', Items.COAL,
                's', new ItemStack(Items.SKULL, 1, 1)
        );

        Wolf = reviewRecipe(
                "BWB",
                "WaW",
                "BWB",
                'B', Items.BONE,
                'W', woolWhite,
                'a', backpack
        );

        Yellow = reviewRecipe(covered,
                'X', woolYellow,
                'a', backpack
        );

        Zombie = reviewRecipe(
                "FSF",
                "FaF",
                "FFF",
                'F', Items.ROTTEN_FLESH,
                'S', new ItemStack(Items.SKULL, 1, 2),
                'a', backpack
        );

        Carrot = reviewRecipe(covered,
                'a', backpack,
                'X', Items.CARROT
        );

        Silverfish = reviewRecipe(
                "CGC",
                "CaC",
                "CGC",
                'a', backpack,
                'G', woolLightGray,
                'C', new ItemStack(Blocks.STONEBRICK, 1, 2)
        );

        Sunflower = reviewRecipe(
                covered,
                'a', backpack,
                'X', new ItemStack(Blocks.DOUBLE_PLANT, 1, 0)
        );

        Horse = reviewRecipe(
                "LSL",
                "XaX",
                "LXL",
                'L', Items.LEATHER,
                'S', Items.SADDLE,
                'X', Blocks.HAY_BLOCK,
                'a', backpack);

        Overworld = reviewRecipe(
                "BBB",
                "GaG",
                "CCC",
                'B', woolLightBlue,
                'G', Blocks.GRASS,
                'a', backpack,
                'C', Blocks.COBBLESTONE

        );

        Squid = reviewRecipe(
                "BIB",
                "IaI",
                "BIB",
                'a', backpack,
                'B', woolBlue,
                'I', new ItemStack(Items.DYE, 1, 0)
        );

        Sponge = reviewRecipe(covered,
                'X', Blocks.SPONGE,
                'a', backpack
        );
    }

    public final Object[] Silverfish;
    public final Object[] Squid;
    public final Object[] Sunflower;
    public final Object[] Horse;
    public final Object[] Overworld;
    public final Object[] Carrot;
    public final Object[] Black;
    public final Object[] Blaze;
    public final Object[] Blue;
    public final Object[] Bookshelf;
    public final Object[] Brown;
    public final Object[] BrownMushroom;
    public final Object[] Cactus;
    public final Object[] Cake;
    public final Object[] Chicken;
    public final Object[] Chest;
    public final Object[] Coal;
    public final Object[] Cookie;
    public final Object[] Cow;
    public final Object[] Creeper;
    public final Object[] Cyan;
    public final Object[] Diamond;
    public final Object[] Dragon;
    public final Object[] Egg;
    public final Object[] Emerald;
    public final Object[] End;
    public final Object[] Enderman;
    public final Object[] Ghast;
    public final Object[] Glowstone;
    public final Object[] Gold;
    public final Object[] Gray;
    public final Object[] Green;
    public final Object[] Haybale;
    public final Object[] Iron;
    public final Object[] Lapis;
    public final Object[] Leather;
    public final Object[] LightBlue;
    public final Object[] LightGray;
    public final Object[] Lime;
    public final Object[] Magenta;
    public final Object[] MagmaCube;
    public final Object[] Melon;
    public final Object[] Mooshroom;
    public final Object[] Nether;
    public final Object[] Obsidian;
    public final Object[] Ocelot;
    public final Object[] Orange;
    public final Object[] Pig;
    public final Object[] Pink;
    public final Object[] Pumpkin;
    public final Object[] Purple;
    public final Object[] Quartz;
    public final Object[] Rainbow;
    public final Object[] Red;
    public final Object[] RedMushroom;
    public final Object[] Redstone;
    public final Object[] Sandstone;
    public final Object[] Sheep;
    public final Object[] Skeleton;
    public final Object[] Slime;
    public final Object[] Snow;
    public final Object[] Spider;
    public final Object[] Standard;
    public final Object[] Sponge;
    public final Object[] White;
    public final Object[] Wither;
    public final Object[] WitherSkeleton;
    public final Object[] Wolf;
    public final Object[] Yellow;
    public final Object[] Zombie;

    public static Object[] reviewRecipe(Object... objects)
    {
        return objects;
    }
}
