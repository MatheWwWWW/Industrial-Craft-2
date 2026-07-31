/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

public class ChatPreviewThrottler {
    private final AtomicReference<Request> f_236972_ = new AtomicReference();
    @Nullable
    private CompletableFuture<?> f_236973_;

    public void m_236975_() {
        if (this.f_236973_ != null && this.f_236973_.isDone()) {
            this.f_236973_ = null;
        }
        if (this.f_236973_ == null) {
            this.m_236978_();
        }
    }

    private void m_236978_() {
        Request $$0 = this.f_236972_.getAndSet(null);
        if ($$0 != null) {
            this.f_236973_ = $$0.m_236979_();
        }
    }

    public void m_236976_(Request p_236977_) {
        this.f_236972_.set(p_236977_);
    }

    @FunctionalInterface
    public static interface Request {
        public CompletableFuture<?> m_236979_();
    }
}

