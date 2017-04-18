package com.darkona.adventurebackpack.capablities.player;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.concurrent.Callable;

import com.darkona.adventurebackpack.capabilities.BackpacksCapabilities;
import com.darkona.adventurebackpack.reference.ModInfo;

public class PlayerWearingBackpackCapabilities implements ICapabilitySerializable<NBTTagCompound> {

    public static final String CAP_PACK_TAG = ModInfo.MOD_ID;

    private ItemStack equippedBackpack;
    private ItemStack currentBackpack;

    public PlayerWearingBackpackCapabilities() {
        this.equippedBackpack = null;
        this.currentBackpack = null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return BackpacksCapabilities.WEARING_BACKPACK_CAPABILITY != null && capability == BackpacksCapabilities.WEARING_BACKPACK_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return BackpacksCapabilities.WEARING_BACKPACK_CAPABILITY != null && capability == BackpacksCapabilities.WEARING_BACKPACK_CAPABILITY ? (T)this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        //make new list
        NBTTagList tagList = new NBTTagList();

        //make new compound for the equipped pack
        NBTTagCompound equipped = new NBTTagCompound();
        ItemStack equippedBackpack = getEquippedBackpack();
        if (equippedBackpack != null) {
            equippedBackpack.writeToNBT(equipped);
        }else{
            equipped.setBoolean("noEquipped", false);
        }
        tagList.appendTag(equipped);

        //make another for the saved one
        NBTTagCompound current = new NBTTagCompound();
        ItemStack currentBackpack = getCurrentBackpack();
        if (currentBackpack != null) {
            currentBackpack.writeToNBT(current);
        }else{
            current.setBoolean("noCurrent", false);
        }
        tagList.appendTag(current);

        //save all to the tag
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag(CAP_PACK_TAG, tagList);

        //return compound
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {

        NBTTagList tagList = compound.getTagList(CAP_PACK_TAG, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);

        //get the equipped backpack without crashing
        if (!tagList.getCompoundTagAt(0).hasKey("noEquipped")){ //if the key doesn't exist
            try {
                setEquippedBackpack(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0)));
            } catch (NullPointerException e) { //might as well keep this catch statement
                setEquippedBackpack(null);
            }
        } else {
            setEquippedBackpack(null);
        }

        //get the current backpack without crashing
        if (!tagList.getCompoundTagAt(1).hasKey("noCurrent")) {
            try {
                setCurrentBackpack(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(1)));
            } catch (NullPointerException e) {
                setCurrentBackpack(null);
            }
        } else {
            setCurrentBackpack(null);
        }

    }

    // Not sure what this does honestly
    public static class Storage implements Capability.IStorage<PlayerWearingBackpackCapabilities> {

        @Override
        public NBTBase writeNBT(Capability<PlayerWearingBackpackCapabilities> capability, PlayerWearingBackpackCapabilities instance, EnumFacing side) {
           return null; //unused?
        }

        @Override
        public void readNBT(Capability<PlayerWearingBackpackCapabilities> capability, PlayerWearingBackpackCapabilities instance, EnumFacing side, NBTBase nbt) {
            //empty
        }

    }

    // Empty factory, just implemented here for ease of future expansion
    public static class Factory implements Callable<PlayerWearingBackpackCapabilities> {
        @Override
        public PlayerWearingBackpackCapabilities call() throws Exception {
            return null;
        }
    }


    //Getters and setters

    public ItemStack getEquippedBackpack() {
        return equippedBackpack;
    }

    public void setEquippedBackpack(ItemStack stack) {
        this.equippedBackpack = stack;
    }

    public ItemStack getCurrentBackpack() {
        return currentBackpack;
    }

    public void setCurrentBackpack(ItemStack stack) {
        this.currentBackpack = stack;
    }

    //Other helper methods

    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerWearingBackpackCapabilities.class, new PlayerWearingBackpackCapabilities.Storage(), new PlayerWearingBackpackCapabilities.Factory());
    }

    //Static methods

    public static ItemStack getEquippedBackpack(EntityLivingBase livingBase) {
        PlayerWearingBackpackCapabilities cap = BackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null) //can this ever be null?
            return cap.getEquippedBackpack();
        else
            return null;
    }

    public static void setEquippedBackpack(EntityLivingBase livingBase, ItemStack stack) {
        PlayerWearingBackpackCapabilities cap = BackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null)
            cap.setEquippedBackpack(stack);
    }

    public static ItemStack getCurrentBackpack(EntityLivingBase livingBase) {
        PlayerWearingBackpackCapabilities cap = BackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null)
            return cap.getCurrentBackpack();
        else
            return null;
    }

    public static void setCurrentBackpack(EntityLivingBase livingBase, ItemStack stack) {
        PlayerWearingBackpackCapabilities cap = BackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null)
            cap.setCurrentBackpack(stack);
    }

    public static void reset(EntityLivingBase livingBase) {
        setCurrentBackpack(livingBase, null);
        setEquippedBackpack(livingBase, null);
    }
}