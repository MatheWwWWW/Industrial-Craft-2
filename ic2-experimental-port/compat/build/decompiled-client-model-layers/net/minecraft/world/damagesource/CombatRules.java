/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.damagesource;

import net.minecraft.util.Mth;

public class CombatRules {
    public static final float f_146688_ = 20.0f;
    public static final float f_146689_ = 25.0f;
    public static final float f_146690_ = 2.0f;
    public static final float f_146691_ = 0.2f;
    private static final int f_146692_ = 4;

    public static float m_19272_(float p_19273_, float p_19274_, float p_19275_) {
        float $$3 = 2.0f + p_19275_ / 4.0f;
        float $$4 = Mth.m_14036_(p_19274_ - p_19273_ / $$3, p_19274_ * 0.2f, 20.0f);
        return p_19273_ * (1.0f - $$4 / 25.0f);
    }

    public static float m_19269_(float p_19270_, float p_19271_) {
        float $$2 = Mth.m_14036_(p_19271_, 0.0f, 20.0f);
        return p_19270_ * (1.0f - $$2 / 25.0f);
    }
}

