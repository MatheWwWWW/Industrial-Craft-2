/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.BaseDurabilitySimulatedStack;
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class SimulatedIsotopicCell
extends BaseDurabilitySimulatedStack {
    protected int finished = 0;
    protected int heatRequired;

    public SimulatedIsotopicCell(short id, int maxDamage, int heatRequired) {
        super(id, maxDamage);
        this.damage = maxDamage;
        this.heatRequired = heatRequired;
    }

    @Override
    public ItemStack syncStack(ItemStack original) {
        original.m_41721_(this.damage);
        if (this.finished > 0) {
            original.m_41700_("breed", (Tag)IntTag.m_128679_((int)this.finished));
        } else {
            original.m_41749_("breed");
        }
        return original;
    }

    @Override
    public void reset() {
        this.damage = this.maxDamage;
        this.finished = 0;
    }

    @Override
    public void simulate(ISimulatedReactor reactor, int x, int y, boolean heatTick, boolean damageTick) {
    }

    @Override
    public boolean acceptUraniumPulse(ISimulatedReactor reactor, int x, int y, SimulatedStack source, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        if (reactor.isSimulatingPulses()) {
            reactor.addBreedingPulse();
        }
        if (reactor.isSteamReactor()) {
            if (damageTick) {
                int level = this.damage - 1 - reactor.getHeat() / this.heatRequired;
                if (level <= 0) {
                    this.damage = this.maxDamage;
                    ++this.finished;
                } else {
                    this.damage = level;
                }
            }
            return true;
        }
        int level = this.damage - 1 - reactor.getHeat() / this.heatRequired;
        if (level <= 0) {
            this.damage = this.maxDamage;
            ++this.finished;
        } else {
            this.damage = level;
        }
        return true;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats() {
        return CollectionUtils.asList(IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY);
    }

    @Override
    public IReactorPlannerComponent.ReactorType getValidType() {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getComponentType() {
        return IReactorPlannerComponent.ComponentType.ISOTOPE_CELL;
    }

    @Override
    public NumericTag getStat(IReactorPlannerComponent.ReactorStat stat) {
        return stat == IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY ? IntTag.m_128679_((int)this.maxDamage) : NULL_VALUE;
    }
}

