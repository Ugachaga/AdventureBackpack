package com.darkona.adventurebackpack.item;

import java.util.Set;

import com.darkona.adventurebackpack.CreativeTabAB;
import com.darkona.adventurebackpack.init.ModMaterials;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created on 10/10/2014
 *
 * @author Darkona
 */
public class ItemMachete extends ToolAB
{
    @SuppressWarnings("rawtypes")
	private static final Set breakableBlocks = Sets.newHashSet(Blocks.PUMPKIN,
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

    @SuppressWarnings("unused")
    private float field_150934_a;

    public ItemMachete()
    {

        super(ModMaterials.ruggedIron, breakableBlocks);
        setCreativeTab(CreativeTabAB.ADVENTURE_BACKPACK_CREATIVE_TAB);
        setMaxDamage(Items.IRON_SWORD.getMaxDamage() + 250);
        this.field_150934_a = ModMaterials.ruggedIron.getDamageVsEntity();
        this.setUnlocalizedName("machete");
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        if (material == Material.WOOD) return 4F;
        if (state.getBlock() == Blocks.MELON_BLOCK) return 2F;
        if (state.getBlock() == Blocks.WEB) return 10F;
        for (ItemStack stacky : OreDictionary.getOres("treeLeaves"))
        {
            if (stacky.getItem() == Item.getItemFromBlock(state.getBlock())) return 15F;
        }
        return (material == Material.PLANTS || material == Material.VINE || material == Material.CORAL || material == Material.GOURD || material == Material.LEAVES || material == Material.CLOTH) ? 12.0F : 0.5F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     * TODO: clean up var names
     */
    @Override
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        p_77644_1_.damageItem(1, p_77644_3_);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLivingBase)
    {
        if (state.getBlock() != Blocks.VINE && !(state.getBlock() instanceof IShearable))
        {
            return super.onBlockDestroyed(stack, world, state, pos, entityLivingBase);
        } else
        {
            return true;
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        /*
        if (player.worldObj.isRemote)
        {
            return false;
        }
        int id = player.worldObj.getBlockId(x, y, z);
        for(ItemStack stacky : OreDictionary.getOres("treeLeaves")){
        	if (stacky.itemID == id) return false;
        }

        if (Block.blocksList[id] instanceof IShearable )
        {
            IShearable target = (IShearable)Block.blocksList[id];
            if (target.isShearable(itemstack, player.worldObj, x, y, z))
            {
                ArrayList<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z,
                		EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                Random rand = new Random();

                for(ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d  = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d2 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(player.worldObj, (double)x + d, (double)y + d1, (double)z + d2, stack);
                    entityitem.delayBeforeCanPickup = 10;
                    player.worldObj.spawnEntityInWorld(entityitem);
                }

                itemstack.damageItem(1, player);
                player.addStat(StatList.mineBlockStatArray[id], 1);
            }
        }
        return false;
         */
        return new ItemStack(new ItemShears()).getItem().onBlockStartBreak(itemstack, pos, player);
    }

}
