/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2IntMaps
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server;

import ic2.core.block.rendering.world.impl.ChunkLoaderOverlay;
import ic2.core.networking.packets.IC2Packet;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class ChunkAnswerPacket
extends IC2Packet {
    Long2IntMap map = new Long2IntLinkedOpenHashMap();

    public ChunkAnswerPacket() {
    }

    public ChunkAnswerPacket(Long2IntMap map) {
        this.map = map;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.map.size());
        for (Long2IntMap.Entry entry : Long2IntMaps.fastIterable((Long2IntMap)this.map)) {
            buffer.writeLong(entry.getLongKey());
            buffer.m_130130_(entry.getIntValue());
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            this.map.put(buffer.readLong(), buffer.m_130242_());
        }
    }

    @Override
    public void handlePacket(Player source) {
        ChunkLoaderOverlay.INSTANCE.setForceLoadedChunks(this.map);
    }
}

