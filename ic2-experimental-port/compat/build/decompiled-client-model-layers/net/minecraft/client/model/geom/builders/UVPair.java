/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.geom.builders;

public class UVPair {
    private final float f_171607_;
    private final float f_171608_;

    public UVPair(float p_171610_, float p_171611_) {
        this.f_171607_ = p_171610_;
        this.f_171608_ = p_171611_;
    }

    public float m_171612_() {
        return this.f_171607_;
    }

    public float m_171613_() {
        return this.f_171608_;
    }

    public String toString() {
        return "(" + this.f_171607_ + "," + this.f_171608_ + ")";
    }
}

