/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.ReactorHeatStorageBase;
import ic2.core.item.reactor.planner.SimulatedHeatStorage;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;

public class ReactorHeatStorageItem
extends ReactorHeatStorageBase {
    private short componentId;

    public ReactorHeatStorageItem(String name, int maxDamage, String textureFolder, String textureName, int componentId) {
        super(name, textureFolder, textureName, new PropertiesBuilder().maxDamage(maxDamage));
        this.componentId = (short)componentId;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedHeatStorage(this.componentId, this.getMaxDamage(self));
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return this.componentId;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.COOLANT_CELL;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.HEAT_STORAGE);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        if (stat == IReactorPlannerComponent.ReactorStat.HEAT_STORAGE) {
            return IntTag.m_128679_((int)this.getMaxDamage(stack));
        }
        return NULL_VALUE;
    }
}

