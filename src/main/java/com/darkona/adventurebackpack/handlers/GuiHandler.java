package com.darkona.adventurebackpack.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.gui.GuiBackpack;
import com.darkona.adventurebackpack.client.gui.GuiCopter;
import com.darkona.adventurebackpack.client.gui.GuiJetpack;
import com.darkona.adventurebackpack.common.Constants.Source;
import com.darkona.adventurebackpack.inventory.ContainerBackpack;
import com.darkona.adventurebackpack.inventory.ContainerCopter;
import com.darkona.adventurebackpack.inventory.ContainerJetpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */
public class GuiHandler implements IGuiHandler
{
    private static final byte BACKPACK_TILE = 0;
    private static final byte BACKPACK_HOLDING = 1;
    private static final byte BACKPACK_WEARING = 2;

    private static final byte COPTER_HOLDING = 3;
    private static final byte COPTER_WEARING = 4;

    private static final byte JETPACK_HOLDING = 5;
    private static final byte JETPACK_WEARING = 6;

    public GuiHandler() {}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case BACKPACK_TILE:
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntity != null && tileEntity instanceof TileBackpack)
                {
                    return new ContainerBackpack(player, (TileBackpack) tileEntity, Source.TILE);
                }
                break;
            case BACKPACK_HOLDING:
                if (Wearing.isHoldingBackpack(player))
                {
                    return new ContainerBackpack(player, new InventoryBackpack(Wearing.getHoldingBackpack(player)), Source.HOLDING);
                }
                break;
            case BACKPACK_WEARING:
                if (Wearing.isWearingBackpack(player))
                {
                    return new ContainerBackpack(player, new InventoryBackpack(Wearing.getWearingBackpack(player)), Source.WEARING);
                }
                break;
            case COPTER_HOLDING:
                if (Wearing.isHoldingCopter(player))
                {
                    return new ContainerCopter(player, new InventoryCopter(Wearing.getHoldingCopter(player)), Source.HOLDING);
                }
                break;
            case COPTER_WEARING:
                if (Wearing.isWearingCopter(player))
                {
                    return new ContainerCopter(player, new InventoryCopter(Wearing.getWearingCopter(player)), Source.WEARING);
                }
                break;
            case JETPACK_HOLDING:
                if (Wearing.isHoldingJetpack(player))
                {
                    return new ContainerJetpack(player, new InventoryJetpack(Wearing.getHoldingJetpack(player)), Source.HOLDING);
                }
                break;
            case JETPACK_WEARING:
                if (Wearing.isWearingJetpack(player))
                {
                    return new ContainerJetpack(player, new InventoryJetpack(Wearing.getWearingJetpack(player)), Source.WEARING);
                }
                break;
            default:
                player.closeScreen();
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case BACKPACK_TILE:
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntity != null && tileEntity instanceof TileBackpack)
                {
                    return new GuiBackpack(player, (TileBackpack) tileEntity, Source.TILE);
                }
                break;
            case BACKPACK_HOLDING:
                if (Wearing.isHoldingBackpack(player))
                {
                    return new GuiBackpack(player, new InventoryBackpack(Wearing.getHoldingBackpack(player)), Source.HOLDING);
                }
                break;
            case BACKPACK_WEARING:
                if (Wearing.isWearingBackpack(player))
                {
                    return new GuiBackpack(player, new InventoryBackpack(Wearing.getWearingBackpack(player)), Source.WEARING);
                }
                break;
            case COPTER_HOLDING:
                if (Wearing.isHoldingCopter(player))
                {
                    return new GuiCopter(player, new InventoryCopter(Wearing.getHoldingCopter(player)), Source.HOLDING);
                }
                break;
            case COPTER_WEARING:
                if (Wearing.isWearingCopter(player))
                {
                    return new GuiCopter(player, new InventoryCopter(Wearing.getWearingCopter(player)), Source.WEARING);
                }
                break;
            case JETPACK_HOLDING:
                if (Wearing.isHoldingJetpack(player))
                {
                    return new GuiJetpack(player, new InventoryJetpack(Wearing.getHoldingJetpack(player)), Source.HOLDING);
                }
                break;
            case JETPACK_WEARING:
                if (Wearing.isWearingJetpack(player))
                {
                    return new GuiJetpack(player, new InventoryJetpack(Wearing.getWearingJetpack(player)), Source.WEARING);
                }
                break;
            default:
                player.closeScreen();
                break;
        }
        return null;
    }
}
