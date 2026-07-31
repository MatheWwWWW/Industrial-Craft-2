/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class ChatPreviewAnimator {
    private static final long f_241675_ = 200L;
    @Nullable
    private Component f_241697_;
    private long f_241647_;
    private long f_241676_;

    public void m_241926_(long p_242307_) {
        this.f_241697_ = null;
        this.f_241647_ = 0L;
        this.f_241676_ = p_242307_;
    }

    public State m_241860_(long p_242415_, @Nullable Component p_242349_) {
        long $$2 = p_242415_ - this.f_241676_;
        this.f_241676_ = p_242415_;
        if (p_242349_ != null) {
            return this.m_242010_($$2, p_242349_);
        }
        return this.m_241961_($$2);
    }

    private State m_242010_(long p_242198_, Component p_242208_) {
        this.f_241697_ = p_242208_;
        if (this.f_241647_ < 200L) {
            this.f_241647_ = Math.min(this.f_241647_ + p_242198_, 200L);
        }
        return new State(p_242208_, ChatPreviewAnimator.m_241802_(this.f_241647_));
    }

    private State m_241961_(long p_242440_) {
        if (this.f_241647_ > 0L) {
            this.f_241647_ = Math.max(this.f_241647_ - p_242440_, 0L);
        }
        return this.f_241647_ > 0L ? new State(this.f_241697_, ChatPreviewAnimator.m_241802_(this.f_241647_)) : State.f_241601_;
    }

    private static float m_241802_(long p_242250_) {
        return (float)p_242250_ / 200.0f;
    }

    public record State(@Nullable Component f_241637_, float f_241616_) {
        public static final State f_241601_ = new State(null, 0.0f);

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{State.class, "preview;alpha", "f_241637_", "f_241616_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{State.class, "preview;alpha", "f_241637_", "f_241616_"}, this);
        }

        @Override
        public final boolean equals(Object p_242171_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{State.class, "preview;alpha", "f_241637_", "f_241616_"}, this, p_242171_);
        }
    }
}

