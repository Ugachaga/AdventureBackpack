package com.darkona.adventurebackpack.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fluids.FluidTank;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.reference.BackpackTypes;

import static com.darkona.adventurebackpack.reference.BackpackTypes.*;

/**
 * Created on 17/12/2014
 *
 * @author Darkona
 */
public class ModelBackpackBlock extends ModelBase
{
    public ModelRenderer mainBody;
    public ModelRenderer lampLight;
    public ModelRenderer tankLeftTop;
    public ModelRenderer tankRightTop;
    public ModelRenderer bed;
    public ModelRenderer lampPole1;
    public ModelRenderer kitchenBase;
    public ModelRenderer villagerNose;
    public ModelRenderer pigNose;
    public ModelRenderer ocelotNose;
    public ModelRenderer leftStrap;
    public ModelRenderer rightStrap;
    public ModelRenderer top;
    public ModelRenderer bottom;
    public ModelRenderer pocketFace;
    public ModelRenderer tankLeftBottom;
    public ModelRenderer tankLeftWall4;
    public ModelRenderer tankLeftWall3;
    public ModelRenderer tankLeftWall2;
    public ModelRenderer tankLeftWall1;
    public ModelRenderer tankRightBottom;
    public ModelRenderer tankRightWall2;
    public ModelRenderer tankRightWall1;
    public ModelRenderer tankRightWall3;
    public ModelRenderer tankRightWall4;
    public ModelRenderer bedStrapLeftMid;
    public ModelRenderer bedStrapRightBottom;
    public ModelRenderer bedStrapLeftBottom;
    public ModelRenderer bedStrapRightMid;
    public ModelRenderer bedStrapRightTop;
    public ModelRenderer bedStrapLeftTop;
    public ModelRenderer lampPole2;
    public ModelRenderer lampTop;
    public ModelRenderer lampPole3;
    public ModelRenderer lampBottom;
    public ModelRenderer lampGlassLeft;
    public ModelRenderer lampGlassRight;
    public ModelRenderer lampGlassBack;
    public ModelRenderer lampGlassFront;
    public ModelRenderer kitchen;

    public ModelBackpackBlock()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;

        //Main Backpack
        this.mainBody = new ModelRenderer(this, 0, 9);
        this.mainBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mainBody.addBox(-5.0F, 0.0F, -3.0F, 10, 9, 5);

        this.leftStrap = new ModelRenderer(this, 21, 24);
        this.leftStrap.setRotationPoint(3.0F, 0.0F, -3.0F);
        this.leftStrap.addBox(0.0F, 0.0F, -1.0F, 1, 8, 1);
        this.mainBody.addChild(this.leftStrap);

        this.rightStrap = new ModelRenderer(this, 26, 24);
        this.rightStrap.setRotationPoint(-4.0F, 0.0F, -3.0F);
        this.rightStrap.addBox(0.0F, 0.0F, -1.0F, 1, 8, 1);
        this.mainBody.addChild(this.rightStrap);

        this.top = new ModelRenderer(this, 0, 0);
        this.top.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.top.addBox(-5.0F, -3.0F, 0.0F, 10, 3, 5);
        this.mainBody.addChild(this.top);

        this.bottom = new ModelRenderer(this, 0, 34);
        this.bottom.setRotationPoint(-5.0F, 9.0F, -3.0F);
        this.bottom.addBox(0.0F, 0.0F, 0.0F, 10, 1, 4);
        this.mainBody.addChild(this.bottom);

        this.pocketFace = new ModelRenderer(this, 0, 24);
        this.pocketFace.setRotationPoint(0.0F, 6.9F, 2.0F);
        this.pocketFace.addBox(-4.0F, -6.0F, 0.0F, 8, 6, 2);
        this.mainBody.addChild(this.pocketFace);

        //Left Tank
        this.tankLeftTop = new ModelRenderer(this, 0, 40);
        this.tankLeftTop.setRotationPoint(5.0F, 0.0F, -2.5F);
        this.tankLeftTop.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);

        this.tankLeftBottom = new ModelRenderer(this, 0, 46);
        this.tankLeftBottom.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.tankLeftBottom.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
        this.tankLeftTop.addChild(this.tankLeftBottom);

        this.tankLeftWall1 = new ModelRenderer(this, 0, 52);
        this.tankLeftWall1.setRotationPoint(3.0F, -8.0F, 0.0F);
        this.tankLeftWall1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankLeftBottom.addChild(this.tankLeftWall1);

        this.tankLeftWall2 = new ModelRenderer(this, 5, 52);
        this.tankLeftWall2.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.tankLeftWall2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankLeftBottom.addChild(this.tankLeftWall2);

        this.tankLeftWall3 = new ModelRenderer(this, 10, 52);
        this.tankLeftWall3.setRotationPoint(0.0F, -8.0F, 3.0F);
        this.tankLeftWall3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankLeftBottom.addChild(this.tankLeftWall3);

        this.tankLeftWall4 = new ModelRenderer(this, 15, 52);
        this.tankLeftWall4.setRotationPoint(3.0F, -8.0F, 3.0F);
        this.tankLeftWall4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankLeftBottom.addChild(this.tankLeftWall4);

        //Right Tank
        this.tankRightTop = new ModelRenderer(this, 17, 40);
        this.tankRightTop.setRotationPoint(-9.0F, 0.0F, -2.5F);
        this.tankRightTop.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);

        this.tankRightBottom = new ModelRenderer(this, 17, 46);
        this.tankRightBottom.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.tankRightBottom.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
        this.tankRightTop.addChild(this.tankRightBottom);

        this.tankRightWall1 = new ModelRenderer(this, 22, 52);
        this.tankRightWall1.setRotationPoint(3.0F, -8.0F, 3.0F);
        this.tankRightWall1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankRightBottom.addChild(this.tankRightWall1);

        this.tankRightWall2 = new ModelRenderer(this, 27, 52);
        this.tankRightWall2.setRotationPoint(3.0F, -8.0F, 0.0F);
        this.tankRightWall2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankRightBottom.addChild(this.tankRightWall2);

        this.tankRightWall3 = new ModelRenderer(this, 32, 52);
        this.tankRightWall3.setRotationPoint(0.0F, -8.0F, 3.0F);
        this.tankRightWall3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankRightBottom.addChild(this.tankRightWall3);

        this.tankRightWall4 = new ModelRenderer(this, 37, 52);
        this.tankRightWall4.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.tankRightWall4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        this.tankRightBottom.addChild(this.tankRightWall4);

        //Bed
        this.bed = new ModelRenderer(this, 31, 0);
        this.bed.setRotationPoint(-7.0F, 7.0F, 2.0F);
        this.bed.addBox(0.0F, 0.0F, 0.0F, 14, 2, 2);

        this.bedStrapRightTop = new ModelRenderer(this, 40, 5);
        this.bedStrapRightTop.setRotationPoint(2.0F, -1.0F, 0.0F);
        this.bedStrapRightTop.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
        this.bed.addChild(this.bedStrapRightTop);

        this.bedStrapRightMid = new ModelRenderer(this, 38, 10);
        this.bedStrapRightMid.setRotationPoint(2.0F, 0.0F, 2.0F);
        this.bedStrapRightMid.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1);
        this.bed.addChild(this.bedStrapRightMid);

        this.bedStrapRightBottom = new ModelRenderer(this, 42, 15);
        this.bedStrapRightBottom.setRotationPoint(2.0F, 2.0F, -1.0F);
        this.bedStrapRightBottom.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        this.bed.addChild(this.bedStrapRightBottom);

        this.bedStrapLeftTop = new ModelRenderer(this, 31, 5);
        this.bedStrapLeftTop.setRotationPoint(11.0F, -1.0F, 0.0F);
        this.bedStrapLeftTop.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
        this.bed.addChild(this.bedStrapLeftTop);

        this.bedStrapLeftMid = new ModelRenderer(this, 31, 10);
        this.bedStrapLeftMid.setRotationPoint(10.0F, 0.0F, 2.0F);
        this.bedStrapLeftMid.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1);
        this.bed.addChild(this.bedStrapLeftMid);

        this.bedStrapLeftBottom = new ModelRenderer(this, 31, 15);
        this.bedStrapLeftBottom.setRotationPoint(10.0F, 2.0F, -1.0F);
        this.bedStrapLeftBottom.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        this.bed.addChild(this.bedStrapLeftBottom);

        //Lamp
        this.lampPole1 = new ModelRenderer(this, 32, 24);
        this.lampPole1.setRotationPoint(5.0F, -10.0F, -1.0F);
        this.lampPole1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);

        this.lampPole2 = new ModelRenderer(this, 37, 25);
        this.lampPole2.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.lampPole2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
        this.lampPole1.addChild(this.lampPole2);

        this.lampPole3 = new ModelRenderer(this, 40, 28);
        this.lampPole3.setRotationPoint(3.0F, 1.0F, 0.0F);
        this.lampPole3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        this.lampPole2.addChild(this.lampPole3);

        this.lampTop = new ModelRenderer(this, 53, 8);
        this.lampTop.setRotationPoint(3.5F, 2.0F, 0.5F);
        this.lampTop.addBox(-2.5F, 0.0F, -2.5F, 5, 1, 5);
        this.lampPole2.addChild(this.lampTop);

        this.lampGlassRight = new ModelRenderer(this, 41, 30);
        this.lampGlassRight.setRotationPoint(-2.5F, 1.0F, -2.5F);
        this.lampGlassRight.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5);
        this.lampTop.addChild(this.lampGlassRight);

        this.lampGlassFront = new ModelRenderer(this, 40, 40);
        this.lampGlassFront.setRotationPoint(-1.5F, 1.0F, -2.5F);
        this.lampGlassFront.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1);
        this.lampTop.addChild(this.lampGlassFront);

        this.lampGlassBack = new ModelRenderer(this, 40, 40);
        this.lampGlassBack.setRotationPoint(-1.5F, 1.0F, 1.5F);
        this.lampGlassBack.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1);
        this.lampTop.addChild(this.lampGlassBack);

        this.lampGlassLeft = new ModelRenderer(this, 41, 30);
        this.lampGlassLeft.setRotationPoint(1.5F, 1.0F, -2.5F);
        this.lampGlassLeft.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5);
        this.lampTop.addChild(this.lampGlassLeft);

        this.lampBottom = new ModelRenderer(this, 53, 23);
        this.lampBottom.setRotationPoint(-2.5F, 5.0F, -0.5F);
        this.lampBottom.addBox(0.0F, 0.0F, -2.0F, 5, 1, 5);
        this.lampTop.addChild(this.lampBottom);

        this.lampLight = new ModelRenderer(this, 57, 15);
        this.lampLight.setRotationPoint(8.0F, -7.0F, -2.0F);
        this.lampLight.addBox(0.0F, 0.0F, 0.0F, 3, 4, 3);

        //Kitchen
        this.kitchenBase = new ModelRenderer(this, 49, 46);
        this.kitchenBase.setRotationPoint(-9.0F, -1.0F, -1.5F);
        this.kitchenBase.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);

        this.kitchen = new ModelRenderer(this, 49, 37);
        this.kitchen.setRotationPoint(-3.0F, -2.0F, -1.5F);
        this.kitchen.addBox(0.0F, 0.0F, 0.0F, 5, 2, 6);
        this.kitchenBase.addChild(this.kitchen);

        //Noses
        this.villagerNose = new ModelRenderer(this, 64, 0);
        this.villagerNose.setRotationPoint(-1.0F, 4.0F, 4.0F);
        this.villagerNose.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);

        this.ocelotNose = new ModelRenderer(this, 74, 0);
        this.ocelotNose.setRotationPoint(-1.0F, 4.0F, 4.0F);
        this.ocelotNose.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);

        this.pigNose = new ModelRenderer(this, 74, 0);
        this.pigNose.setRotationPoint(-2.0F, 4.0F, 4.0F);
        this.pigNose.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale, IInventoryBackpack backpack)
    {
        //scale*=0.9;
        boolean sleepingbag = backpack.isSleepingBagDeployed();
        FluidTank tankLeft = backpack.getLeftTank();
        FluidTank tankRight = backpack.getRightTank();
        setRotationAngles(f, f1, f2, f3, f4, scale, entity);

        GL11.glPushMatrix();
        renderBackpack(backpack, scale);
        //renderFluidsInTanks(backpack.getLeftTank(),backpack.getRightTank(),scale);
        GL11.glPopMatrix();

        if (tankLeft != null && tankLeft.getFluid() != null)
        {
            Vector3 victor = new Vector3(
                    (tankLeftTop.rotationPointX * 0.1f - 0.22f),
                    (tankLeftTop.rotationPointY * 0.1f + 0.05f),
                    (tankLeftTop.rotationPointZ * 0.1f + 0.15f));
            GL11.glPushMatrix();
            CCRenderState.instance().reset();
            CCRenderState.instance().pullLightmap();
            RenderUtils.renderFluidCuboid(tankLeft.getFluid(), new Cuboid6(0f, 0.39f, 0f, 0.15f, 0f, 0.15f).add(victor),
                    ((1.0F * tankLeft.getFluidAmount()) / (1.0F * tankLeft.getCapacity())), 0.8);
            GL11.glPopMatrix();

        }

        if (tankRight != null && tankRight.getFluid() != null)
        {
            Vector3 victor = new Vector3(
                    (tankRightTop.rotationPointX * 0.1f + 0.48f),
                    (tankRightTop.rotationPointY * 0.1f + 0.05f),
                    (tankRightTop.rotationPointZ * 0.1f + 0.15f));
            GL11.glPushMatrix();
            CCRenderState.instance().reset();
            CCRenderState.instance().pullLightmap();
            RenderUtils.renderFluidCuboid(tankRight.getFluid(), new Cuboid6(0, 0.39, 0, 0.15, 0, 0.15).add(victor),
                    ((1.0F * tankRight.getFluidAmount()) / (1.0F * tankRight.getCapacity())), 0.8);
            GL11.glPopMatrix();
        }
    }

    private void renderFluidsInTanks(FluidTank tankLeft, FluidTank tankRight, float scale)
    {
        //Size of the cuboid
        //Y-- is up, Y++ is down
        float minX = 0f;
        float minY = 0.5f;
        float minZ = 0f;

        float maxX = 0.17f;
        float maxY = 0f;
        float maxZ = 0.17f;

        if (tankLeft != null && tankLeft.getFluid() != null)
        {
            //0.5F, -0.1F, -0.25F - Rotation Points of the top
            //X++ to the right, X-- to the left
            //Z-- to the front, Z++ to the back
            Vector3 victor = new Vector3(
                    (tankLeftTop.rotationPointX * 0.1f - 0.17f), //
                    (tankLeftTop.rotationPointY * 0.1f + 0.1f),
                    (tankLeftTop.rotationPointZ * 0.1f + 0.13f));
            //ChickenStuff
            CCRenderState.instance().reset();
            CCRenderState.instance().pullLightmap();

            Cuboid6 left = new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ);
            //Thanks Chickenbones!
            RenderUtils.renderFluidCuboid(tankLeft.getFluid(), left.add(victor), ((1.0F * tankLeft.getFluidAmount()) / (1.0F * Constants.BASIC_TANK_CAPACITY)), 0.2);
        }

        if (tankRight != null && tankRight.getFluid() != null)
        {
            //-0.9F, -0.1F, -0.25F - Rotation points of the top
            //X-- to the right, X++ to the left
            //Z-- to the front, Z++ to the back
            Vector3 victor = new Vector3(
                    (tankRightTop.rotationPointX * 0.1f + 0.41f), //
                    (tankRightTop.rotationPointY * 0.1f + 0.1f),
                    (tankRightTop.rotationPointZ * 0.1f + 0.13f));
            //ChickenStuff
            CCRenderState.instance().reset();
            CCRenderState.instance().pullLightmap();

            Cuboid6 right = new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ);
            RenderUtils.renderFluidCuboid(tankRight.getFluid(), right.add(victor), ((1.0F * tankRight.getFluidAmount()) / (1.0F * Constants.BASIC_TANK_CAPACITY)), 0.2);
        }

    }

    private void renderBackpack(IInventoryBackpack backpack, float scale)
    {
        BackpackTypes type = backpack.getType();

        if (type == QUARTZ || type == SLIME || type == SNOW)
        {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //GL11.glEnable(GL11.GL_CULL_FACE);

            this.mainBody.render(scale);

            GL11.glPopMatrix();
            //GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
        }
        else
        {
            this.mainBody.render(scale);
        }
        tankLeftTop.render(scale);
        tankRightTop.render(scale);

        if (!backpack.isSleepingBagDeployed()) bed.render(scale);

        if (type == PIG || type == HORSE)
        {
            pigNose.render(scale);
        }
        if (type == VILLAGER || type == IRON_GOLEM)
        {
            villagerNose.render(scale);
        }
        if (type == OCELOT)
        {
            ocelotNose.render(scale);
        }

        /*if(type == STANDARD)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_CULL_FACE);
            this.lampPole1.render(scale);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
        }*/
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
