/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.networking.buffers.data;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;

public class BaseTeleporterBuffer
implements INetworkDataBuffer {
    String networkID;
    String name;
    byte type;

    public BaseTeleporterBuffer() {
    }

    public BaseTeleporterBuffer(String networkID, String name, byte type) {
        this.networkID = networkID;
        this.name = name;
        this.type = type;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte(this.type);
        buffer.writeString(this.name);
        buffer.writeString(this.networkID);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.type = buffer.readByte();
        this.name = buffer.readString();
        this.networkID = buffer.readString();
    }

    public String getName() {
        return this.name;
    }

    public String getNetworkID() {
        return this.networkID;
    }

    public byte getType() {
        return this.type;
    }
}

