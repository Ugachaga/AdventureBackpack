package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpackBlock;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.util.Resources;

public final class BackpackRenderer extends WearableRenderer
{
    private BackpackRenderer() {}

    public static class TileEntity extends TileEntitySpecialRenderer<TileBackpack>
    {
        private static final ModelBackpackBlock MODEL_BACKPACK = new ModelBackpackBlock();

        @Override
        public void render(TileBackpack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
        {
            int rotation = te.getBlockMetadata() * 90;
            bindTexture(Resources.getBackpackTexture(te.getType()));

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            //GlStateManager.scale(0.8F, 0.8F, 0.8F);
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F); // flip from head to legs
            GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F); // world direction

            //MODEL_BACKPACK.render(0.0625F, te);
            MODEL_BACKPACK.renderTileEntity(te, 0.05F); //TODO scale to 0.0625, prescale to 0.8
            //FIXME CC fluid renderer doesn't reset some GL stuff after work, so need to find how to reset it manually. it's also blending-related (Slime backpack). reminder: black shield inventory icon.

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    public static class Layer extends WearableLayer
    {
        private static final ModelBackpackBlock MODEL_BACKPACK = new ModelBackpackBlock();

        @Override
        public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            ItemStack stack = null;

            if (player.getHeldItemMainhand().getItem() == ModItems.ADVENTURE_BACKPACK) //TODO capability: isWearingBackpack
                stack = player.getHeldItemMainhand();

            if (stack == null)
                return;

            IInventoryBackpack backpack = new InventoryBackpack(stack);
            Minecraft.getMinecraft().renderEngine.bindTexture(Resources.getBackpackTexture(backpack.getType()));

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.8F, 0.8F, 0.8F);
            if (player.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.236F, 0.45F);
                GlStateManager.rotate(SNEAK_ANGLE, 1.0F, 0.0F, 0.0F);
            }
            else
            {
                GlStateManager.translate(0.0F, 0.205F, 0.4F);
            }

            MODEL_BACKPACK.renderLayer(backpack, 0.0625F);

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    public void renderItem(ItemStack stack, float scale)
    {
        //TODO
    }

}
