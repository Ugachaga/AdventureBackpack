package com.darkona.adventurebackpack.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.darkona.adventurebackpack.client.gui.GuiBackpack;
import com.darkona.adventurebackpack.client.gui.GuiCopter;
import com.darkona.adventurebackpack.client.gui.GuiJetpack;
import com.darkona.adventurebackpack.client.render.RenderFriendlySpider;
import com.darkona.adventurebackpack.client.renderer.WearableRenderer;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.handlers.KeyInputEventHandler;
import com.darkona.adventurebackpack.inventory.IInventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopter;
import com.darkona.adventurebackpack.inventory.InventoryJetpack;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy
{
    private final Minecraft MC = Minecraft.getMinecraft();

    @Override
    public void preInit()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityFriendlySpider.class, RenderFriendlySpider::new); //TODO move
    }

    @Override
    public void init()
    {
        registerKeybindings();
        registerLayers();

//        MinecraftForge.EVENT_BUS.register(new GuiOverlay(Minecraft.getMinecraft()));
    }

    @Override
    public void postInit()
    {

    }

    private void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(Keybindings.openInventory);
        ClientRegistry.registerKeyBinding(Keybindings.toggleActions);
        MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
    }

    private void registerLayers()
    {
        MC.getRenderManager().getSkinMap().forEach((s, render) -> render.addLayer(new WearableRenderer.WearableLayer()));
    }

    @Override
    public void displayBackpackGUI(EntityPlayer player, IInventoryBackpack inv, Constants.Source source)
    {
        System.out.println("player: " + player + "  inv: " + inv + "  source: " + source);
        MC.displayGuiScreen(new GuiBackpack(player, inv, source)); //TODO merge to one method, switch by enum
    }

    @Override
    public void displayCopterGUI(EntityPlayer player, InventoryCopter inv, Constants.Source source)
    {
        MC.displayGuiScreen(new GuiCopter(player, inv, source));
    }

    @Override
    public void displayJetpackGUI(EntityPlayer player, InventoryJetpack inv, Constants.Source source)
    {
        MC.displayGuiScreen(new GuiJetpack(player, inv, source));
    }

//    @Override
//    public void synchronizePlayer(int id, NBTTagCompound properties)
//    {
//        Entity entity = Minecraft.getMinecraft().world.getEntityByID(id);
//
//        if (entity instanceof EntityPlayer && properties != null)
//        {
//            EntityPlayer player = (EntityPlayer) entity;
//
//            if (BackpackProperty.get(player) == null)
//                BackpackProperty.register(player);
//
//            BackpackProperty.get(player).loadNBTData(properties);
//        }
//    }

}
