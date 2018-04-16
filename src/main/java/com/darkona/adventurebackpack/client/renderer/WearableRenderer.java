package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings("WeakerAccess")
public abstract class WearableRenderer
{
    // see: ModelBiped#setRotationAngles, ModelRenderer#renderWithRotation
    protected static final float SNEAK_ANGLE = 0.5F * (180.0F / (float) Math.PI);

    public abstract static class WearableLayer implements LayerRenderer<EntityPlayer>
    {
        @Override
        public boolean shouldCombineTextures()
        {
            return false;
        }
    }
}
