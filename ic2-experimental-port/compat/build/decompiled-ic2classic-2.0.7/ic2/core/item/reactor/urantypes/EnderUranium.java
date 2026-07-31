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
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class EnderUranium
extends UraniumBaseType {
    public static final EnderUranium INSTANCE = new EnderUranium();
    List<int[]> pulseArea = CollectionUtils.createList();

    private EnderUranium() {
        this.addArray(-1, -1);
        this.addArray(1, -1);
        this.addArray(-1, 1);
        this.addArray(1, 1);
        for (int x = 1; x < 10; ++x) {
            this.addEntry(x, 0);
            this.addEntry(-x, 0);
        }
        for (int y = 1; y < 7; ++y) {
            this.addEntry(0, y);
            this.addEntry(0, -y);
        }
    }

    void addEntry(int x, int y) {
        this.pulseArea.add(new int[]{x, y});
    }

    @Override
    public List<int[]> getPulseArea() {
        return this.pulseArea;
    }

    @Override
    public int getRodDurability() {
        return 5000;
    }

    @Override
    public float getPulseEU() {
        return 2.0f;
    }

    @Override
    public int getUraniumPulses() {
        return 1;
    }

    @Override
    public int getPulsesForConnection() {
        return 1;
    }

    @Override
    public float getPulseHeatModifier() {
        return 0.25f;
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
        return 3;
    }

    @Override
    public ItemStack getBaseIngot() {
        return new ItemStack((ItemLike)IC2Items.INGOT_URANIUM_ENRICHED_ENDERPEARL);
    }

    @Override
    public String getName() {
        return "ender";
    }

    @Override
    public int getColor() {
        return ColorUtils.rgb(35, 174, 113, 255);
    }

    @Override
    public ItemStack createNearDepletedRod(int stacksize) {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_NEAR_DEPLETED_ENDER_PEARL, stacksize);
    }

    @Override
    public ItemStack createReEnrichedRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_RE_ENRICHED_ENDER_PEARL);
    }

    @Override
    public ItemStack createIsotopicRod() {
        ItemStack stack = new ItemStack((ItemLike)IC2Items.URANIUM_ROD_ISOTOPIC_ENDER_PEARL);
        stack.m_41721_(stack.m_41776_());
        return stack;
    }

    @Override
    public ItemStack createSingleRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_ENDER_PEARL_SINGLE);
    }

    @Override
    public ItemStack createDualRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_ENDER_PEARL_DUAL);
    }

    @Override
    public ItemStack createQuadRod() {
        return new ItemStack((ItemLike)IC2Items.URANIUM_ROD_ENDER_PEARL_QUAD);
    }
}

