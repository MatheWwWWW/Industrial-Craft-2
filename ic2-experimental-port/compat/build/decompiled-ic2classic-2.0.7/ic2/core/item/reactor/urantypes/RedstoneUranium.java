/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.reactor.urantypes;

import ic2.core.item.reactor.urantypes.UraniumBaseType;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.ColorUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class RedstoneUranium
extends UraniumBaseType {
    public static final RedstoneUranium INSTANCE = new RedstoneUranium();

    private RedstoneUranium() {
    }

    @Override
    public int getRodDurability() {
        return 8000;
    }

    @Override
    public float getPulseEU() {
        return 1.0f;
    }

    @Override
    public int getUraniumPulses() {
        return 2;
    }

    @Override
    public int getPulsesForConnection() {
        return 1;
    }

    @Override
    public float getPulseHeatModifier() {
        return 0.5f;
    }

    @Override
    public float getExplosionModifier() {
        return 2.0f;
    }

    @Override
    public boolean isEnrichedUranium() {
        return true;
    }

    @Override
    public int getFusionHeat() {
        return 10;
    }

    @Override
    public ItemStack getBaseIngot() {
        return new ItemStack((ItemLike)IC2Items.INGOT_URANIUM_ENRICHED_REDSTONE);
    }

    @Override
    public String getName() {
        return "redstone";
    }

    @Override
    public int getColor() {
        return ColorUtils.RED;
    }

    @Override
    public ItemStack createNearDepletedRod(int stacksize) {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_NEAR_DEPLETED_REDSTONE, stacksize);
    }

    @Override
    public ItemStack createReEnrichedRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_RE_ENRICHED_REDSTONE);
    }

    @Override
    public ItemStack createIsotopicRod() {
        ItemStack stack = new ItemStack((ItemLike)IC2Items.URANIUM_ROD_ISOTOPIC_REDSTONE);
        stack.m_41721_(stack.m_41776_());
        return stack;
    }

    @Override
    public ItemStack createSingleRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_REDSTONE_SINGLE);
    }

    @Override
    public ItemStack createDualRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_REDSTONE_DUAL);
    }

    @Override
    public ItemStack createQuadRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_REDSTONE_QUAD);
    }
}

