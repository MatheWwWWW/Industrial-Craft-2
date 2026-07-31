/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.util.datafix;

import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;

public class PackedBitStorage {
    private static final int f_145044_ = 6;
    private final long[] f_14550_;
    private final int f_14551_;
    private final long f_14552_;
    private final int f_14553_;

    public PackedBitStorage(int p_14555_, int p_14556_) {
        this(p_14555_, p_14556_, new long[Mth.m_144941_(p_14556_ * p_14555_, 64) / 64]);
    }

    public PackedBitStorage(int p_14558_, int p_14559_, long[] p_14560_) {
        Validate.inclusiveBetween((long)1L, (long)32L, (long)p_14558_);
        this.f_14553_ = p_14559_;
        this.f_14551_ = p_14558_;
        this.f_14550_ = p_14560_;
        this.f_14552_ = (1L << p_14558_) - 1L;
        int $$3 = Mth.m_144941_(p_14559_ * p_14558_, 64) / 64;
        if (p_14560_.length != $$3) {
            throw new IllegalArgumentException("Invalid length given for storage, got: " + p_14560_.length + " but expected: " + $$3);
        }
    }

    public void m_14564_(int p_14565_, int p_14566_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_14553_ - 1), (long)p_14565_);
        Validate.inclusiveBetween((long)0L, (long)this.f_14552_, (long)p_14566_);
        int $$2 = p_14565_ * this.f_14551_;
        int $$3 = $$2 >> 6;
        int $$4 = (p_14565_ + 1) * this.f_14551_ - 1 >> 6;
        int $$5 = $$2 ^ $$3 << 6;
        this.f_14550_[$$3] = this.f_14550_[$$3] & (this.f_14552_ << $$5 ^ 0xFFFFFFFFFFFFFFFFL) | ((long)p_14566_ & this.f_14552_) << $$5;
        if ($$3 != $$4) {
            int $$6 = 64 - $$5;
            int $$7 = this.f_14551_ - $$6;
            this.f_14550_[$$4] = this.f_14550_[$$4] >>> $$7 << $$7 | ((long)p_14566_ & this.f_14552_) >> $$6;
        }
    }

    public int m_14562_(int p_14563_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_14553_ - 1), (long)p_14563_);
        int $$1 = p_14563_ * this.f_14551_;
        int $$2 = $$1 >> 6;
        int $$3 = (p_14563_ + 1) * this.f_14551_ - 1 >> 6;
        int $$4 = $$1 ^ $$2 << 6;
        if ($$2 == $$3) {
            return (int)(this.f_14550_[$$2] >>> $$4 & this.f_14552_);
        }
        int $$5 = 64 - $$4;
        return (int)((this.f_14550_[$$2] >>> $$4 | this.f_14550_[$$3] << $$5) & this.f_14552_);
    }

    public long[] m_14561_() {
        return this.f_14550_;
    }

    public int m_14567_() {
        return this.f_14551_;
    }
}

