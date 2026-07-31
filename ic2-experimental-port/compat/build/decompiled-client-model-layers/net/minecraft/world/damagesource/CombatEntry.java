/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CombatEntry {
    private final DamageSource f_19250_;
    private final int f_19251_;
    private final float f_19252_;
    private final float f_19253_;
    @Nullable
    private final String f_19254_;
    private final float f_19255_;

    public CombatEntry(DamageSource p_19257_, int p_19258_, float p_19259_, float p_19260_, @Nullable String p_19261_, float p_19262_) {
        this.f_19250_ = p_19257_;
        this.f_19251_ = p_19258_;
        this.f_19252_ = p_19260_;
        this.f_19253_ = p_19259_;
        this.f_19254_ = p_19261_;
        this.f_19255_ = p_19262_;
    }

    public DamageSource m_19263_() {
        return this.f_19250_;
    }

    public int m_146684_() {
        return this.f_19251_;
    }

    public float m_19264_() {
        return this.f_19252_;
    }

    public float m_146685_() {
        return this.f_19253_;
    }

    public float m_146686_() {
        return this.f_19253_ - this.f_19252_;
    }

    public boolean m_19265_() {
        return this.f_19250_.m_7639_() instanceof LivingEntity;
    }

    @Nullable
    public String m_19266_() {
        return this.f_19254_;
    }

    @Nullable
    public Component m_19267_() {
        return this.m_19263_().m_7639_() == null ? null : this.m_19263_().m_7639_().m_5446_();
    }

    @Nullable
    public Entity m_146687_() {
        return this.m_19263_().m_7639_();
    }

    public float m_19268_() {
        if (this.f_19250_ == DamageSource.f_19317_) {
            return Float.MAX_VALUE;
        }
        return this.f_19255_;
    }
}

