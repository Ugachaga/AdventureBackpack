package com.darkona.adventurebackpack.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class Visuals
{

    public static void NyanParticles(EntityPlayer player, World world)
    {
        // World world = player.worldObj;
        int i = 2;
        for (int j = 0; j < i * 3; ++j)
        {
            float f = world.rand.nextFloat() * (float) Math.PI * 2.0F;
            float f1 = world.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * i * 0.5F * f1;
            world.spawnParticle(EnumParticleTypes.NOTE,
                    player.posX + f2,
                    player.getEntityBoundingBox().minY + 0.8f,
                    player.posZ + f3,
                    (double) (float) Math.pow(2.0D, (world.rand.nextInt(169) - 12) / 12.0D) / 24.0D,
                    -1.0D,
                    0.0D);
        }
    }

    public static void SlimeParticles(EntityPlayer player, World world)
    {
        int i = 3;
        for (int j = 0; j < i * 2; ++j)
        {
            float f = world.rand.nextFloat() * (float) Math.PI * 2.0F;
            float f1 = world.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * i * 0.5F * f1;
            world.spawnParticle(EnumParticleTypes.SLIME, player.posX + f2, player.getEntityBoundingBox().minY, player.posZ + f3, 0.0D, 0.0625D, 0.0D);
        }
    }

    public static void CopterParticles(EntityPlayer player, World world)
    {
        Vec3d playerPosition = new Vec3d(player.posX, player.posY, player.posZ);
        Vec3d victor = new Vec3d(-0.25D, -0.19D, -0.40D);
        victor.rotateYaw(-player.renderYawOffset * 3.141593F / 180.0F);
        Vec3d finalPosition = playerPosition.addVector(victor.xCoord, victor.yCoord, victor.zCoord);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, finalPosition.xCoord, finalPosition.yCoord, finalPosition.zCoord, 0, -0.4, 0);
    }

    public static void JetpackParticles(EntityPlayer player, World world)
    {
        Vec3d playerPosition = new Vec3d(player.posX, player.posY, player.posZ);
        Vec3d victor = new Vec3d(-0.5D, -0.5D, -0.5D);
        Vec3d victoria = new Vec3d(0.5D, -0.5D, -0.5D);
        victor.rotateYaw(-player.renderYawOffset * 3.141593F / 180.0F);
        victoria.rotateYaw(-player.renderYawOffset * 3.141593F / 180.0F);
        Vec3d leftPosition = victor.addVector(playerPosition.xCoord, playerPosition.yCoord, playerPosition.zCoord);
        Vec3d rightPosition = victoria.addVector(playerPosition.xCoord, playerPosition.yCoord, playerPosition.zCoord);
        for (int i = 0; i < 4; i++)
        {
            //TODO: check this particle type
            world.spawnParticle(EnumParticleTypes.CLOUD, leftPosition.xCoord, leftPosition.yCoord, leftPosition.zCoord, 0.04 * world.rand.nextGaussian(), -0.8, 0.04 * world.rand.nextGaussian());
            world.spawnParticle(EnumParticleTypes.CLOUD, rightPosition.xCoord, rightPosition.yCoord, rightPosition.zCoord, 0.04 * world.rand.nextGaussian(), -0.8, 0.04 * world.rand.nextGaussian());
        }
    }

}
