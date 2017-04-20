package com.darkona.adventurebackpack.capablities;

import com.darkona.adventurebackpack.capablities.player.PlayerWearingBackpackCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class BackpacksCapabilities {

    //TODO: Refactor to API eventually
    @CapabilityInject(PlayerWearingBackpackCapabilities.class)
    public static final Capability<PlayerWearingBackpackCapabilities> WEARING_BACKPACK_CAPABILITY = null;

    //Get capabilities
    public static PlayerWearingBackpackCapabilities getEquippedBackpack(EntityPlayer player) {
        return player.getCapability(WEARING_BACKPACK_CAPABILITY, null);
    }

    //public static BackpacksCapabilities getDeathBackpackCapability(EntityPlayer player) {
    //    return player.getCapability(DEATH_BACKPACK_CAPABILITY, null);
    //}

    //Registration
    public static void registerAllCapabilities(){
        PlayerWearingBackpackCapabilities.register();
        //PlayerDeathBackpackCapabilities.register();
    }

    //Useful methods for other classes
    public static ItemStack getWornBackpack(EntityPlayer player) {
        PlayerWearingBackpackCapabilities cap = getEquippedBackpack(player);
        if (cap != null)
            return cap.getEquippedBackpack();
        return null;
    }

}