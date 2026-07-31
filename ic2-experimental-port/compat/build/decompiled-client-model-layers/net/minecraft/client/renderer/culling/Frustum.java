/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.culling;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.world.phys.AABB;

public class Frustum {
    public static final int f_194437_ = 4;
    private final Vector4f[] f_112995_ = new Vector4f[6];
    private Vector4f f_194438_;
    private double f_112996_;
    private double f_112997_;
    private double f_112998_;

    public Frustum(Matrix4f p_113000_, Matrix4f p_113001_) {
        this.m_113026_(p_113000_, p_113001_);
    }

    public Frustum(Frustum p_194440_) {
        System.arraycopy(p_194440_.f_112995_, 0, this.f_112995_, 0, p_194440_.f_112995_.length);
        this.f_112996_ = p_194440_.f_112996_;
        this.f_112997_ = p_194440_.f_112997_;
        this.f_112998_ = p_194440_.f_112998_;
        this.f_194438_ = p_194440_.f_194438_;
    }

    public Frustum m_194441_(int p_194442_) {
        double $$1 = Math.floor(this.f_112996_ / (double)p_194442_) * (double)p_194442_;
        double $$2 = Math.floor(this.f_112997_ / (double)p_194442_) * (double)p_194442_;
        double $$3 = Math.floor(this.f_112998_ / (double)p_194442_) * (double)p_194442_;
        double $$4 = Math.ceil(this.f_112996_ / (double)p_194442_) * (double)p_194442_;
        double $$5 = Math.ceil(this.f_112997_ / (double)p_194442_) * (double)p_194442_;
        double $$6 = Math.ceil(this.f_112998_ / (double)p_194442_) * (double)p_194442_;
        while (!this.m_194443_((float)($$1 - this.f_112996_), (float)($$2 - this.f_112997_), (float)($$3 - this.f_112998_), (float)($$4 - this.f_112996_), (float)($$5 - this.f_112997_), (float)($$6 - this.f_112998_))) {
            this.f_112996_ -= (double)(this.f_194438_.m_123601_() * 4.0f);
            this.f_112997_ -= (double)(this.f_194438_.m_123615_() * 4.0f);
            this.f_112998_ -= (double)(this.f_194438_.m_123616_() * 4.0f);
        }
        return this;
    }

    public void m_113002_(double p_113003_, double p_113004_, double p_113005_) {
        this.f_112996_ = p_113003_;
        this.f_112997_ = p_113004_;
        this.f_112998_ = p_113005_;
    }

    private void m_113026_(Matrix4f p_113027_, Matrix4f p_113028_) {
        Matrix4f $$2 = p_113028_.m_27658_();
        $$2.m_27644_(p_113027_);
        $$2.m_27659_();
        this.f_194438_ = new Vector4f(0.0f, 0.0f, 1.0f, 0.0f);
        this.f_194438_.m_123607_($$2);
        this.m_113020_($$2, -1, 0, 0, 0);
        this.m_113020_($$2, 1, 0, 0, 1);
        this.m_113020_($$2, 0, -1, 0, 2);
        this.m_113020_($$2, 0, 1, 0, 3);
        this.m_113020_($$2, 0, 0, -1, 4);
        this.m_113020_($$2, 0, 0, 1, 5);
    }

    private void m_113020_(Matrix4f p_113021_, int p_113022_, int p_113023_, int p_113024_, int p_113025_) {
        Vector4f $$5 = new Vector4f(p_113022_, p_113023_, p_113024_, 1.0f);
        $$5.m_123607_(p_113021_);
        $$5.m_123618_();
        this.f_112995_[p_113025_] = $$5;
    }

    public boolean m_113029_(AABB p_113030_) {
        return this.m_113006_(p_113030_.f_82288_, p_113030_.f_82289_, p_113030_.f_82290_, p_113030_.f_82291_, p_113030_.f_82292_, p_113030_.f_82293_);
    }

    private boolean m_113006_(double p_113007_, double p_113008_, double p_113009_, double p_113010_, double p_113011_, double p_113012_) {
        float $$6 = (float)(p_113007_ - this.f_112996_);
        float $$7 = (float)(p_113008_ - this.f_112997_);
        float $$8 = (float)(p_113009_ - this.f_112998_);
        float $$9 = (float)(p_113010_ - this.f_112996_);
        float $$10 = (float)(p_113011_ - this.f_112997_);
        float $$11 = (float)(p_113012_ - this.f_112998_);
        return this.m_113013_($$6, $$7, $$8, $$9, $$10, $$11);
    }

    private boolean m_113013_(float p_113014_, float p_113015_, float p_113016_, float p_113017_, float p_113018_, float p_113019_) {
        for (int $$6 = 0; $$6 < 6; ++$$6) {
            Vector4f $$7 = this.f_112995_[$$6];
            if ($$7.m_123613_(new Vector4f(p_113014_, p_113015_, p_113016_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113017_, p_113015_, p_113016_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113014_, p_113018_, p_113016_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113017_, p_113018_, p_113016_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113014_, p_113015_, p_113019_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113017_, p_113015_, p_113019_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113014_, p_113018_, p_113019_, 1.0f)) > 0.0f) continue;
            if ($$7.m_123613_(new Vector4f(p_113017_, p_113018_, p_113019_, 1.0f)) > 0.0f) continue;
            return false;
        }
        return true;
    }

    private boolean m_194443_(float p_194444_, float p_194445_, float p_194446_, float p_194447_, float p_194448_, float p_194449_) {
        for (int $$6 = 0; $$6 < 6; ++$$6) {
            Vector4f $$7 = this.f_112995_[$$6];
            Vector4f vector4f = new Vector4f(p_194444_, p_194445_, p_194446_, 1.0f);
            if ($$7.m_123613_(vector4f) <= 0.0f) {
                return false;
            }
            Vector4f vector4f2 = new Vector4f(p_194447_, p_194445_, p_194446_, 1.0f);
            if ($$7.m_123613_(vector4f2) <= 0.0f) {
                return false;
            }
            Vector4f vector4f3 = new Vector4f(p_194444_, p_194448_, p_194446_, 1.0f);
            if ($$7.m_123613_(vector4f3) <= 0.0f) {
                return false;
            }
            Vector4f vector4f4 = new Vector4f(p_194447_, p_194448_, p_194446_, 1.0f);
            if ($$7.m_123613_(vector4f4) <= 0.0f) {
                return false;
            }
            Vector4f vector4f5 = new Vector4f(p_194444_, p_194445_, p_194449_, 1.0f);
            if ($$7.m_123613_(vector4f5) <= 0.0f) {
                return false;
            }
            Vector4f vector4f6 = new Vector4f(p_194447_, p_194445_, p_194449_, 1.0f);
            if ($$7.m_123613_(vector4f6) <= 0.0f) {
                return false;
            }
            Vector4f vector4f7 = new Vector4f(p_194444_, p_194448_, p_194449_, 1.0f);
            if ($$7.m_123613_(vector4f7) <= 0.0f) {
                return false;
            }
            Vector4f vector4f8 = new Vector4f(p_194447_, p_194448_, p_194449_, 1.0f);
            if (!($$7.m_123613_(vector4f8) <= 0.0f)) continue;
            return false;
        }
        return true;
    }
}

