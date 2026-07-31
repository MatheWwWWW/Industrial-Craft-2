/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.Immutable
 */
package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;

@Immutable
public class DifficultyInstance {
    private static final float f_146646_ = -72000.0f;
    private static final float f_146647_ = 1440000.0f;
    private static final float f_146648_ = 3600000.0f;
    private final Difficulty f_19041_;
    private final float f_19042_;

    public DifficultyInstance(Difficulty p_19044_, long p_19045_, long p_19046_, float p_19047_) {
        this.f_19041_ = p_19044_;
        this.f_19042_ = this.m_19051_(p_19044_, p_19045_, p_19046_, p_19047_);
    }

    public Difficulty m_19048_() {
        return this.f_19041_;
    }

    public float m_19056_() {
        return this.f_19042_;
    }

    public boolean m_146649_() {
        return this.f_19042_ >= (float)Difficulty.HARD.ordinal();
    }

    public boolean m_19049_(float p_19050_) {
        return this.f_19042_ > p_19050_;
    }

    public float m_19057_() {
        if (this.f_19042_ < 2.0f) {
            return 0.0f;
        }
        if (this.f_19042_ > 4.0f) {
            return 1.0f;
        }
        return (this.f_19042_ - 2.0f) / 2.0f;
    }

    private float m_19051_(Difficulty p_19052_, long p_19053_, long p_19054_, float p_19055_) {
        if (p_19052_ == Difficulty.PEACEFUL) {
            return 0.0f;
        }
        boolean $$4 = p_19052_ == Difficulty.HARD;
        float $$5 = 0.75f;
        float $$6 = Mth.m_14036_(((float)p_19053_ + -72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        $$5 += $$6;
        float $$7 = 0.0f;
        $$7 += Mth.m_14036_((float)p_19054_ / 3600000.0f, 0.0f, 1.0f) * ($$4 ? 1.0f : 0.75f);
        $$7 += Mth.m_14036_(p_19055_ * 0.25f, 0.0f, $$6);
        if (p_19052_ == Difficulty.EASY) {
            $$7 *= 0.5f;
        }
        return (float)p_19052_.m_19028_() * ($$5 += $$7);
    }
}

