package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpackBlock;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Resources;

public final class BackpackRenderer
{
    // see: ModelBiped#setRotationAngles, ModelRenderer#renderWithRotation
    private static final float ANGLE_SNEAK = 0.5F * (180.0F / (float) Math.PI);

    private BackpackRenderer() {}

    public static class TileEntity extends TileEntitySpecialRenderer<TileBackpack>
    {
        //private static final ResourceLocation[] TEXTURES;
        private ModelBackpackBlock model_block = new ModelBackpackBlock();
        private ModelBackpack model_item = new ModelBackpack();

//        static
//        {
//            BackpackTypes[] types = BackpackTypes.values();
//            TEXTURES = new ResourceLocation[types.length];
//
//            for (BackpackTypes type : types)
//            {
//                TEXTURES[type.ordinal()] = new ResourceLocation(ModInfo.MODID, "textures/backpack/" + type.getName() + ".png");
//            }
//        }

        @Override
        public void render(TileBackpack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
        {
            //BackpackTypes type = te.getType();
            //ResourceLocation resourcelocation = TEXTURES[type.getMeta()];
            //this.bindTexture(resourcelocation);

            ResourceLocation modelTexture = Resources.getBackpackTexture(BackpackTypes.STANDARD);

            int rotation = 0;

            //TODO seems TESR *items* comes here with NULL te... BakedModel? BlockStates? flattering? *custom model loader*? we have to get type somehow
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
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //TODO del?
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5f, (float) z + 0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F); // flip from head to legs
            GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F); // world direction

            model_block.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1 / 20F, te);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //TODO del?
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
            ItemStack backpack = null;

            if (player.getHeldItemMainhand().getItem() == ModItems.ADVENTURE_BACKPACK) //TODO capability: isWearingBackpack
            {
                backpack = player.getHeldItemMainhand();
            }

            if (backpack != null)
            {
                Minecraft.getMinecraft().renderEngine.bindTexture(Resources.getBackpackTexture(BackpackTypes.getType(backpack)));

                GlStateManager.pushMatrix();
                GlStateManager.enableRescaleNormal();
                GlStateManager.scale(.82f, .82f, .82f); //TODO tuning wearing backpack size
                if (player.isSneaking())
                {
                    GlStateManager.translate(0.0F, 0.236F, 0.45F);
                    GlStateManager.rotate(ANGLE_SNEAK, 1.0F, 0.0F, 0.0F);
                }
                else
                {
                    GlStateManager.translate(0.0F, 0.205F, 0.4F);
                }
                MODEL_BACKPACK.renderLayer(player, 0.0625F, backpack);
                GlStateManager.disableRescaleNormal();
                GlStateManager.popMatrix();
            }
        }

        @Override
        public boolean shouldCombineTextures()
        {
            return false;
        }
    }

}
