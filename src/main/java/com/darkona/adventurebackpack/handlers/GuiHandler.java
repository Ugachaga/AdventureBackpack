package com.darkona.adventurebackpack.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.client.gui.GuiAdvBackpack;
import com.darkona.adventurebackpack.client.gui.GuiCoalJetpack;
import com.darkona.adventurebackpack.client.gui.GuiCopterPack;
import com.darkona.adventurebackpack.common.Constants.Source;
import com.darkona.adventurebackpack.inventory.ContainerBackpack;
import com.darkona.adventurebackpack.inventory.ContainerCopter;
import com.darkona.adventurebackpack.inventory.ContainerJetpack;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCoalJetpack;
import com.darkona.adventurebackpack.inventory.InventoryCopterPack;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */
public class GuiHandler implements IGuiHandler
{
    public static final byte JETPACK_WEARING = 6;
    public static final byte JETPACK_HOLDING = 5;
    public static final byte COPTER_WEARING = 4;
    public static final byte COPTER_HOLDING = 3;
    public static final byte BACKPACK_WEARING = 2;
    public static final byte BACKPACK_HOLDING = 1;
    public static final byte BACKPACK_TILE = 0;

    public GuiHandler()
    {

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case BACKPACK_TILE:
                BlockPos pos = new BlockPos(x, y, z);
                if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBackpack)
                {
                    return new ContainerBackpack(player, (TileBackpack) world.getTileEntity(pos), Source.TILE);
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
                    return new ContainerCopter(player, new InventoryCopterPack(Wearing.getHoldingCopter(player)), Source.HOLDING);
                }
                break;
            case COPTER_WEARING:
                if (Wearing.isWearingCopter(player))
                {
                    return new ContainerCopter(player, new InventoryCopterPack(Wearing.getWearingCopter(player)), Source.WEARING);
                }
                break;
            case JETPACK_HOLDING:
                if (Wearing.isHoldingJetpack(player))
                {
                    return new ContainerJetpack(player, new InventoryCoalJetpack(Wearing.getHoldingJetpack(player)), Source.HOLDING);
                }
                break;
            case JETPACK_WEARING:
                if (Wearing.isWearingJetpack(player))
                {
                    return new ContainerJetpack(player, new InventoryCoalJetpack(Wearing.getWearingJetpack(player)), Source.WEARING);
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
                BlockPos pos = new BlockPos(x, y, z);
                if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBackpack)
                {
                    return new GuiAdvBackpack(player, (TileBackpack) world.getTileEntity(pos), Source.TILE);
                }
                break;
            case BACKPACK_HOLDING:
                if (Wearing.isHoldingBackpack(player))
                {
                    return new GuiAdvBackpack(player, new InventoryBackpack(Wearing.getHoldingBackpack(player)), Source.HOLDING);
                }
                break;
            case BACKPACK_WEARING:
                if (Wearing.isWearingBackpack(player))
                {
                    return new GuiAdvBackpack(player, new InventoryBackpack(Wearing.getWearingBackpack(player)), Source.WEARING);
                }
                break;
            case COPTER_HOLDING:
                if (Wearing.isHoldingCopter(player))
                {
                    return new GuiCopterPack(player, new InventoryCopterPack(Wearing.getHoldingCopter(player)), Source.HOLDING);
                }
                break;
            case COPTER_WEARING:
                if (Wearing.isWearingCopter(player))
                {
                    return new GuiCopterPack(player, new InventoryCopterPack(Wearing.getWearingCopter(player)), Source.WEARING);
                }
                break;
            case JETPACK_HOLDING:
                if (Wearing.isHoldingJetpack(player))
                {
                    return new GuiCoalJetpack(player, new InventoryCoalJetpack(Wearing.getHoldingJetpack(player)), Source.HOLDING);
                }
                break;
            case JETPACK_WEARING:
                if (Wearing.isWearingJetpack(player))
                {
                    return new GuiCoalJetpack(player, new InventoryCoalJetpack(Wearing.getWearingJetpack(player)), Source.WEARING);
                }
                break;
            default:
                player.closeScreen();
                break;
        }
        return null;
    }
}
