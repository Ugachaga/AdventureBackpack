package com.darkona.adventurebackpack.proxy;

import java.lang.reflect.Field;

import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.client.gui.GuiOverlay;
import com.darkona.adventurebackpack.client.models.ModelBackpackArmor;
import com.darkona.adventurebackpack.client.models.ModelCoalJetpack;
import com.darkona.adventurebackpack.client.models.ModelCopterPack;
import com.darkona.adventurebackpack.client.render.RenderRideableSpider;
import com.darkona.adventurebackpack.client.render.RendererAdventureBackpackBlock;
import com.darkona.adventurebackpack.client.render.RendererHose;
import com.darkona.adventurebackpack.client.render.RendererInflatableBoat;
import com.darkona.adventurebackpack.client.render.RendererItemAdventureBackpack;
import com.darkona.adventurebackpack.client.render.RendererItemClockworkCrossbow;
import com.darkona.adventurebackpack.client.render.RendererWearableEquipped;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.handlers.KeybindHandler;
import com.darkona.adventurebackpack.handlers.RenderHandler;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.ModInfo;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 10/10/2014
 *
 * @author Darkona
 */
public class ClientProxy implements IProxy
{

    public static RendererItemAdventureBackpack rendererItemAdventureBackpack;
    public static RendererHose rendererHose;
    public static RendererWearableEquipped rendererWearableEquipped;
    public static RenderHandler renderHandler;
    public static RendererInflatableBoat renderInflatableBoat;
    public static RenderRideableSpider renderRideableSpider;
    public static RendererItemClockworkCrossbow renderCrossbow;
    public static ModelCoalJetpack modelCoalJetpack = new ModelCoalJetpack();
    public static ModelBackpackArmor modelAdventureBackpack = new ModelBackpackArmor();
    public static ModelCopterPack modelCopterPack = new ModelCopterPack();

    public static Field camRollField;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModItems.init();
    }


    @Override
    public void init()
    {
        //initRenderers();
        registerKeybindings();
        MinecraftForge.EVENT_BUS.register(new GuiOverlay(Minecraft.getMinecraft()));
    }

    @Override
    public void initNetwork()
    {

    }

    @Override
    public void joinPlayer(EntityPlayer player)
    {
    }

    public void onPlayerLogin(EntityPlayer player)
    {
    }

    @Override
    public void synchronizePlayer(int id, NBTTagCompound properties)
    {
        Entity entity = Minecraft.getMinecraft().world.getEntityByID(id);
        if (Utils.notNullAndInstanceOf(entity, EntityPlayer.class) && properties != null)
        {
            EntityPlayer player = (EntityPlayer) entity;
            if (BackpackProperty.get(player) == null) BackpackProperty.register(player);
            BackpackProperty.get(player).loadNBTData(properties);
        }
    }

    @Override
    public void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(Keybindings.openBackpack);
        ClientRegistry.registerKeyBinding(Keybindings.toggleHose);
        FMLCommonHandler.instance().bus().register(new KeybindHandler());
    }

    @Override
    public void registerHandlers()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void registerRenderInformation()
    {
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        renderHandler = new RenderHandler();
        MinecraftForge.EVENT_BUS.register(renderHandler);
        rendererWearableEquipped = new RendererWearableEquipped(rm);

        rendererItemAdventureBackpack = new RendererItemAdventureBackpack();
        ClientRegistry.bindTileEntitySpecialRenderer(TileAdventureBackpack.class, new RendererAdventureBackpackBlock());
        int i = 0;
        for (Field curField : EntityRenderer.class.getDeclaredFields())
        {
            if (curField.getType() == float.class)
            {
                if (++i == 15)
                {
                    camRollField = curField;
                    curField.setAccessible(true);
                }
            }
        }
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.MOD_ID + ":" + id, "inventory"));
    }

    public void registerCustomItemRenderer(Item item, int meta, String id) {
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModInfo.MOD_ID + ":" + id, "inventory"));
	}
}

}
