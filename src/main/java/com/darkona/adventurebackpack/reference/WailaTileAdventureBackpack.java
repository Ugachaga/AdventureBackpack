package com.darkona.adventurebackpack.reference;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidTank;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.util.BackpackUtils;
import com.darkona.adventurebackpack.util.TipUtils;
import com.darkona.adventurebackpack.util.Utils;

import static com.darkona.adventurebackpack.common.Constants.TAG_INVENTORY;
import static com.darkona.adventurebackpack.common.Constants.TAG_LEFT_TANK;
import static com.darkona.adventurebackpack.common.Constants.TAG_RIGHT_TANK;
import static com.darkona.adventurebackpack.common.Constants.TAG_TYPE;
import static com.darkona.adventurebackpack.common.Constants.TAG_WEARABLE_COMPOUND;

public class WailaTileAdventureBackpack implements IWailaDataProvider
{
//    public static void init()
//    {
//        FMLInterModComms.sendMessage("waila", "register", "com.darkona.adventurebackpack.reference.WailaTileAdventureBackpack.callbackRegister");
//    }

    @SuppressWarnings("unused")
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        registrar.registerStackProvider(new WailaTileAdventureBackpack(), TileBackpack.class);
        registrar.registerHeadProvider(new WailaTileAdventureBackpack(), TileBackpack.class);
        registrar.registerBodyProvider(new WailaTileAdventureBackpack(), TileBackpack.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return addTypeToStack(accessor);
    }

    private ItemStack addTypeToStack(IWailaDataAccessor accessor)
    {
        if (accessor.getNBTData().hasKey(TAG_WEARABLE_COMPOUND))
        {
            NBTTagCompound backpackTag = accessor.getNBTData().getCompoundTag(TAG_WEARABLE_COMPOUND);
            BackpackTypes type = BackpackTypes.getType(backpackTag.getInteger(TAG_TYPE));
            return BackpackUtils.createBackpackStack(type);
        }
        return BackpackUtils.createBackpackStack(BackpackTypes.STANDARD);
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        addHeadToBackpack(itemStack, currenttip);
        return currenttip;
    }

    private static void addHeadToBackpack(ItemStack itemStack, List<String> currenttip)
    {

        //TODO come back here when tile.backpack is fixed
        if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(TAG_WEARABLE_COMPOUND))
        {
            //System.out.println(itemStack.getTagCompound().toString());
            NBTTagCompound backpackTag = itemStack.getTagCompound().getCompoundTag(TAG_WEARABLE_COMPOUND);
            addHeadToBackpack(currenttip, backpackTag);
        }
    }

    private static void addHeadToBackpack(List<String> currenttip, NBTTagCompound backpackTag)
    {
        //currenttip.remove(0);
        BackpackTypes type = BackpackTypes.getType(backpackTag.getInteger(TAG_TYPE));
        String skin = "";
        if (type != BackpackTypes.STANDARD)
            skin = TextFormatting.GRAY + " \"" + Utils.getColoredSkinName(type) + TextFormatting.GRAY + "\"";
        currenttip.add(TextFormatting.WHITE + "Adventure Backpack" + skin);
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        addTipToBackpack(currenttip, accessor);
        return currenttip;
    }

    private static void addTipToBackpack(List<String> currenttip, IWailaDataAccessor accessor)
    {
        if (accessor.getNBTData().hasKey(TAG_WEARABLE_COMPOUND))
        {
            NBTTagCompound backpackTag = accessor.getNBTData().getCompoundTag(TAG_WEARABLE_COMPOUND);
            addTipToBackpack(currenttip, backpackTag);
        }
    }

    private static void addTipToBackpack(List<String> currenttip, NBTTagCompound backpackTag)
    {
        NBTTagList itemList = backpackTag.getTagList(TAG_INVENTORY, NBT.TAG_COMPOUND);
        currenttip.add(TipUtils.l10n("backpack.slots.used") + ": " + TipUtils.inventoryTooltip(itemList));

        FluidTank tank = new FluidTank(Constants.BASIC_TANK_CAPACITY);

        tank.readFromNBT(backpackTag.getCompoundTag(TAG_LEFT_TANK));
        currenttip.add(TextFormatting.RESET + TipUtils.l10n("backpack.tank.left")
                + ": " + TipUtils.tankTooltip(tank));

        tank.readFromNBT(backpackTag.getCompoundTag(TAG_RIGHT_TANK));
        currenttip.add(TextFormatting.RESET + TipUtils.l10n("backpack.tank.right")
                + ": " + TipUtils.tankTooltip(tank));
    }

}
