package com.darkona.adventurebackpack.capablities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityBedrollHandler
{
    // http://mcforge.readthedocs.io/en/latest/datastorage/capabilities/
    // https://www.planetminecraft.com/blog/forge-tutorial-capability-system/
    // https://gist.github.com/williewillus/c8dc2a1e7963b57ef436c699f25a710d
    // http://jabelarminecraft.blogspot.ru/p/minecraft-17x.html

    @CapabilityInject(IBedrollHandler.class)
    public static Capability<IBedrollHandler> BEDROLL_HANDLER_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IBedrollHandler.class, new Capability.IStorage<IBedrollHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<IBedrollHandler> capability, IBedrollHandler instance, EnumFacing side)
            {
                NBTTagList nbtTagList = new NBTTagList();

                //TODO implement

                return nbtTagList;
            }

            @Override
            public void readNBT(Capability<IBedrollHandler> capability, IBedrollHandler instance, EnumFacing side, NBTBase base)
            {
                //TODO implement
            }
        }, BedrollHandler::new);
    }

}
