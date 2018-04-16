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

        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base.addBox(-4.0F, 0.0F, 2.0F, 8, 12, 1);

        //TANK
        this.tankTop = new ModelRenderer(this, 0, 33);
        this.tankTop.setRotationPoint(1.0F, 0.0F, 3.0F);
        this.tankTop.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5, 0.0F);
        this.base.addChild(this.tankTop);

        this.tankWallLeftFront = new ModelRenderer(this, 0, 40);
        this.tankWallLeftFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tankWallLeftFront.addBox(4.0F, 1.0F, 0.0F, 1, 4, 1, 0.0F);
        this.tankTop.addChild(this.tankWallLeftFront);

        this.tankWallLeftBack = new ModelRenderer(this, 0, 32);
        this.tankWallLeftBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tankWallLeftBack.addBox(4.0F, 1.0F, 4.0F, 1, 4, 1, 0.0F);
        this.tankTop.addChild(this.tankWallLeftBack);

        this.tankWallRightFront = new ModelRenderer(this, 16, 40);
        this.tankWallRightFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tankWallRightFront.addBox(0.0F, 1.0F, 0.0F, 1, 4, 1, 0.0F);
        this.tankTop.addChild(this.tankWallRightFront);

        this.tankWallRightBack = new ModelRenderer(this, 16, 32);
        this.tankWallRightBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tankWallRightBack.addBox(0.0F, 1.0F, 4.0F, 1, 4, 1, 0.0F);
        this.tankTop.addChild(this.tankWallRightBack);

        this.tankBottom = new ModelRenderer(this, 0, 41);
        this.tankBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tankBottom.addBox(0.0F, 5.0F, 0.0F, 5, 1, 5, 0.0F);
        this.tankTop.addChild(this.tankBottom);

        this.fuelLine1 = new ModelRenderer(this, 15, 48);
        this.fuelLine1.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.fuelLine1.addBox(2.0F, 1.0F, 2.0F, 1, 4, 1, 0.0F);
        this.tankBottom.addChild(this.fuelLine1);

        this.fuelLine2 = new ModelRenderer(this, 0, 48);
        this.fuelLine2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.fuelLine2.addBox(1.0F, 1.0F, 2.0F, 1, 1, 1, 0.0F);
        this.tankBottom.addChild(this.fuelLine2);

        //ENGINE
        this.engineBody = new ModelRenderer(this, 0, 23);
        this.engineBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.engineBody.addBox(-2.0F, 8.0F, 3.0F, 4, 4, 4);
        this.base.addChild(this.engineBody);

        this.enginePistonLeft = new ModelRenderer(this, 0, 18);
        this.enginePistonLeft.setRotationPoint(0.0F, 8.0F, 5.0F);
        this.enginePistonLeft.addBox(1.7F, 1.0F, -1.0F, 4, 2, 2);
        this.setRotateAngle(enginePistonLeft, 0.0F, -0.7853981633974483F, 0.0F);
        this.engineBody.addChild(this.enginePistonLeft);

        this.enginePistonRight = new ModelRenderer(this, 13, 18);
        this.enginePistonRight.setRotationPoint(0.0F, 8.0F, 5.0F);
        this.enginePistonRight.addBox(1.7F, 1.0F, -1.0F, 4, 2, 2);
        this.setRotateAngle(enginePistonRight, 0.0F, -2.356194490192345F, 0.0F);
        this.engineBody.addChild(this.enginePistonRight);

        this.axis = new ModelRenderer(this, 25, 22);
        this.axis.setRotationPoint(0.0F, 8.0F, 5.75F);
        this.axis.addBox(-0.5F, -25.0F, -0.5F, 1, 25, 1);
        //this.engineBody.addChild(this.axis);

        this.blade1 = new ModelRenderer(this, 29, 0);
        this.blade1.setRotationPoint(0.0F, -25.0F, 0.0F);
        this.blade1.addBox(0.0F, -0.5F, -1.0F, 15, 1, 2);
        this.axis.addChild(this.blade1);

        this.blade2 = new ModelRenderer(this, 30, 4);
        this.blade2.setRotationPoint(0.0F, -25.0F, 0.0F);
        this.blade2.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 15);
        this.axis.addChild(this.blade2);

        this.blade3 = new ModelRenderer(this, 29, 0);
        this.blade3.setRotationPoint(0.0F, -25.0F, 0.0F);
        this.blade3.addBox(0.0F, -0.5F, -1.0F, 15, 1, 2);
        this.setRotateAngle(blade3, 0.0F, 0.0F, 3.141592653589793F);
        this.axis.addChild(this.blade3);

        this.blade4 = new ModelRenderer(this, 30, 4);
        this.blade4.setRotationPoint(0.0F, -25.0F, 0.0F);
        this.blade4.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 15);
        this.setRotateAngle(blade4, -3.141592653589793F, 0.0F, 0.0F);
        this.axis.addChild(this.blade4);

        //ESCAPE
        this.escape1 = new ModelRenderer(this, 9, 35);
        this.escape1.setRotationPoint(-4.0F, 9.0F, 4.0F);
        this.escape1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
        this.base.addChild(this.escape1);

        this.escape2 = new ModelRenderer(this, 38, 40);
        this.escape2.setRotationPoint(-4.0F, 0.0F, 4.0F);
        this.escape2.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1);
        this.base.addChild(this.escape2);

        this.escape3 = new ModelRenderer(this, 6, 24);
        this.escape3.setRotationPoint(-4.0F, 0.0F, 5.0F);
        this.escape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
        this.base.addChild(this.escape3);

        this.escapeFilter = new ModelRenderer(this, 35, 28);
        this.escapeFilter.setRotationPoint(-4.4F, 2.0F, 3.5F);
        this.escapeFilter.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2);
        this.base.addChild(this.escapeFilter);

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
            this.axis.rotateAngleY = (deg <= 360 + degrees) ? Utils.degreesToRadians(deg + degrees) : 0;
        }
        else
        {
            axis.isHidden = true;
        }

        GlStateManager.pushMatrix();
        this.base.render(scale);
        this.axis.render(scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        renderFluidInTank(copter.getFuelTank(), fluidCuboid.copy());
        GlStateManager.popMatrix();
    }

}