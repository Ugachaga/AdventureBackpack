package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;

import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.reference.BackpackTypes;

import static com.darkona.adventurebackpack.reference.BackpackTypes.HORSE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.IRON_GOLEM;
import static com.darkona.adventurebackpack.reference.BackpackTypes.OCELOT;
import static com.darkona.adventurebackpack.reference.BackpackTypes.PIG;
import static com.darkona.adventurebackpack.reference.BackpackTypes.QUARTZ;
import static com.darkona.adventurebackpack.reference.BackpackTypes.SLIME;
import static com.darkona.adventurebackpack.reference.BackpackTypes.SNOW;
import static com.darkona.adventurebackpack.reference.BackpackTypes.VILLAGER;

@SuppressWarnings("FieldCanBeLocal")
public class ModelBackpack extends ModelWearable
{
    private ModelRenderer mainBody;
    private ModelRenderer leftStrap;
    private ModelRenderer rightStrap;
    private ModelRenderer top;
    private ModelRenderer bottom;
    private ModelRenderer pocketFace;

    private ModelRenderer tankLeftTop;
    private ModelRenderer tankLeftBottom;
    private ModelRenderer tankLeftWall1;
    private ModelRenderer tankLeftWall2;
    private ModelRenderer tankLeftWall3;
    private ModelRenderer tankLeftWall4;

    private ModelRenderer tankRightTop;
    private ModelRenderer tankRightBottom;
    private ModelRenderer tankRightWall1;
    private ModelRenderer tankRightWall2;
    private ModelRenderer tankRightWall3;
    private ModelRenderer tankRightWall4;

    private ModelRenderer bed;
    private ModelRenderer bedStrapRightTop;
    private ModelRenderer bedStrapRightMid;
    private ModelRenderer bedStrapRightBottom;
    private ModelRenderer bedStrapLeftTop;
    private ModelRenderer bedStrapLeftMid;
    private ModelRenderer bedStrapLeftBottom;

    private ModelRenderer lampPole1; // lamp unused
    private ModelRenderer lampPole2;
    private ModelRenderer lampPole3;
    private ModelRenderer lampTop;
    private ModelRenderer lampBottom;
    private ModelRenderer lampGlassRight;
    private ModelRenderer lampGlassFront;
    private ModelRenderer lampGlassBack;
    private ModelRenderer lampGlassLeft;
    private ModelRenderer lampLight;

    private ModelRenderer kitchenBase; // kitchen unused
    private ModelRenderer kitchen;

    private ModelRenderer ocelotNose;
    private ModelRenderer pigNose;
    private ModelRenderer villagerNose;

    private Cuboid6 leftFluidCuboid16;
    private Cuboid6 rightFluidCuboid16;

    private Cuboid6 rightFluidCuboid20;
    private Cuboid6 leftFluidCuboid20;

    public ModelBackpack()
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

        // fluid rendering stuff
        // scale 0.0625 (1/16)
        leftFluidCuboid16 = getFluidCuboid16().add(createVector3(tankLeftTop, -0.158, 0.06, 0.125));
        rightFluidCuboid16 = getFluidCuboid16().add(createVector3(tankRightTop, 0.37, 0.06, 0.125));

        // scale 0.05 (1/20)
        leftFluidCuboid20 = getFluidCuboid20().add(createVector3(tankLeftTop, -0.22, -0.05, 0.15));
        rightFluidCuboid20 = getFluidCuboid20().add(createVector3(tankRightTop, 0.48, -0.05, 0.15));

        CCRenderState.instance().reset();
    }

    private Cuboid6 getFluidCuboid16()
    {
        return new Cuboid6(0.0, 0.505, 0.0, 0.188, 0.0, 0.188);
    }

    private Cuboid6 getFluidCuboid20()
    {
        return new Cuboid6(0.0, -0.4, 0.0, 0.15, 0.0, 0.15);
    }

    public void renderTileEntity(IInventoryBackpack backpack, float scale)
    {
        GlStateManager.pushMatrix();
        renderBackpack(backpack, scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        renderFluidInTank(backpack.getLeftTank(), leftFluidCuboid20.copy());
        renderFluidInTank(backpack.getRightTank(), rightFluidCuboid20.copy());
        GlStateManager.popMatrix();
    }

    public void renderLayer(EntityPlayer player, ItemStack stack, float scale)
    {
        InventoryBackpack backpack = new InventoryBackpack(stack);

        GlStateManager.pushMatrix();
        renderBackpack(backpack, scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        renderFluidInTank(backpack.getLeftTank(), leftFluidCuboid16.copy());
        renderFluidInTank(backpack.getRightTank(), rightFluidCuboid16.copy());
        GlStateManager.popMatrix();

        //TODO render items in ToolSlots, see 1.7.10 implementation in ModelBackpackOld and RenderUtils#renderItemUniform
    }

    private void renderBackpack(IInventoryBackpack backpack, float scale)
    {
        BackpackTypes type = backpack.getType();

        if (type == QUARTZ || type == SLIME || type == SNOW)
        {
            //GlStateManager.pushMatrix();
            startBlending();
            //GlStateManager.enableCull();
            this.mainBody.render(scale);
            //GlStateManager.disableCull();
            stopBlending();
            //GlStateManager.popMatrix();
        }
        else
        {
            this.mainBody.render(scale);
        }

        tankLeftTop.render(scale);
        tankRightTop.render(scale);

        if (!backpack.isSleepingBagDeployed())
            bed.render(scale);

        if (type == PIG || type == HORSE)
            pigNose.render(scale);
        else if (type == VILLAGER || type == IRON_GOLEM)
            villagerNose.render(scale);
        else if (type == OCELOT)
            ocelotNose.render(scale);

        /*if(type == BackpackTypes.STANDARD)
        {
            startBlending();
            GlStateManager.enableCull();
            this.lampPole1.render(scale);
            GlStateManager.disableCull();
            stopBlending();
        }*/
    }
}
