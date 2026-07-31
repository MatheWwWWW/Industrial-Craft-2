/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.sounds;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class ChannelAccess {
    private final Set<ChannelHandle> f_120121_ = Sets.newIdentityHashSet();
    final Library f_120122_;
    final Executor f_120123_;

    public ChannelAccess(Library p_120125_, Executor p_120126_) {
        this.f_120122_ = p_120125_;
        this.f_120123_ = p_120126_;
    }

    public CompletableFuture<ChannelHandle> m_120128_(Library.Pool p_120129_) {
        CompletableFuture<ChannelHandle> $$1 = new CompletableFuture<ChannelHandle>();
        this.f_120123_.execute(() -> {
            Channel $$2 = this.f_120122_.m_83697_(p_120129_);
            if ($$2 != null) {
                ChannelHandle $$3 = new ChannelHandle($$2);
                this.f_120121_.add($$3);
                $$1.complete($$3);
            } else {
                $$1.complete(null);
            }
        });
        return $$1;
    }

    public void m_120137_(Consumer<Stream<Channel>> p_120138_) {
        this.f_120123_.execute(() -> p_120138_.accept(this.f_120121_.stream().map(p_174978_ -> p_174978_.f_120146_).filter(Objects::nonNull)));
    }

    public void m_120127_() {
        this.f_120123_.execute(() -> {
            Iterator<ChannelHandle> $$0 = this.f_120121_.iterator();
            while ($$0.hasNext()) {
                ChannelHandle $$1 = $$0.next();
                $$1.f_120146_.m_83682_();
                if (!$$1.f_120146_.m_83680_()) continue;
                $$1.m_120156_();
                $$0.remove();
            }
        });
    }

    public void m_120139_() {
        this.f_120121_.forEach(ChannelHandle::m_120156_);
        this.f_120121_.clear();
    }

    public class ChannelHandle {
        @Nullable
        Channel f_120146_;
        private boolean f_120147_;

        public boolean m_120151_() {
            return this.f_120147_;
        }

        public ChannelHandle(Channel p_120150_) {
            this.f_120146_ = p_120150_;
        }

        public void m_120154_(Consumer<Channel> p_120155_) {
            ChannelAccess.this.f_120123_.execute(() -> {
                if (this.f_120146_ != null) {
                    p_120155_.accept(this.f_120146_);
                }
            });
        }

        public void m_120156_() {
            this.f_120147_ = true;
            ChannelAccess.this.f_120122_.m_83695_(this.f_120146_);
            this.f_120146_ = null;
        }
    }
}

