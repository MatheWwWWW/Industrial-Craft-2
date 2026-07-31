/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.math;

public class Vector3d {
    public double f_86214_;
    public double f_86215_;
    public double f_86216_;

    public Vector3d(double p_86218_, double p_86219_, double p_86220_) {
        this.f_86214_ = p_86218_;
        this.f_86215_ = p_86219_;
        this.f_86216_ = p_86220_;
    }

    public void m_176289_(Vector3d p_176290_) {
        this.f_86214_ = p_176290_.f_86214_;
        this.f_86215_ = p_176290_.f_86215_;
        this.f_86216_ = p_176290_.f_86216_;
    }

    public void m_176285_(double p_176286_, double p_176287_, double p_176288_) {
        this.f_86214_ = p_176286_;
        this.f_86215_ = p_176287_;
        this.f_86216_ = p_176288_;
    }

    public void m_176283_(double p_176284_) {
        this.f_86214_ *= p_176284_;
        this.f_86215_ *= p_176284_;
        this.f_86216_ *= p_176284_;
    }

    public void m_176291_(Vector3d p_176292_) {
        this.f_86214_ += p_176292_.f_86214_;
        this.f_86215_ += p_176292_.f_86215_;
        this.f_86216_ += p_176292_.f_86216_;
    }
}

