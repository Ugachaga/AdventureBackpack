package com.darkona.adventurebackpack.block;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileCampfire extends TileEntity
{
    private int burnTicks;
    private ItemStack[] foodCooking = new ItemStack[4];

    public TileCampfire()
    {

    }
}
