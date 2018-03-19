package com.darkona.adventurebackpack.common;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.darkona.adventurebackpack.block.TileBackpack;
import com.darkona.adventurebackpack.config.ConfigHandler;
import com.darkona.adventurebackpack.entity.ai.EntityAIAvoidPlayerWithBackpack;
import com.darkona.adventurebackpack.init.ModFluids;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.network.messages.EntityParticlePacket;
import com.darkona.adventurebackpack.reference.BackpackTypes;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Utils;
import com.darkona.adventurebackpack.util.Wearing;

/**
 * @see com.darkona.adventurebackpack.block.BlockBackpack
 * @see com.darkona.adventurebackpack.block.TileBackpack
 * @see com.darkona.adventurebackpack.item.ItemBackpack
 */
@SuppressWarnings("unused")
public class BackpackAbilities
{
    public static BackpackAbilities backpackAbilities = new BackpackAbilities();
    public static BackpackRemovals backpackRemovals = new BackpackRemovals();

    /**
     * Executes the ability of any given backpack, be it on the ground or be it on a player.
     */
    public void executeAbility(EntityPlayer player, World world, ItemStack backpack)
    {
        String skinName = BackpackTypes.getSkinName(backpack); //TODO capitalize or rename methods
        try
        {
            //This is black magic and shouldn't be attempted by the faint of heart.
            this.getClass()
                    .getMethod("item" + skinName, EntityPlayer.class, World.class, ItemStack.class)
                    .invoke(backpackAbilities, player, world, backpack);
        }
        catch (Exception oops)
        {
            //NOBODY CARES
        }
    }

    public void executeTileAbility(World world, TileBackpack backpack)
    {
        String skinName = backpack.getType().getName();
        try
        {
            /*
            This is witchery, witchery I say!
            But seriously, if you want to know how this works just pay very close attention:
            invoke will execute any method of a given class, okay? so this should be obvious.
            Look at the names of the methods in this class and you'll figure it out.
            You have to indicate exactly the classes that the method should use as parameters so
            be very careful with "getMethod".
            */
            this.getClass()
                    .getMethod("tile" + skinName, World.class, TileBackpack.class)
                    .invoke(backpackAbilities, world, backpack);
        }
        catch (Exception oops)
        {
            //Seriously, nobody cares if this can't work, this is just so the game won't explode.
        }
    }

    public void executeRemoval(EntityPlayer player, World world, ItemStack backpack)
    {
        String skinName = BackpackTypes.getSkinName(backpack);
        try
        {
            //This is black magic and shouldn't be attempted by the faint of heart.
            backpackRemovals.getClass()
                    .getMethod("item" + skinName, EntityPlayer.class, World.class, ItemStack.class)
                    .invoke(backpackRemovals, player, world, backpack);
        }
        catch (Exception oops)
        {
            LogHelper.error("---Something bad happened when removing a backpack---");
            oops.printStackTrace();
        }
    }

    /**
     * Detects if a player is under the rain. For detecting when it is Under The Sea (maybe to sing a nice Disney tune)
     * it won't work, there's a different method for that, isInWater
     *
     * @param player The player
     * @return True if the player is outside and it's raining.
     */
    private boolean isUnderRain(EntityPlayer player)
    {
        return player.world.isRaining()
                && (player.world.canSeeSky(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY), MathHelper.floor(player.posZ)))
                    || player.world.canSeeSky(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY + player.height), MathHelper.floor(player.posZ))));
    }

    /**
     * This backpack will feed you while you stay in the sun, slowly. At the very least you shouldn't starve.
     */
    public void itemSunflower(EntityPlayer player, World world, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);

        if (inv.getLastTime() <= 0)
        {
            if (world.isDaytime()
                    && world.canSeeSky(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY + 1), MathHelper.floor(player.posZ))))
            {
                player.getFoodStats().addStats(2, 0.2f);
                //LogHelper.info("OMNOMNOMNOM");
            }
            inv.setLastTime(Utils.secondsToTicks(120));
        }
        else
        {
            inv.setLastTime(inv.getLastTime() - 1);
        }
        inv.dirtyTime();
    }

    /**
     * Nana nana nana nana Bat - Batpack! See in the dark!
     */
    public void itemBat(EntityPlayer player, World world, ItemStack backpack)
    {
        PotionEffect nightVision = null;

        if (player.isPotionActive(MobEffects.NIGHT_VISION))
        {
            nightVision = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
        }
        if ((nightVision == null || nightVision.getDuration() < 222) && !Wearing.getWearingBackpackInv(player).getDisableNVision())
        {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 239, -1, false, false));
        }
        else if (nightVision != null && Wearing.getWearingBackpackInv(player).getDisableNVision())
        {
            backpackRemovals.itemBat(player, world, backpack);
        }
    }

    public void itemSquid(EntityPlayer player, World world, ItemStack backpack)
    {
        if (player.isInsideOfMaterial(Material.WATER))
        {
            player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 19, -1, false, false));
            itemBat(player, world, backpack);
        }
        else if (player.isPotionActive(MobEffects.WATER_BREATHING) && player.getActivePotionEffect(MobEffects.WATER_BREATHING).getAmplifier() == -1)
        {
            backpackRemovals.itemSquid(player, world, backpack);
        }
    }

    public void itemPigman(EntityPlayer player, World world, ItemStack backpack)
    {
        PotionEffect potion = null;

        if (player.isPotionActive(MobEffects.FIRE_RESISTANCE))
        {
            potion = player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE);
        }
        if (potion == null || potion.getDuration() < 222)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 239, -1, false, false));
        }
    }

    /**
     * The Dragon Backpack does something awesome.
     */
    public void itemDragon(EntityPlayer player, World world, ItemStack backpack)
    {
        itemBat(player, world, backpack);
        itemPigman(player, world, backpack);

        PotionEffect potion = null;

        if (ConfigHandler.dragonBackpackRegen != 0)
        {
            if (player.isPotionActive(MobEffects.REGENERATION))
            {
                potion = player.getActivePotionEffect(MobEffects.REGENERATION);
            }
            if (player.getHealth() < player.getMaxHealth())
            {
                if (potion == null || potion.getDuration() < 20)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, ConfigHandler.dragonBackpackRegen - 1, false, false));
                }
            }
            else if (potion != null && potion.getAmplifier() == ConfigHandler.dragonBackpackRegen - 1)
            {
                player.removePotionEffect(MobEffects.REGENERATION);
            }
        }

        if (ConfigHandler.dragonBackpackDamage != 0)
        {
            if (player.isPotionActive(MobEffects.STRENGTH))
            {
                potion = player.getActivePotionEffect(MobEffects.STRENGTH);
            }
            if (potion == null || potion.getDuration() < 222)
            {
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 239, ConfigHandler.dragonBackpackDamage - 1, false, false));
            }
        }
    }

    public void itemRainbow(EntityPlayer player, World world, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int noteTime = inv.getLastTime() - 1;

        if (noteTime >= 0 && noteTime < Utils.secondsToTicks(147))
        {
            //player.setSprinting(true);
            if (ConfigHandler.rainbowBackpackSSpeed != 0)
            {
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, ConfigHandler.rainbowBackpackSSpeed - 1, false, false));
            }
            if (ConfigHandler.rainbowBackpackSJump != 0)
            {
                player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60, ConfigHandler.rainbowBackpackSJump - 1, false, false));
            }
            if (noteTime % 2 == 0)
            {
                //Visuals.NyanParticles(player, world);
                if (!world.isRemote)
                {
                    ModNetwork.sendToNearby(new EntityParticlePacket.Message(EntityParticlePacket.NYAN_PARTICLE, player), player);
                }
            }
        }

        PotionEffect moveSpeed = null;

        if (player.isPotionActive(MobEffects.SPEED))
        {
            moveSpeed = player.getActivePotionEffect(MobEffects.SPEED);
        }
        if (ConfigHandler.rainbowBackpackSpeed != 0 && (moveSpeed == null || moveSpeed.getDuration() < 222))
        {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 239, ConfigHandler.rainbowBackpackSpeed - 1, false, false));
        }
        inv.setLastTime(noteTime);
        inv.markDirty();
    }

    public void itemIronGolem(EntityPlayer player, World world, ItemStack backpack)
    {

    }

    /**
     * Mirroring real life cactii, the Cactus Backpack fills with water slowly or rapidly depending where is the player.
     * If it's raining it will fill 1milibucket of water each tick.
     * If the player is in water it will fill 2milibuckets of water each tick.
     * The quantities can be combined.
     *
     * @param player   The player. No, seriously.
     * @param world    The world the player is in.
     * @param backpack The backpack the player is wearing. This should be rechecked like 20 times by now, so
     *                 I'm not checking.
     */
    public void itemCactus(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime is in ticks for this backpack.
        if (world.isRemote) return;
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int drops = 0;
        if (player.isInWater())
        {
            drops += 2;
        }
        if (isUnderRain(player))
        {
            drops += 1;
        }

        if (inv.getLastTime() <= 0 && drops > 0)
        {
            inv.setLastTime(5);
            FluidStack raindrop = new FluidStack(FluidRegistry.WATER, drops);
            inv.getLeftTank().fill(raindrop, true);
            inv.getRightTank().fill(raindrop, true);
        }
        else
        {
            inv.setLastTime(inv.getLastTime() - 1);
        }
        inv.dirtyTime();
        inv.dirtyTanks();
    }

    /**
     * The Pig Backpack will annoy you and your friends! This beautiful design by 豚, will do as the pigs do when they
     * are frolicking around in the green pastures and terrifying slaughterhouses of the Minecraft world, after a random
     * number of seconds. It's not so frequent as I'd like.
     * Translation for pigs: Oink oink oink Oink! squee oink oink Minecraft Oink oink. "Oink" oink oink.
     *
     * @param player   The player
     * @param world    The world object
     * @param backpack The backpack the player is wearing.
     */
    public void itemPig(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime is in seconds for this backpack.
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int oinkTime = inv.getLastTime() - 1;
        if (oinkTime <= 0)
        {
            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PIG_AMBIENT, SoundCategory.BLOCKS, 0.8f, 1f);
            oinkTime = Utils.secondsToTicks(world.rand.nextInt(61));
        }
        inv.setLastTime(oinkTime);
        inv.dirtyTime();
    }

    /**
     * Squishy! The Slime Backpack has an incredibly useless "ability". Makes the player leave a slimy trail of
     * particles whenever he or she is running, and make that splishy splashy squishy sound on each step as well!.
     */
    public void itemSlime(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime is in Ticks for this backpack.
        //0 is Full Moon, 1 is Waning Gibbous, 2 is Last Quarter, 3 is Waning Crescent,
        // 4 is New Moon, 5 is Waxing Crescent, 6 is First Quarter and 7 is Waxing Gibbous
        if (world.getMoonPhase() == 0 && !world.isDaytime())
        {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 0, false, false));
        }
        if (player.onGround)
        {

            if ((player.moveForward == 0 && player.moveStrafing == 0) && (Math.abs(player.moveForward) < 3 && Math.abs(player.moveStrafing) < 3))
            {
                player.addVelocity(player.motionX *= 0.828, 0, player.motionZ *= 0.828);
            }
            if (player.isSprinting())
            {
                InventoryBackpack inv = new InventoryBackpack(backpack);
                int slimeTime = inv.getLastTime() > 0 ? inv.getLastTime() - 1 : 5;
                if (slimeTime <= 0)
                {
                    if (!world.isRemote)
                    {
                        ModNetwork.sendToNearby(new EntityParticlePacket.Message(EntityParticlePacket.SLIME_PARTICLE, player), player);
                    }
                    world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 0.6F, (world.rand.nextFloat() - world.rand.nextFloat()) * 1F);
                    slimeTime = 5;
                }
                inv.setLastTime(slimeTime);
                inv.dirtyTime();
            }
        }
    }

    /**
     * The Chicken Backpack will go and *plop* an egg for you randomly each so many seconds. It's very rare though.
     */
    public void itemChicken(EntityPlayer player, World world, ItemStack backpack)
    {
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int eggTime = inv.getLastTime() - 1;
        if (eggTime <= 0)
        {
            player.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F + 1.0F);
            if (!world.isRemote) player.dropItem(Items.EGG, 1);
            eggTime = Utils.secondsToTicks(200 + 10 * world.rand.nextInt(10));
        }
        inv.setLastTime(eggTime);
        inv.dirtyTime();
    }

    /**
     * The Melon Backpack, like his cousin the Cactus Backpack, will fill itself, but with delicious
     * and refreshing Melon Juice, if the backpack is wet in any way.
     */
    public void itemMelon(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime is in ticks for this backpack.
        if (world.isRemote) return;
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int drops = 0;
        if (player.isInWater())
        {
            drops += 2;
        }
        if (isUnderRain(player))
        {
            drops += 1;
        }

        if (inv.getLastTime() <= 0 && drops > 0)
        {
            inv.setLastTime(5);
            FluidStack raindrop = new FluidStack(ModFluids.melonJuice, drops);
            inv.getLeftTank().fill(raindrop, true);
            inv.getRightTank().fill(raindrop, true);
        }
        else
        {
            inv.setLastTime(inv.getLastTime() - 1);
        }
        inv.dirtyTime();
        inv.dirtyTanks();
    }

    /**
     * Sneaky! Scare your friends! Or your enemies!
     * Sneak on another player to make them jump in confusion as they think one of those green bastards is behind him/her.
     * You can only do it once every so often. A couple of minutes. Remember, you must be sneaking.
     *
     * @see com.darkona.adventurebackpack.handlers.PlayerEventHandler
     */
    @SuppressWarnings("unchecked")
    public void itemCreeper(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime is in seconds for this ability
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int pssstTime = inv.getLastTime() - 1;

        if (pssstTime <= 0)
        {
            pssstTime = 20;
            if (player.isSneaking())
            {
                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player,
                        new AxisAlignedBB(player.posX, player.posY, player.posZ,
                                player.posX + 1.0D,player.posY + 1.0D, player.posZ + 1.0D)
                                .expand(5.0D, 1.0D, 5.0D));

                if (entities.isEmpty())
                {
                    pssstTime -= 1;
                    return;
                }

                for (Entity entity : entities)
                {
                    if (entity instanceof EntityPlayer)
                    {
                        if (player.getDistance(entity) <= 3)
                        {
                            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1.2F, 0.5F);
                            pssstTime = Utils.secondsToTicks(120);
                        }
                    }
                }
            }
        }
        inv.setLastTime(pssstTime);
        inv.markDirty();
    }

    private FluidStack milkStack = new FluidStack(FluidRegistry.getFluid("milk"), 1);
    private FluidStack soupStack = new FluidStack(FluidRegistry.getFluid("mushroomstew"), 1);
    private FluidStack lavaStack = new FluidStack(FluidRegistry.LAVA, 1);

    /**
     * The Cow Backpack fills itself with milk when there is wheat in the backpack's inventory, but it will do so slowly
     * and will eat the wheat. It's like having a cow in your backpack. Each 16 wheat makes a bucket. It only happens
     * when it is being worn. For not-player related milk generation go get a cow. Moo!
     */
    public void itemCow(EntityPlayer player, World world, ItemStack backpack)
    {
        if (world.isRemote) return; //TODO not syncing properly with client if GUI is open
        InventoryBackpack inv = new InventoryBackpack(backpack);
        inv.openInventory(player);

        if (inv.getLeftTank().fill(milkStack, false) <= 0 && inv.getRightTank().fill(milkStack, false) <= 0)
        {
            return;
        }
        //Set Cow Properties
        int wheatConsumed = 0;
        int milkTime = -1;
        if (inv.getExtendedProperties() != null)
        {
            if (inv.getExtendedProperties().hasKey("wheatConsumed"))
            {
                wheatConsumed = inv.getExtendedProperties().getInteger("wheatConsumed");
                milkTime = inv.getExtendedProperties().getInteger("milkTime") - 1;
            }
        }

        int eatTime = (inv.getLastTime() - 1 >= 0) ? inv.getLastTime() - 1 : 0;
        if (inv.hasItem(Items.WHEAT) && eatTime <= 0 && milkTime <= 0)
        {
            eatTime = 20;
            //LogHelper.info("Consuming Wheat in " + ((world.isRemote) ? "Client" : "Server"));
            inv.consumeInventoryItem(Items.WHEAT);
            wheatConsumed++;
            inv.dirtyInventory();
        }

        int factor = 1;
        if (wheatConsumed == 16)
        {
            wheatConsumed = 0;
            milkTime = (1000 * factor) - factor;
            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_COW_AMBIENT, SoundCategory.NEUTRAL, 1F, 1F);
        }

        if (milkTime >= 0 && (milkTime % factor == 0))
        {
            if (inv.getLeftTank().fill(milkStack, true) <= 0)
            {
                inv.getRightTank().fill(milkStack, true);
            }
            inv.dirtyTanks();
        }
        if (milkTime < -1) milkTime = -1;
        inv.getExtendedProperties().setInteger("wheatConsumed", wheatConsumed);
        inv.getExtendedProperties().setInteger("milkTime", milkTime);
        inv.setLastTime(eatTime);
        //inv.setLastTime(eatTime);
        inv.dirtyExtended();
        //inv.dirtyTanks();
        inv.dirtyTime();
        //inv.dirtyInventory();

        //So naughty!!!
    }

    public void itemMooshroom(EntityPlayer player, World world, ItemStack backpack)
    {
        if (world.isRemote) return;
        InventoryBackpack inv = new InventoryBackpack(backpack);
        inv.openInventory(player);

        if (inv.getLeftTank().fill(soupStack, false) <= 0 && inv.getRightTank().fill(soupStack, false) <= 0)
        {
            return;
        }
        //Set Cow Properties
        int wheatConsumed = 0;
        int milkTime = -1;
        if (inv.getExtendedProperties() != null)
        {
            if (inv.getExtendedProperties().hasKey("wheatConsumed"))
            {
                wheatConsumed = inv.getExtendedProperties().getInteger("wheatConsumed");
                milkTime = inv.getExtendedProperties().getInteger("milkTime") - 1;
            }
        }

        int eatTime = (inv.getLastTime() - 1 >= 0) ? inv.getLastTime() - 1 : 0;
        if (inv.hasItem(Items.WHEAT) && eatTime <= 0 && milkTime <= 0)
        {
            eatTime = 20;
            //LogHelper.info("Consuming Wheat in " + ((world.isRemote) ? "Client" : "Server"));
            inv.consumeInventoryItem(Items.WHEAT);
            wheatConsumed++;
            inv.dirtyInventory();
        }

        int factor = 1;
        if (wheatConsumed == 16)
        {
            wheatConsumed = 0;
            milkTime = (1000 * factor) - factor;
            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_COW_AMBIENT, SoundCategory.NEUTRAL, 1F, 1F);
        }

        if (milkTime >= 0 && (milkTime % factor == 0))
        {
            if (inv.getLeftTank().fill(soupStack, true) <= 0)
            {
                inv.getRightTank().fill(soupStack, true);
            }
            inv.dirtyTanks();
        }
        if (milkTime < -1) milkTime = -1;
        inv.getExtendedProperties().setInteger("wheatConsumed", wheatConsumed);
        inv.getExtendedProperties().setInteger("milkTime", milkTime);
        inv.setLastTime(eatTime);
        //inv.setLastTime(eatTime);
        inv.dirtyExtended();
        //inv.dirtyTanks();
        inv.dirtyTime();
        //inv.dirtyInventory();

        //So naughty!!!
    }

    /**
     * The Wolf Backpack is a handy one if you're out in the wild. It checks around for any wolves that may lurk around.
     * If any of them gets mad at you, it will smell the scent of it's kin on you and promptly forget about the whole
     * deal. Smelling like dog is awesome.
     */
    @SuppressWarnings("unchecked")
    public void itemWolf(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime is in Ticks for this backpack
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int lastCheckTime = inv.getLastTime() - 1;

        if (lastCheckTime <= 0)
        {
            lastCheckTime = 20;
            List<EntityWolf> wolves = world.getEntitiesWithinAABB
                    (EntityWolf.class, new AxisAlignedBB(player.posX, player.posY, player.posZ,
                            player.posX + 1.0D, player.posY + 1.0D, player.posZ + 1.0D)
                            .expand(16.0D, 4.0D, 16.0D));

            if (wolves.isEmpty()) return;

            for (EntityWolf wolf : wolves)
            {
                if (wolf.isAngry() && wolf.getAttackTarget() == player)
                {
                    wolf.setAngry(false);
                    wolf.setAttackTarget(null);
                    wolf.setRevengeTarget(null);
                    Iterator<?> i2 = wolf.targetTasks.taskEntries.iterator();
                    while (i2.hasNext())
                    {
                        ((EntityAIBase) i2.next()).resetTask();
                    }
                }
            }
        }
        inv.setLastTime(lastCheckTime);
        inv.dirtyTime();
    }

    /**
     * The Blaze Backpack will make you inmune to fire and lava and burning and heat and... not really. You're supposed
     * to die a fiery death if you are not careful, but this backpack will protect you against those burning fire
     * elemental inhabitants of the Nether. Any blast of fire directed your way will be stopped, deflected or whatever.
     */
    public void itemBlaze(EntityPlayer player, World world, ItemStack backpack)
    {

    }

    /**
     * Like actual Ocelots and Cats, the Ocelot Backpack will scare the hell out of Creepers, so they won't creep on you
     * while you're busy doing something else, paying no attention whatsoever at your surroundings like a mindless chicken.
     */
    @SuppressWarnings("unchecked")
    public void itemOcelot(EntityPlayer player, World world, ItemStack backpack)
    {
        //lastTime in this backpack is in Ticks.
        InventoryBackpack inv = new InventoryBackpack(backpack);
        int lastCheckTime = inv.getLastTime() - 1;
        if (lastCheckTime <= 0)
        {
            lastCheckTime = 20;
            List<EntityCreeper> creepers = player.world.getEntitiesWithinAABB
                    (EntityCreeper.class, new AxisAlignedBB(player.posX, player.posY, player.posZ,
                            player.posX + 1.0D, player.posY + 1.0D, player.posZ + 1.0D)
                            .expand(16.0D, 4.0D, 16.0D));

            for (EntityCreeper creeper : creepers)
            {
                boolean set = true;
                EntityAIAvoidPlayerWithBackpack task = new EntityAIAvoidPlayerWithBackpack(creeper, EntityPlayer.class, 10.0F, 1.0, 1.3, BackpackTypes.OCELOT);
                for (Object entry : creeper.tasks.taskEntries)
                {
                    if (((EntityAITasks.EntityAITaskEntry) entry).action instanceof EntityAIAvoidPlayerWithBackpack)
                    {
                        set = false;
                        break;
                    }
                }

                if (set)
                {
                    //System.out.println("Found creeper who doesn't know to fear the backpack, making it a pussy now");
                    creeper.tasks.addTask(3, task);
                }
            }
        }
        inv.setLastTime(lastCheckTime);
        inv.markDirty();
    }

    /* ==================================== TILE ABILITIES ==========================================*/

    private void fillWithRain(World world, TileBackpack backpack, FluidStack fluid, int time)
    {
        if (world.isRaining() && world.canSeeSky(new BlockPos(backpack.getPos())))
        {
            int dropTime = backpack.getLastTime() - 1;
            if (dropTime <= 0)
            {
                backpack.getRightTank().fill(fluid, true);
                backpack.getLeftTank().fill(fluid, true);
                dropTime = time;
                backpack.markDirty();
            }
            backpack.setLastTime(dropTime);
        }
    }

    /**
     * Like real life cactii, this backpack will fill slowly while it's raining with refreshing water.
     */
    public void tileCactus(World world, TileBackpack backpack)
    {
        fillWithRain(world, backpack, new FluidStack(FluidRegistry.WATER, 2), 5);
    }

    public void tileMelon(World world, TileBackpack backpack)
    {
        fillWithRain(world, backpack, new FluidStack(ModFluids.melonJuice, 2), 5);
    }

    /*public void tileCow(World world, TileBackpack backpack)
    {
        IInventoryBackpack inv = backpack; //TODO make CowBackpack (and others) working in tile form
    }*/
}
