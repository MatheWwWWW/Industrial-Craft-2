/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.energy.tile;

import net.minecraft.util.EnumFacing;

public interface IHeatSource {
    @Deprecated
    public int maxrequestHeatTick(EnumFacing var1);

    default public int getConnectionBandwidth(EnumFacing side) {
        return this.maxrequestHeatTick(side);
    }

    @Deprecated
    public int requestHeat(EnumFacing var1, int var2);

    default public int drawHeat(EnumFacing side, int request, boolean simulate) {
        return !simulate ? this.requestHeat(side, request) : this.maxrequestHeatTick(side);
    }
}

