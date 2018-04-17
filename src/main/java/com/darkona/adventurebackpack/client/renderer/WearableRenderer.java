package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.init.ModItems;

@SuppressWarnings("WeakerAccess")
public abstract class WearableRenderer
{
    protected static final TextureManager TEXTURE_MANAGER = Minecraft.getMinecraft().renderEngine;
    protected static final float SNEAK_ANGLE = 0.5F * (180.0F / (float) Math.PI);

    public static class WearableLayer implements LayerRenderer<EntityPlayer>
    {
        @Override
        public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch, float scale)
        {
            if (player.isInvisible()) return;

            ItemStack stack = player.getHeldItemMainhand(); //TODO capability: isWearingWearable
            Item held = stack.getItem();

            if (held == ModItems.ADVENTURE_BACKPACK)
            {
                BackpackRenderer.Layer.render(player, stack);
            }
            else if (held == ModItems.COPTER_PACK)
            {
                CopterRenderer.Layer.render(player, stack);
            }
            else if (held == ModItems.STEAM_JETPACK)
            {
                JetpackRenderer.Layer.render(player, stack);
            }
        }

        @Override
        public boolean shouldCombineTextures()
        {
            return false;
        }
    }
}
