package com.darkona.adventurebackpack.item;

import java.util.Set;

import com.google.common.collect.Sets;

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

import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.init.ModMaterials;
import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 10/10/2014
 *
 * @author Darkona
 */
public class ItemMachete extends ToolAB
{
    private static final Set BREAKABLE_BLOCKS = Sets.newHashSet(
            Blocks.PUMPKIN,
            Blocks.WEB,
            Blocks.LEAVES,
            Blocks.LEAVES2,
            Blocks.MELON_BLOCK,
            Blocks.MELON_STEM,
            Blocks.BROWN_MUSHROOM,
            Blocks.RED_FLOWER,
            Blocks.RED_MUSHROOM,
            Blocks.CACTUS,
            Blocks.COCOA,
            Blocks.HAY_BLOCK,
            Blocks.CARROTS,
            Blocks.POTATOES,
            Blocks.RED_MUSHROOM_BLOCK,
            Blocks.BROWN_MUSHROOM_BLOCK,
            Blocks.REEDS,
            Blocks.GRASS,
            Blocks.TALLGRASS,
            Blocks.YELLOW_FLOWER,
            Blocks.WATERLILY,
            Blocks.WHEAT,
            Blocks.WOOL);

    @SuppressWarnings("FieldCanBeLocal")
    private float field_150934_a;

    public ItemMachete()
    {
        super(ModMaterials.ruggedIron, BREAKABLE_BLOCKS);
        setCreativeTab(CreativeTabAB.TAB_AB);
        setMaxDamage(Items.IRON_SWORD.getMaxDamage() + 250);
        this.field_150934_a = ModMaterials.ruggedIron.getAttackDamage();
        this.setUnlocalizedName("machete");
        this.setRegistryName(ModInfo.MOD_ID, "machete");
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
