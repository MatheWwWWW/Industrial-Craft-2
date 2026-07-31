/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectIterators
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 */
package ic2.core.energy;

import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.energy.EnergyNetPaths;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EnergyNetGrid {
    static final int DIRTY = 1;
    static final int SPLIT = 2;
    static final int RESET = 4;
    static final int POTENTIAL_SPLIT = 8;
    static int GRID_ID = 1;
    final EnergyNetLocal local;
    final int gridID;
    EnergyNetPaths.EnergyNetSubPaths subPaths;
    Map<IEnergyConductor, WireConnection> cables = CollectionUtils.createLinkedMap();
    Set<IEnergySource> sources = CollectionUtils.createLinkedSet();
    Set<IEnergySink> sinks = CollectionUtils.createLinkedSet();
    Set<IEnergySource> newSources = CollectionUtils.createLinkedSet();
    Set<IEnergySink> newSinks = CollectionUtils.createLinkedSet();
    ObjectSet<WireConnection> loseEnds = CollectionUtils.createSet();
    int flags = 0;

    EnergyNetGrid(int gridID, EnergyNetLocal local) {
        this.gridID = gridID;
        this.local = local;
        this.subPaths = local.energySourcePaths.createSubMap();
    }

    public final int getGridID() {
        return this.gridID;
    }

    public void addConductor(IEnergyConductor conductor) {
        if (this.cables.containsKey(conductor)) {
            return;
        }
        WireConnection connection = new WireConnection(conductor, this);
        this.cables.put(conductor, connection);
        this.local.addConductorToGrid(conductor, this.gridID);
        if (connection.getTotalConnections() > 1 && this.flags == 0) {
            this.markDirty(false);
            this.setFlag(4);
            this.newSinks.addAll(this.sinks);
            this.newSources.addAll(this.sources);
        }
    }

    public void addSink(IEnergySink sink) {
        this.sinks.add(sink);
        this.newSinks.add(sink);
        this.markDirty(false);
    }

    public void addSinks(Collection<IEnergySink> toAdd) {
        this.sinks.addAll(toAdd);
        this.newSinks.addAll(toAdd);
        this.markDirty(false);
    }

    public void addSource(IEnergySource source) {
        this.sources.add(source);
        this.newSources.add(source);
        this.markDirty(false);
    }

    public void addSource(Collection<IEnergySource> toAdd) {
        this.sources.addAll(toAdd);
        this.newSources.addAll(toAdd);
        this.markDirty(false);
    }

    public void removeConductor(IEnergyConductor conductor) {
        WireConnection connection = this.cables.remove(conductor);
        if (connection != null) {
            this.loseEnds.remove((Object)connection);
            ObjectIterators.pour(connection.iterator(), this.loseEnds);
            connection.destroy();
        }
    }

    public void removeSink(IEnergySink sink) {
        this.sinks.remove(sink);
        this.newSinks.remove(sink);
        this.subPaths.removeSink(sink);
        this.markDirty(false);
    }

    public void removeSource(IEnergySource source) {
        this.sources.remove(source);
        this.newSources.remove(source);
        this.subPaths.removeSource(source);
        this.markDirty(false);
    }

    public void addGrid(EnergyNetGrid grid) {
        this.sinks.addAll(grid.sinks);
        this.sources.addAll(grid.sources);
        for (IEnergyConductor conductor : grid.cables.keySet()) {
            this.cables.put(conductor, new WireConnection(conductor, this));
        }
    }

    public void finishCombining() {
        for (IEnergyConductor conductor : this.cables.keySet()) {
            this.local.addConductorToGrid(conductor, this.gridID);
        }
        this.setFlag(4);
        this.markDirty(true);
    }

    public void destroyGrid() {
        this.subPaths.reset();
        this.subPaths = null;
    }

    public void clearGrid() {
        this.subPaths.clear();
        this.subPaths = null;
    }

    public void markSplit() {
        this.markDirty(false);
        this.setFlag(8);
    }

    public void markDirty(boolean force) {
        if ((this.hasFlag(1) || this.hasFlag(2)) && !force) {
            return;
        }
        this.setFlag(1);
        this.local.markGridDirty(this.gridID);
    }

    public void processChanges() {
        if (this.hasFlag(1)) {
            if (this.hasFlag(4)) {
                this.subPaths.reset();
                this.newSinks.addAll(this.sinks);
                this.newSources.addAll(this.sources);
            }
            if (this.hasFlag(8)) {
                if (this.isGridSplit()) {
                    this.local.splitGrid(this);
                    return;
                }
                this.subPaths.reset();
                this.newSinks.addAll(this.sinks);
                this.newSources.addAll(this.sources);
            }
            if (this.newSinks.size() > 0 && this.sources.size() > 0 || this.newSources.size() > 0 && this.sinks.size() > 0) {
                this.updatePaths();
            }
            this.flags = 0;
        }
    }

    void updatePaths() {
        List<EnergyNetLocal.EnergyPath> paths;
        ObjectOpenHashSet sourceTargets = new ObjectOpenHashSet(this.sinks);
        Object2ObjectMap<IEnergySink, ObjectOpenHashSet> sinkList = CollectionUtils.createMap();
        for (IEnergySink iEnergySink : this.newSinks) {
            sinkList.put(iEnergySink, new ObjectOpenHashSet(this.sources));
        }
        this.newSinks.clear();
        for (IEnergySource iEnergySource : this.newSources) {
            paths = this.local.discoverPaths(iEnergySource, (Set<IEnergyTile>)sourceTargets, false, iEnergySource.getMaxEnergyOutput());
            this.subPaths.removePaths(paths);
            this.subPaths.addSourcePaths(iEnergySource, paths);
            for (EnergyNetLocal.EnergyPath path : paths) {
                Set sinkTodo = (Set)sinkList.get(path.target);
                if (sinkTodo == null) continue;
                sinkTodo.remove(iEnergySource);
                if (!sinkTodo.isEmpty()) continue;
                sinkList.remove(path.target);
            }
        }
        this.newSources.clear();
        for (Map.Entry entry : sinkList.entrySet()) {
            paths = this.local.discoverPaths((IEnergyTile)entry.getKey(), (Set)entry.getValue(), true, Integer.MAX_VALUE);
            this.subPaths.removePaths(paths);
            this.subPaths.addSinkPaths((IEnergySink)entry.getKey(), paths);
        }
    }

    boolean isGridSplit() {
        if (this.loseEnds.isEmpty()) {
            return false;
        }
        ObjectArrayFIFOQueue todoList = new ObjectArrayFIFOQueue();
        todoList.enqueue((Object)((WireConnection)this.loseEnds.iterator().next()));
        ObjectSet foundConnections = CollectionUtils.createSet();
        while (!todoList.isEmpty() && !this.loseEnds.isEmpty()) {
            foundConnections.add((WireConnection)((WireConnection)todoList.first()));
            this.loseEnds.remove(todoList.first());
            for (WireConnection connection : (WireConnection)todoList.dequeue()) {
                if (!foundConnections.add((WireConnection)connection)) continue;
                todoList.enqueue((Object)connection);
                this.loseEnds.remove((Object)connection);
            }
        }
        return !this.loseEnds.isEmpty() && foundConnections.size() != this.cables.size();
    }

    void finishSplit() {
        ObjectLinkedOpenHashSet stored;
        Object2ObjectSortedMap todoList = CollectionUtils.createLinkedMap();
        for (IEnergySource source : this.sources) {
            stored = new ObjectLinkedOpenHashSet(this.sinks);
            for (EnergyNetLocal.EnergyPath path : this.subPaths.get(source)) {
                stored.remove(path.target);
            }
            if (stored.size() <= 0) continue;
            Iterator<EnergyNetLocal.EnergyPath> iter = stored.iterator();
            while (iter.hasNext()) {
                if (!this.local.connectivity.get(iter.next()).getEmitters().isEmpty()) continue;
                iter.remove();
            }
            if (stored.size() <= 0) continue;
            todoList.put((Object)source, (Object)stored);
        }
        for (IEnergySink sink : this.sinks) {
            stored = new ObjectLinkedOpenHashSet(this.sources);
            for (EnergyNetLocal.EnergyPath path : this.subPaths.getReverse(sink)) {
                stored.remove(path.source);
            }
            if (stored.size() <= 0) continue;
            for (IEnergySource source : stored) {
                if (this.local.connectivity.get(source).getReceivers().isEmpty()) continue;
                ObjectSortedSet list = (ObjectSortedSet)todoList.get((Object)source);
                if (list == null) {
                    list = CollectionUtils.createLinkedSet();
                    todoList.put((Object)source, list);
                }
                list.add((IEnergySink)sink);
            }
        }
        this.newSinks.clear();
        this.newSources.clear();
        if (todoList.size() > 0) {
            for (Map.Entry entry : Object2ObjectMaps.fastIterable(todoList)) {
                IEnergySource source = (IEnergySource)entry.getKey();
                HashSet<IEnergyTile> targets = new HashSet<IEnergyTile>((Collection)entry.getValue());
                this.subPaths.addSourcePaths(source, this.local.discoverPaths(source, targets, false, source.getMaxEnergyOutput()));
            }
        }
        this.clearFlag(2);
    }

    void setFlag(int flag) {
        this.flags |= flag;
    }

    void clearFlag(int flag) {
        this.flags &= ~flag;
    }

    boolean hasFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    public int hashCode() {
        return this.gridID;
    }

    public boolean equals(Object obj) {
        if (obj instanceof EnergyNetGrid) {
            return ((EnergyNetGrid)obj).gridID == this.gridID;
        }
        return false;
    }

    static EnergyNetGrid createNewGrid(EnergyNetLocal local) {
        return new EnergyNetGrid(GRID_ID++, local);
    }

    static class WireConnection
    implements Iterable<WireConnection> {
        IEnergyConductor owner;
        Set<WireConnection> links = CollectionUtils.createLinkedSet();

        public WireConnection(IEnergyConductor owner, EnergyNetGrid local) {
            this.owner = owner;
            for (EnergyNetLocal.EnergyLink link : local.local.connectivity.get(owner).getAll()) {
                WireConnection other;
                if (!(link.target instanceof IEnergyConductor) || (other = local.cables.get(link.target)) == null || !other.add(this)) continue;
                this.add(other);
            }
        }

        public void destroy() {
            for (WireConnection entry : this.links) {
                entry.remove(this);
            }
            this.links.clear();
        }

        public int getTotalConnections() {
            return this.links.size();
        }

        public boolean add(WireConnection other) {
            return this.links.add(other);
        }

        public void remove(WireConnection other) {
            this.links.remove(other);
        }

        @Override
        public Iterator<WireConnection> iterator() {
            return this.links.iterator();
        }
    }
}

