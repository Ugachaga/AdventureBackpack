package com.darkona.adventurebackpack.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import com.darkona.adventurebackpack.entity.EntityInflatableBoat;

@SuppressWarnings("FieldCanBeLocal")
public class ModelInflatableBoat extends ModelBase
{
    private ModelRenderer boatSides1;
    private ModelRenderer boatSides2;
    private ModelRenderer boatSides3;
    private ModelRenderer boatSides4;
    private ModelRenderer boatSides5;
    private ModelRenderer engineBody;
    private ModelRenderer axis;
    private ModelRenderer enginePistonLeft;
    private ModelRenderer enginePistonRight;
    private ModelRenderer tankTop;
    private ModelRenderer tankBottom;
    private ModelRenderer tankWallRightFront;
    private ModelRenderer tankWallLeftFront;
    private ModelRenderer tankWallRightBack;
    private ModelRenderer tankWallLeftBack;
    private ModelRenderer blade1;
    private ModelRenderer blade2;
    private ModelRenderer blade3;
    private ModelRenderer blade4;

    public ModelInflatableBoat()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        boatSides4 = new ModelRenderer(this, 0, 36);
        boatSides4.setRotationPoint(0.0F, 4.0F, -9.0F);
        boatSides4.addBox(-10.0F, -7.0F, -1.0F, 20, 6, 3, 0.0F);
        setRotateAngle(boatSides4, 0.0F, 3.141592653589793F, 0.0F);
        axis = new ModelRenderer(this, 25, 55);
        axis.setRotationPoint(16.0F, 1.0F, 0.0F);
        axis.addBox(-0.5F, -6.8F, -0.5F, 1, 8, 1, 0.0F);
        setRotateAngle(axis, 3.141592653589793F, 0.045553093477052F, -0.4363323129985824F);
        blade3 = new ModelRenderer(this, 20, 48);
        blade3.setRotationPoint(0.0F, -7.0F, 0.0F);
        blade3.addBox(0.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
        setRotateAngle(blade3, -0.3490658503988659F, 0.0F, 3.141592653589793F);
        tankWallRightFront = new ModelRenderer(this, 16, 59);
        tankWallRightFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallRightFront.addBox(0.0F, 1.0F, 0.0F, 1, 4, 1, 0.0F);
        enginePistonLeft = new ModelRenderer(this, 0, 55);
        enginePistonLeft.setRotationPoint(0.0F, 0.0F, 2.0F);
        enginePistonLeft.addBox(1.7F, 1.0F, -1.0F, 4, 2, 2, 0.0F);
        setRotateAngle(enginePistonLeft, 0.0F, -0.7853981633974483F, 0.0F);
        boatSides1 = new ModelRenderer(this, 0, 10);
        boatSides1.setRotationPoint(0.0F, 4.0F, 0.0F);
        boatSides1.addBox(-12.0F, -9.0F, -3.0F, 24, 18, 4, 0.0F);
        setRotateAngle(boatSides1, 1.5707963267948966F, 0.0F, 0.0F);
        boatSides2 = new ModelRenderer(this, 0, 0);
        boatSides2.setRotationPoint(-11.0F, 4.0F, 0.0F);
        boatSides2.addBox(-11.0F, -7.0F, -1.3F, 22, 6, 3, 0.0F);
        setRotateAngle(boatSides2, 0.0F, 4.6898742330339624F, 0.0F);
        tankTop = new ModelRenderer(this, 32, 58);
        tankTop.setRotationPoint(2.5F, -6.0F, -0.5F);
        tankTop.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
        setRotateAngle(tankTop, 0.0F, -1.5707963267948966F, 0.0F);
        blade1 = new ModelRenderer(this, 20, 48);
        blade1.setRotationPoint(0.0F, -7.0F, 0.0F);
        blade1.addBox(0.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
        setRotateAngle(blade1, -0.3490658503988659F, 0.0F, 0.0F);
        tankBottom = new ModelRenderer(this, 32, 58);
        tankBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankBottom.addBox(0.0F, 5.0F, 0.0F, 5, 1, 5, 0.0F);
        boatSides3 = new ModelRenderer(this, 0, 0);
        boatSides3.setRotationPoint(11.0F, 4.0F, 0.0F);
        boatSides3.addBox(-11.0F, -7.0F, -1.0F, 22, 6, 3, 0.0F);
        setRotateAngle(boatSides3, 0.0F, 1.5707963267948966F, 0.0F);
        blade4 = new ModelRenderer(this, 30, 48);
        blade4.setRotationPoint(0.0F, -7.0F, 0.0F);
        blade4.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 4, 0.0F);
        setRotateAngle(blade4, -3.141592653589793F, 0.0F, 0.3490658503988659F);
        tankWallRightBack = new ModelRenderer(this, 16, 59);
        tankWallRightBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallRightBack.addBox(0.0F, 1.0F, 4.0F, 1, 4, 1, 0.0F);
        boatSides5 = new ModelRenderer(this, 0, 36);
        boatSides5.setRotationPoint(0.0F, 4.0F, 9.0F);
        boatSides5.addBox(-10.0F, -7.0F, -1.0F, 20, 6, 3, 0.0F);
        engineBody = new ModelRenderer(this, 0, 46);
        engineBody.setRotationPoint(13.3F, -3.0F, 0.0F);
        engineBody.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        setRotateAngle(engineBody, 0.0F, 1.5707963267948966F, 0.0F);
        tankWallLeftFront = new ModelRenderer(this, 16, 59);
        tankWallLeftFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallLeftFront.addBox(4.0F, 1.0F, 0.0F, 1, 4, 1, 0.0F);
        blade2 = new ModelRenderer(this, 30, 48);
        blade2.setRotationPoint(0.0F, -7.0F, 0.0F);
        blade2.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 4, 0.0F);
        setRotateAngle(blade2, 0.0F, 0.0F, -0.3490658503988659F);
        tankWallLeftBack = new ModelRenderer(this, 16, 59);
        tankWallLeftBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallLeftBack.addBox(4.0F, 1.0F, 4.0F, 1, 4, 1, 0.0F);
        enginePistonRight = new ModelRenderer(this, 13, 55);
        enginePistonRight.setRotationPoint(0.0F, 0.0F, 2.0F);
        enginePistonRight.addBox(1.7F, 1.0F, -1.0F, 4, 2, 2, 0.0F);
        setRotateAngle(enginePistonRight, 0.0F, -2.356194490192345F, 0.0F);
        axis.addChild(blade3);
        tankTop.addChild(tankWallRightFront);
        engineBody.addChild(enginePistonLeft);
        engineBody.addChild(tankTop);
        axis.addChild(blade1);
        tankTop.addChild(tankBottom);
        axis.addChild(blade4);
        tankTop.addChild(tankWallRightBack);
        tankTop.addChild(tankWallLeftFront);
        axis.addChild(blade2);
        tankTop.addChild(tankWallLeftBack);
        engineBody.addChild(enginePistonRight);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        if (entity instanceof EntityInflatableBoat)
        {
            EntityInflatableBoat theBoat = (EntityInflatableBoat) entity;

            if (theBoat.isMotorized() && theBoat.isInflated())
            {
                axis.isHidden = engineBody.isHidden = false;
            }
            else
            {
                axis.isHidden = engineBody.isHidden = true;
            }

            GL11.glPushMatrix();
            GL11.glScalef(theBoat.inflation, theBoat.inflation, theBoat.inflation);

            boatSides5.render(f5);
            boatSides3.render(f5);
            boatSides2.render(f5);
            boatSides1.render(f5);
            boatSides4.render(f5);
            axis.render(f5);
            engineBody.render(f5);

            GL11.glPopMatrix();
        }
        else
        {
            boatSides5.render(f5);
            boatSides3.render(f5);
            boatSides2.render(f5);
            axis.render(f5);
            boatSides1.render(f5);
            boatSides4.render(f5);
            engineBody.render(f5);
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
