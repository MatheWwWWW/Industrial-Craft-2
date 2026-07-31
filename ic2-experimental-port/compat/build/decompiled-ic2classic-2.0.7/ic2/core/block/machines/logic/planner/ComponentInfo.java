/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  net.minecraft.nbt.NumericTag
 */
package ic2.core.block.machines.logic.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import net.minecraft.nbt.NumericTag;

public class ComponentInfo {
    final short id;
    final IReactorPlannerComponent.ComponentType type;
    final Object2ObjectMap<IReactorPlannerComponent.ReactorStat, NumericTag> nbt;

    public ComponentInfo(SimulatedStack sim) {
        Object2ObjectSortedMap tempNBT = CollectionUtils.createLinkedMap();
        this.id = sim.getId();
        this.type = sim.getComponentType();
        for (IReactorPlannerComponent.ReactorStat stat : sim.getStats()) {
            tempNBT.put((Object)stat, (Object)sim.getStat(stat));
        }
        this.nbt = Object2ObjectMaps.unmodifiable(tempNBT);
    }

    public short getId() {
        return this.id;
    }

    public IReactorPlannerComponent.ComponentType getType() {
        return this.type;
    }

    public Object2ObjectMap<IReactorPlannerComponent.ReactorStat, NumericTag> getStats() {
        return this.nbt;
    }
}

