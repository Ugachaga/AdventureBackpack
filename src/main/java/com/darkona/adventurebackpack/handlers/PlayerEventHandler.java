package com.darkona.adventurebackpack.handlers;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.develop.msg;
import com.darkona.adventurebackpack.develop.texturemsg;
import com.darkona.adventurebackpack.entity.EntityFriendlySpider;
import com.darkona.adventurebackpack.entity.ai.EntityAIHorseFollowOwner;
import com.darkona.adventurebackpack.init.ModBlocks;
import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.item.IBackWearableItem;
import com.darkona.adventurebackpack.playerProperties.BackpackProperty;
import com.darkona.adventurebackpack.proxy.ServerProxy;
import com.darkona.adventurebackpack.reference.BackpackNames;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;

/**
 * Created on 11/10/2014 Handle ALL the events!
 *
 * @author Darkona
 * @see com.darkona.adventurebackpack.client.ClientActions
 */
public class PlayerEventHandler
{
    @SuppressWarnings("unused")
    private static int tickCounter = 0;

    @SubscribeEvent
    public void registerBackpackProperty(EntityEvent.EntityConstructing event)
    {
        if (event.getEntity() instanceof EntityPlayer && BackpackProperty.get((EntityPlayer) event.getEntity()) == null)
        {
            BackpackProperty.register((EntityPlayer) event.getEntity());
            /*if (!event.entity.worldObj.isRemote)
            {
                AdventureBackpack.proxy.joinPlayer((EntityPlayer)event.entity);
            }*/
        }

    }

    @SubscribeEvent
    public void joinPlayer(EntityJoinWorldEvent event)
    {
        if (!event.getWorld().isRemote)
        {
            if (Utils.notNullAndInstanceOf(event.getEntity(), EntityPlayer.class))
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
                msg.handleJoin(player);
                texturemsg.handleJoin(player);
            }
        }
    }

    @SubscribeEvent
    public void playerLogsIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            BackpackProperty.sync(event.player);
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
     *
     * @param event
     */
    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event)
    {
        if (event.getEntity() != null && event.getEntityLiving() instanceof EntityPlayer)
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
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Wearing.isWearingBoots(player))
            {
                if (!pistonBootsStepHeight)
                {
                    pistonBootsStepHeight = true;
                }
            } else if (pistonBootsStepHeight)
            {
                player.stepHeight = 0.5001F;
                pistonBootsStepHeight = false;
            }
        }
    }

    /**
     * Used by the Piston boots to lessen the fall damage. It's hacky, but I don't care.
     *
     * @param event
     */
    @SubscribeEvent
    public void onFall(LivingFallEvent event)
    {
        if (event.getEntity() != null)
        {
            if (event.getEntityLiving() instanceof EntityCreature && ConfigHandler.fixLead)
            {
                EntityCreature creature = (EntityCreature) event.getEntityLiving();
                if (creature.getLeashed() && creature.getLeashedToEntity() != null && creature.getLeashedToEntity() instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) creature.getLeashedToEntity();
                    if (creature.motionY > -2.0f && player.motionY > -2.0f)
                    {
                        event.setCanceled(true);
                    }
                }
            }

            if (event.getEntityLiving() instanceof EntityFriendlySpider)
            {
            //TODO: work this riddenByEntity shit out
            //    if (((EntityFriendlySpider) event.getEntityLiving()).riddenByEntity != null
            //            && ((EntityFriendlySpider) event.getEntityLiving()).riddenByEntity instanceof EntityPlayer
            //            && event.distance < 5)
            //    {
            //        event.setCanceled(true);
            //    }
            }

            if (event.getEntityLiving() instanceof EntityPlayer)
            {
                if (Wearing.isWearingBoots(((EntityPlayer) event.getEntityLiving())) && event.getDistance() < 8)
                {
                    event.setCanceled(true);
                }
                if (Wearing.isWearingTheRightBackpack((EntityPlayer) event.getEntityLiving(), "IronGolem") && ConfigHandler.backpackAbilities)
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void playerDeathDrop(PlayerDropsEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if (Wearing.isWearingWearable(player))
        {
            if (Wearing.isWearingBackpack(player))
            {
                if (ConfigHandler.backpackDeathPlace)
                {
                    BackpackProperty props = BackpackProperty.get(player);
                    ((IBackWearableItem) props.getWearable().getItem()).onPlayerDeath(player.world, player, props.getWearable());
                    if (props.isForcedCampFire())
                    {
                        BlockPos lastCampFire = BackpackProperty.get(player).getCampFire();
                        if (lastCampFire != null)
                        {
                            player.setSpawnChunk(lastCampFire, false, player.dimension);
                        }
                        //Set the forced spawn coordinates on the campfire. False, because the player must respawn at spawn point if there's no campfire.
                    }
                    ServerProxy.storePlayerProps(player);
                } else
                {
                    ItemStack pack = Wearing.getWearingBackpack(player);
                    if (Utils.isSoulBounded(pack)) ServerProxy.storePlayerProps(player);
                    else event.getDrops().add(new EntityItem(player.world, player.posX, player.posY, player.posZ, pack));
                    //TODO get rid of campfire
                }
            } else if (Wearing.isWearingCopter(player))
            {
                ItemStack pack = Wearing.getWearingCopter(player);
                if (Utils.isSoulBounded(pack)) ServerProxy.storePlayerProps(player);
                else event.getDrops().add(new EntityItem(player.world, player.posX, player.posY, player.posZ, pack));
            } else if (Wearing.isWearingJetpack(player))
            {
                ItemStack pack = Wearing.getWearingJetpack(player);
                if (Utils.isSoulBounded(pack)) ServerProxy.storePlayerProps(player);
                else event.getDrops().add(new EntityItem(player.world, player.posX, player.posY, player.posZ, pack));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void playerDies(LivingDeathEvent event)
    {
        if (Utils.notNullAndInstanceOf(event.getEntity(), EntityPlayer.class))
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (!player.world.isRemote
                    && player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
            {
                //LogHelper.info("Player died");
                ServerProxy.storePlayerProps(player);
            }
        }
        event.setResult(Event.Result.ALLOW);
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
        if (event.crafting.getItem() == ModItems.adventureBackpack)
        {
            LogHelper.info("Player crafted a backpack, and that backpack's appearance is: " + event.crafting.getTagCompound().getString("colorName"));
            if (BackpackNames.getBackpackColorName(event.crafting).equals("Dragon") && !ConfigHandler.consumeDragonEgg)
            {
                event.player.dropItem(new ItemStack(Blocks.DRAGON_EGG, 1), false);
                event.player.playSound(new SoundEvent(new ResourceLocation("entity.enderdragon.growl")), 1.0f, 5.0f);
            }
        }
    }

    /**.
     * TODO: disbled pending futher investigation
    @SubscribeEvent
    public void interactWithCreatures(EntityInteractEvent  event)
    {
        EntityPlayer player = event.entityPlayer;
        if (!event.entityPlayer.worldObj.isRemote)
        {
            if (Utils.notNullAndInstanceOf(event.target, EntitySpider.class))
            {
                if (Wearing.isWearingTheRightBackpack(player, "Spider"))
                {
                    EntityFriendlySpider pet = new EntityFriendlySpider(event.target.worldObj);
                    pet.setLocationAndAngles(event.target.posX, event.target.posY, event.target.posZ, event.target.rotationYaw, event.target.rotationPitch);
                    event.target.setDead();
                    event.entityPlayer.worldObj.spawnEntityInWorld(pet);
                    event.entityPlayer.mountEntity(pet);
                }
            }
            if (Utils.notNullAndInstanceOf(event.target, EntityHorse.class))
            {
                ItemStack stack = player.getCurrentEquippedItem();
                EntityHorse horse = (EntityHorse) event.target;
                if (stack != null && stack.getItem() instanceof ItemNameTag && stack.hasDisplayName())
                {
                    if (horse.getCustomNameTag().equals("") && horse.isTame())
                    {
                        horse.setTamedBy(player);
                        horse.tasks.addTask(4, new EntityAIHorseFollowOwner(horse, 1.5d, 2.0f, 20.0f));

                        horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
                        LogHelper.info("The horse follow range is now: " + horse.getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue());
                    }
                }
            }
        }
        event.setResult(Event.Result.ALLOW);
    }
    */


    @SubscribeEvent
    public void playerWokeUp(PlayerWakeUpEvent event)
    {
        if (event.getEntity().world.isRemote) return;
        BlockPos bedLocation = event.getEntityPlayer().getBedLocation(event.getEntityPlayer().dimension);
        if (event.getEntityPlayer().world.getBlockState(bedLocation).getBlock() == ModBlocks.blockSleepingBag)
        {
            //If the player wakes up in one of those super confortable SleepingBags (tm) (Patent Pending)
            BackpackProperty.get(event.getEntityPlayer()).setForceCampFire(true);
            LogHelper.info("Player just woke up in a sleeping bag, forcing respawn in the last lighted campfire, if there's any");
        } else
        {
            //If it's a regular bed or whatever
            BackpackProperty.get(event.getEntityPlayer()).setForceCampFire(false);
        }
    }

    @SubscribeEvent
    public void tickPlayer(TickEvent.PlayerTickEvent event)
    {
        if (event.player != null && !event.player.isDead && Wearing.isWearingWearable(event.player))
        {
            if (event.phase == TickEvent.Phase.START)
            {
                BackpackProperty.get(event.player).executeWearableUpdateProtocol();
            }
        }
    }

}
