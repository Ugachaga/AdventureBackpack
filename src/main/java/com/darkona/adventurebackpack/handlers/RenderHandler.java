package com.darkona.adventurebackpack.handlers;

import com.darkona.adventurebackpack.proxy.ClientProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.RenderPlayerEvent;

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

        double x = event.getEntityPlayer().posX;
        double y = event.getEntityPlayer().posY;
        double z = event.getEntityPlayer().posZ;

        float pitch = event.getEntityPlayer().rotationPitch;
        float yaw = event.getEntityPlayer().rotationYaw;
        ClientProxy.rendererWearableEquipped.render(event.getEntityPlayer(), x, y, z, rotationX, rotationY, rotationZ, pitch, yaw);
    }
}
