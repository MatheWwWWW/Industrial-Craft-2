/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.machines.logic.planner.newLogic;

public class SimulationResult {
    public int totalSimTicks;
    public int ticksSimulated;
    public int ticksCooled;
    public float effectiveProduction;
    public int tickRate;
    public boolean isStable;

    public void calculateEff() {
        this.effectiveProduction = this.ticksSimulated == this.totalSimTicks ? (float)this.totalSimTicks / (float)Math.max(this.ticksSimulated + this.ticksCooled, 1) : (float)this.ticksSimulated / (float)(this.totalSimTicks + this.ticksCooled);
    }
}

