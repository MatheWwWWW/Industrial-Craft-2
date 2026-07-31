/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.BaseHeatSimulatedStack;
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class SimulatedCondensator
extends BaseHeatSimulatedStack {
    protected int reset = 0;

    public SimulatedCondensator(short id, int maxHeat) {
        super(id, maxHeat);
    }

    @Override
    public ItemStack syncStack(ItemStack original) {
        original.m_41721_(this.heat);
        CompoundTag tag = original.m_41784_();
        if (this.heatChanges.getCount() > 0) {
            tag.m_128405_("reset", this.reset);
            tag.m_128405_("total", this.heatChanges.getTotal());
            tag.m_128350_("average", this.heatChanges.getAverage());
        } else {
            tag.m_128473_("reset");
            tag.m_128473_("total");
            tag.m_128473_("average");
        }
        return original;
    }

    @Override
    public void reset() {
        super.reset();
        this.reset = 0;
    }

    @Override
    public void simulate(ISimulatedReactor planner, int x, int y, boolean heatTick, boolean damageTick) {
    }

    @Override
    public boolean canViewHeat(ISimulatedReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getStoredHeat(ISimulatedReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int storeHeat(ISimulatedReactor reactor, int x, int y, int heatChange) {
        int can = Math.min(this.maxHeat - (this.heat + 1), heatChange);
        if (can < heatChange) {
            ++this.reset;
            this.heat = 0;
            can = Math.min(this.maxHeat - (this.heat + 1), heatChange);
        }
        this.heat += can;
        this.heatChanges.addChange(can);
        return heatChange - can;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.HEAT_STORAGE);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.CONDENSATOR;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        return stat == IReactorPlannerComponent.ReactorStat.HEAT_STORAGE ? IntTag.m_128679_((int)this.maxHeat) : NULL_VALUE;
    }
}

