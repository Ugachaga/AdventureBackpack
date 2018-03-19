package com.darkona.adventurebackpack.util;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.darkona.adventurebackpack.common.Constants;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.reference.GeneralReference;

public final class TipUtils
{
    private TipUtils() {}

    public static String l10n(String tip)
    {
        return I18n.translateToLocal("adventurebackpack:tooltips." + tip); //TODO deprecated, see http://www.minecraftforge.net/forum/topic/60206-whats-the-1102-equivalent-of-statcollectortranslatetolocal/
    }

    public static void shiftFooter(List<String> eventTips)
    {
        if (GuiScreen.isCtrlKeyDown())
            eventTips.add("");
        else
            eventTips.add(holdCtrl());
    }

    public static String holdShift()
    {
        return holdThe(true);
    }

    public static String holdCtrl()
    {
        return holdThe(false);
    }

    private static String holdThe(boolean button)
    {
        return whiteFormat(TextFormatting.ITALIC + "<" + (button ? l10n("hold.shift")
                                                                     : l10n("hold.ctrl")) + ">");
    }

    public static String whiteFormat(String stringIn)
    {
        return TextFormatting.WHITE + stringIn + TextFormatting.GRAY;
    }

    public static String actionKeyFormat()
    {
        return whiteFormat(Keybindings.getActionKeyName());
    }

    public static String pressKeyFormat(String button)
    {
        return l10n("press") + " '" + button + "' ";
    }

    public static String pressShiftKeyFormat(String button)
    {
        return l10n("press") + " Shift+'" + button + "' ";
    }

    public static String inventoryTooltip(NBTTagList itemList)
    {
        int itemCount = itemList.tagCount();
        boolean toolSlotU = false;
        boolean toolSlotL = false;
        for (int i = itemCount - 1; i >= 0; i--)
        {
            int slotAtI = itemList.getCompoundTagAt(i).getInteger(Constants.TAG_SLOT);
            if (slotAtI < Constants.TOOL_UPPER)
                break;
            else if (slotAtI == Constants.TOOL_UPPER)
                toolSlotU = true;
            else if (slotAtI == Constants.TOOL_LOWER)
                toolSlotL = true;
            else
                itemCount--; // this need for correct count while GUI is open and bucket slots may be occupied
        }
        itemCount -= (toolSlotU ? 1 : 0) + (toolSlotL ? 1 : 0);
        return toolSlotFormat(toolSlotU) + toolSlotFormat(toolSlotL) + " " + mainSlotsFormat(itemCount);
    }

    private static String toolSlotFormat(boolean isTool)
    {
        return (isTool ? TextFormatting.WHITE : TextFormatting.DARK_GRAY) + "[]";
    }

    private static String mainSlotsFormat(int slotsUsed)
    {
        String slotsFormatted = Integer.toString(slotsUsed);
        if (slotsUsed == 0)
            slotsFormatted = TextFormatting.DARK_GRAY + slotsFormatted;
        else if (slotsUsed == Constants.INVENTORY_MAIN_SIZE)
            slotsFormatted = TextFormatting.WHITE + slotsFormatted;
        else
            slotsFormatted = TextFormatting.GRAY + slotsFormatted;
        return slotsFormatted + "/" + Constants.INVENTORY_MAIN_SIZE;
    }

    public static String tankTooltip(FluidTank tank)
    {
        return tankTooltip(tank, true);
    }

    public static String tankTooltip(FluidTank tank, boolean attachName)
    {
        String fluidAmount = fluidAmountFormat(tank.getFluidAmount(), tank.getCapacity());
        String fluidName = tank.getFluid() == null ? "" : attachName ? fluidNameFormat(tank.getFluid()) : " ";
        return fluidAmount + (tank.getFluidAmount() > 0 ? "/" + tank.getCapacity() : "") + fluidName;
    }

    private static String fluidAmountFormat(int fluidAmount, int tankCapacity)
    {
        String amountFormatted = Integer.toString(fluidAmount);
        if (fluidAmount == tankCapacity)
            amountFormatted = TextFormatting.WHITE + amountFormatted;
        else if (fluidAmount == 0)
            amountFormatted = emptyFormat();
        return amountFormatted;
    }

    private static String fluidNameFormat(FluidStack fluid)
    {
        String nameUnlocalized = fluid.getUnlocalizedName().toLowerCase();
        String nameFormatted = " ";
        if (nameUnlocalized.contains("lava") || nameUnlocalized.contains("fire"))
            nameFormatted += TextFormatting.RED;
        else if (nameUnlocalized.contains("water"))
            nameFormatted += TextFormatting.BLUE;
        else if (nameUnlocalized.contains("oil"))
            nameFormatted += TextFormatting.DARK_GRAY;
        else if (nameUnlocalized.contains("fuel") || nameUnlocalized.contains("creosote"))
            nameFormatted += TextFormatting.YELLOW;
        else if (nameUnlocalized.contains("milk"))
            nameFormatted += TextFormatting.WHITE;
        else if (nameUnlocalized.contains("xpjuice"))
            nameFormatted += TextFormatting.GREEN;
        else
            nameFormatted += TextFormatting.GRAY;
        return nameFormatted + fluid.getLocalizedName();
    }

    public static String switchTooltip(boolean status, boolean doFormat)
    {
        return doFormat ? switchFormat(status) : status ? l10n("on") : l10n("off");
    }

    private static String switchFormat(boolean status)
    {
        String switchFormatted = status ? TextFormatting.WHITE + l10n("on")
                                        : TextFormatting.DARK_GRAY + l10n("off");
        return "[" + switchFormatted + TextFormatting.GRAY + "]";
    }

    public static String slotStackTooltip(NBTTagList itemList, int slot)
    {
        int slotID, slotMeta, slotCount = slotID = slotMeta = 0;
        for (int i = 0; i <= slot; i++)
        {
            int slotAtI = itemList.getCompoundTagAt(i).getInteger(Constants.TAG_SLOT);
            if (slotAtI == slot)
            {
                slotID = itemList.getCompoundTagAt(i).getInteger("id");
                slotMeta = itemList.getCompoundTagAt(i).getInteger("Damage");
                slotCount = itemList.getCompoundTagAt(i).getInteger("Count");
                break;
            }
        }
        return stackDataFormat(slotID, slotMeta, slotCount);
    }

    private static String stackDataFormat(int id, int meta, int count)
    {
        if (count == 0)
            return emptyFormat();

        String dataFormatted;
        try
        {
            @SuppressWarnings("ConstantConditions")
            ItemStack iStack = new ItemStack(Item.REGISTRY.getObjectById(id), 0, meta);
            dataFormatted = iStack.getDisplayName() + " (" + stackSizeFormat(iStack, count) + ")";
        }
        catch (Exception e)
        {
            dataFormatted = TextFormatting.RED + l10n("error");
        }
        return dataFormatted;
    }

    private static String stackSizeFormat(ItemStack stack, int count)
    {
        return stack.getMaxStackSize() == count ? whiteFormat(Integer.toString(count)) : Integer.toString(count);
    }

    public static String fuelConsumptionTooltip(FluidTank tank)
    {
        return (tank.getFluid() != null)
               ? String.format("x%.2f", GeneralReference.getFuelRate(tank.getFluid().getFluid().getName()))
               : TextFormatting.DARK_GRAY + "-" ;
    }

    private static String emptyFormat()
    {
        return TextFormatting.DARK_GRAY.toString() + TextFormatting.ITALIC + l10n("empty");
    }

}