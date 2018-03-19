package com.darkona.adventurebackpack.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.inventory.IInventoryTanks;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.reference.ToolHandler;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Wearing;

public class GuiOverlay extends Gui
{
    private static final ResourceLocation TANKS_OVERLAY = new ResourceLocation(ModInfo.MODID, "textures/gui/overlay.png");

    private Minecraft mc;
    private RenderItem itemRender;
    private FontRenderer fontRenderer;

    private int screenWidth;
    private int screenHeight;

    public GuiOverlay(Minecraft mc)
    {
        super();
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
        this.itemRender = mc.getRenderItem();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
            return;

        EntityPlayer player = mc.player;
        ScaledResolution resolution = new ScaledResolution(mc);
        screenWidth = resolution.getScaledWidth();
        screenHeight = resolution.getScaledHeight();

        if (ConfigHandler.tanksOverlay)
        {
            if (Wearing.isWearingWearable(player))
            {
                IInventoryTanks inv = Wearing.getWearingWearableInv(player);
                assert inv != null;
                inv.openInventory(player);

                int textureHeight = 23;
                int textureWidth = 10;

                int xPos = ConfigHandler.tanksOverlayRight
                           ? screenWidth - (textureWidth * 2) - ConfigHandler.tanksOverlayIndentH
                           : ConfigHandler.tanksOverlayIndentH;
                int yPos = ConfigHandler.tanksOverlayBottom
                           ? screenHeight - textureHeight - ConfigHandler.tanksOverlayIndentV
                           : ConfigHandler.tanksOverlayIndentV;

                int tankX = xPos;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                if (inv.getTanksArray().length == 1 && ConfigHandler.tanksOverlayRight)
                {
                    tankX += textureWidth + 1; // case for copter, stick lonely tank to window border
                }
                for (FluidTank tank : inv.getTanksArray())
                {
                    this.mc.getTextureManager().bindTexture(TANKS_OVERLAY);
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
                    if (Wearing.isHoldingHose(player))
                    {
                        tank = (short) (ItemHose.getHoseTank(player.getHeldItemMainhand()));
                    }
                    if (tank > -1)
                    {
                        u[0] = (tank == 0) ? 0 : 10;
                        u[1] = (tank == 1) ? 0 : 10;
                    }

                    this.mc.getTextureManager().bindTexture(TANKS_OVERLAY);
                    drawTexturedModalRect(xStart[0], yStart[0], u[0], v[0], textureWidth, textureHeight); //Left Tank
                    drawTexturedModalRect(xStart[1], yStart[0], u[1], v[1], textureWidth, textureHeight); //Right Tank
                    RenderHelper.enableStandardItemLighting();
                    RenderHelper.enableGUIStandardItemLighting();
                    GL11.glPushMatrix();
                    GL11.glTranslatef(xStart[0] - textureWidth, yStart[0], 0);
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                    if (ConfigHandler.enableToolsRender)
                    {
                        ItemStack upperStack = inv.getStackInSlot(Constants.TOOL_UPPER);
                        ItemStack lowerStack = inv.getStackInSlot(Constants.TOOL_LOWER);
                        drawItemStack(upperStack, ToolHandler.getToolHandler(upperStack),0, 0);
                        drawItemStack(lowerStack, ToolHandler.getToolHandler(lowerStack), 0, 16);
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
                //TODO overlay icons
//                IIcon icon = fluid.getFluid().getStillIcon();
//                TextureUtils.bindAtlas(fluid.getFluid().getSpriteNumber());
                int top = startY + height - (fluid.amount / liquidPerPixel);
                for (int j = startY + height - 1; j >= top; j--)
                {
                    for (int i = startX; i <= startX + width - 1; i++)
                    {
                        GL11.glPushMatrix();
                        if (j >= top + 5)
                        {
                            GL11.glColor4f(0.9f, 0.9f, 0.9f, 1);
                        }
                        else
                        {
                            GL11.glColor4f(1, 1, 1, 1);
                        }
//                        GuiTank.drawFluidPixelFromIcon(i, j, icon, 1, 1, 0, 0, 0, 0, 1);
                        GL11.glPopMatrix();
                    }
                }
            }
            catch (Exception oops)
            {
                LogHelper.error("Exception while trying to render the fluid in the GUI");
            }
        }
    }

    private void drawItemStack(ItemStack stack, ToolHandler toolHandler, int x, int y)
    {
        if (stack == null)
            return;

        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;

        switch (toolHandler)
        {
            case GREGTECH:
//                GL11.glTranslatef(x, y, 32.0F);
//                GregtechUtils.renderTool(stack, IItemRenderer.ItemRenderType.INVENTORY);
//                break;
            case TCONSTRUCT:
//                TextureManager tm = mc.getTextureManager();
//                tm.bindTexture(tm.getResourceLocation(stack.getItemSpriteNumber()));
//                GL11.glTranslatef(x, y, 32.0F);
//                TinkersUtils.renderTool(stack, IItemRenderer.ItemRenderType.INVENTORY);
//                break;
            case THAUMCRAFT:
//                // Forge PreRender: net.minecraftforge.client.ForgeHooksClient.renderInventoryItem
//                GL11.glPushMatrix();
//                GL11.glTranslatef(x - 2, y + 3, -3.0F + zLevel);
//                GL11.glScalef(10F, 10F, 10F);
//                GL11.glTranslatef(1.0F, 0.5F, 1.0F);
//                GL11.glScalef(1.0F, 1.0F, -1F);
//                GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
//                // Thaumcraft Render
//                ThaumcraftUtils.renderTool(stack, IItemRenderer.ItemRenderType.INVENTORY);
//                GL11.glPopMatrix();
//                break;
            case VANILLA:
            default:
                GL11.glTranslatef(0F, 0F, 32.0F);
                FontRenderer font = null;
                font = stack.getItem().getFontRenderer(stack);
                if (font == null) font = fontRenderer;
                itemRender.renderItemIntoGUI(stack, x, y);
                break;
        }

        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

}
