/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter.special;

import ic2.core.IC2;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.InvertedFilter;
import net.minecraft.world.item.ItemStack;

public class FuelFilter
implements IFilter {
    public static final IFilter WITH_LAVA = new FuelFilter(true);
    public static final IFilter WITHOUT_LAVA = new FuelFilter(false);
    public static final IFilter NOT_WITH_LAVA = new InvertedFilter(WITH_LAVA);
    public static final IFilter NOT_WITHOUT_LAVA = new InvertedFilter(WITHOUT_LAVA);
    boolean lava;

    public FuelFilter(boolean lava) {
        this.lava = lava;
    }

    @Override
    public boolean matches(ItemStack input) {
        return IC2.RECIPES.get(true).getFuel(input, this.lava) > 0;
    }
}

