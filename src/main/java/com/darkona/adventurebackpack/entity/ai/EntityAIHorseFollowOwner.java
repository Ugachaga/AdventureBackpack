package com.darkona.adventurebackpack.entity.ai;

import com.darkona.adventurebackpack.util.Wearing;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

/**
 * Created on 09/01/2015
 *
 * @author Darkona
 */
public class EntityAIHorseFollowOwner extends EntityAIBase
{
        private EntityHorse theHorse;
        private EntityPlayer theOwner;
        World theWorld;
        private double speed;
        private PathNavigate petPathfinder;
        private int tickCounter;
        float maxDist;
        float minDist;
        @SuppressWarnings("unused")
		private static final String __OBFID = "CL_00001585";

        public EntityAIHorseFollowOwner(EntityHorse horse, double speed, float minDist, float maxDist)
        {
            theHorse = horse;
            theWorld = horse.world;
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



        public boolean shouldExecute()
        {
            if(!theHorse.isTame() || theHorse.getLeashed() || !theHorse.hasCustomName())return false;
            if (theOwner == null) {
                theOwner = theWorld.getPlayerEntityByUUID(theHorse.getOwnerUniqueId());
                if (theOwner == null)
                {
                    return false;
                }
            }
            if(!Wearing.isWearingTheRightBackpack(theOwner, "Horse"))return false;
            if (theHorse.getDistanceSqToEntity(theOwner) < minDist * minDist * 20) {
                return false;
            }
            return true;
        }

        public boolean continueExecuting()
        {
            return (Wearing.isWearingTheRightBackpack(theOwner,"Horse") && !this.petPathfinder.noPath() && theHorse.getDistanceSqToEntity(theOwner) > this.maxDist * this.maxDist * 2);
        }

        public void startExecuting()
        {
            tickCounter = 0;
            petPathfinder = theHorse.getNavigator();
        }

        public void resetTask()
        {
            theOwner = null;
            petPathfinder.clearPathEntity();
        }


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
