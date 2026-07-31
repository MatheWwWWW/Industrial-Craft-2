/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.energy.tile;

import net.minecraft.util.EnumFacing;

public interface IKineticSource {
    @Deprecated
    public int maxrequestkineticenergyTick(EnumFacing var1);

    default public int getConnectionBandwidth(EnumFacing side) {
        return this.maxrequestkineticenergyTick(side);
    }

    @Deprecated
    public int requestkineticenergy(EnumFacing var1, int var2);

    default public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
        return !simulate ? this.requestkineticenergy(side, request) : this.maxrequestkineticenergyTick(side);
    }
}

