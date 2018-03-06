package com.darkona.adventurebackpack.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModFluids;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 19/10/2014
 *
 * @author Darkona
 */
public class ItemJuiceBottle extends ItemAB
{
    public ItemJuiceBottle()
    {
        super();
        setCreativeTab(CreativeTabAB.TAB_AB);
        setFull3D();
        setUnlocalizedName("melonJuiceBottle");
        this.setRegistryName(ModInfo.MOD_ID, "melon_juice_bottle");
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
    {
        FluidEffectRegistry.executeFluidEffectsForFluid(ModFluids.melonJuice, entity, world);
        return new ItemStack(Items.GLASS_BOTTLE, 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 28;
    }

}
