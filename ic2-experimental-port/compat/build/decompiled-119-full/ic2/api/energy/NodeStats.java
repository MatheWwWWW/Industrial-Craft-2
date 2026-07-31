/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy;

public class NodeStats {
    protected double energyIn;
    protected double energyOut;
    protected double voltage;

    public NodeStats(double d, double d2, double d3) {
        this.energyIn = d;
        this.energyOut = d2;
        this.voltage = d3;
    }

    public double getEnergyIn() {
        return this.energyIn;
    }

    public double getEnergyOut() {
        return this.energyOut;
    }

    public double getVoltage() {
        return this.voltage;
    }
}

