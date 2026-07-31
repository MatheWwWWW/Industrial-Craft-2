/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.util.thread.EffectiveSide
 */
package ic2.api.util;

import net.minecraftforge.fml.util.thread.EffectiveSide;

public class SidedObject<T> {
    T client;
    T server;

    public void set(T value, boolean serverSide) {
        if (serverSide) {
            this.server = value;
            return;
        }
        this.client = value;
    }

    public T get() {
        return this.get(this.isSimulating());
    }

    public T get(boolean serverSide) {
        return serverSide ? this.server : this.client;
    }

    protected boolean isSimulating() {
        return EffectiveSide.get().isServer();
    }
}

