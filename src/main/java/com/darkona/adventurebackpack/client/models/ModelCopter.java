package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;

import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.item.ItemCopter;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.Utils;

import static com.darkona.adventurebackpack.common.Constants.Copter.TAG_STATUS;

@SuppressWarnings("FieldCanBeLocal")
public class ModelCopter extends ModelWearable
{
    private ModelRenderer base;

    private ModelRenderer tankTop;
    private ModelRenderer tankWallLeftFront;
    private ModelRenderer tankWallLeftBack;
    private ModelRenderer tankWallRightFront;
    private ModelRenderer tankWallRightBack;
    private ModelRenderer tankBottom;
    private ModelRenderer fuelLine1;
    private ModelRenderer fuelLine2;

    private ModelRenderer engineBody;
    private ModelRenderer enginePistonLeft;
    private ModelRenderer enginePistonRight;
    private ModelRenderer axis;
    private ModelRenderer blade1;
    private ModelRenderer blade2;
    private ModelRenderer blade3;
    private ModelRenderer blade4;

    private ModelRenderer escape1;
    private ModelRenderer escape2;
    private ModelRenderer escape3;
    private ModelRenderer escapeFilter;

    private Cuboid6 fluidCuboid;

    public ModelCopter()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        base = new ModelRenderer(this, 0, 0);
        base.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.addBox(-4.0F, 0.0F, 2.0F, 8, 12, 1);

        //TANK
        tankTop = new ModelRenderer(this, 0, 33);
        tankTop.setRotationPoint(1.0F, 0.0F, 3.0F);
        tankTop.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
        base.addChild(tankTop);

        tankWallLeftFront = new ModelRenderer(this, 0, 40);
        tankWallLeftFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallLeftFront.addBox(4.0F, 1.0F, 0.0F, 1, 4, 1, 0.0F);
        tankTop.addChild(tankWallLeftFront);

        tankWallLeftBack = new ModelRenderer(this, 0, 32);
        tankWallLeftBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallLeftBack.addBox(4.0F, 1.0F, 4.0F, 1, 4, 1, 0.0F);
        tankTop.addChild(tankWallLeftBack);

        tankWallRightFront = new ModelRenderer(this, 16, 40);
        tankWallRightFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallRightFront.addBox(0.0F, 1.0F, 0.0F, 1, 4, 1, 0.0F);
        tankTop.addChild(tankWallRightFront);

        tankWallRightBack = new ModelRenderer(this, 16, 32);
        tankWallRightBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankWallRightBack.addBox(0.0F, 1.0F, 4.0F, 1, 4, 1, 0.0F);
        tankTop.addChild(tankWallRightBack);

        tankBottom = new ModelRenderer(this, 0, 41);
        tankBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        tankBottom.addBox(0.0F, 5.0F, 0.0F, 5, 1, 5, 0.0F);
        tankTop.addChild(tankBottom);

        fuelLine1 = new ModelRenderer(this, 15, 48);
        fuelLine1.setRotationPoint(0.0F, 5.0F, 0.0F);
        fuelLine1.addBox(2.0F, 1.0F, 2.0F, 1, 4, 1, 0.0F);
        tankBottom.addChild(fuelLine1);

        fuelLine2 = new ModelRenderer(this, 0, 48);
        fuelLine2.setRotationPoint(0.0F, 8.0F, 0.0F);
        fuelLine2.addBox(1.0F, 1.0F, 2.0F, 1, 1, 1, 0.0F);
        tankBottom.addChild(fuelLine2);

        //ENGINE
        engineBody = new ModelRenderer(this, 0, 23);
        engineBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        engineBody.addBox(-2.0F, 8.0F, 3.0F, 4, 4, 4);
        base.addChild(engineBody);

        enginePistonLeft = new ModelRenderer(this, 0, 18);
        enginePistonLeft.setRotationPoint(0.0F, 8.0F, 5.0F);
        enginePistonLeft.addBox(1.7F, 1.0F, -1.0F, 4, 2, 2);
        setRotateAngle(enginePistonLeft, 0.0F, -0.7853981633974483F, 0.0F);
        engineBody.addChild(enginePistonLeft);

        enginePistonRight = new ModelRenderer(this, 13, 18);
        enginePistonRight.setRotationPoint(0.0F, 8.0F, 5.0F);
        enginePistonRight.addBox(1.7F, 1.0F, -1.0F, 4, 2, 2);
        setRotateAngle(enginePistonRight, 0.0F, -2.356194490192345F, 0.0F);
        engineBody.addChild(enginePistonRight);

        axis = new ModelRenderer(this, 25, 22);
        axis.setRotationPoint(0.0F, 8.0F, 5.75F);
        axis.addBox(-0.5F, -25.0F, -0.5F, 1, 25, 1);
        //engineBody.addChild(axis);

        blade1 = new ModelRenderer(this, 29, 0);
        blade1.setRotationPoint(0.0F, -25.0F, 0.0F);
        blade1.addBox(0.0F, -0.5F, -1.0F, 15, 1, 2);
        axis.addChild(blade1);

        blade2 = new ModelRenderer(this, 30, 4);
        blade2.setRotationPoint(0.0F, -25.0F, 0.0F);
        blade2.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 15);
        axis.addChild(blade2);

        blade3 = new ModelRenderer(this, 29, 0);
        blade3.setRotationPoint(0.0F, -25.0F, 0.0F);
        blade3.addBox(0.0F, -0.5F, -1.0F, 15, 1, 2);
        setRotateAngle(blade3, 0.0F, 0.0F, 3.141592653589793F);
        axis.addChild(blade3);

        blade4 = new ModelRenderer(this, 30, 4);
        blade4.setRotationPoint(0.0F, -25.0F, 0.0F);
        blade4.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 15);
        setRotateAngle(blade4, -3.141592653589793F, 0.0F, 0.0F);
        axis.addChild(blade4);

        //ESCAPE
        escape1 = new ModelRenderer(this, 9, 35);
        escape1.setRotationPoint(-4.0F, 9.0F, 4.0F);
        escape1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
        base.addChild(escape1);

        escape2 = new ModelRenderer(this, 38, 40);
        escape2.setRotationPoint(-4.0F, 0.0F, 4.0F);
        escape2.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1);
        base.addChild(escape2);

        escape3 = new ModelRenderer(this, 6, 24);
        escape3.setRotationPoint(-4.0F, 0.0F, 5.0F);
        escape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
        base.addChild(escape3);

        escapeFilter = new ModelRenderer(this, 35, 28);
        escapeFilter.setRotationPoint(-4.4F, 2.0F, 3.5F);
        escapeFilter.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2);
        base.addChild(escapeFilter);

        // fluid rendering stuff
        fluidCuboid = new Cuboid6(0, 0.2505, 0, 0.26, -0.005, 0.26)
                .add(createVector3(tankTop, -0.015, 0.0625, -0.093));

        CCRenderState.instance().reset();
    }

    public void renderLayer(EntityPlayer player, ItemStack stack, float scale)
    {
        InventoryCopter copter = new InventoryCopter(stack);
        copter.loadFromNBT(stack.getTagCompound()); //TODO redundant?

        if (BackpackUtils.getWearableCompound(stack).getByte(TAG_STATUS) != ItemCopter.OFF_MODE) //TODO get status from inventory?
        {
            axis.isHidden = false;

            int degrees;
            if (player.onGround || player.isSneaking())
                degrees = 16;
            else
                degrees = player.motionY > 0 ? 36 : 28;

            float deg = Utils.radiansToDegrees(this.axis.rotateAngleY);
            axis.rotateAngleY = (deg <= 360 + degrees) ? Utils.degreesToRadians(deg + degrees) : 0;
        }
        else
        {
            axis.isHidden = true;
        }

        GlStateManager.pushMatrix();
        base.render(scale);
        axis.render(scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        renderFluidInTank(copter.getFuelTank(), fluidCuboid.copy());
        GlStateManager.popMatrix();
    }

}