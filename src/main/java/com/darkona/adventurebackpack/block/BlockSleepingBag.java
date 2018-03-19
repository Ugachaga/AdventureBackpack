package com.darkona.adventurebackpack.block;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.util.CoordsUtils;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Wearing;

public class BlockSleepingBag extends BlockBed
{
    private static final AxisAlignedBB SLEEPING_BAG_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);

    private static final String TAG_STORED_SPAWN = "storedSpawn";
    private static final String TAG_SPAWN_POS_X = "posX";
    private static final String TAG_SPAWN_POS_Y = "posY";
    private static final String TAG_SPAWN_POS_Z = "posZ";

    public BlockSleepingBag(String name)
    {
        AdventureBlock.setBlockName(this, name);
    }

    public static boolean isSleepingInPortableBag(EntityPlayer player)
    {
        return Wearing.isWearingBackpack(player)
                && Wearing.getWearingBackpackInv(player).getExtendedProperties().hasKey(Constants.TAG_SLEEPING_IN_BAG);
    }

    public static void packPortableSleepingBag(EntityPlayer player)
    {
        if (isSleepingInPortableBag(player))
        {
            InventoryBackpack inv = Wearing.getWearingBackpackInv(player);
            inv.removeSleepingBag(player.world);
            inv.getExtendedProperties().removeTag(Constants.TAG_SLEEPING_IN_BAG);
        }
    }

    public static void storeOriginalSpawn(EntityPlayer player, NBTTagCompound tag)
    {
        BlockPos spawn = player.getBedLocation(player.world.provider.getDimension());
        NBTTagCompound storedSpawn = new NBTTagCompound();
        storedSpawn.setInteger(TAG_SPAWN_POS_X, spawn.getX());
        storedSpawn.setInteger(TAG_SPAWN_POS_Y, spawn.getY());
        storedSpawn.setInteger(TAG_SPAWN_POS_Z, spawn.getZ());
        tag.setTag(TAG_STORED_SPAWN, storedSpawn);
        LogHelper.info("Stored spawn data for " + player.getDisplayName() + ": " + spawn.toString()
                + " dimID: " + player.world.provider.getDimension());
    }

    public static void restoreOriginalSpawn(EntityPlayer player, NBTTagCompound tag)
    {
        if (tag.hasKey(TAG_STORED_SPAWN))
        {
            NBTTagCompound storedSpawn = tag.getCompoundTag(TAG_STORED_SPAWN);
            BlockPos coords = new BlockPos(
                    storedSpawn.getInteger(TAG_SPAWN_POS_X),
                    storedSpawn.getInteger(TAG_SPAWN_POS_Y),
                    storedSpawn.getInteger(TAG_SPAWN_POS_Z));
            player.setSpawnChunk(coords, false, player.world.provider.getDimension());
            tag.removeTag(TAG_STORED_SPAWN);
            LogHelper.info("Restored spawn data for " + player.getDisplayName() + ": " + coords.toString()
                    + " dimID: " + player.world.provider.getDimension());
        }
        else
        {
            LogHelper.warn("No spawn data to restore for " + player.getDisplayName());
        }
    }

    public void onPortableBlockActivated(World world, EntityPlayer player, BlockPos pos)
    {
        if (world.isRemote)
            return;
        if (!isSleepingInPortableBag(player))
            return;

        if (!onBlockActivated(world, pos, this.getDefaultState(), player, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0f, 0f, 0f))
            packPortableSleepingBag(player);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            if (state.getValue(PART) != EnumPartType.HEAD)
            {
                pos = pos.offset(state.getValue(FACING));
                state = world.getBlockState(pos);

                if (state.getBlock() != this)
                {
                    return false;
                }
            }

            if (world.provider.canRespawnHere() && world.getBiome(pos) != Biomes.HELL)
            {
                if (state.getValue(OCCUPIED))
                {
                    EntityPlayer entityplayer = this.getPlayerInBed(world, pos);

                    if (entityplayer != null)
                    {
                        player.sendStatusMessage(new TextComponentTranslation("tile.bed.occupied"), true);
                        return false;
                    }

                    state = state.withProperty(OCCUPIED, Boolean.FALSE);
                    world.setBlockState(pos, state, 4);
                }

                EntityPlayer.SleepResult entityplayer$sleepresult = player.trySleep(pos);

                if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK)
                {
                    state = state.withProperty(OCCUPIED, Boolean.TRUE);
                    world.setBlockState(pos, state, 4);

                    if (isSleepingInPortableBag(player))
                    {
                        storeOriginalSpawn(player, Wearing.getWearingBackpackInv(player).getExtendedProperties());
                        player.setSpawnChunk(pos, true, player.dimension);
                    }
                    else //TODO campfire logic could be buggy, not checking dimension and so on
                    {
                        player.setSpawnChunk(pos, true, player.dimension);
                        LogHelper.info("Looking for a campfire nearby...");
                        BlockPos campfire = CoordsUtils.findBlock3D(world, pos.getX(), pos.getY(), pos.getZ(), ModBlocks.CAMPFIRE_BLOCK, 8, 2);
                        if (campfire != null)
                        {
                            LogHelper.info("Campfire Found, saving coordinates. " + campfire.toString());
                            BackpackProperty.get(player).setCampFire(campfire);
                        }
                    }

                    return true;
                }
                else
                {
                    if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW)
                        player.sendStatusMessage(new TextComponentTranslation("tile.bed.noSleep"), true);
                    else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE)
                        player.sendStatusMessage(new TextComponentTranslation("tile.bed.notSafe"), true);
                    else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY)
                        player.sendStatusMessage(new TextComponentTranslation("tile.bed.tooFarAway"), true);

                    return false;
                }
            }
            else
            {
                world.setBlockToAir(pos);
                BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

                if (world.getBlockState(blockpos).getBlock() == this)
                    world.setBlockToAir(blockpos);

                world.newExplosion(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, true);
                return false;
            }
        }
    }

    @Nullable
    private EntityPlayer getPlayerInBed(World world, BlockPos pos)
    {
        for (EntityPlayer entityplayer : world.playerEntities)
        {
            if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos))
                return entityplayer;
        }
        return null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.AIR);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SLEEPING_BAG_AABB;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        // nope
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        super.harvestBlock(world, player, pos, state, null, stack);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn)
    {
        onBlockDestroyedByPlayer(world, pos, world.getBlockState(pos));
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
        //TODO make it work if player destroyed head block of sleeping bag (so backpack 1 more tile away)
        EnumFacing facing = state.getValue(FACING);
        switch (facing)
        {
            case NORTH:
                pos.north(); //TODO check directions and pos methods
                break;
            case EAST:
                pos.east();
                break;
            case SOUTH:
                pos.south();
                break;
            case WEST:
                pos.west();
                break;
        }
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBackpack)
        {
            ((TileBackpack) world.getTileEntity(pos)).setSleepingBagDeployed(false);
        }
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
    {
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) //TODO del?
    {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) //TODO del?
    {
        return false;
    }
}
