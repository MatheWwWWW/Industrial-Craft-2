/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public abstract class SimplePreparableReloadListener<T>
implements PreparableReloadListener {
    @Override
    public final CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_10780_, ResourceManager p_10781_, ProfilerFiller p_10782_, ProfilerFiller p_10783_, Executor p_10784_, Executor p_10785_) {
        return ((CompletableFuture)CompletableFuture.supplyAsync(() -> this.m_5944_(p_10781_, p_10782_), p_10784_).thenCompose(p_10780_::m_6769_)).thenAcceptAsync(p_10792_ -> this.m_5787_(p_10792_, p_10781_, p_10783_), p_10785_);
    }

    protected abstract T m_5944_(ResourceManager var1, ProfilerFiller var2);

    protected abstract void m_5787_(T var1, ResourceManager var2, ProfilerFiller var3);
}

