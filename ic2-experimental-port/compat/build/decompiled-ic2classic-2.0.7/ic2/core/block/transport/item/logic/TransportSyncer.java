/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.transport.item.logic;

import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.IC2;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.buffers.data.NBTBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TransportSyncer {
    ObjectSet<TransportedItem> items = new ObjectLinkedOpenHashSet();

    public void markDirty(TransportedItem item) {
        this.items.add((Object)item);
    }

    public void removeAll(Set<TransportedItem> toDelete) {
        this.items.removeAll(toDelete);
    }

    public boolean hasData() {
        return this.items.size() > 0;
    }

    public void clear() {
        this.items.clear();
    }

    public byte[] serialize() {
        ByteBuf buf = Unpooled.buffer();
        OutputBuffer buffer = new OutputBuffer(buf);
        buffer.writeVarInt(this.items.size());
        for (TransportedItem item : this.items) {
            buffer.writeVarInt(item.getId());
            buffer.writeShort(item.serializeEssentials());
        }
        this.items.clear();
        byte[] data = new byte[buf.writerIndex()];
        buf.readBytes(data);
        return data;
    }

    public void deserialize(byte[] itemData, BlockEntity receiver, String tag, List<TransportedItem> stored) {
        int i;
        InputBuffer buffer = new InputBuffer(itemData);
        Int2ShortOpenHashMap items = new Int2ShortOpenHashMap();
        int m = buffer.readVarInt();
        for (i = 0; i < m; ++i) {
            int id = buffer.readVarInt();
            items.put(id, buffer.readShort());
        }
        m = stored.size();
        for (i = 0; i < m; ++i) {
            TransportedItem item = stored.get(i);
            if (!items.containsKey(item.getId())) continue;
            item.deserializeEssentials(items.remove(item.getId()));
        }
        if (items.size() > 0) {
            IC2.NETWORKING.get().sendClientTileDataBufferEvent(receiver, tag, new NBTBuffer("data", (Tag)new IntArrayTag(items.keySet().toIntArray())));
        }
    }
}

