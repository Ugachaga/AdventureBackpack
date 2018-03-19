package com.darkona.adventurebackpack.handlers;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.darkona.adventurebackpack.block.BlockSleepingBag;
import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.entity.ai.EntityAIHorseFollowOwner;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.item.IBackWearableItem;
import com.darkona.adventurebackpack.item.ItemBackpack;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.proxy.ServerProxy;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.EnchUtils;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Wearing;

public class PlayerEventHandler
{
    @SubscribeEvent
    public void registerBackpackProperty(EntityEvent.EntityConstructing event)
    {
        if (event.getEntity() instanceof EntityPlayer && BackpackProperty.get((EntityPlayer) event.getEntity()) == null)
        {
            BackpackProperty.register((EntityPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public void joinPlayer(EntityJoinWorldEvent event)
    {
        if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            LogHelper.info("Joined EntityPlayer of name: " + player.getName());
            NBTTagCompound playerData = ServerProxy.extractPlayerProps(player.getUniqueID());
            if (playerData != null)
            {
                BackpackProperty.get(player).loadNBTData(playerData);
                BackpackProperty.sync(player);
                LogHelper.info("Stored properties retrieved");
            }
        }
    }

    @SubscribeEvent
    public void playerLogsIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            EntityPlayer player = event.player;

            BackpackProperty.sync(player);

            if (Wearing.isWearingCopter(player))
            {
                ServerActions.copterSoundAtLogin(player);
            }
            if (Wearing.isWearingJetpack(player))
            {
                ServerActions.jetpackSoundAtLogin(player);
            }
        }
    }

    @SubscribeEvent
    public void playerTravelsAcrossDimensions(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            BackpackProperty.sync(event.player);
        }
    }

    /**
     * Used for the Piston Boots to give them their amazing powers.
     */
    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event)
    {
        if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            if (Wearing.isWearingBoots(player) && player.onGround)
            {
                ServerActions.pistonBootsJump(player);
            }
        }
    }

    private boolean pistonBootsStepHeight = false;

    @SubscribeEvent
    public void pistonBootsUnequipped(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (Wearing.isWearingBoots(player))
            {
                if (!pistonBootsStepHeight)
                {
                    pistonBootsStepHeight = true;
                }
            }
            else
            {
                if (pistonBootsStepHeight)
                {
                    player.stepHeight = 0.5001F;
                    pistonBootsStepHeight = false;
                }
            }
        }
    }

    /**
     * Used by the Piston boots to lessen the fall damage. It's hacky, but I don't care.
     */
    @SubscribeEvent
    public void onFall(LivingFallEvent event)
    {
        if (event.getEntity() != null)
        {
            if (event.getEntity() instanceof EntityCreature && ConfigHandler.fixLead)
            {
                EntityCreature creature = (EntityCreature) event.getEntity();
                if (creature.getLeashed() && creature.getLeashHolder() instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) creature.getLeashHolder();
                    if (creature.motionY > -2.0f && player.motionY > -2.0f)
                    {
                        event.setCanceled(true);
                    }
                }
            }

            if (event.getEntity() instanceof EntityFriendlySpider)
            {
                if ((event.getEntity()).isBeingRidden()
                        && event.getEntity().getPassengers().get(0) instanceof EntityPlayer
                        && event.getDistance() < 5)
                {
                    event.setCanceled(true);
                }
            }

            if (event.getEntity() instanceof EntityPlayer)
            {
                if (Wearing.isWearingBoots(((EntityPlayer) event.getEntity())) && event.getDistance() < 8)
                {
                    event.setCanceled(true);
                }
                if (Wearing.isWearingTheRightBackpack((EntityPlayer) event.getEntity(), BackpackTypes.IRON_GOLEM) && ConfigHandler.backpackAbilities)
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void playerDies(LivingDeathEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            if (!player.world.isRemote)
            {
                BackpackProperty props = BackpackProperty.get(player);

                if (ConfigHandler.enableCampfireSpawn && props.isForcedCampFire())
                {
                    BlockPos lastCampFire = props.getCampFire();
                    if (lastCampFire != null)
                    {
                        player.setSpawnChunk(lastCampFire, false, player.dimension);
                    }
                    //Set the forced spawn coordinates on the campfire. False, because the player must respawn at spawn point if there's no campfire.
                }

                if (Wearing.isWearingWearable(player))
                {
                    if (Wearing.isWearingTheRightBackpack(player, BackpackTypes.CREEPER))
                    {
                        player.world.createExplosion(player, player.posX, player.posY, player.posZ, 4.0F, false);
                    }

                    if (player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
                    {
                        ((IBackWearableItem) props.getWearable().getItem()).onPlayerDeath(player.world, player, props.getWearable());
                        ServerProxy.storePlayerProps(player);
                    }
                }
            }
        }
        event.setResult(Event.Result.ALLOW);
    }

    @SubscribeEvent
    public void playerDeathDrop(PlayerDropsEvent event)
    {
        if (!(event.getEntity() instanceof  EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntity();

        if (Wearing.isWearingWearable(player))
        {
            ItemStack pack = Wearing.getWearingWearable(player);
            BackpackProperty props = BackpackProperty.get(player);

            if (EnchUtils.isSoulBounded(pack)
                    || (ConfigHandler.backpackDeathPlace && pack.getItem() instanceof ItemBackpack))
            {
                ((IBackWearableItem) props.getWearable().getItem()).onPlayerDeath(player.world, player, props.getWearable());
                ServerProxy.storePlayerProps(player);
            }
            else
            {
                event.getDrops().add(new EntityItem(player.world, player.posX, player.posY, player.posZ, pack));
                props.setWearable(null);
            }
        }
    }

    @SubscribeEvent
    public void playerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            BackpackProperty.sync(event.player);
        }
    }

    @SubscribeEvent
    public void playerCraftsBackpack(PlayerEvent.ItemCraftedEvent event)
    {
        if (event.crafting.getItem() == ModItems.ADVENTURE_BACKPACK)
        {
            LogHelper.info("Player crafted a backpack, and that backpack's appearance is: "
                    + BackpackTypes.getSkinName(event.crafting));

            if (!ConfigHandler.consumeDragonEgg && BackpackTypes.getType(event.crafting) == BackpackTypes.DRAGON)
            {
                event.player.dropItem(new ItemStack(Blocks.DRAGON_EGG, 1), false);
                event.player.playSound(new SoundEvent(new ResourceLocation("entity.enderdragon.growl")), 1.0f, 5.0f);
            }
        }
    }

    @SubscribeEvent
    public void interactWithCreatures(PlayerInteractEvent.EntityInteract event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if (!player.world.isRemote)
        {
            if (event.getTarget() instanceof EntitySpider)
            {
                if (Wearing.isWearingTheRightBackpack(player, BackpackTypes.SPIDER))
                {
                    EntityFriendlySpider pet = new EntityFriendlySpider(event.getTarget().world);
                    pet.setLocationAndAngles(event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, event.getTarget().rotationYaw, event.getTarget().rotationPitch);
                    event.getTarget().setDead();
                    player.world.spawnEntity(pet);
                    player.startRiding(pet);
                }
            }

            if (event.getTarget() instanceof EntityHorse)
            {
                EntityHorse horse = (EntityHorse) event.getTarget();
                ItemStack stack = player.getHeldItemMainhand();

                if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemNameTag && stack.hasDisplayName())
                {
                    if (horse.getCustomNameTag().isEmpty() && horse.isTame())
                    {
                        horse.setTamedBy(player);
                        horse.tasks.addTask(4, new EntityAIHorseFollowOwner(horse, 1.5d, 2.0f, 20.0f));
                        horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
                        LogHelper.info("The horse follow range is now: " + horse.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue());
                    }
                }
            }
        }
        event.setResult(Event.Result.ALLOW);
    }

    @SubscribeEvent
    public void playerWokeUp(PlayerWakeUpEvent event)
    {
        if (event.getEntity().world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        BlockPos bedLocation = player.getBedLocation(player.dimension);
        if (player.world.getBlockState(bedLocation) == ModBlocks.SLEEPING_BAG_BLOCK)
        {
            //If the player wakes up in one of those super confortable SleepingBags (tm) (Patent Pending)
            if (BlockSleepingBag.isSleepingInPortableBag(player))
            {
                BlockSleepingBag.packPortableSleepingBag(player);
                BackpackProperty.get(player).setWakingUpInPortableBag(true);
                LogHelper.info("Player just woke up in a portable sleeping bag.");
            }
            else
            {
                BackpackProperty.get(player).setForceCampFire(true);
                LogHelper.info("Player just woke up in a sleeping bag, forcing respawn in the last lighted campfire, if there's any");
            }
        }
        else
        {
            //If it's a regular bed or whatever
            BackpackProperty.get(player).setForceCampFire(false);
        }
    }

    @SubscribeEvent
    public void tickPlayer(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        if (player != null && !player.isDead && Wearing.isWearingWearable(player))
        {
            if (event.phase == TickEvent.Phase.START)
            {
                BackpackProperty.get(player).executeWearableUpdateProtocol();
            }
            if (event.phase == TickEvent.Phase.END)
            {
                if (!player.world.isRemote)
                {
                    if (BackpackProperty.get(player).isWakingUpInPortableBag() && Wearing.isWearingBackpack(player))
                    {
                        BlockSleepingBag.restoreOriginalSpawn(player, Wearing.getWearingBackpackInv(player).getExtendedProperties());
                        BackpackProperty.get(player).setWakingUpInPortableBag(false);
                    }
                }
            }
        }
    }

}
