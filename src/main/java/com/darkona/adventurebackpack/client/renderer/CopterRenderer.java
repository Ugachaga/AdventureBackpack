package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.client.models.ModelCopter;
import com.darkona.adventurebackpack.util.Resources;

public final class CopterRenderer extends WearableRenderer
{
    private CopterRenderer() {}

    public static class Layer
    {
        private static final ResourceLocation TEXTURE_COPTER = Resources.getModelTexture("copter");
        private static final ModelCopter MODEL_COPTER = new ModelCopter();

        public static void render(EntityPlayer player, ItemStack stack)
        {
            TEXTURE_MANAGER.bindTexture(TEXTURE_COPTER);

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translate(0.0F, 0.0F, 0.1F);
            if (player.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.14F, -0.03F);
                GlStateManager.rotate(SNEAK_ANGLE, 1.0F, 0.0F, 0.0F);
            }

            MODEL_COPTER.renderLayer(player, stack, 0.0625F);

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

}
