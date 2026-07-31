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

public class NetherStarUranium
extends UraniumBaseType {
    public static final NetherStarUranium INSTANCE = new NetherStarUranium();

    private NetherStarUranium() {
    }

    @Override
    public int getRodDurability() {
        return 20000;
    }

    @Override
    public float getPulseEU() {
        return 5.0f;
    }

    @Override
    public int getUraniumPulses() {
        return 2;
    }

    @Override
    public int getPulsesForConnection() {
        return 3;
    }

    @Override
    public float getPulseHeatModifier() {
        return 1.2f;
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
        return 20;
    }

    @Override
    public ItemStack getBaseIngot() {
        return new ItemStack((ItemLike)IC2Items.INGOT_URANIUM_ENRICHED_NETHERSTAR);
    }

    @Override
    public String getName() {
        return "nether_star";
    }

    @Override
    public int getColor() {
        return ColorUtils.rgb(255, 239, 106, 255);
    }

    @Override
    public ItemStack createNearDepletedRod(int stacksize) {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_NEAR_DEPLETED_NETHER_STAR, stacksize);
    }

    @Override
    public ItemStack createReEnrichedRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_RE_ENRICHED_NETHER_STAR);
    }

    @Override
    public ItemStack createIsotopicRod() {
        ItemStack stack = new ItemStack((ItemLike)IC2Items.URANIUM_ROD_ISOTOPIC_NETHER_STAR);
        stack.m_41721_(stack.m_41776_());
        return stack;
    }

    @Override
    public ItemStack createSingleRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_NETHER_STAR_SINGLE);
    }

    @Override
    public ItemStack createDualRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_NETHER_STAR_DUAL);
    }

    @Override
    public ItemStack createQuadRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_NETHER_STAR_QUAD);
    }
}

