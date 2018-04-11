package com.darkona.adventurebackpack.block.test;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.block.AdventureBlock;
import com.darkona.adventurebackpack.block.BackpackMaterial;
import com.darkona.adventurebackpack.handlers.GuiHandler;

public class BlockTest extends BlockAbstractTest<TileTest>
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.4D, 0.0D, 0.0D, 0.6D, 0.6D, 1.0D);
    private static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4D, 1.0D, 0.6D, 0.6D);

    public BlockTest(String name)
    {
        super(new BackpackMaterial());
        AdventureBlock.setBlockName(this, name);

        this.setSoundType(SoundType.CLOTH);
        this.setHardness(1.0f);
        this.setResistance(2000f);

        //this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public Class<TileTest> getTileEntityClass()
    {
        return TileTest.class;
    }

    @Override
    public TileTest createTileEntity(World world, IBlockState state)
    {
        return new TileTest();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileTest tile = getTileEntity(world, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            player.openGui(AdventureBackpack.INSTANCE, GuiHandler.TEST_TILE, world, pos.getX(), pos.getY(), pos.getZ());
            tile.markDirty();
//            else
//            {
//                ItemStack stack = itemHandler.getStackInSlot(0);
//                if (!stack.isEmpty())
//                {
//                    String localized = I18n.format(stack.getUnlocalizedName() + ".name");
//                    player.sendMessage(new TextComponentString(stack.getCount() + "x " + localized));
//                }
//                else
//                {
//                    player.sendMessage(new TextComponentString("Empty"));
//                }
//            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileTest tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty())
            {
                EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                world.spawnEntity(item);
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

}