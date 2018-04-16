package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.client.models.ModelCopter;
import com.darkona.adventurebackpack.reference.ModInfo;

public final class CopterRenderer extends WearableRenderer
{
    private CopterRenderer() {}

    public static class Layer
    {
        private static final ResourceLocation TEXTURE_COPTER = new ResourceLocation(ModInfo.MODID, "textures/models/copter.png");
        private static final ModelCopter MODEL_COPTER = new ModelCopter();

        public static void render(EntityPlayer player, ItemStack stack)
        {
            TEXTURE_MANAGER.bindTexture(TEXTURE_COPTER);

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.8F, 0.8F, 0.8F);
            if (player.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.3F, 0.15F); //TODO tune it
                GlStateManager.rotate(SNEAK_ANGLE, 1.0F, 0.0F, 0.0F);
            }
            else
            {
                GlStateManager.translate(0.0F, 0.1F, 0.1F);
            }

            MODEL_COPTER.renderLayer(player, stack, 0.0625F);

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

}
