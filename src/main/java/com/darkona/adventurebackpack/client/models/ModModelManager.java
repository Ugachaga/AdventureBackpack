package com.darkona.adventurebackpack.client.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.item.ItemComponent;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.temp.IVariant;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModInfo.MODID)
public class ModModelManager
{
    public static final ModModelManager INSTANCE = new ModModelManager();

    private static final String FLUID_MODEL_PATH = ModInfo.MODID + ":fluid";

    private ModModelManager() {}

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        INSTANCE.registerBlockModels();
        INSTANCE.registerItemModels();
    }


    private void registerBlockModels()
    {
        OBJLoader.INSTANCE.addDomain(ModInfo.MODID);
        B3DLoader.INSTANCE.addDomain(ModInfo.MODID);

//        B3DLoader.INSTANCE.loadModel()
//        OBJLoader.INSTANCE.loadModel()

        ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream()
                .filter(item -> !itemsRegistered.contains(item))
                .forEach(this::registerItemModel);
    }



    private final Set<Item> itemsRegistered = new HashSet<>();

    private void registerItemModels()
    {
        registerVariantItemModels(ModItems.COMPONENT, "variant", ItemComponent.Types.values());

        ModItems.RegistrationHandler.ITEMS.stream()
                .filter(item -> !itemsRegistered.contains(item))
                .forEach(this::registerItemModel);
    }

    private <T extends IVariant> void registerVariantItemModels(Item item, String variantName, T[] values)
    {
        for (T value : values)
        {
            registerItemModelForMeta(item, value.getMeta(), variantName + "=" + value.getName());
        }
    }

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
