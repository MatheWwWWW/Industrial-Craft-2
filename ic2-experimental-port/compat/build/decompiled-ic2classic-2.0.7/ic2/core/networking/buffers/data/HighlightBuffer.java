/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 */
package ic2.core.networking.buffers.data;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

public class HighlightBuffer
implements INetworkDataBuffer {
    LongList list = new LongArrayList();
    int color;

    public HighlightBuffer() {
    }

    public HighlightBuffer(LongList list, int color) {
        this.list = list;
        this.color = color;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeInt(this.list.size());
        int m = this.list.size();
        for (int i = 0; i < m; ++i) {
            buffer.writeLong(this.list.getLong(i));
        }
        buffer.writeInt(this.color);
    }

    @Override
    public void read(IInputBuffer buffer) {
        int size = buffer.readInt();
        for (int i = 0; i < size; ++i) {
            this.list.add(buffer.readLong());
        }
        this.color = buffer.readInt();
    }

    public int getColor() {
        return this.color;
    }

    public LongList getList() {
        return this.list;
    }
}

