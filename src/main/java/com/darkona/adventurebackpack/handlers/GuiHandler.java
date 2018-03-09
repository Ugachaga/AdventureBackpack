package com.darkona.adventurebackpack.handlers;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.gui.GuiBackpack;
import com.darkona.adventurebackpack.common.Constants.Source;
import com.darkona.adventurebackpack.inventory.ContainerBackpack;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */
public class GuiHandler implements IGuiHandler
{
    public static final byte BACKPACK_TILE = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID)
        {
            case BACKPACK_TILE:
                if (tileEntity != null && tileEntity instanceof TileBackpack)
                {
                    return new ContainerBackpack(player, (TileBackpack) tileEntity, Source.TILE);
                }
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID)
        {
            case BACKPACK_TILE:
                if (tileEntity != null && tileEntity instanceof TileBackpack)
                {
                    return new GuiBackpack(player, (TileBackpack) tileEntity, Source.TILE);
                }
            default:
                return null;
        }
    }
}
