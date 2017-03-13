package com.darkona.adventurebackpack.block;

import java.util.Random;
import javax.annotation.Nullable;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.client.Icons;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.handlers.GuiHandler;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.BackpackNames;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.Utils;

import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

/**
 * Created on 12/10/2014.
 *
 * @author Javier Darkona
 */
@SuppressWarnings("unused")
public class BlockAdventureBackpack extends BlockContainer
{

    public BlockAdventureBackpack()
    {
        super(new BackpackMaterial());
        setHardness(1.0f);
        //setStepSound(soundTypeCloth);
        setResistance(2000f);
    }

    /**
     * Pretty effects for the bookshelf ;)
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @param random
     */
    /**
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (getAssociatedTileColorName(world, x, y, z).equals("Bookshelf"))
        {
            ChunkCoordinates enchTable = Utils.findBlock3D(world, x, y, z, Blocks.enchanting_table, 2, 2);
            if (enchTable != null)
            {
                if (!world.isAirBlock((enchTable.posX - x) / 2 + x, enchTable.posY, (enchTable.posZ - z) / 2 + z))
                {
                    return;
                }
                for (int o = 0; o < 4; o++)
                {
                    world.spawnParticle("enchantmenttable", enchTable.posX + 0.5D, enchTable.posY + 2.0D, enchTable.posZ + 0.5D,
                            ((x - enchTable.posX) + random.nextFloat()) - 0.5D,
                            ((y - enchTable.posY) - random.nextFloat() - 1.0F),
                            ((z - enchTable.posZ) + random.nextFloat()) - 0.5D);
                }
            }
        }
    }
    **/

    private String getAssociatedTileColorName(IBlockAccess world, BlockPos pos)
    {
        final TileEntity tile = world.getTileEntity(pos);
        return (tile instanceof TileAdventureBackpack) ? ((TileAdventureBackpack) tile).getColorName() : "error";
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        return getAssociatedTileColorName(world, pos).equals("Bookshelf") ? 10 : 0;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 0;
    }


    /**
     * Called when a player hits the block. Args: world, x, y, z, player
     *
     * @param p_149699_1_
     * @param p_149699_2_
     * @param p_149699_3_
     * @param p_149699_4_
     * @param p_149699_5_
     */
    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        super.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }

    /**
     * TODO: rendering code
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        Icons.milkStill = iconRegister.registerIcon(ModInfo.MOD_ID + ":fluid.milk");
        Icons.melonJuiceStill = iconRegister.registerIcon(ModInfo.MOD_ID + ":fluid.melonJuiceStill");
        Icons.melonJuiceFlowing = iconRegister.registerIcon(ModInfo.MOD_ID + ":fluid.melonJuiceFlowing");
        Icons.mushRoomStewStill = iconRegister.registerIcon(ModInfo.MOD_ID + ":fluid.mushroomStewStill");
        Icons.mushRoomStewFlowing = iconRegister.registerIcon(ModInfo.MOD_ID + ":fluid.mushroomStewFlowing");
    }
    **/

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (getAssociatedTileColorName(world, pos).equals("Glowstone"))
        {
            return 15;
        } else if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileAdventureBackpack)
        {
            return ((TileAdventureBackpack) world.getTileEntity(pos)).getLuminosity();
        } else
        {
            return 0;
        }
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return getAssociatedTileColorName(world, pos).equals("Redstone");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            Integer currentDimID = (world.provider.getDimension());
            for (String id : ConfigHandler.forbiddenDimensions)
            {
                if (id.equals(currentDimID.toString())) return false;
            }

            FMLNetworkHandler.openGui(player, AdventureBackpack.instance, GuiHandler.BACKPACK_TILE, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        ItemStack backpack = new ItemStack(ModItems.adventureBackpack, 1);
        BackpackNames.setBackpackColorNameFromDamage(backpack, BackpackNames.getBackpackDamageFromName(getAssociatedTileColorName(world, pos)));
        return backpack;
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }


    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        /**
         * todo get the math helper function to work
        int dir = MathHelper.floor_double((placer.rotationYaw * 4F) / 360F + 0.5D) & 3;
        if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey("color"))
        {
            if (stack.stackTagCompound.getString("color").contains("BlockRedstone"))
            {
                dir = dir | 8;
            }
            if (stack.stackTagCompound.getString("color").contains("Lightgem"))
            {
                dir = dir | 4;
            }
        }
        world.setBlockMetadataWithNotify(pos, dir, 3);
        createNewTileEntity(world, world.getBlockMetadata(pos));
        */
    }

    /**
     * TODO: more rendering dode
     Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return Blocks.wool.getIcon(par1, par2);
    }
    */

    /**
     * TODO: do i need this
    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3d start, Vec3d end)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, start, end);
    }

    */

    @Override
    public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, @Nullable ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileAdventureBackpack && !world.isRemote && player != null)
        {
            if ((player.isSneaking()) ?
                    //TODO: update to blockpos
                    ((TileAdventureBackpack) tile).equip(world, player, pos.getX(), pos.getY(), pos.getZ()) :
                    ((TileAdventureBackpack) tile).drop(world, player, pos.getX(), pos.getY(), pos.getZ()))
            {
                return world.destroyBlock(pos, false);
            }
        } else
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
                ItemStack stack = inventory.getStackInSlot(i);

                if (stack != null)
                {
                    float spawnX = pos.getX() + world.rand.nextFloat();
                    float spawnY = pos.getY() + world.rand.nextFloat();
                    float spawnZ = pos.getZ() + world.rand.nextFloat();
                    float mult = 0.05F;

                    EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);

                    droppedItem.motionX = -0.5F + world.rand.nextFloat() * mult;
                    droppedItem.motionY = 4 + world.rand.nextFloat() * mult;
                    droppedItem.motionZ = -0.5 + world.rand.nextFloat() * mult;

                    //TODO: make this entitiy spawn
                    //world.spawnEntityInWorld((Entity) droppedItem);
                }
            }

        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileAdventureBackpack();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileAdventureBackpack();
    }

    @Override
    public boolean canDropFromExplosion(Explosion p_149659_1_)
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
