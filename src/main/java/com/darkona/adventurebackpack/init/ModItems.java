package com.darkona.adventurebackpack.init;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.darkona.adventurebackpack.item.AdventureArmor;
import com.darkona.adventurebackpack.item.ItemAdventureHat;
import com.darkona.adventurebackpack.item.ItemAdventurePants;
import com.darkona.adventurebackpack.item.ItemAdventureSuit;
import com.darkona.adventurebackpack.item.ItemBackpack;
import com.darkona.adventurebackpack.item.ItemComponent;
import com.darkona.adventurebackpack.item.ItemCopter;
import com.darkona.adventurebackpack.item.ItemHose;
import com.darkona.adventurebackpack.item.ItemJetpack;
import com.darkona.adventurebackpack.item.ItemJuiceBottle;
import com.darkona.adventurebackpack.item.ItemMachete;
import com.darkona.adventurebackpack.item.ItemPistonBoots;
import com.darkona.adventurebackpack.reference.ModInfo;

import static com.darkona.adventurebackpack.util.Utils.getNull;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@GameRegistry.ObjectHolder(ModInfo.MODID)
public class ModItems
{
    public static class Materials
    {
        public static final Item.ToolMaterial TOOL_RUGGED_IRON = EnumHelper.addToolMaterial(
                "rugged_iron", 2, 350, 6.5F, 5.2F, 10);

        public static final ItemArmor.ArmorMaterial ARMOR_RUGGED_LEATHER = EnumHelper.addArmorMaterial(
                "rugged_leather", ModInfo.MODID + ":adventureSuit", 15,
                new int[]{2, 5, 4, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0f);
    }

    public static final ItemMachete MACHETE = getNull();
    public static final AdventureArmor ADVENTURE_HAT = getNull();
    public static final AdventureArmor ADVENTURE_SUIT = getNull();
    public static final AdventureArmor ADVENTURE_PANTS = getNull();
    public static final AdventureArmor PISTON_BOOTS = getNull();
    public static final ItemBackpack ADVENTURE_BACKPACK = getNull();
    public static final ItemCopter COPTER_PACK = getNull();
    public static final ItemJetpack STEAM_JETPACK = getNull();
    public static final ItemHose BACKPACK_HOSE = getNull();
    public static final ItemComponent COMPONENT = getNull();
    public static final ItemJuiceBottle MELON_JUICE_BOTTLE = getNull();

    @Mod.EventBusSubscriber(modid = ModInfo.MODID)
    public static class RegistrationHandler
    {
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            final Item[] items = {
                    new ItemMachete("machete"),
                    new ItemAdventureHat("adventure_hat"),
                    new ItemAdventureSuit("adventure_suit"),
                    new ItemAdventurePants("adventure_pants"),
                    new ItemPistonBoots("piston_boots"),
                    new ItemBackpack("adventure_backpack"),
                    new ItemCopter("copter_pack"),
                    new ItemJetpack("steam_jetpack"),
                    new ItemHose("backpack_hose"),
                    new ItemComponent("component"),
                    new ItemJuiceBottle("melon_juice_bottle"),
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items)
            {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }

}
