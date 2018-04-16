package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.client.models.ModelBackpackBlock;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.util.Resources;

public class TileEntityItemSpecialRenderer extends TileEntityItemStackRenderer
{
    private static final ModelBackpackBlock MODEL_BACKPACK = new ModelBackpackBlock();

    private TileEntityItemStackRenderer parentRenderer;
    private Minecraft mc = Minecraft.getMinecraft();

    public TileEntityItemSpecialRenderer(TileEntityItemStackRenderer renderer)
    {
        this.parentRenderer = renderer;
    }

    @Override
    public void renderByItem(ItemStack stack)
    {
        if (stack.getItem() != ModItems.ADVENTURE_BACKPACK)
        {
            parentRenderer.renderByItem(stack);
            return;
        }

        IInventoryBackpack backpack = new InventoryBackpack(stack);
        ResourceLocation modelTexture = Resources.getBackpackTexture(backpack.getType());
        mc.getTextureManager().bindTexture(modelTexture);

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

        MODEL_BACKPACK.renderTileEntity(backpack, 0.05F);

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }
}
