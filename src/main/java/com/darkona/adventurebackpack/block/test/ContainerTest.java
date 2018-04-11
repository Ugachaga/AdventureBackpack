package com.darkona.adventurebackpack.block.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.darkona.adventurebackpack.inventory.SlotFluid;
import com.darkona.adventurebackpack.inventory.SlotTool;

import static com.darkona.adventurebackpack.common.Constants.BUCKET_IN_LEFT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_IN_RIGHT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_OUT_LEFT;
import static com.darkona.adventurebackpack.common.Constants.BUCKET_OUT_RIGHT;
import static com.darkona.adventurebackpack.common.Constants.TOOL_LOWER;
import static com.darkona.adventurebackpack.common.Constants.TOOL_UPPER;

public class ContainerTest extends Container
{
    protected static final int PLAYER_INV_ROWS = 3;
    protected static final int PLAYER_INV_COLUMNS = 9;
    protected static final int PLAYER_HOT_START = 0;
    protected static final int PLAYER_HOT_END = PLAYER_HOT_START + PLAYER_INV_COLUMNS - 1;
    protected static final int PLAYER_INV_END = PLAYER_HOT_END + PLAYER_INV_COLUMNS * PLAYER_INV_ROWS;
    protected static final int PLAYER_INV_LENGTH = PLAYER_INV_END + 1;


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


    public ContainerTest(InventoryPlayer invPlayer, TileTest te)
    {
        makeSlots(invPlayer, te);
    }

    private void makeSlots(InventoryPlayer invPlayer, TileTest te)
    {
        bindPlayerInventory(invPlayer, 44, 125); // 4*9 playerInventory, 36 Slots [0-35]
        IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

        int startX = 62;
        int startY = 7;
        for (int row = 0; row < BACK_INV_ROWS; row++) // 6*8 inventory, 48 Slots (#1-#48) [36-83]
        {
            for (int col = 0; col < BACK_INV_COLUMNS; col++)
            {
                int offsetX = startX + (18 * col);
                int offsetY = startY + (18 * row);

                addSlotToContainer(new SlotTest(inventory, (row * BACK_INV_COLUMNS + col), offsetX, offsetY));
            }
        }

        addSlotToContainer(new SlotTest(inventory, TOOL_UPPER, 44, 79)); // #49 [84]
        addSlotToContainer(new SlotTest(inventory, TOOL_LOWER, 44, 97)); // #50 [85]

        addSlotToContainer(new SlotTest(inventory, BUCKET_IN_LEFT, 6, 7)); // #51 [86]
        addSlotToContainer(new SlotTest(inventory, BUCKET_OUT_LEFT, 6, 37)); // #52 [87]
        addSlotToContainer(new SlotTest(inventory, BUCKET_IN_RIGHT, 226, 7)); // #53 [88]
        addSlotToContainer(new SlotTest(inventory, BUCKET_OUT_RIGHT, 226, 37)); // #54 [89]
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
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot)
    {
        Slot slot = getSlot(fromSlot);

        if (slot.getStack().isEmpty())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ItemStack result = stack.copy();

        if (fromSlot < PLAYER_INV_LENGTH)
        {
            if (!mergeBackpackInv(stack))
                return ItemStack.EMPTY;
        }
        else
        {
            if (!mergePlayerInv(stack))
                return ItemStack.EMPTY;
        }

        if (stack.isEmpty())
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

    private boolean mergeBackpackInv(ItemStack stack)
    {
        return mergeItemStack(stack, BACK_INV_START, BACK_INV_END + 1, false);
    }

}