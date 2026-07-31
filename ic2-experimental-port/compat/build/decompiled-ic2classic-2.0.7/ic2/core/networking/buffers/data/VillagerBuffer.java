/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.networking.buffers.data;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import java.util.UUID;

public class VillagerBuffer
implements INetworkDataBuffer {
    UUID id;
    int index;
    boolean state;

    public VillagerBuffer() {
    }

    public VillagerBuffer(UUID id, int index, boolean state) {
        this.id = id;
        this.index = index;
        this.state = state;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeUUID(this.id);
        buffer.writeVarInt(this.index);
        buffer.writeBoolean(this.state);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.id = buffer.readUUID();
        this.index = buffer.readVarInt();
        this.state = buffer.readBoolean();
    }

    public UUID getId() {
        return this.id;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean getState() {
        return this.state;
    }
}

