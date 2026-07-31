/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import java.util.function.LongFunction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;

public class WorldgenRandom
extends LegacyRandomSource {
    private final RandomSource f_190054_;
    private int f_64676_;

    public WorldgenRandom(RandomSource p_224680_) {
        super(0L);
        this.f_190054_ = p_224680_;
    }

    public int m_158960_() {
        return this.f_64676_;
    }

    @Override
    public RandomSource m_213769_() {
        return this.f_190054_.m_213769_();
    }

    @Override
    public PositionalRandomFactory m_188582_() {
        return this.f_190054_.m_188582_();
    }

    @Override
    public int m_64707_(int p_64708_) {
        ++this.f_64676_;
        RandomSource randomSource = this.f_190054_;
        if (randomSource instanceof LegacyRandomSource) {
            LegacyRandomSource $$1 = (LegacyRandomSource)randomSource;
            return $$1.m_64707_(p_64708_);
        }
        return (int)(this.f_190054_.m_188505_() >>> 64 - p_64708_);
    }

    @Override
    public synchronized void m_188584_(long p_190073_) {
        if (this.f_190054_ == null) {
            return;
        }
        this.f_190054_.m_188584_(p_190073_);
    }

    public long m_64690_(long p_64691_, int p_64692_, int p_64693_) {
        this.m_188584_(p_64691_);
        long $$3 = this.m_188505_() | 1L;
        long $$4 = this.m_188505_() | 1L;
        long $$5 = (long)p_64692_ * $$3 + (long)p_64693_ * $$4 ^ p_64691_;
        this.m_188584_($$5);
        return $$5;
    }

    public void m_190064_(long p_190065_, int p_190066_, int p_190067_) {
        long $$3 = p_190065_ + (long)p_190066_ + (long)(10000 * p_190067_);
        this.m_188584_($$3);
    }

    public void m_190068_(long p_190069_, int p_190070_, int p_190071_) {
        this.m_188584_(p_190069_);
        long $$3 = this.m_188505_();
        long $$4 = this.m_188505_();
        long $$5 = (long)p_190070_ * $$3 ^ (long)p_190071_ * $$4 ^ p_190069_;
        this.m_188584_($$5);
    }

    public void m_190058_(long p_190059_, int p_190060_, int p_190061_, int p_190062_) {
        long $$4 = (long)p_190060_ * 341873128712L + (long)p_190061_ * 132897987541L + p_190059_ + (long)p_190062_;
        this.m_188584_($$4);
    }

    public static RandomSource m_224681_(int p_224682_, int p_224683_, long p_224684_, long p_224685_) {
        return RandomSource.m_216335_(p_224684_ + (long)(p_224682_ * p_224682_ * 4987142) + (long)(p_224682_ * 5947611) + (long)(p_224683_ * p_224683_) * 4392871L + (long)(p_224683_ * 389711) ^ p_224685_);
    }

    public static final class Algorithm
    extends Enum<Algorithm> {
        public static final /* enum */ Algorithm LEGACY = new Algorithm(LegacyRandomSource::new);
        public static final /* enum */ Algorithm XOROSHIRO = new Algorithm(XoroshiroRandomSource::new);
        private final LongFunction<RandomSource> f_190076_;
        private static final /* synthetic */ Algorithm[] $VALUES;

        public static Algorithm[] values() {
            return (Algorithm[])$VALUES.clone();
        }

        public static Algorithm valueOf(String p_190087_) {
            return Enum.valueOf(Algorithm.class, p_190087_);
        }

        private Algorithm(LongFunction<RandomSource> p_190082_) {
            this.f_190076_ = p_190082_;
        }

        public RandomSource m_224687_(long p_224688_) {
            return this.f_190076_.apply(p_224688_);
        }

        private static /* synthetic */ Algorithm[] m_190083_() {
            return new Algorithm[]{LEGACY, XOROSHIRO};
        }

        static {
            $VALUES = Algorithm.m_190083_();
        }
    }
}

