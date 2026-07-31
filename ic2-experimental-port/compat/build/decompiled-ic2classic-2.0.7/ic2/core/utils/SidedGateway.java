/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.fml.util.thread.EffectiveSide
 */
package ic2.core.utils;

import java.util.function.Supplier;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class SidedGateway<T> {
    private final T clientInstance;
    private final T serverInstance;

    public SidedGateway(Class<? extends T> server, Class<? extends T> client) {
        try {
            this.clientInstance = FMLEnvironment.dist.isClient() ? client.newInstance() : null;
            this.serverInstance = server.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SidedGateway(Supplier<? extends T> server, Supplier<? extends T> client) {
        this.serverInstance = server.get();
        this.clientInstance = FMLEnvironment.dist.isClient() ? client.get() : null;
    }

    public SidedGateway(String server, String client) {
        try {
            this.clientInstance = FMLEnvironment.dist.isClient() ? Class.forName(client).newInstance() : null;
            this.serverInstance = Class.forName(server).newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T get() {
        if (EffectiveSide.get().isClient()) {
            return this.clientInstance;
        }
        return this.serverInstance;
    }

    public T get(boolean simulating) {
        if (simulating) {
            return this.serverInstance;
        }
        return this.clientInstance;
    }
}

