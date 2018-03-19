package com.darkona.adventurebackpack.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;

public class AdventureTool extends ItemTool
{
    AdventureTool(String name, ToolMaterial material, Set<Block> effectiveBlocks)
    {
        super(material, effectiveBlocks);
        AdventureItem.setItemName(this, name);

        //setCreativeTab(CreativeTabAB.CREATIVE_TAB);
    }
}
