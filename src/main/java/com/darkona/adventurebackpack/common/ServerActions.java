package com.darkona.adventurebackpack.common;

import java.util.Random;

import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCoalJetpack;
import com.darkona.adventurebackpack.inventory.SlotTool;
import com.darkona.adventurebackpack.item.ItemCopterPack;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.network.WearableModePacket;
import com.darkona.adventurebackpack.network.messages.EntitySoundPacket;
import com.darkona.adventurebackpack.reference.BackpackNames;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created on 23/12/2014
 *
 * @author Darkona
 */
public class ServerActions
{
    public static final boolean HOSE_SWITCH = false;
    public static final boolean HOSE_TOGGLE = true;
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    /**
     * Cycles tools. In a cycle. The tool in your hand with the tools in the special tool slots of the backpack, to be precise.
     *
     * @param player    - Duh
     * @param direction - An integer indicating the direction of the switch. Nobody likes to swith always inthe same
     *                  direction all the timeInSeconds. That's stupid.
     * @param slot      The slot that will be switched with the backpack.
     */
    //Using @Sir-Will dupe fixed
    public static void cycleTool(EntityPlayer player, int direction, int slot)
    {
        try
        {
            InventoryBackpack backpack = Wearing.getBackpackInv(player, true);
            ItemStack current = player.getHeldItemMainhand();
            backpack.openInventory(player);
            if (SlotTool.isValidTool(current))
            {
                if (direction < 0)
                {
                    player.inventory.mainInventory[slot] = backpack.getStackInSlot(Constants.upperTool);
                    backpack.setInventorySlotContentsNoSave(Constants.upperTool, backpack.getStackInSlot(Constants.lowerTool));
                    backpack.setInventorySlotContentsNoSave(Constants.lowerTool, current);
                } else
                {
                    if (direction > 0)
                    {
                        player.inventory.mainInventory[slot] = backpack.getStackInSlot(Constants.lowerTool);
                        backpack.setInventorySlotContentsNoSave(Constants.lowerTool, backpack.getStackInSlot(Constants.upperTool));
                        backpack.setInventorySlotContentsNoSave(Constants.upperTool, current);
                    }
                }
            }
            backpack.markDirty();
            player.inventory.closeInventory(player);
        } catch (Exception oops)
        {
            LogHelper.debug("Exception trying to cycle tools.");
            oops.printStackTrace();
        }
    }

    /**
     * @param world  The world. Like, the WHOLE world. That's a lot of stuff. Do stuff with it, like detecting biomes
     *               or whatever.
     * @param player Is a player. To whom  the nice or evil effects you're going to apply will affect.
     *               See? I know the proper use of the words "effect" & "affect".
     * @param tank   The tank that holds the fluid, whose effect will affect the player that's in the world.
     * @return If the effect can be applied, and it is actually applied, returns true.
     */
    public static boolean setFluidEffect(World world, EntityPlayer player, FluidTank tank)
    {
        FluidStack drained = tank.drain(Constants.bucket, false);
        boolean done = false;
        if (drained != null && drained.amount >= Constants.bucket && FluidEffectRegistry.hasFluidEffect(drained.getFluid()))
        {
            done = FluidEffectRegistry.executeFluidEffectsForFluid(drained.getFluid(), player, world);
        }
        return done;
    }

    /**
     * @param player    Duh!
     * @param direction The direction in which the hose modes will switch.
     * @param action    The type of the action to be performed on the hose.
     *                  Can be HOSE_SWITCH for mode or HOSE_TOGGLE for tank
     * @param slot      The slot in which the hose gleefully frolicks in the inventory.
     */
    public static void switchHose(EntityPlayer player, boolean action, int direction, int slot)
    {

        ItemStack hose = player.inventory.mainInventory[slot];
        if (hose != null && hose.getItem() instanceof ItemHose)
        {
            NBTTagCompound tag = hose.hasTagCompound() ? hose.getTagCompound() : new NBTTagCompound();
            if (!action)
            {
                int mode = ItemHose.getHoseMode(hose);
                if (!ConfigHandler.enableHoseDrink)
                {
                    mode = (mode + 1) % 2;
                } else if (direction > 0)
                {
                    mode = (mode + 1) % 3;
                } else if (direction < 0)
                {
                    mode = (mode - 1 < 0) ? 2 : mode - 1;
                }
                tag.setInteger("mode", mode);
            }

            if (action)
            {
                int tank = ItemHose.getHoseTank(hose);
                tank = (tank + 1) % 2;
                tag.setInteger("tank", tank);
            }
            hose.setTagCompound(tag);
        }
    }

    /**
     * Electrifying! Transforms a backpack into its electrified version. Shhh this is kinda secret, ok?
     *
     * @param player The player wearing the backpack.
     */
    public static void electrify(EntityPlayer player)
    {
        ItemStack backpack = Wearing.getWearingBackpack(player);
        if (BackpackNames.getBackpackColorName(backpack).equals("Pig"))
        {
            BackpackNames.setBackpackColorName(backpack, "Pigman");
        }
        if (BackpackNames.getBackpackColorName(backpack).equals("Diamond"))
        {
            BackpackNames.setBackpackColorName(backpack, "Electric");
        }
    }

    /**
     * @param player
     * @param bow
     * @param charge
     */
    public static void leakArrow(EntityPlayer player, ItemStack bow, int charge)
    {
        World world = player.world;
        Random itemRand = new Random();
        InventoryBackpack backpack = new InventoryBackpack(Wearing.getWearingBackpack(player));

        //this is all vanilla code for the bow
        boolean flag = player.capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;

        if (flag || backpack.hasItem(Items.ARROW))
        {
            float f = (float) charge / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if ((double) f < 0.1D)
            {
                return;
            }
            if (f > 1.0F)
            {
                f = 1.0F;
            }
            EntityArrow entityarrow = new EntityTippedArrow(world, player);
            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }
            int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
            if (power > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double) power * 0.5D + 0.5D);
            }
            int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);
            if (punch > 0)
            {
                entityarrow.setKnockbackStrength(punch);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, bow) > 0)
            {
                entityarrow.setFire(100);
            }

            bow.damageItem(1, player);
            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.BLOCKS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
            } else
            {
                /*
                * From here, instead of leaking an arrow to the player inventory, which may be full and then it would be
                * pointless, leak an arrow straight from the backpack ^_^
                *
                * It could be possible to switch a whole stack with the player inventory, fire the arrow, and then
                * switch back, but that's stupid.
                *
                * That's how you make a quiver (for vanilla bows at least, or anything that uses the events and vanilla
                * arrows) Until we have an event that fires when a player consumes items in his/her inventory.
                *
                * I should make a pull request. Too lazy, though.
                * */
                backpack.consumeInventoryItem(Items.ARROW);
                backpack.dirtyInventory();
            }

            if (!world.isRemote)
            {
                world.spawnEntity(entityarrow);
            }
        }
    }

    /**
     * @param player
     * @param coordX
     * @param coordY
     * @param coordZ
     */
    public static void toggleSleepingBag(EntityPlayer player, int coordX, int coordY, int coordZ)
    {
        World world = player.world;
        if (world.getTileEntity(new BlockPos(coordX, coordY, coordZ)) instanceof TileAdventureBackpack)
        {
            TileAdventureBackpack te = (TileAdventureBackpack) world.getTileEntity(new BlockPos(coordX, coordY, coordZ));
            if (!te.isSBDeployed())
            {
                int can[] = canDeploySleepingBag(world, coordX, coordY, coordZ);
                if (can[0] > -1)
                {
                    if (te.deploySleepingBag(player, world, can[1], can[2], can[3], can[0]))
                    {
                        player.closeScreen();
                    }
                } else if (world.isRemote)
                {
                    //TODO: test this
                    player.sendStatusMessage(new TextComponentString("Can't deploy the sleeping bag! Check the surrounding area."));
                }
            } else
            {
                te.removeSleepingBag(world);
            }
            player.closeScreen();
        }

    }

    public static int[] canDeploySleepingBag(World world, int coordX, int coordY, int coordZ)
    {
        BlockPos pos = new BlockPos(coordX, coordY, coordZ);
        TileAdventureBackpack te = (TileAdventureBackpack) world.getTileEntity(pos);
        int newMeta = -1;

        if (!te.isSBDeployed())
        {
            ///TODO: confirm we are checking the right direction
            EnumFacing dir = ((EnumFacing)world.getBlockState(pos).getValue(FACING));
            switch (dir)
            {
                case NORTH:
                    --coordZ;
                    if (world.isAirBlock(pos) && world.getBlockState(new BlockPos(coordX, coordY - 1, coordZ)).getMaterial().isSolid())
                    {
                        if (world.isAirBlock(new BlockPos(coordX, coordY, coordZ - 1)) && world.getBlockState(new BlockPos(coordX, coordY - 1, coordZ - 1)).getMaterial().isSolid())
                        {
                            newMeta = 2;
                        }
                    }
                    break;
                case EAST:
                    ++coordX;
                    if (world.isAirBlock(pos) && world.getBlockState(new BlockPos(coordX, coordY - 1, coordZ)).getMaterial().isSolid())
                    {
                        if (world.isAirBlock(new BlockPos(coordX + 1, coordY, coordZ)) && world.getBlockState(new BlockPos(coordX + 1, coordY - 1, coordZ)).getMaterial().isSolid())
                        {
                            newMeta = 3;
                        }
                    }
                    break;
                case SOUTH:
                    ++coordZ;
                    if (world.isAirBlock(pos) && world.getBlockState(new BlockPos(coordX, coordY - 1, coordZ)).getMaterial().isSolid())
                    {
                        if (world.isAirBlock(new BlockPos(coordX, coordY, coordZ + 1)) && world.getBlockState(new BlockPos(coordX, coordY - 1, coordZ + 1)).getMaterial().isSolid())
                        {
                            newMeta = 0;
                        }
                    }
                    break;
                case WEST:
                    --coordX;
                    if (world.isAirBlock(pos) && world.getBlockState(new BlockPos(coordX, coordY - 1, coordZ)).getMaterial().isSolid())
                    {
                        if (world.isAirBlock(new BlockPos(coordX - 1, coordY, coordZ)) && world.getBlockState(new BlockPos(coordX - 1, coordY - 1, coordZ)).getMaterial().isSolid())
                        {
                            newMeta = 1;
                        }
                    }
                    break;
            }
        }
        int result[] = { newMeta, coordX, coordY, coordZ };
        return result;
    }

    /**
     * Adds vertical inertia to the movement in the Y axis of the player, and makes Newton's Laws cry.
     * In other words, makes you jump higher.
     * Also it plays a nice sound effect that will probably get annoying after a while.
     *
     * @param player - The player performing the jump.
     */
    public static void pistonBootsJump(EntityPlayer player)
    {
        if (ConfigHandler.allowSoundPiston)
        {
            player.playSound(SoundEvents.BLOCK_PISTON_EXTEND, 0.5F, player.getRNG().nextFloat() * 0.25F + 0.6F);
        }
        player.motionY += ConfigHandler.pistonBootsJumpHeight / 10.0F;
        player.jumpMovementFactor += 0.3;
    }

    public static void toggleCopterPack(EntityPlayer player, ItemStack copter, byte type)
    {
        String message = "";
        boolean actionPerformed = false;

        if (!copter.hasTagCompound())
        {
            copter.setTagCompound(new NBTTagCompound());
        }
        if (!copter.getTagCompound().hasKey("status"))
        {
            copter.getTagCompound().setByte("status", ItemCopterPack.OFF_MODE);
        }

        byte mode = copter.getTagCompound().getByte("status");
        byte newMode = ItemCopterPack.OFF_MODE;

        if (type == WearableModePacket.COPTER_ON_OFF)
        {
            if (mode == ItemCopterPack.OFF_MODE)
            {
                newMode = ItemCopterPack.NORMAL_MODE;
                message = "adventurebackpack:messages.copterpack.normal";
                actionPerformed = true;
                if (!player.world.isRemote)
                {
                    ModNetwork.sendToNearby(new EntitySoundPacket.Message(EntitySoundPacket.COPTER_SOUND, player), player);

                }
            } else
            {
                newMode = ItemCopterPack.OFF_MODE;
                message = "adventurebackpack:messages.copterpack.off";
                actionPerformed = true;
            }
        }

        if (type == WearableModePacket.COPTER_TOGGLE && mode != ItemCopterPack.OFF_MODE)
        {
            if (mode == ItemCopterPack.NORMAL_MODE)
            {
                newMode = ItemCopterPack.HOVER_MODE;
                message = "adventurebackpack:messages.copterpack.hover";
                actionPerformed = true;
            }
            if (mode == ItemCopterPack.HOVER_MODE)
            {
                newMode = ItemCopterPack.NORMAL_MODE;
                message = "adventurebackpack:messages.copterpack.normal";
                actionPerformed = true;
            }
        }

        if (actionPerformed)
        {
            copter.getTagCompound().setByte("status", newMode);
            if (player.world.isRemote)
            {
                player.sendStatusMessage(new TextComponentTranslation(message));
            }

        }
    }

    public static void toggleToolCycling(EntityPlayer player, ItemStack backpack, byte on_off)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);
        if (ConfigHandler.enableToolsCycling)
        {
            if (inv.getDisableCycling())
            {
                inv.setDisableCycling(false);
                inv.markDirty();
                if (player.world.isRemote)
                {
                    player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.cycling.on"));
                }
            } else
            {
                inv.setDisableCycling(true);
                inv.markDirty();
                if (player.world.isRemote)
                {
                    player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.cycling.off"));
                }
            }
        }
    }

    public static void toggleNightVision(EntityPlayer player, ItemStack backpack, byte on_off)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);
        if (inv.getDisableNVision())
        {
            inv.setDisableNVision(false);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.playSound(SoundEvents.ENTITY_BAT_AMBIENT, 0.2F, 1.0F);
                player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.nightvision.on"));
            }
        } else
        {
            inv.setDisableNVision(true);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.playSound(SoundEvents.ENTITY_BAT_DEATH, 0.2F, 2.0F);
                player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.nightvision.off"));
            }
        }
    }

    public static void toggleCoalJetpack(EntityPlayer player, ItemStack jetpack, byte on_off)
    {
        InventoryCoalJetpack inv = new InventoryCoalJetpack(jetpack);
        if (inv.getStatus())
        {
            inv.setStatus(false);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.jetpack.off"));
            }
        } else
        {
            inv.setStatus(true);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.sendStatusMessage(new TextComponentTranslation("adventurebackpack:messages.jetpack.on"));
            }
        }
    }

}