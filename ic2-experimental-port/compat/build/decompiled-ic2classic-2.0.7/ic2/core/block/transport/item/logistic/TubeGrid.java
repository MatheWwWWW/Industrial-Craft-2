/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
 *  it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.block.transport.item.logistic;

import ic2.api.tiles.tubes.ILimiterTube;
import ic2.api.tiles.tubes.IProviderTube;
import ic2.api.tiles.tubes.IRequestTube;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.transport.item.logistic.RequestGrid;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.LongAverager;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class TubeGrid {
    Long2ObjectMap<ITube> tubes = new Long2ObjectOpenHashMap();
    Object2ObjectMap<ITube, Connection> connections = new Object2ObjectLinkedOpenHashMap();
    Long2IntMap tubeToGrid = new Long2IntOpenHashMap();
    IntSet dirtyGrids = new IntLinkedOpenHashSet();
    Int2ObjectMap<RequestGrid> grids = new Int2ObjectOpenHashMap();
    LongAverager totalLag = new LongAverager(200);
    Level world;

    public TubeGrid(Level world) {
        this.world = world;
    }

    public void onTick() {
        if (this.dirtyGrids.size() > 0) {
            int[] gridIds = this.dirtyGrids.toIntArray();
            this.dirtyGrids.clear();
            for (int i = 0; i < gridIds.length; ++i) {
                RequestGrid grid = (RequestGrid)this.grids.get(gridIds[i]);
                if (grid == null || grid.tubes.isEmpty()) continue;
                grid.processChanges();
            }
        }
        long start = System.nanoTime();
        for (RequestGrid grid : this.grids.values()) {
            grid.tick();
        }
        this.totalLag.addEntry(System.nanoTime() - start);
    }

    public void printLag(ITube tube, Player player) {
        long pos = tube.getPosition().m_121878_();
        if (!this.tubes.containsKey(pos)) {
            return;
        }
        RequestGrid grid = (RequestGrid)this.grids.get(this.tubeToGrid.get(pos));
        if (grid != null) {
            StringBuilder builder = new StringBuilder();
            long lag = this.totalLag.getCachedAverage();
            builder.append("Global Lag: ms=").append(lag / 1000000L).append(", \u00b5s=").append(lag / 1000L).append(", ns=").append(lag).append("\n");
            lag = grid.totalLag.getCachedAverage();
            builder.append("Grid Lag: ms=").append(lag / 1000000L).append(", \u00b5s=").append(lag / 1000L).append(", ns=").append(lag).append("\n");
            player.m_213846_((Component)Component.m_237113_((String)builder.toString()));
        }
    }

    public void addTube(ITube tube) {
        long pos = tube.getPosition().m_121878_();
        if (this.tubes.containsKey(pos)) {
            return;
        }
        this.tubes.put(pos, (Object)tube);
        this.update(tube.getPosition());
        Connection connect = new Connection(tube);
        this.connections.put((Object)tube, (Object)connect);
        IntLinkedOpenHashSet combine = new IntLinkedOpenHashSet();
        for (TubeLink link : connect.getLinks()) {
            combine.add(this.tubeToGrid.get(link.targetPos.m_121878_()));
        }
        if (combine.isEmpty()) {
            graph = new RequestGrid(this);
            graph.addTube(tube);
            this.grids.put(graph.getId(), (Object)graph);
        } else if (combine.size() == 1) {
            ((RequestGrid)this.grids.get(combine.firstInt())).addTube(tube);
        } else {
            graph = new RequestGrid(this);
            this.grids.put(graph.getId(), (Object)graph);
            IntBidirectionalIterator intBidirectionalIterator = combine.iterator();
            while (intBidirectionalIterator.hasNext()) {
                int id = (Integer)intBidirectionalIterator.next();
                RequestGrid other = (RequestGrid)this.grids.remove(id);
                graph.addGrid(other);
                other.destroy();
            }
            graph.addTube(tube);
            graph.finishMerge();
        }
    }

    public void updateTube(ITube tube) {
        long pos = tube.getPosition().m_121878_();
        if (this.tubes.containsKey(pos)) {
            this.removeTube(tube);
            this.addTube(tube);
        }
    }

    public void removeTube(ITube tube) {
        long pos = tube.getPosition().m_121878_();
        if (!this.tubes.containsKey(pos)) {
            return;
        }
        tube = (ITube)this.tubes.remove(pos);
        this.update(tube.getPosition());
        Connection connect = (Connection)this.connections.remove((Object)tube);
        connect.destroy();
        RequestGrid graph = (RequestGrid)this.grids.get(this.tubeToGrid.remove(pos));
        switch (connect.getLinks().size()) {
            case 0: {
                graph.removeTube(tube);
                if (!graph.tubes.isEmpty()) break;
                this.grids.remove(graph.getId());
                break;
            }
            case 1: {
                graph.removeTube(tube);
                break;
            }
            default: {
                graph.removeTube(tube);
                graph.markSplit();
            }
        }
    }

    public void clearRequests(IRequestTube tube) {
        long pos = tube.getPosition().m_121878_();
        if (!this.tubes.containsKey(pos)) {
            return;
        }
        RequestGrid grid = (RequestGrid)this.grids.get(this.tubeToGrid.get(pos));
        if (grid != null) {
            grid.clearRequests(tube);
        }
    }

    public Object2IntMap<Item> getPotentialItems(IRequestTube tube) {
        long pos = tube.getPosition().m_121878_();
        if (this.tubes.containsKey(pos)) {
            RequestGrid grid = (RequestGrid)this.grids.get(this.tubeToGrid.get(pos));
            return grid.getPotentialItems();
        }
        return Object2IntMaps.emptyMap();
    }

    public void onRequestLost(ITube tube, ItemStack stack, UUID requestId) {
        long pos = tube.getPosition().m_121878_();
        if (!this.tubes.containsKey(pos)) {
            return;
        }
        RequestGrid grid = (RequestGrid)this.grids.get(this.tubeToGrid.get(pos));
        if (grid != null) {
            grid.onRequestLost(requestId, stack);
        }
    }

    void splitGrid(RequestGrid grid) {
        this.grids.remove(grid.getId());
        ObjectList gridsToAdd = CollectionUtils.createList();
        ObjectSortedSet left = CollectionUtils.createLinkedSet();
        ObjectSortedSet processed = CollectionUtils.createLinkedSet();
        left.addAll(grid.tubes.keySet());
        while (left.size() > 0) {
            RequestGrid graph = new RequestGrid(this);
            graph.setFlag(2);
            PriorityQueue toProcess = CollectionUtils.createInsertionQueue();
            toProcess.enqueue((Object)((ITube)left.first()));
            while (!toProcess.isEmpty()) {
                ITube tube = (ITube)toProcess.dequeue();
                if (!this.tubes.containsKey(tube.getPosition().m_121878_())) continue;
                left.remove((Object)tube);
                graph.addTube(tube);
                Connection targets = (Connection)this.connections.get((Object)tube);
                for (TubeLink link : targets.getLinks()) {
                    ITube tile = link.target;
                    if (!processed.add((ITube)tile) || !left.remove((Object)tile)) continue;
                    toProcess.enqueue((Object)tile);
                }
            }
            gridsToAdd.add((RequestGrid)graph);
            this.grids.put(graph.getId(), (Object)graph);
            graph.finishSplit();
        }
        grid.destroy();
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

    public List<TubePath> discoverPaths(IRequestTube request, Set<IProviderTube> targets) {
        Object2ObjectSortedMap<ITube, TubeBlock> reachedLinks = CollectionUtils.createLinkedMap();
        LinkedList<ITube> toCheck = new LinkedList<ITube>();
        toCheck.add(request);
        ObjectLinkedOpenHashSet targetsReached = new ObjectLinkedOpenHashSet(targets);
        while (!toCheck.isEmpty()) {
            ITube tube = (ITube)toCheck.pollFirst();
            TubeBlock block = (TubeBlock)reachedLinks.get(tube);
            int distanceWalked = block == null ? 0 : block.distanceWalked;
            for (TubeLink link : ((Connection)this.connections.get((Object)tube)).getLinks()) {
                ITube target = link.target;
                if (target == request) continue;
                int extra = target instanceof ILimiterTube ? 10000 : 1;
                block = (TubeBlock)reachedLinks.get(target);
                if (block != null && block.distanceWalked <= distanceWalked + extra) continue;
                if (target instanceof IProviderTube) {
                    IProviderTube provider = (IProviderTube)target;
                    targetsReached.remove(provider);
                }
                reachedLinks.put(target, new TubeBlock(link, distanceWalked + extra));
                toCheck.remove(target);
                toCheck.add(target);
            }
        }
        targets.removeAll((Collection<?>)targetsReached);
        if (targets.isEmpty()) {
            return Collections.emptyList();
        }
        ObjectList results = CollectionUtils.createList();
        for (IProviderTube target : targets) {
            TubeBlock block = (TubeBlock)reachedLinks.get(target);
            if (block == null) continue;
            EnumSet<DyeColor> validColors = EnumSet.allOf(DyeColor.class);
            boolean hasLimits = false;
            while (true) {
                ITube tube;
                if ((tube = block.source) instanceof ILimiterTube) {
                    ILimiterTube limiter = (ILimiterTube)tube;
                    validColors.retainAll(limiter.getValidColors());
                    hasLimits = true;
                }
                if (tube == request) break;
                block = (TubeBlock)reachedLinks.get(tube);
                if (block != null) continue;
                IC2.PLATFORM.displayError("An Tube network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: Tube Block, tile entities below)\nE: " + request + " (" + request.getPosition() + ")\nC: " + tube + " (" + tube.getPosition() + ")\nR: " + target + " (" + target.getPosition() + ")");
            }
            TubePath path = new TubePath();
            path.request = request;
            path.provider = target;
            path.validColors = hasLimits ? this.getLimitedColors(validColors) : -1;
            results.add((TubePath)path);
        }
        return results;
    }

    private int getLimitedColors(EnumSet<DyeColor> valid) {
        int color = 0;
        for (DyeColor dye : valid) {
            color |= 1 << dye.m_41060_();
        }
        return color;
    }

    class Connection {
        ITube tube;
        Set<TubeLink> links = new ObjectLinkedOpenHashSet();

        public Connection(ITube tube) {
            this.tube = tube;
            long pos = tube.getPosition().m_121878_();
            for (Direction dir : DirectionList.ALL) {
                Connection connect = (Connection)TubeGrid.this.connections.get(TubeGrid.this.tubes.get(BlockPos.m_121915_((long)pos, (Direction)dir)));
                if (connect == null || !tube.canConnect(connect.tube, dir) || !connect.tube.canConnect(tube, dir.m_122424_())) continue;
                TubeLink link = new TubeLink(tube, connect.tube, dir);
                this.add(link);
                connect.add(link.invert());
            }
        }

        void add(TubeLink link) {
            this.links.add(link);
        }

        void remove(TubeLink link) {
            this.links.remove(link);
        }

        public Set<TubeLink> getLinks() {
            return this.links;
        }

        public void destroy() {
            for (TubeLink link : this.links) {
                Connection other = (Connection)TubeGrid.this.connections.get((Object)link.target);
                if (other == null) continue;
                other.remove(link.invert());
            }
        }
    }

    class TubeLink {
        public ITube source;
        public BlockPos sourcePos;
        public ITube target;
        public BlockPos targetPos;
        public Direction direction;
        TubeLink invert;

        private TubeLink() {
        }

        public TubeLink(ITube source, ITube target, Direction direction) {
            this.source = source;
            this.sourcePos = source.getPosition();
            this.target = target;
            this.targetPos = target.getPosition();
            this.direction = direction;
            this.invert = new TubeLink();
            this.invert.source = target;
            this.invert.sourcePos = this.targetPos;
            this.invert.target = source;
            this.invert.targetPos = this.sourcePos;
            this.invert.direction = direction.m_122424_();
            this.invert.invert = this;
        }

        public TubeLink invert() {
            return this.invert;
        }

        public int hashCode() {
            return Objects.hash(this.targetPos, this.direction);
        }

        public boolean equals(Object obj) {
            if (obj instanceof TubeLink) {
                TubeLink other = (TubeLink)obj;
                return other.targetPos.equals((Object)this.targetPos) && other.direction == this.direction;
            }
            return false;
        }

        public String toString() {
            return "Link: Source[" + this.source + "(" + this.sourcePos + ")], Target[" + this.target + "(" + this.targetPos + ")]";
        }
    }

    static class TubeBlock {
        ITube source;
        BlockPos sourcePos;
        ITube target;
        BlockPos targetPos;
        Direction direction;
        int distanceWalked;

        public TubeBlock(TubeLink link, int distanceWalked) {
            this.source = link.source;
            this.sourcePos = link.sourcePos;
            this.target = link.target;
            this.targetPos = link.targetPos;
            this.direction = link.direction;
            this.distanceWalked = distanceWalked;
        }
    }

    public static class TubePath {
        IRequestTube request;
        int validColors;
        IProviderTube provider;
    }
}

