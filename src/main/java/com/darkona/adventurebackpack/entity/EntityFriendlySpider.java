package com.darkona.adventurebackpack.entity;

import com.darkona.adventurebackpack.util.Wearing;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.Random;

/**
 * Created on 11/01/2015
 *
 * @author Darkona
 */
public class EntityFriendlySpider extends EntityCreature
{

    private float prevRearingAmount;
    private int jumpTicks;
    @SuppressWarnings("unused")
	private EntityPlayer owner;
    @SuppressWarnings("unused")
	private boolean tamed = false;
    @SuppressWarnings("unused")
//	private final EntityAIControlledByPlayer aiControlledByPlayer;

    @Override
    protected void entityInit() {
        super.entityInit();
        //this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    }

    public EntityFriendlySpider(World world) {
        super(world);
        this.setSize(1.4F, 0.9F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        //TODO: add ai for play control
        //this.tasks.addTask(2, new EntityAIFollowParent(this, 0.3F));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    public int getTalkInterval()
    {
        return 300;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    //TODO: find out what this is
    //@Override
    protected void func_145780_a(int x, int y, int z, Block block)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getSwimSound()
    {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    protected SoundEvent getSplashSound()
    {
        return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount)
    {
        if (this.isEntityInvulnerable(damageSource))
        {
            return false;
        }
        else if (super.attackEntityFrom(damageSource, amount))
        {
            Entity entity = damageSource.getEntity();

            if (this.isRiding() && this.getRidingEntity() != entity)
            {
                if (entity != this)
                {
                    //TODO: come back to this to make sure we are doing damage to the right thing
                    //this.entityToAttack = entity;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    @Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return 0.5F - this.world.getLightBrightness(pos);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        return false;
    }

    //@Override
    protected boolean interact(EntityPlayer player) {

        try
        {
            if (!this.world.isRemote && Wearing.isWearingTheRightBackpack(player, "Spider"))
            {
                //player.mountEntity(this);
                this.addPassenger(player);
                return true;
            }
        } catch (Exception oops)
        {
            return false;
        }
        return false;
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isDead;
    }

    @Override
    public boolean shouldRiderFaceForward(EntityPlayer player) {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //if (this.world.isRemote && this.dataWatcher.hasChanges())
        //{
        //TODO: find out what dataWatcher is
        //    this.dataWatcher.func_111144_e();
        //}
        if (this.getRidingEntity() instanceof EntityPlayer)
        {

        }
        if (this.getRidingEntity() != null && this.getRidingEntity().isDead)
        {
            //TODO: what to do when our rider dies
            //not sure on this on
            //this.setRidingEntity() = null;
        }
        if (!this.world.isRemote)
        {
            //this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }

    private void normalLivingUpdateWithNoAI(){
        /**
         * TODO: whats this all doing?
        if (this.jumpTicks > 0)
        {
            --this.jumpTicks;
        }

        if (this.newPosRotationIncrements > 0)
        {
            double d0 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
            double d1 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
            double d2 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double) this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (!this.isClientWorld())
        {
            this.motionX *= 0.98D;
            this.motionY *= 0.98D;
            this.motionZ *= 0.98D;
        }

        if (Math.abs(this.motionX) < 0.005D)
        {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.005D)
        {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.005D)
        {
            this.motionZ = 0.0D;
        }

        this.world.theProfiler.startSection("ai");

        if (this.isMovementBlocked())
        {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        }
        else if (this.isClientWorld())
        {

        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");

        if (this.isJumping)
        {
            if (!this.isInWater() && !this.handleLavaMovement())
            {
                if (this.onGround && this.jumpTicks == 0)
                {
                    this.jump();
                    this.jumpTicks = 10;
                }
            }
            else
            {
                this.motionY += 0.03999999910593033D;
            }
        }
        else
        {
            this.jumpTicks = 0;
        }
        this.setJumping(false);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98F;
        this.moveForward *= 0.98F;
        this.randomYawVelocity *= 0.9F;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");

        if (!this.worldObj.isRemote)
        {
            this.collideWithNearbyEntities();
        }

        this.worldObj.theProfiler.endSection();
        **/
    }

    @Override
    public void onLivingUpdate() {
        if (this.getRidingEntity() != null)
        {
            normalLivingUpdateWithNoAI();
        } else
        {
            super.onLivingUpdate();
        }
        this.updateArmSwingProgress();
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset();
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (this.getRidingEntity() != null)
        {
            this.prevRotationYaw = this.rotationYaw = this.getRidingEntity().rotationYaw;
            this.rotationPitch = this.getRidingEntity().rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            strafe = ((EntityLivingBase)this.getRidingEntity()).moveStrafing * 0.5F;
            forward = ((EntityLivingBase)this.getRidingEntity()).moveForward;

            if (forward <= 0.0F)
            {
                forward *= 0.25F;
            }
            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.2F;

            if (!this.world.isRemote)
            {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                super.moveEntityWithHeading(strafe, forward);
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
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    public void spiderJump()
    {
        this.getJumpHelper().setJumping();
        //this.getJumpHelper().doJump();
    }

    public static class GroupData implements IEntityLivingData
    {
        public int field_111105_a;
        @SuppressWarnings("unused")
		private static final String __OBFID = "CL_00001700";

        public void func_111104_a(Random p_111104_1_)
        {
            int i = p_111104_1_.nextInt(5);

            if (i <= 1)
            {
                this.field_111105_a = PotionType.getID(PotionTypes.SWIFTNESS);
            }
            else if (i <= 2)
            {
                this.field_111105_a = PotionType.getID(PotionTypes.STRENGTH);
            }
            else if (i <= 3)
            {
                this.field_111105_a = PotionType.getID(PotionTypes.REGENERATION);
            }
            else if (i <= 4)
            {
                this.field_111105_a = PotionType.getID(PotionTypes.INVISIBILITY);
            }
        }
    }

    @Override
    protected Item getDropItem()
    {
        return Items.STRING;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        super.dropFewItems(p_70628_1_, p_70628_2_);

        if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.SPIDER_EYE, 1);
        }
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    @Override
    public boolean isOnLadder()
    {
        //TODO: implement
        return false;
    }

    /**
     * Sets the Entity inside a web block.
     */
    @Override
    public void setInWeb() {}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potEffect)
    {
        return Potion.getIdFromPotion(potEffect.getPotion()) == PotionType.getID(PotionTypes.POISON) ? false : super.isPotionApplicable(potEffect);
    }
}

