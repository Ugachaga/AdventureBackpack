package com.darkona.adventurebackpack.common;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.darkona.adventurebackpack.block.BlockSleepingBag;
import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.inventory.SlotTool;
import com.darkona.adventurebackpack.item.ItemCopter;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.network.WearableModePacket;
import com.darkona.adventurebackpack.network.messages.EntitySoundPacket;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.CoordsUtils;
import com.darkona.adventurebackpack.util.Wearing;

import static com.darkona.adventurebackpack.common.Constants.BUCKET;
import static com.darkona.adventurebackpack.common.Constants.Copter.TAG_STATUS;
import static com.darkona.adventurebackpack.common.Constants.TOOL_LOWER;
import static com.darkona.adventurebackpack.common.Constants.TOOL_UPPER;

/**
 * Created on 23/12/2014
 *
 * @author Darkona
 */
public class ServerActions
{
    public static final boolean HOSE_SWITCH = false;
    public static final boolean HOSE_TOGGLE = true;

    /**
     * Cycles tools. In a cycle. The tool in your hand with the tools in the special tool playerSlot of the backpack,
     * to be precise.
     *
     * @param player      Duh
     * @param isWheelUp   A boolean indicating the direction of the switch. Nobody likes to swith always in the same
     *                    direction all the timeInSeconds. That's stupid.
     * @param playerSlot  The slot that will be switched with the backpack.
     */
    public static void cycleTool(EntityPlayer player, boolean isWheelUp, int playerSlot)
    {
        if (!GeneralReference.isDimensionAllowed(player))
            return;

        //TODO we should get from playerSlot instead, in case slot were changed already
        ItemStack current = player.getHeldItemMainhand();
        if (SlotTool.isValidTool(current))
        {
            int backpackSlot = isWheelUp ? TOOL_UPPER : TOOL_LOWER;
            InventoryBackpack backpack = Wearing.getWearingBackpackInv(player);
            backpack.openInventory(player);
            player.inventory.mainInventory.set(playerSlot, backpack.getStackInSlot(backpackSlot));
            backpack.setInventorySlotContentsNoSave(backpackSlot, current);
            backpack.dirtyInventory();
        }
    }

    /**
     * @param world  The world. Like, the WHOLE world. That's a lot of stuff. Do stuff with it, like detecting biomes
     *               or whatever.
     * @param player Is a player. To whom  the nice or evil effects you're going to apply will affect.
     *               See? I know the proper use of the words "effect" & "affect".
     * @param tank   The tank that holds the fluid, whose effect will affect the player that's in the world.
     * @return       If the effect can be applied, and it is actually applied, returns true.
     */
    public static boolean setFluidEffect(World world, EntityPlayer player, FluidTank tank)
    {
        FluidStack drained = tank.drain(BUCKET, false);
        boolean done = false;
        if (drained != null && drained.amount >= BUCKET && FluidEffectRegistry.hasFluidEffect(drained.getFluid()))
        {
            done = FluidEffectRegistry.executeFluidEffectsForFluid(drained.getFluid(), player, world);
        }
        return done;
    }

    /**
     * @param player    Duh!
     * @param isWheelUp The direction in which the hose modes will switch.
     * @param action    The type of the action to be performed on the hose.
     *                  Can be HOSE_SWITCH for mode or HOSE_TOGGLE for tank
     */
    public static void switchHose(EntityPlayer player, boolean isWheelUp, boolean action)
    {
        if (Wearing.isHoldingHose(player))
        {
            ItemStack hose = player.inventory.getCurrentItem();
            NBTTagCompound tag = hose.hasTagCompound() ? hose.getTagCompound() : new NBTTagCompound();

            if (!action)
            {
                int mode = ItemHose.getHoseMode(hose);
                if (!ConfigHandler.enableHoseDrink)
                {
                    mode = (mode + 1) % 2;
                }
                else if (isWheelUp)
                {
                    mode = (mode + 1) % 3;
                }
                else
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

        if (BackpackTypes.getType(backpack) == BackpackTypes.PIG)
        {
            BackpackUtils.setBackpackType(backpack, BackpackTypes.PIGMAN);
        }
        if (BackpackTypes.getType(backpack) == BackpackTypes.DIAMOND)
        {
            BackpackUtils.setBackpackType(backpack, BackpackTypes.ELECTRIC);
        }
    }

    public static void toggleSleepingBag(EntityPlayer player, boolean isTile, int x, int y, int z)
    {
        World world = player.world;
        BlockPos pos = new BlockPos(x, y, z);

        if (!world.provider.canRespawnHere() || world.getBiome(pos) == Biomes.HELL)
        {
            player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.cant.sleep.here"));
            player.closeScreen();
            return;
        }

        if (isTile && world.getTileEntity(pos) instanceof TileBackpack)
        {
            TileBackpack te = (TileBackpack) world.getTileEntity(pos);
            if (!te.isSleepingBagDeployed())
            {
                int can[] = CoordsUtils.canDeploySleepingBag(world, player, x, y, z, true);
                if (can[0] > -1)
                {
                    if (te.deploySleepingBag(player, world, can[0], can[1], can[2], can[3]))
                    {
                        player.closeScreen();
                    }
                }
                else if (!world.isRemote)
                {
                    player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.backpack.cant.bag"));
                }
            }
            else
            {
                te.removeSleepingBag(world);
            }
            player.closeScreen();
        }
        else if (!isTile && Wearing.isWearingBackpack(player))
        {
            int can[] = CoordsUtils.canDeploySleepingBag(world, player, x, y, z, false);
            if (can[0] > -1)
            {
                InventoryBackpack inv = Wearing.getWearingBackpackInv(player);
                if (inv.deploySleepingBag(player, world, can[0], can[1], can[2], can[3]))
                {
                    Block portableBag = world.getBlockState(new BlockPos(can[1], can[2], can[3])).getBlock();
                    if (portableBag instanceof BlockSleepingBag)
                    {
                        inv.getExtendedProperties().setBoolean(Constants.TAG_SLEEPING_IN_BAG, true);
                        ((BlockSleepingBag) portableBag).onPortableBlockActivated(world, player, new BlockPos(can[1], can[2], can[3]));
                    }
                }
            }
            else if (!world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.backpack.cant.bag"));
            }
            player.closeScreen();
        }
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

    public static void copterSoundAtLogin(EntityPlayer player)
    {
        byte status = BackpackUtils.getWearableCompound(BackpackProperty.get(player).getWearable()).getByte(TAG_STATUS);

        if (!player.world.isRemote && status != ItemCopter.OFF_MODE)
        {
            ModNetwork.sendToNearby(new EntitySoundPacket.Message(EntitySoundPacket.COPTER_SOUND, player), player);
        }
    }

    public static void jetpackSoundAtLogin(EntityPlayer player)
    {
        boolean isBoiling = BackpackUtils.getWearableCompound(BackpackProperty.get(player).getWearable()).getBoolean("boiling");

        if (!player.world.isRemote && isBoiling)
        {
            //ModNetwork.sendToNearby(new EntitySoundPacket.Message(EntitySoundPacket.BOILING_BUBBLES, player), player); //TODO difference?
            ModNetwork.INSTANCE.sendTo(new EntitySoundPacket.Message(EntitySoundPacket.BOILING_BUBBLES, player), (EntityPlayerMP) player);
        }
    }

    public static void toggleCopter(EntityPlayer player, ItemStack copter, byte type)
    {
        String message = "";
        boolean actionPerformed = false;
        byte mode = BackpackUtils.getWearableCompound(copter).getByte(TAG_STATUS);
        byte newMode = ItemCopter.OFF_MODE;

        if (type == WearableModePacket.COPTER_ON_OFF)
        {
            if (mode == ItemCopter.OFF_MODE)
            {
                newMode = ItemCopter.NORMAL_MODE;
                message = "adventurebackpack:messages.copter.normal";
                actionPerformed = true;
                if (!player.world.isRemote)
                {
                    ModNetwork.sendToNearby(new EntitySoundPacket.Message(EntitySoundPacket.COPTER_SOUND, player), player);
                }
            }
            else
            {
                newMode = ItemCopter.OFF_MODE;
                message = "adventurebackpack:messages.copter.off";
                actionPerformed = true;
            }
        }

        if (type == WearableModePacket.COPTER_TOGGLE && mode != ItemCopter.OFF_MODE)
        {
            if (mode == ItemCopter.NORMAL_MODE)
            {
                newMode = ItemCopter.HOVER_MODE;
                message = "adventurebackpack:messages.copter.hover";
                actionPerformed = true;
            }
            if (mode == ItemCopter.HOVER_MODE)
            {
                newMode = ItemCopter.NORMAL_MODE;
                message = "adventurebackpack:messages.copter.normal";
                actionPerformed = true;
            }
        }

        if (actionPerformed)
        {
            BackpackUtils.getWearableCompound(copter).setByte(TAG_STATUS, newMode);
            if (player.world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation(message));
            }
        }
    }

    public static void toggleToolCycling(EntityPlayer player, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);

        if (inv.getDisableCycling())
        {
            inv.setDisableCycling(false);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.cycling.on"));
            }
        }
        else
        {
            inv.setDisableCycling(true);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.cycling.off"));
            }
        }
    }

    public static void toggleNightVision(EntityPlayer player, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);
        if (inv.getDisableNVision())
        {
            inv.setDisableNVision(false);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.playSound(SoundEvents.ENTITY_BAT_AMBIENT, 0.2F, 1.0F);
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.nightvision.on"));
            }
        }
        else
        {
            inv.setDisableNVision(true);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.playSound(SoundEvents.ENTITY_BAT_DEATH, 0.2F, 2.0F);
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.nightvision.off"));
            }
        }
    }

    public static void toggleJetpack(EntityPlayer player, ItemStack jetpack)
    {
        InventoryJetpack inv = new InventoryJetpack(jetpack);
        if (inv.getStatus())
        {
            inv.setStatus(false);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.jetpack.off"));
            }
        }
        else
        {
            inv.setStatus(true);
            inv.markDirty();
            if (player.world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.jetpack.on"));
            }
        }
    }
}