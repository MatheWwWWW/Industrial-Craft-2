/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.inventory.filter;

import ic2.api.crops.ICropSeed;
import ic2.api.items.IAutoEatable;
import ic2.api.items.IFuelableItem;
import ic2.api.items.ITagBlock;
import ic2.api.items.ITagItem;
import ic2.api.items.IUpgradeItem;
import ic2.api.items.IWindmillBlade;
import ic2.api.items.ItemRegistries;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.items.readers.ICropReader;
import ic2.api.items.readers.IEUReader;
import ic2.api.items.readers.IThermometer;
import ic2.api.recipes.registries.IUUMatterRegistry;
import ic2.core.IC2;
import ic2.core.inventory.filter.ArrayOrFilter;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.TagFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.item.base.features.IHandlerItem;
import ic2.core.item.tool.PainterTool;
import ic2.core.item.upgrades.special.UpgradeContainerItem;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SpecialFilters {
    static IFilter CHARGE_FILTER_CACHE;
    static IFilter[] DYE_FILTER;
    public static final IFilter ALWAYS_FALSE;
    public static final IFilter ALWAYS_TRUE;
    public static final IFilter ANY_ITEM;
    public static final IFilter NO_TAGS;
    public static final IFilter TOOL_BOX;
    public static final IFilter BLOCK_FILTER;
    public static final IFilter BLOCK_TAG_FILTER;
    public static final IFilter CROP_FILTER;
    public static final IFilter CROP_FILTER_SCANNED;
    public static final IFilter CROP_HARVESTER_FILTER;
    public static final IFilter BOOK_FILTER;
    public static final IFilter ENCHANT_FILTER;
    public static final IFilter AUTO_EATABLE;
    public static final IFilter UU_OUTPUT;
    public static final IFilter HANDLER_ITEM;
    public static final IFilter FUEL_CAN;
    public static final IFilter EU_READER;
    public static final IFilter THERMOMETER;
    public static final IFilter CROP_SCANNER;
    public static final IFilter WINDMILL_ROTOR;
    public static final IFilter EMPTY_PAINTER;
    public static final IFilter PAINTER;
    public static final IFilter UPGRADE_CONTAINER;

    public static IFilter createChargeFilter() {
        if (CHARGE_FILTER_CACHE == null) {
            CHARGE_FILTER_CACHE = new ArrayOrFilter(ElectricItemFilter.DISCHARGE_EXTERNAL_FILTER, new SimpleFilter((ItemLike)Items.f_42451_), new SimpleFilter((ItemLike)IC2Items.SU_BATTERY));
        }
        return CHARGE_FILTER_CACHE;
    }

    public static IFilter createDyeFilter(DyeColor dye) {
        if (DYE_FILTER == null) {
            DYE_FILTER = new IFilter[16];
            for (DyeColor color : DyeColor.values()) {
                SpecialFilters.DYE_FILTER[color.m_41060_()] = new TagFilter((TagKey<Item>)ItemTags.create((ResourceLocation)new ResourceLocation("forge", "dyes/" + color.m_41065_())));
            }
        }
        return DYE_FILTER[dye.m_41060_()];
    }

    public static DyeColor getNextColor(DyeColor prev, boolean allowNull) {
        if (prev == null) {
            if (!allowNull) {
                throw new IllegalStateException("Null Color is not allowed");
            }
            return DyeColor.WHITE;
        }
        return allowNull && prev == DyeColor.BLACK ? null : DyeColor.m_41053_((int)(prev.m_41060_() + 1));
    }

    public static DyeColor getPrevColor(DyeColor next, boolean allowNull) {
        if (next == null) {
            if (!allowNull) {
                throw new IllegalStateException("Null Color is not allowed");
            }
            return DyeColor.BLACK;
        }
        return next == DyeColor.WHITE ? (allowNull ? null : DyeColor.BLACK) : DyeColor.m_41053_((int)(next.m_41060_() - 1));
    }

    public static ItemStack createDyeItem(DyeColor dye) {
        if (dye == null) {
            return new ItemStack((ItemLike)IC2Items.PAINTER);
        }
        switch (dye) {
            case BLACK: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_BLACK);
            }
            case BLUE: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_BLUE);
            }
            case BROWN: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_BROWN);
            }
            case CYAN: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_CYAN);
            }
            case GRAY: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_GRAY);
            }
            case GREEN: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_GREEN);
            }
            case LIGHT_BLUE: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_LIGHT_BLUE);
            }
            case LIGHT_GRAY: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_LIGHT_GRAY);
            }
            case LIME: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_LIME);
            }
            case MAGENTA: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_MAGENTA);
            }
            case ORANGE: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_ORANGE);
            }
            case PINK: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_PINK);
            }
            case PURPLE: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_PURPLE);
            }
            case RED: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_RED);
            }
            case WHITE: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_WHITE);
            }
            case YELLOW: {
                return new ItemStack((ItemLike)IC2Items.PAINTER_YELLOW);
            }
        }
        return ItemStack.f_41583_;
    }

    static {
        DYE_FILTER = null;
        ALWAYS_FALSE = T -> false;
        ALWAYS_TRUE = T -> true;
        ANY_ITEM = T -> !T.m_41619_();
        NO_TAGS = T -> !T.m_41619_() && !(T.m_41720_() instanceof ITagItem);
        TOOL_BOX = T -> ItemRegistries.isBoxable(T) || T.m_204117_(IC2Tags.TOOLBOX);
        BLOCK_FILTER = T -> Block.m_49814_((Item)T.m_41720_()) != Blocks.f_50016_;
        BLOCK_TAG_FILTER = T -> Block.m_49814_((Item)T.m_41720_()) != Blocks.f_50016_ || T.m_41720_() instanceof ITagBlock;
        CROP_FILTER = T -> T.m_41720_() instanceof ICropSeed;
        CROP_FILTER_SCANNED = T -> T.m_41720_() instanceof ICropSeed && ((ICropSeed)T.m_41720_()).getScanLevel(T) >= 4;
        CROP_HARVESTER_FILTER = T -> {
            Item item = T.m_41720_();
            return item == IC2Items.OVERCLOCKER_UPGRADE || item == IC2Items.QUANTUM_OVERCLOCKER_UPGRADE || item == IC2Items.FIELD_PAD_UPGRADE || item == IC2Items.ADVANCED_FIELD_PAD_UPGRADE || item == IC2Items.BASIC_FIELD_PAD_UPGRADE;
        };
        BOOK_FILTER = T -> T.m_41720_() == Items.f_42517_ || T.m_41720_() == Items.f_42690_;
        ENCHANT_FILTER = T -> T.m_41720_() == Items.f_42517_ || T.m_41720_() == Items.f_42690_ || T.m_41720_().m_8120_(T) || T.m_41720_() instanceof IElectricEnchantable;
        AUTO_EATABLE = T -> {
            IAutoEatable eatable;
            Item patt2904$temp = T.m_41720_();
            return patt2904$temp instanceof IAutoEatable && (eatable = (IAutoEatable)patt2904$temp).canAutoEat(T);
        };
        UU_OUTPUT = T -> {
            for (IUUMatterRegistry.UUMatterEntry entry : IC2.RECIPES.get().UU.getEntries()) {
                if (!StackUtil.isStackEqual(entry.getStack(), T)) continue;
                return true;
            }
            return false;
        };
        HANDLER_ITEM = T -> T.m_41720_() instanceof IHandlerItem;
        FUEL_CAN = T -> T.m_41720_() instanceof IFuelableItem && ((IFuelableItem)T.m_41720_()).hasFuel(T);
        EU_READER = IEUReader::isEUReaderImpl;
        THERMOMETER = IThermometer::isThermometerImpl;
        CROP_SCANNER = ICropReader::isCropReaderImpl;
        WINDMILL_ROTOR = T -> T.m_41720_() instanceof IWindmillBlade;
        EMPTY_PAINTER = T -> T.m_41720_() instanceof PainterTool;
        PAINTER = T -> T.m_41720_() instanceof PainterTool && ((PainterTool)T.m_41720_()).getColor() != null;
        UPGRADE_CONTAINER = T -> T.m_41720_() instanceof IUpgradeItem && !(T.m_41720_() instanceof UpgradeContainerItem);
    }
}

