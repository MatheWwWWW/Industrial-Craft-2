/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.fluid;

import ic2.core.fluid.Ic2FluidStack;

public final class FluidTankInfo {
    public static final int ALL_SIDES = 63;
    private final int drainSideMask;
    private final int fillSideMask;
    private final int capacity;
    private final Ic2FluidStack content;

    public FluidTankInfo(int n, int n2, int n3, Ic2FluidStack ic2FluidStack) {
        this.drainSideMask = n;
        this.fillSideMask = n2;
        this.capacity = n3;
        this.content = ic2FluidStack;
    }

    public int getDrainSideMask() {
        return this.drainSideMask;
    }

    public int getFillSideMask() {
        return this.fillSideMask;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Ic2FluidStack getContent() {
        return this.content;
    }
}

