/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

public class RunningTrimmedMean {
    private final long[] f_110707_;
    private int f_110708_;
    private int f_110709_;

    public RunningTrimmedMean(int p_110711_) {
        this.f_110707_ = new long[p_110711_];
    }

    public long m_110712_(long p_110713_) {
        if (this.f_110708_ < this.f_110707_.length) {
            ++this.f_110708_;
        }
        this.f_110707_[this.f_110709_] = p_110713_;
        this.f_110709_ = (this.f_110709_ + 1) % this.f_110707_.length;
        long $$1 = Long.MAX_VALUE;
        long $$2 = Long.MIN_VALUE;
        long $$3 = 0L;
        for (int $$4 = 0; $$4 < this.f_110708_; ++$$4) {
            long $$5 = this.f_110707_[$$4];
            $$3 += $$5;
            $$1 = Math.min($$1, $$5);
            $$2 = Math.max($$2, $$5);
        }
        if (this.f_110708_ > 2) {
            return ($$3 -= $$1 + $$2) / (long)(this.f_110708_ - 2);
        }
        if ($$3 > 0L) {
            return (long)this.f_110708_ / $$3;
        }
        return 0L;
    }
}

