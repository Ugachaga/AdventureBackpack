package com.darkona.adventurebackpack.util;

public final class ToolRenderHelper
{
    private static final String METHOD_RENDERER = "renderItem";
    private static final Object[] EMPTY_OBJECT = {};

    private ToolRenderHelper() {}

//    public static void render(ItemStack stack, ItemRenderType type, Class<?> renderer, Object rendererInstance)
//    {
//        if (rendererInstance == null)
//            return;
//
//        try
//        {
//            renderer
//                    .getMethod(METHOD_RENDERER, ItemRenderType.class, ItemStack.class, Object[].class)
//                    .invoke(rendererInstance, type, stack, EMPTY_OBJECT);
//        }
//        catch (Exception e)
//        { /*  */ }
//    }
}
