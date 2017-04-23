package com.darkona.adventurebackpack.item;

import java.util.List;

import com.darkona.adventurebackpack.entity.EntityInflatableBoat;
import com.darkona.adventurebackpack.block.BlockSleepingBag;
import com.darkona.adventurebackpack.init.ModBlocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created on 11/10/2014
 *
 * @author Darkona
 */
public class ItemComponent extends ItemAB
{

    private String[] names = {
            "sleepingBag",
            "backpackTank",
            "hoseHead",
            "macheteHandle",
            "copterEngine",
            "copterBlades",
            "inflatableBoat",
            "inflatableBoatMotorized",
            "hydroBlades",
            "adventureBackpack",
    };

    public ItemComponent()
    {
        setNoRepair();
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(16);
        this.setUnlocalizedName("backpackComponent");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {

        int meta = stack.getMetadata();
    
        if (meta < 0 || meta >= 10)
        {
            meta = 0;
        }

        return super.getUnlocalizedName(names[meta]);
    }



    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int i = 1; i <= names.length; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (itemStack.getItemDamage() != 1)
        {
            return EnumActionResult.SUCCESS;
        }
        if (world.isRemote)
        {
            return EnumActionResult.SUCCESS;
        } else if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        } else
        {
            pos.up();
            BlockSleepingBag blockbed = ModBlocks.blockSleepingBag;
            int i1 = MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte b0 = 0;
            byte b1 = 0;
            if (i1 == 0)
            {
                b1 = 1;
            }
            if (i1 == 1)
            {
                b0 = -1;
            }
            if (i1 == 2)
            {
                b1 = -1;
            }
            if (i1 == 3)
            {
                b0 = 1;
            }
            if (player.canPlayerEdit(pos, facing, itemStack) && player.canPlayerEdit(new BlockPos(pos.getX() + b0, pos.getY(), pos.getZ() + b1), facing, itemStack))
            {
                if (world.isAirBlock(pos) &&
                    world.isAirBlock(new BlockPos(pos.getX() + b0, pos.getY(), pos.getZ() + b1)) &&
                    world.isSideSolid(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), EnumFacing.UP) &&
                    world.isSideSolid(new BlockPos(pos.getX() + b0, pos.getY() - 1, pos.getZ() + b1), EnumFacing.UP))
                {
                    world.setBlockState(pos, blockbed.getDefaultState());
                    if (world.getBlockState(pos).getBlock() == blockbed)
                    {
                        world.setBlockState(new BlockPos(pos.getX() + b0, pos.getY(), pos.getZ() + b1), blockbed.getDefaultState());
                    }
                    --itemStack.stackSize;
                    return EnumActionResult.SUCCESS;
                } else
                {
                    return EnumActionResult.FAIL;
                }
            } else
            {
                return EnumActionResult.FAIL;
            }
        }
    }

    private ItemStack placeBoat(ItemStack stack, World world, EntityPlayer player, boolean motorized)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f + 1.62D - (double) player.renderOffsetY;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3d Vec3d = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3d Vec3d1 = Vec3d.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        RayTraceResult movingobjectposition = world.rayTraceBlocks(Vec3d, Vec3d1, true);

        if (movingobjectposition == null)
        {
            return stack;
        } else
        {
            Vec3d Vec3d2 = player.getLook(f);
            boolean flag = false;
            float f9 = 1.0F;
            List list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().addCoord(Vec3d2.xCoord * d3, Vec3d2.yCoord * d3, Vec3d2.zCoord * d3).expand((double) f9, (double) f9, (double) f9));
            int i;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity) list.get(i);

                if (entity.canBeCollidedWith())
                {
                    float f10 = entity.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double) f10, (double) f10, (double) f10);

                    if (axisalignedbb.isVecInside(Vec3d))
                    {
                        flag = true;
                    }
                }
            }

            if (flag)
            {
                return stack;
            } else
            {
                if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    i = movingobjectposition.getBlockPos().getX();
                    int j = movingobjectposition.getBlockPos().getY();
                    int k = movingobjectposition.getBlockPos().getZ();

                    if (world.getBlockState(new BlockPos(i, j, k)) == Blocks.SNOW_LAYER)
                    {
                        --j;
                    }

                    EntityInflatableBoat inflatableBoat = new EntityInflatableBoat(world, i + 0.5, j + 1.0, k + 0.5, motorized);

                    inflatableBoat.rotationYaw = (float) (((MathHelper.floor((double) (player.rotationYaw * 4.0 / 360.0) + 0.5D) & 3) - 1) * 90);
                    if (!world.getCollisionBoxes
                    (inflatableBoat, inflatableBoat.getEntityBoundingBox().expand(-0.1, -0.1, -0.1)).isEmpty())
                    {
                        return stack;
                    }

                    if (!world.isRemote)
                    {
                        world.spawnEntity(inflatableBoat);
                    }

                    if (!player.capabilities.isCreativeMode)
                    {
                        --stack.stackSize;
                    }
                }
                return stack;
            }
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     *
     * @param stack
     * @param world
     * @param player
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if (stack.getItemDamage() == 7) return new ActionResult(EnumActionResult.PASS, placeBoat(stack, world, player, false));
        if (stack.getItemDamage() == 8) return new ActionResult(EnumActionResult.PASS, placeBoat(stack, world, player, true));
        return new ActionResult(EnumActionResult.PASS, stack);
    }
}