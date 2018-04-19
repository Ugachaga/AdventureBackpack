package com.darkona.adventurebackpack.init;

import java.util.HashSet;
import java.util.Set;

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
import com.darkona.adventurebackpack.block.BlockCampfire;
import com.darkona.adventurebackpack.block.BlockSleepingBag;
import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.block.TileCampfire;
import com.darkona.adventurebackpack.block.test.BlockTest;
import com.darkona.adventurebackpack.block.test.TileTest;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.getNull;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@GameRegistry.ObjectHolder(ModInfo.MODID)
public class ModBlocks
{
    public static final BlockBackpack BACKPACK_BLOCK = getNull();
    public static final BlockCampfire CAMPFIRE_BLOCK = getNull();
    public static final BlockSleepingBag SLEEPING_BAG_BLOCK = getNull();

    public static final BlockTest TEST_BLOCK = getNull();

    @Mod.EventBusSubscriber(modid = ModInfo.MODID)
    public static class RegistrationHandler
    {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event)
        {
            IForgeRegistry<Block> registry = event.getRegistry();

            Block[] blocks = {
                    new BlockBackpack("backpack_block"),
                    new BlockCampfire("campfire_block"),
                    new BlockSleepingBag("sleeping_bag_block"),

                    new BlockTest("test_block"),
            };

            registry.registerAll(blocks);
            registerTileEntities();
        }

        @SubscribeEvent
        public static void registerItemBlocks(RegistryEvent.Register<Item> event)
        {
            ItemBlock[] items = {
                    //new ItemBlock(BACKPACK_BLOCK), //TODO maybe we should extends ItemBackpack from ItemBlock and register it here instead of ModItems?
                    new ItemBlock(CAMPFIRE_BLOCK),
                    new ItemBlock(SLEEPING_BAG_BLOCK),

                    new ItemBlock(TEST_BLOCK),
            };

            IForgeRegistry<Item> registry = event.getRegistry();

            for (ItemBlock item : items)
            {
                Block block = item.getBlock();
                ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }
        }
    }

    private static void registerTileEntities()
    {
        registerTileEntity(TileBackpack.class, "backpack_tile"); //TODO name = getRegistryName().toString() ?
        registerTileEntity(TileCampfire.class, "campfire_tile");

        registerTileEntity(TileTest.class, "test_tile");
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String name)
    {
        GameRegistry.registerTileEntity(tileEntityClass, ModInfo.MODID + ":" + name);
    }
}
