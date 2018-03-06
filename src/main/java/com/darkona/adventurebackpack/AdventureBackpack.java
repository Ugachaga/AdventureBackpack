package com.darkona.adventurebackpack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.handlers.ClientEventHandler;
import com.darkona.adventurebackpack.handlers.GeneralEventHandler;
import com.darkona.adventurebackpack.handlers.GuiHandler;
import com.darkona.adventurebackpack.handlers.PlayerEventHandler;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModDates;
import com.darkona.adventurebackpack.init.ModEntities;
import com.darkona.adventurebackpack.init.ModFluids;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.init.ModRecipes;
import com.darkona.adventurebackpack.init.ModWorldGen;
import com.darkona.adventurebackpack.proxy.IProxy;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.reference.LoadedMods;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.reference.WailaTileAdventureBackpack;

/**
 * Created on 10/10/2014
 *
 * @author Javier Darkona
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, guiFactory = ModInfo.GUI_FACTORY_CLASS,
        dependencies = "required-after:codechickenlib@[3.1.5.331,)")
public class AdventureBackpack
{
    @SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_SERVER_PROXY)
    public static IProxy proxy;

    @Mod.Instance(ModInfo.MOD_ID)
    public static AdventureBackpack instance;

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Configuration
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        //ModStuff
        ModDates.init();
        ModItems.init();
        ModBlocks.init();
        ModFluids.init();
        FluidEffectRegistry.init();
        ModEntities.init();
        ModNetwork.init();
        proxy.initNetwork();

        //Events
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LoadedMods.init();
        proxy.init();
        ModRecipes.init();
        ModWorldGen.init();
        WailaTileAdventureBackpack.init();

        //GUIs
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        GeneralReference.init();

        //ConditionalFluidEffect.init();
        //ModItems.conditionalInit();
        //ModRecipes.conditionalInit();
    }
}
