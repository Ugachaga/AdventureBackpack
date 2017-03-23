package com.darkona.adventurebackpack.playerProperties;

import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.item.IBackWearableItem;
import com.darkona.adventurebackpack.network.SyncPropertiesPacket;
import com.darkona.adventurebackpack.util.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
//import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created on 24/10/2014
 *
 * @author Darkona
 * TODO: change for capabilityes system
 */
@SuppressWarnings("unused")
public class BackpackProperty //implements IExtendedEntityProperties
{

    public static final String PROPERTY_NAME = "abp.property";
    protected EntityPlayer player = null;
    private ItemStack wearable = null;
    private BlockPos campFire = null;
    private NBTTagCompound wearableData = new NBTTagCompound();
    private boolean forceCampFire = false;
    private int dimension = 0;
    private RenderPlayerEvent.Specials.Pre event;

    public NBTTagCompound getWearableData()
    {
        return wearableData;
    }

    public static void sync(EntityPlayer player)
    {
        if (player instanceof EntityPlayerMP)
        {
            syncToNear(player);
        }
    }

    public static void syncToNear(EntityPlayer player)
    {
        //Thanks diesieben07!!!
        if (player != null && player instanceof EntityPlayerMP)
        {
            try
            {
                ((EntityPlayerMP) player).getServerWorld().getEntityTracker().sendToTrackingAndSelf(player, ModNetwork.net.getPacketFrom(new SyncPropertiesPacket.Message(player.getEntityId(), get(player).getData())));
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public BackpackProperty(EntityPlayer player)
    {
        this.player = player;
    }

    public NBTTagCompound getData()
    {
        NBTTagCompound data = new NBTTagCompound();
        saveNBTData(data);

        return data;
    }

    public static void register(EntityPlayer player)
    {
        //TODO: set up capabilities
        //player.registerExtendedProperties(PROPERTY_NAME, new BackpackProperty(player));
    }

    public static BackpackProperty get(EntityPlayer player)
    {
        //TODO: update to capabilityes
        //return (BackpackProperty) player.getExtendedProperties(PROPERTY_NAME);
        return new BackpackProperty(player);
    }

    /**
     * Called when the entity that this class is attached to is saved.
     * Any custom entity data  that needs saving should be saved here.
     *
     * @param compound The compound to save to.
     */
    //@Override
    public void saveNBTData(NBTTagCompound compound)
    {
        if (wearable != null) compound.setTag("wearable", wearable.writeToNBT(new NBTTagCompound()));
        if (campFire != null)
        {
            compound.setInteger("campFireX", campFire.getX());
            compound.setInteger("campFireY", campFire.getY());
            compound.setInteger("campFireZ", campFire.getZ());
            compound.setInteger("campFireDim", dimension);

        }
        compound.setBoolean("forceCampFire", forceCampFire);
    }

    /**
     * Called when the entity that this class is attached to is loaded.
     * In order to hook into this, you will need to subscribe to the EntityConstructing event.
     * Otherwise, you will need to initialize manually.
     *
     * @param compound The compound to load from.
     */
    //@Override
    public void loadNBTData(NBTTagCompound compound)
    {
        if (compound != null)
        {
            setWearable(compound.hasKey("wearable") ? ItemStack.loadItemStackFromNBT(compound.getCompoundTag("wearable")) : null);
            setCampFire(new BlockPos(compound.getInteger("campFireX"), compound.getInteger("campFireY"), compound.getInteger("campFireZ")));
            dimension = compound.getInteger("compFireDim");
            forceCampFire = compound.getBoolean("forceCampfire");
        }
    }

    /**
     * Used to initialize the extended properties with the entity that this is attached to, as well
     * as the world object.
     * Called automatically if you register with the EntityConstructing event.
     * May be called multiple times if the extended properties is moved over to a new entity.
     * Such as when a player switches dimension {Minecraft re-creates the player entity}
     *
     * @param entity The entity that this extended properties is attached to
     * @param world  The world in which the entity exists
     */
    //@Override
    public void init(Entity entity, World world)
    {
        this.player = (EntityPlayer) entity;
    }

    public void setWearable(ItemStack bp)
    {
        wearable = bp;
    }

    public ItemStack getWearable()
    {
        return wearable != null ? wearable : null;
    }

    public void setCampFire(BlockPos cf)
    {
        campFire = cf;
    }

    public boolean hasWearable()
    {
        return wearable != null;
    }

    public BlockPos getCampFire()
    {
        return campFire;
    }

    public EntityPlayer getPlayer()
    {
        return player;
    }

    public void setDimension(int dimension)
    {
        this.dimension = dimension;
    }

    public int getDimension()
    {
        return dimension;
    }

    public boolean isForcedCampFire()
    {
        return forceCampFire;
    }

    public void setForceCampFire(boolean forceCampFire)
    {
        this.forceCampFire = forceCampFire;
    }

    //Scary names for methods because why not
    public void executeWearableUpdateProtocol()
    {
        if (Utils.notNullAndInstanceOf(wearable.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem) wearable.getItem()).onEquippedUpdate(player.getEntityWorld(), player, wearable);
        }
    }

    public void executeWearableDeathProtocol()
    {
        if (Utils.notNullAndInstanceOf(wearable.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem) wearable.getItem()).onPlayerDeath(player.getEntityWorld(), player, wearable);
        }
    }

    public void executeWearableEquipProtocol()
    {
        if (Utils.notNullAndInstanceOf(wearable.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem) wearable.getItem()).onEquipped(player.getEntityWorld(), player, wearable);
        }
    }

    public void executeWearableUnequipProtocol()
    {
        if (Utils.notNullAndInstanceOf(wearable.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem) wearable.getItem()).onUnequipped(player.getEntityWorld(), player, wearable);
        }
    }
}