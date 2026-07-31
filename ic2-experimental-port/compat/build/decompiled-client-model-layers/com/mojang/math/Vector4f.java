/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.math;

import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;

public class Vector4f {
    private float f_123589_;
    private float f_123590_;
    private float f_123591_;
    private float f_123592_;

    public Vector4f() {
    }

    public Vector4f(float p_123595_, float p_123596_, float p_123597_, float p_123598_) {
        this.f_123589_ = p_123595_;
        this.f_123590_ = p_123596_;
        this.f_123591_ = p_123597_;
        this.f_123592_ = p_123598_;
    }

    public Vector4f(Vector3f p_123600_) {
        this(p_123600_.m_122239_(), p_123600_.m_122260_(), p_123600_.m_122269_(), 1.0f);
    }

    public boolean equals(Object p_123620_) {
        if (this == p_123620_) {
            return true;
        }
        if (p_123620_ == null || this.getClass() != p_123620_.getClass()) {
            return false;
        }
        Vector4f $$1 = (Vector4f)p_123620_;
        if (Float.compare($$1.f_123589_, this.f_123589_) != 0) {
            return false;
        }
        if (Float.compare($$1.f_123590_, this.f_123590_) != 0) {
            return false;
        }
        if (Float.compare($$1.f_123591_, this.f_123591_) != 0) {
            return false;
        }
        return Float.compare($$1.f_123592_, this.f_123592_) == 0;
    }

    public int hashCode() {
        int $$0 = Float.floatToIntBits(this.f_123589_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_123590_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_123591_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_123592_);
        return $$0;
    }

    public float m_123601_() {
        return this.f_123589_;
    }

    public float m_123615_() {
        return this.f_123590_;
    }

    public float m_123616_() {
        return this.f_123591_;
    }

    public float m_123617_() {
        return this.f_123592_;
    }

    public void m_176870_(float p_176871_) {
        this.f_123589_ *= p_176871_;
        this.f_123590_ *= p_176871_;
        this.f_123591_ *= p_176871_;
        this.f_123592_ *= p_176871_;
    }

    public void m_123611_(Vector3f p_123612_) {
        this.f_123589_ *= p_123612_.m_122239_();
        this.f_123590_ *= p_123612_.m_122260_();
        this.f_123591_ *= p_123612_.m_122269_();
    }

    public void m_123602_(float p_123603_, float p_123604_, float p_123605_, float p_123606_) {
        this.f_123589_ = p_123603_;
        this.f_123590_ = p_123604_;
        this.f_123591_ = p_123605_;
        this.f_123592_ = p_123606_;
    }

    public void m_176875_(float p_176876_, float p_176877_, float p_176878_, float p_176879_) {
        this.f_123589_ += p_176876_;
        this.f_123590_ += p_176877_;
        this.f_123591_ += p_176878_;
        this.f_123592_ += p_176879_;
    }

    public float m_123613_(Vector4f p_123614_) {
        return this.f_123589_ * p_123614_.f_123589_ + this.f_123590_ * p_123614_.f_123590_ + this.f_123591_ * p_123614_.f_123591_ + this.f_123592_ * p_123614_.f_123592_;
    }

    public boolean m_123618_() {
        float $$0 = this.f_123589_ * this.f_123589_ + this.f_123590_ * this.f_123590_ + this.f_123591_ * this.f_123591_ + this.f_123592_ * this.f_123592_;
        if ((double)$$0 < 1.0E-5) {
            return false;
        }
        float $$1 = Mth.m_14195_($$0);
        this.f_123589_ *= $$1;
        this.f_123590_ *= $$1;
        this.f_123591_ *= $$1;
        this.f_123592_ *= $$1;
        return true;
    }

    public void m_123607_(Matrix4f p_123608_) {
        float $$1 = this.f_123589_;
        float $$2 = this.f_123590_;
        float $$3 = this.f_123591_;
        float $$4 = this.f_123592_;
        this.f_123589_ = p_123608_.f_27603_ * $$1 + p_123608_.f_27604_ * $$2 + p_123608_.f_27605_ * $$3 + p_123608_.f_27606_ * $$4;
        this.f_123590_ = p_123608_.f_27607_ * $$1 + p_123608_.f_27608_ * $$2 + p_123608_.f_27609_ * $$3 + p_123608_.f_27610_ * $$4;
        this.f_123591_ = p_123608_.f_27611_ * $$1 + p_123608_.f_27612_ * $$2 + p_123608_.f_27613_ * $$3 + p_123608_.f_27614_ * $$4;
        this.f_123592_ = p_123608_.f_27615_ * $$1 + p_123608_.f_27616_ * $$2 + p_123608_.f_27617_ * $$3 + p_123608_.f_27618_ * $$4;
    }

    public void m_123609_(Quaternion p_123610_) {
        Quaternion $$1 = new Quaternion(p_123610_);
        $$1.m_80148_(new Quaternion(this.m_123601_(), this.m_123615_(), this.m_123616_(), 0.0f));
        Quaternion $$2 = new Quaternion(p_123610_);
        $$2.m_80157_();
        $$1.m_80148_($$2);
        this.m_123602_($$1.m_80140_(), $$1.m_80150_(), $$1.m_80153_(), this.m_123617_());
    }

    public void m_123621_() {
        this.f_123589_ /= this.f_123592_;
        this.f_123590_ /= this.f_123592_;
        this.f_123591_ /= this.f_123592_;
        this.f_123592_ = 1.0f;
    }

    public void m_176872_(Vector4f p_176873_, float p_176874_) {
        float $$2 = 1.0f - p_176874_;
        this.f_123589_ = this.f_123589_ * $$2 + p_176873_.f_123589_ * p_176874_;
        this.f_123590_ = this.f_123590_ * $$2 + p_176873_.f_123590_ * p_176874_;
        this.f_123591_ = this.f_123591_ * $$2 + p_176873_.f_123591_ * p_176874_;
        this.f_123592_ = this.f_123592_ * $$2 + p_176873_.f_123592_ * p_176874_;
    }

    public String toString() {
        return "[" + this.f_123589_ + ", " + this.f_123590_ + ", " + this.f_123591_ + ", " + this.f_123592_ + "]";
    }
}

