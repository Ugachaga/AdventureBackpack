package com.darkona.adventurebackpack.block.test;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import com.darkona.adventurebackpack.common.Constants.Source;

public class SlotTest extends SlotItemHandler
{
    private IItemHandler itemHandler;
    private Source source = Source.TILE;


    public SlotTest(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
    }

    @Override
    public void onSlotChanged()
    {
        if (source == Source.TILE && itemHandler instanceof TileTest)
        {
            System.out.println("mark dirty");
            ((TileTest) itemHandler).markDirty();
        }
    }
}
