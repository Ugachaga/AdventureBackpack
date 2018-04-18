package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.block.TileCampfire;
import com.darkona.adventurebackpack.client.models.ModelCampfire;
import com.darkona.adventurebackpack.util.Resources;

public final class CampfireRenderer
{
    private CampfireRenderer() {}

    public static class TileEntity extends TileEntitySpecialRenderer<TileCampfire>
    {
        private static final ModelCampfire MODEL_CAMPFIRE = new ModelCampfire();
        private static final ResourceLocation TEXTURE_CAMPFIRE = Resources.getModelTexture("campfire");

        @Override
        public void render(TileCampfire te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
        {
            bindTexture(TEXTURE_CAMPFIRE);

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translate((float) x + 0.5F, (float) y + 1.2F, (float) z + 0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

            MODEL_CAMPFIRE.renderTileEntity(0.05F);

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }
}
