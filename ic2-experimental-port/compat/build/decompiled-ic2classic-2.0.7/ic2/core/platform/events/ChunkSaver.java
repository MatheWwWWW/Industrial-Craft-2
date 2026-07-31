/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkStatus
 */
package ic2.core.platform.events;

import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;

public class ChunkSaver {
    static final Map<Level, ChunkSaver> SAVER_MAP = Object2ObjectMaps.synchronize(CollectionUtils.createMap());
    Long2ObjectMap<LongSet> chunkMap = new Long2ObjectLinkedOpenHashMap();

    public static ChunkSaver getSaver(Level world) {
        if (world.f_46443_) {
            return null;
        }
        ChunkSaver saver = SAVER_MAP.get(world);
        if (saver == null) {
            saver = new ChunkSaver();
            SAVER_MAP.put(world, saver);
        }
        return saver;
    }

    public static void onWorldUnload(Level world) {
        SAVER_MAP.remove(world);
    }

    public void addPosition(BlockPos pos) {
        long key = ChunkPos.m_45589_((int)(pos.m_123341_() >> 4), (int)(pos.m_123343_() >> 4));
        LongSet blockSet = (LongSet)this.chunkMap.get(key);
        if (blockSet == null) {
            blockSet = new LongOpenHashSet();
            this.chunkMap.put(key, (Object)blockSet);
        }
        blockSet.add(pos.m_121878_());
    }

    public void removePosition(BlockPos pos) {
        long key = ChunkPos.m_45589_((int)(pos.m_123341_() >> 4), (int)(pos.m_123343_() >> 4));
        LongSet blockSet = (LongSet)this.chunkMap.get(key);
        if (blockSet == null) {
            return;
        }
        blockSet.remove(pos.m_121878_());
        if (blockSet.isEmpty()) {
            this.chunkMap.remove(key);
        }
    }

    public boolean shouldSave(ChunkPos pos) {
        return this.chunkMap.containsKey(pos.m_45588_());
    }

    public void onSave(Level world) {
        LongIterator longIterator = this.chunkMap.keySet().iterator();
        while (longIterator.hasNext()) {
            long entry = (Long)longIterator.next();
            ChunkAccess chunk = world.m_6522_(ChunkPos.m_45592_((long)entry), ChunkPos.m_45602_((long)entry), ChunkStatus.f_62326_, false);
            if (chunk == null) continue;
            chunk.m_8092_(true);
        }
    }
}

