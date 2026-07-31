/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.base;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public interface IUraniumRod {
    public int getRodDurability();

    public float getPulseEU();

    public int getUraniumPulses();

    public int getPulsesForConnection();

    public List<int[]> getPulseArea();

    public List<int[]> getHeatArea();

    public float getPulseHeatModifier();

    public float getExplosionModifier();

    public boolean isEnrichedUranium();

    public int getFusionHeat();

    public ItemStack getBaseIngot();

    public String getName();

    public int getColor();

    public ItemStack createNearDepletedRod(int var1);

    public ItemStack createNearDepletedRod();

    public ItemStack createReEnrichedRod();

    public ItemStack createIsotopicRod();

    public ItemStack createSingleRod();

    public ItemStack createDualRod();

    public ItemStack createQuadRod();
}

