package com.darkona.adventurebackpack.block;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.CoordsUtils;

import static com.darkona.adventurebackpack.reference.BackpackTypes.BOOKSHELF;
import static com.darkona.adventurebackpack.reference.BackpackTypes.CACTUS;
import static com.darkona.adventurebackpack.reference.BackpackTypes.GLOWSTONE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.REDSTONE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.UNKNOWN;

/**
 * Created on 12/10/2014.
 *
 * @author Javier Darkona
 */
public class BlockBackpack extends Block
{
    public static final PropertyDirection FACING_HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);



    //TODO see https://shadowfacts.net/tutorials/forge-modding-112/tile-entities-inventory/
    //IBlockState immutable and pregenerated, so maybe we should avoid to use it for Types
    //public static final PropertyEnum<BackpackTypes> TYPE = PropertyEnum.create();

//    @Override
//    protected BlockStateContainer createBlockState()
//    {
//        return new BlockStateContainer(this);
//    }
//
//    @Override
//    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
//    {
//        return super.getActualState(state, worldIn, pos);
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state)
//    {
//        return super.getMetaFromState(state);
//    }
//
//    @Override
//    public IBlockState getStateFromMeta(int meta)
//    {
//        return super.getStateFromMeta(meta);
//    }





    public BlockBackpack()
    {
        super(new BackpackMaterial());
        setSoundType(SoundType.CLOTH);
        setHardness(1.0f);
        setResistance(2000f);
        setRegistryName(ModInfo.MODID + ":" + getUnlocalizedName());


//        setDefaultState(blockState.getBaseState()
//                .withProperty(FACING_HORIZONTAL, EnumFacing.NORTH));
//                //.withProperty(TYPE, BackpackTypes.STANDARD));
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
                {
                    return;
                }
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
        final TileEntity tile = world.getTileEntity(pos);
        return tile instanceof TileBackpack ? ((TileBackpack) tile).getType() : UNKNOWN;
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

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return super.canRenderInLayer(state, layer); //TODO was: canRenderInPass(int pass) return true;
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
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
    {
        return false;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 0;
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
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
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }

    @Override
    public String getUnlocalizedName()
    {
        return "blockAdventureBackpack";
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    //public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        if (getAssociatedTileBackpackType(world, pos) == GLOWSTONE)
        {
            return 15;
        }
        else if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBackpack)
        {
            return ((TileBackpack) world.getTileEntity(pos)).getLuminosity();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return getAssociatedTileBackpackType(world, pos) == REDSTONE ? 15 : 0;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
    {
        return getAssociatedTileBackpackType(world, pos) == REDSTONE;
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

        if (!world.isRemote && GeneralReference.isDimensionAllowed(player))
        {
            if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBackpack)
            {
                //TODO
                ((EntityPlayerMP) player).getServerWorld().addScheduledTask(()
                        -> AdventureBackpack.proxy.displayBackpackGUI(player,
                        (TileBackpack) world.getTileEntity(pos), Constants.Source.TILE));
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return BackpackUtils.createBackpackStack(getAssociatedTileBackpackType(world, pos));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
//        IBlockState blockState = this.getActualState(state, source, pos);
//        EnumFacing facing = blockState.getValue(FACING_HORIZONTAL);
//        AxisAlignedBB aabb;
//        switch (facing)
//        {
//            case NORTH:
//            case SOUTH:
//                aabb = new AxisAlignedBB(0.0F, 0.0F, 0.4F, 1.0F, 0.6F, 0.6F);
//                break;
//            case EAST:
//            case WEST:
//            default: // wat?
//                aabb = new AxisAlignedBB(0.4F, 0.0F, 0.0F, 0.6F, 0.6F, 1.0F);
//                break;
//        }
//        return aabb;
        return new AxisAlignedBB(0.4F, 0.0F, 0.0F, 0.6F, 0.6F, 1.0F);
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED; //TODO not sure
    }


    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack)
    {
        //TODO what should it do?
//        int dir = MathHelper.floor((player.rotationYaw * 4F) / 360F + 0.5D) & 3;
//        if (stack != null && stack.getTagCompound() != null && stack.getTagCompound().hasKey("color"))
//        {
//            if (stack.getTagCompound().getString("color").contains("BlockRedstone"))
//            {
//                dir = dir | 8;
//            }
//            if (stack.getTagCompound().getString("color").contains("Lightgem"))
//            {
//                dir = dir | 4;
//            }
//        }
        world.setBlockState(pos, state, 3);
        createTileEntity(world, state);
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        getBoundingBox(state, world, pos);
        return super.getSelectedBoundingBox(state, world, pos);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        getBoundingBox(state, world, pos);
        return super.getCollisionBoundingBox(state, world, pos);

    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        getBoundingBox(state, world, pos);
        return super.collisionRayTrace(state, world, pos, start, end);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean harvest)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileBackpack && !world.isRemote)
        {
            if ((player.isSneaking())
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
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof IInventory)
        {
            IInventory inventory = (IInventory) te;
            for (int i = 0; i < inventory.getSizeInventory(); i++)
            {
                ItemStack stack = inventory.removeStackFromSlot(i);

                if (stack != ItemStack.EMPTY)
                {
                    float spawnX = pos.getX() + world.rand.nextFloat();
                    float spawnY = pos.getY() + world.rand.nextFloat();
                    float spawnZ = pos.getZ() + world.rand.nextFloat();
                    float mult = 0.05F;

                    EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);

                    droppedItem.motionX = -0.5F + world.rand.nextFloat() * mult;
                    droppedItem.motionY = 4 + world.rand.nextFloat() * mult;
                    droppedItem.motionZ = -0.5 + world.rand.nextFloat() * mult;

                    world.spawnEntity(droppedItem);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileBackpack();
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion)
    {
        world.destroyBlock(pos, false);
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        //DO NOTHING
    }
}
