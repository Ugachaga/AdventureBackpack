package com.darkona.adventurebackpack.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpackBlock;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Resources;

public class TileEntityBackpackRenderer extends TileEntitySpecialRenderer<TileBackpack>
{
    //private static final ResourceLocation[] TEXTURES;
    private ModelBackpackBlock model_block = new ModelBackpackBlock();
    private ModelBackpack model_item = new ModelBackpack();

//    static
//    {
//        BackpackTypes[] types = BackpackTypes.values();
//        TEXTURES = new ResourceLocation[types.length];
//
//        for (BackpackTypes type : types)
//        {
//            TEXTURES[type.ordinal()] = new ResourceLocation(ModInfo.MODID, "textures/backpack/" + type.getName() + ".png");
//        }
//    }

    public TileEntityBackpackRenderer() {}

    @Override
    public void render(TileBackpack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        //BackpackTypes type = te.getType();
        //ResourceLocation resourcelocation = TEXTURES[type.getMeta()];
        //this.bindTexture(resourcelocation);

        ResourceLocation modelTexture = Resources.getBackpackTexture(BackpackTypes.STANDARD);

        int rotation = 0;

        if (te != null) //TODO fix sync
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
