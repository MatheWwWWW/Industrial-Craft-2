/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.networking.buffers.data;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;

public class StringDataBuffer
implements INetworkDataBuffer {
    int id;
    String value;

    public StringDataBuffer() {
    }

    public StringDataBuffer(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.id);
        buffer.writeString(this.value);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.id = buffer.readVarInt();
        this.value = buffer.readString();
    }

    public int getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }
}

