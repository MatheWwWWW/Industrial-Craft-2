/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.util;

import java.util.Arrays;
import java.util.function.IntConsumer;
import net.minecraft.util.BitStorage;
import org.apache.commons.lang3.Validate;

public class ZeroBitStorage
implements BitStorage {
    public static final long[] f_184787_ = new long[0];
    private final int f_184788_;

    public ZeroBitStorage(int p_184791_) {
        this.f_184788_ = p_184791_;
    }

    @Override
    public int m_13516_(int p_184796_, int p_184797_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_184788_ - 1), (long)p_184796_);
        Validate.inclusiveBetween((long)0L, (long)0L, (long)p_184797_);
        return 0;
    }

    @Override
    public void m_13524_(int p_184802_, int p_184803_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_184788_ - 1), (long)p_184802_);
        Validate.inclusiveBetween((long)0L, (long)0L, (long)p_184803_);
    }

    @Override
    public int m_13514_(int p_184794_) {
        Validate.inclusiveBetween((long)0L, (long)(this.f_184788_ - 1), (long)p_184794_);
        return 0;
    }

    @Override
    public long[] m_13513_() {
        return f_184787_;
    }

    @Override
    public int m_13521_() {
        return this.f_184788_;
    }

    @Override
    public int m_144604_() {
        return 0;
    }

    @Override
    public void m_13519_(IntConsumer p_184799_) {
        for (int $$1 = 0; $$1 < this.f_184788_; ++$$1) {
            p_184799_.accept(0);
        }
    }

    @Override
    public void m_197970_(int[] p_198170_) {
        Arrays.fill(p_198170_, 0, this.f_184788_, 0);
    }

    @Override
    public BitStorage m_199833_() {
        return this;
    }
}

