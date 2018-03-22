package com.darkona.adventurebackpack.reference;

import java.util.Arrays;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.Validate;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;

import com.darkona.adventurebackpack.util.BackpackUtils;

import static com.darkona.adventurebackpack.common.Constants.TAG_TYPE;
import static com.darkona.adventurebackpack.reference.BackpackTypes.Props.NIGHT_VISION;
import static com.darkona.adventurebackpack.reference.BackpackTypes.Props.REMOVAL;
import static com.darkona.adventurebackpack.reference.BackpackTypes.Props.SPECIAL;
import static com.darkona.adventurebackpack.reference.BackpackTypes.Props.TILE;

@SuppressWarnings("unused")
public enum BackpackTypes implements IType
{
    // @formatter:off
    STANDARD        (  0),

    BAT             (  2, SPECIAL, REMOVAL, NIGHT_VISION),
    BLACK           (  3),
    BLAZE           (  4),
    BLUE            ( 14),
    BOOKSHELF       ( 15),
    BROWN           ( 16),
    BROWN_MUSHROOM  ( 43),
    CACTUS          ( 17, SPECIAL, TILE),
    CAKE            ( 18),
    CARROT          (  5),
    CHEST           ( 19),
    CHICKEN         ( 28, SPECIAL),
    COAL            (  6),
    COOKIE          ( 20),
    COW             (  1, SPECIAL),
    CREEPER         ( 64, SPECIAL),
    CYAN            ( 21),
    DELUXE          ( 25),
    DIAMOND         (  7),
    DRAGON          ( 22, SPECIAL, REMOVAL, NIGHT_VISION),
    EGG             ( 23),
    ELECTRIC        ( 24),
    EMERALD         (  8),
    END             ( 27),
    ENDERMAN        ( 26),
    GHAST           ( 30),
    GLOWSTONE       ( 37),
    GOLD            (  9),
    GRAY            ( 31),
    GREEN           ( 32),
    HAYBALE         ( 33),
    HORSE           ( 34),
    IRON            ( 10),
    IRON_GOLEM      ( 11), //TODO has other ability, need some prop
    LAPIS           ( 12),
    LEATHER         ( 35),
    LIGHT_BLUE      ( 36),
    LIGHT_GRAY      ( 38),
    LIME            ( 39),
    MAGENTA         ( 40),
    MAGMA_CUBE      ( 41),
    MELON           ( 42, SPECIAL, TILE),
    MOOSHROOM       ( 45, SPECIAL),
    NETHER          ( 46),
    OBSIDIAN        ( 48),
    OCELOT          ( 29, SPECIAL),
    ORANGE          ( 49),
    OVERWORLD       ( 50),
    PIG             ( 53, SPECIAL),
    PIGMAN          ( 51, SPECIAL, REMOVAL),
    PINK            ( 52),
    PUMPKIN         ( 54),
    PURPLE          ( 55),
    QUARTZ          ( 56),
    RAINBOW         ( 57, SPECIAL, REMOVAL),
    RED             ( 58),
    RED_MUSHROOM    ( 44),
    REDSTONE        ( 13),
    SANDSTONE       ( 59),
    SHEEP           ( 60),
    SILVERFISH      ( 61),
    SKELETON        ( 65),
    SLIME           ( 67, SPECIAL),
    SNOW            ( 68),
    SPIDER          ( 69),
    SPONGE          ( 70),
    SQUID           ( 62, SPECIAL, REMOVAL, NIGHT_VISION),
    SUNFLOWER       ( 63, SPECIAL),
    VILLAGER        ( 71),
    WHITE           ( 72),
    WITHER          ( 47),
    WITHER_SKELETON ( 66),
    WOLF            ( 73, SPECIAL),
    YELLOW          ( 74),
    ZOMBIE          ( 75),

    UNKNOWN         (127), // Byte.MAX_VALUE
    ;
    // @formatter:on

    public static final ImmutableBiMap<Integer, BackpackTypes> BY_META;

    private final int meta;
    private final String name;
    private final ImmutableSet<Props> props;

    BackpackTypes(int meta, Props... props)
    {
        Validate.inclusiveBetween(0, (int) Byte.MAX_VALUE, meta, "wrong meta value: %s (%s)", meta, this);

        this.meta = meta;
        this.name = name().toLowerCase();
        this.props = Sets.immutableEnumSet(Arrays.asList(props));
    }

    static
    {
        BiMap<BackpackTypes, Integer> byMeta = EnumHashBiMap.create(BackpackTypes.class);

        for (BackpackTypes type : BackpackTypes.values())
            if (byMeta.put(type, type.meta) != null)
                throw new IllegalArgumentException("duplicate meta: " + type.meta);

        BY_META = ImmutableBiMap.copyOf(byMeta.inverse());
    }

    public static String getSkinName(ItemStack backpack)
    {
        return getType(backpack).getName();
    }

    public static BackpackTypes getType(int meta)
    {
        Validate.inclusiveBetween(0, (int) Byte.MAX_VALUE, meta, "wrong meta value: %s", meta);
        BackpackTypes type = BY_META.get(meta);
        return type != null ? type : UNKNOWN;
    }

    public static BackpackTypes getType(String name)
    {
        for (BackpackTypes type : BackpackTypes.values())
            if (type.name.equals(name))
                return type;

        return UNKNOWN;
    }

    public static BackpackTypes getType(ItemStack backpack)
    {
        if (backpack == null) // well... Wearing.getWearingBackpack() may return null... //TODO solve this damn null
            return null;

        NBTTagCompound backpackTag = BackpackUtils.getWearableCompound(backpack);
        int meta = backpackTag.getInteger(TAG_TYPE);
        if (meta == UNKNOWN.meta) //TODO remove? are we rly need to normalize it?
        {
            backpackTag.setInteger(TAG_TYPE, STANDARD.meta);
        }
        return getType(meta);
    }

    public static int getLowestUnusedMeta()
    {
        for (int i = 0; i < Byte.MAX_VALUE; i++)
            if (BY_META.get(i) == null)
                return i;

        return -1;
    }

    public boolean isNightVision()
    {
        return hasProperty(NIGHT_VISION);
    }

    public boolean isSpecial()
    {
        return hasProperty(SPECIAL);
    }

    public boolean hasProperty(Props prop)
    {
        return this.props.contains(prop);
    }

    public boolean hasProperties(ImmutableSet<Props> props)
    {
        return this.props.containsAll(props);
    }

    public enum Props
    {
        SPECIAL,
        REMOVAL,
        TILE,
        NIGHT_VISION,
        //HOLIDAY,
        //OTHER_ABILITY, // creeper or skeleton etc
        ;

        public static final ImmutableSet<Props> POTION_EFFECT = Sets.immutableEnumSet(SPECIAL, REMOVAL);
    }

    @Override
    public int getMeta()
    {
        return this.meta;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    public String getLocalizedName()
    {
        return I18n.translateToLocal("adventurebackpack:skin." + name + ".name");
    }
}
