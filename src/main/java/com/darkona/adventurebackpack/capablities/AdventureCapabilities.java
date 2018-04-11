package com.darkona.adventurebackpack.capablities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import static com.darkona.adventurebackpack.util.Utils.getNull;

public class AdventureCapabilities
{

    @CapabilityInject(IBedrollHandler.class)
    public static Capability<IBedrollHandler> BEDROLL_HANDLER_CAPABILITY = getNull();

}
