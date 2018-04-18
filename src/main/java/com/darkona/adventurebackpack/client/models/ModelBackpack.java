package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.RenderUtils;
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
    private final RenderType renderType;

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

    public enum RenderType
    {
        TILE, ITEM, LAYER;
    }

    public ModelBackpack(RenderType renderType)
    {
        this.renderType = renderType;

        init();

        if (renderType == RenderType.LAYER) //TODO raised tanks looks good, should be done so for all renderTypes?
        {
            tankLeftTop.setRotationPoint(5.0F, -1.0F, -2.5F);
            tankRightTop.setRotationPoint(-9.0F, -1.0F, -2.5F);
        }
    }

    private void init()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;

        //Main Backpack
        mainBody = new ModelRenderer(this, 0, 9);
        mainBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        mainBody.addBox(-5.0F, 0.0F, -3.0F, 10, 9, 5);

        leftStrap = new ModelRenderer(this, 21, 24);
        leftStrap.setRotationPoint(3.0F, 0.0F, -3.0F);
        leftStrap.addBox(0.0F, 0.0F, -1.0F, 1, 8, 1);
        mainBody.addChild(leftStrap);

        rightStrap = new ModelRenderer(this, 26, 24);
        rightStrap.setRotationPoint(-4.0F, 0.0F, -3.0F);
        rightStrap.addBox(0.0F, 0.0F, -1.0F, 1, 8, 1);
        mainBody.addChild(rightStrap);

        top = new ModelRenderer(this, 0, 0);
        top.setRotationPoint(0.0F, 0.0F, -3.0F);
        top.addBox(-5.0F, -3.0F, 0.0F, 10, 3, 5);
        mainBody.addChild(top);

        bottom = new ModelRenderer(this, 0, 34);
        bottom.setRotationPoint(-5.0F, 9.0F, -3.0F);
        bottom.addBox(0.0F, 0.0F, 0.0F, 10, 1, 4);
        mainBody.addChild(bottom);

        pocketFace = new ModelRenderer(this, 0, 24);
        pocketFace.setRotationPoint(0.0F, 6.9F, 2.0F);
        pocketFace.addBox(-4.0F, -6.0F, 0.0F, 8, 6, 2);
        mainBody.addChild(pocketFace);

        //Left Tank
        tankLeftTop = new ModelRenderer(this, 0, 40);
        tankLeftTop.setRotationPoint(5.0F, 0.0F, -2.5F);
        tankLeftTop.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);

        tankLeftBottom = new ModelRenderer(this, 0, 46);
        tankLeftBottom.setRotationPoint(0.0F, 9.0F, 0.0F);
        tankLeftBottom.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
        tankLeftTop.addChild(tankLeftBottom);

        tankLeftWall1 = new ModelRenderer(this, 0, 52);
        tankLeftWall1.setRotationPoint(3.0F, -8.0F, 0.0F);
        tankLeftWall1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankLeftBottom.addChild(tankLeftWall1);

        tankLeftWall2 = new ModelRenderer(this, 5, 52);
        tankLeftWall2.setRotationPoint(0.0F, -8.0F, 0.0F);
        tankLeftWall2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankLeftBottom.addChild(tankLeftWall2);

        tankLeftWall3 = new ModelRenderer(this, 10, 52);
        tankLeftWall3.setRotationPoint(0.0F, -8.0F, 3.0F);
        tankLeftWall3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankLeftBottom.addChild(tankLeftWall3);

        tankLeftWall4 = new ModelRenderer(this, 15, 52);
        tankLeftWall4.setRotationPoint(3.0F, -8.0F, 3.0F);
        tankLeftWall4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankLeftBottom.addChild(tankLeftWall4);

        //Right Tank
        tankRightTop = new ModelRenderer(this, 17, 40);
        tankRightTop.setRotationPoint(-9.0F, 0.0F, -2.5F);
        tankRightTop.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);

        tankRightBottom = new ModelRenderer(this, 17, 46);
        tankRightBottom.setRotationPoint(0.0F, 9.0F, 0.0F);
        tankRightBottom.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
        tankRightTop.addChild(tankRightBottom);

        tankRightWall1 = new ModelRenderer(this, 22, 52);
        tankRightWall1.setRotationPoint(3.0F, -8.0F, 3.0F);
        tankRightWall1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankRightBottom.addChild(tankRightWall1);

        tankRightWall2 = new ModelRenderer(this, 27, 52);
        tankRightWall2.setRotationPoint(3.0F, -8.0F, 0.0F);
        tankRightWall2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankRightBottom.addChild(tankRightWall2);

        tankRightWall3 = new ModelRenderer(this, 32, 52);
        tankRightWall3.setRotationPoint(0.0F, -8.0F, 3.0F);
        tankRightWall3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankRightBottom.addChild(tankRightWall3);

        tankRightWall4 = new ModelRenderer(this, 37, 52);
        tankRightWall4.setRotationPoint(0.0F, -8.0F, 0.0F);
        tankRightWall4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
        tankRightBottom.addChild(tankRightWall4);

        //Bed
        bed = new ModelRenderer(this, 31, 0);
        bed.setRotationPoint(-7.0F, 7.0F, 2.0F);
        bed.addBox(0.0F, 0.0F, 0.0F, 14, 2, 2);

        bedStrapRightTop = new ModelRenderer(this, 40, 5);
        bedStrapRightTop.setRotationPoint(2.0F, -1.0F, 0.0F);
        bedStrapRightTop.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
        bed.addChild(bedStrapRightTop);

        bedStrapRightMid = new ModelRenderer(this, 38, 10);
        bedStrapRightMid.setRotationPoint(2.0F, 0.0F, 2.0F);
        bedStrapRightMid.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1);
        bed.addChild(bedStrapRightMid);

        bedStrapRightBottom = new ModelRenderer(this, 42, 15);
        bedStrapRightBottom.setRotationPoint(2.0F, 2.0F, -1.0F);
        bedStrapRightBottom.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        bed.addChild(bedStrapRightBottom);

        bedStrapLeftTop = new ModelRenderer(this, 31, 5);
        bedStrapLeftTop.setRotationPoint(11.0F, -1.0F, 0.0F);
        bedStrapLeftTop.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
        bed.addChild(bedStrapLeftTop);

        bedStrapLeftMid = new ModelRenderer(this, 31, 10);
        bedStrapLeftMid.setRotationPoint(10.0F, 0.0F, 2.0F);
        bedStrapLeftMid.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1);
        bed.addChild(bedStrapLeftMid);

        bedStrapLeftBottom = new ModelRenderer(this, 31, 15);
        bedStrapLeftBottom.setRotationPoint(10.0F, 2.0F, -1.0F);
        bedStrapLeftBottom.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        bed.addChild(bedStrapLeftBottom);

        //Lamp
        lampPole1 = new ModelRenderer(this, 32, 24);
        lampPole1.setRotationPoint(5.0F, -10.0F, -1.0F);
        lampPole1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);

        lampPole2 = new ModelRenderer(this, 37, 25);
        lampPole2.setRotationPoint(1.0F, 0.0F, 0.0F);
        lampPole2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
        lampPole1.addChild(lampPole2);

        lampPole3 = new ModelRenderer(this, 40, 28);
        lampPole3.setRotationPoint(3.0F, 1.0F, 0.0F);
        lampPole3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        lampPole2.addChild(lampPole3);

        lampTop = new ModelRenderer(this, 53, 8);
        lampTop.setRotationPoint(3.5F, 2.0F, 0.5F);
        lampTop.addBox(-2.5F, 0.0F, -2.5F, 5, 1, 5);
        lampPole2.addChild(lampTop);

        lampGlassRight = new ModelRenderer(this, 41, 30);
        lampGlassRight.setRotationPoint(-2.5F, 1.0F, -2.5F);
        lampGlassRight.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5);
        lampTop.addChild(lampGlassRight);

        lampGlassFront = new ModelRenderer(this, 40, 40);
        lampGlassFront.setRotationPoint(-1.5F, 1.0F, -2.5F);
        lampGlassFront.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1);
        lampTop.addChild(lampGlassFront);

        lampGlassBack = new ModelRenderer(this, 40, 40);
        lampGlassBack.setRotationPoint(-1.5F, 1.0F, 1.5F);
        lampGlassBack.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1);
        lampTop.addChild(lampGlassBack);

        lampGlassLeft = new ModelRenderer(this, 41, 30);
        lampGlassLeft.setRotationPoint(1.5F, 1.0F, -2.5F);
        lampGlassLeft.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5);
        lampTop.addChild(lampGlassLeft);

        lampBottom = new ModelRenderer(this, 53, 23);
        lampBottom.setRotationPoint(-2.5F, 5.0F, -0.5F);
        lampBottom.addBox(0.0F, 0.0F, -2.0F, 5, 1, 5);
        lampTop.addChild(lampBottom);

        lampLight = new ModelRenderer(this, 57, 15);
        lampLight.setRotationPoint(8.0F, -7.0F, -2.0F);
        lampLight.addBox(0.0F, 0.0F, 0.0F, 3, 4, 3);

        //Kitchen
        kitchenBase = new ModelRenderer(this, 49, 46);
        kitchenBase.setRotationPoint(-9.0F, -1.0F, -1.5F);
        kitchenBase.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);

        kitchen = new ModelRenderer(this, 49, 37);
        kitchen.setRotationPoint(-3.0F, -2.0F, -1.5F);
        kitchen.addBox(0.0F, 0.0F, 0.0F, 5, 2, 6);
        kitchenBase.addChild(kitchen);

        //Noses
        villagerNose = new ModelRenderer(this, 64, 0);
        villagerNose.setRotationPoint(-1.0F, 4.0F, 4.0F);
        villagerNose.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);

        ocelotNose = new ModelRenderer(this, 74, 0);
        ocelotNose.setRotationPoint(-1.0F, 4.0F, 4.0F);
        ocelotNose.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);

        pigNose = new ModelRenderer(this, 74, 0);
        pigNose.setRotationPoint(-2.0F, 4.0F, 4.0F);
        pigNose.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1);

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

        renderBackpack(backpack, scale);

        ItemStack testUpper = new ItemStack(Items.DIAMOND_PICKAXE); //TODO del
        ItemStack testLower = new ItemStack(Items.DIAMOND_AXE);

        renderUpperTool(testUpper /*backpack.getStackInSlot(Constants.TOOL_UPPER)*/);
        renderLowerTool(testLower /*backpack.getStackInSlot(Constants.TOOL_LOWER)*/);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -0.1F, 0.0F);
        renderFluidInTank(backpack.getLeftTank(), leftFluidCuboid16.copy());
        renderFluidInTank(backpack.getRightTank(), rightFluidCuboid16.copy());
        GlStateManager.popMatrix();
    }

    private void renderUpperTool(ItemStack upperTool)
    {
        if (upperTool.isEmpty()) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5f, -0.5f, 0.3f);
        GlStateManager.scale(1.45F, 1.45F, 1.45F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-45F, 0, 0, 1);

        RenderUtils.renderItemUniform(upperTool);

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private void renderLowerTool(ItemStack lowerTool)
    {
        if (lowerTool.isEmpty()) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.335f, 0.4f, -0.53f);
        GlStateManager.scale(1.45F, 1.45F, 1.45F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(90F, 0, 1, 0);
        GlStateManager.rotate(45F, 0, 0, 1);

        RenderUtils.renderItemUniform(lowerTool);

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private void renderBackpack(IInventoryBackpack backpack, float scale)
    {
        BackpackTypes type = backpack.getType();

        if (type == QUARTZ || type == SLIME || type == SNOW)
        {
            //GlStateManager.pushMatrix();
            startBlending(); //TODO something wrong, it spoils images of enchanted items in open container
            //GlStateManager.enableCull();
            mainBody.render(scale);
            //GlStateManager.disableCull();
            stopBlending();
            //GlStateManager.popMatrix();
        }
        else
        {
            mainBody.render(scale);
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
            lampPole1.render(scale);
            GlStateManager.disableCull();
            stopBlending();
        }*/
    }
}
