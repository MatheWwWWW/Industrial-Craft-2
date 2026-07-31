/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 */
package net.minecraft.util;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.ThreadSafeLegacyRandomSource;

public interface RandomSource {
    @Deprecated
    public static final double f_216326_ = 2.297;

    public static RandomSource m_216327_() {
        return RandomSource.m_216335_(RandomSupport.m_224599_());
    }

    @Deprecated
    public static RandomSource m_216337_() {
        return new ThreadSafeLegacyRandomSource(RandomSupport.m_224599_());
    }

    public static RandomSource m_216335_(long p_216336_) {
        return new LegacyRandomSource(p_216336_);
    }

    public static RandomSource m_216343_() {
        return new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());
    }

    public RandomSource m_213769_();

    public PositionalRandomFactory m_188582_();

    public void m_188584_(long var1);

    public int m_188502_();

    public int m_188503_(int var1);

    default public int m_216332_(int p_216333_, int p_216334_) {
        return this.m_188503_(p_216334_ - p_216333_ + 1) + p_216333_;
    }

    public long m_188505_();

    public boolean m_188499_();

    public float m_188501_();

    public double m_188500_();

    public double m_188583_();

    default public double m_216328_(double p_216329_, double p_216330_) {
        return p_216329_ + p_216330_ * (this.m_188500_() - this.m_188500_());
    }

    default public void m_190110_(int p_216338_) {
        for (int $$1 = 0; $$1 < p_216338_; ++$$1) {
            this.m_188502_();
        }
    }

    default public int m_216339_(int p_216340_, int p_216341_) {
        if (p_216340_ >= p_216341_) {
            throw new IllegalArgumentException("bound - origin is non positive");
        }
        return p_216340_ + this.m_188503_(p_216341_ - p_216340_);
    }
}

