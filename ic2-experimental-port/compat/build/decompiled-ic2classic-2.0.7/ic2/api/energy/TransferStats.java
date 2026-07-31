/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy;

public class TransferStats {
    final long energyIn;
    final long energyOut;
    final long energyLossIn;
    final long energyLossOut;

    public TransferStats(long energyIn, long energyOut, long energyLossIn, long energyLossOut) {
        this.energyIn = energyIn;
        this.energyOut = energyOut;
        this.energyLossIn = energyLossIn;
        this.energyLossOut = energyLossOut;
    }

    public long getEnergyIn() {
        return this.energyIn;
    }

    public long getEnergyOut() {
        return this.energyOut;
    }

    public long getEnergyLossIn() {
        return this.energyLossIn;
    }

    public long getEnergyLossOut() {
        return this.energyLossOut;
    }

    public String toString() {
        return "TransferStats[In=" + this.energyIn + ", Out=" + this.energyOut + ", LossIn=" + this.energyLossIn + ", LossOut=" + this.energyLossOut + "]";
    }
}

