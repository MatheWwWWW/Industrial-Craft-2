/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;

public interface ReloadInstance {
    public CompletableFuture<?> m_7237_();

    public float m_7750_();

    default public boolean m_7746_() {
        return this.m_7237_().isDone();
    }

    default public void m_7748_() {
        CompletableFuture<?> $$0 = this.m_7237_();
        if ($$0.isCompletedExceptionally()) {
            $$0.join();
        }
    }
}

