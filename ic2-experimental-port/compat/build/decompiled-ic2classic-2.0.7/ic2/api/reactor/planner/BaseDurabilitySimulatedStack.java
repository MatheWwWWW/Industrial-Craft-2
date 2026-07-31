/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.api.reactor.planner;

import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import net.minecraft.nbt.CompoundTag;

public abstract class BaseDurabilitySimulatedStack
implements SimulatedStack {
    protected int maxDamage;
    protected int damage;
    protected short id;

    public BaseDurabilitySimulatedStack(short id, int maxDamage) {
        this.id = id;
        this.maxDamage = maxDamage;
    }

    @Override
    public CompoundTag save() {
        CompoundTag data = new CompoundTag();
        data.m_128405_("damage", this.damage);
        return data;
    }

    @Override
    public void load(CompoundTag data) {
        this.damage = data.m_128451_("damage");
    }

    @Override
    public void commitState() {
    }

    @Override
    public void reset() {
        this.damage = 0;
    }

    @Override
    public boolean acceptUraniumPulse(ISimulatedReactor reactor, int x, int y, SimulatedStack source, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ISimulatedReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getStoredHeat(ISimulatedReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getMaxStoredHeat(ISimulatedReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int storeHeat(ISimulatedReactor reactor, int x, int y, int heatChange) {
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

