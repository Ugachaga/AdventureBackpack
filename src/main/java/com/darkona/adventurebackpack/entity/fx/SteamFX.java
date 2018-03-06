package com.darkona.adventurebackpack.entity.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created on 19/01/2015
 *
 * @author Darkona
 */

@SideOnly(Side.CLIENT)
public class SteamFX extends Particle
{
    private float smokeParticleScale;

    public SteamFX(World world, double x, double y, double z, double velX, double velY, double velZ)
    {
        this(world, x, y, z, velX, velY, velZ, 1.0F);
    }

    public SteamFX(World world, double x, double y, double z, double velX, double velY, double velZ, float scale)
    {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.1D;
        this.motionY *= 0.1D;
        this.motionZ *= 0.1D;
        this.motionX += velX;
        this.motionY += velY;
        this.motionZ += velZ;
        this.particleRed = 206;
        this.particleGreen = 206;
        this.particleBlue = 206;
        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.smokeParticleScale = this.particleScale;
        this.particleMaxAge = ((int) (8.0D / (Math.random() * 0.8D + 0.2D)));
        this.particleMaxAge = ((int) (this.particleMaxAge * scale));
        //this.noClip = true; //no longer extends from Entity
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float age = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
        if (age < 0.0F)
        {
            age = 0.0F;
        }
        if (age > 1.0F)
        {
            age = 1.0F;
        }
        this.particleScale = (this.smokeParticleScale * age);
        super.renderParticle(buffer, entity, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }
        setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.motionY += 0.003;
        this.move(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }
        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;
        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}
