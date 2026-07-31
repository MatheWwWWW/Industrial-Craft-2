/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ExchangerProperty;
import ic2.core.item.reactor.base.ItemStackCoord;
import ic2.core.item.reactor.base.ReactorHeatStorageBase;
import ic2.core.item.reactor.planner.SimulatedHeatExchanger;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ReactorHeatExchangerItem
extends ReactorHeatStorageBase {
    private ExchangerProperty prop;

    public ReactorHeatExchangerItem(String name, ExchangerProperty prop) {
        super(name, prop.getTextureFolder(), prop.getTextureName(), new PropertiesBuilder().maxDamage(prop.getHeatStorage()));
        this.prop = prop;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
        int side = this.prop.getSelf();
        int react = this.prop.getReactor();
        int myHeat = 0;
        ObjectArrayList heatAcceptors = new ObjectArrayList();
        double med = (double)this.getStoredHeat(stack, reactor, x, y) / (double)this.getMaxStoredHeat(stack, reactor, x, y);
        int c = 1;
        if (react > 0) {
            ++c;
            med += (double)reactor.getHeat() / (double)reactor.getMaxHeat();
        }
        if (side > 0) {
            List<Vec2i> offsets = this.prop.getOffsets();
            int m = offsets.size();
            for (int i = 0; i < m; ++i) {
                Vec2i pos = offsets.get(i);
                med += this.checkHeatAcceptor(reactor, x + pos.getX(), y + pos.getY(), (List<ItemStackCoord>)heatAcceptors);
            }
        }
        med /= (double)(c + heatAcceptors.size());
        if (side > 0) {
            for (ItemStackCoord coord : heatAcceptors) {
                IReactorComponent heatable = (IReactorComponent)coord.stack.m_41720_();
                int add = Mth.m_14045_((int)coord.getTransferRate(heatable, reactor, med), (int)(-side), (int)side);
                myHeat -= add;
                myHeat += coord.storeHeat(heatable, reactor, add);
            }
        }
        if (react > 0) {
            int add = Mth.m_14045_((int)((int)(med * (double)reactor.getMaxHeat()) - reactor.getHeat()), (int)(-react), (int)react);
            myHeat -= add;
            reactor.setHeat(reactor.getHeat() + add);
        }
        this.storeHeat(stack, reactor, x, y, myHeat);
    }

    private double checkHeatAcceptor(IReactor reactor, int x, int y, List<ItemStackCoord> heatAcceptors) {
        IReactorComponent comp;
        ItemStack thing = reactor.getStackInReactor(x, y);
        if (thing.m_41720_() instanceof IReactorComponent && (comp = (IReactorComponent)thing.m_41720_()).canStoreHeat(thing, reactor, x, y)) {
            heatAcceptors.add(new ItemStackCoord(thing, x, y));
            double max = comp.getMaxStoredHeat(thing, reactor, x, y);
            if (max <= 0.0) {
                return 0.0;
            }
            return (double)comp.getStoredHeat(thing, reactor, x, y) / max;
        }
        return 0.0;
    }

    @Override
    public void addAffectedSlots(int x, int y, BiPredicate<Integer, Integer> slots) {
        slots.test(x, y);
        if (this.prop.getSelf() > 0) {
            slots.test(x + 1, y);
            slots.test(x - 1, y);
            slots.test(x, y + 1);
            slots.test(x, y - 1);
        }
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedHeatExchanger(this.prop);
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return this.prop.getComponentID();
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.HEAT_EXCHANGER;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.REACTOR_BALANCING);
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.PART_BALANCING);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.REACTOR_BALANCING) {
            return IntTag.m_128679_((int)this.prop.getReactor());
        }
        if (stat == IReactorPlannerComponent.ReactorStat.PART_BALANCING) {
            return IntTag.m_128679_((int)this.prop.getSelf());
        }
        return NULL_VALUE;
    }
}

