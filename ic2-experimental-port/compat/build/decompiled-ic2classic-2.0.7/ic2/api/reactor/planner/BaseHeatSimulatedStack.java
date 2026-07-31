/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.api.reactor.planner;

import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.api.reactor.planner.Tracker;
import net.minecraft.nbt.CompoundTag;

public abstract class BaseHeatSimulatedStack
implements SimulatedStack {
    protected int maxHeat;
    protected int heat;
    protected short id;
    protected Tracker heatChanges = new Tracker();

    public BaseHeatSimulatedStack(short id, int maxHeat) {
        this.id = id;
        this.maxHeat = maxHeat;
    }

    @Override
    public CompoundTag save() {
        CompoundTag data = new CompoundTag();
        data.m_128405_("heat", this.heat);
        this.heatChanges.save(data);
        return data;
    }

    @Override
    public void load(CompoundTag data) {
        this.heat = data.m_128451_("heat");
        this.heatChanges.load(data);
    }

    @Override
    public void commitState() {
        this.heatChanges.commit();
    }

    @Override
    public void reset() {
        this.heatChanges.reset();
        this.heat = 0;
    }

    @Override
    public boolean acceptUraniumPulse(ISimulatedReactor reactor, int x, int y, SimulatedStack source, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ISimulatedReactor reactor, int x, int y) {
        return true;
    }

    @Override
    public int getStoredHeat(ISimulatedReactor reactor, int x, int y) {
        return this.heat;
    }

    @Override
    public int getMaxStoredHeat(ISimulatedReactor reactor, int x, int y) {
        return this.maxHeat;
    }

    @Override
    public int storeHeat(ISimulatedReactor reactor, int x, int y, int heatChange) {
        this.heatChanges.addChange(heatChange);
        this.heat += heatChange;
        if (this.heat > this.maxHeat) {
            reactor.markBroken(x, y);
            heatChange = this.maxHeat - this.heat + 1;
        } else if (this.heat < 0) {
            heatChange = this.heat;
            this.heat = 0;
        } else {
            heatChange = 0;
        }
        return heatChange;
    }

    @Override
    public boolean canViewHeat(ISimulatedReactor reactor, int x, int y) {
        return true;
    }

    @Override
    public float getExplosionInfluence(ISimulatedReactor reactor) {
        return 0.0f;
    }

    @Override
    public short getId() {
        return this.id;
    }
}

