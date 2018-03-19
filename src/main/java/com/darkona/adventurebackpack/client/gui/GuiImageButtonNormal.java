package com.darkona.adventurebackpack.client.gui;

public class GuiImageButtonNormal
{
    private int X;
    private int Y;
    private int W;
    private int H;

    public GuiImageButtonNormal(int U, int V, int W, int H)
    {
        this.X = U;
        this.Y = V;
        this.W = W;
        this.H = H;
    }

    public void draw(GuiWithTanks gui, int U, int V)
    {
        gui.drawTexturedModalRect(gui.getLeft() + X, gui.getTop() + Y, U, V, W, H);
    }

    public boolean inButton(GuiWithTanks gui, int mouseX, int mouseY)
    {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();
        return X <= mouseX && mouseX <= X + W && Y <= mouseY && mouseY <= Y + H;
    }
}
