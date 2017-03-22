package com.darkona.adventurebackpack.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created on 05/01/2015
 *
 * @author Darkona
 */
public class ItemCrossbow extends ItemAB
{

    public ItemCrossbow()
    {
        super();
        setFull3D();
        setUnlocalizedName("clockworkCrossbow");
        setMaxStackSize(1);
    }

    /**
     * Returns true if players can use this item to affect the world (e.g. placing blocks, placing ender eyes in portal)
     * when not in creative
     */
    @Override
    public boolean canItemEditBlocks()
    {
        return false;
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        return EnumActionResult.PASS;
    }

    @Override
    public boolean isRepairable()
    {
        return super.isRepairable();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return EnumActionResult.PASS;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.BOW;
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     *
     * @param stack
     * @param world
     * @param player
     */
    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        super.onCreated(stack, world, player);

        NBTTagCompound xbowProps = new NBTTagCompound();

        xbowProps.setBoolean("Loaded", false);
        xbowProps.setShort("Magazine", (short) 0);
        xbowProps.setBoolean("Charging", false);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {

    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean current)
    {
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setByte("Shot", (byte) 0);
            stack.getTagCompound().setInteger("Reloading", 0);
        }

        if (current)
        {
            byte shot = stack.getTagCompound().getByte("Shot");
            int reloading = stack.getTagCompound().getInteger("Reloading");
            if (shot > 0) stack.getTagCompound().setByte("Shot", (byte) (shot - 1));
            if (reloading > 0) stack.getTagCompound().setInteger("Reloading", reloading - 1);
            if (entity instanceof EntityPlayer)
            {
                //((EntityPlayer)entity).setItemInUse(stack,2);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        if (!stack.getTagCompound().hasKey("Reloading")) stack.getTagCompound().setInteger("Reloading", 0);
        int reloading = stack.getTagCompound().getInteger("Reloading");
        if (reloading <= 0)
        {
            shootArrow(stack, player.world, player, 1000);
            stack.getTagCompound().setByte("Shot", (byte) 4);
            int reloadTime = 20;
            stack.getTagCompound().setInteger("Reloading", reloadTime);

        }
        return new ActionResult(EnumActionResult.PASS, stack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
    }

    public void shootArrow(ItemStack stack, World world, EntityPlayer player, int count)
    {
        int j = count;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

        if (flag || player.inventory.hasItemStack(new ItemStack(Items.ARROW)))
        {
            float f = j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if (f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityTippedArrow(world, player, f * 3.0F);
            f = 1.0f;

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            world.playSoundAtEntity(player, "adventurebackpack:crossbowshot", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            } else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }
}
