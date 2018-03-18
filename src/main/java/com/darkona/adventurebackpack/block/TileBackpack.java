package com.darkona.adventurebackpack.block;

import javax.annotation.Nullable;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidTank;

import com.darkona.adventurebackpack.common.BackpackAbilities;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryActions;
import com.darkona.adventurebackpack.inventory.SlotBackpack;
import com.darkona.adventurebackpack.inventory.SlotTool;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.CoordsUtils;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

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
import static com.darkona.adventurebackpack.common.Constants.TOOL_LOWER;
import static com.darkona.adventurebackpack.common.Constants.TOOL_UPPER;

/**
 * Created by Darkona on 12/10/2014.
 */
public class TileBackpack extends TileAdventure implements IInventoryBackpack, ITickable, ISidedInventory//, IInteractionObject//, IItemHandler
{
    private static final int[] MAIN_INVENTORY_SLOTS = Utils.createSlotArray(0, Constants.INVENTORY_MAIN_SIZE);

    private BackpackTypes type = BackpackTypes.STANDARD;
    private FluidTank leftTank = new FluidTank(Constants.BASIC_TANK_CAPACITY);
    private FluidTank rightTank = new FluidTank(Constants.BASIC_TANK_CAPACITY);
    private NBTTagCompound extendedProperties = new NBTTagCompound();

    private NBTTagList ench;
    private boolean disableCycling;
    private boolean disableNVision;
    private int lastTime = 0;

    private boolean sleepingBagDeployed;
    private int sbdir;
    private int sbx;
    private int sby;
    private int sbz;

    private int checkTime = 0;
    private int luminosity = 0;

    public TileBackpack()
    {
        super(Constants.INVENTORY_SIZE);
    }

//    @Override
//    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
//    {
//        return new ContainerBackpack(player, this, Constants.Source.TILE);
//    }
//
//    @Override
//    public String getGuiID()
//    {
//        return "tile:backpack";
//    }

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
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        loadFromNBT(compound);
        sleepingBagDeployed = compound.getBoolean("sleepingbag");
        sbx = compound.getInteger("sbx");
        sby = compound.getInteger("sby");
        sbz = compound.getInteger("sbz");
        sbdir = compound.getInteger("sbdir");
        luminosity = compound.getInteger("lumen");
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("ench"))
            ench = compound.getTagList("ench", NBT.TAG_COMPOUND);

        NBTTagCompound backpackTag = compound.getCompoundTag(TAG_WEARABLE_COMPOUND);
        type = BackpackTypes.getType(backpackTag.getByte(TAG_TYPE));
        setInventoryFromTagList(backpackTag.getTagList(TAG_INVENTORY, NBT.TAG_COMPOUND));
        leftTank.readFromNBT(backpackTag.getCompoundTag(TAG_LEFT_TANK));
        rightTank.readFromNBT(backpackTag.getCompoundTag(TAG_RIGHT_TANK));
        extendedProperties = backpackTag.getCompoundTag(TAG_EXTENDED_COMPOUND);
        disableCycling = backpackTag.getBoolean(TAG_DISABLE_CYCLING);
        disableNVision = backpackTag.getBoolean(TAG_DISABLE_NVISION);
        lastTime = backpackTag.getInteger("lastTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        saveToNBT(compound);
        compound.setBoolean("sleepingbag", sleepingBagDeployed);
        compound.setInteger("sbx", sbx);
        compound.setInteger("sby", sby);
        compound.setInteger("sbz", sbz);
        compound.setInteger("sbdir", sbdir);
        compound.setInteger("lumen", luminosity);
        return compound;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound)
    {
        if (ench != null)
            compound.setTag("ench", ench);

        NBTTagCompound backpackTag = new NBTTagCompound();
        backpackTag.setByte(TAG_TYPE, BackpackTypes.getMeta(type));
        backpackTag.setTag(TAG_INVENTORY, getInventoryTagList());
        backpackTag.setTag(TAG_RIGHT_TANK, rightTank.writeToNBT(new NBTTagCompound()));
        backpackTag.setTag(TAG_LEFT_TANK, leftTank.writeToNBT(new NBTTagCompound()));
        backpackTag.setTag(TAG_EXTENDED_COMPOUND, extendedProperties);
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

    @Deprecated
    @Override
    public void dirtyExtended()
    {
        // if we really want to use it, we have to re-implement it, more efficient way
        dirtyInventory();
    }

    @Deprecated
    @Override
    public void dirtyTime()
    {
        // if we really want to use it, we have to re-implement it, more efficient way
        dirtyInventory();
    }

    @Override
    public int getLastTime()
    {
        return lastTime;
    }

    @Override
    public void setLastTime(int lastTime)
    {
        this.lastTime = lastTime;
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

    // Logic: from tile to item
    public boolean equip(World world, EntityPlayer player, BlockPos pos)
    {
        removeSleepingBag(world);

        ItemStack stacky = BackpackUtils.createBackpackStack(type);
        transferCompoundToStack(stacky);

        if (BackpackUtils.equipWearable(stacky, player) != BackpackUtils.Reasons.SUCCESSFUL)
        {
            Wearing.WearableType wtype = Wearing.getWearingWearableType(player);
            if (wtype != Wearing.WearableType.UNKNOWN)
                player.sendMessage(new TextComponentTranslation("adventurebackpack:messages.already.equipped." + wtype.name().toLowerCase()));

            if (!player.inventory.addItemStackToInventory(stacky))
                return drop(world, player, pos);
        }
        return true;
    }

    public boolean drop(World world, EntityPlayer player, BlockPos pos)
    {
        removeSleepingBag(world);

        if (player.capabilities.isCreativeMode)
            return true;

        ItemStack stacky = BackpackUtils.createBackpackStack(type);
        transferCompoundToStack(stacky);

        float spawnX = pos.getX() + world.rand.nextFloat();
        float spawnY = pos.getY() + world.rand.nextFloat();
        float spawnZ = pos.getZ() + world.rand.nextFloat();
        EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stacky);

        float mult = 0.05F;
        droppedItem.motionX = (-0.3F + world.rand.nextFloat()) * mult;
        droppedItem.motionY = (3 + world.rand.nextFloat()) * mult;
        droppedItem.motionZ = (-0.3F + world.rand.nextFloat()) * mult;

        return world.spawnEntity(droppedItem);
    }

    private void transferCompoundToStack(ItemStack stack)
    {
        NBTTagCompound compound = new NBTTagCompound();
        saveToNBT(compound);
        stack.setTagCompound(compound);
    }

    // Sleeping Bag
    @Override
    public boolean isSleepingBagDeployed()
    {
        return this.sleepingBagDeployed;
    }

    public void setSleepingBagDeployed(boolean state)
    {
        this.sleepingBagDeployed = state;
    }

    public boolean deploySleepingBag(EntityPlayer player, World world, int meta, int cX, int cY, int cZ)
    {
        if (world.isRemote)
            return false;

        sleepingBagDeployed = CoordsUtils.spawnSleepingBag(player, world, meta, cX, cY, cZ);
        if (sleepingBagDeployed)
        {
            sbx = cX;
            sby = cY;
            sbz = cZ;
            sbdir = meta;
            //world.notifyBlockUpdate(this.pos, , , ); //TODO check it
        }
        return sleepingBagDeployed;
    }

    public void removeSleepingBag(World world)
    {
        BlockPos pos = new BlockPos(sbx, sby, sbz);
        if (sleepingBagDeployed && world.getBlockState(pos).getBlock() == ModBlocks.BLOCK_SLEEPING_BAG)
            world.destroyBlock(pos, false);

        sleepingBagDeployed = false;
        markDirty();
    }

    // Automation
    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        if (GeneralReference.isDimensionAllowed(world.provider.getDimension()))
            return MAIN_INVENTORY_SLOTS;

        return null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction)
    {
        return isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (slot <= Constants.END_OF_INVENTORY)
            return SlotBackpack.isValidItem(stack);

        return (slot == TOOL_UPPER || slot == TOOL_LOWER) && SlotTool.isValidTool(stack);
    }

    // Syncing and Ticking
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        // writeToNBT(new NBTTagCompound()); //TODO should we overribe getUpdateTag() ?
        return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        //readFromNBT(pkt.getNbtCompound()); //TODO
    }

    @Override
    public void update()
    {
        //Execute this backpack's TILE ability. No, seriously. You might not infer that from the code. Just sayin'
        if (ConfigHandler.backpackAbilities && BackpackTypes.hasProperty(type, BackpackTypes.Props.TILE))
        {
            BackpackAbilities.backpackAbilities.executeTileAbility(this.world, this);
        }

        //Check for backpack luminosity and a deployed sleeping bag, just in case because i'm super paranoid.
        if (checkTime == 0)
        {
            int lastLumen = luminosity;
            int left = (leftTank.getFluid() != null) ? leftTank.getFluid().getFluid().getLuminosity() : 0;
            int right = (rightTank.getFluid() != null) ? rightTank.getFluid().getFluid().getLuminosity() : 0;
            luminosity = Math.max(left, right);
            if (luminosity != lastLumen)
            {
                world.setBlockState(this.pos, ModBlocks.BLOCK_BACKPACK.getDefaultState(), 3);
                world.setLightFor(EnumSkyBlock.BLOCK, this.pos, luminosity);
            }

            if (sleepingBagDeployed && world.getBlockState(new BlockPos(sbx, sby, sbz)) != ModBlocks.BLOCK_SLEEPING_BAG)
                sleepingBagDeployed = false;

            checkTime = 20;
        }
        else
        {
            checkTime--;
        }
    }

    public int getLuminosity()
    {
        return luminosity;
    }
}