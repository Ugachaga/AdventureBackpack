package com.darkona.adventurebackpack.playerProperties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.item.IBackWearableItem;
import com.darkona.adventurebackpack.network.SyncPropertiesPacket;

/**
 * Created on 24/10/2014
 *
 * @author Darkona
 */
public class BackpackProperty //implements IExtendedEntityProperties
{

    //TODO rework to capabilities

    private static BackpackProperty instance = new BackpackProperty(null);

    private static final String PROPERTY_NAME = "abp.property";

    private EntityPlayer player;
    private ItemStack wearable = null;
    private BlockPos campFire = null;
    private boolean forceCampFire = false;
    private int dimension = 0;

    private boolean isWakingUpInPortableBag = false;

    public void setWakingUpInPortableBag(boolean b)
    {
        this.isWakingUpInPortableBag = b;
    }

    public boolean isWakingUpInPortableBag()
    {
        return this.isWakingUpInPortableBag;
    }

    public static void sync(EntityPlayer player)
    {
        if (player instanceof EntityPlayerMP)
        {
            syncToNear((EntityPlayerMP) player);
        }
    }

    private static void syncToNear(EntityPlayerMP player)
    {
        //Thanks diesieben07!!!
        try
        {
            player.getServerWorld().getEntityTracker()
                    .sendToTrackingAndSelf(player, ModNetwork.INSTANCE.getPacketFrom(new SyncPropertiesPacket
                            .Message(player.getEntityId(), get(player).getData())));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
        //player.registerExtendedProperties(PROPERTY_NAME, new BackpackProperty(player));
    }

    public static BackpackProperty get(EntityPlayer player)
    {
        return instance;
        //return (BackpackProperty) player.getExtendedProperties(PROPERTY_NAME);
    }

    public void saveNBTData(NBTTagCompound compound)
    {
        if (wearable != null) compound.setTag("wearable", wearable.writeToNBT(new NBTTagCompound()));
        if (campFire != null)
        {
            compound.setInteger("campFireX", campFire.getX());
            compound.setInteger("campFireY", campFire.getY());
            compound.setInteger("campFireZ", campFire.getZ());
            compound.setInteger("campFireDim", dimension); //TODO use it for check dim
        }
        compound.setBoolean("forceCampFire", forceCampFire);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        if (compound != null)
        {
            setWearable(compound.hasKey("wearable") ? new  ItemStack(compound.getCompoundTag("wearable")) : ItemStack.EMPTY);
            setCampFire(new BlockPos(compound.getInteger("campFireX"), compound.getInteger("campFireY"), compound.getInteger("campFireZ")));
            dimension = compound.getInteger("campFireDim");
            forceCampFire = compound.getBoolean("forceCampFire");
        }
    }

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
        return wearable;
    }

    public void setCampFire(BlockPos cf)
    {
        campFire = cf;
    }

    public BlockPos getCampFire()
    {
        return campFire;
    }

    public EntityPlayer getPlayer()
    {
        return player;
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
        if (wearable.getItem() instanceof IBackWearableItem)
        {
            ((IBackWearableItem) wearable.getItem()).onEquippedUpdate(player.getEntityWorld(), player, wearable);
        }
    }

    public void executeWearableEquipProtocol()
    {
        if (wearable.getItem() instanceof IBackWearableItem)
        {
            ((IBackWearableItem) wearable.getItem()).onEquipped(player.getEntityWorld(), player, wearable);
        }
    }

    public void executeWearableUnequipProtocol()
    {
        if (wearable.getItem() instanceof IBackWearableItem)
        {
            ((IBackWearableItem) wearable.getItem()).onUnequipped(player.getEntityWorld(), player, wearable);
        }
    }
}