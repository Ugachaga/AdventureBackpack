package com.darkona.adventurebackpack.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.common.Constants.Source;

/**
 * Created on 10.04.2017
 *
 * @author Ugachaga
 */
@SuppressWarnings("WeakerAccess")
public abstract class ContainerAdventure extends Container
{
    protected static final int PLAYER_INV_ROWS = 3;
    protected static final int PLAYER_INV_COLUMNS = 9;
    protected static final int PLAYER_HOT_START = 0;
    protected static final int PLAYER_HOT_END = PLAYER_HOT_START + PLAYER_INV_COLUMNS - 1;
    protected static final int PLAYER_INV_END = PLAYER_HOT_END + PLAYER_INV_COLUMNS * PLAYER_INV_ROWS;
    protected static final int PLAYER_INV_LENGTH = PLAYER_INV_END + 1;

    protected final EntityPlayer player;
    protected final IInventoryTanks inventory;
    protected final Source source;

    private final int[] fluidsAmount;
    private int itemsCount;

    protected ContainerAdventure(EntityPlayer player, IInventoryTanks inventory, Source source)
    {
        this.player = player;
        this.inventory = inventory;
        this.source = source;
        this.fluidsAmount = new int[this.inventory.getTanksArray().length];
    }

    protected void bindPlayerInventory(InventoryPlayer invPlayer, int startX, int startY)
    {
        for (int col = 0; col < PLAYER_INV_COLUMNS; col++) // hotbar - 9 slots
            addSlotToContainer(new Slot(invPlayer, col, (startX + 18 * col), (58 + startY)));

        for (int row = 0; row < PLAYER_INV_ROWS; row++) // inventory - 3*9, 27 slots
            for (int col = 0; col < PLAYER_INV_COLUMNS; col++)
                addSlotToContainer(new Slot(invPlayer, (PLAYER_INV_COLUMNS + row * PLAYER_INV_COLUMNS + col), (startX + 18 * col), (startY + row * 18)));
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (source == Source.HOLDING) // used for refresh tooltips and redraw tanks content while GUI is open
        {
            if ((detectItemChanges() | detectFluidChanges()) && player instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP) player).sendAllContents(this, this.getInventory());
            }
        }
    }

    protected boolean detectItemChanges()
    {
        ItemStack[] inv = inventory.getInventory();
        int tempCount = 0;
        for (int i = 0; i < inv.length - inventory.getSlotsOnClosing().length; i++)
        {
            if (inv[i] != null)
                tempCount++;
        }
        if (itemsCount != tempCount)
        {
            itemsCount = tempCount;
            return true;
        }
        return false;
    }

    private boolean detectFluidChanges()
    {
        boolean changesDetected = false;
        for (int i = 0; i < fluidsAmount.length; i++)
        {
            int amount = inventory.getTanksArray()[i].getFluidAmount();
            if (fluidsAmount[i] != amount)
            {
                fluidsAmount[i] = amount;
                changesDetected = true;
            }
        }
        return changesDetected;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot)
    {
        Slot slot = getSlot(fromSlot);

        if (slot.getStack() == ItemStack.EMPTY)
            return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ItemStack result = stack.copy();

        if (fromSlot < PLAYER_INV_LENGTH)
        {
            if (!transferStackToPack(stack))
                return ItemStack.EMPTY;
        }
        else
        {
            if (!mergePlayerInv(stack))
                return ItemStack.EMPTY;
        }

        if (stack.getCount() == 0)
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else
        {
            slot.onSlotChanged();
        }

        if (stack.getCount() == result.getCount())
            return ItemStack.EMPTY;

        slot.onSlotChanged();
        return result;
    }

    protected boolean mergePlayerInv(ItemStack stack)
    {
        return mergeItemStack(stack, PLAYER_HOT_START, PLAYER_INV_END + 1, false);
    }

    protected abstract boolean transferStackToPack(ItemStack stack);

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player)
    {
        if (source == Source.HOLDING && slot >= 0)
        {
            if (getSlot(slot).getStack() == player.getHeldItemMainhand()) //TODO dupe check
            {
                return ItemStack.EMPTY;
            }
            if (clickType == ClickType.SWAP/*QUICK_MOVE*/ && getSlot(dragType).getStack() == player.getHeldItemMainhand()) //TODO dupe check
            {
                return ItemStack.EMPTY;
            }
        }
        return super.slotClick(slot, dragType, clickType, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return inventory.isUsableByPlayer(player);
    }

    @Override
    protected boolean mergeItemStack(ItemStack initStack, int minIndex, int maxIndex, boolean backward)
    {
        boolean changesMade = false;
        int activeIndex = (backward ? maxIndex - 1 : minIndex);
        Slot activeSlot;
        ItemStack activeStack;

        if (initStack.isStackable())
        {
            while (initStack.getCount() > 0 && (!backward && activeIndex < maxIndex || backward && activeIndex >= minIndex))
            {
                activeSlot = (Slot) this.inventorySlots.get(activeIndex);
                activeStack = activeSlot.getStack();

                if (activeStack.getItem() == initStack.getItem()
                        && (!initStack.getHasSubtypes() || initStack.getItemDamage() == activeStack.getItemDamage())
                        && ItemStack.areItemStackTagsEqual(initStack, activeStack))
                {
                    int mergedSize = activeStack.getCount() + initStack.getCount();
                    int maxStackSize = Math.min(initStack.getMaxStackSize(), activeSlot.getSlotStackLimit());

                    if (mergedSize <= maxStackSize)
                    {
                        initStack.setCount(0);
                        activeStack.setCount(mergedSize);
                        activeSlot.onSlotChanged();
                        changesMade = true;
                    }
                    else if (activeStack.getCount() < maxStackSize && !(activeSlot instanceof SlotFluid))
                    {
                        initStack.shrink(maxStackSize - activeStack.getCount());
                        activeStack.setCount(maxStackSize);
                        activeSlot.onSlotChanged();
                        changesMade = true;
                    }
                }
                activeIndex += (backward ? -1 : 1);
            }
        }

        if (initStack.getCount() > 0)
        {
            activeIndex = (backward ? maxIndex - 1 : minIndex);

            while (!backward && activeIndex < maxIndex || backward && activeIndex >= minIndex)
            {
                activeSlot = (Slot) this.inventorySlots.get(activeIndex);
                activeStack = activeSlot.getStack();

                if (activeStack == ItemStack.EMPTY /*&& activeSlot.isItemValid(initStack)*/)
                {
                    ItemStack copyStack = initStack.copy();
                    int mergedSize = Math.min(copyStack.getCount(), activeSlot.getSlotStackLimit());
                    copyStack.setCount(mergedSize);

                    activeSlot.putStack(copyStack);
                    if (mergedSize >= initStack.getCount())
                    {
                        initStack.setCount(0);
                    }
                    else
                    {
                        initStack.shrink(mergedSize);
                    }
                    changesMade = true;
                    break;
                }
                activeIndex += (backward ? -1 : 1);
            }
        }

        return changesMade;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!player.world.isRemote)
        {
            dropContentOnClose();
        }
    }

    protected void dropContentOnClose()
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemstack = inventory.removeStackFromSlot(i);
            if (itemstack != ItemStack.EMPTY)
            {
                inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                player.dropItem(itemstack, false);
            }
        }
    }
}