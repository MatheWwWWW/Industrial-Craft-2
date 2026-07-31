/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network.buffer;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;

public class EmptyDataBuffer
implements INetworkDataBuffer {
    public static final EmptyDataBuffer INSTANCE = new EmptyDataBuffer();

    @Override
    public void write(IOutputBuffer buffer) {
    }

    @Override
    public void read(IInputBuffer buffer) {
    }
}

