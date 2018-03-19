package com.darkona.adventurebackpack.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.oredict.OreDictionary;

import com.darkona.adventurebackpack.init.ModItems;
import com.darkona.adventurebackpack.reference.ModInfo;

public class ItemMachete extends AdventureTool
{
    private static final Set<Block> BREAKABLE_BLOCKS = Sets.newHashSet(
            Blocks.BROWN_MUSHROOM,
            Blocks.BROWN_MUSHROOM_BLOCK,
            Blocks.CACTUS,
            Blocks.CARROTS,
            Blocks.COCOA,
            Blocks.GRASS,
            Blocks.HAY_BLOCK,
            Blocks.LEAVES,
            Blocks.LEAVES2,
            Blocks.MELON_BLOCK,
            Blocks.MELON_STEM,
            Blocks.POTATOES,
            Blocks.PUMPKIN,
            Blocks.RED_FLOWER,
            Blocks.RED_MUSHROOM,
            Blocks.RED_MUSHROOM_BLOCK,
            Blocks.REEDS,
            Blocks.TALLGRASS,
            Blocks.WATERLILY,
            Blocks.WEB,
            Blocks.WHEAT,
            Blocks.WOOL,
            Blocks.YELLOW_FLOWER
    );

    public ItemMachete(String name)
    {
        super(name, ModItems.Materials.TOOL_RUGGED_IRON, BREAKABLE_BLOCKS);

        this.setMaxDamage(Items.IRON_SWORD.getMaxDamage() + 250);
        this.setCreativeTab(ModInfo.CREATIVE_TAB);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        if (material == Material.WOOD) return 4F;
        if (state.getBlock() == Blocks.MELON_BLOCK) return 2F;
        if (material == Material.WEB) return 10F;
        for (ItemStack stacky : OreDictionary.getOres("treeLeaves"))
        {
            if (stacky.getItem() == Item.getItemFromBlock(state.getBlock())) return 15F;
        }
        return material == Material.PLANTS || material == Material.VINE || material == Material.CORAL
                       || material == Material.GOURD || material == Material.LEAVES
                       || material == Material.CLOTH ? 12.0F : 0.5F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        return state.getBlock() == Blocks.VINE || state.getBlock() instanceof IShearable
                || super.onBlockDestroyed(stack, world, state, pos, entityLiving);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(new ItemShears()).getItem().onBlockStartBreak(itemstack, pos, player);
    }
}
