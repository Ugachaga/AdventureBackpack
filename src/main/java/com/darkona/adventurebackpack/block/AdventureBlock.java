package com.darkona.adventurebackpack.block;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.reference.ModInfo;

public class AdventureBlock
{
    public static void setBlockName(Block block, String name)
    {
        block.setRegistryName(ModInfo.MODID, name);
        ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
        block.setUnlocalizedName(registryName.toString());
    }
}
