package com.darkona.adventurebackpack.capablities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import static com.darkona.adventurebackpack.util.Utils.getNull;

public class AdventureCapabilities
{

    /*
    So sample plan. We'll use default ItemHandler and FluidHandler for the start. Also we need:
    - BedrollHandler to handle bedroll (sleeping bag too long and two words, so bedroll). TileBackpack, ItemBackpack;
    - CraftHandler (?) to handle craftMatrix and craftResult, to sync with ItemHandler and so on. TileBackpack, ItemBackpack;
    - WearableHandler to handle wearing ItemStack. All wearable packs (items).
    */

    @CapabilityInject(IBedrollHandler.class)
    public static Capability<IBedrollHandler> BEDROLL_HANDLER_CAPABILITY = getNull();

}
