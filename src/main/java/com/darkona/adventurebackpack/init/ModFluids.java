package com.darkona.adventurebackpack.init;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.Resources;

@SuppressWarnings("WeakerAccess")
public class ModFluids
{
    public static final Set<Fluid> FLUIDS = new HashSet<>();
    public static final Set<IFluidBlock> MOD_FLUID_BLOCKS = new HashSet<>();

    // getColor: 0xffffff
    public static final Fluid MILK = createFluid("milk", false,
            fluid -> fluid.setLuminosity(0).setDensity(1200).setViscosity(1200),
            fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SNOW)));

    // getColor: 0xc31d08
    public static final Fluid MELON_JUICE = createFluid("melon_juice", true,
            fluid -> fluid.setLuminosity(0).setDensity(1000).setViscosity(1000),
            fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.RED)));

    // getColor: 0xcd8c6f
    public static final Fluid MUSHROOM_STEW = createFluid("mushroom_stew", true,
            fluid -> fluid.setLuminosity(0).setDensity(1200).setViscosity(1200),
            fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.BROWN)));

    private static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory)
    {
        String texturePrefix = Resources.RESOURCE_PREFIX + "blocks/fluid_";

        ResourceLocation still = new ResourceLocation(texturePrefix + name + "_still");
        ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + name + "_flow") : still;

        Fluid fluid = new Fluid(name, still, flowing);
        boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

        if (useOwnFluid)
        {
            fluidPropertyApplier.accept(fluid);
            MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
        }
        else
        {
            fluid = FluidRegistry.getFluid(name);
        }

        FLUIDS.add(fluid);

        return fluid;
    }

    @Mod.EventBusSubscriber(modid = ModInfo.MODID)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event)
        {
            IForgeRegistry<Block> registry = event.getRegistry();

            for (IFluidBlock fluidBlock : MOD_FLUID_BLOCKS)
            {
                Block block = (Block) fluidBlock;
                block.setRegistryName(ModInfo.MODID, "fluid." + fluidBlock.getFluid().getName());
                block.setUnlocalizedName(Resources.RESOURCE_PREFIX + fluidBlock.getFluid().getUnlocalizedName());
                block.setCreativeTab(ModInfo.CREATIVE_TAB);
                registry.register(block);
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
            IForgeRegistry<Item> registry = event.getRegistry();

            for (IFluidBlock fluidBlock : MOD_FLUID_BLOCKS)
            {
                Block block = (Block) fluidBlock;
                ItemBlock itemBlock = new ItemBlock(block);
                ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName());
                itemBlock.setRegistryName(registryName);
                registry.register(itemBlock);
            }

            registerFluidContainers();
        }
    }

    private static void registerFluidContainers()
    {
        for (Fluid fluid : FLUIDS)
        {
            registerBucket(fluid);
            //TODO register: MELON_JUICE + Items.GLASS_BOTTLE = ModItems.MELON_JUICE_BOTTLE
            //TODO register: MUSHROOM_STEW + Items.BOWL = Items.MUSHROOM_STEW
        }
    }

    private static void registerBucket(Fluid fluid)
    {
        FluidRegistry.addBucketForFluid(fluid);
    }
}
