package com.darkona.adventurebackpack.block;

import java.util.Random;

import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.Utils;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created on 05/01/2015
 *
 * @author Darkona
 */
public class BlockCampFire extends BlockContainer
{
   // private IIcon icon;

    public BlockCampFire()
    {
        super(Material.ROCK);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabAB.ADVENTURE_BACKPACK_CREATIVE_TAB);

    }

    /**
     * TODO: rendering code

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        icon = iconRegister.registerIcon(ModInfo.MOD_ID + ":campFire");
    }
     */

    @Override
    public String getUnlocalizedName()
    {
        return "blockCampFire";
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_)
    {
        return new TileCampfire();
    }

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 30;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
        double rndX = pos.getX() + rand.nextFloat();
        double rndY = (pos.getY() + 1) - rand.nextFloat() * 0.1F;
        double rndZ = pos.getZ() + rand.nextFloat();
        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, rndX, rndY, rndZ, 0.0D, 0.0D, 0.0D);
        for (int i = 0; i < 4; i++)
        {
            rndX = pos.getX() + 0.5f - (double) rand.nextGaussian() * 0.08f;
            rndY = (double) (pos.getY() + 1f - Math.cos((double) rand.nextGaussian() * 0.1f));
            rndZ = pos.getX() + 0.5f - (double) rand.nextGaussian() * 0.08f;

            world.spawnParticle(EnumParticleTypes.FLAME, rndX, rndY + 0.16, rndZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileCampfire();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public int getLightValue(IBlockState state)
    {
        return 11;
    }

    /**
     * TODO: rendering code
    @Override
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
    {
        return icon;
    }
     */


    /**
     * Gets the block's texture. Args: side, meta
     *
     * @param p_149691_1_
     * @param p_149691_2_
     */
    /**
     * TODO: rendering code
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return icon;
    }
     */

    /**
     * Determines if this block is classified as a Bed, Allowing
     * players to sleep in it, though the block has to specifically
     * perform the sleeping functionality in it's activated event.
     *
     * @param world  The current world
     * @param x      X Position
     * @param y      Y Position
     * @param z      Z Position
     * @param player The player or camera entity, null in some cases.
     * @return True to treat this as a bed
     */
    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, Entity player)
    {
        return true;
    }
}
