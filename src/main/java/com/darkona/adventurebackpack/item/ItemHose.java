package com.darkona.adventurebackpack.item;

import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModFluids;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Darkona on 12/10/2014.
 */
public class ItemHose extends ItemAB
{

    //IIcon drinkIcon;
    //IIcon spillIcon;
    //IIcon suckIcon;
    final byte HOSE_SUCK_MODE = 0;
    final byte HOSE_SPILL_MODE = 1;
    final byte HOSE_DRINK_MODE = 2;

    public ItemHose()
    {
        super();
        setMaxStackSize(1);
        setFull3D();
        //.setCreativeTab(CreativeTabs.tabTools)
        setNoRepair();
        setUnlocalizedName("backpackHose");
        setCreativeTab(CreativeTabAB.ADVENTURE_BACKPACK_CREATIVE_TAB);
    }

    // ================================================ GETTERS  =====================================================//

    public static int getHoseMode(ItemStack hose)
    {
        return hose.getTagCompound() != null ? hose.getTagCompound().getInteger("mode") : -1;
    }

    public static int getHoseTank(ItemStack hose)
    {
        return hose.hasTagCompound() ? hose.getTagCompound().getInteger("tank") : -1;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("mode"))
        {
            return (stack.getTagCompound().getInteger("mode") == 2) ? EnumAction.DRINK : EnumAction.NONE;
        }
        return EnumAction.NONE;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        String name = "hose_" + (getHoseTank(stack) == 0 ? "leftTank" : getHoseTank(stack) == 1 ? "rightTank" : "");
        switch (getHoseMode(stack))
        {
            case 0:
                return super.getUnlocalizedName(name + "_suck");
            case 1:
                return super.getUnlocalizedName(name + "_spill");
            case 2:
                return super.getUnlocalizedName(name + "_drink");
            default:
                return super.getUnlocalizedName("hoseUseless");
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 24;
    }

    @Override
    public int getMaxDamage()
    {
        return 0;
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return 0;
    }

    // ================================================ SETTERS  =====================================================//


    // ================================================ ACTIONS  =====================================================//

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int inv_slot, boolean isCurrent)
    {
        if (entity == null || !(entity instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) entity;
        if (world.isRemote && player.getActiveItemStack() != null && player.getActiveItemStack().getItem().equals(this)) return;

        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        ItemStack backpack = Wearing.getWearingBackpack(player);
        if (backpack != null)
        {
            if (nbt.getInteger("tank") == -1) nbt.setInteger("tank", 0);
            if (nbt.getInteger("mode") == -1) nbt.setInteger("mode", 0);
            InventoryBackpack inv = new InventoryBackpack(backpack);
            FluidTank tank = nbt.getInteger("tank") == 0 ? inv.getLeftTank() : inv.getRightTank();
            if (tank != null && tank.getFluid() != null)
            {
                nbt.setString("fluid", Utils.capitalize(tank.getFluid().getFluid().getName()));
                nbt.setInteger("amount", tank.getFluidAmount());
            } else
            {
                nbt.setInteger("amount", 0);
                nbt.setString("fluid", "Empty");
            }
        } else
        {
            nbt.setInteger("amount", 0);
            nbt.setString("fluid", "None");
            nbt.setInteger("mode", -1);
            nbt.setInteger("tank", -1);
        }
        stack.setTagCompound(nbt);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!Wearing.isWearingBackpack(player)) return EnumActionResult.SUCCESS;

        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inv.openInventory(player);
        FluidTank tank = getHoseTank(stack) == 0 ? inv.getLeftTank() : inv.getRightTank();
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof IFluidHandler)
        {
            IFluidHandler exTank = (IFluidHandler) te;
            int accepted = 0;
            switch (getHoseMode(stack))
            {
                case HOSE_SUCK_MODE:

                    accepted = tank.fill(exTank.drain(EnumFacing.UP, Constants.bucket, false), false);
                    if (accepted > 0)
                    {
                        tank.fill(exTank.drain(EnumFacing.UP, accepted, true), true);
                        te.markDirty();
                        inv.dirtyTanks();
                        return EnumActionResult.SUCCESS;
                    }
                    break;

                case HOSE_SPILL_MODE:

                    accepted = exTank.fill(EnumFacing.UP, tank.drain(Constants.bucket, false), false);
                    if (accepted > 0)
                    {
                        exTank.fill(EnumFacing.UP, tank.drain(accepted, true), true);
                        te.markDirty();
                        inv.dirtyTanks();
                        return EnumActionResult.SUCCESS;
                    }
                    break;
            }
        }
        return EnumActionResult.FAIL;

    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return false;
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
        if (!Wearing.isWearingBackpack(player)) return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inv.openInventory(player);
        RayTraceResult mop = rayTrace(world, player, true);
        FluidTank tank = getHoseTank(stack) == 0 ? inv.getLeftTank() : inv.getRightTank();
        if (tank != null)
        {
            switch (getHoseMode(stack))
            {
                case HOSE_SUCK_MODE: // If it's in Suck Mode

                    if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK)
                    {
                        if (!player.canPlayerEdit(mop.getBlockPos(), mop.sideHit, null))
                        {
                            return new ActionResult(EnumActionResult.PASS, stack);
                        }
                        //TODO adjust for Adventure Mode
                        Fluid fluidBlock = FluidRegistry.lookupFluidForBlock(world.getBlockState(mop.getBlockPos()).getBlock());
                        if (fluidBlock != null)
                        {
                            FluidStack fluid = new FluidStack(fluidBlock, Constants.bucket);
                            if (tank.getFluid() == null || tank.getFluid().containsFluid(fluid))
                            {
                                int accepted = tank.fill(fluid, false);
                                if (accepted > 0)
                                {
                                    world.setBlockToAir(mop.getBlockPos());
                                    tank.fill(new FluidStack(fluidBlock, accepted), true);
                                }
                            }
                        }
                        inv.dirtyTanks();
                    }
                    break;

                case HOSE_SPILL_MODE: // If it's in Spill Mode
                    if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK)
                    {
                        if (world.getBlockState(mop.getBlockPos()).getBlock().isBlockSolid(world, mop.getBlockPos(), mop.sideHit))
                        {

                            switch (mop.sideHit)
                            {
                                case DOWN:
                                    mop.getBlockPos().down();
                                    break;
                                case UP:
                                    mop.getBlockPos().up();
                                    break;
                                case NORTH:
                                    mop.getBlockPos().north();
                                    break;
                                case SOUTH:
                                    mop.getBlockPos().south();
                                    break;
                                case EAST:
                                    mop.getBlockPos().east();
                                    break;
                                case WEST:
                                    mop.getBlockPos().west();
                                    break;
                            }
                        }
                        if (tank.getFluidAmount() > 0)
                        {
                            FluidStack fluid = tank.getFluid();
                            if (fluid != null)
                            {
                                if (fluid.getFluid().canBePlacedInWorld())
                                {
                                    Material material = world.getBlockState(mop.getBlockPos()).getMaterial();
                                    boolean flag = !material.isSolid();
                                    if (!world.isAirBlock(mop.getBlockPos()) && !flag)
                                    {
                                        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
                                                                        }
                                    /* IN HELL DIMENSION No, I won't let you put water in the nether. You freak*/
                                    if (world.provider.doesWaterVaporize() && fluid.getFluid() == FluidRegistry.WATER)
                                    {
                                        tank.drain(Constants.bucket, true);
                                        player.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                                        for (int l = 0; l < 12; ++l)
                                        {
                                            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ(), 0, 0, 0);
                                        }
                                    } else
                                    {
                                        /* NOT IN HELL DIMENSION. */
                                        FluidStack drainedFluid = tank.drain(Constants.bucket, false);
                                        if (drainedFluid != null && drainedFluid.amount >= Constants.bucket)
                                        {
                                            if (!world.isRemote && flag && !material.isLiquid())
                                            {
                                                world.destroyBlock(mop.getBlockPos(), true);
                                            }

                                            if (fluid.getFluid().getBlock() == Blocks.WATER)
                                            {
                                                if (world.setBlockState(mop.getBlockPos(), Blocks.FLOWING_WATER.getDefaultState(), 3))
                                                {
                                                    tank.drain(Constants.bucket, true);
                                                }
                                            } else if (fluid.getFluid().getBlock() == Blocks.LAVA)
                                            {
                                                if (world.setBlockState(mop.getBlockPos(), Blocks.FLOWING_LAVA.getDefaultState(), 3))
                                                {
                                                    tank.drain(Constants.bucket, true);
                                                }
                                            } else if (world.setBlockState(mop.getBlockPos(), fluid.getFluid().getBlock().getDefaultState(), 3))
                                            {
                                                tank.drain(Constants.bucket, true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        inv.dirtyTanks();
                    }
                    break;
                case HOSE_DRINK_MODE:
                    if (tank.getFluid() != null && tank.getFluidAmount() >= Constants.bucket)
                    {
                        if (FluidEffectRegistry.hasFluidEffect(tank.getFluid().getFluid()))
                        {
                            player.setActiveHand(hand);
                        }
                    }
                    break;
                default:
                   return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return false;
    }

    //TODO: what this been plreaceed with
    //@Override
    public ItemStack onEaten(ItemStack hose, World world, EntityPlayer player, EnumHand hand)
    {
        if (!Wearing.isWearingBackpack(player)) return hose;
        int mode = -1;
        int tank = -1;
        if (hose.getTagCompound() != null)
        {
            tank = getHoseTank(hose);
            mode = getHoseMode(hose);
        }
        if (mode == HOSE_DRINK_MODE && tank > -1)
        {
            InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
            inv.openInventory(player);
            FluidTank backpackTank = (tank == 0) ? inv.getLeftTank() : (tank == 1) ? inv.getRightTank() : null;
            if (backpackTank != null)
            {
                if (ServerActions.setFluidEffect(world, player, backpackTank))
                {
                    backpackTank.drain(Constants.bucket, true);
                    inv.dirtyTanks();
                }
            }
        }
        return hose;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return true;
    }

    // ================================================ BOOLEANS =====================================================//
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand)
    {
        if (!Wearing.isWearingBackpack(player)) return false;
        InventoryBackpack inventory = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inventory.openInventory(player);
        if (entity instanceof EntityCow && !(entity instanceof EntityMooshroom))
        {

            FluidTank tank = getHoseTank(stack) == 0 ? inventory.getLeftTank() : inventory.getRightTank();
            tank.fill(new FluidStack(ModFluids.milk, Constants.bucket), true);
            inventory.dirtyTanks();

            ((EntityCow) entity).faceEntity(player, 0.1f, 0.1f);
            return true;
        }
        if (entity instanceof EntityMooshroom)
        {
            FluidTank tank = getHoseTank(stack) == 0 ? inventory.getLeftTank() : inventory.getRightTank();
            tank.fill(new FluidStack(ModFluids.mushroomStew, Constants.bucket), true);
            inventory.dirtyTanks();

            ((EntityMooshroom) entity).faceEntity(player, 0.1f, 0.1f);
            return true;
        }

        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack)
    {
        return FluidRegistry.lookupFluidForBlock(block.getBlock()) != null;
    }

}
