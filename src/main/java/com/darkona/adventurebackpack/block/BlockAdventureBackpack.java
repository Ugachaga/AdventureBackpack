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

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created on 12/10/2014.
 *
 * @author Javier Darkona
 */
@SuppressWarnings("unused")
public class BlockAdventureBackpack extends BlockContainer
{

    public static final String name = "blockBackpack";

    public BlockAdventureBackpack()
    {
        super(new BackpackMaterial());
        setHardness(1.0f);
        setResistance(2000f);
    }

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
        int dir = MathHelper.floor((placer.rotationYaw * 4F) / 360F + 0.5D) & 3;
        if (stack != null && stack.getTagCompound() != null && stack.getTagCompound().hasKey("color"))
        {
            if (stack.getTagCompound().getString("color").contains("BlockRedstone"))
            {
                dir = dir | 8;
            }
            if (stack.getTagCompound().getString("color").contains("Lightgem"))
            {
                dir = dir | 4;
            }
        }
        world.setBlockState(pos, state, 3);
        createNewTileEntity(world, 0);
    }

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

                    world.spawnEntity((Entity) droppedItem);
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

    public void registerItemModel(ItemBlock itemBlock) {
		AdventureBackpack.proxy.registerItemRenderer(itemBlock, 0, name);
	}
}
