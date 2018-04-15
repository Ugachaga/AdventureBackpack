package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SuppressWarnings("WeakerAccess")
public class ModelSleepingBag extends ModelBase
{
    public ModelRenderer footer;
    public ModelRenderer header;
    public ModelRenderer pillow;

    public ModelSleepingBag()
    {
        textureWidth = 64;
        textureHeight = 64;

        footer = new ModelRenderer(this, 0, 0);
        header = new ModelRenderer(this, 0, 17);
        pillow = new ModelRenderer(this, 0, 34);

        footer.addBox(-8F, 0F, 0F, 16, 1, 16);
        footer.setRotationPoint(0F, 23F, -8F);
        footer.setTextureSize(64, 64);
        footer.mirror = true;
        setRotation(footer, 0F, 0F, 0F);

        header.addBox(-16F, 0F, 16F, 16, 1, 16);
        header.setRotationPoint(8F, 23F, -8F);
        header.setTextureSize(64, 64);
        header.mirror = true;
        setRotation(header, 0F, 0F, 0F);

        pillow.addBox(-6F, -1F, 26F, 12, 1, 6);
        pillow.setRotationPoint(0F, 23F, -8F);
        pillow.setTextureSize(64, 64);
        pillow.mirror = true;
        setRotation(pillow, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        footer.render(f5);
        header.render(f5);
        pillow.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
