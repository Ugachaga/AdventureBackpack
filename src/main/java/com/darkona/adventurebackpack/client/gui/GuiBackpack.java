package com.darkona.adventurebackpack.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.common.Constants.Source;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.ContainerBackpack;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.network.SleepingBagPacket;
import com.darkona.adventurebackpack.reference.LoadedMods;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.TinkersUtils;

@SideOnly(Side.CLIENT)
public class GuiBackpack extends GuiWithTanks
{
    private static final ResourceLocation TEXTURE = Resources.getGuiTexture("backpack");
    private static final int TINKERS_SLOT = 74; //ContainerBackpack.CRAFT_MATRIX_EMULATION[4] + 36
    //private static final int TINKERS_SLOT = 38; //ContainerBackpack.CRAFT_MATRIX_EMULATION[4]

    private static GuiImageButtonNormal bedButton = new GuiImageButtonNormal(5, 91, 18, 18);
    private static GuiImageButtonNormal equipButton = new GuiImageButtonNormal(5, 91, 18, 18);
    private static GuiImageButtonNormal unequipButton = new GuiImageButtonNormal(5, 91, 18, 18);
    private static GuiTank tankLeft = new GuiTank(25, 7, 100, 16, ConfigHandler.typeTankRender);
    private static GuiTank tankRight = new GuiTank(207, 7, 100, 16, ConfigHandler.typeTankRender);

    private IInventoryBackpack inventory;

    private boolean isHoldingSpace;

    public GuiBackpack(EntityPlayer player, IInventoryBackpack inventoryBackpack, Source source)
    {
        super(new ContainerBackpack(player, inventoryBackpack, source));
        this.player = player;
        inventory = inventoryBackpack;
        this.source = source;
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
        GL11.glColor4f(1, 1, 1, 1);
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
        inventory.openInventory(player);
        FluidTank lft = inventory.getLeftTank();
        FluidTank rgt = inventory.getRightTank();
        tankLeft.draw(this, lft);
        tankRight.draw(this, rgt);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_BLEND);
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

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException
    {
        if (isBedButtonCase() && bedButton.inButton(this, mouseX, mouseY))
        {
            if (source == Source.TILE)
            {
                TileBackpack te = (TileBackpack) inventory;
                ModNetwork.INSTANCE.sendToServer(new SleepingBagPacket.Message(true, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
            }
            else
            {
                int posX = MathHelper.floor(player.posX);
                int posY = MathHelper.floor(player.posY) - 1;
                int posZ = MathHelper.floor(player.posZ);
                ModNetwork.INSTANCE.sendToServer(new SleepingBagPacket.Message(false, posX, posY, posZ));
            }
        }
        else
        {
            super.mouseClicked(mouseX, mouseY, button);
        }
    }

//    @Override
//    public void updateScreen()
//    {
//        super.updateScreen();
//
//        if (!isHoldingSpace)
//        {
//            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
//            {
//                isHoldingSpace = true;
//                ModNetwork.INSTANCE.sendToServer(new PlayerActionPacket.Message(PlayerActionPacket.GUI_HOLDING_SPACE));
//                inventory.getExtendedProperties().setBoolean(Constants.TAG_HOLDING_SPACE, true);
//            }
//        }
//        else
//        {
//            if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE))
//            {
//                isHoldingSpace = false;
//                ModNetwork.INSTANCE.sendToServer(new PlayerActionPacket.Message(PlayerActionPacket.GUI_NOT_HOLDING_SPACE));
//                inventory.getExtendedProperties().removeTag(Constants.TAG_HOLDING_SPACE);
//            }
//        }
//    }
}
