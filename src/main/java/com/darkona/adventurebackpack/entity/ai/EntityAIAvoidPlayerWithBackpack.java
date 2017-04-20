package com.darkona.adventurebackpack.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;


/**
 * Created by Darkona on 12/10/2014.
 */
public class EntityAIAvoidPlayerWithBackpack extends EntityAIBase
{
    //private String backpackName;

    //public final IEntitySelector field_98218_a = new AvoidEntitySelector(this);

    /**
     * The com.darkona.adventurebackpack.entity we are attached to
     */
    public EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    //private float distanceFromEntity;

    /**
     * The PathEntity of our com.darkona.adventurebackpack.entity
     */
    //private PathEntity entityPathEntity;

    /**
     * The PathNavigate of our com.darkona.adventurebackpack.entity
     */
    private PathNavigate entityPathNavigate;

    /**
     * The class of the com.darkona.adventurebackpack.entity we should avoid
     */
    //@SuppressWarnings("rawtypes")
	//private Class targetEntityClass;

    public EntityAIAvoidPlayerWithBackpack(EntityCreature par1EntityCreature, @SuppressWarnings("rawtypes") Class par2Class, float par3, double par4, double par6, String colorName)
    {
        this.theEntity = par1EntityCreature;
        //this.targetEntityClass = par2Class;
        //this.distanceFromEntity = par3;
        this.farSpeed = par4;
        this.nearSpeed = par6;
        this.entityPathNavigate = par1EntityCreature.getNavigator();
        this.setMutexBits(1);
        //this.backpackName = colorName;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        /**
        if (this.targetEntityClass == EntityPlayer.class)
        {
            if (this.theEntity instanceof EntityTameable && ((EntityTameable) this.theEntity).isTamed())
            {
                return false;
            }

            List<?> list = this.theEntity.world.selectEntitiesWithinAABB(this.targetEntityClass,
                                                                         this.theEntity.getEntityBoundingBox().expand(
                                                                                    this.distanceFromEntity, 3.0D, this.distanceFromEntity),
                                                                        this.field_98218_a);

            if (list.isEmpty())
            {
                return false;
            }

            for (Object player : list)
            {
                if (BackpackNames.getBackpackColorName(Wearing.getWearingBackpack((EntityPlayer) player)).equals(backpackName))
                {
                    this.closestLivingEntity = (Entity) player;
                }
            }

            if (this.closestLivingEntity == null)
            {
                return false;
            }


            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3d(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

            if (vec3d == null)
            {
                return false;
            } else if (this.closestLivingEntity.getDistanceSq(Vec3d.xCoord, Vec3d.yCoord, Vec3d.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))
            {
                return false;
            } else
            {
    //            this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(Vec3d.xCoord, Vec3d.yCoord, Vec3d.zCoord);
  //              return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(Vec3d);
            }
        }
        **/
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entityPathNavigate.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        //this.entityPathNavigate.setPath(this.theEntity.createNavigator(theEntity.world), this.farSpeed);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D)
        {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        } else
        {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }

    static EntityCreature func_98217_a(EntityAIAvoidPlayerWithBackpack par0EntityAIAvoidEntity)
    {
        return par0EntityAIAvoidEntity.theEntity;
    }
}
