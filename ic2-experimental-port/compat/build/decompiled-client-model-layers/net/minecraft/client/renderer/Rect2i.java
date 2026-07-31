/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

public class Rect2i {
    private int f_110076_;
    private int f_110077_;
    private int f_110078_;
    private int f_110079_;

    public Rect2i(int p_110081_, int p_110082_, int p_110083_, int p_110084_) {
        this.f_110076_ = p_110081_;
        this.f_110077_ = p_110082_;
        this.f_110078_ = p_110083_;
        this.f_110079_ = p_110084_;
    }

    public Rect2i m_173052_(Rect2i p_173053_) {
        int $$1 = this.f_110076_;
        int $$2 = this.f_110077_;
        int $$3 = this.f_110076_ + this.f_110078_;
        int $$4 = this.f_110077_ + this.f_110079_;
        int $$5 = p_173053_.m_110085_();
        int $$6 = p_173053_.m_110086_();
        int $$7 = $$5 + p_173053_.m_110090_();
        int $$8 = $$6 + p_173053_.m_110091_();
        this.f_110076_ = Math.max($$1, $$5);
        this.f_110077_ = Math.max($$2, $$6);
        this.f_110078_ = Math.max(0, Math.min($$3, $$7) - this.f_110076_);
        this.f_110079_ = Math.max(0, Math.min($$4, $$8) - this.f_110077_);
        return this;
    }

    public int m_110085_() {
        return this.f_110076_;
    }

    public int m_110086_() {
        return this.f_110077_;
    }

    public void m_173047_(int p_173048_) {
        this.f_110076_ = p_173048_;
    }

    public void m_173054_(int p_173055_) {
        this.f_110077_ = p_173055_;
    }

    public int m_110090_() {
        return this.f_110078_;
    }

    public int m_110091_() {
        return this.f_110079_;
    }

    public void m_173056_(int p_173057_) {
        this.f_110078_ = p_173057_;
    }

    public void m_173058_(int p_173059_) {
        this.f_110079_ = p_173059_;
    }

    public void m_173049_(int p_173050_, int p_173051_) {
        this.f_110076_ = p_173050_;
        this.f_110077_ = p_173051_;
    }

    public boolean m_110087_(int p_110088_, int p_110089_) {
        return p_110088_ >= this.f_110076_ && p_110088_ <= this.f_110076_ + this.f_110078_ && p_110089_ >= this.f_110077_ && p_110089_ <= this.f_110077_ + this.f_110079_;
    }
}

