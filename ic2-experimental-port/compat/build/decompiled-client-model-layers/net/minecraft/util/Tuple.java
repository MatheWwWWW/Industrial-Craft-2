/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class Tuple<A, B> {
    private A f_14413_;
    private B f_14414_;

    public Tuple(A p_14416_, B p_14417_) {
        this.f_14413_ = p_14416_;
        this.f_14414_ = p_14417_;
    }

    public A m_14418_() {
        return this.f_14413_;
    }

    public void m_145023_(A p_145024_) {
        this.f_14413_ = p_145024_;
    }

    public B m_14419_() {
        return this.f_14414_;
    }

    public void m_145025_(B p_145026_) {
        this.f_14414_ = p_145026_;
    }
}

