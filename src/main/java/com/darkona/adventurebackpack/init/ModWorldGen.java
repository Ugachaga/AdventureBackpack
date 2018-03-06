package com.darkona.adventurebackpack.init;

/**
 * Created on 24/12/2014
 *
 * @author Darkona
 */
public class ModWorldGen
{
    public static void init()
    {
        //TODO ChestGenHooks => LootTables
        //TODO registerVillageTradeHandler => http://www.minecraftforge.net/forum/topic/40455-1102custom-villager-trades-information/
//        {
//            ItemStack backpack = BackpackUtils.createBackpackStack(VILLAGER);
//
//            VillagerRegistry.instance().registerVillageTradeHandler(1, new ModWorldGen.TradeHandler(backpack));
//
//
//            VillagerRegistry.instance().registerVillageTradeHandler(1, new ModWorldGen.TradeHandler(backpack));
//            VillagerRegistry.instance().registerVillageTradeHandler(2, new ModWorldGen.TradeHandler(backpack));
//            VillagerRegistry.instance().registerVillageTradeHandler(3, new ModWorldGen.TradeHandler(backpack));
//        }
//        if (ConfigHandler.allowGolemGen)
//        {
//            ItemStack backpack = BackpackUtils.createBackpackStack(IRON_GOLEM);
//            ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(backpack, 1, 1, 2));
//        }
//        if (ConfigHandler.allowBatGen)
//        {
//            ItemStack backpack = BackpackUtils.createBackpackStack(BAT);
//            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(backpack, 1, 1, 2));
//            ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(backpack, 1, 1, 12));
//        }
//        if (ConfigHandler.allowPigmanGen)
//        {
//            ItemStack backpack = BackpackUtils.createBackpackStack(PIGMAN);
//            ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(backpack, 1, 1, 12));
//            VillagerRegistry.instance().registerVillageTradeHandler(BackpackTypes.getMeta(PIGMAN), new ModWorldGen.TradeHandler(backpack));
//        }
//        if (ConfigHandler.allowBonusGen)
//        {
//            ItemStack backpack = BackpackUtils.createBackpackStack(STANDARD);
//            ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(backpack, 0, 1, 5));
//        }
    }

//    public static class TradeHandler implements VillagerRegistry.IVillageTradeHandler
//    {
//        ItemStack backpack;
//
//        TradeHandler(ItemStack backpack)
//        {
//            this.backpack = backpack;
//        }
//
//        @Override
//        @SuppressWarnings("unchecked")
//        public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
//        {
//            //0 Farmer, 1 Librarian, 2Priest, 3 Blacksmith, 4 Butcher
//            if (villager.getProfession() == 1 || villager.getProfession() == 2)
//            {
//                ItemStack payment = BackpackUtils.createBackpackStack(STANDARD);
//                recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 10), payment, this.backpack));
//            }
//            if (villager.getProfession() == 3)
//            {
//                ItemStack payment = BackpackUtils.createBackpackStack(IRON_GOLEM);
//                recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 10), payment, this.backpack));
//            }
//        }
//    }
}
