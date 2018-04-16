package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;

public class TileEntityItemSpecialRenderer extends TileEntityItemStackRenderer
{
    private TileEntityItemStackRenderer parentRenderer;

    public TileEntityItemSpecialRenderer(TileEntityItemStackRenderer renderer)
    {
        this.parentRenderer = renderer;
    }

    @Override
    public void renderByItem(ItemStack stack)
    {
        if (stack.getItem() == ModItems.ADVENTURE_BACKPACK)
        {
            BackpackRenderer.renderItem(stack);
            return;
        }

        parentRenderer.renderByItem(stack);
    }
}
