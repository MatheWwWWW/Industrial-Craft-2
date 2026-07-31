/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ForcedChunksSavedData
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.common.world.ForgeChunkManager$TicketTracker
 */
package ic2.core.networking.packets.client;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.server.ChunkAnswerPacket;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.concurrent.TimeUnit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;

public class ChunkRequestPacket
extends IC2Packet {
    static final Cache<ResourceKey<Level>, Long2IntLinkedOpenHashMap> CACHE = CacheBuilder.newBuilder().concurrencyLevel(1).expireAfterWrite(10L, TimeUnit.SECONDS).build();

    @Override
    public void write(FriendlyByteBuf buffer) {
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
    }

    @Override
    public void handlePacket(Player source) {
        if (!(source instanceof ServerPlayer)) {
            return;
        }
        Long2IntLinkedOpenHashMap map = (Long2IntLinkedOpenHashMap)CACHE.getIfPresent((Object)source.f_19853_.m_46472_());
        if (map == null) {
            map = new Long2IntLinkedOpenHashMap();
            ForcedChunksSavedData data = (ForcedChunksSavedData)((ServerPlayer)source).m_9236_().m_8895_().m_164861_(ForcedChunksSavedData::m_151483_, ForcedChunksSavedData::new, "chunks");
            if (data != null) {
                this.addToMap(map, data.getBlockForcedChunks());
                this.addToMap(map, data.getEntityForcedChunks());
                this.addToMap(map, data.m_46116_());
            }
            CACHE.put((Object)source.f_19853_.m_46472_(), (Object)map);
        }
        PacketManager.INSTANCE.sendToPlayer(new ChunkAnswerPacket((Long2IntMap)map), source);
    }

    private void addToMap(Long2IntLinkedOpenHashMap map, ForgeChunkManager.TicketTracker<?> tracker) {
        for (LongSet set : tracker.getChunks().values()) {
            this.addToMap(map, set);
        }
        for (LongSet set : tracker.getTickingChunks().values()) {
            this.addToMap(map, set);
        }
    }

    private void addToMap(Long2IntLinkedOpenHashMap map, LongSet set) {
        LongIterator iter = set.iterator();
        while (iter.hasNext()) {
            map.addTo(iter.nextLong(), 1);
        }
    }
}

