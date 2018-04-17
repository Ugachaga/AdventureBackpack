package com.darkona.adventurebackpack.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.client.render.RendererStack;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.reference.ToolHandler;

import static com.darkona.adventurebackpack.reference.BackpackTypes.HORSE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.IRON_GOLEM;
import static com.darkona.adventurebackpack.reference.BackpackTypes.OCELOT;
import static com.darkona.adventurebackpack.reference.BackpackTypes.PIG;
import static com.darkona.adventurebackpack.reference.BackpackTypes.QUARTZ;
import static com.darkona.adventurebackpack.reference.BackpackTypes.SLIME;
import static com.darkona.adventurebackpack.reference.BackpackTypes.SNOW;
import static com.darkona.adventurebackpack.reference.BackpackTypes.VILLAGER;

public class ModelBackpackOld extends ModelWearable
{
    public static final ModelBackpackOld instance = new ModelBackpackOld();

    public ModelRenderer mainBody;
    public ModelRenderer tankLeftTop;
    public ModelRenderer tankRightTop;
    public ModelRenderer bed;
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
    RendererStack lowerTool;
    RendererStack upperTool;
    public ItemStack backpack;

    private void init()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;

        //Main Backpack
        mainBody = new ModelRenderer(this, 0, 9);
        mainBody.addBox(-5.0F, 0.0F, -3.0F, 10, 9, 5);
        mainBody.setRotationPoint(0.0F, 0.0F, 0.0F);

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
        tankLeftTop.setRotationPoint(5.0F, -1.0F, -2.5F);
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
        tankRightTop.setRotationPoint(-9.0F, -1.0F, -2.5F);
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

        lowerTool = new RendererStack(this, true);
        upperTool = new RendererStack(this, false);

//        bipedBody.addChild(mainBody);
//        bipedBody.addChild(bed);
//        bipedBody.addChild(tankLeftTop);
//        bipedBody.addChild(tankRightTop);
//        bipedBody.addChild(villagerNose);
//        bipedBody.addChild(ocelotNose);
//        bipedBody.addChild(pigNose);
        mainBody.addChild(lowerTool);
        mainBody.addChild(upperTool);

        float offsetZ = 0.4F;
        float offsetY = 0.2F;

//        for (ModelRenderer part : (List<ModelRenderer>) bipedBody.childModels)
//        {
//            setOffset(part, part.offsetX + 0, part.offsetY + offsetY, part.offsetZ + offsetZ);
//        }

    }

    public ModelBackpackOld setWearable(ItemStack wearable)
    {
        this.backpack = wearable;
        return this;
    }

    public ModelBackpackOld()
    {
        init();
    }

    public ModelBackpackOld(ItemStack backpack)
    {
        init();
        this.backpack = backpack;
    }

    private void renderBackpack(Float scale)
    {
        InventoryBackpack inv = new InventoryBackpack(this.backpack);
        inv.loadFromNBT(backpack.getTagCompound());
        BackpackTypes type = inv.getType();
//        for (ModelRenderer model : (List<ModelRenderer>) bipedBody.childModels)
//        {
//            model.mirror = false;
//        }

        lowerTool.setRotationPoint(-.5F, .10F, .3F);
        setOffset(lowerTool, -.28F, 0.8F, -.1F);
        setOffset(upperTool, 0.0f, 0.04f, 0.25f);

        if (ConfigHandler.enableToolsRender)
        {
            ItemStack upperStack = inv.getStackInSlot(Constants.TOOL_UPPER);
            ItemStack lowerStack = inv.getStackInSlot(Constants.TOOL_LOWER);
            upperTool.setStack(upperStack, ToolHandler.getToolHandler(upperStack));
            lowerTool.setStack(lowerStack, ToolHandler.getToolHandler(lowerStack));
        }

        if (type == QUARTZ || type == SLIME || type == SNOW)
        {
            startBlending();
            this.mainBody.render(scale);
            stopBlending();
        }
        else
        {
            this.mainBody.render(scale);
        }

        GL11.glPushMatrix();

        tankLeftTop.render(scale);
        tankRightTop.render(scale);

        bed.render(scale);
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
        GL11.glPopMatrix();

        //GL11.glPushMatrix();
        //GL11.glTranslatef(bipedBody.offsetX + 0, bipedBody.offsetY + 0.2F, bipedBody.offsetZ + 0.3f);

        //renderFluidInTankOld(inv.getLeftTank(), new Vector3(0f, .5f, 0f), new Vector3(.17f, 0, .17f), new Vector3(-.17f, .05f, .2f), tankLeftTop);

        //renderFluidInTankOld(inv.getRightTank(), new Vector3(0f, .5f, 0f), new Vector3(.17f, 0, .17f), new Vector3(.41f, .05f, .2f), tankRightTop);
        //GL11.glPopMatrix();
    }

//    @Override
//    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
//    {
//        isSneak = ((entity != null) && (entity).isSneaking());
//        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
//        float oV = (isSneak) ? 0 : .3F;
//
//        float scale = f5 * 0.9f;
//
//        GL11.glPushMatrix();
//
//        GL11.glTranslatef(bipedBody.offsetX, bipedBody.offsetY, bipedBody.offsetZ);
//        GL11.glColor4f(1, 1, 1, 1);
//
//        if (bipedBody.rotateAngleX == 0.0F && bipedBody.rotateAngleY == 0.0F && bipedBody.rotateAngleZ == 0.0F)
//        {
//            if (bipedBody.rotationPointX == 0.0F && bipedBody.rotationPointY == 0.0F && bipedBody.rotationPointZ == 0.0F)
//            {
//                renderBackpack(scale);
//            }
//            else
//            {
//                GL11.glTranslatef(bipedBody.rotationPointX * f5, bipedBody.rotationPointY * f5, bipedBody.rotationPointZ * f5);
//                renderBackpack(scale);
//                GL11.glTranslatef(-bipedBody.rotationPointX * f5, -bipedBody.rotationPointY * f5, -bipedBody.rotationPointZ * f5);
//            }
//        }
//        else
//        {
//            GL11.glPushMatrix();
//            GL11.glTranslatef(bipedBody.rotationPointX * f5, bipedBody.rotationPointY * f5, bipedBody.rotationPointZ * f5);
//
//            if (bipedBody.rotateAngleZ != 0.0F)
//            {
//                GL11.glRotatef(bipedBody.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
//            }
//
//            if (bipedBody.rotateAngleY != 0.0F)
//            {
//                GL11.glRotatef(bipedBody.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
//            }
//
//            if (bipedBody.rotateAngleX != 0.0F)
//            {
//                GL11.glRotatef(bipedBody.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
//            }
//            renderBackpack(scale);
//            GL11.glPopMatrix();
//        }
//        GL11.glTranslatef(-bipedBody.offsetX, -bipedBody.offsetY, -(bipedBody.offsetZ));
//        GL11.glPopMatrix();
//    }

//    @Override
//    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack stack)
//    {
//        this.backpack = stack;
//        render(entity, f, f1, f2, f3, f4, f5);
//    }
}
