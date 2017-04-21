package com.darkona.adventurebackpack.handlers;

import com.darkona.adventurebackpack.capablities.BackpacksCapabilities;
import com.darkona.adventurebackpack.capablities.player.PlayerWearingBackpackCapabilities;
import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.events.WearableEvent;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class GeneralEventHandler
{


    /**
     * Attaches a capability to the entity. Works on both the client and server players.
     * @param event - attach capability event
     */
    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent.Entity event) {
        if (!event.getEntity().hasCapability(BackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null)) 
        {
            if (event.getEntity() instanceof EntityPlayer) {
                event.addCapability(new ResourceLocation(ModInfo.MOD_ID + "." + ModInfo.WEARING_BACKPACK_CAPABILITY_STRING), new PlayerWearingBackpackCapabilities());
            }
            //if (!event.getEntity().hasCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null)) {
            //    event.addCapability(new ResourceLocation(ModInfo.MOD_ID + "." + Constants.DEATH_BACKPACK_CAPABILITY_STRING), new PlayerDeathBackpackCapabilities());
            //}
        }
    }

    @SubscribeEvent
    public void detectBow(ArrowNockEvent event)
    {
        if (!ConfigHandler.backpackAbilities) return;
        if (Wearing.isWearingTheRightBackpack(event.getEntityPlayer(), "Skeleton"))
        {
            InventoryBackpack backpack = new InventoryBackpack(Wearing.getWearingBackpack(event.getEntityPlayer()));
            if (backpack.hasItem(Items.ARROW))
            {
                //TODO: find out what this dose
                //event.getPlayer().setItemInUse(event.getResult(), event.getBow().getMaxItemUseDuration());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void detectArrow(ArrowLooseEvent event)
    {
        if (!ConfigHandler.backpackAbilities) return;
        if (Wearing.isWearingTheRightBackpack(event.getEntityPlayer(), "Skeleton"))
        {
            InventoryBackpack backpack = new InventoryBackpack(Wearing.getWearingBackpack(event.getEntityPlayer()));
            if (backpack.hasItem(Items.ARROW))
            {
                ServerActions.leakArrow(event.getEntityPlayer(), event.getBow(), event.getCharge());
                event.setCanceled(true);
            }
        }
    }

    /**
     * @param event
     */
    @SubscribeEvent
    public void detectLightning(EntityStruckByLightningEvent event)
    {
        if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
        {
            ServerActions.electrify((EntityPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public void makeHorsesFollowOwner(EntityJoinWorldEvent event)
    {
        if (!ConfigHandler.backpackAbilities) return;
        if (event.getEntity() instanceof EntityHorse)
        {

            EntityHorse horse = ((EntityHorse) event.getEntity());
            if (!horse.isDead && horse.isTame() && horse.hasCustomName())
            {
                String ownerUUIDstring = horse.getOwnerUniqueId().toString();
                if (ownerUUIDstring != null && !ownerUUIDstring.isEmpty())
                {
                    //boolean set = true;
                    /**
                    if (horse.worldObj.func_152378_a(UUID.fromString(ownerUUIDstring)) != null)
                    {
                        for (Object entry : horse.tasks.taskEntries)
                        {
                            if (((EntityAITasks.EntityAITaskEntry) entry).action instanceof EntityAIHorseFollowOwner)
                            {
                                set = false;
                            }
                        }
                    }
                    if (set)
                    {
                        horse.tasks.addTask(4, new EntityAIHorseFollowOwner(horse, 1.5d, 2.0f, 20.0f));

                        if (horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange) != null)
                        {
                            horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
                        }
                    }
                    **/
                }
            }
        }
    }

    @SubscribeEvent
    public void backpackUnequipped(WearableEvent.UnequipWearableEvent event)
    {

    }

    /*  @SubscribeEvent
    public void listFluids(FluidRegistry.FluidRegisterEvent event)
    {
        LogHelper.info("Registered fluid " + event.fluidName + " with id " +  event.fluidID);
    }*/
}
