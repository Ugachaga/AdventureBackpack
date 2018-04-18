package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCampfire extends ModelBase
{
    private ModelRenderer log;

    private ModelRenderer stick1;
    private ModelRenderer stick2;
    private ModelRenderer stick3;
    private ModelRenderer stick4;

    private ModelRenderer rock1;
    private ModelRenderer rock2;
    private ModelRenderer rock3;
    private ModelRenderer rock4;
    private ModelRenderer rock5;
    private ModelRenderer rock6;
    private ModelRenderer rock7;
    private ModelRenderer rock8;
    private ModelRenderer rock9;
    private ModelRenderer rock10;
    private ModelRenderer rock11;
    private ModelRenderer rock12;
    private ModelRenderer rock13;
    private ModelRenderer rock14;
    private ModelRenderer rock15;
    private ModelRenderer rock16;
    private ModelRenderer rock17;

    public ModelCampfire()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;

        log = new ModelRenderer(this, 6, 19);
        log.setRotationPoint(0.0F, 23.0F, 0.0F);
        log.addBox(0.0F, -3.0F, -2.0F, 3, 3, 10, 0.0F);
        setRotateAngle(log, 0.36425021489121656F, 0.0F, 0.0F);

        stick1 = new ModelRenderer(this, 0, 17);
        stick1.setRotationPoint(-3.0F, 24.0F, 5.0F);
        stick1.addBox(-0.5F, -14.0F, -0.5F, 1, 14, 1, 0.0F);
        setRotateAngle(stick1, 0.40980330836826856F, 0.7285004297824331F, 0.6373942428283291F);

        stick2 = new ModelRenderer(this, 0, 17);
        stick2.setRotationPoint(6.0F, 22.0F, 1.0F);
        stick2.addBox(-0.5F, -14.0F, -0.5F, 1, 14, 1, 0.0F);
        setRotateAngle(stick2, 0.40980330836826856F, 1.5481070465189704F, 0.0F);

        stick3 = new ModelRenderer(this, 0, 17);
        stick3.setRotationPoint(4.0F, 24.0F, -6.0F);
        stick3.addBox(-0.5F, -14.0F, -0.5F, 1, 14, 1, 0.0F);
        setRotateAngle(stick3, -0.5462880558742251F, -0.36425021489121656F, 0.0F);

        stick4 = new ModelRenderer(this, 0, 17);
        stick4.setRotationPoint(-5.0F, 24.0F, -1.0F);
        stick4.addBox(-0.5F, -14.0F, -0.5F, 1, 14, 1, 0.0F);
        setRotateAngle(stick4, 0.5009094953223726F, -1.9123572614101867F, 0.0F);
        
        rock1 = new ModelRenderer(this, 0, 8);
        rock1.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock1.addBox(-1.0F, -2.0F, 4.0F, 2, 2, 2, 0.0F);

        rock2 = new ModelRenderer(this, 0, 0);
        rock2.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock2.addBox(-2.0F, -1.0F, 4.0F, 1, 1, 1, 0.0F);

        rock3 = new ModelRenderer(this, 0, 8);
        rock3.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock3.addBox(-3.0F, -2.0F, 2.0F, 2, 2, 2, 0.0F);

        rock4 = new ModelRenderer(this, 0, 8);
        rock4.setRotationPoint(1.0F, 23.0F, 1.0F);
        rock4.addBox(4.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);

        rock5 = new ModelRenderer(this, 0, 8);
        rock5.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock5.addBox(-6.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);

        rock6 = new ModelRenderer(this, 0, 0);
        rock6.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock6.addBox(2.0F, -1.0F, -6.0F, 1, 1, 1, 0.0F);

        rock7 = new ModelRenderer(this, 10, 7);
        rock7.setRotationPoint(0.0F, 23.0F, 0.0F);
        rock7.addBox(-5.0F, -2.0F, -5.0F, 3, 3, 3, 0.0F);

        rock8 = new ModelRenderer(this, 0, 8);
        rock8.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock8.addBox(-2.0F, -2.0F, -6.0F, 2, 2, 2, 0.0F);

        rock9 = new ModelRenderer(this, 0, 8);
        rock9.setRotationPoint(-1.0F, 24.0F, 1.0F);
        rock9.addBox(-4.0F, -2.0F, 0.0F, 2, 2, 2, 0.0F);

        rock10 = new ModelRenderer(this, 0, 8);
        rock10.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock10.addBox(0.0F, -2.0F, -7.0F, 2, 2, 2, 0.0F);

        rock11 = new ModelRenderer(this, 0, 8);
        rock11.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock11.addBox(4.0F, -2.0F, 1.0F, 2, 2, 2, 0.0F);

        rock12 = new ModelRenderer(this, 10, 7);
        rock12.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock12.addBox(1.0F, -3.0F, 3.0F, 3, 3, 3, 0.0F);

        rock13 = new ModelRenderer(this, 0, 8);
        rock13.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock13.addBox(3.0F, -2.0F, -5.0F, 2, 2, 2, 0.0F);

        rock14 = new ModelRenderer(this, 0, 8);
        rock14.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock14.addBox(4.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);

        rock15 = new ModelRenderer(this, 0, 0);
        rock15.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock15.addBox(6.0F, -3.0F, 1.0F, 1, 1, 1, 0.0F);

        rock16 = new ModelRenderer(this, 0, 0);
        rock16.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock16.addBox(-5.0F, -1.0F, -2.0F, 1, 1, 1, 0.0F);

        rock17 = new ModelRenderer(this, 0, 0);
        rock17.setRotationPoint(0.0F, 24.0F, 0.0F);
        rock17.addBox(4.0F, -1.0F, 3.0F, 1, 1, 1, 0.0F);
    }

    public void renderTileEntity(float scale)
    {
        log.render(scale);

        stick1.render(scale);
        stick2.render(scale);
        stick3.render(scale);
        stick4.render(scale);

        rock1.render(scale);
        rock2.render(scale);
        rock3.render(scale);
        rock4.render(scale);
        rock5.render(scale);
        rock6.render(scale);
        rock7.render(scale);
        rock8.render(scale);
        rock9.render(scale);
        rock10.render(scale);
        rock11.render(scale);
        rock12.render(scale);
        rock13.render(scale);
        rock14.render(scale);
        rock15.render(scale);
        rock16.render(scale);
        rock17.render(scale);
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
