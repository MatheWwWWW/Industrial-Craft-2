/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.FloatTag
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
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class SimulatedReactorPlating
implements SimulatedStack {
    protected short componentId;
    protected int heatModifier;
    protected float effectModifier;

    public SimulatedReactorPlating(short componentId, int heatModifier, float effectModifier) {
        this.componentId = componentId;
        this.heatModifier = heatModifier;
        this.effectModifier = effectModifier;
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
        if (heatTick) {
            reactor.setMaxHeat(reactor.getMaxHeat() + this.heatModifier);
            reactor.setHeatEffectModifier(reactor.getHeatEffectModifier() * this.effectModifier);
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
        return this.effectModifier >= 1.0f ? 0.0f : this.effectModifier;
    }

    @Override
    public short getId() {
        return this.componentId;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.MAX_HEAT_STORAGE, IReactorPlannerComponent.ReactorStat.REACTOR_HEAT_EFFECT_MULTIPLIER);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.PLATING;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        return stat == IReactorPlannerComponent.ReactorStat.MAX_HEAT_STORAGE ? IntTag.m_128679_((int)this.heatModifier) : (stat == IReactorPlannerComponent.ReactorStat.REACTOR_HEAT_EFFECT_MULTIPLIER ? FloatTag.m_128566_((float)this.effectModifier) : NULL_VALUE);
    }
}

