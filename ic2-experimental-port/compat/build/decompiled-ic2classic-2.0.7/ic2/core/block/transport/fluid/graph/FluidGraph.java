/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.Direction
 */
package ic2.core.block.transport.fluid.graph;

import ic2.api.util.DirectionList;
import ic2.core.block.transport.fluid.graph.FluidGrid;
import ic2.core.block.transport.fluid.graph.FluidPaths;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Direction;

public class FluidGraph {
    static final int DIRTY = 1;
    static final int SPLIT = 2;
    static final int RESET = 4;
    static final int POTENTIAL_SPLIT = 8;
    static final BitSet USED_GRIDS = new BitSet();
    FluidGrid grid;
    FluidPaths.FluidSubPaths paths;
    int gridId;
    int flags;
    Map<IFluidPipe, FluidConnection> pipes = CollectionUtils.createLinkedMap();
    Set<IFluidPipe.FluidOutput> outputs = CollectionUtils.createLinkedSet();
    Set<FluidGrid.DirSource> inputs = CollectionUtils.createLinkedSet();
    Set<IFluidPipe.FluidOutput> newOutputs = CollectionUtils.createLinkedSet();
    Set<FluidGrid.DirSource> newInputs = CollectionUtils.createLinkedSet();
    Set<FluidConnection> looseEnds = CollectionUtils.createLinkedSet();

    public FluidGraph(FluidGrid grid) {
        this.grid = grid;
        this.paths = grid.paths.createPaths();
        this.gridId = USED_GRIDS.nextClearBit(1);
        USED_GRIDS.set(this.gridId);
    }

    public int getId() {
        return this.gridId;
    }

    public void destroy() {
        this.paths.reset();
        this.paths = null;
        USED_GRIDS.clear(this.gridId);
    }

    public void addSource(IFluidPipe pipe, DirectionList list) {
        if (list == null) {
            this.addSource(new FluidGrid.DirSource(pipe, null));
        } else {
            for (Direction dir : list) {
                this.addSource(new FluidGrid.DirSource(pipe, dir));
            }
        }
    }

    private void addSource(FluidGrid.DirSource source) {
        if (this.inputs.add(source)) {
            this.newInputs.add(source);
            this.markDirty(false);
        }
    }

    public void addPipe(IFluidPipe pipe) {
        if (this.pipes.containsKey(pipe)) {
            return;
        }
        this.grid.pipesToGraph.put(pipe.getPosition().m_121878_(), this.gridId);
        FluidConnection connection = new FluidConnection(pipe, this);
        this.pipes.put(pipe, connection);
        List list = this.grid.outputs.getOrDefault(pipe, Collections.emptyList());
        if (list.size() > 0) {
            this.outputs.addAll(list);
            this.newOutputs.addAll(list);
            this.markDirty(false);
        }
        for (Direction dir : pipe.getEmitterSources()) {
            this.addSource(new FluidGrid.DirSource(pipe, dir));
        }
        if (connection.shouldReset() && this.flags == 0) {
            this.markDirty(false);
            this.setFlag(4);
            this.newOutputs.addAll(this.outputs);
            this.newInputs.addAll(this.inputs);
        }
    }

    public void addGraph(FluidGraph graph) {
        for (IFluidPipe pipe : graph.pipes.keySet()) {
            this.pipes.put(pipe, new FluidConnection(pipe, this));
        }
        this.outputs.addAll(graph.outputs);
        this.inputs.addAll(graph.inputs);
    }

    public void finishMerge() {
        for (IFluidPipe pipe : this.pipes.keySet()) {
            this.grid.pipesToGraph.put(pipe.getPosition().m_121878_(), this.gridId);
        }
        this.setFlag(4);
        this.markDirty(true);
    }

    public void removePipe(IFluidPipe pipe) {
        FluidConnection connection = this.pipes.remove(pipe);
        if (connection != null) {
            this.looseEnds.remove(connection);
            this.looseEnds.addAll(connection.links);
            connection.destroy();
            this.outputs.removeAll(this.grid.outputs.getOrDefault(pipe, Collections.emptyList()));
            this.inputs.removeAll(FluidGrid.DirSource.createSources(pipe));
            this.paths.removePaths(this.paths.getPathsForPipe(pipe));
        }
    }

    public void clearGrid() {
        this.paths.clear();
        this.paths = null;
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
        this.grid.dirtyGrids.add(this.gridId);
    }

    public void processChanges() {
        if (this.hasFlag(1)) {
            if (this.hasFlag(4)) {
                this.newOutputs.addAll(this.outputs);
                this.newInputs.addAll(this.inputs);
            }
            if (this.hasFlag(8)) {
                if (this.isGridSplit()) {
                    this.grid.splitGrid(this);
                    return;
                }
                this.newOutputs.addAll(this.outputs);
                this.newInputs.addAll(this.inputs);
            }
            if (this.newOutputs.size() > 0 && this.inputs.size() > 0 || this.newInputs.size() > 0 && this.outputs.size() > 0) {
                this.updatePaths();
            }
            this.flags = 0;
        }
    }

    boolean isGridSplit() {
        if (this.looseEnds.isEmpty()) {
            return false;
        }
        ObjectArrayFIFOQueue todoList = new ObjectArrayFIFOQueue();
        todoList.enqueue((Object)this.looseEnds.iterator().next());
        ObjectSet foundConnections = CollectionUtils.createSet();
        while (!todoList.isEmpty() && !this.looseEnds.isEmpty()) {
            foundConnections.add((FluidConnection)((FluidConnection)todoList.first()));
            this.looseEnds.remove(todoList.first());
            for (FluidConnection connection : ((FluidConnection)todoList.dequeue()).links) {
                if (!foundConnections.add((FluidConnection)connection)) continue;
                todoList.enqueue((Object)connection);
                this.looseEnds.remove(connection);
            }
        }
        return !this.looseEnds.isEmpty() && foundConnections.size() != this.pipes.size();
    }

    public void finishSplit() {
        this.clearFlag(2);
        this.markDirty(true);
        this.setFlag(4);
    }

    public void updatePaths() {
        Object2ObjectSortedMap targetOutputs = CollectionUtils.createLinkedMap();
        if (this.newOutputs.size() > 0) {
            for (FluidGrid.DirSource pump : this.inputs) {
                targetOutputs.put((Object)pump, (Object)new ObjectOpenHashSet(this.outputs));
            }
            this.newOutputs.clear();
            this.newInputs.clear();
        } else {
            for (FluidGrid.DirSource pump : this.newInputs) {
                targetOutputs.put((Object)pump, (Object)new ObjectOpenHashSet(this.outputs));
            }
            this.newInputs.clear();
        }
        for (Map.Entry entry : Object2ObjectMaps.fastIterable(targetOutputs)) {
            Set<FluidGrid.FluidPath> result = this.grid.generatePaths((FluidGrid.DirSource)entry.getKey(), (Set)entry.getValue());
            this.paths.removePaths(result);
            this.paths.addPaths((FluidGrid.DirSource)entry.getKey(), result);
        }
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

    static class FluidConnection {
        IFluidPipe pipe;
        Set<FluidConnection> links = CollectionUtils.createLinkedSet();

        public FluidConnection(IFluidPipe pipe, FluidGraph graph) {
            this.pipe = pipe;
            for (FluidGrid.FluidLink link : graph.grid.connections.get(pipe).getLinks()) {
                FluidConnection other = graph.pipes.get(link.target);
                if (other == null) continue;
                other.links.add(this);
                this.links.add(other);
            }
        }

        public boolean shouldReset() {
            return this.links.size() > 1;
        }

        public void destroy() {
            for (FluidConnection connection : this.links) {
                connection.links.remove(this);
            }
            this.links.clear();
        }
    }
}

