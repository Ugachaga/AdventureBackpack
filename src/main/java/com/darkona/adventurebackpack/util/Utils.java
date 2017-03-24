package com.darkona.adventurebackpack.util;

import java.util.Calendar;

import com.darkona.adventurebackpack.config.ConfigHandler;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created on 12/10/2014
 * @author Darkona
 *
 */
public class Utils
{

    public static float degreesToRadians(float degrees)
    {
        return degrees / 57.2957795f;
    }

    public static float radiansToDegrees(float radians)
    {
        return radians * 57.2957795f;
    }

    public static int[] calculateEaster(int year)
    {

        int a = year % 19,
                b = year / 100,
                c = year % 100,
                d = b / 4,
                e = b % 4,
                g = (8 * b + 13) / 25,
                h = (19 * a + b - d - g + 15) % 30,
                j = c / 4,
                k = c % 4,
                m = (a + 11 * h) / 319,
                r = (2 * e + 2 * j - k - h + m + 32) % 7,
                n = (h - m + r + 90) / 25,
                p = (h - m + r + n + 19) % 32;

        return new int[] { n, p };
    }

    public static String getHoliday()
    {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH) + 1,
                day = calendar.get(Calendar.DAY_OF_MONTH);

        //if (AdventureBackpack.instance.chineseNewYear) return "ChinaNewYear";
        //if (AdventureBackpack.instance.hannukah) return "Hannukah";
        if (month == Utils.calculateEaster(year)[0] && day == Utils.calculateEaster(year)[1]) return "Easter";
        String dia = "Standard";
        if (month == 1)
        {
            if (day == 1) dia = "NewYear";
            if (day == 28) dia = "Shuttle";//Challenger
        }
        if (month == 2)
        {
            if (day == 1) dia = "Shuttle";//Columbia
            if (day == 14) dia = "Valentines";
            //if (day == 23) dia = "Fatherland";
        }
        if (month == 3)
        {
            if (day == 17) dia = "Patrick";
        }
        if (month == 4)
        {
            if (day == 1) dia = "Fools";
            if (day == 25) dia = "Italy";
        }
        if (month == 5)
        {
            if (day == 8 || day == 9 || day == 10) dia = "Liberation";
        }
        if (month == 6)
        {
        }
        if (month == 7)
        {
            if (day == 4) dia = "USA";
            if (day == 24) dia = "Bolivar";
            //if (day == 14) dia = "Bastille";
        }
        if (month == 8)
        {
        }
        if (month == 9)
        {
            //if (day == 19) dia = "Pirate";
        }
        if (month == 10)
        {
            if (day == 3) dia = "Germany";
            if (day == 12) dia = "Columbus";
            if (day == 31) dia = "Halloween";
        }
        if (month == 11)
        {
            // if (day == 2) dia = "Muertos";
        }
        if (month == 12)
        {
            if (day >= 22 && day <= 26) dia = "Christmas";
            if (day == 31) dia = "NewYear";
        }
        //LogHelper.info("Today is: " + day + "/" + month + "/" + year + ". Which means today is: " + dia);
        return dia;

    }

    public static int isBlockRegisteredAsFluid(Block block)
    {
        /*
         * for (Map.Entry<String,Fluid> fluid :
         * getRegisteredFluids().entrySet()) { int ID =
         * (fluid.getValue().getBlockID() == BlockID) ? fluid.getValue().getID()
         * : -1; if (ID > 0) return ID; }
         */
        int fluidID = -1;
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values())
        {
            fluidID = (fluid.getBlock() == block) ? Block.getIdFromBlock(fluid.getBlock()) : -1;
            if (fluidID > 0)
            {
                return fluidID;
            }
        }
        return fluidID;
    }

    public static boolean shouldGiveEmpty(ItemStack cont)
    {
        boolean valid = true;
        // System.out.println("Item class name is: "+cont.getItem().getClass().getName());

        try
        {
            // Industrialcraft cells
            // if (apis.ic2.api.item.Items.getItem("cell").getClass().isInstance(cont.getItem()))
            // {
            //     valid = false;
            // }
            // Forestry capsules
            if (java.lang.Class.forName("forestry.core.items.ItemLiquidContainer").isInstance(cont.getItem()))
            {
                valid = false;
            }
        } catch (Exception oops)
        {

        }
        // Others

        return valid;
    }

    public static ChunkPos findBlock2D(World world, int x, int y, int z, Block block, int range)
    {
        for (int i = x - range; i <= x + range; i++)
        {
            for (int j = z - range; j <= z + range; j++)
            {
                if (world.getBlockState(new BlockPos(i, y, j)).getBlock() == block)
                {
                    return new ChunkPos(new BlockPos(i, y, j));
                }
            }
        }
        return null;
    }

    public static ChunkPos findBlock3D(World world, int x, int y, int z, Block block, int hRange, int vRange)
    {
        for (int i = (y - vRange); i <= (y + vRange); i++)
        {
            for (int j = (x - hRange); j <= (x + hRange); j++)
            {
                for (int k = (z - hRange); k <= (z + hRange); k++)
                {
                    if (world.getBlockState(new BlockPos(j, i, k)).getBlock() == block)
                    {
                        return new ChunkPos(new BlockPos(j, i, k));
                    }
                }
            }
        }
        return null;
    }

    public static String capitalize(String s)
    {
        // Character.toUpperCase(itemName.charAt(0)) + itemName.substring(1);
        return s.substring(0, 1).toUpperCase().concat(s.substring(1));
    }

    public static int getOppositeCardinalFromMeta(int meta)
    {
        return (meta % 2 == 0) ? (meta == 0) ? 2 : 0 : ((meta + 1) % 4) + 1;
    }

    //This is some black magic that returns a block or entity as far as the argument reach goes.
    public static RayTraceResult getMovingObjectPositionFromPlayersHat(World world, EntityPlayer player, boolean flag, double reach)
    {
        float f = 1.0F;
        float playerPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float playerYaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double playerPosX = player.prevPosX + (player.posX - player.prevPosX) * f;
        double playerPosY = (player.prevPosY + (player.posY - player.prevPosY) * f + 1.6200000000000001D) - player.getYOffset();
        double playerPosZ = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3d vecPlayer = new Vec3d(playerPosX, playerPosY, playerPosZ);
        float cosYaw = (float) Math.cos(-playerYaw * 0.01745329F - 3.141593F);
        float sinYaw = (float) Math.sin(-playerYaw * 0.01745329F - 3.141593F);
        float cosPitch = (float) -Math.cos(-playerPitch * 0.01745329F);
        float sinPitch = (float) Math.sin(-playerPitch * 0.01745329F);
        float pointX = sinYaw * cosPitch;
        float pointY = sinPitch;
        float pointZ = cosYaw * cosPitch;
        Vec3d vecPoint = vecPlayer.addVector(pointX * reach, pointY * reach, pointZ * reach);
        return world.rayTraceBlocks(vecPlayer, vecPoint, flag, !flag, flag);
    }

    public static String printCoordinates(int x, int y, int z)
    {
        return "X= " + x + ", Y= " + y + ", Z= " + z;
    }

    public static int secondsToTicks(int seconds)
    {
        return seconds * 20;
    }

    public static int secondsToTicks(float seconds)
    {
        return (int) seconds * 20;
    }

    public static boolean inServer()
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        return side == Side.SERVER;
    }

    private static ChunkPos checkCoordsForBackpack(IBlockAccess world, int origX, int origZ, int X, int Y, int Z, boolean except)
    {
        if (world.isAirBlock(new BlockPos(X, Y, Z)) || isReplaceable(world, X, Y, Z))
        {
            return new ChunkPos(new BlockPos(X, Y, Z));
        }
        return null;
    }

    public static boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
        return block.isReplaceable(world, new BlockPos(x, y, z));
    }

    private static ChunkPos checkCoordsForPlayer(IBlockAccess world, int origX, int origZ, int X, int Y, int Z, boolean except)
    {
        LogHelper.info("Checking coordinates in X=" + X + ", Y=" + Y + ", Z=" + Z);
        if (except && world.isSideSolid(new BlockPos(X, Y - 1, Z), EnumFacing.UP, true) && world.isAirBlock(new BlockPos(X, Y, Z)) && world.isAirBlock(new BlockPos(X, Y + 1, Z)) && !areCoordinatesTheSame2D(origX, origZ, X, Z))
        {
            LogHelper.info("Found spot with the exception of the origin point");
            return new ChunkPos(new BlockPos(X, Y, Z));
        }
        if (!except && world.isSideSolid(new BlockPos(X, Y - 1, Z), EnumFacing.UP, true) && world.isAirBlock(new BlockPos(X, Y, Z)) && world.isAirBlock(new BlockPos(X, Y + 1, Z)))
        {
            LogHelper.info("Found spot without exceptions");
            return new ChunkPos(new BlockPos(X, Y, Z));
        }
        return null;
    }

    /**
     * Gets you the nearest Empty Chunk Coordinates, free of charge! Looks in two dimensions and finds a block
     * that a: can have stuff placed on it and b: has space above it.
     * This is a spiral search, will begin at close range and move out.
     * @param world  The world object.
     * @param origX  Original X coordinate
     * @param origZ  Original Z coordinate
     * @param X      Moving X coordinate, should be the same as origX when called.
     * @param Y      Y coordinate, does not move.
     * @param Z      Moving Z coordinate, should be the same as origZ when called.
     * @param radius The radius of the search. If set to high numbers, will create a ton of lag
     * @param except Wether to include the origin of the search as a valid block.
     * @param steps  Number of steps of the recursive recursiveness that recurses through the recursion. It is the first size of the spiral, should be one (1) always at the first call.
     * @param pass   Pass switch for the witchcraft I can't quite explain. Set to 0 always at the beggining.
     * @param type   True = for player, False = for backpack
     * @return The coordinates of the block in the chunk of the world of the game of the server of the owner of the computer, where you can place something above it.
     */
    public static ChunkPos getNearestEmptyChunkCoordinatesSpiral(IBlockAccess world, int origX, int origZ, int X, int Y, int Z, int radius, boolean except, int steps, byte pass, boolean type)
    {
        //Spiral search, because I'm awesome :)
        //This is so the backpack tries to get placed near the death point first
        //And then goes looking farther away at each step
        //Steps mod 2 == 0 => X++, Z--
        //Steps mod 2 == 1 => X--, Z++

        //
        if (steps >= radius) return null;
        int i = X, j = Z;
        if (steps % 2 == 0)
        {
            if (pass == 0)
            {
                for (; i <= X + steps; i++)
                {

                    ChunkPos coords = type ? checkCoordsForPlayer(world, origX, origZ, X, Y, Z, except) : checkCoordsForBackpack(world, origX, origZ, X, Y, Z, except);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, Y, j, radius, except, steps, pass, type);
            }
            if (pass == 1)
            {
                for (; j >= Z - steps; j--)
                {
                    ChunkPos coords = type ? checkCoordsForPlayer(world, origX, origZ, X, Y, Z, except) : checkCoordsForBackpack(world, origX, origZ, X, Y, Z, except);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass--;
                steps++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, Y, j, radius, except, steps, pass, type);
            }
        }

        if (steps % 2 == 1)
        {
            if (pass == 0)
            {
                for (; i >= X - steps; i--)
                {
                    ChunkPos coords = type ? checkCoordsForPlayer(world, origX, origZ, X, Y, Z, except) : checkCoordsForBackpack(world, origX, origZ, X, Y, Z, except);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, Y, j, radius, except, steps, pass, type);
            }
            if (pass == 1)
            {
                for (; j <= Z + steps; j++)
                {
                    ChunkPos coords = type ? checkCoordsForPlayer(world, origX, origZ, X, Y, Z, except) : checkCoordsForBackpack(world, origX, origZ, X, Y, Z, except);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass--;
                steps++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, Y, j, radius, except, steps, pass, type);
            }
        }

        /* Old code. Still works, though.
        for (int i = x - radius; i <= x + radius; i++)
        {
            for (int j = y - (radius / 2); j <= y + (radius / 2); j++)
            {
                for (int k = z + radius; k <= z + (radius); k++)
                {
                    if (except && world.isSideSolid(i, j - 1, k, ForgeDirection.UP) && world.isAirBlock(i, j, k) && !areCoordinatesTheSame(x, y, z, i, j, k))
                    {
                        return new ChunkCoordinates(i, j, k);
                    }
                    if (!except && world.isSideSolid(i, j - 1, k, ForgeDirection.UP) && world.isAirBlock(i, j, k))
                    {
                        return new ChunkCoordinates(i, j, k);
                    }
                }
            }
        }*/
        return null;
    }

    private static boolean areCoordinatesTheSame2D(int X1, int Z1, int X2, int Z2)
    {
        return (X1 == X2 && Z1 == Z2);
    }

    /**
     * Seriously why doesn't Java's instanceof check for null?
     * @return true if the object is not null and is an instance of the supplied class.
     */
    public static boolean notNullAndInstanceOf(Object object, @SuppressWarnings("rawtypes") Class clazz)
    {
        return object != null && clazz.isInstance(object);
    }

    public static String getFirstWord(String text)
    {
        if (text.indexOf(' ') > -1)
        { // Check if there is more than one word.
            String firstWord = text.substring(0, text.indexOf(' '));
            String secondWord = text.substring(text.indexOf(' ') + 1);
            return firstWord.equals("Molten") ? secondWord : firstWord;// Extract first word.
        } else
        {
            return text; // Text is the first word itself.
        }
    }

    // -6 - not initialized
    // -3 - disabled by config
    // -2 - EnderIO not found
    // -1 - enchantment not found
    private static int soulBoundID = -6;

    public static int getSoulBoundID()
    {
        if (soulBoundID == -6) setSoulBoundID(); // initialize
        return soulBoundID;
    }

    private static void setSoulBoundID()
    {
        if (ConfigHandler.allowSoulBound)
        {
            if (ConfigHandler.IS_ENDERIO)
            {
                Enchantment ench = Enchantment.getEnchantmentByLocation("enchantment.enderio.soulBound");
                if (ench != null && ench.getName().equals("enchantment.enderio.soulBound"))
                {
                    soulBoundID = Enchantment.getEnchantmentID(ench);
                    return;
                }
                soulBoundID = -1;
            } else soulBoundID = -2;
        } else soulBoundID = -3;
    }

    public static boolean isSoulBounded(ItemStack stack)
    {
        int soulBound = getSoulBoundID();
        NBTTagList stackEnch = stack.getEnchantmentTagList();
        if (soulBound >= 0 && stackEnch != null)
        {
            for (int i = 0; i < stackEnch.tagCount(); i++)
            {
                int id = stackEnch.getCompoundTagAt(i).getInteger("id");
                if (id == soulBound) return true;
            }
        }
        return false;
    }

    public static boolean isSoulBook(ItemStack book)
    {
        int soulBound = getSoulBoundID();
        if (soulBound >= 0 && book.hasTagCompound())
        {
            NBTTagCompound bookData = book.getTagCompound();
            if (bookData.hasKey("StoredEnchantments"))
            {
                NBTTagList bookEnch = bookData.getTagList("StoredEnchantments", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                if (!bookEnch.getCompoundTagAt(1).getBoolean("id"))
                {
                    int id = bookEnch.getCompoundTagAt(0).getInteger("id");
                    if (id == soulBound) return true;
                }
            }
        }
        return false;
    }

}
