package com.darkona.adventurebackpack.client.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.block.test.TESRTest;
import com.darkona.adventurebackpack.block.test.TileTest;
import com.darkona.adventurebackpack.client.renderer.TileEntityItemSpecialRenderer;
import com.darkona.adventurebackpack.client.renderer.BackpackRenderer;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModFluids;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.item.ItemComponent;
import com.darkona.adventurebackpack.reference.IType;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.Resources;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModInfo.MODID)
public class ModModelManager
{
    public static final ModModelManager INSTANCE = new ModModelManager();

    private static final String FLUID_MODEL_PATH = Resources.RESOURCE_PREFIX + "fluid";

    private ModModelManager() {}

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        INSTANCE.registerFluidModels();
        INSTANCE.registerTileEntityModels();
        INSTANCE.registerBlockModels();
        INSTANCE.registerItemModels();

        TileEntityItemStackRenderer.instance = new TileEntityItemSpecialRenderer(TileEntityItemStackRenderer.instance);
    }

    private void registerFluidModels()
    {
        ModFluids.MOD_FLUID_BLOCKS.forEach(this::registerFluidModel);
    }

    private void registerFluidModel(IFluidBlock fluidBlock)
    {
        Item item = Item.getItemFromBlock((Block) fluidBlock);
        assert item != Items.AIR;
        ModelBakery.registerItemVariants(item);
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());
        ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);
        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_)
            {
                return modelResourceLocation;
            }
        });
    }

    private void registerTileEntityModels()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileBackpack.class, new BackpackRenderer.TileEntity());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTest.class, new TESRTest());
    }

    private final Set<Item> itemsRegistered = new HashSet<>();

    private void registerBlockModels()
    {
        ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream()
                .filter(item -> !itemsRegistered.contains(item))
                .forEach(this::registerItemModel);
    }

    private void registerItemModels()
    {
        registerVariantItemModels(ModItems.COMPONENT, "variant", ItemComponent.Types.values());

        ModItems.RegistrationHandler.ITEMS.stream()
                .filter(item -> !itemsRegistered.contains(item))
                .forEach(this::registerItemModel);
    }

    @SuppressWarnings("SameParameterValue")
    private <T extends IType> void registerVariantItemModels(Item item, String variantName, T[] values)
    {
        for (T value : values)
        {
            registerItemModelForMeta(item, value.getMeta(), variantName + "=" + value.getName());
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void registerItemModelForMeta(Item item, int metadata, String variant)
    {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    private void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation)
    {
        itemsRegistered.add(item);
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }

    private void registerItemModel(Item item)
    {
        ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
        registerItemModel(item, registryName.toString());
    }

    private void registerItemModel(Item item, String modelLocation)
    {
        ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
        registerItemModel(item, fullModelLocation);
    }

    private void registerItemModel(Item item, ModelResourceLocation fullModelLocation)
    {
        ModelBakery.registerItemVariants(item, fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
        registerItemModel(item, stack -> fullModelLocation);
    }

    private void registerItemModel(Item item, ItemMeshDefinition meshDefinition)
    {
        itemsRegistered.add(item);
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }
    
}
