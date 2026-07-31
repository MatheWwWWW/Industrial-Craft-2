/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import java.util.function.Consumer;
import net.minecraft.util.Mth;

public class AnimationState {
    private static final long f_216969_ = Long.MAX_VALUE;
    private long f_216970_ = Long.MAX_VALUE;
    private long f_216971_;

    public void m_216977_(int p_216978_) {
        this.f_216970_ = (long)p_216978_ * 1000L / 20L;
        this.f_216971_ = 0L;
    }

    public void m_216982_(int p_216983_) {
        if (!this.m_216984_()) {
            this.m_216977_(p_216983_);
        }
    }

    public void m_216973_() {
        this.f_216970_ = Long.MAX_VALUE;
    }

    public void m_216979_(Consumer<AnimationState> p_216980_) {
        if (this.m_216984_()) {
            p_216980_.accept(this);
        }
    }

    public void m_216974_(float p_216975_, float p_216976_) {
        if (!this.m_216984_()) {
            return;
        }
        long $$2 = Mth.m_14134_(p_216975_ * 1000.0f / 20.0f);
        this.f_216971_ += (long)((float)($$2 - this.f_216970_) * p_216976_);
        this.f_216970_ = $$2;
    }

    public long m_216981_() {
        return this.f_216971_;
    }

    public boolean m_216984_() {
        return this.f_216970_ != Long.MAX_VALUE;
    }
}

