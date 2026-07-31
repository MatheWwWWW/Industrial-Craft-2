/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network;

import ic2.api.network.IGrowingBuffer;
import java.io.IOException;

public interface INetworkCustomEncoder {
    public void encode(IGrowingBuffer var1, Object var2) throws IOException;

    public Object decode(IGrowingBuffer var1) throws IOException;

    public boolean isThreadSafe();
}

