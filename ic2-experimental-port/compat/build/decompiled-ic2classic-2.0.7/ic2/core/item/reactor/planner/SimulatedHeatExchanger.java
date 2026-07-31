/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.BaseHeatSimulatedStack;
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.reactor.base.ExchangerProperty;
import ic2.core.item.reactor.planner.SimulatedCoord;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class SimulatedHeatExchanger
extends BaseHeatSimulatedStack {
    ExchangerProperty property;

    public SimulatedHeatExchanger(ExchangerProperty property) {
        super(property.getComponentID(), property.getHeatStorage());
        this.property = property;
    }

    @Override
    public ItemStack syncStack(ItemStack original) {
        original.m_41721_(this.heat);
        return original;
    }

    @Override
    public void simulate(ISimulatedReactor reactor, int x, int y, boolean heatTick, boolean damageTick) {
        int side = this.property.getSelf();
        int react = this.property.getReactor();
        int myHeat = 0;
        ObjectArrayList heatAcceptors = new ObjectArrayList();
        double med = (double)this.heat / (double)this.maxHeat;
        int c = 1;
        if (react > 0) {
            ++c;
            med += (double)reactor.getHeat() / (double)reactor.getMaxHeat();
        }
        if (side > 0) {
            List<Vec2i> offsets = this.property.getOffsets();
            int m = offsets.size();
            for (int i = 0; i < m; ++i) {
                Vec2i pos = offsets.get(i);
                med += this.checkHeatAcceptor(reactor, x + pos.getX(), y + pos.getY(), (List<SimulatedCoord>)heatAcceptors);
            }
        }
        med /= (double)(c + heatAcceptors.size());
        if (side > 0) {
            for (SimulatedCoord coord : heatAcceptors) {
                int add = Mth.m_14045_((int)coord.getTransferRate(reactor, med), (int)(-side), (int)side);
                myHeat -= add;
                myHeat += coord.storeHeat(reactor, add);
            }
        }
        if (react > 0) {
            int add = Mth.m_14045_((int)((int)(med * (double)reactor.getMaxHeat()) - reactor.getHeat()), (int)(-react), (int)react);
            myHeat -= add;
            reactor.setHeat(reactor.getHeat() + add);
        }
        this.storeHeat(reactor, x, y, myHeat);
    }

    private double checkHeatAcceptor(ISimulatedReactor reactor, int x, int y, List<SimulatedCoord> heatAcceptors) {
        SimulatedStack comp = reactor.getItem(x, y);
        if (comp != null && comp.canStoreHeat(reactor, x, y)) {
            heatAcceptors.add(new SimulatedCoord(comp, x, y));
            double max = comp.getMaxStoredHeat(reactor, x, y);
            if (max <= 0.0) {
                return 0.0;
            }
            return (double)comp.getStoredHeat(reactor, x, y) / max;
        }
        return 0.0;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.REACTOR_BALANCING, IReactorPlannerComponent.ReactorStat.PART_BALANCING);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.HEAT_EXCHANGER;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        if (stat == IReactorPlannerComponent.ReactorStat.REACTOR_BALANCING) {
            return IntTag.m_128679_((int)this.property.getReactor());
        }
        if (stat == IReactorPlannerComponent.ReactorStat.PART_BALANCING) {
            return IntTag.m_128679_((int)this.property.getSelf());
        }
        return NULL_VALUE;
    }
}

