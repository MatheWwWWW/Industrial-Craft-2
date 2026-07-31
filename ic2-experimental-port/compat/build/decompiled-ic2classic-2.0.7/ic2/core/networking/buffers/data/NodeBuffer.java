/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.networking.buffers.data;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;

public class NodeBuffer
implements INetworkDataBuffer {
    int[] data = new int[6];

    public NodeBuffer() {
    }

    public NodeBuffer(int[] data) {
        this.data = data;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        for (int i = 0; i < 6; ++i) {
            buffer.writeByte((byte)this.data[i]);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        for (int i = 0; i < 6; ++i) {
            this.data[i] = buffer.readByte();
        }
    }

    public int[] getData() {
        return this.data;
    }
}

