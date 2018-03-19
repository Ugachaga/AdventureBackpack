package com.darkona.adventurebackpack.util;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.init.ModBlocks;

public class CoordsUtils
{
    private CoordsUtils() {}

    public static BlockPos findBlock2D(World world, int x, int y, int z, Block block, int range)
    {
        for (int i = x - range; i <= x + range; i++)
        {
            for (int j = z - range; j <= z + range; j++)
            {
                BlockPos pos = new BlockPos(i, y, j);
                if (world.getBlockState(new BlockPos(pos)) == block)
                    return pos;
            }
        }
        return null;
    }

    public static BlockPos findBlock3D(World world, int x, int y, int z, Block block, int hRange, int vRange)
    {
        for (int i = (y - vRange); i <= (y + vRange); i++)
        {
            for (int j = (x - hRange); j <= (x + hRange); j++)
            {
                for (int k = (z - hRange); k <= (z + hRange); k++)
                {
                    BlockPos pos = new BlockPos(j, i, k);
                    if (world.getBlockState(pos) == block)
                        return pos;
                }
            }
        }
        return null;
    }

    public static boolean isValidHeight(World world, BlockPos pos)
    {
        return pos.getY() > 0 && pos.getY() < world.getHeight();
    }

    @Nullable
    private static BlockPos checkCoordsForBackpack(IBlockAccess world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        return isAirOrReplaceable(world, pos) ? pos : null;
    }

    private static boolean isAirOrReplaceable(IBlockAccess world, BlockPos pos)
    {
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    private static BlockPos checkCoordsForPlayer(IBlockAccess world, int origX, int origZ, int x, int y, int z, boolean except)
    {
        //TODO simplify
        LogHelper.info("Checking coordinates in X=" + x + ", Y=" + y + ", Z=" + z);
        if (except && world.isSideSolid(new BlockPos(x, y - 1, z), EnumFacing.UP, true)
                && world.isAirBlock(new BlockPos(x, y, z)) && world.isAirBlock(new BlockPos(x, y + 1, z))
                && !areCoordinatesTheSame2D(origX, origZ, x, z))
        {
            LogHelper.info("Found spot with the exception of the origin point");
            return new BlockPos(x, y, z);
        }
        if (!except && world.isSideSolid(new BlockPos(x, y - 1, z), EnumFacing.UP, true)
                && world.isAirBlock(new BlockPos(x, y, z)) && world.isAirBlock(new BlockPos(x, y + 1, z)))
        {
            LogHelper.info("Found spot without exceptions");
            return new BlockPos(x, y, z);
        }
        return null;
    }

    /**
     * Gets you the nearest Empty Chunk Coordinates, free of charge! Looks in two dimensions and finds a block
     * that a: can have stuff placed on it and b: has space above it.
     * This is a spiral search, will begin at close range and move out.
     *
     * @param world  The world object.
     * @param origX  Original x coordinate
     * @param origZ  Original z coordinate
     * @param x      Moving x coordinate, should be the same as origX when called.
     * @param y      y coordinate, does not move.
     * @param z      Moving z coordinate, should be the same as origZ when called.
     * @param radius The radius of the search. If set to high numbers, will create a ton of lag
     * @param except Wether to include the origin of the search as a valid block.
     * @param steps  Number of steps of the recursive recursiveness that recurses through the recursion. It is the first size of the spiral, should be one (1) always at the first call.
     * @param pass   Pass switch for the witchcraft I can't quite explain. Set to 0 always at the beggining.
     * @param type   True = for player, False = for backpack
     * @return The coordinates of the block in the chunk of the world of the game of the server of the owner of the computer, where you can place something above it.
     */
    public static BlockPos getNearestEmptyChunkCoordinatesSpiral(IBlockAccess world, int origX, int origZ, int x, int y, int z, int radius, boolean except, int steps, byte pass, boolean type)
    {
        //Spiral search, because I'm awesome :)
        //This is so the backpack tries to get placed near the death point first
        //And then goes looking farther away at each step
        //Steps mod 2 == 0 => x++, z--
        //Steps mod 2 == 1 => x--, z++

        if (steps >= radius) return null;
        int i = x, j = z;
        if (steps % 2 == 0)
        {
            if (pass == 0)
            {
                for (; i <= x + steps; i++)
                {
                    BlockPos coords = type ? checkCoordsForPlayer(world, origX, origZ, x, y, z, except) : checkCoordsForBackpack(world, x, y, z);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, y, j, radius, except, steps, pass, type);
            }
            if (pass == 1)
            {
                for (; j >= z - steps; j--)
                {
                    BlockPos coords = type ? checkCoordsForPlayer(world, origX, origZ, x, y, z, except) : checkCoordsForBackpack(world, x, y, z);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass--;
                steps++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, y, j, radius, except, steps, pass, type);
            }
        }

        if (steps % 2 == 1)
        {
            if (pass == 0)
            {
                for (; i >= x - steps; i--)
                {
                    BlockPos coords = type ? checkCoordsForPlayer(world, origX, origZ, x, y, z, except) : checkCoordsForBackpack(world, x, y, z);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, y, j, radius, except, steps, pass, type);
            }
            if (pass == 1)
            {
                for (; j <= z + steps; j++)
                {
                    BlockPos coords = type ? checkCoordsForPlayer(world, origX, origZ, x, y, z, except) : checkCoordsForBackpack(world, x, y, z);
                    if (coords != null)
                    {
                        return coords;
                    }
                }
                pass--;
                steps++;
                return getNearestEmptyChunkCoordinatesSpiral(world, origX, origZ, i, y, j, radius, except, steps, pass, type);
            }
        }

        return null;
    }

    public static int[] canDeploySleepingBag(World world, EntityPlayer player, int x, int y, int z, boolean isTile)
    {
        int switchBy = -1;
        if (isTile)
        {
            BlockPos pos = new BlockPos(x, y, z);
            TileBackpack te = (TileBackpack) world.getTileEntity(pos);
            if (!te.isSleepingBagDeployed())
                //TODO confirm we are checking the right direction (c) Phil
                switchBy = world.getBlockState(pos).getValue(BlockHorizontal.FACING).ordinal();
                //switchBy = world.getBlockMetadata(x, y, z) & 3;
        }
        else
        {
            int playerDirection = MathHelper.floor((double) ((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            int[] tileSequence = {2, 3, 0, 1};
            for (int i = 0; i < tileSequence.length; i++) // converts to use isTile format
            {
                if (playerDirection == i)
                {
                    switchBy = tileSequence[i];
                    break;
                }
            }
        }
        return getDirectionAndCoordsForSleepingBag(switchBy, world, x, y, z);
    }

    private static int[] getDirectionAndCoordsForSleepingBag(int switchBy, World world, int x, int y, int z)
    {
        int direction = -1;
        switch (switchBy)
        {
            case 0:
                --z;
                if (isAirAboveSolid(world, x, y, z) && isAirAboveSolid(world, x, y, z - 1))
                    direction = 2;
                break;
            case 1:
                ++x;
                if (isAirAboveSolid(world, x, y, z) && isAirAboveSolid(world, x + 1, y, z))
                    direction = 3;
                break;
            case 2:
                ++z;
                if (isAirAboveSolid(world, x, y, z) && isAirAboveSolid(world, x, y, z + 1))
                    direction = 0;
                break;
            case 3:
                --x;
                if (isAirAboveSolid(world, x, y, z) && isAirAboveSolid(world, x - 1, y, z))
                    direction = 1;
                break;
            default:
                break;
        }
        return new int[]{direction, x, y, z};
    }

    private static boolean isAirAboveSolid(World world, int x, int y, int z)
    {
        return world.isAirBlock(new BlockPos(x, y, z))
                && world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial().isSolid();
    }

    public static boolean spawnSleepingBag(EntityPlayer player, World world, int meta, int x, int y, int z)
    {
        Block sleepingBag = ModBlocks.SLEEPING_BAG_BLOCK;
        BlockPos pos = new BlockPos(x, y, z);
        if (world.setBlockState(pos, sleepingBag.getDefaultState(), 3)) //TODO meta = getDefaultState() ?
        //if (world.setBlock(x, y, z, sleepingBag, meta, 3))
        {
            world.playSound(player, pos, SoundEvents.BLOCK_CLOTH_PLACE, SoundCategory.BLOCKS, 0.5f, 1.0f);
            switch (meta & 3)
            {
                case 0:
                    ++z;
                    break;
                case 1:
                    --x;
                    break;
                case 2:
                    --z;
                    break;
                case 3:
                    ++x;
                    break;
            }
            return world.setBlockState(new BlockPos(x, y, z), sleepingBag.getDefaultState(), 3); //TODO or (meta+8) = getDefaultState() ?
            //return world.setBlock(cX, cY, cZ, sleepingBag, meta + 8, 3);
        }
        return false;
    }

    /**
     * Compares two coordinates. Heh.
     *
     * @param x1 First coordinate X.
     * @param y1 First coordinate Y.
     * @param z1 First coordinate Z.
     * @param x2 Second coordinate X.
     * @param y2 Second coordinate Y.
     * @param z2 Second coordinate Z. I really didn't need to type all that, its obvious.
     * @return If both coordinates are the same, returns true. This is the least helpful javadoc ever.
     */
    private static boolean areCoordinatesTheSame(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        return (x1 == x2 && y1 == y2 && z1 == z2);
    }

    private static boolean areCoordinatesTheSame2D(int x1, int z1, int x2, int z2)
    {
        return (x1 == x2 && z1 == z2);
    }
}
