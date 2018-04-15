package com.darkona.adventurebackpack.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;

import com.darkona.adventurebackpack.common.Constants;

public abstract class ModelWearable extends ModelBase //ModelBiped
{
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

    //public abstract void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack stack);

    protected void renderFluidInTank(FluidTank tank, Vector3 minCoords, Vector3 maxCoords, Vector3 offset, ModelRenderer parent)
    { //TODO move to renderFluidInTank2
        if (tank.getFluid() != null)
        {
            Vector3 victor = new Vector3(
                    (parent.rotationPointX * 0.1f + parent.offsetX * 0.1 + offset.x),
                    (parent.rotationPointY * 0.1f + parent.offsetY * 0.1 + offset.y),
                    (parent.rotationPointZ * 0.1f + parent.offsetZ * 0.1 + offset.z));

            Cuboid6 cat = new Cuboid6(minCoords.x, minCoords.y, minCoords.z, maxCoords.x, maxCoords.y, maxCoords.z);
            RenderUtils.renderFluidCuboidGL(tank.getFluid(), cat.add(victor), ((1.0F * tank.getFluidAmount()) / (1.0F * tank.getCapacity())), 0.8);
        }
    }

    protected void renderFluidInTank2(FluidTank tank, Cuboid6 cuboid)
    {
        if (tank.getFluid() == null)
        {
            //return;
        }

        double fullness = ((double) tank.getFluidAmount()) / ((double) tank.getCapacity());

        FluidStack fs = new FluidStack(FluidRegistry.WATER, Constants.BASIC_TANK_CAPACITY); //TODO del
        fullness = 1.0; //del

        RenderUtils.renderFluidCuboidGL(fs/*tank.getFluid()*/, cuboid, fullness, 0.8);
    }
}
