package com.darkona.adventurebackpack.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 11/01/2015
 *
 * @author Darkona
 */
public class EntityFriendlySpider extends EntityCreature
{
    private float prevRearingAmount;
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntitySpider.class, DataSerializers.BYTE);
    private int jumpTicks;
    //private final EntityAIControlledByPlayer aiControlledByPlayer;

    //TODO see EntitySpider
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    }

    public EntityFriendlySpider(World world)
    {
        super(world);
        this.setSize(1.4F, 0.9F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        //TODO ai
        //this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    public int getTalkInterval()
    {
        return 300;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount)
    {
        if (this.isEntityInvulnerable(damageSource))
            return false;

        if (super.attackEntityFrom(damageSource, amount))
        {
            EntityLivingBase entity = (EntityLivingBase) damageSource.getTrueSource();

            if (this.isRiding() && this.getRidingEntity() != entity && entity != this)
            {
                this.setAttackTarget(entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return 0.5F - this.world.getLightBrightness(pos);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        try
        {
            if (!this.world.isRemote && Wearing.isWearingTheRightBackpack(player, BackpackTypes.SPIDER))
            {
                player.startRiding(this);
                return true;
            }
        }
        catch (Exception oops)
        {
            return false;
        }
        return false;
    }

    @Override
    public boolean canBeSteered()
    {
        return true;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    @Override
    public boolean shouldRiderFaceForward(EntityPlayer player)
    {
        return true;
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (dataManager.get(CLIMBING) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = dataManager.get(CLIMBING);

        if (climbing)
            b0 = (byte)(b0 | 1);
        else
            b0 = (byte)(b0 & -2);

        this.dataManager.set(CLIMBING, b0);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote && this.dataManager.isDirty())
        {
            this.dataManager.getDirty(); //TODO are we really need to do it manually?
        }
        if (this.getRidingEntity() instanceof EntityPlayer)
        {
            // ???
        }
        if (this.getRidingEntity() != null && this.getRidingEntity().isDead)
        {
            this.dismountRidingEntity();
        }

        if (!this.world.isRemote)
        {
            setBesideClimbableBlock(collidedHorizontally);
        }
    }

    //TODO ok.. this piece of code from 1.7.10 vanilla EntityLivingBase#onLivingUpdate, with little changes
    private void normalLivingUpdateWithNoAI()
    {
//        if (this.jumpTicks > 0)
//        {
//            --this.jumpTicks;
//        }
//
//        if (this.newPosRotationIncrements > 0)
//        {
//            double d0 = this.posX + (this.newPosX - this.posX) / (double) this.newPosRotationIncrements;
//            double d1 = this.posY + (this.newPosY - this.posY) / (double) this.newPosRotationIncrements;
//            double d2 = this.posZ + (this.newPosZ - this.posZ) / (double) this.newPosRotationIncrements;
//            double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double) this.rotationYaw);
//            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.newPosRotationIncrements);
//            this.rotationPitch = (float) ((double) this.rotationPitch + (this.newRotationPitch - (double) this.rotationPitch) / (double) this.newPosRotationIncrements);
//            --this.newPosRotationIncrements;
//            this.setPosition(d0, d1, d2);
//            this.setRotation(this.rotationYaw, this.rotationPitch);
//        }
//        else if (!this.isClientWorld())
//        {
//            this.motionX *= 0.98D;
//            this.motionY *= 0.98D;
//            this.motionZ *= 0.98D;
//        }
//
//        if (Math.abs(this.motionX) < 0.005D)
//        {
//            this.motionX = 0.0D;
//        }
//
//        if (Math.abs(this.motionY) < 0.005D)
//        {
//            this.motionY = 0.0D;
//        }
//
//        if (Math.abs(this.motionZ) < 0.005D)
//        {
//            this.motionZ = 0.0D;
//        }
//
//        this.worldObj.theProfiler.startSection("ai");
//
//        if (this.isMovementBlocked())
//        {
//            this.isJumping = false;
//            this.moveStrafing = 0.0F;
//            this.moveForward = 0.0F;
//            this.randomYawVelocity = 0.0F;
//        }
//        else if (this.isClientWorld())
//        {
//
//        }
//
//        this.worldObj.theProfiler.endSection();
//        this.worldObj.theProfiler.startSection("jump");
//
//        if (this.isJumping)
//        {
//            if (!this.isInWater() && !this.handleLavaMovement())
//            {
//                if (this.onGround && this.jumpTicks == 0)
//                {
//                    this.jump();
//                    this.jumpTicks = 10;
//                }
//            }
//            else
//            {
//                this.motionY += 0.03999999910593033D;
//            }
//        }
//        else
//        {
//            this.jumpTicks = 0;
//        }
//        this.setJumping(false);
//        this.worldObj.theProfiler.endSection();
//        this.worldObj.theProfiler.startSection("travel");
//        this.moveStrafing *= 0.98F;
//        this.moveForward *= 0.98F;
//        this.randomYawVelocity *= 0.9F;
//        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
//        this.worldObj.theProfiler.endSection();
//        this.worldObj.theProfiler.startSection("push");
//
//        if (!this.worldObj.isRemote)
//        {
//            this.collideWithNearbyEntities();
//        }
//
//        this.worldObj.theProfiler.endSection();
    }

    @Override
    public void onLivingUpdate()
    {
        if (this.getRidingEntity() != null)
        {
            normalLivingUpdateWithNoAI();
        }
        else
        {
            super.onLivingUpdate();
        }
        this.updateArmSwingProgress();
    }

    @Override
    public double getMountedYOffset()
    {
        return super.getMountedYOffset();
    }

    @Override
    public void travel(float strafe, float vertical, float forward)
    {
        if (this.getRidingEntity() != null)
        {
            this.prevRotationYaw = this.rotationYaw = this.getRidingEntity().rotationYaw;
            this.rotationPitch = this.getRidingEntity().rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            strafe = ((EntityLivingBase) this.getRidingEntity()).moveStrafing * 0.5F;
            forward = ((EntityLivingBase) this.getRidingEntity()).moveForward;

            if (forward <= 0.0F)
            {
                forward *= 0.25F;
            }
            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.2F;

            if (!this.world.isRemote)
            {
                this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                super.travel(strafe, 0f, forward); //TODO 0F is ok?
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d0 = this.posX - this.prevPosX;
            double d1 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F)
            {
                f4 = 1.0F;
            }

            this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.travel(strafe, 0f, forward); //TODO 0F is ok?
        }
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        super.updatePassenger(passenger);

        if (passenger instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)passenger;
            this.renderYawOffset = entityliving.renderYawOffset;
        }

        if (this.prevRearingAmount > 0.0F) //TODO set prevRearingAmount, see AbstractHorse implementation
        {
            float f3 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
            float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
            float f1 = 0.7F * this.prevRearingAmount;
            float f2 = 0.15F * this.prevRearingAmount;
            passenger.setPosition(this.posX + (double)(f1 * f3), this.posY + this.getMountedYOffset() + passenger.getYOffset() + (double)f2, this.posZ - (double)(f1 * f));

            if (passenger instanceof EntityLivingBase)
            {
                ((EntityLivingBase)passenger).renderYawOffset = this.renderYawOffset;
            }
        }
    }

    @Override
    protected Item getDropItem()
    {
        return Items.STRING;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        super.dropFewItems(wasRecentlyHit, lootingModifier);

        if (wasRecentlyHit && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + lootingModifier) > 0))
        {
            this.dropItem(Items.SPIDER_EYE, 1);
        }
    }

    @Override
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    @Override
    public void setInWeb()
    {
        // MUAHAHAH!
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect)
    {
        return effect.getPotion() != MobEffects.POISON && super.isPotionApplicable(effect);
    }



    //TODO implement other stuff from EntitySpider, or maybe just extends from it
    static class AISpiderAttack extends EntityAIAttackMelee
    {
        public AISpiderAttack(EntitySpider spider)
        {
            super(spider, 1.0D, true);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0)
            {
                this.attacker.setAttackTarget((EntityLivingBase)null);
                return false;
            }
            else
            {
                return super.shouldContinueExecuting();
            }
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (double)(4.0F + attackTarget.width);
        }
    }

    static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
    {
        public AISpiderTarget(EntitySpider spider, Class<T> classTarget)
        {
            super(spider, classTarget, true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            float f = this.taskOwner.getBrightness();
            return f >= 0.5F ? false : super.shouldExecute();
        }
    }

    public static class GroupData implements IEntityLivingData
    {
        public Potion effect;

        public void setRandomEffect(Random rand)
        {
            int i = rand.nextInt(5);

            if (i <= 1)
            {
                this.effect = MobEffects.SPEED;
            }
            else if (i <= 2)
            {
                this.effect = MobEffects.STRENGTH;
            }
            else if (i <= 3)
            {
                this.effect = MobEffects.REGENERATION;
            }
            else if (i <= 4)
            {
                this.effect = MobEffects.INVISIBILITY;
            }
        }
    }
}

