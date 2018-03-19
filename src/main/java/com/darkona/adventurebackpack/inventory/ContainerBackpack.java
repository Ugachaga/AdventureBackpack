package com.darkona.adventurebackpack.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.common.Constants.Source;

import static com.darkona.adventurebackpack.common.Constants.BUCKET_IN_LEFT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_IN_RIGHT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_OUT_LEFT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_OUT_RIGHT;
import static com.darkona.adventurebackpack.common.Constants.TOOL_LOWER;
import static com.darkona.adventurebackpack.common.Constants.TOOL_UPPER;

public class ContainerBackpack extends ContainerWearable
{
    private static final int BACK_INV_ROWS = 6;
    private static final int BACK_INV_COLUMNS = 8;
    private static final int BACK_INV_START = PLAYER_INV_END + 1;
    private static final int BACK_INV_END = PLAYER_INV_END + (BACK_INV_ROWS * BACK_INV_COLUMNS);
    private static final int TOOL_START = BACK_INV_END + 1;
    private static final int TOOL_END = TOOL_START + 1;
    private static final int BUCKET_LEFT = TOOL_END + 1;
    private static final int BUCKET_RIGHT = BUCKET_LEFT + 2;
    private static final int MATRIX_DIMENSION = 3;
    private static final int CRAFT_RESULT = BUCKET_RIGHT + 2 + (MATRIX_DIMENSION * MATRIX_DIMENSION);
    private static final int[] CRAFT_MATRIX_EMULATION = findCraftMatrixEmulationIDs();

    private InventoryCraftingBackpack craftMatrix = new InventoryCraftingBackpack(this, MATRIX_DIMENSION, MATRIX_DIMENSION);
    private InventoryCraftResult craftResult = new InventoryCraftResult();

    public ContainerBackpack(EntityPlayer player, IInventoryBackpack backpack, Source source)
    {
        super(player, backpack, source);
        makeSlots(player.inventory);
        inventory.openInventory(player);
    }

    public IInventoryBackpack getInventoryBackpack()
    {
        return (IInventoryBackpack) inventory;
    }

    private void makeSlots(InventoryPlayer invPlayer)
    {
        bindPlayerInventory(invPlayer, 44, 125); // 4*9 playerInventory, 36 Slots [0-35]

        int startX = 62;
        int startY = 7;
        for (int row = 0; row < BACK_INV_ROWS; row++) // 6*8 inventory, 48 Slots (#1-#48) [36-83]
        {
            for (int col = 0; col < BACK_INV_COLUMNS; col++)
            {
                int offsetX = startX + (18 * col);
                int offsetY = startY + (18 * row);

                addSlotToContainer(new SlotBackpack((IInventoryBackpack) inventory, (row * BACK_INV_COLUMNS + col), offsetX, offsetY));
            }
        }

        addSlotToContainer(new SlotTool(inventory, TOOL_UPPER, 44, 79)); // #49 [84]
        addSlotToContainer(new SlotTool(inventory, TOOL_LOWER, 44, 97)); // #50 [85]

        addSlotToContainer(new SlotFluid(inventory, BUCKET_IN_LEFT, 6, 7)); // #51 [86]
        addSlotToContainer(new SlotFluid(inventory, BUCKET_OUT_LEFT, 6, 37)); // #52 [87]
        addSlotToContainer(new SlotFluid(inventory, BUCKET_IN_RIGHT, 226, 7)); // #53 [88]
        addSlotToContainer(new SlotFluid(inventory, BUCKET_OUT_RIGHT, 226, 37)); // #54 [89]

        startX = 215;
        startY = -2500;
        //startY = LoadedMods.DEV_ENV ? 125 : -2500;
        for (int row = 0; row < MATRIX_DIMENSION; row++) // craftMatrix, usually 9 slots, [90-98]
        {
            for (int col = 0; col < MATRIX_DIMENSION; col++)
            {
                int offsetX = startX + (18 * col);
                int offsetY = startY + (18 * row);
                addSlotToContainer(new SlotCraftMatrix(craftMatrix, (row * MATRIX_DIMENSION + col), offsetX, offsetY));
            }
        }

        addSlotToContainer(new SlotCraftResult(this, invPlayer.player, craftMatrix, craftResult, 0, 226, 97)); // craftResult [99]
        syncCraftMatrixWithInventory(true);
    }

    @Override
    public void detectAndSendChanges()
    {
        syncCraftResultToServer();
        super.detectAndSendChanges();
    }

    private void syncCraftResultToServer()
    {
        ItemStack stackA = inventorySlots.get(CRAFT_RESULT).getStack();
        ItemStack stackB = inventoryItemStacks.get(CRAFT_RESULT);

        if (!ItemStack.areItemStacksEqual(stackB, stackA))
        {
            stackB = stackA.isEmpty() ? ItemStack.EMPTY : stackA.copy();
            inventoryItemStacks.set(CRAFT_RESULT, stackB);

            if (player instanceof EntityPlayerMP)
                ((EntityPlayerMP) player).sendAllContents(this, this.getInventory());
        }
    }

    @Override
    protected boolean transferStackToPack(ItemStack stack)
    {
        if (SlotTool.isValidTool(stack))
        {
            if (!mergeToolSlot(stack))
                if (SlotBackpack.isValidItem(stack))
                    return mergeBackpackInv(stack);
        }
        else if (SlotFluid.isContainer(stack) && !isHoldingSpace())
        {
            return transferFluidContainer(stack);
        }
        else if (SlotBackpack.isValidItem(stack))
        {
            return mergeBackpackInv(stack);
        }
        return true;
    }

    private boolean mergeToolSlot(ItemStack stack)
    {
        return mergeItemStack(stack, TOOL_START, TOOL_END + 1, false);
    }

    private boolean mergeBackpackInv(ItemStack stack)
    {
        return mergeItemStack(stack, BACK_INV_START, BACK_INV_END + 1, false);
    }

    private boolean mergeLeftBucket(ItemStack stack)
    {
        return mergeItemStack(stack, BUCKET_LEFT, BUCKET_LEFT + 1, false);
    }

    private boolean mergeRightBucket(ItemStack stack)
    {
        return mergeItemStack(stack, BUCKET_RIGHT, BUCKET_RIGHT + 1, false);
    }

    private boolean isHoldingSpace()
    {
        return getInventoryBackpack().getExtendedProperties().hasKey(Constants.TAG_HOLDING_SPACE);
    }

    private boolean transferFluidContainer(ItemStack container)
    {
        FluidTank leftTank = getInventoryBackpack().getLeftTank();
        FluidTank rightTank = getInventoryBackpack().getRightTank();
        ItemStack leftStackOut = getSlot(BUCKET_LEFT + 1).getStack();
        ItemStack rightStackOut = getSlot(BUCKET_RIGHT + 1).getStack();

        boolean isLeftTankEmpty = SlotFluid.isEmpty(leftTank);
        boolean isRightTankEmpty = SlotFluid.isEmpty(rightTank);
        boolean suitableToLeft = SlotFluid.isEqualAndCanFit(container, leftTank);
        boolean suitableToRight = SlotFluid.isEqualAndCanFit(container, rightTank);
        boolean areLeftSameType = InventoryActions.areContainersOfSameType(container, leftStackOut);
        boolean areRightSameType = InventoryActions.areContainersOfSameType(container, rightStackOut);

        if (SlotFluid.isFilled(container))
        {
            if (isLeftTankEmpty)
            {
                if (!isRightTankEmpty && suitableToRight && (rightStackOut.isEmpty() || areRightSameType))
                    return mergeRightBucket(container);
                else if (leftStackOut.isEmpty() || areLeftSameType)
                    return mergeLeftBucket(container);
            }
            else
            {
                if (suitableToLeft && (leftStackOut.isEmpty() || areLeftSameType))
                    return mergeLeftBucket(container);
                else if ((isRightTankEmpty || suitableToRight) && (rightStackOut.isEmpty() || areRightSameType))
                    return mergeRightBucket(container);
                else if (leftStackOut.isEmpty() && rightStackOut.isEmpty() && SlotBackpack.isValidItem(container))
                    return mergeBackpackInv(container);
            }
        }
        else if (SlotFluid.isEmpty(container))
        {
            if (isLeftTankEmpty)
            {
                if (!isRightTankEmpty && (rightStackOut.isEmpty() || areRightSameType))
                    return mergeRightBucket(container);
                else if (leftStackOut.isEmpty() && rightStackOut.isEmpty() && SlotBackpack.isValidItem(container))
                    return mergeBackpackInv(container);
            }
            else
            {
                if (leftStackOut.isEmpty() || areLeftSameType)
                    return mergeLeftBucket(container);
            }
        }
        return false;
    }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player)
    {
        final ItemStack result = super.slotClick(slot, dragType, clickType, player);
        syncCraftMatrixWithInventory(true);
        return result;
    }

    @Override
    protected void dropContentOnClose()
    {
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            this.craftMatrix.setInventorySlotContentsNoUpdate(i, ItemStack.EMPTY);
        }
        onCraftMatrixChanged(craftMatrix);

        super.dropContentOnClose();
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
//        if (ConfigHandler.tinkerToolsMaintenance && TinkersUtils.isToolOrWeapon(craftMatrix.getStackInSlot(4)))
//        {
//            craftResult.setInventorySlotContents(0, TinkersUtils.getTinkersRecipe(craftMatrix));
//            return;
//        }

        this.slotChangedCraftingGrid(this.player.world, this.player, this.craftMatrix, this.craftResult);
    }

    protected void syncCraftMatrixWithInventory(boolean preCraft)
    {
        boolean changesMade = false;

        for (int craftSlotID = 0; craftSlotID < CRAFT_MATRIX_EMULATION.length; craftSlotID++)
        {
            int invSlotID = CRAFT_MATRIX_EMULATION[craftSlotID];
            ItemStack invStack = inventory.getStackInSlot(invSlotID);
            ItemStack craftStack = craftMatrix.getStackInSlot(craftSlotID);

            if (!ItemStack.areItemStacksEqual(invStack, craftStack))
            {
                if (preCraft)
                {
                    craftStack = invStack.isEmpty() ? ItemStack.EMPTY : invStack.copy();
                    craftMatrix.setInventorySlotContentsNoUpdate(craftSlotID, craftStack);
                }
                else
                {
                    invStack = craftStack.isEmpty() ? ItemStack.EMPTY : craftStack.copy();
                    inventory.setInventorySlotContentsNoSave(invSlotID, invStack);
                }
                changesMade = true;
            }
        }

        if (changesMade)
        {
            if (preCraft)
                onCraftMatrixChanged(craftMatrix);
            else
                inventory.markDirty();
        }
    }

    /**
     *  Returns the array of inventory slotIDs, emulates the craftMatrix in the lower right corner of the inventory.
     */
    private static int[] findCraftMatrixEmulationIDs()
    {
        int[] slotsArray = new int[MATRIX_DIMENSION * MATRIX_DIMENSION];
        for (int row = 0; row < MATRIX_DIMENSION; row++)
        {
            for (int col = 0; col < MATRIX_DIMENSION; col++)
            {
                int id = row * MATRIX_DIMENSION + col;
                int fullRowSum = (BACK_INV_ROWS - (MATRIX_DIMENSION - 1) + row) * BACK_INV_COLUMNS;
                int columnShift = (MATRIX_DIMENSION - 1) - col;
                slotsArray[id] = fullRowSum - columnShift - 1;
            }
        }
        return slotsArray;
    }
}