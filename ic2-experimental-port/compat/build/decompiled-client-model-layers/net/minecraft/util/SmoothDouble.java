/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import net.minecraft.util.Mth;

public class SmoothDouble {
    private double f_14232_;
    private double f_14233_;
    private double f_14234_;

    public double m_14237_(double p_14238_, double p_14239_) {
        this.f_14232_ += p_14238_;
        double $$2 = this.f_14232_ - this.f_14233_;
        double $$3 = Mth.m_14139_(0.5, this.f_14234_, $$2);
        double $$4 = Math.signum($$2);
        if ($$4 * $$2 > $$4 * this.f_14234_) {
            $$2 = $$3;
        }
        this.f_14234_ = $$3;
        this.f_14233_ += $$2 * p_14239_;
        return $$2 * p_14239_;
    }

    public void m_14236_() {
        this.f_14232_ = 0.0;
        this.f_14233_ = 0.0;
        this.f_14234_ = 0.0;
    }
}

