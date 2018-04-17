package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

@SuppressWarnings("FieldCanBeLocal")
public class ModelAdventureHat extends ModelBiped
{
    public static final ModelAdventureHat INSTANCE = new ModelAdventureHat(); //TODO move to ModModelManager

    private ModelRenderer top;
    private ModelRenderer wing;
    private ModelRenderer thing;

    public ModelAdventureHat()
    {
        textureWidth = 64;
        textureHeight = 32;

        top = new ModelRenderer(this, 0, 21);
        top.addBox(-4F, -11F, -4F, 8, 3, 8);
        top.setRotationPoint(0F, 0F, 0F);

        wing = new ModelRenderer(this, 0, 0);
        wing.addBox(-6F, -8.5F, -6F, 12, 1, 12);
        wing.setRotationPoint(0F, 0F, 0F);
        top.addChild(wing);

        thing = new ModelRenderer(this, 32, 21);
        thing.addBox(4F, -9F, -1F, 1, 1, 2);
        thing.setRotationPoint(0F, 0F, 0F);
        top.addChild(thing);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, age, yaw, pitch, scale, entity);

        GlStateManager.pushMatrix();
        if (entity.isSneaking())
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        this.bipedHead.postRender(scale);
        top.render(scale);
        GlStateManager.popMatrix();
    }
}