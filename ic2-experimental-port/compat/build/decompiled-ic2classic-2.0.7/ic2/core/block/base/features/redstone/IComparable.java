/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.base.features.redstone;

import ic2.core.block.base.features.redstone.IComparatorProvider;
import ic2.core.block.base.misc.comparator.ComparatorManager;
import net.minecraft.core.Direction;

public interface IComparable
extends IComparatorProvider {
    public ComparatorManager getManager();

    default public boolean isAllowingUI() {
        ComparatorManager manager = this.getManager();
        return manager != null && manager.hasComparators();
    }

    @Override
    default public int getSignalStrength(Direction side) {
        ComparatorManager manager = this.getManager();
        return manager == null ? 0 : manager.getValue(side);
    }
}

