/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.util.RandomSource;

public interface BitRandomSource
extends RandomSource {
    public static final float f_188496_ = 5.9604645E-8f;
    public static final double f_188497_ = (double)1.110223E-16f;

    public int m_64707_(int var1);

    @Override
    default public int m_188502_() {
        return this.m_64707_(32);
    }

    @Override
    default public int m_188503_(int p_188504_) {
        int $$2;
        int $$1;
        if (p_188504_ <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }
        if ((p_188504_ & p_188504_ - 1) == 0) {
            return (int)((long)p_188504_ * (long)this.m_64707_(31) >> 31);
        }
        while (($$1 = this.m_64707_(31)) - ($$2 = $$1 % p_188504_) + (p_188504_ - 1) < 0) {
        }
        return $$2;
    }

    @Override
    default public long m_188505_() {
        int $$0 = this.m_64707_(32);
        int $$1 = this.m_64707_(32);
        long $$2 = (long)$$0 << 32;
        return $$2 + (long)$$1;
    }

    @Override
    default public boolean m_188499_() {
        return this.m_64707_(1) != 0;
    }

    @Override
    default public float m_188501_() {
        return (float)this.m_64707_(24) * 5.9604645E-8f;
    }

    @Override
    default public double m_188500_() {
        int $$0 = this.m_64707_(26);
        int $$1 = this.m_64707_(27);
        long $$2 = ((long)$$0 << 27) + (long)$$1;
        return (double)$$2 * (double)1.110223E-16f;
    }
}

