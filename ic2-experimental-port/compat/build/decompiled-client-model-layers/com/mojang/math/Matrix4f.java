/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.math;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.nio.FloatBuffer;

public final class Matrix4f {
    private static final int f_162197_ = 4;
    protected float f_27603_;
    protected float f_27604_;
    protected float f_27605_;
    protected float f_27606_;
    protected float f_27607_;
    protected float f_27608_;
    protected float f_27609_;
    protected float f_27610_;
    protected float f_27611_;
    protected float f_27612_;
    protected float f_27613_;
    protected float f_27614_;
    protected float f_27615_;
    protected float f_27616_;
    protected float f_27617_;
    protected float f_27618_;

    public Matrix4f() {
    }

    public Matrix4f(Matrix4f p_27621_) {
        this.f_27603_ = p_27621_.f_27603_;
        this.f_27604_ = p_27621_.f_27604_;
        this.f_27605_ = p_27621_.f_27605_;
        this.f_27606_ = p_27621_.f_27606_;
        this.f_27607_ = p_27621_.f_27607_;
        this.f_27608_ = p_27621_.f_27608_;
        this.f_27609_ = p_27621_.f_27609_;
        this.f_27610_ = p_27621_.f_27610_;
        this.f_27611_ = p_27621_.f_27611_;
        this.f_27612_ = p_27621_.f_27612_;
        this.f_27613_ = p_27621_.f_27613_;
        this.f_27614_ = p_27621_.f_27614_;
        this.f_27615_ = p_27621_.f_27615_;
        this.f_27616_ = p_27621_.f_27616_;
        this.f_27617_ = p_27621_.f_27617_;
        this.f_27618_ = p_27621_.f_27618_;
    }

    public Matrix4f(Quaternion p_27623_) {
        float $$1 = p_27623_.m_80140_();
        float $$2 = p_27623_.m_80150_();
        float $$3 = p_27623_.m_80153_();
        float $$4 = p_27623_.m_80156_();
        float $$5 = 2.0f * $$1 * $$1;
        float $$6 = 2.0f * $$2 * $$2;
        float $$7 = 2.0f * $$3 * $$3;
        this.f_27603_ = 1.0f - $$6 - $$7;
        this.f_27608_ = 1.0f - $$7 - $$5;
        this.f_27613_ = 1.0f - $$5 - $$6;
        this.f_27618_ = 1.0f;
        float $$8 = $$1 * $$2;
        float $$9 = $$2 * $$3;
        float $$10 = $$3 * $$1;
        float $$11 = $$1 * $$4;
        float $$12 = $$2 * $$4;
        float $$13 = $$3 * $$4;
        this.f_27607_ = 2.0f * ($$8 + $$13);
        this.f_27604_ = 2.0f * ($$8 - $$13);
        this.f_27611_ = 2.0f * ($$10 - $$12);
        this.f_27605_ = 2.0f * ($$10 + $$12);
        this.f_27612_ = 2.0f * ($$9 + $$11);
        this.f_27609_ = 2.0f * ($$9 - $$11);
    }

    public boolean m_162198_() {
        Matrix4f $$0 = new Matrix4f();
        $$0.f_27615_ = 1.0f;
        $$0.f_27616_ = 1.0f;
        $$0.f_27617_ = 1.0f;
        $$0.f_27618_ = 0.0f;
        Matrix4f $$1 = this.m_27658_();
        $$1.m_27644_($$0);
        return Matrix4f.m_162217_($$1.f_27603_ / $$1.f_27606_) && Matrix4f.m_162217_($$1.f_27607_ / $$1.f_27610_) && Matrix4f.m_162217_($$1.f_27611_ / $$1.f_27614_) && Matrix4f.m_162217_($$1.f_27604_ / $$1.f_27606_) && Matrix4f.m_162217_($$1.f_27608_ / $$1.f_27610_) && Matrix4f.m_162217_($$1.f_27612_ / $$1.f_27614_) && Matrix4f.m_162217_($$1.f_27605_ / $$1.f_27606_) && Matrix4f.m_162217_($$1.f_27609_ / $$1.f_27610_) && Matrix4f.m_162217_($$1.f_27613_ / $$1.f_27614_);
    }

    private static boolean m_162217_(float p_162218_) {
        return (double)Math.abs(p_162218_ - (float)Math.round(p_162218_)) <= 1.0E-5;
    }

    public boolean equals(Object p_27661_) {
        if (this == p_27661_) {
            return true;
        }
        if (p_27661_ == null || this.getClass() != p_27661_.getClass()) {
            return false;
        }
        Matrix4f $$1 = (Matrix4f)p_27661_;
        return Float.compare($$1.f_27603_, this.f_27603_) == 0 && Float.compare($$1.f_27604_, this.f_27604_) == 0 && Float.compare($$1.f_27605_, this.f_27605_) == 0 && Float.compare($$1.f_27606_, this.f_27606_) == 0 && Float.compare($$1.f_27607_, this.f_27607_) == 0 && Float.compare($$1.f_27608_, this.f_27608_) == 0 && Float.compare($$1.f_27609_, this.f_27609_) == 0 && Float.compare($$1.f_27610_, this.f_27610_) == 0 && Float.compare($$1.f_27611_, this.f_27611_) == 0 && Float.compare($$1.f_27612_, this.f_27612_) == 0 && Float.compare($$1.f_27613_, this.f_27613_) == 0 && Float.compare($$1.f_27614_, this.f_27614_) == 0 && Float.compare($$1.f_27615_, this.f_27615_) == 0 && Float.compare($$1.f_27616_, this.f_27616_) == 0 && Float.compare($$1.f_27617_, this.f_27617_) == 0 && Float.compare($$1.f_27618_, this.f_27618_) == 0;
    }

    public int hashCode() {
        int $$0 = this.f_27603_ != 0.0f ? Float.floatToIntBits(this.f_27603_) : 0;
        $$0 = 31 * $$0 + (this.f_27604_ != 0.0f ? Float.floatToIntBits(this.f_27604_) : 0);
        $$0 = 31 * $$0 + (this.f_27605_ != 0.0f ? Float.floatToIntBits(this.f_27605_) : 0);
        $$0 = 31 * $$0 + (this.f_27606_ != 0.0f ? Float.floatToIntBits(this.f_27606_) : 0);
        $$0 = 31 * $$0 + (this.f_27607_ != 0.0f ? Float.floatToIntBits(this.f_27607_) : 0);
        $$0 = 31 * $$0 + (this.f_27608_ != 0.0f ? Float.floatToIntBits(this.f_27608_) : 0);
        $$0 = 31 * $$0 + (this.f_27609_ != 0.0f ? Float.floatToIntBits(this.f_27609_) : 0);
        $$0 = 31 * $$0 + (this.f_27610_ != 0.0f ? Float.floatToIntBits(this.f_27610_) : 0);
        $$0 = 31 * $$0 + (this.f_27611_ != 0.0f ? Float.floatToIntBits(this.f_27611_) : 0);
        $$0 = 31 * $$0 + (this.f_27612_ != 0.0f ? Float.floatToIntBits(this.f_27612_) : 0);
        $$0 = 31 * $$0 + (this.f_27613_ != 0.0f ? Float.floatToIntBits(this.f_27613_) : 0);
        $$0 = 31 * $$0 + (this.f_27614_ != 0.0f ? Float.floatToIntBits(this.f_27614_) : 0);
        $$0 = 31 * $$0 + (this.f_27615_ != 0.0f ? Float.floatToIntBits(this.f_27615_) : 0);
        $$0 = 31 * $$0 + (this.f_27616_ != 0.0f ? Float.floatToIntBits(this.f_27616_) : 0);
        $$0 = 31 * $$0 + (this.f_27617_ != 0.0f ? Float.floatToIntBits(this.f_27617_) : 0);
        $$0 = 31 * $$0 + (this.f_27618_ != 0.0f ? Float.floatToIntBits(this.f_27618_) : 0);
        return $$0;
    }

    private static int m_27641_(int p_27642_, int p_27643_) {
        return p_27643_ * 4 + p_27642_;
    }

    public void m_162212_(FloatBuffer p_162213_) {
        this.f_27603_ = p_162213_.get(Matrix4f.m_27641_(0, 0));
        this.f_27604_ = p_162213_.get(Matrix4f.m_27641_(0, 1));
        this.f_27605_ = p_162213_.get(Matrix4f.m_27641_(0, 2));
        this.f_27606_ = p_162213_.get(Matrix4f.m_27641_(0, 3));
        this.f_27607_ = p_162213_.get(Matrix4f.m_27641_(1, 0));
        this.f_27608_ = p_162213_.get(Matrix4f.m_27641_(1, 1));
        this.f_27609_ = p_162213_.get(Matrix4f.m_27641_(1, 2));
        this.f_27610_ = p_162213_.get(Matrix4f.m_27641_(1, 3));
        this.f_27611_ = p_162213_.get(Matrix4f.m_27641_(2, 0));
        this.f_27612_ = p_162213_.get(Matrix4f.m_27641_(2, 1));
        this.f_27613_ = p_162213_.get(Matrix4f.m_27641_(2, 2));
        this.f_27614_ = p_162213_.get(Matrix4f.m_27641_(2, 3));
        this.f_27615_ = p_162213_.get(Matrix4f.m_27641_(3, 0));
        this.f_27616_ = p_162213_.get(Matrix4f.m_27641_(3, 1));
        this.f_27617_ = p_162213_.get(Matrix4f.m_27641_(3, 2));
        this.f_27618_ = p_162213_.get(Matrix4f.m_27641_(3, 3));
    }

    public void m_162219_(FloatBuffer p_162220_) {
        this.f_27603_ = p_162220_.get(Matrix4f.m_27641_(0, 0));
        this.f_27604_ = p_162220_.get(Matrix4f.m_27641_(1, 0));
        this.f_27605_ = p_162220_.get(Matrix4f.m_27641_(2, 0));
        this.f_27606_ = p_162220_.get(Matrix4f.m_27641_(3, 0));
        this.f_27607_ = p_162220_.get(Matrix4f.m_27641_(0, 1));
        this.f_27608_ = p_162220_.get(Matrix4f.m_27641_(1, 1));
        this.f_27609_ = p_162220_.get(Matrix4f.m_27641_(2, 1));
        this.f_27610_ = p_162220_.get(Matrix4f.m_27641_(3, 1));
        this.f_27611_ = p_162220_.get(Matrix4f.m_27641_(0, 2));
        this.f_27612_ = p_162220_.get(Matrix4f.m_27641_(1, 2));
        this.f_27613_ = p_162220_.get(Matrix4f.m_27641_(2, 2));
        this.f_27614_ = p_162220_.get(Matrix4f.m_27641_(3, 2));
        this.f_27615_ = p_162220_.get(Matrix4f.m_27641_(0, 3));
        this.f_27616_ = p_162220_.get(Matrix4f.m_27641_(1, 3));
        this.f_27617_ = p_162220_.get(Matrix4f.m_27641_(2, 3));
        this.f_27618_ = p_162220_.get(Matrix4f.m_27641_(3, 3));
    }

    public void m_162214_(FloatBuffer p_162215_, boolean p_162216_) {
        if (p_162216_) {
            this.m_162219_(p_162215_);
        } else {
            this.m_162212_(p_162215_);
        }
    }

    public void m_162210_(Matrix4f p_162211_) {
        this.f_27603_ = p_162211_.f_27603_;
        this.f_27604_ = p_162211_.f_27604_;
        this.f_27605_ = p_162211_.f_27605_;
        this.f_27606_ = p_162211_.f_27606_;
        this.f_27607_ = p_162211_.f_27607_;
        this.f_27608_ = p_162211_.f_27608_;
        this.f_27609_ = p_162211_.f_27609_;
        this.f_27610_ = p_162211_.f_27610_;
        this.f_27611_ = p_162211_.f_27611_;
        this.f_27612_ = p_162211_.f_27612_;
        this.f_27613_ = p_162211_.f_27613_;
        this.f_27614_ = p_162211_.f_27614_;
        this.f_27615_ = p_162211_.f_27615_;
        this.f_27616_ = p_162211_.f_27616_;
        this.f_27617_ = p_162211_.f_27617_;
        this.f_27618_ = p_162211_.f_27618_;
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append("Matrix4f:\n");
        $$0.append(this.f_27603_);
        $$0.append(" ");
        $$0.append(this.f_27604_);
        $$0.append(" ");
        $$0.append(this.f_27605_);
        $$0.append(" ");
        $$0.append(this.f_27606_);
        $$0.append("\n");
        $$0.append(this.f_27607_);
        $$0.append(" ");
        $$0.append(this.f_27608_);
        $$0.append(" ");
        $$0.append(this.f_27609_);
        $$0.append(" ");
        $$0.append(this.f_27610_);
        $$0.append("\n");
        $$0.append(this.f_27611_);
        $$0.append(" ");
        $$0.append(this.f_27612_);
        $$0.append(" ");
        $$0.append(this.f_27613_);
        $$0.append(" ");
        $$0.append(this.f_27614_);
        $$0.append("\n");
        $$0.append(this.f_27615_);
        $$0.append(" ");
        $$0.append(this.f_27616_);
        $$0.append(" ");
        $$0.append(this.f_27617_);
        $$0.append(" ");
        $$0.append(this.f_27618_);
        $$0.append("\n");
        return $$0.toString();
    }

    public void m_27650_(FloatBuffer p_27651_) {
        p_27651_.put(Matrix4f.m_27641_(0, 0), this.f_27603_);
        p_27651_.put(Matrix4f.m_27641_(0, 1), this.f_27604_);
        p_27651_.put(Matrix4f.m_27641_(0, 2), this.f_27605_);
        p_27651_.put(Matrix4f.m_27641_(0, 3), this.f_27606_);
        p_27651_.put(Matrix4f.m_27641_(1, 0), this.f_27607_);
        p_27651_.put(Matrix4f.m_27641_(1, 1), this.f_27608_);
        p_27651_.put(Matrix4f.m_27641_(1, 2), this.f_27609_);
        p_27651_.put(Matrix4f.m_27641_(1, 3), this.f_27610_);
        p_27651_.put(Matrix4f.m_27641_(2, 0), this.f_27611_);
        p_27651_.put(Matrix4f.m_27641_(2, 1), this.f_27612_);
        p_27651_.put(Matrix4f.m_27641_(2, 2), this.f_27613_);
        p_27651_.put(Matrix4f.m_27641_(2, 3), this.f_27614_);
        p_27651_.put(Matrix4f.m_27641_(3, 0), this.f_27615_);
        p_27651_.put(Matrix4f.m_27641_(3, 1), this.f_27616_);
        p_27651_.put(Matrix4f.m_27641_(3, 2), this.f_27617_);
        p_27651_.put(Matrix4f.m_27641_(3, 3), this.f_27618_);
    }

    public void m_162229_(FloatBuffer p_162230_) {
        p_162230_.put(Matrix4f.m_27641_(0, 0), this.f_27603_);
        p_162230_.put(Matrix4f.m_27641_(1, 0), this.f_27604_);
        p_162230_.put(Matrix4f.m_27641_(2, 0), this.f_27605_);
        p_162230_.put(Matrix4f.m_27641_(3, 0), this.f_27606_);
        p_162230_.put(Matrix4f.m_27641_(0, 1), this.f_27607_);
        p_162230_.put(Matrix4f.m_27641_(1, 1), this.f_27608_);
        p_162230_.put(Matrix4f.m_27641_(2, 1), this.f_27609_);
        p_162230_.put(Matrix4f.m_27641_(3, 1), this.f_27610_);
        p_162230_.put(Matrix4f.m_27641_(0, 2), this.f_27611_);
        p_162230_.put(Matrix4f.m_27641_(1, 2), this.f_27612_);
        p_162230_.put(Matrix4f.m_27641_(2, 2), this.f_27613_);
        p_162230_.put(Matrix4f.m_27641_(3, 2), this.f_27614_);
        p_162230_.put(Matrix4f.m_27641_(0, 3), this.f_27615_);
        p_162230_.put(Matrix4f.m_27641_(1, 3), this.f_27616_);
        p_162230_.put(Matrix4f.m_27641_(2, 3), this.f_27617_);
        p_162230_.put(Matrix4f.m_27641_(3, 3), this.f_27618_);
    }

    public void m_162221_(FloatBuffer p_162222_, boolean p_162223_) {
        if (p_162223_) {
            this.m_162229_(p_162222_);
        } else {
            this.m_27650_(p_162222_);
        }
    }

    public void m_27624_() {
        this.f_27603_ = 1.0f;
        this.f_27604_ = 0.0f;
        this.f_27605_ = 0.0f;
        this.f_27606_ = 0.0f;
        this.f_27607_ = 0.0f;
        this.f_27608_ = 1.0f;
        this.f_27609_ = 0.0f;
        this.f_27610_ = 0.0f;
        this.f_27611_ = 0.0f;
        this.f_27612_ = 0.0f;
        this.f_27613_ = 1.0f;
        this.f_27614_ = 0.0f;
        this.f_27615_ = 0.0f;
        this.f_27616_ = 0.0f;
        this.f_27617_ = 0.0f;
        this.f_27618_ = 1.0f;
    }

    public float m_27652_() {
        float $$0 = this.f_27603_ * this.f_27608_ - this.f_27604_ * this.f_27607_;
        float $$1 = this.f_27603_ * this.f_27609_ - this.f_27605_ * this.f_27607_;
        float $$2 = this.f_27603_ * this.f_27610_ - this.f_27606_ * this.f_27607_;
        float $$3 = this.f_27604_ * this.f_27609_ - this.f_27605_ * this.f_27608_;
        float $$4 = this.f_27604_ * this.f_27610_ - this.f_27606_ * this.f_27608_;
        float $$5 = this.f_27605_ * this.f_27610_ - this.f_27606_ * this.f_27609_;
        float $$6 = this.f_27611_ * this.f_27616_ - this.f_27612_ * this.f_27615_;
        float $$7 = this.f_27611_ * this.f_27617_ - this.f_27613_ * this.f_27615_;
        float $$8 = this.f_27611_ * this.f_27618_ - this.f_27614_ * this.f_27615_;
        float $$9 = this.f_27612_ * this.f_27617_ - this.f_27613_ * this.f_27616_;
        float $$10 = this.f_27612_ * this.f_27618_ - this.f_27614_ * this.f_27616_;
        float $$11 = this.f_27613_ * this.f_27618_ - this.f_27614_ * this.f_27617_;
        float $$12 = this.f_27608_ * $$11 - this.f_27609_ * $$10 + this.f_27610_ * $$9;
        float $$13 = -this.f_27607_ * $$11 + this.f_27609_ * $$8 - this.f_27610_ * $$7;
        float $$14 = this.f_27607_ * $$10 - this.f_27608_ * $$8 + this.f_27610_ * $$6;
        float $$15 = -this.f_27607_ * $$9 + this.f_27608_ * $$7 - this.f_27609_ * $$6;
        float $$16 = -this.f_27604_ * $$11 + this.f_27605_ * $$10 - this.f_27606_ * $$9;
        float $$17 = this.f_27603_ * $$11 - this.f_27605_ * $$8 + this.f_27606_ * $$7;
        float $$18 = -this.f_27603_ * $$10 + this.f_27604_ * $$8 - this.f_27606_ * $$6;
        float $$19 = this.f_27603_ * $$9 - this.f_27604_ * $$7 + this.f_27605_ * $$6;
        float $$20 = this.f_27616_ * $$5 - this.f_27617_ * $$4 + this.f_27618_ * $$3;
        float $$21 = -this.f_27615_ * $$5 + this.f_27617_ * $$2 - this.f_27618_ * $$1;
        float $$22 = this.f_27615_ * $$4 - this.f_27616_ * $$2 + this.f_27618_ * $$0;
        float $$23 = -this.f_27615_ * $$3 + this.f_27616_ * $$1 - this.f_27617_ * $$0;
        float $$24 = -this.f_27612_ * $$5 + this.f_27613_ * $$4 - this.f_27614_ * $$3;
        float $$25 = this.f_27611_ * $$5 - this.f_27613_ * $$2 + this.f_27614_ * $$1;
        float $$26 = -this.f_27611_ * $$4 + this.f_27612_ * $$2 - this.f_27614_ * $$0;
        float $$27 = this.f_27611_ * $$3 - this.f_27612_ * $$1 + this.f_27613_ * $$0;
        this.f_27603_ = $$12;
        this.f_27607_ = $$13;
        this.f_27611_ = $$14;
        this.f_27615_ = $$15;
        this.f_27604_ = $$16;
        this.f_27608_ = $$17;
        this.f_27612_ = $$18;
        this.f_27616_ = $$19;
        this.f_27605_ = $$20;
        this.f_27609_ = $$21;
        this.f_27613_ = $$22;
        this.f_27617_ = $$23;
        this.f_27606_ = $$24;
        this.f_27610_ = $$25;
        this.f_27614_ = $$26;
        this.f_27618_ = $$27;
        return $$0 * $$11 - $$1 * $$10 + $$2 * $$9 + $$3 * $$8 - $$4 * $$7 + $$5 * $$6;
    }

    public float m_162226_() {
        float $$0 = this.f_27603_ * this.f_27608_ - this.f_27604_ * this.f_27607_;
        float $$1 = this.f_27603_ * this.f_27609_ - this.f_27605_ * this.f_27607_;
        float $$2 = this.f_27603_ * this.f_27610_ - this.f_27606_ * this.f_27607_;
        float $$3 = this.f_27604_ * this.f_27609_ - this.f_27605_ * this.f_27608_;
        float $$4 = this.f_27604_ * this.f_27610_ - this.f_27606_ * this.f_27608_;
        float $$5 = this.f_27605_ * this.f_27610_ - this.f_27606_ * this.f_27609_;
        float $$6 = this.f_27611_ * this.f_27616_ - this.f_27612_ * this.f_27615_;
        float $$7 = this.f_27611_ * this.f_27617_ - this.f_27613_ * this.f_27615_;
        float $$8 = this.f_27611_ * this.f_27618_ - this.f_27614_ * this.f_27615_;
        float $$9 = this.f_27612_ * this.f_27617_ - this.f_27613_ * this.f_27616_;
        float $$10 = this.f_27612_ * this.f_27618_ - this.f_27614_ * this.f_27616_;
        float $$11 = this.f_27613_ * this.f_27618_ - this.f_27614_ * this.f_27617_;
        return $$0 * $$11 - $$1 * $$10 + $$2 * $$9 + $$3 * $$8 - $$4 * $$7 + $$5 * $$6;
    }

    public void m_27659_() {
        float $$0 = this.f_27607_;
        this.f_27607_ = this.f_27604_;
        this.f_27604_ = $$0;
        $$0 = this.f_27611_;
        this.f_27611_ = this.f_27605_;
        this.f_27605_ = $$0;
        $$0 = this.f_27612_;
        this.f_27612_ = this.f_27609_;
        this.f_27609_ = $$0;
        $$0 = this.f_27615_;
        this.f_27615_ = this.f_27606_;
        this.f_27606_ = $$0;
        $$0 = this.f_27616_;
        this.f_27616_ = this.f_27610_;
        this.f_27610_ = $$0;
        $$0 = this.f_27617_;
        this.f_27617_ = this.f_27614_;
        this.f_27614_ = $$0;
    }

    public boolean m_27657_() {
        float $$0 = this.m_27652_();
        if (Math.abs($$0) > 1.0E-6f) {
            this.m_27630_($$0);
            return true;
        }
        return false;
    }

    public void m_27644_(Matrix4f p_27645_) {
        float $$1 = this.f_27603_ * p_27645_.f_27603_ + this.f_27604_ * p_27645_.f_27607_ + this.f_27605_ * p_27645_.f_27611_ + this.f_27606_ * p_27645_.f_27615_;
        float $$2 = this.f_27603_ * p_27645_.f_27604_ + this.f_27604_ * p_27645_.f_27608_ + this.f_27605_ * p_27645_.f_27612_ + this.f_27606_ * p_27645_.f_27616_;
        float $$3 = this.f_27603_ * p_27645_.f_27605_ + this.f_27604_ * p_27645_.f_27609_ + this.f_27605_ * p_27645_.f_27613_ + this.f_27606_ * p_27645_.f_27617_;
        float $$4 = this.f_27603_ * p_27645_.f_27606_ + this.f_27604_ * p_27645_.f_27610_ + this.f_27605_ * p_27645_.f_27614_ + this.f_27606_ * p_27645_.f_27618_;
        float $$5 = this.f_27607_ * p_27645_.f_27603_ + this.f_27608_ * p_27645_.f_27607_ + this.f_27609_ * p_27645_.f_27611_ + this.f_27610_ * p_27645_.f_27615_;
        float $$6 = this.f_27607_ * p_27645_.f_27604_ + this.f_27608_ * p_27645_.f_27608_ + this.f_27609_ * p_27645_.f_27612_ + this.f_27610_ * p_27645_.f_27616_;
        float $$7 = this.f_27607_ * p_27645_.f_27605_ + this.f_27608_ * p_27645_.f_27609_ + this.f_27609_ * p_27645_.f_27613_ + this.f_27610_ * p_27645_.f_27617_;
        float $$8 = this.f_27607_ * p_27645_.f_27606_ + this.f_27608_ * p_27645_.f_27610_ + this.f_27609_ * p_27645_.f_27614_ + this.f_27610_ * p_27645_.f_27618_;
        float $$9 = this.f_27611_ * p_27645_.f_27603_ + this.f_27612_ * p_27645_.f_27607_ + this.f_27613_ * p_27645_.f_27611_ + this.f_27614_ * p_27645_.f_27615_;
        float $$10 = this.f_27611_ * p_27645_.f_27604_ + this.f_27612_ * p_27645_.f_27608_ + this.f_27613_ * p_27645_.f_27612_ + this.f_27614_ * p_27645_.f_27616_;
        float $$11 = this.f_27611_ * p_27645_.f_27605_ + this.f_27612_ * p_27645_.f_27609_ + this.f_27613_ * p_27645_.f_27613_ + this.f_27614_ * p_27645_.f_27617_;
        float $$12 = this.f_27611_ * p_27645_.f_27606_ + this.f_27612_ * p_27645_.f_27610_ + this.f_27613_ * p_27645_.f_27614_ + this.f_27614_ * p_27645_.f_27618_;
        float $$13 = this.f_27615_ * p_27645_.f_27603_ + this.f_27616_ * p_27645_.f_27607_ + this.f_27617_ * p_27645_.f_27611_ + this.f_27618_ * p_27645_.f_27615_;
        float $$14 = this.f_27615_ * p_27645_.f_27604_ + this.f_27616_ * p_27645_.f_27608_ + this.f_27617_ * p_27645_.f_27612_ + this.f_27618_ * p_27645_.f_27616_;
        float $$15 = this.f_27615_ * p_27645_.f_27605_ + this.f_27616_ * p_27645_.f_27609_ + this.f_27617_ * p_27645_.f_27613_ + this.f_27618_ * p_27645_.f_27617_;
        float $$16 = this.f_27615_ * p_27645_.f_27606_ + this.f_27616_ * p_27645_.f_27610_ + this.f_27617_ * p_27645_.f_27614_ + this.f_27618_ * p_27645_.f_27618_;
        this.f_27603_ = $$1;
        this.f_27604_ = $$2;
        this.f_27605_ = $$3;
        this.f_27606_ = $$4;
        this.f_27607_ = $$5;
        this.f_27608_ = $$6;
        this.f_27609_ = $$7;
        this.f_27610_ = $$8;
        this.f_27611_ = $$9;
        this.f_27612_ = $$10;
        this.f_27613_ = $$11;
        this.f_27614_ = $$12;
        this.f_27615_ = $$13;
        this.f_27616_ = $$14;
        this.f_27617_ = $$15;
        this.f_27618_ = $$16;
    }

    public void m_27646_(Quaternion p_27647_) {
        this.m_27644_(new Matrix4f(p_27647_));
    }

    public void m_27630_(float p_27631_) {
        this.f_27603_ *= p_27631_;
        this.f_27604_ *= p_27631_;
        this.f_27605_ *= p_27631_;
        this.f_27606_ *= p_27631_;
        this.f_27607_ *= p_27631_;
        this.f_27608_ *= p_27631_;
        this.f_27609_ *= p_27631_;
        this.f_27610_ *= p_27631_;
        this.f_27611_ *= p_27631_;
        this.f_27612_ *= p_27631_;
        this.f_27613_ *= p_27631_;
        this.f_27614_ *= p_27631_;
        this.f_27615_ *= p_27631_;
        this.f_27616_ *= p_27631_;
        this.f_27617_ *= p_27631_;
        this.f_27618_ *= p_27631_;
    }

    public void m_162224_(Matrix4f p_162225_) {
        this.f_27603_ += p_162225_.f_27603_;
        this.f_27604_ += p_162225_.f_27604_;
        this.f_27605_ += p_162225_.f_27605_;
        this.f_27606_ += p_162225_.f_27606_;
        this.f_27607_ += p_162225_.f_27607_;
        this.f_27608_ += p_162225_.f_27608_;
        this.f_27609_ += p_162225_.f_27609_;
        this.f_27610_ += p_162225_.f_27610_;
        this.f_27611_ += p_162225_.f_27611_;
        this.f_27612_ += p_162225_.f_27612_;
        this.f_27613_ += p_162225_.f_27613_;
        this.f_27614_ += p_162225_.f_27614_;
        this.f_27615_ += p_162225_.f_27615_;
        this.f_27616_ += p_162225_.f_27616_;
        this.f_27617_ += p_162225_.f_27617_;
        this.f_27618_ += p_162225_.f_27618_;
    }

    public void m_162227_(Matrix4f p_162228_) {
        this.f_27603_ -= p_162228_.f_27603_;
        this.f_27604_ -= p_162228_.f_27604_;
        this.f_27605_ -= p_162228_.f_27605_;
        this.f_27606_ -= p_162228_.f_27606_;
        this.f_27607_ -= p_162228_.f_27607_;
        this.f_27608_ -= p_162228_.f_27608_;
        this.f_27609_ -= p_162228_.f_27609_;
        this.f_27610_ -= p_162228_.f_27610_;
        this.f_27611_ -= p_162228_.f_27611_;
        this.f_27612_ -= p_162228_.f_27612_;
        this.f_27613_ -= p_162228_.f_27613_;
        this.f_27614_ -= p_162228_.f_27614_;
        this.f_27615_ -= p_162228_.f_27615_;
        this.f_27616_ -= p_162228_.f_27616_;
        this.f_27617_ -= p_162228_.f_27617_;
        this.f_27618_ -= p_162228_.f_27618_;
    }

    public float m_162231_() {
        return this.f_27603_ + this.f_27608_ + this.f_27613_ + this.f_27618_;
    }

    public static Matrix4f m_27625_(double p_27626_, float p_27627_, float p_27628_, float p_27629_) {
        float $$4 = (float)(1.0 / Math.tan(p_27626_ * 0.01745329238474369 / 2.0));
        Matrix4f $$5 = new Matrix4f();
        $$5.f_27603_ = $$4 / p_27627_;
        $$5.f_27608_ = $$4;
        $$5.f_27613_ = (p_27629_ + p_27628_) / (p_27628_ - p_27629_);
        $$5.f_27617_ = -1.0f;
        $$5.f_27614_ = 2.0f * p_27629_ * p_27628_ / (p_27628_ - p_27629_);
        return $$5;
    }

    public static Matrix4f m_27636_(float p_27637_, float p_27638_, float p_27639_, float p_27640_) {
        Matrix4f $$4 = new Matrix4f();
        $$4.f_27603_ = 2.0f / p_27637_;
        $$4.f_27608_ = 2.0f / p_27638_;
        float $$5 = p_27640_ - p_27639_;
        $$4.f_27613_ = -2.0f / $$5;
        $$4.f_27618_ = 1.0f;
        $$4.f_27606_ = -1.0f;
        $$4.f_27610_ = 1.0f;
        $$4.f_27614_ = -(p_27640_ + p_27639_) / $$5;
        return $$4;
    }

    public static Matrix4f m_162203_(float p_162204_, float p_162205_, float p_162206_, float p_162207_, float p_162208_, float p_162209_) {
        Matrix4f $$6 = new Matrix4f();
        float $$7 = p_162205_ - p_162204_;
        float $$8 = p_162206_ - p_162207_;
        float $$9 = p_162209_ - p_162208_;
        $$6.f_27603_ = 2.0f / $$7;
        $$6.f_27608_ = 2.0f / $$8;
        $$6.f_27613_ = -2.0f / $$9;
        $$6.f_27606_ = -(p_162205_ + p_162204_) / $$7;
        $$6.f_27610_ = -(p_162206_ + p_162207_) / $$8;
        $$6.f_27614_ = -(p_162209_ + p_162208_) / $$9;
        $$6.f_27618_ = 1.0f;
        return $$6;
    }

    public void m_27648_(Vector3f p_27649_) {
        this.f_27606_ += p_27649_.m_122239_();
        this.f_27610_ += p_27649_.m_122260_();
        this.f_27614_ += p_27649_.m_122269_();
    }

    public Matrix4f m_27658_() {
        return new Matrix4f(this);
    }

    public void m_162199_(float p_162200_, float p_162201_, float p_162202_) {
        this.f_27606_ = this.f_27603_ * p_162200_ + this.f_27604_ * p_162201_ + this.f_27605_ * p_162202_ + this.f_27606_;
        this.f_27610_ = this.f_27607_ * p_162200_ + this.f_27608_ * p_162201_ + this.f_27609_ * p_162202_ + this.f_27610_;
        this.f_27614_ = this.f_27611_ * p_162200_ + this.f_27612_ * p_162201_ + this.f_27613_ * p_162202_ + this.f_27614_;
        this.f_27618_ = this.f_27615_ * p_162200_ + this.f_27616_ * p_162201_ + this.f_27617_ * p_162202_ + this.f_27618_;
    }

    public static Matrix4f m_27632_(float p_27633_, float p_27634_, float p_27635_) {
        Matrix4f $$3 = new Matrix4f();
        $$3.f_27603_ = p_27633_;
        $$3.f_27608_ = p_27634_;
        $$3.f_27613_ = p_27635_;
        $$3.f_27618_ = 1.0f;
        return $$3;
    }

    public static Matrix4f m_27653_(float p_27654_, float p_27655_, float p_27656_) {
        Matrix4f $$3 = new Matrix4f();
        $$3.f_27603_ = 1.0f;
        $$3.f_27608_ = 1.0f;
        $$3.f_27613_ = 1.0f;
        $$3.f_27618_ = 1.0f;
        $$3.f_27606_ = p_27654_;
        $$3.f_27610_ = p_27655_;
        $$3.f_27614_ = p_27656_;
        return $$3;
    }
}

