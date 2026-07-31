/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  org.apache.commons.lang3.tuple.Triple
 */
package com.mojang.math;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.nio.FloatBuffer;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
    private static final int f_152761_ = 3;
    private static final float f_8143_ = 3.0f + 2.0f * (float)Math.sqrt(2.0);
    private static final float f_8144_ = (float)Math.cos(0.39269908169872414);
    private static final float f_8145_ = (float)Math.sin(0.39269908169872414);
    private static final float f_8146_ = 1.0f / (float)Math.sqrt(2.0);
    protected float f_8134_;
    protected float f_8135_;
    protected float f_8136_;
    protected float f_8137_;
    protected float f_8138_;
    protected float f_8139_;
    protected float f_8140_;
    protected float f_8141_;
    protected float f_8142_;

    public Matrix3f() {
    }

    public Matrix3f(Quaternion p_8154_) {
        float $$1 = p_8154_.m_80140_();
        float $$2 = p_8154_.m_80150_();
        float $$3 = p_8154_.m_80153_();
        float $$4 = p_8154_.m_80156_();
        float $$5 = 2.0f * $$1 * $$1;
        float $$6 = 2.0f * $$2 * $$2;
        float $$7 = 2.0f * $$3 * $$3;
        this.f_8134_ = 1.0f - $$6 - $$7;
        this.f_8138_ = 1.0f - $$7 - $$5;
        this.f_8142_ = 1.0f - $$5 - $$6;
        float $$8 = $$1 * $$2;
        float $$9 = $$2 * $$3;
        float $$10 = $$3 * $$1;
        float $$11 = $$1 * $$4;
        float $$12 = $$2 * $$4;
        float $$13 = $$3 * $$4;
        this.f_8137_ = 2.0f * ($$8 + $$13);
        this.f_8135_ = 2.0f * ($$8 - $$13);
        this.f_8140_ = 2.0f * ($$10 - $$12);
        this.f_8136_ = 2.0f * ($$10 + $$12);
        this.f_8141_ = 2.0f * ($$9 + $$11);
        this.f_8139_ = 2.0f * ($$9 - $$11);
    }

    public static Matrix3f m_8174_(float p_8175_, float p_8176_, float p_8177_) {
        Matrix3f $$3 = new Matrix3f();
        $$3.f_8134_ = p_8175_;
        $$3.f_8138_ = p_8176_;
        $$3.f_8142_ = p_8177_;
        return $$3;
    }

    public Matrix3f(Matrix4f p_8152_) {
        this.f_8134_ = p_8152_.f_27603_;
        this.f_8135_ = p_8152_.f_27604_;
        this.f_8136_ = p_8152_.f_27605_;
        this.f_8137_ = p_8152_.f_27607_;
        this.f_8138_ = p_8152_.f_27608_;
        this.f_8139_ = p_8152_.f_27609_;
        this.f_8140_ = p_8152_.f_27611_;
        this.f_8141_ = p_8152_.f_27612_;
        this.f_8142_ = p_8152_.f_27613_;
    }

    public Matrix3f(Matrix3f p_8150_) {
        this.f_8134_ = p_8150_.f_8134_;
        this.f_8135_ = p_8150_.f_8135_;
        this.f_8136_ = p_8150_.f_8136_;
        this.f_8137_ = p_8150_.f_8137_;
        this.f_8138_ = p_8150_.f_8138_;
        this.f_8139_ = p_8150_.f_8139_;
        this.f_8140_ = p_8150_.f_8140_;
        this.f_8141_ = p_8150_.f_8141_;
        this.f_8142_ = p_8150_.f_8142_;
    }

    private static Pair<Float, Float> m_8161_(float p_8162_, float p_8163_, float p_8164_) {
        float $$4 = p_8163_;
        float $$3 = 2.0f * (p_8162_ - p_8164_);
        if (f_8143_ * $$4 * $$4 < $$3 * $$3) {
            float $$5 = Mth.m_14195_($$4 * $$4 + $$3 * $$3);
            return Pair.of((Object)Float.valueOf($$5 * $$4), (Object)Float.valueOf($$5 * $$3));
        }
        return Pair.of((Object)Float.valueOf(f_8145_), (Object)Float.valueOf(f_8144_));
    }

    private static Pair<Float, Float> m_8158_(float p_8159_, float p_8160_) {
        float $$2 = (float)Math.hypot(p_8159_, p_8160_);
        float $$3 = $$2 > 1.0E-6f ? p_8160_ : 0.0f;
        float $$4 = Math.abs(p_8159_) + Math.max($$2, 1.0E-6f);
        if (p_8159_ < 0.0f) {
            float $$5 = $$3;
            $$3 = $$4;
            $$4 = $$5;
        }
        float $$6 = Mth.m_14195_($$4 * $$4 + $$3 * $$3);
        return Pair.of((Object)Float.valueOf($$3 *= $$6), (Object)Float.valueOf($$4 *= $$6));
    }

    private static Quaternion m_8181_(Matrix3f p_8182_) {
        Matrix3f $$1 = new Matrix3f();
        Quaternion $$2 = Quaternion.f_80118_.m_80161_();
        if (p_8182_.f_8135_ * p_8182_.f_8135_ + p_8182_.f_8137_ * p_8182_.f_8137_ > 1.0E-6f) {
            Pair<Float, Float> $$3 = Matrix3f.m_8161_(p_8182_.f_8134_, 0.5f * (p_8182_.f_8135_ + p_8182_.f_8137_), p_8182_.f_8138_);
            Float $$4 = (Float)$$3.getFirst();
            Float $$5 = (Float)$$3.getSecond();
            Quaternion $$6 = new Quaternion(0.0f, 0.0f, $$4.floatValue(), $$5.floatValue());
            float $$7 = $$5.floatValue() * $$5.floatValue() - $$4.floatValue() * $$4.floatValue();
            float $$8 = -2.0f * $$4.floatValue() * $$5.floatValue();
            float $$9 = $$5.floatValue() * $$5.floatValue() + $$4.floatValue() * $$4.floatValue();
            $$2.m_80148_($$6);
            $$1.m_8180_();
            $$1.f_8134_ = $$7;
            $$1.f_8138_ = $$7;
            $$1.f_8137_ = -$$8;
            $$1.f_8135_ = $$8;
            $$1.f_8142_ = $$9;
            p_8182_.m_8178_($$1);
            $$1.m_8155_();
            $$1.m_8178_(p_8182_);
            p_8182_.m_8169_($$1);
        }
        if (p_8182_.f_8136_ * p_8182_.f_8136_ + p_8182_.f_8140_ * p_8182_.f_8140_ > 1.0E-6f) {
            Pair<Float, Float> $$10 = Matrix3f.m_8161_(p_8182_.f_8134_, 0.5f * (p_8182_.f_8136_ + p_8182_.f_8140_), p_8182_.f_8142_);
            float $$11 = -((Float)$$10.getFirst()).floatValue();
            Float $$12 = (Float)$$10.getSecond();
            Quaternion $$13 = new Quaternion(0.0f, $$11, 0.0f, $$12.floatValue());
            float $$14 = $$12.floatValue() * $$12.floatValue() - $$11 * $$11;
            float $$15 = -2.0f * $$11 * $$12.floatValue();
            float $$16 = $$12.floatValue() * $$12.floatValue() + $$11 * $$11;
            $$2.m_80148_($$13);
            $$1.m_8180_();
            $$1.f_8134_ = $$14;
            $$1.f_8142_ = $$14;
            $$1.f_8140_ = $$15;
            $$1.f_8136_ = -$$15;
            $$1.f_8138_ = $$16;
            p_8182_.m_8178_($$1);
            $$1.m_8155_();
            $$1.m_8178_(p_8182_);
            p_8182_.m_8169_($$1);
        }
        if (p_8182_.f_8139_ * p_8182_.f_8139_ + p_8182_.f_8141_ * p_8182_.f_8141_ > 1.0E-6f) {
            Pair<Float, Float> $$17 = Matrix3f.m_8161_(p_8182_.f_8138_, 0.5f * (p_8182_.f_8139_ + p_8182_.f_8141_), p_8182_.f_8142_);
            Float $$18 = (Float)$$17.getFirst();
            Float $$19 = (Float)$$17.getSecond();
            Quaternion $$20 = new Quaternion($$18.floatValue(), 0.0f, 0.0f, $$19.floatValue());
            float $$21 = $$19.floatValue() * $$19.floatValue() - $$18.floatValue() * $$18.floatValue();
            float $$22 = -2.0f * $$18.floatValue() * $$19.floatValue();
            float $$23 = $$19.floatValue() * $$19.floatValue() + $$18.floatValue() * $$18.floatValue();
            $$2.m_80148_($$20);
            $$1.m_8180_();
            $$1.f_8138_ = $$21;
            $$1.f_8142_ = $$21;
            $$1.f_8141_ = -$$22;
            $$1.f_8139_ = $$22;
            $$1.f_8134_ = $$23;
            p_8182_.m_8178_($$1);
            $$1.m_8155_();
            $$1.m_8178_(p_8182_);
            p_8182_.m_8169_($$1);
        }
        return $$2;
    }

    private static void m_152765_(Matrix3f p_152766_, Quaternion p_152767_) {
        float $$2 = p_152766_.f_8134_ * p_152766_.f_8134_ + p_152766_.f_8137_ * p_152766_.f_8137_ + p_152766_.f_8140_ * p_152766_.f_8140_;
        float $$3 = p_152766_.f_8135_ * p_152766_.f_8135_ + p_152766_.f_8138_ * p_152766_.f_8138_ + p_152766_.f_8141_ * p_152766_.f_8141_;
        float $$4 = p_152766_.f_8136_ * p_152766_.f_8136_ + p_152766_.f_8139_ * p_152766_.f_8139_ + p_152766_.f_8142_ * p_152766_.f_8142_;
        if ($$2 < $$3) {
            float $$5 = p_152766_.f_8137_;
            p_152766_.f_8137_ = -p_152766_.f_8134_;
            p_152766_.f_8134_ = $$5;
            $$5 = p_152766_.f_8138_;
            p_152766_.f_8138_ = -p_152766_.f_8135_;
            p_152766_.f_8135_ = $$5;
            $$5 = p_152766_.f_8139_;
            p_152766_.f_8139_ = -p_152766_.f_8136_;
            p_152766_.f_8136_ = $$5;
            Quaternion $$6 = new Quaternion(0.0f, 0.0f, f_8146_, f_8146_);
            p_152767_.m_80148_($$6);
            $$5 = $$2;
            $$2 = $$3;
            $$3 = $$5;
        }
        if ($$2 < $$4) {
            float $$7 = p_152766_.f_8140_;
            p_152766_.f_8140_ = -p_152766_.f_8134_;
            p_152766_.f_8134_ = $$7;
            $$7 = p_152766_.f_8141_;
            p_152766_.f_8141_ = -p_152766_.f_8135_;
            p_152766_.f_8135_ = $$7;
            $$7 = p_152766_.f_8142_;
            p_152766_.f_8142_ = -p_152766_.f_8136_;
            p_152766_.f_8136_ = $$7;
            Quaternion $$8 = new Quaternion(0.0f, f_8146_, 0.0f, f_8146_);
            p_152767_.m_80148_($$8);
            $$4 = $$2;
        }
        if ($$3 < $$4) {
            float $$9 = p_152766_.f_8140_;
            p_152766_.f_8140_ = -p_152766_.f_8137_;
            p_152766_.f_8137_ = $$9;
            $$9 = p_152766_.f_8141_;
            p_152766_.f_8141_ = -p_152766_.f_8138_;
            p_152766_.f_8138_ = $$9;
            $$9 = p_152766_.f_8142_;
            p_152766_.f_8142_ = -p_152766_.f_8139_;
            p_152766_.f_8139_ = $$9;
            Quaternion $$10 = new Quaternion(f_8146_, 0.0f, 0.0f, f_8146_);
            p_152767_.m_80148_($$10);
        }
    }

    public void m_8155_() {
        float $$0 = this.f_8135_;
        this.f_8135_ = this.f_8137_;
        this.f_8137_ = $$0;
        $$0 = this.f_8136_;
        this.f_8136_ = this.f_8140_;
        this.f_8140_ = $$0;
        $$0 = this.f_8139_;
        this.f_8139_ = this.f_8141_;
        this.f_8141_ = $$0;
    }

    public Triple<Quaternion, Vector3f, Quaternion> m_8173_() {
        Quaternion $$0 = Quaternion.f_80118_.m_80161_();
        Quaternion $$1 = Quaternion.f_80118_.m_80161_();
        Matrix3f $$2 = this.m_8183_();
        $$2.m_8155_();
        $$2.m_8178_(this);
        for (int $$3 = 0; $$3 < 5; ++$$3) {
            $$1.m_80148_(Matrix3f.m_8181_($$2));
        }
        $$1.m_80160_();
        Matrix3f $$4 = new Matrix3f(this);
        $$4.m_8178_(new Matrix3f($$1));
        float $$5 = 1.0f;
        Pair<Float, Float> $$6 = Matrix3f.m_8158_($$4.f_8134_, $$4.f_8137_);
        Float $$7 = (Float)$$6.getFirst();
        Float $$8 = (Float)$$6.getSecond();
        float $$9 = $$8.floatValue() * $$8.floatValue() - $$7.floatValue() * $$7.floatValue();
        float $$10 = -2.0f * $$7.floatValue() * $$8.floatValue();
        float $$11 = $$8.floatValue() * $$8.floatValue() + $$7.floatValue() * $$7.floatValue();
        Quaternion $$12 = new Quaternion(0.0f, 0.0f, $$7.floatValue(), $$8.floatValue());
        $$0.m_80148_($$12);
        Matrix3f $$13 = new Matrix3f();
        $$13.m_8180_();
        $$13.f_8134_ = $$9;
        $$13.f_8138_ = $$9;
        $$13.f_8137_ = $$10;
        $$13.f_8135_ = -$$10;
        $$13.f_8142_ = $$11;
        $$5 *= $$11;
        $$13.m_8178_($$4);
        $$6 = Matrix3f.m_8158_($$13.f_8134_, $$13.f_8140_);
        float $$14 = -((Float)$$6.getFirst()).floatValue();
        Float $$15 = (Float)$$6.getSecond();
        float $$16 = $$15.floatValue() * $$15.floatValue() - $$14 * $$14;
        float $$17 = -2.0f * $$14 * $$15.floatValue();
        float $$18 = $$15.floatValue() * $$15.floatValue() + $$14 * $$14;
        Quaternion $$19 = new Quaternion(0.0f, $$14, 0.0f, $$15.floatValue());
        $$0.m_80148_($$19);
        Matrix3f $$20 = new Matrix3f();
        $$20.m_8180_();
        $$20.f_8134_ = $$16;
        $$20.f_8142_ = $$16;
        $$20.f_8140_ = -$$17;
        $$20.f_8136_ = $$17;
        $$20.f_8138_ = $$18;
        $$5 *= $$18;
        $$20.m_8178_($$13);
        $$6 = Matrix3f.m_8158_($$20.f_8138_, $$20.f_8141_);
        Float $$21 = (Float)$$6.getFirst();
        Float $$22 = (Float)$$6.getSecond();
        float $$23 = $$22.floatValue() * $$22.floatValue() - $$21.floatValue() * $$21.floatValue();
        float $$24 = -2.0f * $$21.floatValue() * $$22.floatValue();
        float $$25 = $$22.floatValue() * $$22.floatValue() + $$21.floatValue() * $$21.floatValue();
        Quaternion $$26 = new Quaternion($$21.floatValue(), 0.0f, 0.0f, $$22.floatValue());
        $$0.m_80148_($$26);
        Matrix3f $$27 = new Matrix3f();
        $$27.m_8180_();
        $$27.f_8138_ = $$23;
        $$27.f_8142_ = $$23;
        $$27.f_8141_ = $$24;
        $$27.f_8139_ = -$$24;
        $$27.f_8134_ = $$25;
        $$5 *= $$25;
        $$27.m_8178_($$20);
        $$5 = 1.0f / $$5;
        $$0.m_80141_((float)Math.sqrt($$5));
        Vector3f $$28 = new Vector3f($$27.f_8134_ * $$5, $$27.f_8138_ * $$5, $$27.f_8142_ * $$5);
        return Triple.of((Object)$$0, (Object)$$28, (Object)$$1);
    }

    public boolean equals(Object p_8186_) {
        if (this == p_8186_) {
            return true;
        }
        if (p_8186_ == null || this.getClass() != p_8186_.getClass()) {
            return false;
        }
        Matrix3f $$1 = (Matrix3f)p_8186_;
        return Float.compare($$1.f_8134_, this.f_8134_) == 0 && Float.compare($$1.f_8135_, this.f_8135_) == 0 && Float.compare($$1.f_8136_, this.f_8136_) == 0 && Float.compare($$1.f_8137_, this.f_8137_) == 0 && Float.compare($$1.f_8138_, this.f_8138_) == 0 && Float.compare($$1.f_8139_, this.f_8139_) == 0 && Float.compare($$1.f_8140_, this.f_8140_) == 0 && Float.compare($$1.f_8141_, this.f_8141_) == 0 && Float.compare($$1.f_8142_, this.f_8142_) == 0;
    }

    public int hashCode() {
        int $$0 = this.f_8134_ != 0.0f ? Float.floatToIntBits(this.f_8134_) : 0;
        $$0 = 31 * $$0 + (this.f_8135_ != 0.0f ? Float.floatToIntBits(this.f_8135_) : 0);
        $$0 = 31 * $$0 + (this.f_8136_ != 0.0f ? Float.floatToIntBits(this.f_8136_) : 0);
        $$0 = 31 * $$0 + (this.f_8137_ != 0.0f ? Float.floatToIntBits(this.f_8137_) : 0);
        $$0 = 31 * $$0 + (this.f_8138_ != 0.0f ? Float.floatToIntBits(this.f_8138_) : 0);
        $$0 = 31 * $$0 + (this.f_8139_ != 0.0f ? Float.floatToIntBits(this.f_8139_) : 0);
        $$0 = 31 * $$0 + (this.f_8140_ != 0.0f ? Float.floatToIntBits(this.f_8140_) : 0);
        $$0 = 31 * $$0 + (this.f_8141_ != 0.0f ? Float.floatToIntBits(this.f_8141_) : 0);
        $$0 = 31 * $$0 + (this.f_8142_ != 0.0f ? Float.floatToIntBits(this.f_8142_) : 0);
        return $$0;
    }

    private static int m_152762_(int p_152763_, int p_152764_) {
        return p_152764_ * 3 + p_152763_;
    }

    public void m_152768_(FloatBuffer p_152769_) {
        this.f_8134_ = p_152769_.get(Matrix3f.m_152762_(0, 0));
        this.f_8135_ = p_152769_.get(Matrix3f.m_152762_(0, 1));
        this.f_8136_ = p_152769_.get(Matrix3f.m_152762_(0, 2));
        this.f_8137_ = p_152769_.get(Matrix3f.m_152762_(1, 0));
        this.f_8138_ = p_152769_.get(Matrix3f.m_152762_(1, 1));
        this.f_8139_ = p_152769_.get(Matrix3f.m_152762_(1, 2));
        this.f_8140_ = p_152769_.get(Matrix3f.m_152762_(2, 0));
        this.f_8141_ = p_152769_.get(Matrix3f.m_152762_(2, 1));
        this.f_8142_ = p_152769_.get(Matrix3f.m_152762_(2, 2));
    }

    public void m_152773_(FloatBuffer p_152774_) {
        this.f_8134_ = p_152774_.get(Matrix3f.m_152762_(0, 0));
        this.f_8135_ = p_152774_.get(Matrix3f.m_152762_(1, 0));
        this.f_8136_ = p_152774_.get(Matrix3f.m_152762_(2, 0));
        this.f_8137_ = p_152774_.get(Matrix3f.m_152762_(0, 1));
        this.f_8138_ = p_152774_.get(Matrix3f.m_152762_(1, 1));
        this.f_8139_ = p_152774_.get(Matrix3f.m_152762_(2, 1));
        this.f_8140_ = p_152774_.get(Matrix3f.m_152762_(0, 2));
        this.f_8141_ = p_152774_.get(Matrix3f.m_152762_(1, 2));
        this.f_8142_ = p_152774_.get(Matrix3f.m_152762_(2, 2));
    }

    public void m_152770_(FloatBuffer p_152771_, boolean p_152772_) {
        if (p_152772_) {
            this.m_152773_(p_152771_);
        } else {
            this.m_152768_(p_152771_);
        }
    }

    public void m_8169_(Matrix3f p_8170_) {
        this.f_8134_ = p_8170_.f_8134_;
        this.f_8135_ = p_8170_.f_8135_;
        this.f_8136_ = p_8170_.f_8136_;
        this.f_8137_ = p_8170_.f_8137_;
        this.f_8138_ = p_8170_.f_8138_;
        this.f_8139_ = p_8170_.f_8139_;
        this.f_8140_ = p_8170_.f_8140_;
        this.f_8141_ = p_8170_.f_8141_;
        this.f_8142_ = p_8170_.f_8142_;
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append("Matrix3f:\n");
        $$0.append(this.f_8134_);
        $$0.append(" ");
        $$0.append(this.f_8135_);
        $$0.append(" ");
        $$0.append(this.f_8136_);
        $$0.append("\n");
        $$0.append(this.f_8137_);
        $$0.append(" ");
        $$0.append(this.f_8138_);
        $$0.append(" ");
        $$0.append(this.f_8139_);
        $$0.append("\n");
        $$0.append(this.f_8140_);
        $$0.append(" ");
        $$0.append(this.f_8141_);
        $$0.append(" ");
        $$0.append(this.f_8142_);
        $$0.append("\n");
        return $$0.toString();
    }

    public void m_152780_(FloatBuffer p_152781_) {
        p_152781_.put(Matrix3f.m_152762_(0, 0), this.f_8134_);
        p_152781_.put(Matrix3f.m_152762_(0, 1), this.f_8135_);
        p_152781_.put(Matrix3f.m_152762_(0, 2), this.f_8136_);
        p_152781_.put(Matrix3f.m_152762_(1, 0), this.f_8137_);
        p_152781_.put(Matrix3f.m_152762_(1, 1), this.f_8138_);
        p_152781_.put(Matrix3f.m_152762_(1, 2), this.f_8139_);
        p_152781_.put(Matrix3f.m_152762_(2, 0), this.f_8140_);
        p_152781_.put(Matrix3f.m_152762_(2, 1), this.f_8141_);
        p_152781_.put(Matrix3f.m_152762_(2, 2), this.f_8142_);
    }

    public void m_152784_(FloatBuffer p_152785_) {
        p_152785_.put(Matrix3f.m_152762_(0, 0), this.f_8134_);
        p_152785_.put(Matrix3f.m_152762_(1, 0), this.f_8135_);
        p_152785_.put(Matrix3f.m_152762_(2, 0), this.f_8136_);
        p_152785_.put(Matrix3f.m_152762_(0, 1), this.f_8137_);
        p_152785_.put(Matrix3f.m_152762_(1, 1), this.f_8138_);
        p_152785_.put(Matrix3f.m_152762_(2, 1), this.f_8139_);
        p_152785_.put(Matrix3f.m_152762_(0, 2), this.f_8140_);
        p_152785_.put(Matrix3f.m_152762_(1, 2), this.f_8141_);
        p_152785_.put(Matrix3f.m_152762_(2, 2), this.f_8142_);
    }

    public void m_152775_(FloatBuffer p_152776_, boolean p_152777_) {
        if (p_152777_) {
            this.m_152784_(p_152776_);
        } else {
            this.m_152780_(p_152776_);
        }
    }

    public void m_8180_() {
        this.f_8134_ = 1.0f;
        this.f_8135_ = 0.0f;
        this.f_8136_ = 0.0f;
        this.f_8137_ = 0.0f;
        this.f_8138_ = 1.0f;
        this.f_8139_ = 0.0f;
        this.f_8140_ = 0.0f;
        this.f_8141_ = 0.0f;
        this.f_8142_ = 1.0f;
    }

    public float m_8184_() {
        float $$0 = this.f_8138_ * this.f_8142_ - this.f_8139_ * this.f_8141_;
        float $$1 = -(this.f_8137_ * this.f_8142_ - this.f_8139_ * this.f_8140_);
        float $$2 = this.f_8137_ * this.f_8141_ - this.f_8138_ * this.f_8140_;
        float $$3 = -(this.f_8135_ * this.f_8142_ - this.f_8136_ * this.f_8141_);
        float $$4 = this.f_8134_ * this.f_8142_ - this.f_8136_ * this.f_8140_;
        float $$5 = -(this.f_8134_ * this.f_8141_ - this.f_8135_ * this.f_8140_);
        float $$6 = this.f_8135_ * this.f_8139_ - this.f_8136_ * this.f_8138_;
        float $$7 = -(this.f_8134_ * this.f_8139_ - this.f_8136_ * this.f_8137_);
        float $$8 = this.f_8134_ * this.f_8138_ - this.f_8135_ * this.f_8137_;
        float $$9 = this.f_8134_ * $$0 + this.f_8135_ * $$1 + this.f_8136_ * $$2;
        this.f_8134_ = $$0;
        this.f_8137_ = $$1;
        this.f_8140_ = $$2;
        this.f_8135_ = $$3;
        this.f_8138_ = $$4;
        this.f_8141_ = $$5;
        this.f_8136_ = $$6;
        this.f_8139_ = $$7;
        this.f_8142_ = $$8;
        return $$9;
    }

    public float m_152786_() {
        float $$0 = this.f_8138_ * this.f_8142_ - this.f_8139_ * this.f_8141_;
        float $$1 = -(this.f_8137_ * this.f_8142_ - this.f_8139_ * this.f_8140_);
        float $$2 = this.f_8137_ * this.f_8141_ - this.f_8138_ * this.f_8140_;
        return this.f_8134_ * $$0 + this.f_8135_ * $$1 + this.f_8136_ * $$2;
    }

    public boolean m_8187_() {
        float $$0 = this.m_8184_();
        if (Math.abs($$0) > 1.0E-6f) {
            this.m_8156_($$0);
            return true;
        }
        return false;
    }

    public void m_8165_(int p_8166_, int p_8167_, float p_8168_) {
        if (p_8166_ == 0) {
            if (p_8167_ == 0) {
                this.f_8134_ = p_8168_;
            } else if (p_8167_ == 1) {
                this.f_8135_ = p_8168_;
            } else {
                this.f_8136_ = p_8168_;
            }
        } else if (p_8166_ == 1) {
            if (p_8167_ == 0) {
                this.f_8137_ = p_8168_;
            } else if (p_8167_ == 1) {
                this.f_8138_ = p_8168_;
            } else {
                this.f_8139_ = p_8168_;
            }
        } else if (p_8167_ == 0) {
            this.f_8140_ = p_8168_;
        } else if (p_8167_ == 1) {
            this.f_8141_ = p_8168_;
        } else {
            this.f_8142_ = p_8168_;
        }
    }

    public void m_8178_(Matrix3f p_8179_) {
        float $$1 = this.f_8134_ * p_8179_.f_8134_ + this.f_8135_ * p_8179_.f_8137_ + this.f_8136_ * p_8179_.f_8140_;
        float $$2 = this.f_8134_ * p_8179_.f_8135_ + this.f_8135_ * p_8179_.f_8138_ + this.f_8136_ * p_8179_.f_8141_;
        float $$3 = this.f_8134_ * p_8179_.f_8136_ + this.f_8135_ * p_8179_.f_8139_ + this.f_8136_ * p_8179_.f_8142_;
        float $$4 = this.f_8137_ * p_8179_.f_8134_ + this.f_8138_ * p_8179_.f_8137_ + this.f_8139_ * p_8179_.f_8140_;
        float $$5 = this.f_8137_ * p_8179_.f_8135_ + this.f_8138_ * p_8179_.f_8138_ + this.f_8139_ * p_8179_.f_8141_;
        float $$6 = this.f_8137_ * p_8179_.f_8136_ + this.f_8138_ * p_8179_.f_8139_ + this.f_8139_ * p_8179_.f_8142_;
        float $$7 = this.f_8140_ * p_8179_.f_8134_ + this.f_8141_ * p_8179_.f_8137_ + this.f_8142_ * p_8179_.f_8140_;
        float $$8 = this.f_8140_ * p_8179_.f_8135_ + this.f_8141_ * p_8179_.f_8138_ + this.f_8142_ * p_8179_.f_8141_;
        float $$9 = this.f_8140_ * p_8179_.f_8136_ + this.f_8141_ * p_8179_.f_8139_ + this.f_8142_ * p_8179_.f_8142_;
        this.f_8134_ = $$1;
        this.f_8135_ = $$2;
        this.f_8136_ = $$3;
        this.f_8137_ = $$4;
        this.f_8138_ = $$5;
        this.f_8139_ = $$6;
        this.f_8140_ = $$7;
        this.f_8141_ = $$8;
        this.f_8142_ = $$9;
    }

    public void m_8171_(Quaternion p_8172_) {
        this.m_8178_(new Matrix3f(p_8172_));
    }

    public void m_8156_(float p_8157_) {
        this.f_8134_ *= p_8157_;
        this.f_8135_ *= p_8157_;
        this.f_8136_ *= p_8157_;
        this.f_8137_ *= p_8157_;
        this.f_8138_ *= p_8157_;
        this.f_8139_ *= p_8157_;
        this.f_8140_ *= p_8157_;
        this.f_8141_ *= p_8157_;
        this.f_8142_ *= p_8157_;
    }

    public void m_152778_(Matrix3f p_152779_) {
        this.f_8134_ += p_152779_.f_8134_;
        this.f_8135_ += p_152779_.f_8135_;
        this.f_8136_ += p_152779_.f_8136_;
        this.f_8137_ += p_152779_.f_8137_;
        this.f_8138_ += p_152779_.f_8138_;
        this.f_8139_ += p_152779_.f_8139_;
        this.f_8140_ += p_152779_.f_8140_;
        this.f_8141_ += p_152779_.f_8141_;
        this.f_8142_ += p_152779_.f_8142_;
    }

    public void m_152782_(Matrix3f p_152783_) {
        this.f_8134_ -= p_152783_.f_8134_;
        this.f_8135_ -= p_152783_.f_8135_;
        this.f_8136_ -= p_152783_.f_8136_;
        this.f_8137_ -= p_152783_.f_8137_;
        this.f_8138_ -= p_152783_.f_8138_;
        this.f_8139_ -= p_152783_.f_8139_;
        this.f_8140_ -= p_152783_.f_8140_;
        this.f_8141_ -= p_152783_.f_8141_;
        this.f_8142_ -= p_152783_.f_8142_;
    }

    public float m_152787_() {
        return this.f_8134_ + this.f_8138_ + this.f_8142_;
    }

    public Matrix3f m_8183_() {
        return new Matrix3f(this);
    }
}

