package com.darkona.adventurebackpack.capablities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public class BedrollHandler implements IBedrollHandler, INBTSerializable<NBTTagCompound>
{
    private boolean deployed;
    private BlockPos pos;

    @Override
    public boolean isDeployed()
    {
        return deployed;
    }

    @Override
    public boolean deploy(EntityPlayer player, World world, BlockPos pos)
    {
        // deploying...

        return deployed;
    }

    @Override
    public void remove(World world)
    {
        // removing...

        deployed = false;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {

    }
}
