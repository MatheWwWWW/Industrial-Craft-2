/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient;

import java.util.Arrays;

public class KeyCombo {
    private final char[] f_86221_;
    private int f_86222_;
    private final Runnable f_86223_;

    public KeyCombo(char[] p_86225_, Runnable p_86226_) {
        this.f_86223_ = p_86226_;
        if (p_86225_.length < 1) {
            throw new IllegalArgumentException("Must have at least one char");
        }
        this.f_86221_ = p_86225_;
    }

    public KeyCombo(char[] p_167171_) {
        this(p_167171_, () -> {});
    }

    public boolean m_86228_(char p_86229_) {
        if (p_86229_ == this.f_86221_[this.f_86222_++]) {
            if (this.f_86222_ == this.f_86221_.length) {
                this.m_86227_();
                this.f_86223_.run();
                return true;
            }
        } else {
            this.m_86227_();
        }
        return false;
    }

    public void m_86227_() {
        this.f_86222_ = 0;
    }

    public String toString() {
        return "KeyCombo{chars=" + Arrays.toString(this.f_86221_) + ", matchIndex=" + this.f_86222_ + "}";
    }
}

