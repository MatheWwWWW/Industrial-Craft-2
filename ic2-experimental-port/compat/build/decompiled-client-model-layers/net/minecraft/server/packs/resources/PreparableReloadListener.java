/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public interface PreparableReloadListener {
    public CompletableFuture<Void> m_5540_(PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6);

    default public String m_7812_() {
        return this.getClass().getSimpleName();
    }

    public static interface PreparationBarrier {
        public <T> CompletableFuture<T> m_6769_(T var1);
    }
}

