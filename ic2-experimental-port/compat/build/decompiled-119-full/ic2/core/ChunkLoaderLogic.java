/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.TicketType
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.saveddata.SavedData
 */
package ic2.core;

import ic2.core.IC2;
import ic2.core.event.WorldData;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.saveddata.SavedData;

public final class ChunkLoaderLogic {
    private static final String savedStateId = IC2.getIdentifier("loaded_chunks").toString().replace(':', '_');
    private static final TicketType<ChunkPos> ticketType = TicketType.m_9462_((String)IC2.getIdentifier("chunk_loader").toString(), Comparator.comparingLong(ChunkPos::m_45588_));

    public static void addChunkLoader(ServerLevel serverLevel, BlockPos blockPos, LongSet longSet) {
        long l2 = ChunkPos.m_151388_((BlockPos)blockPos);
        if (!longSet.contains(l2)) {
            throw new IllegalArgumentException("missing own position");
        }
        SavedState savedState = (SavedState)serverLevel.m_8895_().m_164861_(SavedState::new, SavedState::new, savedStateId);
        ((Set)savedState.chunksToChunkLoaders.computeIfAbsent(l2, l -> new ObjectOpenHashSet(1))).add(blockPos);
        WorldData worldData = WorldData.get((Level)serverLevel);
        LongIterator longIterator = longSet.iterator();
        while (longIterator.hasNext()) {
            long l3 = (Long)longIterator.next();
            Set set = (Set)worldData.loadedChunks.computeIfAbsent(l3, l -> new ObjectOpenHashSet(1));
            if (set.isEmpty()) {
                ChunkLoaderLogic.addChunkTicket(serverLevel, new ChunkPos(l3));
            }
            set.add(blockPos);
        }
        worldData.chunkLoaders.put((Object)blockPos, (Object)new LongOpenHashSet((LongCollection)longSet));
    }

    public static void removeChunkLoader(ServerLevel serverLevel, BlockPos blockPos) {
        WorldData worldData;
        long l;
        Set set;
        SavedState savedState = (SavedState)serverLevel.m_8895_().m_164858_(SavedState::new, savedStateId);
        if (savedState != null && (set = (Set)savedState.chunksToChunkLoaders.get(l = ChunkPos.m_151388_((BlockPos)blockPos))) != null && set.remove(blockPos) && set.isEmpty()) {
            savedState.chunksToChunkLoaders.remove(l);
        }
        if ((worldData = WorldData.get((Level)serverLevel, false)) != null) {
            ChunkLoaderLogic.disableChunkLoader(serverLevel, blockPos, worldData);
        }
    }

    private static void disableChunkLoader(ServerLevel serverLevel, BlockPos blockPos, WorldData worldData) {
        LongSet longSet = (LongSet)worldData.chunkLoaders.remove((Object)blockPos);
        if (longSet != null) {
            LongIterator longIterator = longSet.iterator();
            while (longIterator.hasNext()) {
                long l = (Long)longIterator.next();
                Set set = (Set)worldData.loadedChunks.get(l);
                if (set == null || !set.remove(blockPos) || !set.isEmpty()) continue;
                worldData.loadedChunks.remove(l);
                ChunkLoaderLogic.removeChunkTicket(serverLevel, new ChunkPos(l));
            }
        }
    }

    public static void updateChunkLoader(ServerLevel serverLevel, BlockPos blockPos, LongSet longSet) {
        Set set;
        long l2;
        long l3 = ChunkPos.m_151388_((BlockPos)blockPos);
        if (!longSet.contains(l3)) {
            throw new IllegalArgumentException("missing own position");
        }
        WorldData worldData = WorldData.get((Level)serverLevel);
        LongSet longSet2 = (LongSet)worldData.chunkLoaders.get((Object)blockPos);
        if (longSet2 == null) {
            ChunkLoaderLogic.addChunkLoader(serverLevel, blockPos, longSet);
            return;
        }
        LongIterator longIterator = longSet2.longIterator();
        while (longIterator.hasNext()) {
            l2 = longIterator.nextLong();
            if (longSet.contains(l2)) continue;
            longIterator.remove();
            set = (Set)worldData.loadedChunks.get(l2);
            set.remove(blockPos);
            if (!set.isEmpty()) continue;
            ChunkLoaderLogic.removeChunkTicket(serverLevel, new ChunkPos(l2));
            worldData.loadedChunks.remove(l2);
        }
        longIterator = longSet.iterator();
        while (longIterator.hasNext()) {
            l2 = (Long)longIterator.next();
            if (!longSet2.add(l2)) continue;
            set = (Set)worldData.loadedChunks.computeIfAbsent(l2, l -> new ObjectOpenHashSet());
            if (set.isEmpty()) {
                ChunkLoaderLogic.addChunkTicket(serverLevel, new ChunkPos(l2));
            }
            set.add(blockPos);
        }
    }

    public static void onWorldLoad(ServerLevel serverLevel) {
        SavedState savedState = (SavedState)serverLevel.m_8895_().m_164858_(SavedState::new, savedStateId);
        if (savedState == null || savedState.chunksToChunkLoaders.isEmpty()) {
            return;
        }
        WorldData worldData = WorldData.get((Level)serverLevel);
        for (Long2ObjectMap.Entry entry : savedState.chunksToChunkLoaders.long2ObjectEntrySet()) {
            long l = entry.getLongKey();
            Set set = (Set)entry.getValue();
            worldData.loadedChunks.put(l, (Object)new ObjectOpenHashSet((Collection)set));
            for (BlockPos blockPos : set) {
                ((LongSet)worldData.chunkLoaders.computeIfAbsent((Object)blockPos, object -> new LongOpenHashSet(1))).add(l);
            }
            ChunkLoaderLogic.addChunkTicket(serverLevel, new ChunkPos(l));
        }
    }

    public static void onChunkUnload(LevelChunk levelChunk) {
        assert (!levelChunk.m_62953_().f_46443_);
        ServerLevel serverLevel = (ServerLevel)levelChunk.m_62953_();
        SavedState savedState = (SavedState)serverLevel.m_8895_().m_164858_(SavedState::new, savedStateId);
        if (savedState == null || savedState.chunksToChunkLoaders.isEmpty()) {
            return;
        }
        Set set = (Set)savedState.chunksToChunkLoaders.get(levelChunk.m_7697_().m_45588_());
        if (set == null || set.isEmpty()) {
            return;
        }
        WorldData worldData = WorldData.get((Level)serverLevel, false);
        if (worldData == null) {
            return;
        }
        for (BlockPos blockPos : set) {
            ChunkLoaderLogic.disableChunkLoader(serverLevel, blockPos, worldData);
        }
    }

    private static void addChunkTicket(ServerLevel serverLevel, ChunkPos chunkPos) {
        serverLevel.m_7726_().m_8387_(ticketType, chunkPos, 2, (Object)chunkPos);
    }

    private static void removeChunkTicket(ServerLevel serverLevel, ChunkPos chunkPos) {
        serverLevel.m_7726_().m_8438_(ticketType, chunkPos, 2, (Object)chunkPos);
    }

    private static final class SavedState
    extends SavedData {
        final Long2ObjectMap<Set<BlockPos>> chunksToChunkLoaders = new Long2ObjectOpenHashMap();

        SavedState() {
        }

        SavedState(CompoundTag compoundTag) {
            ListTag listTag = compoundTag.m_128437_("loaders", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag2 = listTag.m_128728_(i);
                BlockPos blockPos = new BlockPos(compoundTag2.m_128451_("x"), compoundTag2.m_128451_("y"), compoundTag2.m_128451_("z"));
                ((Set)this.chunksToChunkLoaders.computeIfAbsent(ChunkPos.m_151388_((BlockPos)blockPos), l -> new ObjectOpenHashSet(1))).add(blockPos);
            }
        }

        public CompoundTag m_7176_(CompoundTag compoundTag) {
            ListTag listTag = new ListTag();
            compoundTag.m_128365_("loaders", (Tag)listTag);
            for (Set set : this.chunksToChunkLoaders.values()) {
                for (BlockPos blockPos : set) {
                    CompoundTag compoundTag2 = new CompoundTag();
                    listTag.add((Object)compoundTag2);
                    compoundTag2.m_128405_("x", blockPos.m_123341_());
                    compoundTag2.m_128405_("y", blockPos.m_123342_());
                    compoundTag2.m_128405_("z", blockPos.m_123343_());
                }
            }
            return compoundTag;
        }
    }
}

