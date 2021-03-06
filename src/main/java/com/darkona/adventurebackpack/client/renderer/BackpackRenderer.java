package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpack;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Resources;

public final class BackpackRenderer extends WearableRenderer
{
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final ModelBackpack MODEL_BACKPACK = new ModelBackpack(ModelBackpack.RenderType.ITEM);

    private BackpackRenderer() {}

    static void renderItem(ItemStack stack)
    {
        IInventoryBackpack backpack = new InventoryBackpack(stack);
        MC.getTextureManager().bindTexture(Resources.getBackpackTexture(backpack.getType()));

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

        MODEL_BACKPACK.renderTileEntity(backpack, 0.05F);

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    public static class TileEntity extends TileEntitySpecialRenderer<TileBackpack>
    {
        private static final ModelBackpack MODEL_BACKPACK = new ModelBackpack(ModelBackpack.RenderType.TILE);

        @Override
        public void render(TileBackpack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
        {
            int rotation = te.getBlockMetadata() * 90;
            bindTexture(Resources.getBackpackTexture(te.getType()));

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F); // flip from head to legs
            GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F); // world direction

            MODEL_BACKPACK.renderTileEntity(te, 0.05F);

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    public static class Layer
    {
        private static final ModelBackpack MODEL_BACKPACK = new ModelBackpack(ModelBackpack.RenderType.LAYER);

        public static void render(EntityPlayer player, ItemStack stack)
        {
            TEXTURE_MANAGER.bindTexture(Resources.getBackpackTexture(BackpackTypes.getType(stack)));

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.9F, 0.9F, 0.9F);
            GlStateManager.translate(0.0F, 0.2F, 0.46F);
            if (player.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.0F, 0.06F);
                GlStateManager.rotate(SNEAK_ANGLE, 1.0F, 0.0F, 0.0F);
            }

            MODEL_BACKPACK.renderLayer(player, stack, 0.0625F);
            //TODO see: LayerArmorBase#renderEnchantedGlint

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

}
