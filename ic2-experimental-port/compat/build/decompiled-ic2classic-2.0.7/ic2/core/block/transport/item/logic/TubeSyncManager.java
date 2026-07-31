/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.transport.item.logic;

import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.custom.TubeSyncPacket;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TubeSyncManager {
    static final Object2ObjectMap<Level, TubeSyncManager> CACHE = CollectionUtils.createLinkedMap();
    Long2ObjectMap<Int2ObjectMap<byte[]>> data = new Long2ObjectLinkedOpenHashMap();
    Level world;

    public TubeSyncManager(Level world) {
        this.world = world;
    }

    public static void onWorldUnload(Level world) {
        CACHE.remove((Object)world);
    }

    public static void onServerStop() {
        CACHE.clear();
    }

    public static void onTick() {
        for (TubeSyncManager cache : CACHE.values()) {
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(cache.data)) {
                PacketManager.INSTANCE.sendToAllChunkWatchers(cache.world.m_6325_(ChunkPos.m_45592_((long)entry.getLongKey()), ChunkPos.m_45602_((long)entry.getLongKey())), new TubeSyncPacket(entry.getLongKey(), (Int2ObjectMap<byte[]>)((Int2ObjectMap)entry.getValue())));
            }
            cache.data.clear();
        }
    }

    public static void markDirty(BlockEntity tile, byte[] data) {
        BlockPos pos;
        Int2ObjectMap chunk;
        Level world = tile.m_58904_();
        if (world.f_46443_) {
            return;
        }
        TubeSyncManager cache = (TubeSyncManager)CACHE.get((Object)world);
        if (cache == null) {
            cache = new TubeSyncManager(world);
            CACHE.put((Object)world, (Object)cache);
        }
        if ((chunk = (Int2ObjectMap)cache.data.get(ChunkPos.m_45589_((int)((pos = tile.m_58899_()).m_123341_() >> 4), (int)(pos.m_123343_() >> 4)))) == null) {
            chunk = new Int2ObjectLinkedOpenHashMap();
            cache.data.put(ChunkPos.m_45589_((int)(pos.m_123341_() >> 4), (int)(pos.m_123343_() >> 4)), (Object)chunk);
        }
        chunk.put((pos.m_123342_() - world.m_141937_() & 0xFFFF) << 8 | (pos.m_123341_() & 0xF) << 4 | pos.m_123343_() & 0xF, (Object)data);
    }
}

