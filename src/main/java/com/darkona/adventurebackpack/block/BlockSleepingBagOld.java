package com.darkona.adventurebackpack.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;

/**
 * Created on 14/10/2014
 *
 * @author Darkona
 */
public class BlockSleepingBagOld extends BlockDirectional
{
//    private static final int[][] footBlockToHeadBlockMap = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
//    private static final AxisAlignedBB SLEEPING_BAG_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
//
//    private static final String TAG_STORED_SPAWN = "storedSpawn";
//    private static final String TAG_SPAWN_POS_X = "posX";
//    private static final String TAG_SPAWN_POS_Y = "posY";
//    private static final String TAG_SPAWN_POS_Z = "posZ";
//
    public BlockSleepingBagOld()
    {
        super(Material.CLOTH);
        setUnlocalizedName(getUnlocalizedName());
    }
//
//    @Override
//    public String getUnlocalizedName()
//    {
//        return "blockSleepingBag";
//    }
//
//
//    @Override
//    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
//    {
//        return SLEEPING_BAG_AABB;
//    }
//
//    /**
//     * Returns whether or not this bed block is the head of the bed.
//     */
//    private static boolean isBlockHeadOfBed(int meta)
//    {
//        return (meta & 8) != 0;
//    }
//
//    public static boolean isSleepingInPortableBag(EntityPlayer player)
//    {
//        return Wearing.isWearingBackpack(player)
//                && Wearing.getWearingBackpackInv(player).getExtendedProperties().hasKey(Constants.TAG_SLEEPING_IN_BAG);
//    }
//
//    public static void packPortableSleepingBag(EntityPlayer player)
//    {
//        if (isSleepingInPortableBag(player))
//        {
//            InventoryBackpack inv = Wearing.getWearingBackpackInv(player);
//            inv.removeSleepingBag(player.world);
//            inv.getExtendedProperties().removeTag(Constants.TAG_SLEEPING_IN_BAG);
//        }
//    }
//
//    public static void storeOriginalSpawn(EntityPlayer player, NBTTagCompound tag)
//    {
//        BlockPos spawn = player.getBedLocation(player.world.provider.getDimension());
//        NBTTagCompound storedSpawn = new NBTTagCompound();
//        storedSpawn.setInteger(TAG_SPAWN_POS_X, spawn.getX());
//        storedSpawn.setInteger(TAG_SPAWN_POS_Y, spawn.getY());
//        storedSpawn.setInteger(TAG_SPAWN_POS_Z, spawn.getZ());
//        tag.setTag(TAG_STORED_SPAWN, storedSpawn);
//        LogHelper.info("Stored spawn data for " + player.getDisplayName() + ": " + spawn.toString()
//                + " dimID: " + player.world.provider.getDimension());
//    }
//
//    public static void restoreOriginalSpawn(EntityPlayer player, NBTTagCompound tag)
//    {
//        if (tag.hasKey(TAG_STORED_SPAWN))
//        {
//            NBTTagCompound storedSpawn = tag.getCompoundTag(TAG_STORED_SPAWN);
//            BlockPos coords = new BlockPos(
//                    storedSpawn.getInteger(TAG_SPAWN_POS_X),
//                    storedSpawn.getInteger(TAG_SPAWN_POS_Y),
//                    storedSpawn.getInteger(TAG_SPAWN_POS_Z));
//            player.setSpawnChunk(coords, false, player.world.provider.getDimension());
//            tag.removeTag(TAG_STORED_SPAWN);
//            LogHelper.info("Restored spawn data for " + player.getDisplayName() + ": " + coords.toString()
//                    + " dimID: " + player.world.provider.getDimension());
//        }
//        else
//        {
//            LogHelper.warn("No spawn data to restore for " + player.getDisplayName());
//        }
//    }
//
//    public void onPortableBlockActivated(World world, EntityPlayer player, int cX, int cY, int cZ)
//    {
//        if (world.isRemote)
//            return;
//        if (!isSleepingInPortableBag(player))
//            return;
//
//        if (!onBlockActivated(world, cX, cY, cZ, player, 1, 0f, 0f, 0f))
//            packPortableSleepingBag(player);
//    }
//
//    @Override
//    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int id, float f1, float f2, float f3)
//    {
//        if (world.isRemote)
//        {
//            return true;
//        }
//        else
//        {
//            int meta = world.getBlockMetadata(x, y, z);
//
//            if (!isBlockHeadOfBed(meta))
//            {
//                int dir = getDirection(meta);
//                x += footBlockToHeadBlockMap[dir][0];
//                z += footBlockToHeadBlockMap[dir][1];
//
//                if (world.getBlock(x, y, z) != this)
//                {
//                    return false;
//                }
//
//                meta = world.getBlockMetadata(x, y, z);
//            }
//
//            if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell)
//            {
//                if (isBedOccupied(meta))
//                {
//                    EntityPlayer entityplayer1 = null;
//                    Iterator iterator = world.playerEntities.iterator();
//
//                    while (iterator.hasNext())
//                    {
//                        EntityPlayer entityplayer2 = (EntityPlayer) iterator.next();
//
//                        if (entityplayer2.isPlayerSleeping())
//                        {
//                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;
//
//                            if (chunkcoordinates.posX == x && chunkcoordinates.posY == y && chunkcoordinates.posZ == z)
//                            {
//                                entityplayer1 = entityplayer2;
//                            }
//                        }
//                    }
//
//                    if (entityplayer1 != null)
//                    {
//                        player.sendMessage(new TextComponentTranslation("tile.bed.occupied", new Object[0]));
//                        return false;
//                    }
//
//                    setBedOccupied(world, x, y, z, false);
//                }
//
//                EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);
//
//                if (enumstatus == EntityPlayer.EnumStatus.OK)
//                {
//                    setBedOccupied(world, x, y, z, true);
//                    //This is so the wake up event can detect it. It fires before the player wakes up.
//                    //and the bed location isn't set until then, normally.
//
//                    if (isSleepingInPortableBag(player))
//                    {
//                        storeOriginalSpawn(player, Wearing.getWearingBackpackInv(player).getExtendedProperties());
//                        player.setSpawnChunk(new ChunkCoordinates(x, y, z), true, player.dimension);
//                    }
//                    else
//                    {
//                        player.setSpawnChunk(new ChunkCoordinates(x, y, z), true, player.dimension);
//                        LogHelper.info("Looking for a campfire nearby...");
//                        ChunkCoordinates campfire = CoordsUtils.findBlock3D(world, x, y, z, ModBlocks.BLOCK_CAMP_FIRE, 8, 2);
//                        if (campfire != null)
//                        {
//                            LogHelper.info("Campfire Found, saving coordinates. " + campfire.toString());
//                            BackpackProperty.get(player).setCampFire(campfire);
//                        }
//                    }
//                    return true;
//                }
//                else
//                {
//                    if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)
//                    {
//                        player.sendMessage(new TextComponentTranslation("tile.bed.noSleep", new Object[0]));
//                    }
//                    else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE)
//                    {
//                        player.sendMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]));
//                    }
//
//                    return false;
//                }
//            }
//            else
//            {
//                double d2 = (double) x + 0.5D;
//                double d0 = (double) y + 0.5D;
//                double d1 = (double) z + 0.5D;
//                world.setBlockToAir(x, y, z);
//                int k1 = getDirection(meta);
//                x += footBlockToHeadBlockMap[k1][0];
//                z += footBlockToHeadBlockMap[k1][1];
//
//                if (world.getBlock(x, y, z) == this)
//                {
//                    world.setBlockToAir(x, y, z);
//                    d2 = (d2 + (double) x + 0.5D) / 2.0D;
//                    d0 = (d0 + (double) y + 0.5D) / 2.0D;
//                    d1 = (d1 + (double) z + 0.5D) / 2.0D;
//                }
//
//                world.newExplosion((Entity) null, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), 5.0F, true, true);
//
//                return false;
//            }
//        }
//    }
//
//    private static void setBedOccupied(World world, int x, int y, int z, boolean flag)
//    {
//        int l = world.getBlockMetadata(x, y, z);
//
//        if (flag)
//        {
//            l |= 4;
//        }
//        else
//        {
//            l &= -5;
//        }
//
//        world.setBlockMetadataWithNotify(x, y, z, l, 4);
//    }
//
//    private static boolean isBedOccupied(int meta)
//    {
//        return (meta & 4) != 0;
//    }
//
//    @Override
//    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
//    {
//        int meta = world.getBlockMetadata(x, y, z);
//        int dir = getDirection(meta);
//
//        if (isBlockHeadOfBed(meta))
//        {
//            if (world.getBlock(x - footBlockToHeadBlockMap[dir][0], y, z - footBlockToHeadBlockMap[dir][1]) != this)
//            {
//                world.setBlockToAir(x, y, z);
//            }
//        }
//        else if (world.getBlock(x + footBlockToHeadBlockMap[dir][0], y, z + footBlockToHeadBlockMap[dir][1]) != this)
//        {
//            world.setBlockToAir(x, y, z);
//
//            if (!world.isRemote)
//            {
//                this.dropBlockAsItem(world, x, y, z, meta, 0);
//            }
//        }
//    }
//
//    @Override
//    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
//    {
//        this.blockBoundsForRender();
//    }
//
//    private void blockBoundsForRender()
//    {
//        this.func_149978_e();
//    }
//
//    @Override
//    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
//    {
//        return null;
//    }
//
//    @Override
//    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
//    {
//        int direction = getDirection(meta);
//        if (player.capabilities.isCreativeMode && isBlockHeadOfBed(meta))
//        {
//            x -= footBlockToHeadBlockMap[direction][0];
//            z -= footBlockToHeadBlockMap[direction][1];
//
//            if (world.getBlock(x, y, z) == this)
//            {
//                world.setBlockToAir(x, y, z);
//            }
//        }
//    }
//
//    @Override
//    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion boom)
//    {
//        this.onBlockDestroyedByPlayer(world, x, y, z, world.getBlockMetadata(x, y, z));
//    }
//
//    @Override
//    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
//    {
//        //TODO make it work if player destroyed head block of sleeping bag (so backpack 1 more tile away)
//        //LogHelper.info("onBlockDestroyedByPlayer() : BlockSleepingBagOld");
//        int direction = getDirection(meta);
//        int tileZ = z;
//        int tileX = x;
//        switch (meta)
//        {
//            case 0:
//                tileZ--;
//                break;
//            case 1:
//                tileX++;
//                break;
//            case 2:
//                tileZ++;
//                break;
//            case 3:
//                tileX--;
//                break;
//        }
//        //LogHelper.info("onBlockDestroyedByPlayer() Looking for tile entity in x=" +tileX+" y="+y+" z="+tileZ+" while breaking the block in x= "+x+" y="+y+" z="+z);
//        if (world.getTileEntity(tileX, y, tileZ) != null && world.getTileEntity(tileX, y, tileZ) instanceof TileBackpack)
//        {
//            // LogHelper.info("onBlockDestroyedByPlayer() Found the tile entity in x=" +tileX+" y="+y+" z="+z+" while breaking the block in x= "+x+" y="+y+" z="+z+" ...removing.");
//            ((TileBackpack) world.getTileEntity(tileX, y, tileZ)).setSleepingBagDeployed(false);
//        }
//    }
//
//    @Override
//    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player)
//    {
//        return true;
//    }
//
//    @Override
//    public int getRenderType()
//    {
//        return 14;
//    }
//
//    @Override
//    public boolean isNormalCube()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isBlockNormalCube()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean renderAsNormalBlock()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube()
//    {
//        return false;
//    }
}
