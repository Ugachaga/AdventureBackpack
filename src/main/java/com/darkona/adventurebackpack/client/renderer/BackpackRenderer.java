package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpackBlock;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Resources;

public final class BackpackRenderer
{
    // see: ModelBiped#setRotationAngles, ModelRenderer#renderWithRotation
    private static final float SNEAK_ANGLE = 0.5F * (180.0F / (float) Math.PI);

    private BackpackRenderer() {}

    public static class TileEntity extends TileEntitySpecialRenderer<TileBackpack>
    {
        private static final ModelBackpackBlock MODEL_BACKPACK = new ModelBackpackBlock();

        @Override
        public void render(TileBackpack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
        {
            ResourceLocation modelTexture = Resources.getBackpackTexture(BackpackTypes.STANDARD);
            int rotation = 0;

            //TODO TESR *items* comes here with NULL te and without access to parent itemStack (see: ForgeHooksClient#renderTileItem)... BakedModel? flattering? *custom model loader*? we have to get type somehow
            //TODO we also have to solve more complex issue than just multiple skins: items icons (render item model in GUI) have to dynamically render tanks contents, bedroll status and maybe something else
            //TODO see forge universal bucket for dynamic fluid rendering
            if (te != null)
            {
                modelTexture = Resources.getBackpackTexture(te.getType());
                rotation = te.getBlockMetadata() * 90;
            }

            bindTexture(modelTexture);

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            //GlStateManager.scale(0.8F, 0.8F, 0.8F);
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F); // flip from head to legs
            GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F); // world direction

            //MODEL_BACKPACK.render(0.0625F, te);
            MODEL_BACKPACK.renderTileEntity(te, 0.05F); //TODO scale to 0.0625, prescale to 0.8

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    public static class Layer implements LayerRenderer<EntityPlayer>
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

        @Override
        public boolean shouldCombineTextures()
        {
            return false;
        }
    }

}
