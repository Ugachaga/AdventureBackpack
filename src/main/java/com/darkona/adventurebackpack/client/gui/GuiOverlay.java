package com.darkona.adventurebackpack.client.gui;

import java.util.Collection;
import java.util.Iterator;

//import codechicken.lib.texture.TextureUtils;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.util.Wearing;
import com.darkona.adventurebackpack.inventory.IInventoryTanks;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.util.LogHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
//import net.minecraft.potion.PotionType;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.fluids.FluidTank;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 * Created on 09/01/2015
 *
 * @author Darkona
 */
public class GuiOverlay extends Gui
{
    private Minecraft mc;
    private int screenWidth;
    private int screenHeight;
    //protected static RenderItem itemRender = new RenderItem();
    protected FontRenderer fontRendererObj;
    ScaledResolution resolution;

	public GuiOverlay(Minecraft mc)
    {
        super();

        // We need this to invoke the render engine.
        this.mc = mc;
        this.fontRendererObj = mc.getRenderManager().getFontRenderer();
    }

    private static final int BUFF_ICON_SIZE = 18;
    private static final int BUFF_ICON_SPACING = 2; // 2 pixels between buff icons
    private static final int BUFF_ICON_BASE_U_OFFSET = 0;
    private static final int BUFF_ICON_BASE_V_OFFSET = 198;
    private static final int BUFF_ICONS_PER_ROW = 8;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event)
    {
        if(event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
        {
            return;
        }
        resolution = new ScaledResolution(this.mc);
        screenWidth = resolution.getScaledWidth();
        screenHeight = resolution.getScaledHeight();
        if(ConfigHandler.statusOverlay)
        {
            int xPos = ConfigHandler.statusOverlayIndentH;
            int xStep = BUFF_ICON_SIZE + BUFF_ICON_SPACING;
            if (!ConfigHandler.statusOverlayLeft)
            {
        	xPos = screenWidth - BUFF_ICON_SIZE - ConfigHandler.statusOverlayIndentH;
        	xStep = - BUFF_ICON_SIZE - BUFF_ICON_SPACING;
            }
            int yPos = ConfigHandler.statusOverlayIndentV;
            if (!ConfigHandler.statusOverlayTop)
            {
        	yPos = screenHeight - BUFF_ICON_SIZE - ConfigHandler.statusOverlayIndentV;
            }

            @SuppressWarnings("rawtypes")
			Collection collection = this.mc.player.getActivePotionEffects();
            if (!collection.isEmpty())
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));

                for (@SuppressWarnings("rawtypes")
                	Iterator iterator = this.mc.player.getActivePotionEffects()
                        .iterator(); iterator.hasNext(); xPos += xStep)
                {
                    PotionEffect potioneffect = (PotionEffect) iterator.next();
                    Potion potion = potioneffect.getPotion();

                    if (potion.hasStatusIcon())
                    {
                	int iconIndex = potion.getStatusIconIndex();
                	this.drawTexturedModalRect(
                		xPos, yPos,
                		BUFF_ICON_BASE_U_OFFSET + iconIndex % BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE, BUFF_ICON_BASE_V_OFFSET + iconIndex / BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE,
                		BUFF_ICON_SIZE, BUFF_ICON_SIZE);
                    }
                }
            }
        }

        if(ConfigHandler.tanksOverlay)
        {
            EntityPlayer player= mc.player;
            if(Wearing.isWearingWearable(player))
            {
                IInventoryTanks inv = Wearing.getWearableInv(player);
                assert inv != null;
                inv.openInventory(player);

                int textureHeight = 23;
                int textureWidth = 10;

                int xPos = screenWidth - (textureWidth*3) - ConfigHandler.tanksOverlayIndentH;
                if (!ConfigHandler.tanksOverlayRight)
                {
                    xPos = ConfigHandler.tanksOverlayIndentH;
                }
                int yPos = screenHeight - textureHeight - ConfigHandler.tanksOverlayIndentV;
                if (!ConfigHandler.tanksOverlayBottom)
                {
                    yPos = ConfigHandler.tanksOverlayIndentV;
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);

                int tankX = xPos;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                for (FluidTank tank : inv.getTanksArray())
                {

                    mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "textures/gui/overlay.png"));
                    drawTexturedModalRect(tankX, yPos, 10, 0, textureWidth, textureHeight);
                    drawTank(tank, tankX + 1, yPos + 1, textureHeight - 2, textureWidth - 2);
                    ++tankX;
                    tankX += textureWidth;
                }
                if (Wearing.isWearingBackpack(player))
                {
                    int u[] = {10, 10};
                    int v[] = {0, 0};
                    int[] xStart = {xPos, xPos + textureWidth + 1};
                    int[] yStart = {yPos, yPos};
                    short tank = -1;
                    if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemHose)
                    {
                        tank = (short) (ItemHose.getHoseTank(player.getHeldItemMainhand()));
                    }
                    if (tank > -1)
                    {
                        u[0] = (tank == 0) ? 0 : 10;
                        u[1] = (tank == 1) ? 0 : 10;
                    }
                    mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "textures/gui/overlay.png"));

                    //Left Tank
                    drawTexturedModalRect(xStart[0], yStart[0], u[0], v[0], textureWidth, textureHeight);
                    //Right Tank
                    drawTexturedModalRect(xStart[1], yStart[0], u[1], v[1], textureWidth, textureHeight);
                    RenderHelper.enableStandardItemLighting();
                    RenderHelper.enableGUIStandardItemLighting();
                    GL11.glPushMatrix();
                    GL11.glTranslatef(xStart[1] + textureWidth + 2, yStart[0], 0);
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                    if (ConfigHandler.enableToolsRender)
                    {
                        drawItemStack(inv.getStackInSlot(Constants.upperTool), 0, 0);
                        drawItemStack(inv.getStackInSlot(Constants.lowerTool), 0, 16);
                    }
                    GL11.glPopMatrix();
                    RenderHelper.disableStandardItemLighting();

                }
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }

    private void drawTank(FluidTank tank, int startX, int startY, int height, int width)
    {
        int liquidPerPixel = tank.getCapacity() / height;
        FluidStack fluid = tank.getFluid();
        if (fluid != null)
        {
            try
            {
                //IIcon icon = fluid.getFluid().getStillIcon();
                //TextureUtils.bindAtlas(fluid.getFluid().getSpriteNumber());
                int top = startY + height - (fluid.amount / liquidPerPixel);
                for (int j = startY + height - 1; j >= top; j--)
                {
                    for (int i = startX; i <= startX + width - 1; i++)
                    {
                        GL11.glPushMatrix();
                        if (j >= top + 5)
                        {
                            GL11.glColor4f(0.9f, 0.9f, 0.9f, 1);
                        } else
                        {
                            GL11.glColor4f(1, 1, 1, 1);
                        }
                        //GuiTank.drawFluidPixelFromIcon(i, j, icon, 1, 1, 0, 0, 0, 0,1);
                        GL11.glPopMatrix();
                    }
                }
            } catch (Exception oops)
            {
                LogHelper.error("Exception while trying to render the fluid in the GUI");
            }
        }
    }

    private void drawItemStack(ItemStack stack, int x, int y)
    {
        if(stack == null)return;
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        //itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        //itemRender.renderItemIntoGUI(font,mc.getTextureManager(),stack,x,y);
        this.zLevel = 0.0F;
        //yitemRender.zLevel = 0.0F;
    }

}
