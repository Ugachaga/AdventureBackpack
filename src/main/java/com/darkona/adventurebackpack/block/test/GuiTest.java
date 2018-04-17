package com.darkona.adventurebackpack.block.test;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.client.gui.GuiImageButtonNormal;
import com.darkona.adventurebackpack.client.gui.GuiTank;
import com.darkona.adventurebackpack.client.gui.GuiWithTanks;
import com.darkona.adventurebackpack.common.Constants.Source;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.reference.LoadedMods;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.TinkersUtils;

public class GuiTest extends GuiWithTanks//GuiContainer
{
    private static final ResourceLocation BG_TEXTURE = Resources.getGuiTexture("backpack");
    private static final ResourceLocation TEXTURE = Resources.getGuiTexture("backpack");
    private static final int TINKERS_SLOT = 74; //ContainerBackpack.CRAFT_MATRIX_EMULATION[4] + 36

    private static GuiImageButtonNormal bedButton = new GuiImageButtonNormal(5, 91, 18, 18);
    private static GuiImageButtonNormal equipButton = new GuiImageButtonNormal(5, 91, 18, 18);
    private static GuiImageButtonNormal unequipButton = new GuiImageButtonNormal(5, 91, 18, 18);
    private static GuiTank tankLeft = new GuiTank(25, 7, 100, 16, ConfigHandler.typeTankRender);
    private static GuiTank tankRight = new GuiTank(207, 7, 100, 16, ConfigHandler.typeTankRender);

    private InventoryPlayer playerInv;
    private Source source = Source.TILE;

    public GuiTest(Container container, InventoryPlayer playerInv)
    {
        super(container);
        this.playerInv = playerInv;

        xSize = 248;
        ySize = 207;
    }

    private boolean isBedButtonCase()
    {
        return source == Source.TILE
                || (ConfigHandler.portableSleepingBag && source == Source.WEARING && GuiScreen.isShiftKeyDown());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // Buttons and button highlight
        if (isBedButtonCase())
        {
            if (bedButton.inButton(this, mouseX, mouseY))
                bedButton.draw(this, 20, 227);
            else
                bedButton.draw(this, 1, 227);
        }
        else if (source == Source.WEARING)
        {
            if (unequipButton.inButton(this, mouseX, mouseY))
                unequipButton.draw(this, 96, 227);
            else
                unequipButton.draw(this, 77, 227);
        }
        else if (source == Source.HOLDING)
        {
            if (equipButton.inButton(this, mouseX, mouseY))
                equipButton.draw(this, 96, 208);
            else
                equipButton.draw(this, 77, 208);
        }
        if (ConfigHandler.tanksHoveringText)
        {
            if (tankLeft.inTank(this, mouseX, mouseY))
                drawHoveringText(tankLeft.getTankTooltip(), mouseX, mouseY, fontRenderer);

            if (tankRight.inTank(this, mouseX, mouseY))
                drawHoveringText(tankRight.getTankTooltip(), mouseX, mouseY, fontRenderer);
        }

        if (LoadedMods.TCONSTRUCT && ConfigHandler.tinkerToolsMaintenance)
        {
            if (inventorySlots.getSlot(TINKERS_SLOT).getStack().isEmpty())
            {
                this.mc.getTextureManager().bindTexture(TinkersUtils.GUI_ICONS);
                this.drawTexturedModalRect(this.guiLeft + 169, this.guiTop + 77, 0, 233, 18, 18);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
//        inventory.openInventory(player);
//        FluidTank lft = inventory.getLeftTank();
//        FluidTank rgt = inventory.getRightTank();
//        tankLeft.draw(this, lft);
//        tankRight.draw(this, rgt);
    }

    @Override
    protected GuiImageButtonNormal getEquipButton()
    {
        return equipButton;
    }

    @Override
    protected GuiImageButtonNormal getUnequipButton()
    {
        return unequipButton;
    }
}