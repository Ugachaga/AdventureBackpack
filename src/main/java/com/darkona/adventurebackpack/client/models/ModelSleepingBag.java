package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSleepingBag extends ModelBase //unused
{
    private ModelRenderer header;
    private ModelRenderer pillow;
    private ModelRenderer footer;

    public ModelSleepingBag()
    {
        textureWidth = 64;
        textureHeight = 64;

        header = new ModelRenderer(this, 0, 17);
        header.addBox(-16.0F, 0.0F, 16.0F, 16, 1, 16);
        header.setRotationPoint(8.0F, 23.0F, -8.0F);
        header.mirror = true;

        pillow = new ModelRenderer(this, 0, 34);
        pillow.addBox(-6.0F, -1.0F, 26.0F, 12, 1, 6);
        pillow.setRotationPoint(0.0F, 23.0F, -8.0F);
        pillow.mirror = true;

        footer = new ModelRenderer(this, 0, 0);
        footer.addBox(-8.0F, 0.0F, 0.0F, 16, 1, 16);
        footer.setRotationPoint(0.0F, 23.0F, -8.0F);
        footer.mirror = true;
    }

    public void render(float scale)
    {
        header.render(scale);
        pillow.render(scale);
        footer.render(scale);
    }
}
