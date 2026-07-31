/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Charsets
 *  com.google.common.hash.HashFunction
 *  com.google.common.hash.Hashing
 *  com.google.common.primitives.Longs
 */
package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.MarsagliaPolarGaussian;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.Xoroshiro128PlusPlus;

public class XoroshiroRandomSource
implements RandomSource {
    private static final float f_190097_ = 5.9604645E-8f;
    private static final double f_190098_ = (double)1.110223E-16f;
    private Xoroshiro128PlusPlus f_190099_;
    private final MarsagliaPolarGaussian f_190100_ = new MarsagliaPolarGaussian(this);

    public XoroshiroRandomSource(long p_190102_) {
        this.f_190099_ = new Xoroshiro128PlusPlus(RandomSupport.m_189331_(p_190102_));
    }

    public XoroshiroRandomSource(long p_190104_, long p_190105_) {
        this.f_190099_ = new Xoroshiro128PlusPlus(p_190104_, p_190105_);
    }

    @Override
    public RandomSource m_213769_() {
        return new XoroshiroRandomSource(this.f_190099_.m_190096_(), this.f_190099_.m_190096_());
    }

    @Override
    public PositionalRandomFactory m_188582_() {
        return new XoroshiroPositionalRandomFactory(this.f_190099_.m_190096_(), this.f_190099_.m_190096_());
    }

    @Override
    public void m_188584_(long p_190121_) {
        this.f_190099_ = new Xoroshiro128PlusPlus(RandomSupport.m_189331_(p_190121_));
        this.f_190100_.m_188602_();
    }

    @Override
    public int m_188502_() {
        return (int)this.f_190099_.m_190096_();
    }

    @Override
    public int m_188503_(int p_190118_) {
        if (p_190118_ <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }
        long $$1 = Integer.toUnsignedLong(this.m_188502_());
        long $$2 = $$1 * (long)p_190118_;
        long $$3 = $$2 & 0xFFFFFFFFL;
        if ($$3 < (long)p_190118_) {
            int $$4 = Integer.remainderUnsigned(~p_190118_ + 1, p_190118_);
            while ($$3 < (long)$$4) {
                $$1 = Integer.toUnsignedLong(this.m_188502_());
                $$2 = $$1 * (long)p_190118_;
                $$3 = $$2 & 0xFFFFFFFFL;
            }
        }
        long $$5 = $$2 >> 32;
        return (int)$$5;
    }

    @Override
    public long m_188505_() {
        return this.f_190099_.m_190096_();
    }

    @Override
    public boolean m_188499_() {
        return (this.f_190099_.m_190096_() & 1L) != 0L;
    }

    @Override
    public float m_188501_() {
        return (float)this.m_190107_(24) * 5.9604645E-8f;
    }

    @Override
    public double m_188500_() {
        return (double)this.m_190107_(53) * (double)1.110223E-16f;
    }

    @Override
    public double m_188583_() {
        return this.f_190100_.m_188603_();
    }

    @Override
    public void m_190110_(int p_190111_) {
        for (int $$1 = 0; $$1 < p_190111_; ++$$1) {
            this.f_190099_.m_190096_();
        }
    }

    private long m_190107_(int p_190108_) {
        return this.f_190099_.m_190096_() >>> 64 - p_190108_;
    }

    public static class XoroshiroPositionalRandomFactory
    implements PositionalRandomFactory {
        private static final HashFunction f_190122_ = Hashing.md5();
        private final long f_190123_;
        private final long f_190124_;

        public XoroshiroPositionalRandomFactory(long p_190127_, long p_190128_) {
            this.f_190123_ = p_190127_;
            this.f_190124_ = p_190128_;
        }

        @Override
        public RandomSource m_213715_(int p_224691_, int p_224692_, int p_224693_) {
            long $$3 = Mth.m_14130_(p_224691_, p_224692_, p_224693_);
            long $$4 = $$3 ^ this.f_190123_;
            return new XoroshiroRandomSource($$4, this.f_190124_);
        }

        @Override
        public RandomSource m_214111_(String p_224695_) {
            byte[] $$1 = f_190122_.hashString((CharSequence)p_224695_, Charsets.UTF_8).asBytes();
            long $$2 = Longs.fromBytes((byte)$$1[0], (byte)$$1[1], (byte)$$1[2], (byte)$$1[3], (byte)$$1[4], (byte)$$1[5], (byte)$$1[6], (byte)$$1[7]);
            long $$3 = Longs.fromBytes((byte)$$1[8], (byte)$$1[9], (byte)$$1[10], (byte)$$1[11], (byte)$$1[12], (byte)$$1[13], (byte)$$1[14], (byte)$$1[15]);
            return new XoroshiroRandomSource($$2 ^ this.f_190123_, $$3 ^ this.f_190124_);
        }

        @Override
        @VisibleForTesting
        public void m_183502_(StringBuilder p_190136_) {
            p_190136_.append("seedLo: ").append(this.f_190123_).append(", seedHi: ").append(this.f_190124_);
        }
    }
}

