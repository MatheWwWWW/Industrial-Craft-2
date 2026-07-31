/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.util;

import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.util.BitStorage;
import org.apache.commons.lang3.Validate;

public class SimpleBitStorage
implements BitStorage {
    private static final int[] f_184706_ = new int[]{-1, -1, 0, Integer.MIN_VALUE, 0, 0, 0x55555555, 0x55555555, 0, Integer.MIN_VALUE, 0, 1, 0x33333333, 0x33333333, 0, 0x2AAAAAAA, 0x2AAAAAAA, 0, 0x24924924, 0x24924924, 0, Integer.MIN_VALUE, 0, 2, 0x1C71C71C, 0x1C71C71C, 0, 0x19999999, 0x19999999, 0, 390451572, 390451572, 0, 0x15555555, 0x15555555, 0, 0x13B13B13, 0x13B13B13, 0, 306783378, 306783378, 0, 0x11111111, 0x11111111, 0, Integer.MIN_VALUE, 0, 3, 0xF0F0F0F, 0xF0F0F0F, 0, 0xE38E38E, 0xE38E38E, 0, 226050910, 226050910, 0, 0xCCCCCCC, 0xCCCCCCC, 0, 0xC30C30C, 0xC30C30C, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 0xAAAAAAA, 0xAAAAAAA, 0, 171798691, 171798691, 0, 0x9D89D89, 0x9D89D89, 0, 159072862, 159072862, 0, 0x9249249, 0x9249249, 0, 148102320, 148102320, 0, 0x8888888, 0x8888888, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 0x7878787, 0x7878787, 0, 0x7507507, 0x7507507, 0, 0x71C71C7, 0x71C71C7, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 0x6906906, 0x6906906, 0, 0x6666666, 0x6666666, 0, 104755299, 104755299, 0, 0x6186186, 0x6186186, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 0x5B05B05, 0x5B05B05, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 0x5555555, 0x5555555, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 0x5050505, 0x5050505, 0, 0x4EC4EC4, 0x4EC4EC4, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 0x4924924, 0x4924924, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 0x4444444, 0x4444444, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 0x4104104, 0x4104104, 0, Integer.MIN_VALUE, 0, 5};
    private final long[] f_184707_;
    private final int f_184708_;
    private final long f_184709_;
    private final int f_184710_;
    private final int f_184711_;
    private final int f_184712_;
    private final int f_184713_;
    private final int f_184714_;

    public SimpleBitStorage(int p_198164_, int p_198165_, int[] p_198166_) {
        this(p_198164_, p_198165_);
        int $$4;
        int $$3 = 0;
        for ($$4 = 0; $$4 <= p_198165_ - this.f_184711_; $$4 += this.f_184711_) {
            long $$5 = 0L;
            for (int $$6 = this.f_184711_ - 1; $$6 >= 0; --$$6) {
                $$5 <<= p_198164_;
                $$5 |= (long)p_198166_[$$4 + $$6] & this.f_184709_;
            }
            this.f_184707_[$$3++] = $$5;
        }
        int $$7 = p_198165_ - $$4;
        if ($$7 > 0) {
            long $$8 = 0L;
            for (int $$9 = $$7 - 1; $$9 >= 0; --$$9) {
                $$8 <<= p_198164_;
                $$8 |= (long)p_198166_[$$4 + $$9] & this.f_184709_;
            }
            this.f_184707_[$$3] = $$8;
        }
    }

    public SimpleBitStorage(int p_184717_, int p_184718_) {
        this(p_184717_, p_184718_, (long[])null);
    }

    public SimpleBitStorage(int p_184724_, int p_184725_, @Nullable long[] p_184726_) {
        Validate.inclusiveBetween((long)1L, (long)32L, (long)p_184724_);
        this.f_184710_ = p_184725_;
        this.f_184708_ = p_184724_;
        this.f_184709_ = (1L << p_184724_) - 1L;
        this.f_184711_ = (char)(64 / p_184724_);
        int $$3 = 3 * (this.f_184711_ - 1);
        this.f_184712_ = f_184706_[$$3 + 0];
        this.f_184713_ = f_184706_[$$3 + 1];
        this.f_184714_ = f_184706_[$$3 + 2];
        int $$4 = (p_184725_ + this.f_184711_ - 1) / this.f_184711_;
        if (p_184726_ != null) {
            if (p_184726_.length != $$4) {
                throw new InitializationException("Invalid length given for storage, got: " + p_184726_.length + " but expected: " + $$4);
            }
            this.f_184707_ = p_184726_;
        } else {
            this.f_184707_ = new long[$$4];
        }
    }

    private int m_184739_(int p_184740_) {
        long $$1 = Integer.toUnsignedLong(this.f_184712_);
        long $$2 = Integer.toUnsignedLong(this.f_184713_);
        return (int)((long)p_184740_ * $$1 + $$2 >> 32 >> this.f_184714_);
    }

    @Override
    public int m_13516_(int p_184731_, int p_184732_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_184710_ - 1), (long)p_184731_);
        Validate.inclusiveBetween((long)0L, (long)this.f_184709_, (long)p_184732_);
        int $$2 = this.m_184739_(p_184731_);
        long $$3 = this.f_184707_[$$2];
        int $$4 = (p_184731_ - $$2 * this.f_184711_) * this.f_184708_;
        int $$5 = (int)($$3 >> $$4 & this.f_184709_);
        this.f_184707_[$$2] = $$3 & (this.f_184709_ << $$4 ^ 0xFFFFFFFFFFFFFFFFL) | ((long)p_184732_ & this.f_184709_) << $$4;
        return $$5;
    }

    @Override
    public void m_13524_(int p_184742_, int p_184743_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_184710_ - 1), (long)p_184742_);
        Validate.inclusiveBetween((long)0L, (long)this.f_184709_, (long)p_184743_);
        int $$2 = this.m_184739_(p_184742_);
        long $$3 = this.f_184707_[$$2];
        int $$4 = (p_184742_ - $$2 * this.f_184711_) * this.f_184708_;
        this.f_184707_[$$2] = $$3 & (this.f_184709_ << $$4 ^ 0xFFFFFFFFFFFFFFFFL) | ((long)p_184743_ & this.f_184709_) << $$4;
    }

    @Override
    public int m_13514_(int p_184729_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_184710_ - 1), (long)p_184729_);
        int $$1 = this.m_184739_(p_184729_);
        long $$2 = this.f_184707_[$$1];
        int $$3 = (p_184729_ - $$1 * this.f_184711_) * this.f_184708_;
        return (int)($$2 >> $$3 & this.f_184709_);
    }

    @Override
    public long[] m_13513_() {
        return this.f_184707_;
    }

    @Override
    public int m_13521_() {
        return this.f_184710_;
    }

    @Override
    public int m_144604_() {
        return this.f_184708_;
    }

    @Override
    public void m_13519_(IntConsumer p_184734_) {
        int $$1 = 0;
        for (long $$2 : this.f_184707_) {
            for (int $$3 = 0; $$3 < this.f_184711_; ++$$3) {
                p_184734_.accept((int)($$2 & this.f_184709_));
                $$2 >>= this.f_184708_;
                if (++$$1 < this.f_184710_) continue;
                return;
            }
        }
    }

    @Override
    public void m_197970_(int[] p_198168_) {
        int $$1 = this.f_184707_.length;
        int $$2 = 0;
        for (int $$3 = 0; $$3 < $$1 - 1; ++$$3) {
            long $$4 = this.f_184707_[$$3];
            for (int $$5 = 0; $$5 < this.f_184711_; ++$$5) {
                p_198168_[$$2 + $$5] = (int)($$4 & this.f_184709_);
                $$4 >>= this.f_184708_;
            }
            $$2 += this.f_184711_;
        }
        int $$6 = this.f_184710_ - $$2;
        if ($$6 > 0) {
            long $$7 = this.f_184707_[$$1 - 1];
            for (int $$8 = 0; $$8 < $$6; ++$$8) {
                p_198168_[$$2 + $$8] = (int)($$7 & this.f_184709_);
                $$7 >>= this.f_184708_;
            }
        }
    }

    @Override
    public BitStorage m_199833_() {
        return new SimpleBitStorage(this.f_184708_, this.f_184710_, (long[])this.f_184707_.clone());
    }

    public static class InitializationException
    extends RuntimeException {
        InitializationException(String p_184746_) {
            super(p_184746_);
        }
    }
}

