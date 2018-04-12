package com.darkona.adventurebackpack.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import com.darkona.adventurebackpack.client.gui.GuiBackpack;
import com.darkona.adventurebackpack.client.gui.GuiCopter;
import com.darkona.adventurebackpack.client.gui.GuiJetpack;
import com.darkona.adventurebackpack.client.models.ModelBackpack;
import com.darkona.adventurebackpack.client.models.ModelCopter;
import com.darkona.adventurebackpack.client.models.ModelJetpack;
import com.darkona.adventurebackpack.client.renderer.BackpackRenderer;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.handlers.KeyInputEventHandler;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.reference.LoadedMods;

public class ClientProxy implements IProxy
{
    //public static RendererWearableEquipped rendererWearableEquipped = new RendererWearableEquipped(); //TODO renderManager?
    public static ModelBackpack modelBackpack = new ModelBackpack();
    public static ModelJetpack modelJetpack = new ModelJetpack();
    public static ModelCopter modelCopter = new ModelCopter();

    private final Minecraft MINECRAFT = Minecraft.getMinecraft();

    @Override
    public void registerLayers()
    {
        Minecraft.getMinecraft().getRenderManager().getSkinMap().forEach((s, render) -> render.addLayer(new BackpackRenderer.Layer()));
    }

    @Override
    public void displayBackpackGUI(EntityPlayer player, IInventoryBackpack inv, Constants.Source source)
    {
        System.out.println("player: " + player + "  inv: " + inv + "  source: " + source);
        MINECRAFT.displayGuiScreen(new GuiBackpack(player, inv, source)); //TODO merge to one method, switch by enum
    }

    @Override
    public void displayCopterGUI(EntityPlayer player, InventoryCopter inv, Constants.Source source)
    {
        MINECRAFT.displayGuiScreen(new GuiCopter(player, inv, source));
    }

    @Override
    public void displayJetpackGUI(EntityPlayer player, InventoryJetpack inv, Constants.Source source)
    {
        MINECRAFT.displayGuiScreen(new GuiJetpack(player, inv, source));
    }

    @Override
    public void init()
    {
        registerKeybindings();
//        MinecraftForge.EVENT_BUS.register(new GuiOverlay(Minecraft.getMinecraft()));

        if (LoadedMods.NEI)
        {
            //TODO NEI utilize
//            codechicken.nei.api.API.hideItem(new ItemStack(ModBlocks.BACKPACK_BLOCK, 1, OreDictionary.WILDCARD_VALUE));
//            codechicken.nei.api.API.hideItem(new ItemStack(ModBlocks.SLEEPING_BAG_BLOCK, 1, OreDictionary.WILDCARD_VALUE));
        }
    }

    @Override
    public void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(Keybindings.openInventory);
        ClientRegistry.registerKeyBinding(Keybindings.toggleActions);
        MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
    }

    @Override
    public void synchronizePlayer(int id, NBTTagCompound properties)
    {
        Entity entity = Minecraft.getMinecraft().world.getEntityByID(id);

        if (entity instanceof EntityPlayer && properties != null)
        {
            EntityPlayer player = (EntityPlayer) entity;

            if (BackpackProperty.get(player) == null)
                BackpackProperty.register(player);

            BackpackProperty.get(player).loadNBTData(properties);
        }
    }

}
