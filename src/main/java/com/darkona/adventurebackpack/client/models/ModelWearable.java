package com.darkona.adventurebackpack.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fluids.FluidTank;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;

@SuppressWarnings("WeakerAccess")
public abstract class ModelWearable extends ModelBase
{
    protected Vector3 createVector3(ModelRenderer parent, double x, double y, double z)
    {
        return new Vector3(
                (parent.rotationPointX * 0.1 + parent.offsetX * 0.1 + x),
                (parent.rotationPointY * 0.1 + parent.offsetY * 0.1 + y),
                (parent.rotationPointZ * 0.1 + parent.offsetZ * 0.1 + z));
    }

    protected void startBlending()
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    protected void stopBlending()
    {
        GL11.glDisable(GL11.GL_BLEND);
    }

    protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    protected void setOffset(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.offsetX = x;
        modelRenderer.offsetY = y;
        modelRenderer.offsetZ = z;
    }

    protected void setRotationPoints(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotationPointX = x;
        modelRenderer.rotationPointY = y;
        modelRenderer.rotationPointZ = z;
    }

    protected void renderFluidInTank(FluidTank tank, Cuboid6 cuboid)
    {
        if (tank.getFluid() == null) return;

        double fullness = ((double) tank.getFluidAmount()) / ((double) tank.getCapacity());
        RenderUtils.renderFluidCuboidGL(tank.getFluid(), cuboid, fullness, 0.8);

//        FluidStack fs = new FluidStack(FluidRegistry.WATER, Constants.BASIC_TANK_CAPACITY);
//        RenderUtils.renderFluidCuboidGL(fs, cuboid, 1.0, 0.8);
    }
}
