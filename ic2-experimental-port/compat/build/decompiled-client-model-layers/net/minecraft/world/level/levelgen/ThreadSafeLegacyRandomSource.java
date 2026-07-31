/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.MarsagliaPolarGaussian;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

@Deprecated
public class ThreadSafeLegacyRandomSource
implements BitRandomSource {
    private static final int f_224657_ = 48;
    private static final long f_224658_ = 0xFFFFFFFFFFFFL;
    private static final long f_224659_ = 25214903917L;
    private static final long f_224660_ = 11L;
    private final AtomicLong f_224661_ = new AtomicLong();
    private final MarsagliaPolarGaussian f_224662_ = new MarsagliaPolarGaussian(this);

    public ThreadSafeLegacyRandomSource(long p_224664_) {
        this.m_188584_(p_224664_);
    }

    @Override
    public RandomSource m_213769_() {
        return new ThreadSafeLegacyRandomSource(this.m_188505_());
    }

    @Override
    public PositionalRandomFactory m_188582_() {
        return new LegacyRandomSource.LegacyPositionalRandomFactory(this.m_188505_());
    }

    @Override
    public void m_188584_(long p_224666_) {
        this.f_224661_.set((p_224666_ ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL);
    }

    @Override
    public int m_64707_(int p_224668_) {
        long $$2;
        long $$1;
        while (!this.f_224661_.compareAndSet($$1 = this.f_224661_.get(), $$2 = $$1 * 25214903917L + 11L & 0xFFFFFFFFFFFFL)) {
        }
        return (int)($$2 >>> 48 - p_224668_);
    }

    @Override
    public double m_188583_() {
        return this.f_224662_.m_188603_();
    }
}

