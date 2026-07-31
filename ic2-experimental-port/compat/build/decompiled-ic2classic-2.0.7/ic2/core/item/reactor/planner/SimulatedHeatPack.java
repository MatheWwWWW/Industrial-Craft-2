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
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class SimulatedHeatPack
implements SimulatedStack {
    short component;
    int size;

    public SimulatedHeatPack(short component, int size) {
        this.component = component;
        this.size = size;
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
        if (reactor.isSteamReactor()) {
            return;
        }
        int want = 1000 * this.size;
        if (reactor.getHeat() < want) {
            reactor.addHeat(this.size);
        }
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
        return (float)this.size / 10.0f;
    }

    @Override
    public short getId() {
        return this.component;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.ELECTRIC;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.HEAT_PACK;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        return stat == IReactorPlannerComponent.ReactorStat.HEAT_STORAGE ? IntTag.m_128679_((int)this.size) : NULL_VALUE;
    }
}

