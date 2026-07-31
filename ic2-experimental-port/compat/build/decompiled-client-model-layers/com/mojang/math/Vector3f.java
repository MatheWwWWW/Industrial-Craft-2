/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.floats.Float2FloatFunction
 */
package com.mojang.math;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Matrix3f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class Vector3f {
    public static final Codec<Vector3f> f_176762_ = Codec.FLOAT.listOf().comapFlatMap(p_176767_ -> Util.m_143795_(p_176767_, 3).map(p_176774_ -> new Vector3f(((Float)p_176774_.get(0)).floatValue(), ((Float)p_176774_.get(1)).floatValue(), ((Float)p_176774_.get(2)).floatValue())), p_176776_ -> ImmutableList.of((Object)Float.valueOf(p_176776_.f_122228_), (Object)Float.valueOf(p_176776_.f_122229_), (Object)Float.valueOf(p_176776_.f_122230_)));
    public static Vector3f f_122222_ = new Vector3f(-1.0f, 0.0f, 0.0f);
    public static Vector3f f_122223_ = new Vector3f(1.0f, 0.0f, 0.0f);
    public static Vector3f f_122224_ = new Vector3f(0.0f, -1.0f, 0.0f);
    public static Vector3f f_122225_ = new Vector3f(0.0f, 1.0f, 0.0f);
    public static Vector3f f_122226_ = new Vector3f(0.0f, 0.0f, -1.0f);
    public static Vector3f f_122227_ = new Vector3f(0.0f, 0.0f, 1.0f);
    public static Vector3f f_176763_ = new Vector3f(0.0f, 0.0f, 0.0f);
    private float f_122228_;
    private float f_122229_;
    private float f_122230_;

    public Vector3f() {
    }

    public Vector3f(float p_122234_, float p_122235_, float p_122236_) {
        this.f_122228_ = p_122234_;
        this.f_122229_ = p_122235_;
        this.f_122230_ = p_122236_;
    }

    public Vector3f(Vector4f p_176765_) {
        this(p_176765_.m_123601_(), p_176765_.m_123615_(), p_176765_.m_123616_());
    }

    public Vector3f(Vec3 p_122238_) {
        this((float)p_122238_.f_82479_, (float)p_122238_.f_82480_, (float)p_122238_.f_82481_);
    }

    public boolean equals(Object p_122283_) {
        if (this == p_122283_) {
            return true;
        }
        if (p_122283_ == null || this.getClass() != p_122283_.getClass()) {
            return false;
        }
        Vector3f $$1 = (Vector3f)p_122283_;
        if (Float.compare($$1.f_122228_, this.f_122228_) != 0) {
            return false;
        }
        if (Float.compare($$1.f_122229_, this.f_122229_) != 0) {
            return false;
        }
        return Float.compare($$1.f_122230_, this.f_122230_) == 0;
    }

    public int hashCode() {
        int $$0 = Float.floatToIntBits(this.f_122228_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_122229_);
        $$0 = 31 * $$0 + Float.floatToIntBits(this.f_122230_);
        return $$0;
    }

    public float m_122239_() {
        return this.f_122228_;
    }

    public float m_122260_() {
        return this.f_122229_;
    }

    public float m_122269_() {
        return this.f_122230_;
    }

    public void m_122261_(float p_122262_) {
        this.f_122228_ *= p_122262_;
        this.f_122229_ *= p_122262_;
        this.f_122230_ *= p_122262_;
    }

    public void m_122263_(float p_122264_, float p_122265_, float p_122266_) {
        this.f_122228_ *= p_122264_;
        this.f_122229_ *= p_122265_;
        this.f_122230_ *= p_122266_;
    }

    public void m_176770_(Vector3f p_176771_, Vector3f p_176772_) {
        this.f_122228_ = Mth.m_14036_(this.f_122228_, p_176771_.m_122239_(), p_176772_.m_122239_());
        this.f_122229_ = Mth.m_14036_(this.f_122229_, p_176771_.m_122239_(), p_176772_.m_122260_());
        this.f_122230_ = Mth.m_14036_(this.f_122230_, p_176771_.m_122269_(), p_176772_.m_122269_());
    }

    public void m_122242_(float p_122243_, float p_122244_) {
        this.f_122228_ = Mth.m_14036_(this.f_122228_, p_122243_, p_122244_);
        this.f_122229_ = Mth.m_14036_(this.f_122229_, p_122243_, p_122244_);
        this.f_122230_ = Mth.m_14036_(this.f_122230_, p_122243_, p_122244_);
    }

    public void m_122245_(float p_122246_, float p_122247_, float p_122248_) {
        this.f_122228_ = p_122246_;
        this.f_122229_ = p_122247_;
        this.f_122230_ = p_122248_;
    }

    public void m_176768_(Vector3f p_176769_) {
        this.f_122228_ = p_176769_.f_122228_;
        this.f_122229_ = p_176769_.f_122229_;
        this.f_122230_ = p_176769_.f_122230_;
    }

    public void m_122272_(float p_122273_, float p_122274_, float p_122275_) {
        this.f_122228_ += p_122273_;
        this.f_122229_ += p_122274_;
        this.f_122230_ += p_122275_;
    }

    public void m_122253_(Vector3f p_122254_) {
        this.f_122228_ += p_122254_.f_122228_;
        this.f_122229_ += p_122254_.f_122229_;
        this.f_122230_ += p_122254_.f_122230_;
    }

    public void m_122267_(Vector3f p_122268_) {
        this.f_122228_ -= p_122268_.f_122228_;
        this.f_122229_ -= p_122268_.f_122229_;
        this.f_122230_ -= p_122268_.f_122230_;
    }

    public float m_122276_(Vector3f p_122277_) {
        return this.f_122228_ * p_122277_.f_122228_ + this.f_122229_ * p_122277_.f_122229_ + this.f_122230_ * p_122277_.f_122230_;
    }

    public boolean m_122278_() {
        float $$0 = this.f_122228_ * this.f_122228_ + this.f_122229_ * this.f_122229_ + this.f_122230_ * this.f_122230_;
        if ((double)$$0 < 1.0E-5) {
            return false;
        }
        float $$1 = Mth.m_14195_($$0);
        this.f_122228_ *= $$1;
        this.f_122229_ *= $$1;
        this.f_122230_ *= $$1;
        return true;
    }

    public void m_122279_(Vector3f p_122280_) {
        float $$1 = this.f_122228_;
        float $$2 = this.f_122229_;
        float $$3 = this.f_122230_;
        float $$4 = p_122280_.m_122239_();
        float $$5 = p_122280_.m_122260_();
        float $$6 = p_122280_.m_122269_();
        this.f_122228_ = $$2 * $$6 - $$3 * $$5;
        this.f_122229_ = $$3 * $$4 - $$1 * $$6;
        this.f_122230_ = $$1 * $$5 - $$2 * $$4;
    }

    public void m_122249_(Matrix3f p_122250_) {
        float $$1 = this.f_122228_;
        float $$2 = this.f_122229_;
        float $$3 = this.f_122230_;
        this.f_122228_ = p_122250_.f_8134_ * $$1 + p_122250_.f_8135_ * $$2 + p_122250_.f_8136_ * $$3;
        this.f_122229_ = p_122250_.f_8137_ * $$1 + p_122250_.f_8138_ * $$2 + p_122250_.f_8139_ * $$3;
        this.f_122230_ = p_122250_.f_8140_ * $$1 + p_122250_.f_8141_ * $$2 + p_122250_.f_8142_ * $$3;
    }

    public void m_122251_(Quaternion p_122252_) {
        Quaternion $$1 = new Quaternion(p_122252_);
        $$1.m_80148_(new Quaternion(this.m_122239_(), this.m_122260_(), this.m_122269_(), 0.0f));
        Quaternion $$2 = new Quaternion(p_122252_);
        $$2.m_80157_();
        $$1.m_80148_($$2);
        this.m_122245_($$1.m_80140_(), $$1.m_80150_(), $$1.m_80153_());
    }

    public void m_122255_(Vector3f p_122256_, float p_122257_) {
        float $$2 = 1.0f - p_122257_;
        this.f_122228_ = this.f_122228_ * $$2 + p_122256_.f_122228_ * p_122257_;
        this.f_122229_ = this.f_122229_ * $$2 + p_122256_.f_122229_ * p_122257_;
        this.f_122230_ = this.f_122230_ * $$2 + p_122256_.f_122230_ * p_122257_;
    }

    public Quaternion m_122270_(float p_122271_) {
        return new Quaternion(this, p_122271_, false);
    }

    public Quaternion m_122240_(float p_122241_) {
        return new Quaternion(this, p_122241_, true);
    }

    public Vector3f m_122281_() {
        return new Vector3f(this.f_122228_, this.f_122229_, this.f_122230_);
    }

    public void m_122258_(Float2FloatFunction p_122259_) {
        this.f_122228_ = p_122259_.get(this.f_122228_);
        this.f_122229_ = p_122259_.get(this.f_122229_);
        this.f_122230_ = p_122259_.get(this.f_122230_);
    }

    public String toString() {
        return "[" + this.f_122228_ + ", " + this.f_122229_ + ", " + this.f_122230_ + "]";
    }
}

