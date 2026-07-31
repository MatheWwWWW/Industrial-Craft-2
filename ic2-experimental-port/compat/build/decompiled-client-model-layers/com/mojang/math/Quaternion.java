/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.math;

import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;

public final class Quaternion {
    public static final Quaternion f_80118_ = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private float f_80119_;
    private float f_80120_;
    private float f_80121_;
    private float f_80122_;

    public Quaternion(float p_80125_, float p_80126_, float p_80127_, float p_80128_) {
        this.f_80119_ = p_80125_;
        this.f_80120_ = p_80126_;
        this.f_80121_ = p_80127_;
        this.f_80122_ = p_80128_;
    }

    public Quaternion(Vector3f p_80137_, float p_80138_, boolean p_80139_) {
        if (p_80139_) {
            p_80138_ *= (float)Math.PI / 180;
        }
        float $$3 = Quaternion.m_80154_(p_80138_ / 2.0f);
        this.f_80119_ = p_80137_.m_122239_() * $$3;
        this.f_80120_ = p_80137_.m_122260_() * $$3;
        this.f_80121_ = p_80137_.m_122269_() * $$3;
        this.f_80122_ = Quaternion.m_80151_(p_80138_ / 2.0f);
    }

    public Quaternion(float p_80130_, float p_80131_, float p_80132_, boolean p_80133_) {
        if (p_80133_) {
            p_80130_ *= (float)Math.PI / 180;
            p_80131_ *= (float)Math.PI / 180;
            p_80132_ *= (float)Math.PI / 180;
        }
        float $$4 = Quaternion.m_80154_(0.5f * p_80130_);
        float $$5 = Quaternion.m_80151_(0.5f * p_80130_);
        float $$6 = Quaternion.m_80154_(0.5f * p_80131_);
        float $$7 = Quaternion.m_80151_(0.5f * p_80131_);
        float $$8 = Quaternion.m_80154_(0.5f * p_80132_);
        float $$9 = Quaternion.m_80151_(0.5f * p_80132_);
        this.f_80119_ = $$4 * $$7 * $$9 + $$5 * $$6 * $$8;
        this.f_80120_ = $$5 * $$6 * $$9 - $$4 * $$7 * $$8;
        this.f_80121_ = $$4 * $$6 * $$9 + $$5 * $$7 * $$8;
        this.f_80122_ = $$5 * $$7 * $$9 - $$4 * $$6 * $$8;
    }

    public Quaternion(Quaternion p_80135_) {
        this.f_80119_ = p_80135_.f_80119_;
        this.f_80120_ = p_80135_.f_80120_;
        this.f_80121_ = p_80135_.f_80121_;
        this.f_80122_ = p_80135_.f_80122_;
    }

    public static Quaternion m_175218_(float p_175219_, float p_175220_, float p_175221_) {
        Quaternion $$3 = f_80118_.m_80161_();
        $$3.m_80148_(new Quaternion(0.0f, (float)Math.sin(p_175219_ / 2.0f), 0.0f, (float)Math.cos(p_175219_ / 2.0f)));
        $$3.m_80148_(new Quaternion((float)Math.sin(p_175220_ / 2.0f), 0.0f, 0.0f, (float)Math.cos(p_175220_ / 2.0f)));
        $$3.m_80148_(new Quaternion(0.0f, 0.0f, (float)Math.sin(p_175221_ / 2.0f), (float)Math.cos(p_175221_ / 2.0f)));
        return $$3;
    }

    public static Quaternion m_175225_(Vector3f p_175226_) {
        return Quaternion.m_175228_((float)Math.toRadians(p_175226_.m_122239_()), (float)Math.toRadians(p_175226_.m_122260_()), (float)Math.toRadians(p_175226_.m_122269_()));
    }

    public static Quaternion m_175232_(Vector3f p_175233_) {
        return Quaternion.m_175228_(p_175233_.m_122239_(), p_175233_.m_122260_(), p_175233_.m_122269_());
    }

    public static Quaternion m_175228_(float p_175229_, float p_175230_, float p_175231_) {
        Quaternion $$3 = f_80118_.m_80161_();
        $$3.m_80148_(new Quaternion((float)Math.sin(p_175229_ / 2.0f), 0.0f, 0.0f, (float)Math.cos(p_175229_ / 2.0f)));
        $$3.m_80148_(new Quaternion(0.0f, (float)Math.sin(p_175230_ / 2.0f), 0.0f, (float)Math.cos(p_175230_ / 2.0f)));
        $$3.m_80148_(new Quaternion(0.0f, 0.0f, (float)Math.sin(p_175231_ / 2.0f), (float)Math.cos(p_175231_ / 2.0f)));
        return $$3;
    }

    public Vector3f m_175217_() {
        float $$0 = this.m_80156_() * this.m_80156_();
        float $$1 = this.m_80140_() * this.m_80140_();
        float $$2 = this.m_80150_() * this.m_80150_();
        float $$3 = this.m_80153_() * this.m_80153_();
        float $$4 = $$0 + $$1 + $$2 + $$3;
        float $$5 = 2.0f * this.m_80156_() * this.m_80140_() - 2.0f * this.m_80150_() * this.m_80153_();
        float $$6 = (float)Math.asin($$5 / $$4);
        if (Math.abs($$5) > 0.999f * $$4) {
            return new Vector3f(2.0f * (float)Math.atan2(this.m_80140_(), this.m_80156_()), $$6, 0.0f);
        }
        return new Vector3f((float)Math.atan2(2.0f * this.m_80150_() * this.m_80153_() + 2.0f * this.m_80140_() * this.m_80156_(), $$0 - $$1 - $$2 + $$3), $$6, (float)Math.atan2(2.0f * this.m_80140_() * this.m_80150_() + 2.0f * this.m_80156_() * this.m_80153_(), $$0 + $$1 - $$2 - $$3));
    }

    public Vector3f m_175227_() {
        Vector3f $$0 = this.m_175217_();
        return new Vector3f((float)Math.toDegrees($$0.m_122239_()), (float)Math.toDegrees($$0.m_122260_()), (float)Math.toDegrees($$0.m_122269_()));
    }

    public Vector3f m_175234_() {
        float $$0 = this.m_80156_() * this.m_80156_();
        float $$1 = this.m_80140_() * this.m_80140_();
        float $$2 = this.m_80150_() * this.m_80150_();
        float $$3 = this.m_80153_() * this.m_80153_();
        float $$4 = $$0 + $$1 + $$2 + $$3;
        float $$5 = 2.0f * this.m_80156_() * this.m_80140_() - 2.0f * this.m_80150_() * this.m_80153_();
        float $$6 = (float)Math.asin($$5 / $$4);
        if (Math.abs($$5) > 0.999f * $$4) {
            return new Vector3f($$6, 2.0f * (float)Math.atan2(this.m_80150_(), this.m_80156_()), 0.0f);
        }
        return new Vector3f($$6, (float)Math.atan2(2.0f * this.m_80140_() * this.m_80153_() + 2.0f * this.m_80150_() * this.m_80156_(), $$0 - $$1 - $$2 + $$3), (float)Math.atan2(2.0f * this.m_80140_() * this.m_80150_() + 2.0f * this.m_80156_() * this.m_80153_(), $$0 - $$1 + $$2 - $$3));
    }

    public Vector3f m_175235_() {
        Vector3f $$0 = this.m_175234_();
        return new Vector3f((float)Math.toDegrees($$0.m_122239_()), (float)Math.toDegrees($$0.m_122260_()), (float)Math.toDegrees($$0.m_122269_()));
    }

    public boolean equals(Object p_80159_) {
        if (this == p_80159_) {
            return true;
        }
        if (p_80159_ == null || this.getClass() != p_80159_.getClass()) {
            return false;
        }
        Quaternion $$1 = (Quaternion)p_80159_;
        if (Float.compare($$1.f_80119_, this.f_80119_) != 0) {
            return false;
        }
        if (Float.compare($$1.f_80120_, this.f_80120_) != 0) {
            return false;
        }
        if (Float.compare($$1.f_80121_, this.f_80121_) != 0) {
            return false;
        }
        return Float.compare($$1.f_80122_, this.f_80122_) == 0;
    }

    public int hashCode() {
        int $$0 = Float.floatToIntBits(this.f_80119_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_80120_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_80121_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_80122_);
        return $$0;
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append("Quaternion[").append(this.m_80156_()).append(" + ");
        $$0.append(this.m_80140_()).append("i + ");
        $$0.append(this.m_80150_()).append("j + ");
        $$0.append(this.m_80153_()).append("k]");
        return $$0.toString();
    }

    public float m_80140_() {
        return this.f_80119_;
    }

    public float m_80150_() {
        return this.f_80120_;
    }

    public float m_80153_() {
        return this.f_80121_;
    }

    public float m_80156_() {
        return this.f_80122_;
    }

    public void m_80148_(Quaternion p_80149_) {
        float $$1 = this.m_80140_();
        float $$2 = this.m_80150_();
        float $$3 = this.m_80153_();
        float $$4 = this.m_80156_();
        float $$5 = p_80149_.m_80140_();
        float $$6 = p_80149_.m_80150_();
        float $$7 = p_80149_.m_80153_();
        float $$8 = p_80149_.m_80156_();
        this.f_80119_ = $$4 * $$5 + $$1 * $$8 + $$2 * $$7 - $$3 * $$6;
        this.f_80120_ = $$4 * $$6 - $$1 * $$7 + $$2 * $$8 + $$3 * $$5;
        this.f_80121_ = $$4 * $$7 + $$1 * $$6 - $$2 * $$5 + $$3 * $$8;
        this.f_80122_ = $$4 * $$8 - $$1 * $$5 - $$2 * $$6 - $$3 * $$7;
    }

    public void m_80141_(float p_80142_) {
        this.f_80119_ *= p_80142_;
        this.f_80120_ *= p_80142_;
        this.f_80121_ *= p_80142_;
        this.f_80122_ *= p_80142_;
    }

    public void m_80157_() {
        this.f_80119_ = -this.f_80119_;
        this.f_80120_ = -this.f_80120_;
        this.f_80121_ = -this.f_80121_;
    }

    public void m_80143_(float p_80144_, float p_80145_, float p_80146_, float p_80147_) {
        this.f_80119_ = p_80144_;
        this.f_80120_ = p_80145_;
        this.f_80121_ = p_80146_;
        this.f_80122_ = p_80147_;
    }

    private static float m_80151_(float p_80152_) {
        return (float)Math.cos(p_80152_);
    }

    private static float m_80154_(float p_80155_) {
        return (float)Math.sin(p_80155_);
    }

    public void m_80160_() {
        float $$0 = this.m_80140_() * this.m_80140_() + this.m_80150_() * this.m_80150_() + this.m_80153_() * this.m_80153_() + this.m_80156_() * this.m_80156_();
        if ($$0 > 1.0E-6f) {
            float $$1 = Mth.m_14195_($$0);
            this.f_80119_ *= $$1;
            this.f_80120_ *= $$1;
            this.f_80121_ *= $$1;
            this.f_80122_ *= $$1;
        } else {
            this.f_80119_ = 0.0f;
            this.f_80120_ = 0.0f;
            this.f_80121_ = 0.0f;
            this.f_80122_ = 0.0f;
        }
    }

    public void m_175222_(Quaternion p_175223_, float p_175224_) {
        throw new UnsupportedOperationException();
    }

    public Quaternion m_80161_() {
        return new Quaternion(this);
    }
}

