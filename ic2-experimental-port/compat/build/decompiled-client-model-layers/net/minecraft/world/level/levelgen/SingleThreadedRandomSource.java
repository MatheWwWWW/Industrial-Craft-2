/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.MarsagliaPolarGaussian;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public class SingleThreadedRandomSource
implements BitRandomSource {
    private static final int f_189346_ = 48;
    private static final long f_189347_ = 0xFFFFFFFFFFFFL;
    private static final long f_189348_ = 25214903917L;
    private static final long f_189349_ = 11L;
    private long f_189350_;
    private final MarsagliaPolarGaussian f_189351_ = new MarsagliaPolarGaussian(this);

    public SingleThreadedRandomSource(long p_189353_) {
        this.m_188584_(p_189353_);
    }

    @Override
    public RandomSource m_213769_() {
        return new SingleThreadedRandomSource(this.m_188505_());
    }

    @Override
    public PositionalRandomFactory m_188582_() {
        return new LegacyRandomSource.LegacyPositionalRandomFactory(this.m_188505_());
    }

    @Override
    public void m_188584_(long p_189360_) {
        this.f_189350_ = (p_189360_ ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
        this.f_189351_.m_188602_();
    }

    @Override
    public int m_64707_(int p_189356_) {
        long $$1;
        this.f_189350_ = $$1 = this.f_189350_ * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
        return (int)($$1 >> 48 - p_189356_);
    }

    @Override
    public double m_188583_() {
        return this.f_189351_.m_188603_();
    }
}

