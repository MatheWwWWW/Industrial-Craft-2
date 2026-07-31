/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.base.features.redstone;

import net.minecraft.core.Direction;

public interface IRedstoneProvider {
    public int getCommonSignalStrength(Direction var1);

    default public int getStrongSignalStrength(Direction side) {
        return this.getCommonSignalStrength(side);
    }

    default public int getWeakSignalStrength(Direction side) {
        return this.getCommonSignalStrength(side);
    }
}

