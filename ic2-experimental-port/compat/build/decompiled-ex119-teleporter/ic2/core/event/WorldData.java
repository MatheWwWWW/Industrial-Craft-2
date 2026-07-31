/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package ic2.core.event;

import ic2.core.IC2;
import ic2.core.WindSim;
import ic2.core.block.personal.TradingMarket;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.event.IWorldTickCallback;
import ic2.core.network.TeUpdateDataServer;
import ic2.core.util.Util;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public class WorldData {
    private static ConcurrentMap<ResourceLocation, WorldData> idxClient = IC2.envProxy.isClientEnv() ? new ConcurrentHashMap() : null;
    private static ConcurrentMap<ResourceLocation, WorldData> idxServer = new ConcurrentHashMap<ResourceLocation, WorldData>();
    final Queue<IWorldTickCallback> singleUpdates = new ConcurrentLinkedQueue<IWorldTickCallback>();
    final Set<IWorldTickCallback> continuousUpdates = new HashSet<IWorldTickCallback>();
    boolean continuousUpdatesInUse = false;
    final List<IWorldTickCallback> continuousUpdatesToAdd = new ArrayList<IWorldTickCallback>();
    final List<IWorldTickCallback> continuousUpdatesToRemove = new ArrayList<IWorldTickCallback>();
    public final EnergyNetLocal energyNet;
    public final Map<BlockEntity, TeUpdateDataServer> tesToUpdate = new IdentityHashMap<BlockEntity, TeUpdateDataServer>();
    public final TradingMarket tradeMarket;
    public final WindSim windSim;
    public final Map<LevelChunk, CompoundTag> worldGenData = new IdentityHashMap<LevelChunk, CompoundTag>();
    public final Set<LevelChunk> chunksToDecorate = Collections.newSetFromMap(new IdentityHashMap());
    public final Set<LevelChunk> pendingUnloadChunks = Collections.newSetFromMap(new IdentityHashMap());
    public final Long2ObjectMap<Set<BlockPos>> loadedChunks;
    public final Object2ObjectMap<BlockPos, LongSet> chunkLoaders;

    private WorldData(Level level) {
        if (!level.f_46443_) {
            this.energyNet = EnergyNetLocal.create(level);
            this.tradeMarket = new TradingMarket(level);
            this.windSim = new WindSim(level);
            this.loadedChunks = new Long2ObjectOpenHashMap();
            this.chunkLoaders = new Object2ObjectOpenHashMap();
        } else {
            this.energyNet = null;
            this.tradeMarket = null;
            this.windSim = null;
            this.loadedChunks = null;
            this.chunkLoaders = null;
        }
    }

    public static WorldData get(Level level) {
        return WorldData.get(level, true);
    }

    public static WorldData get(Level level, boolean bl) {
        if (level == null) {
            throw new IllegalArgumentException("world is null");
        }
        ConcurrentMap<ResourceLocation, WorldData> concurrentMap = WorldData.getIndex(!level.f_46443_);
        WorldData worldData = (WorldData)concurrentMap.get(WorldData.getKey(level));
        if (worldData != null || !bl) {
            return worldData;
        }
        worldData = new WorldData(level);
        WorldData worldData2 = concurrentMap.putIfAbsent(WorldData.getKey(level), worldData);
        if (worldData2 != null) {
            worldData = worldData2;
        }
        return worldData;
    }

    public static void onWorldUnload(Level level) {
        WorldData.getIndex(!level.f_46443_).remove(WorldData.getKey(level));
    }

    private static ResourceLocation getKey(Level level) {
        return Util.getDimId(level);
    }

    private static ConcurrentMap<ResourceLocation, WorldData> getIndex(boolean bl) {
        return bl ? idxServer : idxClient;
    }
}

