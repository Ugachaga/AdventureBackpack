package com.darkona.adventurebackpack.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.config.ConfigHandler;

public class RendererHose
{
    private FontRenderer fontRenderer;

    public void renderItem(/*ItemRenderType type,*/ ItemStack hose, Object... data)
    {
        //if (type == ItemRenderType.INVENTORY)
        {
            GL11.glColor4f(1, 1, 1, 1);
            //IIcon icon = hose.getItem().getIcon(hose, 0);
            //renderHose.renderIcon(0, 0, icon, 16, 16);
            fontRenderer = Minecraft.getMinecraft().fontRenderer;

            if (hose.hasTagCompound())
            {
                String amount = Integer.toString(hose.getTagCompound().getInteger("amount"));
                String name = hose.getTagCompound().getString("fluid");
                String mode;
                switch (hose.getTagCompound().getInteger("mode"))
                {
                    case 0:
                        mode = "Suck";
                        break;
                    case 1:
                        mode = "Spill";
                        break;
                    case 2:
                        mode = "Drink";
                        break;
                    default:
                        mode = "Useless";
                        break;
                }
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                if (fontRenderer != null)
                {
                    fontRenderer.drawString(mode, 0, 0, 0xFFFFFF);
                    if (!ConfigHandler.tanksOverlay)
                    {
                        fontRenderer.drawString(amount, 0, 18, 0xFFFFFF);
                        fontRenderer.drawString(name, 0, 24, 0xFFFFFF);
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }
}
