package com.darkona.adventurebackpack.init;


import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.darkona.adventurebackpack.block.BlockBackpack;
import com.darkona.adventurebackpack.block.BlockCampFire;
import com.darkona.adventurebackpack.block.BlockSleepingBag;
import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.block.TileCampfire;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.Null;

/**
 * Created by Darkona on 12/10/2014.
 */
@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
public class ModBlocks
{
    public static final BlockBackpack BLOCK_BACKPACK = Null();
    public static final BlockSleepingBag BLOCK_SLEEPING_BAG = Null();
    public static final BlockCampFire BLOCK_CAMP_FIRE = Null();

    //TODO see https://github.com/Choonster-Minecraft-Mods/TestMod3/tree/1.12.2/src/main/java/choonster/testmod3/init
    @Mod.EventBusSubscriber(modid = ModInfo.MOD_ID)
    public static class RegistrationHandler
    {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event)
        {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                    new BlockBackpack(),
                    new BlockSleepingBag(),
                    new BlockCampFire(),
            };

            registry.registerAll(blocks);
            registerTileEntities();
        }

//        @SubscribeEvent
//        public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
//        {
//            final ItemBlock[] items = {
//                    new ItemBlock(BLOCK_BACKPACK),
//                    new ItemBlock(BLOCK_SLEEPING_BAG),
//                    new ItemBlock(BLOCK_CAMP_FIRE),
//            };
//
//            final IForgeRegistry<Item> registry = event.getRegistry();
//
//            for (final ItemBlock item : items)
//            {
//                final Block block = item.getBlock();
//                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
//                registry.register(item.setRegistryName(registryName));
//                ITEM_BLOCKS.add(item);
//            }
//        }
    }

    public static void init()
    {
        //TODO register events
    }


    private static void registerTileEntities() {
        registerTileEntity(TileCampfire.class, "tileCampFire");
        registerTileEntity(TileBackpack.class, "tileBackpack");
    }

    private static void registerTileEntity(final Class<? extends TileEntity> tileEntityClass, final String name)
    {
        GameRegistry.registerTileEntity(tileEntityClass, ModInfo.MOD_ID + ":" + name);
    }
}
