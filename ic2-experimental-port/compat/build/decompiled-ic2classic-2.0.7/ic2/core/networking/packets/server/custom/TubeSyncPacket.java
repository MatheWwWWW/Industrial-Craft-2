/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package ic2.core.networking.packets.server.custom;

import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.networking.packets.IC2Packet;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public class TubeSyncPacket
extends IC2Packet {
    long chunkPos;
    Int2ObjectMap<byte[]> data = new Int2ObjectLinkedOpenHashMap();

    public TubeSyncPacket() {
    }

    public TubeSyncPacket(long chunkPos, Int2ObjectMap<byte[]> data) {
        this.chunkPos = chunkPos;
        this.data = data;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.chunkPos);
        buffer.m_130130_(this.data.size());
        for (Int2ObjectMap.Entry entry : Int2ObjectMaps.fastIterable(this.data)) {
            buffer.writeInt(entry.getIntKey());
            buffer.m_130087_((byte[])entry.getValue());
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.chunkPos = buffer.readLong();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            this.data.put(buffer.readInt(), (Object)buffer.m_130052_());
        }
    }

    @Override
    public void handlePacket(Player source) {
        int x = ChunkPos.m_45592_((long)this.chunkPos);
        int z = ChunkPos.m_45602_((long)this.chunkPos);
        int minHeight = source.f_19853_.m_141937_();
        LevelChunk chunk = source.f_19853_.m_6325_(x, z);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Int2ObjectMap.Entry entry : Int2ObjectMaps.fastIterable(this.data)) {
            int pos = entry.getIntKey();
            BlockEntity tile = chunk.m_7702_((BlockPos)mutable.m_122178_((x << 4) + (pos >> 4 & 0xF), (pos >> 8 & 0xFFFF) + minHeight, (z << 4) + (pos & 0xF)));
            if (!(tile instanceof TubeTileEntity)) continue;
            ((TubeTileEntity)tile).onSyncPacketReceived((byte[])entry.getValue());
        }
    }
}

