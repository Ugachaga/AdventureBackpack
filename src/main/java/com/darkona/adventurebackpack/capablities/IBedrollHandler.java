package com.darkona.adventurebackpack.capablities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBedrollHandler
{
    boolean isDeployed();

    boolean deploy(EntityPlayer player, World world, BlockPos pos);

    void remove(World world);
}
