package com.darkona.adventurebackpack.reference;

import net.minecraft.item.ItemStack;

import com.darkona.adventurebackpack.util.GregtechUtils;
import com.darkona.adventurebackpack.util.ThaumcraftUtils;
import com.darkona.adventurebackpack.util.TinkersUtils;

public enum ToolHandler
{
    VANILLA,
    GREGTECH,
    TCONSTRUCT,
    THAUMCRAFT,
    ;

    public static ToolHandler getToolHandler(ItemStack stack)
    {
        if (stack == null)
            return VANILLA;

        if (GregtechUtils.isTool(stack))
            return GREGTECH;
        if (TinkersUtils.isTool(stack))
            return TCONSTRUCT;
        if (ThaumcraftUtils.isTool(stack))
            return THAUMCRAFT;
        return VANILLA;
    }
}