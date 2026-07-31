/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network.buffer;

import ic2.api.network.buffer.NetworkInfo;

public interface IBitLevelOverride {
    public NetworkInfo.BitLevel getOverride(int var1, String var2);

    public boolean hasOverride(int var1, String var2);
}

