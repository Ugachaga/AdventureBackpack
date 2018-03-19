package com.darkona.adventurebackpack.config;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Keybindings
{
    private static final String KEYS_CATEGORY = "keys.adventurebackpack:category";
    private static final String OPEN_INVENTORY = "keys.adventurebackpack:open_inventory";
    private static final String TOGGLE_ACTIONS = "keys.adventurebackpack:toggle_actions";

    public static KeyBinding openInventory = new KeyBinding(OPEN_INVENTORY, Keyboard.KEY_B, KEYS_CATEGORY);
    public static KeyBinding toggleActions = new KeyBinding(TOGGLE_ACTIONS, Keyboard.KEY_N, KEYS_CATEGORY);

    public static String getInventoryKeyName()
    {
        return GameSettings.getKeyDisplayString(openInventory.getKeyCode());
    }

    public static String getActionKeyName()
    {
        return GameSettings.getKeyDisplayString(toggleActions.getKeyCode());
    }
}