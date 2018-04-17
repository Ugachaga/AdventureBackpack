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

        base = new ModelRenderer(this, 0, 0);
        base.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.addBox(-4.0F, 0.0F, 2.0F, 8, 12, 1, 0.0F);

        fireBox = new ModelRenderer(this, 26, 25);
        fireBox.setRotationPoint(2.0F, 8.0F, 3.0F);
        fireBox.addBox(-3.0F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
        base.addChild(fireBox);

        // tank
        tankTop = new ModelRenderer(this, 10, 20);
        tankTop.setRotationPoint(-4.0F, 0.0F, 3.0F);
        tankTop.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        base.addChild(tankTop);

        tankWallLeft = new ModelRenderer(this, 3, 23);
        tankWallLeft.setRotationPoint(-1.0F, 1.0F, 1.0F);
        tankWallLeft.addBox(0.0F, 6.0F, 2.0F, 1, 1, 3, 0.0F);

        tankWallRight = new ModelRenderer(this, 5, 17);
        tankWallRight.setRotationPoint(-1.0F, 1.0F, 3.0F);
        tankWallRight.addBox(-3.0F, 0.0F, 2.0F, 1, 8, 1, 0.0F);

        tankBottom = new ModelRenderer(this, 10, 20);
        tankBottom.setRotationPoint(-1.0F, 8.0F, 3.0F);
        tankBottom.addBox(-3.0F, 1.0F, 0.0F, 3, 1, 3, 0.0F);
        base.addChild(tankBottom);

        //
        waterTube1 = new ModelRenderer(this, 10, 17);
        waterTube1.setRotationPoint(0.0F, 1.0F, 0.0F);
        waterTube1.addBox(-2.0F, 1.0F, 1.0F, 1, 1, 1, 0.0F);
        tankBottom.addChild(waterTube1);

        waterTube2 = new ModelRenderer(this, 10, 17);
        waterTube2.setRotationPoint(0.0F, 1.0F, 0.0F);
        waterTube2.addBox(-2.0F, 1.0F, 1.0F, 2, 1, 1, 0.0F);
        waterTube1.addChild(waterTube2);

        //
        pressureTank = new ModelRenderer(this, 19, 0);
        pressureTank.setRotationPoint(2.0F, 0.0F, 3.0F);
        pressureTank.addBox(-3.0F, 0.0F, 0.0F, 5, 7, 3, 0.0F);
        base.addChild(pressureTank);

        tubeBendLeft = new ModelRenderer(this, 0, 14);
        tubeBendLeft.setRotationPoint(-2.0F, 1.0F, 2.0F);
        tubeBendLeft.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        setRotateAngle(tubeBendLeft, 0.0F, -0.8080874436733745F, 0.0F);
        pressureTank.addChild(tubeBendLeft);

        tubeBendRight = new ModelRenderer(this, 0, 14);
        tubeBendRight.setRotationPoint(-2.0F, 1.0F, 2.0F);
        tubeBendRight.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        setRotateAngle(tubeBendRight, 0.0F, 0.7976154681614086F, 0.0F);
        pressureTank.addChild(tubeBendRight);

        tubeStraightLeft = new ModelRenderer(this, 0, 14);
        tubeStraightLeft.setRotationPoint(2.7F, 1.0F, 8.3F);
        tubeStraightLeft.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);

        tubeStraightRight = new ModelRenderer(this, 0, 14);
        tubeStraightRight.setRotationPoint(-3.0F, 1.0F, 8.3F);
        tubeStraightRight.addBox(-5.8F, 0.0F, 0.0F, 6, 1, 1, 0.0F);

        tubeEndLeft = new ModelRenderer(this, 0, 17);
        tubeEndLeft.setRotationPoint(7.6F, 1.7F, 8.3F);
        tubeEndLeft.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, -0.1F);
        setRotateAngle(tubeEndLeft, 0.0F, 0.0F, -0.2617993877991494F);

        tubeEndRight = new ModelRenderer(this, 0, 17);
        tubeEndRight.setRotationPoint(-7.7F, 1.6F, 8.3F);
        tubeEndRight.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, -0.1F);
        setRotateAngle(tubeEndRight, 0.0F, 0.0F, 0.2617993877991494F);

        // fluid rendering stuff
        fluidCuboid = new Cuboid6(0, 0.505, 0, 0.165, -0.005, 0.195)
                .add(createVector3(tankTop, 0.175, 0.0625, -0.148));
    }

    public void renderLayer(EntityPlayer player, ItemStack stack, float scale)
    {
        InventoryJetpack jetpack = new InventoryJetpack(stack);
        fireBox.setTextureOffset((jetpack.getBurnTicks() > 0) ? 9 : 26, 25);

        GlStateManager.pushMatrix();
        tubeStraightRight.render(scale);
        tubeEndLeft.render(scale);
        tubeEndRight.render(scale);
        base.render(scale);
        tankWallLeft.render(scale);
        tankWallRight.render(scale);
        tubeStraightLeft.render(scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        renderFluidInTank(jetpack.getWaterTank(), fluidCuboid.copy());
        GlStateManager.popMatrix();
    }
}
