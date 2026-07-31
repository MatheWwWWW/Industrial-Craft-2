/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

import ic2.core.IC2;

public final class SideGateway<T> {
    private final T clientInstance;
    private final T serverInstance;

    public SideGateway(String string, String string2) {
        try {
            this.clientInstance = IC2.envProxy.isClientEnv() ? Class.forName(string2).newInstance() : null;
            this.serverInstance = Class.forName(string).newInstance();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public T get(boolean bl) {
        if (bl) {
            return this.serverInstance;
        }
        return this.clientInstance;
    }
}

