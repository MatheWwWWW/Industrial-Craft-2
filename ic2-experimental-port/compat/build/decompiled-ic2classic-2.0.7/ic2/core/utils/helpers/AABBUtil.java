/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.Beta
 *  com.mojang.datafixers.util.Either
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectArrayPriorityQueue
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ChunkHolder
 *  net.minecraft.server.level.ServerChunkCache
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.utils.helpers;

import com.google.common.annotations.Beta;
import com.mojang.datafixers.util.Either;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.platform.corehacks.mixins.server.ChunkHolderMixin;
import ic2.core.platform.corehacks.mixins.server.ChunkProviderMixin;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.WorldRegion;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayPriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

public class AABBUtil {
    public static final int CHUNK_LOAD = 1;
    public static final int IGNORE_INVALID = 2;
    public static final int RANDOM_START = 4;
    public static final int HOLLOW_ITERATOR = 8;
    public static final int INSTANT_LOADER = 16;
    public static final int MAPPED_POSITIONS = 32;

    public static int countBlocks(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions) {
        return AABBUtil.getValidBlocks(world, start, Box.withRange(start, radius), filter, flags, directions, Integer.MAX_VALUE).size();
    }

    public static int countBlocks(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions, int limit) {
        return AABBUtil.getValidBlocks(world, start, Box.withRange(start, radius), filter, flags, directions, limit).size();
    }

    public static int countBlocks(Level world, BlockPos start, Box box, IBlockFilter filter, int flags, DirectionList directions, int limit) {
        return AABBUtil.getValidBlocks(world, start, box, filter, flags, directions, limit).size();
    }

    public static SearchResult getValidBlocks(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions) {
        return AABBUtil.getValidBlocks(world, start, Box.withRange(start, radius), filter, flags, directions, Integer.MAX_VALUE);
    }

    public static SearchResult getValidBlocks(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions, int limit) {
        return AABBUtil.getValidBlocks(world, start, Box.withRange(start, radius), filter, flags, directions, limit);
    }

    public static QueuedSearcher createSubTask(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions) {
        return AABBUtil.createSubTask(world, start, Box.withRange(start, radius), filter, flags, directions, Integer.MAX_VALUE);
    }

    public static QueuedSearcher createSubTask(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions, int limit) {
        return AABBUtil.createSubTask(world, start, Box.withRange(start, radius), filter, flags, directions, limit);
    }

    public static QueuedSearcher createSubTask(Level world, BlockPos start, Box box, IBlockFilter filter, int flags, DirectionList directions, int limit) {
        return new QueuedSearcher(world, start, box, filter, flags, directions, limit);
    }

    @Beta
    public static void createOffthreadTask(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions, Consumer<SearchResult> listener) {
        AABBUtil.createOffthreadTask(world, start, Box.withRange(start, radius), filter, flags, directions, Integer.MAX_VALUE, listener);
    }

    @Beta
    public static void createOffthreadTask(Level world, BlockPos start, int radius, IBlockFilter filter, int flags, DirectionList directions, int limit, Consumer<SearchResult> listener) {
        AABBUtil.createOffthreadTask(world, start, Box.withRange(start, radius), filter, flags, directions, limit, listener);
    }

    @Beta
    public static void createOffthreadTask(Level world, BlockPos start, Box box, IBlockFilter filter, int flags, DirectionList directions, int limit, Consumer<SearchResult> listener) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            throw new IllegalStateException("Only Usable on a Server!");
        }
        IC2.OFF_THREAD_WORKER.execute(new ThreadedSearcher(new WorldRegion(world, box, true, true), start, box, filter, directions, flags, limit, T -> server.execute(() -> listener.accept((SearchResult)T))));
    }

    public static void getBlockEntities(Level world, Consumer<BlockEntity> result) {
        if (world instanceof ServerLevel) {
            ServerLevel server = (ServerLevel)world;
            AABBUtil.getBlockEntities(server, result);
        }
    }

    public static void getBlockEntities(ServerLevel world, Consumer<BlockEntity> result) {
        ServerChunkCache cache = world.m_7726_();
        for (ChunkHolder holder : ((ChunkProviderMixin)cache.f_8325_).getVisibleChunks()) {
            ChunkHolderMixin mixin = (ChunkHolderMixin)holder;
            LevelChunk chunk = mixin.getLoadingChunk();
            if (chunk == null) {
                ChunkAccess testChunk;
                Either either = holder.m_140080_(ChunkStatus.f_62326_).getNow(null);
                if (either == null || !((testChunk = (ChunkAccess)either.left().orElse(null)) instanceof LevelChunk)) continue;
                chunk = (LevelChunk)testChunk;
            }
            chunk.m_62954_().values().forEach(result);
        }
    }

    public static SearchResult getValidBlocks(Level world, BlockPos start, Box box, IBlockFilter filter, int flags, DirectionList directions, int limit) {
        ObjectList resultList = CollectionUtils.createList();
        Object2ObjectMap mapped_result = (flags & 0x20) != 0 ? CollectionUtils.createLinkedMap() : Object2ObjectMaps.emptyMap();
        boolean map_results = (flags & 0x20) != 0;
        boolean chunkLoad = (flags & 1) != 0;
        WorldRegion reader = new WorldRegion(world, box, (flags & 1) != 0);
        if ((flags & 2) != 0) {
            for (BlockPos blockPos : (flags & 8) != 0 ? box.getHollowIterator() : box) {
                if (!chunkLoad && !reader.m_7232_(blockPos.m_123341_() >> 4, blockPos.m_123343_() >> 4) || !filter.isValid(reader, blockPos)) continue;
                BlockPos immutable = blockPos.m_7949_();
                resultList.add((Object)immutable);
                AABBUtil.mapResult(filter, reader, blockPos, map_results, (Map<Block, List<BlockPos>>)mapped_result);
                if (resultList.size() < limit) continue;
                return new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result);
            }
            return new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result);
        }
        ObjectArrayFIFOQueue queue = new ObjectArrayFIFOQueue();
        ObjectSet objectSet = CollectionUtils.createSet();
        for (Direction direction : (flags & 4) != 0 ? directions.getRandomIterator() : directions) {
            queue.enqueue((Object)start.m_121945_(direction));
        }
        while (queue.size() > 0) {
            BlockPos toProcess = (BlockPos)queue.dequeue();
            if (!box.intersectsWith(toProcess) || !objectSet.add((BlockPos)toProcess) || !chunkLoad && !reader.m_7232_(toProcess.m_123341_() >> 4, toProcess.m_123343_() >> 4) || !filter.isValid(reader, toProcess)) continue;
            resultList.add((Object)toProcess);
            AABBUtil.mapResult(filter, reader, toProcess, map_results, (Map<Block, List<BlockPos>>)mapped_result);
            if (resultList.size() >= limit) {
                return new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result);
            }
            for (Direction dir : directions) {
                BlockPos pos = toProcess.m_121945_(dir);
                if (!box.intersectsWith(pos) || objectSet.contains(pos)) continue;
                queue.enqueue((Object)pos);
            }
        }
        return new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result);
    }

    private static void mapResult(IBlockFilter filter, LevelReader reader, BlockPos pos, boolean doMap, Map<Block, List<BlockPos>> mapper) {
        if (doMap) {
            if (filter instanceof IBlockMapper) {
                ((IBlockMapper)filter).mapBlocks(reader, pos, mapper);
                return;
            }
            AABBUtil.mapResult(reader.m_8055_(pos).m_60734_(), pos, mapper);
        }
    }

    public static void mapResult(Block block, BlockPos pos, Map<Block, List<BlockPos>> mapper) {
        List<BlockPos> positions = mapper.get(block);
        if (positions == null) {
            positions = CollectionUtils.createList();
            mapper.put(block, positions);
        }
        positions.add(pos);
    }

    public static <T extends Entity> T getClosestEntity(List<T> entities, Vec3 center) {
        double closestDist = Double.MAX_VALUE;
        Entity closest = null;
        int m = entities.size();
        for (int i = 0; i < m; ++i) {
            Entity entity = (Entity)entities.get(i);
            double dist = entity.m_20238_(center);
            if (!(closestDist > dist)) continue;
            closestDist = dist;
            closest = entity;
        }
        return (T)closest;
    }

    @FunctionalInterface
    public static interface IBlockFilter {
        default public boolean isValid(LevelReader world, BlockPos pos) {
            return this.isValid(world.m_8055_(pos));
        }

        public boolean isValid(BlockState var1);
    }

    public static class SearchResult {
        ObjectList<BlockPos> positions;
        Object2ObjectMap<Block, List<BlockPos>> mappedPositions;

        public SearchResult(ObjectList<BlockPos> positions, Object2ObjectMap<Block, List<BlockPos>> mappedPositions) {
            this.positions = positions;
            this.mappedPositions = mappedPositions;
        }

        public int size() {
            return this.positions.size();
        }

        public ObjectList<BlockPos> getPositions() {
            return this.positions;
        }

        public Object2ObjectMap<Block, List<BlockPos>> getMappedPositions() {
            return this.mappedPositions;
        }
    }

    public static class QueuedSearcher {
        Level world;
        WorldRegion reader;
        BlockPos start;
        Box box;
        IBlockFilter filter;
        DirectionList directions;
        PriorityQueue<BlockPos> queue = new ObjectArrayPriorityQueue();
        Set<BlockPos> found = CollectionUtils.createSet();
        List<BlockPos> results = CollectionUtils.createList();
        int limit;
        int flags;
        int speed = 100;

        QueuedSearcher(Level world, BlockPos start, Box box, IBlockFilter filter, int flags, DirectionList directions, int limit) {
            this.world = world;
            this.start = start;
            this.box = box;
            this.filter = filter;
            this.directions = directions;
            this.flags = flags;
        }

        public QueuedSearcher setSpeed(int blocksPerProcess) {
            this.speed = blocksPerProcess;
            return this;
        }

        public int size() {
            return this.queue.isEmpty() ? this.results.size() : -1;
        }

        public List<BlockPos> getPos() {
            return this.queue.isEmpty() ? this.results : null;
        }

        public boolean process(boolean result) {
            if (this.reader == null) {
                this.reader = new WorldRegion(this.world, this.box, (this.flags & 1) != 0);
                if ((this.flags & 2) != 0) {
                    for (BlockPos blockPos : (this.flags & 8) != 0 ? this.box.getHollowIterator() : this.box) {
                        this.queue.enqueue((Object)blockPos.m_7949_());
                    }
                } else {
                    for (Direction direction : (this.flags & 4) != 0 ? this.directions.getRandomIterator() : this.directions) {
                        this.queue.enqueue((Object)this.start.m_121945_(direction));
                    }
                }
                if ((this.flags & 0x10) == 0) {
                    return !result;
                }
            }
            boolean loading = (this.flags & 1) != 0;
            boolean bl = (this.flags & 2) != 0;
            for (int i = 0; i < this.speed && this.queue.size() > 0; ++i) {
                BlockPos pos = (BlockPos)this.queue.dequeue();
                if (!bl && (!this.box.intersectsWith(pos) || !this.found.add(pos)) || !loading && !this.reader.m_7232_(pos.m_123341_() >> 4, pos.m_123343_() >> 4) || !this.filter.isValid(this.reader, pos)) continue;
                this.results.add(pos);
                if (this.results.size() >= this.limit) {
                    this.queue.clear();
                    return result;
                }
                if (bl) continue;
                for (Direction dir : this.directions) {
                    BlockPos next = pos.m_121945_(dir);
                    if (!this.box.intersectsWith(next) || this.found.contains(next)) continue;
                    this.queue.enqueue((Object)next);
                }
            }
            return this.queue.isEmpty() == result;
        }
    }

    static class ThreadedSearcher
    implements Runnable {
        WorldRegion reader;
        BlockPos start;
        Box box;
        IBlockFilter filter;
        DirectionList directions;
        int flags;
        int limit;
        Consumer<SearchResult> result;

        public ThreadedSearcher(WorldRegion reader, BlockPos start, Box box, IBlockFilter filter, DirectionList directions, int flags, int limit, Consumer<SearchResult> result) {
            this.reader = reader;
            this.start = start;
            this.box = box;
            this.filter = filter;
            this.directions = directions;
            this.flags = flags;
            this.limit = limit;
            this.result = result;
        }

        @Override
        public void run() {
            boolean map_results;
            ObjectList resultList = CollectionUtils.createList();
            Object2ObjectMap mapped_result = (this.flags & 0x20) != 0 ? CollectionUtils.createLinkedMap() : Object2ObjectMaps.emptyMap();
            boolean bl = map_results = (this.flags & 0x20) != 0;
            if ((this.flags & 2) != 0) {
                for (BlockPos blockPos : (this.flags & 8) != 0 ? this.box.getHollowIterator() : this.box) {
                    if (!this.reader.m_7232_(blockPos.m_123341_() >> 4, blockPos.m_123343_() >> 4) || !this.filter.isValid(this.reader, blockPos)) continue;
                    BlockPos immutable = blockPos.m_7949_();
                    resultList.add((Object)immutable);
                    AABBUtil.mapResult(this.filter, this.reader, blockPos, map_results, (Map<Block, List<BlockPos>>)mapped_result);
                    if (resultList.size() < this.limit) continue;
                    this.result.accept(new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result));
                    return;
                }
                this.result.accept(new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result));
                return;
            }
            ObjectArrayFIFOQueue queue = new ObjectArrayFIFOQueue();
            ObjectSet objectSet = CollectionUtils.createSet();
            for (Direction direction : (this.flags & 4) != 0 ? this.directions.getRandomIterator() : this.directions) {
                queue.enqueue((Object)this.start.m_121945_(direction));
            }
            while (queue.size() > 0) {
                BlockPos toProcess = (BlockPos)queue.dequeue();
                if (!this.box.intersectsWith(toProcess) || objectSet.contains(toProcess)) continue;
                objectSet.add((BlockPos)toProcess);
                if (!this.reader.m_7232_(toProcess.m_123341_() >> 4, toProcess.m_123343_() >> 4) || !this.filter.isValid(this.reader, toProcess)) continue;
                resultList.add((Object)toProcess);
                AABBUtil.mapResult(this.filter, this.reader, toProcess, map_results, (Map<Block, List<BlockPos>>)mapped_result);
                if (resultList.size() >= this.limit) {
                    this.result.accept(new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result));
                    return;
                }
                for (Direction dir : this.directions) {
                    BlockPos pos = toProcess.m_121945_(dir);
                    if (!this.box.intersectsWith(pos) || objectSet.contains(pos)) continue;
                    queue.enqueue((Object)pos);
                }
            }
            this.result.accept(new SearchResult(resultList, (Object2ObjectMap<Block, List<BlockPos>>)mapped_result));
        }
    }

    public static interface IBlockMapper
    extends IBlockFilter {
        public void mapBlocks(LevelReader var1, BlockPos var2, Map<Block, List<BlockPos>> var3);
    }
}

