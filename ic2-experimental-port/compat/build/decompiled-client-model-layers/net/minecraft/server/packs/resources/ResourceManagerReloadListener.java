/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;

public interface ResourceManagerReloadListener
extends PreparableReloadListener {
    @Override
    default public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_10752_, ResourceManager p_10753_, ProfilerFiller p_10754_, ProfilerFiller p_10755_, Executor p_10756_, Executor p_10757_) {
        return p_10752_.m_6769_(Unit.INSTANCE).thenRunAsync(() -> {
            p_10755_.m_7242_();
            p_10755_.m_6180_("listener");
            this.m_6213_(p_10753_);
            p_10755_.m_7238_();
            p_10755_.m_7241_();
        }, p_10757_);
    }

    public void m_6213_(ResourceManager var1);
}

