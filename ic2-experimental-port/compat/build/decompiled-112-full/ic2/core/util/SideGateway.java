/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.FMLCommonHandler
 */
package ic2.core.util;

import net.minecraftforge.fml.common.FMLCommonHandler;

public final class SideGateway<T> {
    private final T clientInstance;
    private final T serverInstance;

    public SideGateway(String serverClass, String clientClass) {
        try {
            this.clientInstance = FMLCommonHandler.instance().getSide().isClient() ? Class.forName(clientClass).newInstance() : null;
            this.serverInstance = Class.forName(serverClass).newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T get(boolean simulating) {
        if (simulating) {
            return this.serverInstance;
        }
        return this.clientInstance;
    }
}

