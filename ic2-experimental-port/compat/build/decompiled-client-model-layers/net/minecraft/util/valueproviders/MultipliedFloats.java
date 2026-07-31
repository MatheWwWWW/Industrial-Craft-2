/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.valueproviders;

import java.util.Arrays;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.SampledFloat;

public class MultipliedFloats
implements SampledFloat {
    private final SampledFloat[] f_216856_;

    public MultipliedFloats(SampledFloat ... p_216858_) {
        this.f_216856_ = p_216858_;
    }

    @Override
    public float m_214084_(RandomSource p_216860_) {
        float $$1 = 1.0f;
        for (int $$2 = 0; $$2 < this.f_216856_.length; ++$$2) {
            $$1 *= this.f_216856_[$$2].m_214084_(p_216860_);
        }
        return $$1;
    }

    public String toString() {
        return "MultipliedFloats" + Arrays.toString(this.f_216856_);
    }
}

