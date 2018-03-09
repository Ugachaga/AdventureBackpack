package com.darkona.adventurebackpack.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 10/10/2014.
 *
 * @author Javier Darkona
 */
public class ConfigHandler
{
    public static Configuration config;

    public static boolean allowSoulBound = true;
    public static boolean backpackDeathPlace = true;
    public static boolean backpackAbilities = true;
    public static boolean enableCampfireSpawn = false;
    public static boolean enableHoseDrink = true;
    public static boolean fixLead = true;
    public static boolean portableSleepingBag = true;
    public static boolean tinkerToolsMaintenance = true;

    public static boolean enableFullnessBar = false;
    public static boolean enableTemperatureBar = false;
    public static boolean enableToolsRender = true;
    public static int typeTankRender = 2;
    public static boolean tanksHoveringText = false;

    public static boolean tanksOverlay = true;
    public static boolean tanksOverlayRight = true;
    public static boolean tanksOverlayBottom = true;
    public static int tanksOverlayIndentH = 2;
    public static int tanksOverlayIndentV = 1;

    public static boolean allowSoundCopter = true;
    public static boolean allowSoundJetpack = true;
    public static boolean allowSoundPiston = true;

    public static String[] copterFuels;
    private static String[] defaultFuels = {"biodiesel, 1.0", "biofuel, 1.0", "bioethanol, 1.5", "creosote, 7.0",
            "fuel, 0.8", "lava, 5.0", "liquid_light_oil, 3.0", "liquid_medium_oil, 3.0", "liquid_heavy_oil, 3.0",
            "liquid_light_fuel, 1.0", "liquid_heavy_fuel, 1.3", "nitrofuel, 0.4", "oil, 3.0", "rocket_fuel, 0.8"};
    public static String[] forbiddenDimensions;
    public static String[] itemBlacklist;

    public static boolean consumeDragonEgg = false;
    public static boolean recipeSaddle = true;

    public static boolean pistonBootsAutoStep = true;
    public static int pistonBootsJumpHeight = 3;
    public static int pistonBootsSprintBoost = 1;
    public static int dragonBackpackRegen = 1;
    public static int dragonBackpackDamage = 2;
    public static int rainbowBackpackSpeed = 1;
    public static int rainbowBackpackSSpeed = 3;
    public static int rainbowBackpackSJump = 1;

    public static boolean allowBatGen = true;
    public static boolean allowBonusGen = false;
    public static boolean allowGolemGen = true;
    public static boolean allowPigmanGen = false;

    public static void init(File configFile)
    {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        // Gameplay
        allowSoulBound = config.getBoolean("Allow SoulBound", "gameplay", true, "Allow SoulBound enchant on wearable packs");
        backpackAbilities = config.getBoolean("Backpack Abilities", "gameplay", true, "Allow the backpacks to execute their special abilities, or be only cosmetic (Doesn't affect lightning transformation) Must be " +
                "disabled in both Client and Server to work properly");
        backpackDeathPlace = config.getBoolean("Backpack Death Place", "gameplay", true, "Place backpacks as a block when you die?");
        fixLead = config.getBoolean("Fix Vanilla Lead", "gameplay", true, "Fix the vanilla Lead? (Checks mobs falling on a leash to not die of fall damage if they're not falling so fast)");
        enableCampfireSpawn = config.getBoolean("Enable Campfire Spawn", "gameplay", false, "Enable/Disable ability to spawn at campfire");
        enableHoseDrink = config.getBoolean("Enable Hose Drink", "gameplay", true, "Enable/Disable hose drink mode");
        portableSleepingBag = config.getBoolean("Portable Sleeping Bag", "gameplay", true, "Allows to use sleeping bag directly from wearing backpacks. Sleep by one touch");
        tinkerToolsMaintenance = config.getBoolean("Maintenance Tinker Tools", "gameplay", true, "Allows to maintenance (repair/upgarde) Tinkers Construct tools in backpacks as if it's Crafting Station");

        // Graphics
        typeTankRender = config.getInt("Tank Render Type", "graphics", 3, 1, 3, "1,2 or 3 for different rendering of fluids in the Backpack GUI");
        enableFullnessBar = config.getBoolean("Enable Fullness Bar", "graphics", false, "Enable durability bar showing fullness of backpacks inventory");
        enableTemperatureBar = config.getBoolean("Enable Temperature Bar", "graphics", false, "Enable durability bar showing temperature of jetpack");
        enableToolsRender = config.getBoolean("Enable Tools Render", "graphics", true, "Enable rendering for tools in the backpack tool slots");
        tanksHoveringText = config.getBoolean("Hovering Text", "graphics", false, "Show hovering text on fluid tanks?");

        // Graphics.Tanks
        tanksOverlay = config.getBoolean("Enable Overlay", "graphics.tanks", true, "Show the different wearable overlays on screen?");
        tanksOverlayRight = config.getBoolean("Stick To Right", "graphics.tanks", true, "Stick to right?");
        tanksOverlayBottom = config.getBoolean("Stick To Bottom", "graphics.tanks", true, "Stick to bottom?");
        tanksOverlayIndentH = config.getInt("Indent Horizontal", "graphics.tanks", 2, 0, 1000, "Horizontal indent from the window border");
        tanksOverlayIndentV = config.getInt("Indent Vertical", "graphics.tanks", 1, 0, 500, "Vertical indent from the window border");

        // Sound
        allowSoundCopter = config.getBoolean("Copter Pack", "sound", true, "Allow playing the CopterPack sound (Client Only, other players may hear it)");
        allowSoundJetpack = config.getBoolean("Steam Jetpack", "sound", true, "Allow playing the SteamJetpack sound (Client Only, other players may hear it)");
        allowSoundPiston = config.getBoolean("Piston Boots", "sound", true, "Allow playing the PistonBoots sound");

        // Items
        copterFuels = config.getStringList("Valid Copter Fuels", "items", defaultFuels, "List of valid fuels for Copter. Consumption rate range: 0.05 ~ 20.0. Format: 'fluid, rate', ex.: 'water, 0.0'");
        forbiddenDimensions = config.getStringList("Forbidden Dimensions", "items", new String[]{}, "Disallow opening backpack inventory for specific dimension ID");
        itemBlacklist = config.getStringList("Item Blacklist", "items", new String[]{}, "Disallow items by internal ID. Example: minecraft:dirt");

        // Items.Recipes
        consumeDragonEgg = config.getBoolean("Consume Dragon Egg", "items.recipes", false, "Consume Dragon Egg when Dragon backpack crafted?");
        recipeSaddle = config.getBoolean("Saddle", "items.recipes", true, "Add recipe for saddle?");

        // Items.Settings
        pistonBootsAutoStep = config.getBoolean("Piston Boots Auto Step", "items.settings", true, "Allow Piston Boots auto step blocks");
        pistonBootsJumpHeight = config.getInt("Piston Boots Jump Height", "items.settings", 3, 1, 8, "Piston Boots jump height in blocks");
        pistonBootsSprintBoost = config.getInt("Piston Boots Sprint", "items.settings", 1, 0, 4, "Piston Boots sprint boost. 0 - disable");
        dragonBackpackRegen = config.getInt("Dragon Regeneration", "items.settings", 1, 0, 4, "Dragon Backpack regeneration level. 0 - disable");
        dragonBackpackDamage = config.getInt("Dragon Damage Boost", "items.settings", 2, 0, 4, "Dragon Backpack damage boost. 0 - disable");
        rainbowBackpackSpeed = config.getInt("Rainbow Speed", "items.settings", 1, 0, 4, "Rainbow Backpack speed boost. 0 - disable");
        rainbowBackpackSSpeed = config.getInt("Rainbow Special Speed", "items.settings", 3, 0, 4, "Rainbow Backpack special speed. 0 - disable");
        rainbowBackpackSJump = config.getInt("Rainbow Special Jump", "items.settings", 1, 0, 4, "Rainbow Backpack special jump. 0 - disable");

        // WorldGen
        allowBatGen = config.getBoolean("Bat Backpacks", "worldgen", true, "Allow generation of Bat Backpacks in dungeon and mineshaft loot. It cannot be obtained by crafting");
        allowBonusGen = config.getBoolean("Bonus Backpack", "worldgen", false, "Include a Standard Adventure Backpack in bonus chest?");
        allowGolemGen = config.getBoolean("IronGolem Backpacks", "worldgen", true, "Allow generation of IronGolem Backpacks in village blacksmith loot. It cannot be obtained by crafting");
        allowPigmanGen = config.getBoolean("Pigman Backpacks", "worldgen", false, "Allow generation of Pigman Backpacks in dungeon loot and villager trades");

        // Experimental
        //

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(ModInfo.MODID))
        {
            loadConfiguration();
        }
    }

}