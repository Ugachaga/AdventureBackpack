package com.darkona.adventurebackpack.item;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.darkona.adventurebackpack.entity.EntityInflatableBoat;
import com.darkona.adventurebackpack.reference.ModInfo;
import com.darkona.adventurebackpack.reference.IType;

public class ItemComponent extends AdventureItem
{
    public ItemComponent(String name)
    {
        super(name);
        this.setNoRepair();
        this.setHasSubtypes(true);
        this.setMaxStackSize(16);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return "item." + ModInfo.MODID + ":" + Types.get(stack.getMetadata()).getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        if (isInCreativeTab(tab))
        {
            List<ItemStack> items = Stream.of(Types.values())
                    .map(types -> new ItemStack(this, 1, types.getMeta()))
                    .collect(Collectors.toList());

            subItems.addAll(items);
        }
    }

    public enum Types implements IType
    {
        SLEEPING_BAG (0),
        BACKPACK_TANK (1),
        HOSE_HEAD (2),
        MACHETE_HANDLE (3),
        COPTER_ENGINE (4),
        COPTER_BLADES (5),
        INFLATABLE_BOAT (6),
        INFLATABLE_BOAT_MOTORIZED (7),
        HYDRO_BLADES (8),
        ;

        private static final Types[] BY_META = Stream.of(values())
                .sorted(Comparator.comparing(Types::getMeta))
                .toArray(Types[]::new);

        private final int meta;
        private final String name;

        Types(int meta)
        {
            this.meta = meta;
            this.name = name().toLowerCase();
        }

        @Override
        public int getMeta()
        {
            return meta;
        }

        @Override
        public String getName()
        {
            return name;
        }

        public static Types get(int meta)
        {
            if (meta < 0 || meta >= BY_META.length)
                meta = 0;

            return BY_META[meta];
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() == 7)
            return new ActionResult(EnumActionResult.PASS, placeBoat(stack, world, player, false));
        if (stack.getItemDamage() == 8)
            return new ActionResult(EnumActionResult.PASS, placeBoat(stack, world, player, false));
        return new ActionResult(EnumActionResult.PASS, stack);
    }


    //TODO seems just vanilla ItemBoat code. boat should extend vanilla one and registered outside components.
    private ItemStack placeBoat(ItemStack stack, World world, EntityPlayer player, boolean motorized)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f + 1.62D - (double) player.renderOffsetY;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3d vec3d1 = vec3d.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        RayTraceResult traceResult = world.rayTraceBlocks(vec3d, vec3d1, true);

        if (traceResult == null)
        {
            return stack;
        }
        else
        {
            Vec3d vec3d2 = player.getLook(f);
            boolean flag = false;
            float f9 = 1.0F;
            List list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(vec3d2.x * d3, vec3d2.y * d3, vec3d2.z * d3).expand((double) f9, (double) f9, (double) f9));
            int i;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity) list.get(i);

                if (entity.canBeCollidedWith())
                {
                    float f10 = entity.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double) f10, (double) f10, (double) f10);

                    if (axisalignedbb.contains(vec3d))
                    {
                        flag = true;
                    }
                }
            }

            if (flag)
            {
                return stack;
            }
            else
            {
                if (traceResult.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    i = traceResult.getBlockPos().getX();
                    int j = traceResult.getBlockPos().getY();
                    int k = traceResult.getBlockPos().getZ();

                    if (world.getBlockState(new BlockPos(i, j, k)) == Blocks.SNOW_LAYER)
                    {
                        --j;
                    }

                    EntityInflatableBoat inflatableBoat = new EntityInflatableBoat(world, i + 0.5, j + 1.0, k + 0.5, motorized);

                    inflatableBoat.rotationYaw = (float) (((MathHelper.floor((player.rotationYaw * 4.0 / 360.0) + 0.5D) & 3) - 1) * 90);
                    if (!world.getCollisionBoxes(inflatableBoat, inflatableBoat.getEntityBoundingBox().expand(-0.1, -0.1, -0.1)).isEmpty())
                    {
                        return stack;
                    }

                    if (!world.isRemote)
                    {
                        world.spawnEntity(inflatableBoat);
                    }

                    if (!player.capabilities.isCreativeMode)
                    {
                        stack.shrink(1);
                    }
                }
                return stack;
            }
        }
    }
}