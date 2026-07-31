/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.RateLimiter
 */
package net.minecraft.realms;

import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.GameNarrator;
import net.minecraft.network.chat.Component;

public class RepeatedNarrator {
    private final float f_120785_;
    private final AtomicReference<Params> f_120786_ = new AtomicReference();

    public RepeatedNarrator(Duration p_120788_) {
        this.f_120785_ = 1000.0f / (float)p_120788_.toMillis();
    }

    public void m_240428_(GameNarrator p_240528_, Component p_240604_) {
        Params $$2 = this.f_120786_.updateAndGet(p_175080_ -> {
            if (p_175080_ == null || !p_240604_.equals(p_175080_.f_120794_)) {
                return new Params(p_240604_, RateLimiter.create((double)this.f_120785_));
            }
            return p_175080_;
        });
        if ($$2.f_120795_.tryAcquire(1)) {
            p_240528_.m_168785_(p_240604_);
        }
    }

    static class Params {
        final Component f_120794_;
        final RateLimiter f_120795_;

        Params(Component p_175082_, RateLimiter p_175083_) {
            this.f_120794_ = p_175082_;
            this.f_120795_ = p_175083_;
        }
    }
}

