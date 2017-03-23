package com.darkona.adventurebackpack.item;

import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.fluids.FluidEffectRegistry;
import com.darkona.adventurebackpack.init.ModFluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

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
        setCreativeTab(CreativeTabAB.ADVENTURE_BACKPACK_CREATIVE_TAB);
        setFull3D();
        setUnlocalizedName("melonJuiceBottle");
        setMaxStackSize(1);
    }

    //@Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        FluidEffectRegistry.executeFluidEffectsForFluid(ModFluids.melonJuice, player, world);
        return new ItemStack(Items.GLASS_BOTTLE, 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        return EnumActionResult.PASS;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 28;
    }

}
