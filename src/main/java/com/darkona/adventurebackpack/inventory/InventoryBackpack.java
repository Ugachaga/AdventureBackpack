package com.darkona.adventurebackpack.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidTank;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.CoordsUtils;

import static com.darkona.adventurebackpack.common.Constants.BUCKET_IN_LEFT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_IN_RIGHT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_OUT_LEFT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_OUT_RIGHT;
import static com.darkona.adventurebackpack.common.Constants.TAG_DISABLE_CYCLING;
import static com.darkona.adventurebackpack.common.Constants.TAG_DISABLE_NVISION;
import static com.darkona.adventurebackpack.common.Constants.TAG_EXTENDED_COMPOUND;
import static com.darkona.adventurebackpack.common.Constants.TAG_INVENTORY;
import static com.darkona.adventurebackpack.common.Constants.TAG_LEFT_TANK;
import static com.darkona.adventurebackpack.common.Constants.TAG_RIGHT_TANK;
import static com.darkona.adventurebackpack.common.Constants.TAG_TYPE;
import static com.darkona.adventurebackpack.common.Constants.TAG_WEARABLE_COMPOUND;

public class InventoryBackpack extends InventoryWearable implements IInventoryBackpack
{
    private static final String TAG_IS_SLEEPING_BAG = "sleepingBag";
    private static final String TAG_SLEEPING_BAG_X = "sleepingBagX";
    private static final String TAG_SLEEPING_BAG_Y = "sleepingBagY";
    private static final String TAG_SLEEPING_BAG_Z = "sleepingBagZ";

    private BackpackTypes type = BackpackTypes.STANDARD;
    private FluidTank leftTank = new FluidTank(Constants.BASIC_TANK_CAPACITY);
    private FluidTank rightTank = new FluidTank(Constants.BASIC_TANK_CAPACITY);
    private NBTTagCompound extendedProperties = new NBTTagCompound();

    private boolean sleepingBagDeployed = false;
    private int sleepingBagX;
    private int sleepingBagY;
    private int sleepingBagZ;

    private boolean disableNVision = false;
    private boolean disableCycling = false;
    private int lastTime = 0;

    public InventoryBackpack(ItemStack backpack)
    {
        super(backpack, Constants.INVENTORY_SIZE);
        loadFromNBT(containerStack.getTagCompound());
    }

    @Override
    public BackpackTypes getType()
    {
        return type;
    }

    @Override
    public ItemStack[] getInventory()
    {
        return inventory;
    }

    @Override
    public FluidTank getLeftTank()
    {
        return leftTank;
    }

    @Override
    public FluidTank getRightTank()
    {
        return rightTank;
    }

    @Override
    public FluidTank[] getTanksArray()
    {
        return new FluidTank[]{leftTank, rightTank};
    }

    @Override
    public int[] getSlotsOnClosing()
    {
        return new int[]{BUCKET_IN_LEFT, BUCKET_IN_RIGHT, BUCKET_OUT_LEFT, BUCKET_OUT_RIGHT};
    }

    @Override
    public NBTTagCompound getExtendedProperties()
    {
        return extendedProperties;
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound)
    {
        if (compound == null)
            return; // this need for NEI and WAILA trying to render tile.backpack and comes here w/o nbt

        NBTTagCompound backpackTag = compound.getCompoundTag(TAG_WEARABLE_COMPOUND);
        type = BackpackTypes.getType(backpackTag.getByte(TAG_TYPE));
        setInventoryFromTagList(backpackTag.getTagList(TAG_INVENTORY, NBT.TAG_COMPOUND));
        leftTank.readFromNBT(backpackTag.getCompoundTag(TAG_LEFT_TANK));
        rightTank.readFromNBT(backpackTag.getCompoundTag(TAG_RIGHT_TANK));
        extendedProperties = backpackTag.getCompoundTag(TAG_EXTENDED_COMPOUND);
        loadSleepingBag();
        disableCycling = backpackTag.getBoolean(TAG_DISABLE_CYCLING);
        disableNVision = backpackTag.getBoolean(TAG_DISABLE_NVISION);
        lastTime = backpackTag.getInteger("lastTime");
    }

    @Override
    public void saveToNBT(NBTTagCompound compound)
    {
        NBTTagCompound backpackTag = new NBTTagCompound();
        backpackTag.setByte(TAG_TYPE, type.getMeta());
        backpackTag.setTag(TAG_INVENTORY, getInventoryTagList());
        backpackTag.setTag(TAG_RIGHT_TANK, rightTank.writeToNBT(new NBTTagCompound()));
        backpackTag.setTag(TAG_LEFT_TANK, leftTank.writeToNBT(new NBTTagCompound()));
        backpackTag.setTag(TAG_EXTENDED_COMPOUND, extendedProperties);
        saveSleepingBag();
        backpackTag.setBoolean(TAG_DISABLE_CYCLING, disableCycling);
        backpackTag.setBoolean(TAG_DISABLE_NVISION, disableNVision);
        backpackTag.setInteger("lastTime", lastTime);

        compound.setTag(TAG_WEARABLE_COMPOUND, backpackTag);
    }

    @Override
    public boolean updateTankSlots()
    {
        boolean changesMade = false;
        while (InventoryActions.transferContainerTank(this, getLeftTank(), BUCKET_IN_LEFT))
            changesMade = true;
        while (InventoryActions.transferContainerTank(this, getRightTank(), BUCKET_IN_RIGHT))
            changesMade = true;
        return changesMade;
    }

    @Override
    public void dirtyTanks()
    {
        getWearableCompound().setTag(TAG_LEFT_TANK, leftTank.writeToNBT(new NBTTagCompound()));
        getWearableCompound().setTag(TAG_RIGHT_TANK, rightTank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void dirtyExtended() //TODO is it redundant?
    {
        getWearableCompound().removeTag(TAG_EXTENDED_COMPOUND); //TODO again: why?
        getWearableCompound().setTag(TAG_EXTENDED_COMPOUND, extendedProperties);
    }

    @Override
    public void dirtyTime()
    {
        getWearableCompound().setInteger("lastTime", lastTime);
    }

    @Override
    public int getLastTime()
    {
        return this.lastTime;
    }

    @Override
    public void setLastTime(int time)
    {
        this.lastTime = time;
    }

    @Override
    public boolean hasItem(Item item)
    {
        return InventoryActions.hasItem(this, item);
    }

    @Override
    public void consumeInventoryItem(Item item)
    {
        InventoryActions.consumeItemInInventory(this, item);
    }

    public boolean getDisableCycling()
    {
        return disableCycling;
    }

    public void setDisableCycling(boolean b)
    {
        this.disableCycling = b;
    }

    public boolean getDisableNVision()
    {
        return disableNVision;
    }

    public void setDisableNVision(boolean b)
    {
        this.disableNVision = b;
    }

    // Sleeping Bag
    @Override
    public boolean isSleepingBagDeployed()
    {
        return sleepingBagDeployed;
    }

    private void loadSleepingBag()
    {
        sleepingBagDeployed = extendedProperties.getBoolean(TAG_IS_SLEEPING_BAG);
        if (sleepingBagDeployed)
        {
            sleepingBagX = extendedProperties.getInteger(TAG_SLEEPING_BAG_X);
            sleepingBagY = extendedProperties.getInteger(TAG_SLEEPING_BAG_Y);
            sleepingBagZ = extendedProperties.getInteger(TAG_SLEEPING_BAG_Z);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void saveSleepingBag()
    {
        if (sleepingBagDeployed)
        {
            extendedProperties.setBoolean(TAG_IS_SLEEPING_BAG, sleepingBagDeployed);
            extendedProperties.setInteger(TAG_SLEEPING_BAG_X, sleepingBagX);
            extendedProperties.setInteger(TAG_SLEEPING_BAG_Y, sleepingBagY);
            extendedProperties.setInteger(TAG_SLEEPING_BAG_Z, sleepingBagZ);
        }
        else
        {
            extendedProperties.removeTag(TAG_IS_SLEEPING_BAG);
            extendedProperties.removeTag(TAG_SLEEPING_BAG_X);
            extendedProperties.removeTag(TAG_SLEEPING_BAG_Y);
            extendedProperties.removeTag(TAG_SLEEPING_BAG_Z);
        }
    }

    public boolean deploySleepingBag(EntityPlayer player, World world, int meta, int cX, int cY, int cZ)
    {
        if (world.isRemote)
            return false;

        if (sleepingBagDeployed)
            removeSleepingBag(world);

        sleepingBagDeployed = CoordsUtils.spawnSleepingBag(player, world, meta, cX, cY, cZ);
        if (sleepingBagDeployed)
        {
            sleepingBagX = cX;
            sleepingBagY = cY;
            sleepingBagZ = cZ;
            markDirty();
        }
        return sleepingBagDeployed;
    }

    public void removeSleepingBag(World world)
    {
        if (this.sleepingBagDeployed)
        {
            BlockPos pos = new BlockPos(sleepingBagX, sleepingBagY, sleepingBagZ);
            if (world.getBlockState(pos) == ModBlocks.SLEEPING_BAG_BLOCK)
                world.destroyBlock(pos, false);
        }
        this.sleepingBagDeployed = false;
        markDirty();
    }
}