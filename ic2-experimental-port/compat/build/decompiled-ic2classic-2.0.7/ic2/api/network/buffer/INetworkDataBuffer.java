/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network.buffer;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;

public interface INetworkDataBuffer {
    public void write(IOutputBuffer var1);

    public void read(IInputBuffer var1);
}

