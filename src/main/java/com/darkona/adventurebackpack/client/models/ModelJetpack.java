package com.darkona.adventurebackpack.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import codechicken.lib.vec.Cuboid6;

import com.darkona.adventurebackpack.inventory.InventoryJetpack;

@SuppressWarnings("FieldCanBeLocal")
public class ModelJetpack extends ModelWearable
{
    private ModelRenderer base;
    private ModelRenderer fireBox;

    private ModelRenderer tankTop;
    private ModelRenderer tankWallLeft;
    private ModelRenderer tankWallRight;
    private ModelRenderer tankBottom;

    private ModelRenderer waterTube1;
    private ModelRenderer waterTube2;

    private ModelRenderer pressureTank;
    private ModelRenderer tubeBendLeft;
    private ModelRenderer tubeBendRight;
    private ModelRenderer tubeStraightLeft;
    private ModelRenderer tubeStraightRight;
    private ModelRenderer tubeEndLeft;
    private ModelRenderer tubeEndRight;

    private final Cuboid6 fluidCuboid;

    public ModelJetpack()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base.addBox(-4.0F, 0.0F, 2.0F, 8, 12, 1, 0.0F);

        this.fireBox = new ModelRenderer(this, 26, 25);
        this.fireBox.setRotationPoint(2.0F, 8.0F, 3.0F);
        this.fireBox.addBox(-3.0F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
        this.base.addChild(this.fireBox);

        // tank
        this.tankTop = new ModelRenderer(this, 10, 20);
        this.tankTop.setRotationPoint(-4.0F, 0.0F, 3.0F);
        this.tankTop.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.base.addChild(this.tankTop);

        this.tankWallLeft = new ModelRenderer(this, 3, 23);
        this.tankWallLeft.setRotationPoint(-1.0F, 1.0F, 1.0F);
        this.tankWallLeft.addBox(0.0F, 6.0F, 2.0F, 1, 1, 3, 0.0F);

        this.tankWallRight = new ModelRenderer(this, 5, 17);
        this.tankWallRight.setRotationPoint(-1.0F, 1.0F, 3.0F);
        this.tankWallRight.addBox(-3.0F, 0.0F, 2.0F, 1, 8, 1, 0.0F);

        this.tankBottom = new ModelRenderer(this, 10, 20);
        this.tankBottom.setRotationPoint(-1.0F, 8.0F, 3.0F);
        this.tankBottom.addBox(-3.0F, 1.0F, 0.0F, 3, 1, 3, 0.0F);
        this.base.addChild(this.tankBottom);

        //
        this.waterTube1 = new ModelRenderer(this, 10, 17);
        this.waterTube1.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.waterTube1.addBox(-2.0F, 1.0F, 1.0F, 1, 1, 1, 0.0F);
        this.tankBottom.addChild(this.waterTube1);

        this.waterTube2 = new ModelRenderer(this, 10, 17);
        this.waterTube2.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.waterTube2.addBox(-2.0F, 1.0F, 1.0F, 2, 1, 1, 0.0F);
        this.waterTube1.addChild(this.waterTube2);

        //
        this.pressureTank = new ModelRenderer(this, 19, 0);
        this.pressureTank.setRotationPoint(2.0F, 0.0F, 3.0F);
        this.pressureTank.addBox(-3.0F, 0.0F, 0.0F, 5, 7, 3, 0.0F);
        this.base.addChild(this.pressureTank);

        this.tubeBendLeft = new ModelRenderer(this, 0, 14);
        this.tubeBendLeft.setRotationPoint(-2.0F, 1.0F, 2.0F);
        this.tubeBendLeft.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.setRotateAngle(tubeBendLeft, 0.0F, -0.8080874436733745F, 0.0F);
        this.pressureTank.addChild(this.tubeBendLeft);

        this.tubeBendRight = new ModelRenderer(this, 0, 14);
        this.tubeBendRight.setRotationPoint(-2.0F, 1.0F, 2.0F);
        this.tubeBendRight.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.setRotateAngle(tubeBendRight, 0.0F, 0.7976154681614086F, 0.0F);
        this.pressureTank.addChild(this.tubeBendRight);

        this.tubeStraightLeft = new ModelRenderer(this, 0, 14);
        this.tubeStraightLeft.setRotationPoint(2.7F, 1.0F, 8.3F);
        this.tubeStraightLeft.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);

        this.tubeStraightRight = new ModelRenderer(this, 0, 14);
        this.tubeStraightRight.setRotationPoint(-3.0F, 1.0F, 8.3F);
        this.tubeStraightRight.addBox(-5.8F, 0.0F, 0.0F, 6, 1, 1, 0.0F);

        this.tubeEndLeft = new ModelRenderer(this, 0, 17);
        this.tubeEndLeft.setRotationPoint(7.6F, 1.7F, 8.3F);
        this.tubeEndLeft.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, -0.1F);
        this.setRotateAngle(tubeEndLeft, 0.0F, 0.0F, -0.2617993877991494F);

        this.tubeEndRight = new ModelRenderer(this, 0, 17);
        this.tubeEndRight.setRotationPoint(-7.7F, 1.6F, 8.3F);
        this.tubeEndRight.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, -0.1F);
        this.setRotateAngle(tubeEndRight, 0.0F, 0.0F, 0.2617993877991494F);

        // fluid rendering stuff
        fluidCuboid = new Cuboid6(0, 0.505, 0, 0.165, -0.005, 0.195)
                .add(createVector3(tankTop, 0.175, 0.0625, -0.148));
    }

    public void renderLayer(EntityPlayer player, ItemStack stack, float scale)
    {
        InventoryJetpack jetpack = new InventoryJetpack(stack);
        this.fireBox.setTextureOffset((jetpack.getBurnTicks() > 0) ? 9 : 26, 25);

        GlStateManager.pushMatrix();
        this.tubeStraightRight.render(scale);
        this.tubeEndLeft.render(scale);
        this.tubeEndRight.render(scale);
        this.base.render(scale);
        this.tankWallLeft.render(scale);
        this.tankWallRight.render(scale);
        this.tubeStraightLeft.render(scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        renderFluidInTank(jetpack.getWaterTank(), fluidCuboid.copy());
        GlStateManager.popMatrix();
    }
}
