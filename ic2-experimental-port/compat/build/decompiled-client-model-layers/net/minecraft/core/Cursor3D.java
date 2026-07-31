/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core;

public class Cursor3D {
    public static final int f_175352_ = 0;
    public static final int f_175353_ = 1;
    public static final int f_175354_ = 2;
    public static final int f_175355_ = 3;
    private final int f_122286_;
    private final int f_122287_;
    private final int f_122288_;
    private final int f_122289_;
    private final int f_122290_;
    private final int f_122291_;
    private final int f_122292_;
    private int f_122293_;
    private int f_122294_;
    private int f_122295_;
    private int f_122296_;

    public Cursor3D(int p_122298_, int p_122299_, int p_122300_, int p_122301_, int p_122302_, int p_122303_) {
        this.f_122286_ = p_122298_;
        this.f_122287_ = p_122299_;
        this.f_122288_ = p_122300_;
        this.f_122289_ = p_122301_ - p_122298_ + 1;
        this.f_122290_ = p_122302_ - p_122299_ + 1;
        this.f_122291_ = p_122303_ - p_122300_ + 1;
        this.f_122292_ = this.f_122289_ * this.f_122290_ * this.f_122291_;
    }

    public boolean m_122304_() {
        if (this.f_122293_ == this.f_122292_) {
            return false;
        }
        this.f_122294_ = this.f_122293_ % this.f_122289_;
        int $$0 = this.f_122293_ / this.f_122289_;
        this.f_122295_ = $$0 % this.f_122290_;
        this.f_122296_ = $$0 / this.f_122290_;
        ++this.f_122293_;
        return true;
    }

    public int m_122305_() {
        return this.f_122286_ + this.f_122294_;
    }

    public int m_122306_() {
        return this.f_122287_ + this.f_122295_;
    }

    public int m_122307_() {
        return this.f_122288_ + this.f_122296_;
    }

    public int m_122308_() {
        int $$0 = 0;
        if (this.f_122294_ == 0 || this.f_122294_ == this.f_122289_ - 1) {
            ++$$0;
        }
        if (this.f_122295_ == 0 || this.f_122295_ == this.f_122290_ - 1) {
            ++$$0;
        }
        if (this.f_122296_ == 0 || this.f_122296_ == this.f_122291_ - 1) {
            ++$$0;
        }
        return $$0;
    }
}

