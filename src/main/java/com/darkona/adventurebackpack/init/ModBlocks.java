package com.darkona.adventurebackpack.init;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.darkona.adventurebackpack.block.BlockBackpack;
import com.darkona.adventurebackpack.block.BlockCampfire0;
import com.darkona.adventurebackpack.block.BlockSleepingBag;
import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.block.TileCampfire0;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.getNull;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@GameRegistry.ObjectHolder(ModInfo.MODID)
public class ModBlocks
{
    @Nonnull public static final BlockBackpack BLOCK_BACKPACK = getNull();
    @Nonnull public static final BlockCampfire0 BLOCK_CAMPFIRE = getNull();
    @Nonnull public static final BlockSleepingBag BLOCK_SLEEPING_BAG = getNull();

    @Mod.EventBusSubscriber(modid = ModInfo.MODID)
    public static class RegistrationHandler
    {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event)
        {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                    new BlockBackpack(),
                    new BlockCampfire0(),
                    new BlockSleepingBag(),
            };

            registry.registerAll(blocks);
            registerTileEntities();
        }

        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
        {
            final ItemBlock[] items = {
                    new ItemBlock(BLOCK_BACKPACK),
                    new ItemBlock(BLOCK_CAMPFIRE),
                    new ItemBlock(BLOCK_SLEEPING_BAG),
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final ItemBlock item : items)
            {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }
        }
    }

    private static void registerTileEntities()
    {
        registerTileEntity(TileBackpack.class, "tile_backpack");
        registerTileEntity(TileCampfire0.class, "tile_campfire");
    }

    private static void registerTileEntity(final Class<? extends TileEntity> tileEntityClass, final String name)
    {
        GameRegistry.registerTileEntity(tileEntityClass, ModInfo.MODID + ":" + name);
    }
}
