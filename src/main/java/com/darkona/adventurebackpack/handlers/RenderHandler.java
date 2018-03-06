package com.darkona.adventurebackpack.handlers;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created on 25/12/2014
 *
 * @author Darkona
 */
public class RenderHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void playerSpecialsRendering(RenderPlayerEvent.Specials.Pre event)
    {
        float rotationY = event.getRenderer().getMainModel().bipedBody.rotateAngleY;
        float rotationX = event.getRenderer().getMainModel().bipedBody.rotateAngleX;
        float rotationZ = event.getRenderer().getMainModel().bipedBody.rotateAngleZ;

        double x = event.getEntity().posX;
        double y = event.getEntity().posY;
        double z = event.getEntity().posZ;

        float pitch = event.getEntity().rotationPitch;
        float yaw = event.getEntity().rotationYaw;

        //TODO render
        //ClientProxy.rendererWearableEquipped.render(event.getEntity(), x, y, z, rotationX, rotationY, rotationZ, pitch, yaw);

        event.setRenderCape(false);
    }
}
