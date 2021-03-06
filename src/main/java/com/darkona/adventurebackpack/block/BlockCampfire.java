package com.darkona.adventurebackpack.block;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.CoordsUtils;

public class BlockCampfire extends Block
{
    private static final AxisAlignedBB CAMPFIRE_AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.15, 0.8);

    public BlockCampfire(String name)
    {
        super(Material.ROCK);
        AdventureBlock.setBlockName(this, name);

        this.setCreativeTab(ModInfo.CREATIVE_TAB);
        this.setTickRandomly(true);
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getLightValue(IBlockState state)
    {
        return 11;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CAMPFIRE_AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rnd)
    {
        float rndX = pos.getX() + rnd.nextFloat();
        float rndY = (pos.getY() + 1) - rnd.nextFloat() * 0.1F;
        float rndZ = pos.getZ() + rnd.nextFloat();
        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, rndX, rndY, rndZ, 0.0D, 0.0D, 0.0D);
        for (int i = 0; i < 4; i++)
        {
            rndX = pos.getX() + 0.5f - (float) rnd.nextGaussian() * 0.08f;
            rndY = (float) (pos.getY() + 1f - Math.cos((float) rnd.nextGaussian() * 0.1f));
            rndZ = pos.getZ() + 0.5f - (float) rnd.nextGaussian() * 0.08f;
            world.spawnParticle(EnumParticleTypes.FLAME, rndX, rndY + 0.16, rndZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int tickRate(World world)
    {
        return 30;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileCampfire();
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
    {
        return true;
    }

    @Nullable
    @Override
    public BlockPos getBedSpawnPosition(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityPlayer player)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int i = y - 5; i <= y + 5; i++)
        {
            BlockPos spawn = CoordsUtils.getNearestEmptyChunkCoordinatesSpiral(world, x, z, x, i, z, 8, true, 1, (byte) 0, true);
            if (spawn != null)
                return spawn;
        }
        return null;
    }
}
