package com.darkona.adventurebackpack.item;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.darkona.adventurebackpack.reference.ModInfo;

public class AdventureItem extends Item
{
    AdventureItem(String name)
    {
        super();
        setItemName(this, name);

        this.setCreativeTab(ModInfo.CREATIVE_TAB);
    }

    public static void setItemName(Item item, String name)
    {
        item.setRegistryName(ModInfo.MODID, name);
        ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
        item.setUnlocalizedName(registryName.toString());
    }
}
