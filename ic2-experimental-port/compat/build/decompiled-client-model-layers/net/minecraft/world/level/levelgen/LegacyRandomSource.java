/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.MarsagliaPolarGaussian;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public class LegacyRandomSource
implements BitRandomSource {
    private static final int f_188571_ = 48;
    private static final long f_188572_ = 0xFFFFFFFFFFFFL;
    private static final long f_188573_ = 25214903917L;
    private static final long f_188574_ = 11L;
    private final AtomicLong f_188575_ = new AtomicLong();
    private final MarsagliaPolarGaussian f_188576_ = new MarsagliaPolarGaussian(this);

    public LegacyRandomSource(long p_188578_) {
        this.m_188584_(p_188578_);
    }

    @Override
    public RandomSource m_213769_() {
        return new LegacyRandomSource(this.m_188505_());
    }

    @Override
    public PositionalRandomFactory m_188582_() {
        return new LegacyPositionalRandomFactory(this.m_188505_());
    }

    @Override
    public void m_188584_(long p_188585_) {
        if (!this.f_188575_.compareAndSet(this.f_188575_.get(), (p_188585_ ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL)) {
            throw ThreadingDetector.m_199417_("LegacyRandomSource", null);
        }
        this.f_188576_.m_188602_();
    }

    @Override
    public int m_64707_(int p_188581_) {
        long $$2;
        long $$1 = this.f_188575_.get();
        if (!this.f_188575_.compareAndSet($$1, $$2 = $$1 * 25214903917L + 11L & 0xFFFFFFFFFFFFL)) {
            throw ThreadingDetector.m_199417_("LegacyRandomSource", null);
        }
        return (int)($$2 >> 48 - p_188581_);
    }

    @Override
    public double m_188583_() {
        return this.f_188576_.m_188603_();
    }

    public static class LegacyPositionalRandomFactory
    implements PositionalRandomFactory {
        private final long f_188586_;

        public LegacyPositionalRandomFactory(long p_188588_) {
            this.f_188586_ = p_188588_;
        }

        @Override
        public RandomSource m_213715_(int p_224198_, int p_224199_, int p_224200_) {
            long $$3 = Mth.m_14130_(p_224198_, p_224199_, p_224200_);
            long $$4 = $$3 ^ this.f_188586_;
            return new LegacyRandomSource($$4);
        }

        @Override
        public RandomSource m_214111_(String p_224202_) {
            int $$1 = p_224202_.hashCode();
            return new LegacyRandomSource((long)$$1 ^ this.f_188586_);
        }

        @Override
        @VisibleForTesting
        public void m_183502_(StringBuilder p_188596_) {
            p_188596_.append("LegacyPositionalRandomFactory{").append(this.f_188586_).append("}");
        }
    }
}

