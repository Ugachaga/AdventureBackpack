package com.darkona.adventurebackpack.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.client.models.ModelAdventureHat;
import com.darkona.adventurebackpack.util.Resources;

public class AdventureHatRenderer
{
    private final ModelAdventureHat model;

    public AdventureHatRenderer()
    {
        model = ModelAdventureHat.instance;
    }
    public void renderItem(/*IItemRenderer.ItemRenderType type,*/ ItemStack item, Object... data)
    {
        ResourceLocation modelTexture = Resources.getModelTexture("adventure_hat");
        Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);
        //switch (type)
        {
            //case INVENTORY:
            {
                GL11.glPushMatrix();
                GL11.glColor4f(1, 1, 1, 128);

                GL11.glPushMatrix();
                GL11.glTranslatef(-0.5f, -1.0f, -0.5f);

                GL11.glPushMatrix();
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                GL11.glPushMatrix();
                GL11.glScalef(1.5f, 1.5f, 1.5f);

                model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.05F);

                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }

            //case ENTITY:
                Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);
            {
                GL11.glPushMatrix();
                GL11.glColor4f(1, 1, 1, 128);

                GL11.glPushMatrix();
                GL11.glTranslatef(0f, 0f, 0f);

                GL11.glPushMatrix();
                GL11.glRotatef(180, 0, 0, 1);

                GL11.glPushMatrix();
                GL11.glScalef(1.0f, 1.0f, 1.0f);

                model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.05F);

                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }

            //case EQUIPPED:
                Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);

                GL11.glPushMatrix();
                GL11.glColor4f(1, 1, 1, 128);
                GL11.glPushMatrix();
                GL11.glScalef(1.0f, 1.0f, 1.0f);

                GL11.glPushMatrix();
                GL11.glRotatef(180, 0, 0, 1);

                GL11.glPushMatrix();
                GL11.glRotatef(90, 0, 1, 0);

                GL11.glPushMatrix();
                GL11.glRotatef(-95, 1, 0, 0);

                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 1.2f, -.6f);

                model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.05F);

                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();

            //case EQUIPPED_FIRST_PERSON:
                Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);

                GL11.glPushMatrix();
                GL11.glColor4f(1, 1, 1, 128);

                GL11.glPushMatrix();
                GL11.glTranslatef(-.1f, 0f, 0.0f);

                GL11.glPushMatrix();
                GL11.glScalef(1.0f, 1.0f, 1.0f);

                GL11.glPushMatrix();
                GL11.glRotatef(180, 0, 0, 1);

                GL11.glPushMatrix();
                GL11.glRotatef(180, 0, 1, 0);

                model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.05F);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();

            //case FIRST_PERSON_MAP:
        }
    }
}