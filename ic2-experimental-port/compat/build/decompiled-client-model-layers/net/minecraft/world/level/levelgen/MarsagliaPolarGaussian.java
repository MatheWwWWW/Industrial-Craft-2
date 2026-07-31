/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class MarsagliaPolarGaussian {
    public final RandomSource f_188597_;
    private double f_188598_;
    private boolean f_188599_;

    public MarsagliaPolarGaussian(RandomSource p_224204_) {
        this.f_188597_ = p_224204_;
    }

    public void m_188602_() {
        this.f_188599_ = false;
    }

    public double m_188603_() {
        double $$1;
        double $$0;
        double $$2;
        if (this.f_188599_) {
            this.f_188599_ = false;
            return this.f_188598_;
        }
        do {
            $$0 = 2.0 * this.f_188597_.m_188500_() - 1.0;
            $$1 = 2.0 * this.f_188597_.m_188500_() - 1.0;
        } while (($$2 = Mth.m_144952_($$0) + Mth.m_144952_($$1)) >= 1.0 || $$2 == 0.0);
        double $$3 = Math.sqrt(-2.0 * Math.log($$2) / $$2);
        this.f_188598_ = $$1 * $$3;
        this.f_188599_ = true;
        return $$0 * $$3;
    }
}

