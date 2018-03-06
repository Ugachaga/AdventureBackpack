package com.darkona.adventurebackpack.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 09/01/2015
 *
 * @author Darkona
 */
public class EntityAIHorseFollowOwner extends EntityAIBase
{
    private EntityHorse theHorse;
    private EntityPlayer theOwner;
    private World theWorld;
    private double speed;
    private PathNavigate petPathfinder;
    private int tickCounter;
    private float maxDist;
    private float minDist;
    private static final String __OBFID = "CL_00001585";

    public EntityAIHorseFollowOwner(EntityHorse horse, double speed, float minDist, float maxDist)
    {
        theHorse = horse;
        theWorld = horse.world;
        if (theHorse.getOwnerUniqueId() != null)
            theOwner = theWorld.getPlayerEntityByUUID(theHorse.getOwnerUniqueId());
        this.speed = speed * 2;
        petPathfinder = horse.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
    }

    public double getDistanceSquaredToOwner()
    {
        double relX = theHorse.posX - theOwner.posX;
        double relY = theHorse.posY - theOwner.posY;
        double relZ = theHorse.posZ - theOwner.posZ;
        return relX * relX + relY * relY + relZ * relZ;
    }

    @Override
    public boolean shouldExecute()
    {
        if (!theHorse.isTame() || theHorse.getLeashed() || !theHorse.hasCustomName())
            return false;

        if (theOwner == null)
        {
            if (theHorse.getOwnerUniqueId() == null) return false;
            theOwner = theWorld.getPlayerEntityByUUID(theHorse.getOwnerUniqueId());
            if (theOwner == null) return false;
        }

        return Wearing.isWearingTheRightBackpack(theOwner, BackpackTypes.HORSE)
                && !(theHorse.getDistanceSq(theOwner) < minDist * minDist * 20);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return (Wearing.isWearingTheRightBackpack(theOwner, BackpackTypes.HORSE)
                && !this.petPathfinder.noPath() && theHorse.getDistanceSq(theOwner) > this.maxDist * this.maxDist * 2);
    }

    @Override
    public void startExecuting()
    {
        tickCounter = 0;
        petPathfinder = theHorse.getNavigator();
    }

    @Override
    public void resetTask()
    {
        theOwner = null;
        petPathfinder.clearPath();
    }

    @Override
    public void updateTask()
    {
        //theHorse.getLookHelper().setLookPositionWithEntity(theOwner, 10.0F, this.theHorse.getVerticalFaceSpeed());
        if (--tickCounter <= 0)
        {
            tickCounter = 10;
            if (!theHorse.getLeashed())
            {
                if (!petPathfinder.tryMoveToEntityLiving(theOwner, speed))
                {
                    return;
                }
            }
        }
    }
}
