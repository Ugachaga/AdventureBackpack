package com.darkona.adventurebackpack.item;

import java.util.List;
import javax.annotation.Nullable;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModFluids;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.TipUtils;
import com.darkona.adventurebackpack.util.Wearing;

import static com.darkona.adventurebackpack.common.Constants.BUCKET;
import static com.darkona.adventurebackpack.util.TipUtils.l10n;

public class ItemHose extends AdventureItem
{
    private static final byte HOSE_SUCK_MODE = 0;
    private static final byte HOSE_SPILL_MODE = 1;
    private static final byte HOSE_DRINK_MODE = 2;

    public ItemHose(String name)
    {
        super(name);
        this.setCreativeTab(ModInfo.CREATIVE_TAB);
        this.setMaxStackSize(1);
        this.setFull3D();
        this.setNoRepair();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (GuiScreen.isCtrlKeyDown())
        {
            tooltip.add(l10n("hose.key.header"));
            tooltip.add("- " + TipUtils.pressKeyFormat(TipUtils.actionKeyFormat()) + l10n("hose.key.tank"));
            tooltip.add("- " + TipUtils.pressShiftKeyFormat(TipUtils.whiteFormat(l10n("mouse.wheel"))) + l10n("hose.key.mode"));
            tooltip.add("");
            tooltip.add(l10n("hose.dump1"));
            tooltip.add(l10n("hose.dump2"));
            tooltip.add(TextFormatting.RED.toString() + l10n("hose.dump.warn"));
        }
        else
        {
            tooltip.add(TipUtils.holdCtrl());
        }
    }

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
        String name = "hose_" + (getHoseTank(stack) == 0 ? "left_tank" : getHoseTank(stack) == 1 ? "right_tank" : "");
        switch (getHoseMode(stack))
        {
            case 0:
                return getUnlocalizedName(name + "_suck");
            case 1:
                return getUnlocalizedName(name + "_spill");
            case 2:
                return getUnlocalizedName(name + "_drink");
            default:
                return getUnlocalizedName("hose_useless");
        }
    }

    public String getUnlocalizedName(String name)
    {
        return "item." + ModInfo.MODID + ":" + name;
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

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int inv_slot, boolean isCurrent)
    {
        if (entity == null || !(entity instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) entity;
        if (world.isRemote && player.getActiveItemStack().getItem().equals(this)) return; //TODO wat?

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
                nbt.setString("fluid", WordUtils.capitalize(tank.getFluid().getFluid().getName()));
                nbt.setInteger("amount", tank.getFluidAmount());
            }
            else
            {
                nbt.setInteger("amount", 0);
                nbt.setString("fluid", "Empty");
            }
        }
        else
        {
            nbt.setInteger("amount", 0);
            nbt.setString("fluid", "None");
            nbt.setInteger("mode", -1);
            nbt.setInteger("tank", -1);
        }
        stack.setTagCompound(nbt);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!Wearing.isWearingBackpack(player))
            return EnumActionResult.PASS;

        ItemStack stack = player.getHeldItem(hand);
        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inv.openInventory(player);
        FluidTank tank = getHoseTank(stack) == 0 ? inv.getLeftTank() : inv.getRightTank();
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof IFluidHandler)
        {
            IFluidHandler exTank = (IFluidHandler) te;
            int accepted;
            switch (getHoseMode(stack))
            {
                case HOSE_SUCK_MODE:
                    accepted = tank.fill(exTank.drain(BUCKET, false), false);
                    if (accepted > 0)
                    {
                        tank.fill(exTank.drain(accepted, true), true);
                        te.markDirty();
                        inv.dirtyTanks();
                        return EnumActionResult.SUCCESS;
                    }
                    break;

                case HOSE_SPILL_MODE:
                    accepted = exTank.fill(tank.drain(BUCKET, false), false);
                    if (accepted > 0)
                    {
                        exTank.fill(tank.drain(accepted, true), true);
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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!Wearing.isWearingBackpack(player))
            return new ActionResult<>(EnumActionResult.PASS, stack);

        InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inv.openInventory(player);
        RayTraceResult rayTrace = rayTrace(world, player, true);
        BlockPos rayTracePos = rayTrace.getBlockPos();
        FluidTank tank = getHoseTank(stack) == 0 ? inv.getLeftTank() : inv.getRightTank();
        if (tank != null)
        {
            switch (getHoseMode(stack))
            {
                case HOSE_SUCK_MODE:
                    if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
                    {
                        if (!player.canPlayerEdit(rayTracePos, rayTrace.sideHit, stack))
                        {
                            return new ActionResult<>(EnumActionResult.FAIL, stack);
                        }
                        //TODO adjust for Adventure Mode
                        Fluid fluidBlock = FluidRegistry.lookupFluidForBlock(world.getBlockState(rayTracePos).getBlock());
                        if (fluidBlock != null)
                        {
                            FluidStack fluid = new FluidStack(fluidBlock, BUCKET);
                            if (tank.getFluid() == null || tank.getFluid().containsFluid(fluid))
                            {
                                int accepted = tank.fill(fluid, false);
                                if (accepted > 0)
                                {
                                    world.setBlockToAir(rayTracePos);
                                    tank.fill(new FluidStack(fluidBlock, accepted), true);
                                }
                            }
                        }
                        inv.dirtyTanks();
                    }
                    break;

                case HOSE_SPILL_MODE:
                    if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
                    {
                        Material rayTraceMaterial = world.getBlockState(rayTracePos).getMaterial();
                        boolean isSolid = rayTraceMaterial.isSolid();

                        if (isSolid)
                        {
                            switch (rayTrace.sideHit)
                            {
                                case DOWN:
                                    rayTracePos.down();
                                    break;
                                case UP:
                                    rayTracePos.up();
                                    break;
                                case NORTH:
                                    rayTracePos.north();
                                    break;
                                case SOUTH:
                                    rayTracePos.south();
                                    break;
                                case EAST:
                                    rayTracePos.east();
                                    break;
                                case WEST:
                                    rayTracePos.west();
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
                                    if (!world.isAirBlock(rayTracePos) && isSolid)
                                    {
                                        return new ActionResult<>(EnumActionResult.FAIL, stack);
                                    }
                                    /* IN HELL DIMENSION No, I won't let you put water in the nether. You freak*/
                                    if (world.provider.doesWaterVaporize() && fluid.getFluid() == FluidRegistry.WATER)
                                    {
                                        tank.drain(BUCKET, true);
                                        //TODO "random.fizz" sound has been removed, ENTITY_CREEPER_PRIMED suggested by Phil, should we try to port back fizz?
                                        player.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                                        for (int l = 0; l < 12; ++l)
                                        {
                                            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, rayTracePos.getX() + Math.random(), rayTracePos.getY() + Math.random(), rayTracePos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
                                        }
                                    }
                                    else
                                    {
                                        /* NOT IN HELL DIMENSION. */
                                        FluidStack drainedFluid = tank.drain(BUCKET, false);
                                        if (drainedFluid != null && drainedFluid.amount >= BUCKET)
                                        {
                                            if (!world.isRemote && isSolid && !rayTraceMaterial.isLiquid())
                                            {
                                                world.destroyBlock(rayTracePos, true);
                                            }

                                            if (fluid.getFluid().getBlock() == Blocks.WATER)
                                            {
                                                if (world.setBlockState(rayTracePos, Blocks.FLOWING_WATER.getDefaultState(), 3))
                                                {
                                                    tank.drain(BUCKET, true);
                                                }
                                            }
                                            else if (fluid.getFluid().getBlock() == Blocks.LAVA)
                                            {
                                                if (world.setBlockState(rayTracePos, Blocks.FLOWING_LAVA.getDefaultState(), 3))
                                                {
                                                    tank.drain(BUCKET, true);
                                                }
                                            }
                                            else if (world.setBlockState(rayTracePos, fluid.getFluid().getBlock().getDefaultState(), 3))
                                            {
                                                tank.drain(BUCKET, true);
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
                    if (tank.getFluid() != null && tank.getFluidAmount() >= BUCKET)
                    {
                        if (FluidEffectRegistry.hasFluidEffect(tank.getFluid().getFluid()))
                        {
                            player.setActiveHand(hand);
                        }
                    }
                    break;
                default:
                    return new ActionResult<>(EnumActionResult.PASS, stack);
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack); //TODO add SUCCESS returns
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
    {
        if (!(entity instanceof EntityPlayer))
            return stack;

        EntityPlayer player = (EntityPlayer) entity;

        if (!Wearing.isWearingBackpack(player))
            return stack;

        int mode = -1;
        int tank = -1;
        if (stack.getTagCompound() != null)
        {
            tank = getHoseTank(stack);
            mode = getHoseMode(stack);
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
                    backpackTank.drain(BUCKET, true);
                    inv.dirtyTanks();
                }
            }
        }
        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand)
    {
        if (!Wearing.isWearingBackpack(player))
            return false;

        InventoryBackpack inventory = new InventoryBackpack(Wearing.getWearingBackpack(player));
        inventory.openInventory(player);

        if (entity instanceof EntityMooshroom)
        {
            FluidTank tank = getHoseTank(stack) == 0 ? inventory.getLeftTank() : inventory.getRightTank();
            tank.fill(new FluidStack(ModFluids.MUSHROOM_STEW, BUCKET), true);
            inventory.dirtyTanks();

            ((EntityMooshroom) entity).faceEntity(player, 0.1f, 0.1f);
            return true;
        }
        else if (entity instanceof EntityCow )
        {
            FluidTank tank = getHoseTank(stack) == 0 ? inventory.getLeftTank() : inventory.getRightTank();
            tank.fill(new FluidStack(ModFluids.MILK, BUCKET), true);
            inventory.dirtyTanks();

            ((EntityCow) entity).faceEntity(player, 0.1f, 0.1f);
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
