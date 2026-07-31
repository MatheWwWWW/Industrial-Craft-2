/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.energy.tile;

import net.minecraft.core.Direction;

public interface IHeatSource {
    @Deprecated
    public int maxrequestHeatTick(Direction var1);

    default public int getConnectionBandwidth(Direction direction) {
        return this.maxrequestHeatTick(direction);
    }

    @Deprecated
    public int requestHeat(Direction var1, int var2);

    default public int drawHeat(Direction direction, int n, boolean bl) {
        return !bl ? this.requestHeat(direction, n) : this.maxrequestHeatTick(direction);
    }
}

