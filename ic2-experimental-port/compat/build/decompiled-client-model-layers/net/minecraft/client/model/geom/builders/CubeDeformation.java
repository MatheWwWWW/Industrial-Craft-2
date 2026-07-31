/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.geom.builders;

public class CubeDeformation {
    public static final CubeDeformation f_171458_ = new CubeDeformation(0.0f);
    final float f_171459_;
    final float f_171460_;
    final float f_171461_;

    public CubeDeformation(float p_171466_, float p_171467_, float p_171468_) {
        this.f_171459_ = p_171466_;
        this.f_171460_ = p_171467_;
        this.f_171461_ = p_171468_;
    }

    public CubeDeformation(float p_171464_) {
        this(p_171464_, p_171464_, p_171464_);
    }

    public CubeDeformation m_171469_(float p_171470_) {
        return new CubeDeformation(this.f_171459_ + p_171470_, this.f_171460_ + p_171470_, this.f_171461_ + p_171470_);
    }

    public CubeDeformation m_171471_(float p_171472_, float p_171473_, float p_171474_) {
        return new CubeDeformation(this.f_171459_ + p_171472_, this.f_171460_ + p_171473_, this.f_171461_ + p_171474_);
    }
}

