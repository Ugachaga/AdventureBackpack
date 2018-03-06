package com.darkona.adventurebackpack.handlers;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemPotion;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.darkona.adventurebackpack.common.ServerActions;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.entity.ai.EntityAIHorseFollowOwner;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.network.messages.EntitySoundPacket;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * Created on 17/10/2014
 *
 * @author Darkona
 */
public class GeneralEventHandler
{
    @SubscribeEvent
    public void eatGoldenApple(LivingEntityUseItemEvent.Finish event)
    {
        if (!(event.getEntity() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntity();

        if (ConfigHandler.backpackAbilities)
        {
            if (event.getItem().getItem() instanceof ItemAppleGold
                    && Wearing.isWearingTheRightBackpack(player, BackpackTypes.RAINBOW))
            {
                InventoryBackpack inv = new InventoryBackpack(Wearing.getWearingBackpack(player));
                if (inv.getLastTime() > 0) return;
                inv.setLastTime(Utils.secondsToTicks(150));
                inv.dirtyTime();
                if (!player.world.isRemote)
                {
                    String nyanString = Utils.makeItRainbow("NYANCAT");
                    player.sendMessage(new TextComponentString(nyanString));
                    ModNetwork.sendToNearby(new EntitySoundPacket.Message(EntitySoundPacket.NYAN_SOUND, player), player);
                }
            }
        }

        if (event.getItem().getItem() instanceof ItemPotion && (event.getItem().getItem()).getDamage(event.getItem()) == 0)
        {
            if (!player.world.isRemote)
            {
                FluidEffectRegistry.executeFluidEffectsForFluid(FluidRegistry.WATER, player, player.getEntityWorld());
            }
        }
    }

    @SubscribeEvent
    public void detectLightning(EntityStruckByLightningEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            ServerActions.electrify((EntityPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public void makeHorsesFollowOwner(EntityJoinWorldEvent event)
    {
        if (!ConfigHandler.backpackAbilities)
            return;

        if (event.getEntity() instanceof EntityHorse)
        {
            EntityHorse horse = ((EntityHorse) event.getEntity());
            if (!horse.isDead && horse.isTame() && !horse.getCustomNameTag().isEmpty())
            {
                UUID ownerUUID = horse.getOwnerUniqueId();
                if (ownerUUID != null && !ownerUUID.toString().isEmpty())
                {
                    boolean set = true;
                    if (horse.world.getPlayerEntityByUUID(ownerUUID) != null)
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
                        horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
                    }
                }
            }
        }
    }
}
