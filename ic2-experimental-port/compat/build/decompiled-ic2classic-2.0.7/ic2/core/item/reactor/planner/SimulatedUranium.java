/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.planner.BaseDurabilitySimulatedStack;
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.IC2;
import ic2.core.item.reactor.base.IUraniumRod;
import ic2.core.item.reactor.planner.SimulatedCoord;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.PriorityQueue;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class SimulatedUranium
extends BaseDurabilitySimulatedStack {
    protected IUraniumRod uranium;
    protected int rodCount;
    protected int refilled;

    public SimulatedUranium(short id, int maxDamage, IUraniumRod uranium, int rodCount) {
        super(id, maxDamage);
        this.uranium = uranium;
        this.rodCount = rodCount;
    }

    @Override
    public ItemStack syncStack(ItemStack original) {
        original.m_41721_(this.damage);
        return original;
    }

    @Override
    public void reset() {
        super.reset();
        this.refilled = 0;
    }

    @Override
    public CompoundTag save() {
        CompoundTag compound = super.save();
        compound.m_128405_("refilled", this.refilled);
        return compound;
    }

    @Override
    public void load(CompoundTag data) {
        super.load(data);
        this.refilled = data.m_128451_("refilled");
    }

    @Override
    public void simulate(ISimulatedReactor reactor, int x, int y, boolean heatTick, boolean damageTick) {
        if (!reactor.isProducingEnergy()) {
            return;
        }
        int pulsePerTick = this.uranium.getUraniumPulses();
        List<int[]> affectedArea = this.uranium.getPulseArea();
        if (heatTick) {
            List<int[]> heatArea = this.uranium.getHeatArea();
            for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                int pulses = (1 + this.rodCount / 2) * pulsePerTick;
                for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                    int m = affectedArea.size();
                    for (int i = 0; i < m; ++i) {
                        int[] offsets = affectedArea.get(i);
                        pulses += this.pulseNeighbor(reactor, x + offsets[0], y + offsets[1], this, x, y, true, damageTick);
                    }
                }
                int heat = (int)((float)(MathUtils.sumUp(pulses) * 4) * this.uranium.getPulseHeatModifier());
                PriorityQueue queue = CollectionUtils.createInsertionQueue();
                int m = heatArea.size();
                for (int i = 0; i < m; ++i) {
                    int[] offsets = heatArea.get(i);
                    this.findNeighbor(reactor, x + offsets[0], y + offsets[1], queue);
                }
                while (queue.size() > 0 && heat > 0) {
                    int deltaHeat = heat / queue.size();
                    heat -= deltaHeat;
                    heat += ((SimulatedCoord)queue.dequeue()).storeHeat(reactor, deltaHeat);
                }
                if (heat <= 0) continue;
                reactor.addHeat(heat);
            }
        } else {
            int pulses = (1 + this.rodCount / 2) * pulsePerTick;
            for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                for (int i = 0; i < pulses; ++i) {
                    this.acceptUraniumPulse(reactor, x, y, this, x, y, false, damageTick);
                }
                for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                    int m = affectedArea.size();
                    for (int i = 0; i < m; ++i) {
                        int[] offsets = affectedArea.get(i);
                        this.pulseNeighbor(reactor, x + offsets[0], y + offsets[1], this, x, y, false, damageTick);
                    }
                }
            }
        }
        if (damageTick) {
            if (this.damage + 1 >= this.maxDamage) {
                ++this.refilled;
                this.damage = 0;
            } else {
                ++this.damage;
            }
        }
    }

    @Override
    public boolean acceptUraniumPulse(ISimulatedReactor reactor, int x, int y, SimulatedStack source, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        if (reactor.isSimulatingPulses()) {
            reactor.addFuelPulse();
        }
        if (reactor instanceof ISteamReactor) {
            return true;
        }
        if (!heatRun) {
            reactor.addOutput(this.uranium.getPulseEU());
        }
        return true;
    }

    @Override
    public float getExplosionInfluence(ISimulatedReactor reactor) {
        return this.uranium.getExplosionModifier() * (float)this.rodCount;
    }

    protected int pulseNeighbor(ISimulatedReactor reactor, int targetX, int targetY, SimulatedStack source, int x, int y, boolean heatRun, boolean damageTick) {
        SimulatedStack other = reactor.getItem(targetX, targetY);
        return other != null && other.acceptUraniumPulse(reactor, targetX, targetY, source, x, y, heatRun, damageTick) ? this.uranium.getPulsesForConnection() : 0;
    }

    protected void findNeighbor(ISimulatedReactor reactor, int x, int y, PriorityQueue<SimulatedCoord> coords) {
        SimulatedStack stack = reactor.getItem(x, y);
        if (stack != null && stack.canStoreHeat(reactor, x, y)) {
            coords.enqueue((Object)new SimulatedCoord(stack, x, y));
        }
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.ROD_COUNT, IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY, IReactorPlannerComponent.ReactorStat.PULSE_COUNT, IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION, IReactorPlannerComponent.ReactorStat.ENERGY_PRODUCTION);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.FUEL_ROD;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        switch (stat) {
            case ROD_COUNT: {
                return IntTag.m_128679_((int)this.rodCount);
            }
            case MAX_COMPONENT_DURABILITY: {
                return IntTag.m_128679_((int)this.maxDamage);
            }
            case PULSE_COUNT: {
                return IntTag.m_128679_((int)((1 + this.rodCount / 2) * this.uranium.getUraniumPulses() * this.rodCount));
            }
            case HEAT_PRODUCTION: {
                return IntTag.m_128679_((int)((int)((float)(MathUtils.sumUp((1 + this.rodCount / 2) * this.uranium.getUraniumPulses()) * 4) * this.uranium.getPulseHeatModifier()) * this.rodCount));
            }
            case ENERGY_PRODUCTION: {
                return FloatTag.m_128566_((float)((float)((1 + this.rodCount / 2) * this.uranium.getUraniumPulses()) * this.uranium.getPulseEU() * (float)this.rodCount * (float)IC2.CONFIG.reactorOutput.get()));
            }
        }
        return NULL_VALUE;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat, ISimulatedReactor planner, int x, int y) {
        switch (stat) {
            case HEAT_PRODUCTION: {
                int pulsePerTick = this.uranium.getUraniumPulses();
                List<int[]> affectedArea = this.uranium.getPulseArea();
                int heat = 0;
                for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                    int pulses = (1 + this.rodCount / 2) * pulsePerTick;
                    for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                        int m = affectedArea.size();
                        for (int i = 0; i < m; ++i) {
                            int[] offsets = affectedArea.get(i);
                            pulses += this.pulseNeighbor(planner, x + offsets[0], y + offsets[1], this, x, y, true, false);
                        }
                    }
                    heat += (int)((float)(MathUtils.sumUp(pulses) * 4) * this.uranium.getPulseHeatModifier());
                }
                return IntTag.m_128679_((int)heat);
            }
            case ENERGY_PRODUCTION: {
                int pulsePerTick = this.uranium.getUraniumPulses();
                List<int[]> affectedArea = this.uranium.getPulseArea();
                int pulses = (1 + this.rodCount / 2) * pulsePerTick;
                for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                    for (int i = 0; i < pulses; ++i) {
                        this.acceptUraniumPulse(planner, x, y, this, x, y, false, false);
                    }
                    for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                        int m = affectedArea.size();
                        for (int i = 0; i < m; ++i) {
                            int[] offsets = affectedArea.get(i);
                            this.pulseNeighbor(planner, x + offsets[0], y + offsets[1], this, x, y, false, false);
                        }
                    }
                }
                return FloatTag.m_128566_((float)planner.getEnergyOutput());
            }
        }
        return super.getStat(stat, planner, x, y);
    }
}

