/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core;

import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;

public class Rotations {
    protected final float f_123146_;
    protected final float f_123147_;
    protected final float f_123148_;

    public Rotations(float p_123150_, float p_123151_, float p_123152_) {
        this.f_123146_ = Float.isInfinite(p_123150_) || Float.isNaN(p_123150_) ? 0.0f : p_123150_ % 360.0f;
        this.f_123147_ = Float.isInfinite(p_123151_) || Float.isNaN(p_123151_) ? 0.0f : p_123151_ % 360.0f;
        this.f_123148_ = Float.isInfinite(p_123152_) || Float.isNaN(p_123152_) ? 0.0f : p_123152_ % 360.0f;
    }

    public Rotations(ListTag p_123154_) {
        this(p_123154_.m_128775_(0), p_123154_.m_128775_(1), p_123154_.m_128775_(2));
    }

    public ListTag m_123155_() {
        ListTag $$0 = new ListTag();
        $$0.add(FloatTag.m_128566_(this.f_123146_));
        $$0.add(FloatTag.m_128566_(this.f_123147_));
        $$0.add(FloatTag.m_128566_(this.f_123148_));
        return $$0;
    }

    public boolean equals(Object p_123160_) {
        if (!(p_123160_ instanceof Rotations)) {
            return false;
        }
        Rotations $$1 = (Rotations)p_123160_;
        return this.f_123146_ == $$1.f_123146_ && this.f_123147_ == $$1.f_123147_ && this.f_123148_ == $$1.f_123148_;
    }

    public float m_123156_() {
        return this.f_123146_;
    }

    public float m_123157_() {
        return this.f_123147_;
    }

    public float m_123158_() {
        return this.f_123148_;
    }

    public float m_175532_() {
        return Mth.m_14177_(this.f_123146_);
    }

    public float m_175533_() {
        return Mth.m_14177_(this.f_123147_);
    }

    public float m_175534_() {
        return Mth.m_14177_(this.f_123148_);
    }
}

