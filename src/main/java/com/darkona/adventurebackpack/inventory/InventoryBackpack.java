package com.darkona.adventurebackpack.inventory;


import com.darkona.adventurebackpack.block.BlockAdventureBackpack;
import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.common.BackpackAbilities;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.common.IInventoryAdventureBackpack;
import com.darkona.adventurebackpack.item.ItemAdventureBackpack;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */
public class InventoryBackpack implements IInventoryAdventureBackpack
{
    public ItemStack[] inventory = new ItemStack[Constants.inventorySize];
    private FluidTank leftTank = new FluidTank(Constants.basicTankCapacity);
    private FluidTank rightTank = new FluidTank(Constants.basicTankCapacity);

    public ItemStack getContainerStack()
    {
        return containerStack;
    }

    public void setContainerStack(ItemStack containerStack)
    {
        this.containerStack = containerStack;
    }

    public static final boolean OFF = false;
    public static final boolean ON = true;

    private boolean disableNVision = OFF;
    private boolean disableCycling = OFF;

    private ItemStack containerStack;
    private String colorName = "Standard";
    private int lastTime = 0;
    private boolean special = false;
    public NBTTagCompound extendedProperties = new NBTTagCompound();

    public InventoryBackpack(ItemStack backpack)
    {
        containerStack = backpack;
        if(!backpack.hasTagCompound())
        {
            backpack.setTagCompound(new NBTTagCompound());
            saveToNBT(backpack.getTagCompound());
        }
        loadFromNBT(backpack.getTagCompound());
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
    public ItemStack[] getInventory()
    {
        return inventory;
    }

    @Override
    public TileAdventureBackpack getTile()
    {
        return null;
    }

    @Override
    public ItemStack getParentItemStack()
    {
        return this.containerStack;
    }

    @Override
    public String getColorName()
    {
        return colorName;
    }

    @Override
    public String getName()
    {
        //TODO: return some thing sensible here
        return "Adventure Backpack";
    }

    @Override
    public int getLastTime()
    {
        return this.lastTime;
    }

    @Override
    public NBTTagCompound getExtendedProperties()
    {
        return extendedProperties;
    }

    @Override
    public void setExtendedProperties(NBTTagCompound properties)
    {
        this.extendedProperties = properties;
    }

    @Override
    public boolean hasCustomName()
    {
        return true;
    }

    @Override
    public boolean isSpecial()
    {
        return special;
    }

    @Override
    public void saveTanks(NBTTagCompound compound)
    {
        compound.setTag("rightTank", rightTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("leftTank", leftTank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadTanks(NBTTagCompound compound)
    {
        leftTank.readFromNBT(compound.getCompoundTag("leftTank"));
        rightTank.readFromNBT(compound.getCompoundTag("rightTank"));
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


    @Override
    public boolean isSBDeployed()
    {
        return false;
    }

    @Override
    public void setLastTime(int time)
    {
        this.lastTime = time;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if (slot > inventory.length) return;
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
        dirtyInventory();
    }

    @Override
    public void setInventorySlotContentsNoSave(int slot, ItemStack stack)
    {
        if (slot > inventory.length) return;
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int quantity)
    {
        ItemStack itemstack = getStackInSlot(slot);
        if (itemstack != null)
        {
            if (itemstack.stackSize <= quantity)
            {
                setInventorySlotContents(slot, null);
            } else
            {
                itemstack = itemstack.splitStack(quantity);
            }
        }
        return itemstack;
    }
    @Override
    public ItemStack decrStackSizeNoSave(int slot, int quantity)
    {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null)
        {
            if (stack.stackSize <= quantity)
            {
                setInventorySlotContentsNoSave(slot, null);
            } else
            {
                stack = stack.splitStack(quantity);
            }
        }
        return stack;
    }

    @Override
    public boolean updateTankSlots()
    {
        return InventoryActions.transferContainerTank(this, getLeftTank(), Constants.bucketInLeft) ||
                InventoryActions.transferContainerTank(this, getRightTank(), Constants.bucketInRight);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound)
    {
        if(compound.hasKey("backpackData"))
        {
            NBTTagCompound backpackData = compound.getCompoundTag("backpackData");
            NBTTagList items = backpackData.getTagList("ABPItems", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < items.tagCount(); i++)
            {
                NBTTagCompound item = items.getCompoundTagAt(i);
                byte slot = item.getByte("Slot");
                if (slot >= 0 && slot < inventory.length)
                {
                    inventory[slot] = ItemStack.loadItemStackFromNBT(item);
                }
            }

            leftTank.readFromNBT(backpackData.getCompoundTag("leftTank"));
            rightTank.readFromNBT(backpackData.getCompoundTag("rightTank"));
            colorName = backpackData.getString("colorName");
            lastTime = backpackData.getInteger("lastTime");
            special = backpackData.getBoolean("special");
            extendedProperties = backpackData.getCompoundTag("extendedProperties");
            disableCycling = backpackData.getBoolean("disableCycling");
            disableNVision = backpackData.getBoolean("disableNVision");
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound compound)
    {
       // if(Utils.inServer())
       // {
        NBTTagCompound backpackData = new NBTTagCompound();
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < inventory.length; i++)
        {
            ItemStack stack = inventory[i];
            if (stack != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }
        backpackData.removeTag("ABPItems");
        backpackData.setTag("ABPItems", items);
        backpackData.setString("colorName", colorName);
        backpackData.setInteger("lastTime", lastTime);
        backpackData.setBoolean("special", BackpackAbilities.hasAbility(colorName));
        backpackData.setTag("extendedProperties", extendedProperties);
        backpackData.setTag("rightTank", rightTank.writeToNBT(new NBTTagCompound()));
        backpackData.setTag("leftTank", leftTank.writeToNBT(new NBTTagCompound()));
        backpackData.setBoolean("disableCycling", disableCycling);
        backpackData.setBoolean("disableNVision", disableNVision);

        compound.setTag("backpackData",backpackData);
        //}
    }

    @Override
    public FluidTank[] getTanksArray()
    {
        FluidTank[] array = {leftTank,rightTank};
        return array;
    }

    //@Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (slot == Constants.bucketInLeft || slot == Constants.bucketInRight || slot == Constants.bucketOutLeft || slot == Constants.bucketOutRight)
        {
            return inventory[slot];
        }
        return null;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentString("Adventure Backpack");
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {

        saveToNBT(containerStack.getTagCompound());
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        loadFromNBT(containerStack.getTagCompound());
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
       /* if(Utils.inServer())
        {*/
        saveToNBT(containerStack.getTagCompound());
       // }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (stack.getItem() instanceof ItemAdventureBackpack || Block.getBlockFromItem(stack.getItem()) instanceof BlockAdventureBackpack)
        {
            return false;
        }
        if (slot == Constants.bucketInRight || slot == Constants.bucketInLeft)
        {
            return FluidContainerRegistry.isContainer(stack);
        }

        return !(slot == Constants.upperTool || slot == Constants.lowerTool) || SlotTool.isValidTool(stack);
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public void dirtyTanks()
    {
        containerStack.getTagCompound().getCompoundTag("backpackData").setTag("leftTank",leftTank.writeToNBT(new NBTTagCompound()));
        containerStack.getTagCompound().getCompoundTag("backpackData").setTag("rightTank",rightTank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void dirtyTime()
    {
        containerStack.getTagCompound().getCompoundTag("backpackData").setInteger("lastTime",lastTime);
    }

    @Override
    public void dirtyExtended()
    {
        containerStack.getTagCompound().getCompoundTag("backpackData").removeTag("extendedProperties");
        containerStack.getTagCompound().getCompoundTag("backpackData").setTag("extendedProperties",extendedProperties);
    }

    @Override
    public void dirtyInventory()
    {
        if(updateTankSlots()){
            dirtyTanks();
        }
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < inventory.length; i++)
        {
            ItemStack stack = inventory[i];
            if (stack != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }
        containerStack.getTagCompound().getCompoundTag("backpackData").removeTag("ABPItems");
        containerStack.getTagCompound().getCompoundTag("backpackData").setTag("ABPItems", items);
    }

    @Override
    public void clear()
    {
        //TODO: clear the inventory
    }

    @Override
    public int getFieldCount()
    {
        return inventory.length;
    }

    @Override
    public void setField(int id, int type)
    {
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public ItemStack removeStackFromSlot(int id)
    {
        return inventory[id] = null;
    }

    public boolean hasBlock(Block block)
    {
        return InventoryActions.hasBlockItem(this, block);
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

}