/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.tuple.Triple
 */
package com.mojang.math;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.Util;
import org.apache.commons.lang3.tuple.Triple;

public final class Transformation {
    private final Matrix4f f_121078_;
    private boolean f_121079_;
    @Nullable
    private Vector3f f_121080_;
    @Nullable
    private Quaternion f_121081_;
    @Nullable
    private Vector3f f_121082_;
    @Nullable
    private Quaternion f_121083_;
    private static final Transformation f_121084_ = Util.m_137537_(() -> {
        Matrix4f $$0 = new Matrix4f();
        $$0.m_27624_();
        Transformation $$1 = new Transformation($$0);
        $$1.m_121105_();
        return $$1;
    });

    public Transformation(@Nullable Matrix4f p_121087_) {
        this.f_121078_ = p_121087_ == null ? Transformation.f_121084_.f_121078_ : p_121087_;
    }

    public Transformation(@Nullable Vector3f p_121089_, @Nullable Quaternion p_121090_, @Nullable Vector3f p_121091_, @Nullable Quaternion p_121092_) {
        this.f_121078_ = Transformation.m_121098_(p_121089_, p_121090_, p_121091_, p_121092_);
        this.f_121080_ = p_121089_ != null ? p_121089_ : new Vector3f();
        this.f_121081_ = p_121090_ != null ? p_121090_ : Quaternion.f_80118_.m_80161_();
        this.f_121082_ = p_121091_ != null ? p_121091_ : new Vector3f(1.0f, 1.0f, 1.0f);
        this.f_121083_ = p_121092_ != null ? p_121092_ : Quaternion.f_80118_.m_80161_();
        this.f_121079_ = true;
    }

    public static Transformation m_121093_() {
        return f_121084_;
    }

    public Transformation m_121096_(Transformation p_121097_) {
        Matrix4f $$1 = this.m_121104_();
        $$1.m_27644_(p_121097_.m_121104_());
        return new Transformation($$1);
    }

    @Nullable
    public Transformation m_121103_() {
        if (this == f_121084_) {
            return this;
        }
        Matrix4f $$0 = this.m_121104_();
        if ($$0.m_27657_()) {
            return new Transformation($$0);
        }
        return null;
    }

    private void m_121106_() {
        if (!this.f_121079_) {
            Pair<Matrix3f, Vector3f> $$0 = Transformation.m_121094_(this.f_121078_);
            Triple<Quaternion, Vector3f, Quaternion> $$1 = ((Matrix3f)$$0.getFirst()).m_8173_();
            this.f_121080_ = (Vector3f)$$0.getSecond();
            this.f_121081_ = (Quaternion)$$1.getLeft();
            this.f_121082_ = (Vector3f)$$1.getMiddle();
            this.f_121083_ = (Quaternion)$$1.getRight();
            this.f_121079_ = true;
        }
    }

    private static Matrix4f m_121098_(@Nullable Vector3f p_121099_, @Nullable Quaternion p_121100_, @Nullable Vector3f p_121101_, @Nullable Quaternion p_121102_) {
        Matrix4f $$4 = new Matrix4f();
        $$4.m_27624_();
        if (p_121100_ != null) {
            $$4.m_27644_(new Matrix4f(p_121100_));
        }
        if (p_121101_ != null) {
            $$4.m_27644_(Matrix4f.m_27632_(p_121101_.m_122239_(), p_121101_.m_122260_(), p_121101_.m_122269_()));
        }
        if (p_121102_ != null) {
            $$4.m_27644_(new Matrix4f(p_121102_));
        }
        if (p_121099_ != null) {
            $$4.f_27606_ = p_121099_.m_122239_();
            $$4.f_27610_ = p_121099_.m_122260_();
            $$4.f_27614_ = p_121099_.m_122269_();
        }
        return $$4;
    }

    public static Pair<Matrix3f, Vector3f> m_121094_(Matrix4f p_121095_) {
        p_121095_.m_27630_(1.0f / p_121095_.f_27618_);
        Vector3f $$1 = new Vector3f(p_121095_.f_27606_, p_121095_.f_27610_, p_121095_.f_27614_);
        Matrix3f $$2 = new Matrix3f(p_121095_);
        return Pair.of((Object)$$2, (Object)$$1);
    }

    public Matrix4f m_121104_() {
        return this.f_121078_.m_27658_();
    }

    public Vector3f m_175940_() {
        this.m_121106_();
        return this.f_121080_.m_122281_();
    }

    public Quaternion m_121105_() {
        this.m_121106_();
        return this.f_121081_.m_80161_();
    }

    public Vector3f m_175941_() {
        this.m_121106_();
        return this.f_121082_.m_122281_();
    }

    public Quaternion m_175942_() {
        this.m_121106_();
        return this.f_121083_.m_80161_();
    }

    public boolean equals(Object p_121108_) {
        if (this == p_121108_) {
            return true;
        }
        if (p_121108_ == null || this.getClass() != p_121108_.getClass()) {
            return false;
        }
        Transformation $$1 = (Transformation)p_121108_;
        return Objects.equals(this.f_121078_, $$1.f_121078_);
    }

    public int hashCode() {
        return Objects.hash(this.f_121078_);
    }

    public Transformation m_175937_(Transformation p_175938_, float p_175939_) {
        Vector3f $$2 = this.m_175940_();
        Quaternion $$3 = this.m_121105_();
        Vector3f $$4 = this.m_175941_();
        Quaternion $$5 = this.m_175942_();
        $$2.m_122255_(p_175938_.m_175940_(), p_175939_);
        $$3.m_175222_(p_175938_.m_121105_(), p_175939_);
        $$4.m_122255_(p_175938_.m_175941_(), p_175939_);
        $$5.m_175222_(p_175938_.m_175942_(), p_175939_);
        return new Transformation($$2, $$3, $$4, $$5);
    }
}

