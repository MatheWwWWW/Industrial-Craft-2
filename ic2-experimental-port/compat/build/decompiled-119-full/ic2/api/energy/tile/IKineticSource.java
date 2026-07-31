/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.energy.tile;

import net.minecraft.core.Direction;

public interface IKineticSource {
    @Deprecated
    public int maxrequestkineticenergyTick(Direction var1);

    default public int getConnectionBandwidth(Direction direction) {
        return this.maxrequestkineticenergyTick(direction);
    }

    @Deprecated
    public int requestkineticenergy(Direction var1, int var2);

    default public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        return !bl ? this.requestkineticenergy(direction, n) : this.maxrequestkineticenergyTick(direction);
    }
}

