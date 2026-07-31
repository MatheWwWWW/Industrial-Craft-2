/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.reactor.planner.SimulatedCoord;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class SimulatedHeatPump
implements SimulatedStack {
    protected short componentId;
    protected int transferRate;
    protected int dirs;

    public SimulatedHeatPump(short componentId, int transferRate, int dirs) {
        this.componentId = componentId;
        this.transferRate = transferRate;
        this.dirs = dirs;
    }

    @Override
    public ItemStack syncStack(ItemStack original) {
        return original;
    }

    @Override
    public CompoundTag save() {
        return new CompoundTag();
    }

    @Override
    public void load(CompoundTag data) {
    }

    @Override
    public void commitState() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void simulate(ISimulatedReactor reactor, int x, int y, boolean heatTick, boolean damageTick) {
        if (this.dirs == 0 || this.dirs == 15) {
            return;
        }
        ObjectArrayList toSend = new ObjectArrayList();
        ObjectArrayList toPull = new ObjectArrayList();
        int heatToSend = 0;
        heatToSend += this.checkHeatAcceptor(reactor, x, y - 1, (this.dirs & 1) != 0, (List<SimulatedCoord>)toSend, (List<SimulatedCoord>)toPull);
        heatToSend += this.checkHeatAcceptor(reactor, x, y + 1, (this.dirs & 2) != 0, (List<SimulatedCoord>)toSend, (List<SimulatedCoord>)toPull);
        heatToSend += this.checkHeatAcceptor(reactor, x - 1, y, (this.dirs & 4) != 0, (List<SimulatedCoord>)toSend, (List<SimulatedCoord>)toPull);
        if (toPull.isEmpty() || toSend.isEmpty() || (heatToSend += this.checkHeatAcceptor(reactor, x + 1, y, (this.dirs & 8) != 0, (List<SimulatedCoord>)toSend, (List<SimulatedCoord>)toPull)) <= 0) {
            return;
        }
        int toAdd = heatToSend / toSend.size();
        heatToSend = 0;
        for (SimulatedCoord coord : toSend) {
            heatToSend += toAdd - coord.storeHeat(reactor, toAdd);
        }
        toAdd = heatToSend / toPull.size();
        for (SimulatedCoord coord : toPull) {
            heatToSend -= toAdd - coord.storeHeat(reactor, -toAdd);
        }
        if (heatToSend > 0) {
            for (int i = 0; heatToSend > 0 && i < 8; heatToSend -= 1 - ((SimulatedCoord)toPull.get(i % toPull.size())).storeHeat(reactor, -1), ++i) {
            }
        }
    }

    private int checkHeatAcceptor(ISimulatedReactor reactor, int x, int y, boolean provide, List<SimulatedCoord> heatAcceptors, List<SimulatedCoord> heatProviders) {
        SimulatedStack comp = reactor.getItem(x, y);
        if (comp != null && comp.canStoreHeat(reactor, x, y)) {
            if (provide) {
                int heat = Math.min(comp.getStoredHeat(reactor, x, y), this.transferRate);
                if (heat > 0) {
                    heatProviders.add(new SimulatedCoord(comp, x, y));
                }
                return heat;
            }
            heatAcceptors.add(new SimulatedCoord(comp, x, y));
        }
        return 0;
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
        return 0;
    }

    @Override
    public boolean canViewHeat(ISimulatedReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public float getExplosionInfluence(ISimulatedReactor reactor) {
        return 0.0f;
    }

    @Override
    public short getId() {
        return this.componentId;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.PART_BALANCING);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.HEAT_PUMP;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        return stat == IReactorPlannerComponent.ReactorStat.PART_BALANCING ? IntTag.m_128679_((int)this.transferRate) : NULL_VALUE;
    }
}

