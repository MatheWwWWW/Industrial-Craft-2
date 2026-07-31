/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.energy;

import ic2.api.energy.NodeStats;

class MutableNodeStats
extends NodeStats {
    protected MutableNodeStats() {
        super(0.0, 0.0, 0.0);
    }

    protected void set(double d, double d2, double d3) {
        this.energyIn = d;
        this.energyOut = d2;
        this.voltage = d3;
    }
}

