/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 */
package com.mojang.blaze3d.vertex;

import com.google.common.collect.Queues;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import java.util.Deque;
import net.minecraft.Util;
import net.minecraft.util.Mth;

public class PoseStack {
    private final Deque<Pose> f_85834_ = Util.m_137469_(Queues.newArrayDeque(), p_85848_ -> {
        Matrix4f $$1 = new Matrix4f();
        $$1.m_27624_();
        Matrix3f $$2 = new Matrix3f();
        $$2.m_8180_();
        p_85848_.add(new Pose($$1, $$2));
    });

    public void m_85837_(double p_85838_, double p_85839_, double p_85840_) {
        Pose $$3 = this.f_85834_.getLast();
        $$3.f_85852_.m_162199_((float)p_85838_, (float)p_85839_, (float)p_85840_);
    }

    public void m_85841_(float p_85842_, float p_85843_, float p_85844_) {
        Pose $$3 = this.f_85834_.getLast();
        $$3.f_85852_.m_27644_(Matrix4f.m_27632_(p_85842_, p_85843_, p_85844_));
        if (p_85842_ == p_85843_ && p_85843_ == p_85844_) {
            if (p_85842_ > 0.0f) {
                return;
            }
            $$3.f_85853_.m_8156_(-1.0f);
        }
        float $$4 = 1.0f / p_85842_;
        float $$5 = 1.0f / p_85843_;
        float $$6 = 1.0f / p_85844_;
        float $$7 = Mth.m_14199_($$4 * $$5 * $$6);
        $$3.f_85853_.m_8178_(Matrix3f.m_8174_($$7 * $$4, $$7 * $$5, $$7 * $$6));
    }

    public void m_85845_(Quaternion p_85846_) {
        Pose $$1 = this.f_85834_.getLast();
        $$1.f_85852_.m_27646_(p_85846_);
        $$1.f_85853_.m_8171_(p_85846_);
    }

    public void m_85836_() {
        Pose $$0 = this.f_85834_.getLast();
        this.f_85834_.addLast(new Pose($$0.f_85852_.m_27658_(), $$0.f_85853_.m_8183_()));
    }

    public void m_85849_() {
        this.f_85834_.removeLast();
    }

    public Pose m_85850_() {
        return this.f_85834_.getLast();
    }

    public boolean m_85851_() {
        return this.f_85834_.size() == 1;
    }

    public void m_166856_() {
        Pose $$0 = this.f_85834_.getLast();
        $$0.f_85852_.m_27624_();
        $$0.f_85853_.m_8180_();
    }

    public void m_166854_(Matrix4f p_166855_) {
        this.f_85834_.getLast().f_85852_.m_27644_(p_166855_);
    }

    public static final class Pose {
        final Matrix4f f_85852_;
        final Matrix3f f_85853_;

        Pose(Matrix4f p_85855_, Matrix3f p_85856_) {
            this.f_85852_ = p_85855_;
            this.f_85853_ = p_85856_;
        }

        public Matrix4f m_85861_() {
            return this.f_85852_;
        }

        public Matrix3f m_85864_() {
            return this.f_85853_;
        }
    }
}

