/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

public class Timer {
    public float f_92518_;
    public float f_92519_;
    private long f_92520_;
    private final float f_92521_;

    public Timer(float p_92523_, long p_92524_) {
        this.f_92521_ = 1000.0f / p_92523_;
        this.f_92520_ = p_92524_;
    }

    public int m_92525_(long p_92526_) {
        this.f_92519_ = (float)(p_92526_ - this.f_92520_) / this.f_92521_;
        this.f_92520_ = p_92526_;
        this.f_92518_ += this.f_92519_;
        int $$1 = (int)this.f_92518_;
        this.f_92518_ -= (float)$$1;
        return $$1;
    }
}

