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
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.IIcon;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
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
    /**
     * TODO: rendering
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
        switch (getHoseMode(stack))
        {
            case HOSE_SUCK_MODE:
                return suckIcon;
            case HOSE_SPILL_MODE:
                return spillIcon;
            case HOSE_DRINK_MODE:
                return drinkIcon;
            default:
                return itemIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return itemIcon;
    }
    */

    public static int getHoseMode(ItemStack hose)
    {
        return hose.stackTagCompound != null ? hose.stackTagCompound.getInteger("mode") : -1;
    }

    public static int getHoseTank(ItemStack hose)
    {
        return hose.hasTagCompound() ? hose.getTagCompound().getInteger("tank") : -1;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("mode"))
        {
            return (stack.stackTagCompound.getInteger("mode") == 2) ? EnumAction.drink : EnumAction.none;
        }
        return EnumAction.none;
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
    // ================================================= ICONS  ======================================================//
    //@Override
    //@SideOnly(Side.CLIENT)
    //public void registerIcons(IIconRegister iconRegister)
    //{
    //    drinkIcon = iconRegister.registerIcon(Resources.getIconString("hoseDrink"));
    //    spillIcon = iconRegister.registerIcon(Resources.getIconString("hoseSpill"));
    //    suckIcon = iconRegister.registerIcon(Resources.getIconString("hoseSuck"));
    //    itemIcon = iconRegister.registerIcon(Resources.getIconString("hoseLeft"));
    //}
    // ================================================ ACTIONS  =====================================================//

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int inv_slot, boolean isCurrent)
    {
        if (entity == null || !(entity instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) entity;
        if (world.isRemote && player.getItemInUse() != null && player.getItemInUse().getItem().equals(this)) return;

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
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (!Wearing.isWearingBackpack(player)) return true;

        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inv.openInventory();
        FluidTank tank = getHoseTank(stack) == 0 ? inv.getLeftTank() : inv.getRightTank();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof IFluidHandler)
        {
            IFluidHandler exTank = (IFluidHandler) te;
            int accepted = 0;
            switch (getHoseMode(stack))
            {
                case HOSE_SUCK_MODE:

                    accepted = tank.fill(exTank.drain(EnumFacing.UNKNOWN, Constants.bucket, false), false);
                    if (accepted > 0)
                    {
                        tank.fill(exTank.drain(ForgeDEnumFacingirection.UNKNOWN, accepted, true), true);
                        te.markDirty();
                        inv.dirtyTanks();
                        return true;
                    }
                    break;

                case HOSE_SPILL_MODE:

                    accepted = exTank.fill(EnumFacing.UNKNOWN, tank.drain(Constants.bucket, false), false);
                    if (accepted > 0)
                    {
                        exTank.fill(EnumFacing.UNKNOWN, tank.drain(accepted, true), true);
                        te.markDirty();
                        inv.dirtyTanks();
                        return true;
                    }
                    break;
            }
        }
        return false;

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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!Wearing.isWearingBackpack(player)) return stack;
        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inv.openInventory();
        RayTraceResult mop = getMovingObjectPositionFromPlayer(world, player, true);
        FluidTank tank = getHoseTank(stack) == 0 ? inv.getLeftTank() : inv.getRightTank();
        if (tank != null)
        {
            switch (getHoseMode(stack))
            {
                case HOSE_SUCK_MODE: // If it's in Suck Mode

                    if (mop != null && mop.typeOfHit == RayTraceResult.MovingObjectType.BLOCK)
                    {
                        /* if (!world.canMineBlock(player, mop.blockX, mop.blockY, mop.blockZ))
                         {
                         return stack;
                         }*/

                        if (!player.canPlayerEdit(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, null))
                        {
                            return stack;
                        }
                        //TODO adjust for Adventure Mode
                        Fluid fluidBlock = FluidRegistry.lookupFluidForBlock(world.getBlock(mop.blockX, mop.blockY, mop.blockZ));
                        if (fluidBlock != null)
                        {
                            FluidStack fluid = new FluidStack(fluidBlock, Constants.bucket);
                            if (tank.getFluid() == null || tank.getFluid().containsFluid(fluid))
                            {
                                int accepted = tank.fill(fluid, false);
                                if (accepted > 0)
                                {
                                    world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
                                    tank.fill(new FluidStack(fluidBlock, accepted), true);
                                }
                            }
                        }
                        inv.dirtyTanks();
                    }
                    break;

                case HOSE_SPILL_MODE: // If it's in Spill Mode
                    if (mop != null && mop.typeOfHit == RayTraceResult.MovingObjectType.BLOCK)
                    {
                        int x = mop.blockX;
                        int y = mop.blockY;
                        int z = mop.blockZ;
                        if (world.getBlock(x, y, z).isBlockSolid(world, x, y, z, mop.sideHit))
                        {

                            switch (mop.sideHit)
                            {
                                case 0:
                                    --y;
                                    break;
                                case 1:
                                    ++y;
                                    break;
                                case 2:
                                    --z;
                                    break;
                                case 3:
                                    ++z;
                                    break;
                                case 4:
                                    --x;
                                    break;
                                case 5:
                                    ++x;
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
                                    Material material = world.getBlock(x, y, z).getMaterial();
                                    boolean flag = !material.isSolid();
                                    if (!world.isAirBlock(x, y, z) && !flag)
                                    {
                                        return stack;
                                    }
                                    /* IN HELL DIMENSION No, I won't let you put water in the nether. You freak*/
                                    if (world.provider.isHellWorld && fluid.getFluid() == FluidRegistry.WATER)
                                    {
                                        tank.drain(Constants.bucket, true);
                                        world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                                        for (int l = 0; l < 12; ++l)
                                        {
                                            world.spawnParticle("largesmoke", x + Math.random(), y + Math.random(), z + Math.random(), 0.0D, 0.0D, 0.0D);
                                        }
                                    } else
                                    {
                                        /* NOT IN HELL DIMENSION. */
                                        FluidStack drainedFluid = tank.drain(Constants.bucket, false);
                                        if (drainedFluid != null && drainedFluid.amount >= Constants.bucket)
                                        {
                                            if (!world.isRemote && flag && !material.isLiquid())
                                            {
                                                world.destroyBlock(x, y, z, true);
                                            }

                                            if (fluid.getFluid().getBlock() == Blocks.water)
                                            {
                                                if (world.setBlock(x, y, z, Blocks.flowing_water, 0, 3))
                                                {
                                                    tank.drain(Constants.bucket, true);
                                                }
                                            } else if (fluid.getFluid().getBlock() == Blocks.lava)
                                            {
                                                if (world.setBlock(x, y, z, Blocks.flowing_lava, 0, 3))
                                                {
                                                    tank.drain(Constants.bucket, true);
                                                }
                                            } else if (world.setBlock(x, y, z, fluid.getFluid().getBlock(), 0, 3))
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
                            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
                        }
                    }
                    break;
                default:
                    return stack;
            }
        }
        return stack;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return false;
    }

    @Override
    public ItemStack onEaten(ItemStack hose, World world, EntityPlayer player)
    {
        if (!Wearing.isWearingBackpack(player)) return hose;
        int mode = -1;
        int tank = -1;
        if (hose.stackTagCompound != null)
        {
            tank = getHoseTank(hose);
            mode = getHoseMode(hose);
        }
        if (mode == HOSE_DRINK_MODE && tank > -1)
        {
            InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
            inv.openInventory();
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
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!Wearing.isWearingBackpack(player)) return false;
        InventoryBackpack inventory = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inventory.openInventory();
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
    public boolean canHarvestBlock(Block block, ItemStack stack)
    {
        return FluidRegistry.lookupFluidForBlock(block) != null;
    }

}
