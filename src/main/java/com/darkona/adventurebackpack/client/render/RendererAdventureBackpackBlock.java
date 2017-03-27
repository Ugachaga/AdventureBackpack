package com.darkona.adventurebackpack.client.render;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.client.models.ModelBackpackBlock;
import com.darkona.adventurebackpack.reference.BackpackNames;
import com.darkona.adventurebackpack.util.Resources;
import com.darkona.adventurebackpack.util.Utils;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */

@SuppressWarnings("unused")
public class RendererAdventureBackpackBlock extends TileEntitySpecialRenderer
{

    private ModelBackpackBlock model;

    public RendererAdventureBackpackBlock()
    {
        this.model = new ModelBackpackBlock();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        int dir = te.getBlockMetadata();
        if ((dir & 8) >= 8)
        {
            dir -= 8;
        }
        if ((dir & 4) >= 4)
        {
            dir -= 4;
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5f, (float) z + 0.5F);

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();
        if (dir == 0)
        {
            GL11.glRotatef(-180F, 0.0F, 1.0F, 0.0F);
        }
        if (dir % 2 != 0)
        {
            GL11.glRotatef(dir * (-90F), 0.0F, 1.0F, 0.0F);
        }
        if (dir % 2 == 0)
        {
            GL11.glRotatef(dir * (-180F), 0.0F, 1.0F, 0.0F);
        }
        ResourceLocation modelTexture;
        if (BackpackNames.getBackpackColorName((TileAdventureBackpack) te).equals("Standard"))
        {
            modelTexture = Resources.backpackTextureFromString(Utils.getHoliday());
        } else
        {
            modelTexture = Resources.backpackTextureFromColor((TileAdventureBackpack) te);
        }

        bindTexture(modelTexture);
        model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1 / 20F, (TileAdventureBackpack) te);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();

    }


}
