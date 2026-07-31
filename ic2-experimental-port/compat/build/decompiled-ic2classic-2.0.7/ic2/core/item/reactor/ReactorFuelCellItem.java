/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedIsotopicCell;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ReactorFuelCellItem
extends ReactorComponentBase {
    public ReactorFuelCellItem() {
        super("reactor_fuel_cell", "cells", "inactive_fuel", new PropertiesBuilder().setRepair().maxDamage(2500));
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        if (reactor instanceof ISteamReactor) {
            if (damageTick) {
                int level = this.getDamage(stack) - 1 - reactor.getHeat() / 1000;
                if (level <= 0) {
                    reactor.setStackInReactor(myX, myY, new ItemStack((ItemLike)IC2Items.CELL_FUEL));
                } else {
                    this.setDamage(stack, level);
                }
            }
            return true;
        }
        int level = this.getDamage(stack) - 1 - reactor.getHeat() / 1000;
        if (level <= 0) {
            reactor.setStackInReactor(myX, myY, new ItemStack((ItemLike)IC2Items.CELL_FUEL));
        } else {
            this.setDamage(stack, level);
        }
        return true;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getMaxStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int storeHeat(ItemStack stack, IReactor reactor, int x, int y, int heatChange) {
        return 0;
    }

    @Override
    public float getExplosionInfluence(ItemStack stack, IReactor reactor) {
        return 0.0f;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedIsotopicCell(52, this.getMaxDamage(self), 1000);
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return 52;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.ELECTRIC;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.ISOTOPE_CELL;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY) {
            return stat.createStat(this.getMaxDamage(stack));
        }
        return NULL_VALUE;
    }
}

