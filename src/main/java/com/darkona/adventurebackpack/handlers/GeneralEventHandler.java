package com.darkona.adventurebackpack.handlers;

import java.util.UUID;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.entity.ai.EntityAIHorseFollowOwner;
import com.darkona.adventurebackpack.events.WearableEvent;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.network.messages.EntitySoundPacket;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemPotion;
//import net.minecraft.util.ChatComponentText;
//import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
//import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class GeneralEventHandler
{
    /**
     * @param event
     */
    /**
    @SubscribeEvent
    public void eatGoldenApple(PlayerUseItemEvent.Finish event)
    {
        EntityPlayer player = event.entityPlayer;
        if (ConfigHandler.backpackAbilities)
        {
            if (event.item.getItem() instanceof ItemAppleGold &&
            //((ItemAppleGold) event.item.getItem()).getRarity(event.item) == EnumRarity.epic &&
                    Wearing.isWearingTheRightBackpack(player, "Rainbow"))
            {

                InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
                if (inv.getLastTime() > 0) return;
                inv.setLastTime(Utils.secondsToTicks(150));
                inv.dirtyTime();
                if (!player.worldObj.isRemote)
                {
                    String nyanString = EnumChatFormatting.RED +
                            "N" + EnumChatFormatting.GOLD +
                            "Y" + EnumChatFormatting.YELLOW +
                            "A" + EnumChatFormatting.GREEN +
                            "N" + EnumChatFormatting.AQUA +
                            "C" + EnumChatFormatting.BLUE +
                            "A" + EnumChatFormatting.DARK_PURPLE +
                            "T";
                    player.addChatComponentMessage(new ChatComponentText(nyanString));
                    ModNetwork.sendToNearby(new EntitySoundPacket.Message(EntitySoundPacket.NYAN_SOUND, player), player);
                }
            }
        }

        if (event.item.getItem() instanceof ItemPotion && (event.item.getItem()).getDamage(event.item) == 0)
        {
            if (!player.worldObj.isRemote)
            {
                FluidEffectRegistry.executeFluidEffectsForFluid(FluidRegistry.WATER, player, player.getEntityWorld());
            }
        }
    }
    */

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
                    boolean set = true;
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
