/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
 *  it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.transport.fluid.graph;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.transport.fluid.graph.FluidGraph;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.FluidPaths;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidGrid {
    static final Sorter SORTER = new Sorter();
    Level world;
    FluidPaths paths = new FluidPaths();
    Long2ObjectMap<IFluidPipe> pipes = new Long2ObjectOpenHashMap();
    Map<IFluidPipe, Connection> connections = CollectionUtils.createMap();
    Map<IFluidPipe, List<IFluidPipe.FluidOutput>> outputs = CollectionUtils.createMap();
    Int2ObjectMap<FluidGraph> graphs = new Int2ObjectOpenHashMap();
    Long2IntMap pipesToGraph = new Long2IntOpenHashMap();
    IntSet dirtyGrids = new IntLinkedOpenHashSet();

    FluidGrid(Level world) {
        this.world = world;
    }

    public void onTick() {
        if (this.dirtyGrids.size() > 0) {
            int[] grids = this.dirtyGrids.toIntArray();
            this.dirtyGrids.clear();
            for (int i = 0; i < grids.length; ++i) {
                FluidGraph graph = (FluidGraph)this.graphs.get(grids[i]);
                if (graph == null || graph.pipes.isEmpty()) continue;
                graph.processChanges();
            }
        }
    }

    public List<FluidPath> getPaths(IFluidPipe pipe, Direction dir) {
        return new ObjectArrayList(this.paths.getPaths(new DirSource(pipe, dir)));
    }

    public int pump(IFluidPipe pipe, Direction dir, FluidStack fluid, int pressure, IFluidHandler.FluidAction action) {
        return fluid.isEmpty() || pressure <= 0 ? 0 : this.sendFluids(this.paths.getPaths(new DirSource(pipe, dir)), fluid, pressure, action);
    }

    public void updatePipe(IFluidPipe pipe) {
        if (this.pipes.containsKey(pipe.getPosition().m_121878_())) {
            this.removePipe(pipe);
            this.addPipe(pipe);
        }
    }

    public void addPipe(IFluidPipe pipe) {
        long pos = pipe.getPosition().m_121878_();
        if (this.pipes.containsKey(pos)) {
            return;
        }
        this.pipes.put(pos, (Object)pipe);
        this.update(pipe.getPosition());
        List<IFluidPipe.FluidOutput> outs = pipe.getOutputs();
        if (outs.size() > 0) {
            this.outputs.put(pipe, outs);
        }
        Connection connect = new Connection(pipe);
        this.connections.put(pipe, connect);
        IntLinkedOpenHashSet combine = new IntLinkedOpenHashSet();
        for (FluidLink link : connect.getLinks()) {
            combine.add(this.pipesToGraph.get(link.targetPos.m_121878_()));
        }
        if (combine.isEmpty()) {
            graph = new FluidGraph(this);
            graph.addPipe(pipe);
            this.graphs.put(graph.getId(), (Object)graph);
        } else if (combine.size() == 1) {
            ((FluidGraph)this.graphs.get(combine.firstInt())).addPipe(pipe);
        } else {
            graph = new FluidGraph(this);
            this.graphs.put(graph.getId(), (Object)graph);
            IntBidirectionalIterator intBidirectionalIterator = combine.iterator();
            while (intBidirectionalIterator.hasNext()) {
                int id = (Integer)intBidirectionalIterator.next();
                FluidGraph other = (FluidGraph)this.graphs.remove(id);
                graph.addGraph(other);
                other.destroy();
            }
            graph.addPipe(pipe);
            graph.finishMerge();
        }
    }

    public void removePipe(IFluidPipe pipe) {
        List<IFluidPipe.FluidOutput> outputList;
        long pos = pipe.getPosition().m_121878_();
        if (!this.pipes.containsKey(pos)) {
            return;
        }
        pipe = (IFluidPipe)this.pipes.remove(pos);
        this.update(pipe.getPosition());
        Connection connect = this.connections.remove(pipe);
        if (connect == null) {
            return;
        }
        connect.destroy();
        FluidGraph graph = (FluidGraph)this.graphs.get(this.pipesToGraph.remove(pos));
        switch (connect.getConnections()) {
            case 0: {
                graph.removePipe(pipe);
                if (!graph.pipes.isEmpty()) break;
                this.graphs.remove(graph.getId());
                break;
            }
            case 1: {
                graph.removePipe(pipe);
                break;
            }
            default: {
                graph.removePipe(pipe);
                graph.markSplit();
            }
        }
        if ((outputList = this.outputs.remove(pipe)) == null) {
            return;
        }
        for (IFluidPipe.FluidOutput out : outputList) {
            this.paths.removeSink(out);
        }
    }

    public IFluidPipe getPipe(BlockPos pos) {
        return (IFluidPipe)this.pipes.get(pos.m_121878_());
    }

    void splitGrid(FluidGraph grid) {
        this.graphs.remove(grid.getId());
        ObjectList gridsToAdd = CollectionUtils.createList();
        ObjectSortedSet left = CollectionUtils.createLinkedSet();
        ObjectSortedSet processed = CollectionUtils.createLinkedSet();
        left.addAll(grid.pipes.keySet());
        while (left.size() > 0) {
            FluidGraph graph = new FluidGraph(this);
            graph.setFlag(2);
            PriorityQueue toProcess = CollectionUtils.createInsertionQueue();
            toProcess.enqueue((Object)((IFluidPipe)left.iterator().next()));
            while (!toProcess.isEmpty()) {
                IFluidPipe pipe = (IFluidPipe)toProcess.dequeue();
                if (!this.pipes.containsKey(pipe.getPosition().m_121878_())) continue;
                left.remove(pipe);
                graph.addPipe(pipe);
                Connection targets = this.connections.get(pipe);
                for (FluidLink link : targets.getLinks()) {
                    IFluidPipe tile = link.target;
                    if (!processed.add((IFluidPipe)tile) || !left.remove(tile)) continue;
                    toProcess.enqueue((Object)tile);
                }
            }
            gridsToAdd.add((FluidGraph)graph);
            this.graphs.put(graph.getId(), (Object)graph);
            graph.finishSplit();
        }
        grid.destroy();
        for (FluidGraph graph : gridsToAdd) {
            graph.paths.addSubSet();
        }
    }

    public Set<FluidPath> generatePaths(DirSource source, Set<IFluidPipe.FluidOutput> targetOutputs) {
        Object2ObjectSortedMap<IFluidPipe, FluidBlock> reachedLinks = CollectionUtils.createLinkedMap();
        LinkedList<IFluidPipe> toCheck = new LinkedList<IFluidPipe>();
        toCheck.add(source.pos());
        ObjectLinkedOpenHashSet outputsReached = new ObjectLinkedOpenHashSet(targetOutputs);
        while (!toCheck.isEmpty()) {
            IFluidPipe pipe = (IFluidPipe)toCheck.pollFirst();
            for (IFluidPipe.FluidOutput output : this.outputs.getOrDefault(pipe, Collections.emptyList())) {
                if (pipe == source.pos() && output.dir == source.dir()) continue;
                outputsReached.remove(output);
            }
            FluidBlock block = (FluidBlock)reachedLinks.get(pipe);
            int pressure = block == null ? 0 : block.pressureRequired;
            for (FluidLink link : this.connections.get(pipe).getLinks(true)) {
                IFluidPipe target = link.target;
                if (target == source.pos()) continue;
                int extra = link.direction.m_122434_().m_122478_() ? (link.direction.m_122421_() == Direction.AxisDirection.POSITIVE ? 2 : 0) : 1;
                block = (FluidBlock)reachedLinks.get(target);
                if (block != null && block.pressureRequired <= pressure + extra) continue;
                reachedLinks.put(target, new FluidBlock(link, pressure + extra));
                toCheck.remove(target);
                toCheck.add(target);
            }
        }
        targetOutputs.removeAll((Collection<?>)outputsReached);
        if (targetOutputs.isEmpty()) {
            return Collections.emptySet();
        }
        ObjectSortedSet outputs = CollectionUtils.createLinkedSet();
        for (IFluidPipe.FluidOutput output : targetOutputs) {
            IFluidPipe pipe = output.source;
            FluidBlock block = (FluidBlock)reachedLinks.get(pipe);
            if (block == null && pipe != source.pos()) continue;
            FluidPath path = new FluidPath();
            path.resistance = Math.max(1, block == null ? 0 : block.pressureRequired);
            path.source = source;
            path.output = output;
            path.paths.add(pipe);
            if (block != null) {
                while (true) {
                    IFluidPipe tile;
                    if ((tile = block.source) == source.pos()) {
                        path.paths.add(tile);
                        break;
                    }
                    path.paths.add(tile);
                    block = (FluidBlock)reachedLinks.get(tile);
                    if (block != null) continue;
                    IC2.PLATFORM.displayError("An Fluid network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: Fluid Block, tile entities below)\nE: " + source + " (" + source.pos().getPosition() + ")\nC: " + tile + " (" + tile.getPosition() + ")\nR: " + path.output + " (" + path.output.source.getPosition() + ")");
                }
            }
            outputs.add((FluidPath)path);
        }
        return outputs;
    }

    void update(BlockPos pos) {
        for (Direction facing : DirectionList.ALL) {
            BlockPos sidedPos = pos.m_121945_(facing);
            if (!this.world.m_46749_(sidedPos)) continue;
            this.world.m_46586_(sidedPos, Blocks.f_50016_, pos);
        }
        if (this.world.m_46749_(pos)) {
            this.world.m_46586_(pos, Blocks.f_50016_, pos);
        }
    }

    ObjectList<FluidPath> create(int prio) {
        return CollectionUtils.createList();
    }

    int sendFluids(Set<FluidPath> targetPumps, FluidStack stack, int pressure, IFluidHandler.FluidAction action) {
        if (targetPumps.isEmpty()) {
            return 0;
        }
        Fluid fluid = stack.getFluid();
        Int2ObjectAVLTreeMap paths = new Int2ObjectAVLTreeMap();
        for (FluidPath path : targetPumps) {
            if (path.resistance >= pressure || !path.output.isFluidAllowed(fluid)) continue;
            ((ObjectList)paths.computeIfAbsent(path.output.getPriority(), this::create)).add((Object)path);
        }
        if (paths.isEmpty()) {
            return 0;
        }
        boolean adding = action.execute();
        int send = 0;
        for (ObjectList activePaths : paths.values()) {
            while (!activePaths.isEmpty()) {
                int left = stack.getAmount() - send;
                if (left <= 0) {
                    return send;
                }
                Collections.shuffle(activePaths);
                while (left < activePaths.size()) {
                    activePaths.remove(activePaths.size() - 1);
                }
                int perTarget = left / activePaths.size();
                int m = activePaths.size();
                for (int i = 0; i < m; ++i) {
                    FluidPath path = (FluidPath)activePaths.get(i);
                    int added = path.output.handler.fill(new FluidStack(stack, perTarget), action);
                    send += added;
                    if (adding) {
                        path.fluidSend += (long)added;
                    }
                    if (send >= stack.getAmount()) {
                        return send;
                    }
                    if (added >= perTarget) continue;
                    activePaths.remove(i--);
                    --m;
                }
            }
        }
        return send;
    }

    public FluidNet.TransportStats getStats(IFluidPipe tile) {
        long in = 0L;
        long out = 0L;
        FluidGraph graph = (FluidGraph)this.graphs.get(this.pipesToGraph.get(tile.getPosition().m_121878_()));
        if (graph != null) {
            for (FluidPath path : graph.paths.getPathsForPipe(tile)) {
                in += path.fluidSend;
                out += path.fluidSend;
            }
        }
        return new FluidNet.TransportStats(in, out);
    }

    public record DirSource(IFluidPipe pos, Direction dir) {
        public static List<DirSource> createSources(IFluidPipe pipe) {
            ObjectArrayList list = new ObjectArrayList(7);
            for (Direction dir : DirectionList.ALL) {
                list.add(new DirSource(pipe, dir));
            }
            list.add(new DirSource(pipe, null));
            return list;
        }
    }

    class Connection {
        IFluidPipe pipe;
        Set<FluidLink> receive = CollectionUtils.createLinkedSet();
        Set<FluidLink> send = CollectionUtils.createLinkedSet();
        Set<FluidLink> links = CollectionUtils.createLinkedSet();

        public Connection(IFluidPipe pipe) {
            this.pipe = pipe;
            long pos = pipe.getPosition().m_121878_();
            for (Direction dir : DirectionList.ALL) {
                FluidLink link;
                Connection connect = FluidGrid.this.connections.get(FluidGrid.this.pipes.get(BlockPos.m_121915_((long)pos, (Direction)dir)));
                if (connect == null) continue;
                if (pipe.canPushFluid(dir) && connect.pipe.canReceiveFluid(dir.m_122424_())) {
                    link = new FluidLink(pipe, connect.pipe, dir);
                    this.add(link, true);
                    connect.add(link.invert(), false);
                }
                if (!pipe.canReceiveFluid(dir) || !connect.pipe.canPushFluid(dir.m_122424_())) continue;
                link = new FluidLink(pipe, connect.pipe, dir);
                this.add(link, false);
                connect.add(link.invert(), true);
            }
        }

        void add(FluidLink link, boolean push) {
            this.links.add(link);
            (push ? this.send : this.receive).add(link);
        }

        void remove(FluidLink link, boolean push) {
            this.links.remove(link);
            (push ? this.send : this.receive).remove(link);
        }

        public Set<FluidLink> getLinks() {
            return this.links;
        }

        public Set<FluidLink> getLinks(boolean push) {
            return push ? this.send : this.receive;
        }

        public int getConnections() {
            return this.links.size();
        }

        public void destroy() {
            Connection other;
            for (FluidLink link : this.send) {
                other = FluidGrid.this.connections.get(link.target);
                if (other == null) continue;
                other.remove(link.invert(), false);
            }
            for (FluidLink link : this.receive) {
                other = FluidGrid.this.connections.get(link.target);
                if (other == null) continue;
                other.remove(link.invert(), true);
            }
        }
    }

    class FluidLink {
        public IFluidPipe source;
        public BlockPos sourcePos;
        public IFluidPipe target;
        public BlockPos targetPos;
        public Direction direction;
        FluidLink invert;

        private FluidLink() {
        }

        public FluidLink(IFluidPipe source, IFluidPipe target, Direction direction) {
            this.source = source;
            this.sourcePos = source.getPosition();
            this.target = target;
            this.targetPos = target.getPosition();
            this.direction = direction;
            this.invert = new FluidLink();
            this.invert.source = target;
            this.invert.sourcePos = this.targetPos;
            this.invert.target = source;
            this.invert.targetPos = this.sourcePos;
            this.invert.direction = direction.m_122424_();
            this.invert.invert = this;
        }

        public FluidLink invert() {
            return this.invert;
        }

        public int hashCode() {
            return Objects.hash(this.targetPos, this.direction);
        }

        public boolean equals(Object obj) {
            if (obj instanceof FluidLink) {
                FluidLink other = (FluidLink)obj;
                return other.targetPos.equals((Object)this.targetPos) && other.direction == this.direction;
            }
            return false;
        }

        public String toString() {
            return "Link: Source[" + this.source + "(" + this.sourcePos + ")], Target[" + this.target + "(" + this.targetPos + ")]";
        }
    }

    static class FluidBlock {
        IFluidPipe source;
        BlockPos sourcePos;
        IFluidPipe target;
        BlockPos targetPos;
        Direction direction;
        int pressureRequired;

        public FluidBlock(FluidLink link, int pressureRequired) {
            this.source = link.source;
            this.sourcePos = link.sourcePos;
            this.target = link.target;
            this.targetPos = link.targetPos;
            this.direction = link.direction;
            this.pressureRequired = pressureRequired;
        }
    }

    public class FluidPath {
        DirSource source;
        IFluidPipe.FluidOutput output;
        int resistance;
        Set<IFluidPipe> paths = CollectionUtils.createSet();
        long fluidSend;

        public int hashCode() {
            return this.source.hashCode() + Long.hashCode(this.output.position) * 31;
        }

        public boolean equals(Object obj) {
            if (obj instanceof FluidPath) {
                FluidPath path = (FluidPath)obj;
                return path.source == this.source && path.output.position == this.output.position;
            }
            return false;
        }

        public BlockPos getTarget() {
            return BlockPos.m_122022_((long)this.output.position);
        }
    }

    static class Sorter
    implements Comparator<FluidPath> {
        Sorter() {
        }

        @Override
        public int compare(FluidPath o1, FluidPath o2) {
            int result = Integer.compare(o2.output.getPriority(), o1.output.getPriority());
            return result != 0 ? result : Integer.compare(o1.resistance, o2.resistance);
        }
    }
}

