package com.darkona.adventurebackpack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.handlers.ClientEventHandler;
import com.darkona.adventurebackpack.handlers.GeneralEventHandler;
import com.darkona.adventurebackpack.handlers.GuiHandler;
import com.darkona.adventurebackpack.handlers.PlayerEventHandler;
import com.darkona.adventurebackpack.init.ModDates;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.init.ModRecipes;
import com.darkona.adventurebackpack.init.ModWorldGen;
import com.darkona.adventurebackpack.proxy.IProxy;
import com.darkona.adventurebackpack.reference.GeneralReference;
import com.darkona.adventurebackpack.reference.LoadedMods;
import com.darkona.adventurebackpack.reference.ModInfo;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION, guiFactory = ModInfo.GUI_FACTORY,
        dependencies = "required-after:codechickenlib@[3.1.5.331,)")
public class AdventureBackpack
{
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static IProxy PROXY;

    @Mod.Instance(ModInfo.MODID)
    public static AdventureBackpack INSTANCE;

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Configuration
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        //ModStuff
        ModDates.init();

        FluidEffectRegistry.init();
        ModNetwork.init();

        //Events
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LoadedMods.init();
        PROXY.init();
        ModRecipes.init();
        ModWorldGen.init();
        //WailaTileAdventureBackpack.init();
        FMLInterModComms.sendMessage("waila", "register", "com.darkona.adventurebackpack.reference.WailaTileAdventureBackpack.callbackRegister");

        //GUIs
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
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
