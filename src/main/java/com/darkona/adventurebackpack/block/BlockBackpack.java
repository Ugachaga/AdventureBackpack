package com.darkona.adventurebackpack.block;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.CoordsUtils;

import static com.darkona.adventurebackpack.reference.BackpackTypes.BOOKSHELF;
import static com.darkona.adventurebackpack.reference.BackpackTypes.CACTUS;
import static com.darkona.adventurebackpack.reference.BackpackTypes.GLOWSTONE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.REDSTONE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.UNKNOWN;

public class BlockBackpack extends Block //TODO extends BlockHorizontal ?
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.4D, 0.0D, 0.0D, 0.6D, 0.6D, 1.0D);
    private static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4D, 1.0D, 0.6D, 0.6D);

    //IBlockState immutable and pregenerated, so maybe we should avoid to use it for Types
    public static final PropertyEnum<BackpackTypes> TYPE = PropertyEnum.create("type", BackpackTypes.class);

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return super.getActualState(state, world, pos); //TODO
    }

//    @Override
//    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos)
//    {
//        return super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
//    }



    public BlockBackpack(String name)
    {
        super(new BackpackMaterial());
        AdventureBlock.setBlockName(this, name);

        this.setSoundType(SoundType.CLOTH);
        this.setHardness(1.0f);
        this.setResistance(2000f);

        this.setDefaultState(blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                //.withProperty(TYPE, BackpackTypes.STANDARD)
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
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
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Pretty effects for the bookshelf ;)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rnd)
    {
        if (getAssociatedTileBackpackType(world, pos) == BOOKSHELF)
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            BlockPos enchTable = CoordsUtils.findBlock3D(world, x, y, z, Blocks.ENCHANTING_TABLE, 2, 2);
            if (enchTable != null)
            {
                if (!world.isAirBlock(new BlockPos((enchTable.getX() - x) / 2 + x, enchTable.getY(), (enchTable.getZ() - z) / 2 + z)))
                    return;

                for (int o = 0; o < 4; o++)
                {
                    world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, enchTable.getX() + 0.5D, enchTable.getY() + 2.0D, enchTable.getZ() + 0.5D,
                            ((x - enchTable.getX()) + rnd.nextFloat()) - 0.5D, ((y - enchTable.getY()) - rnd.nextFloat() - 1.0F), ((z - enchTable.getZ()) + rnd.nextFloat()) - 0.5D);
                }
            }
        }
    }

    private BackpackTypes getAssociatedTileBackpackType(IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        return tile instanceof TileBackpack ? ((TileBackpack) tile).getType() : UNKNOWN;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion)
    {
        world.destroyBlock(pos, false); //TODO redundant?
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
//        if (!world.isRemote && GeneralReference.isDimensionAllowed(player))
//        {
//            TileBackpack tileEntity = (TileBackpack) world.getTileEntity(pos);
//            if (tileEntity != null)
//                tileEntity.openGUI(world, player);
//        }
//        return true;

        if (!world.isRemote /*&& GeneralReference.isDimensionAllowed(player)*/)
        {
            System.out.println("here");
            if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBackpack)
            {
                //TODO
                ((EntityPlayerMP) player).getServerWorld().addScheduledTask(() -> AdventureBackpack.PROXY
                        .displayBackpackGUI(player, (TileBackpack) world.getTileEntity(pos), Constants.Source.TILE));
                return true;
            }
        }

        return false;

    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return getAssociatedTileBackpackType(world, pos) == REDSTONE ? 15 : 0;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (getAssociatedTileBackpackType(world, pos) == CACTUS)
        {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
        //world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 3);
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        List<ItemStack> backpacks = Stream.of(BackpackTypes.values())
                .filter(type -> type != BackpackTypes.UNKNOWN)
                .map(BackpackUtils::createBackpackStack)
                .collect(Collectors.toList());

        items.addAll(backpacks);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (getAssociatedTileBackpackType(world, pos) == GLOWSTONE)
            return 15;

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileBackpack)
            return ((TileBackpack) te).getLuminosity();

        return 0;
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean harvest)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileBackpack && !world.isRemote)
        {
            if (player.isSneaking()
                ? ((TileBackpack) tile).equip(world, player, pos)
                : ((TileBackpack) tile).drop(world, player, pos))
            {
                return world.destroyBlock(pos, false);
            }
        }
        else
        {
            return world.destroyBlock(pos, false);
        }
        return false;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 0;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileBackpack();
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
    {
        return false;
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        //DO NOTHING
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
    {
        return getAssociatedTileBackpackType(world, pos) == REDSTONE;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return BackpackUtils.createBackpackStack(getAssociatedTileBackpackType(world, pos));
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
    {
        return false;
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        return getAssociatedTileBackpackType(world, pos) == BOOKSHELF ? 10 : 0;
    }

    @Nullable
    @Override
    public String getHarvestTool(IBlockState state)
    {
        return null; // anything can harvest this block
    }

    @Override
    public int getHarvestLevel(IBlockState state)
    {
        return 0;
    }

    @Override
    public boolean isToolEffective(String type, IBlockState state)
    {
        return true;
    }
}
