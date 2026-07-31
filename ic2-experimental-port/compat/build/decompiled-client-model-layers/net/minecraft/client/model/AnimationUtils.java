/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CrossbowItem;

public class AnimationUtils {
    public static void m_102097_(ModelPart p_102098_, ModelPart p_102099_, ModelPart p_102100_, boolean p_102101_) {
        ModelPart $$4 = p_102101_ ? p_102098_ : p_102099_;
        ModelPart $$5 = p_102101_ ? p_102099_ : p_102098_;
        $$4.f_104204_ = (p_102101_ ? -0.3f : 0.3f) + p_102100_.f_104204_;
        $$5.f_104204_ = (p_102101_ ? 0.6f : -0.6f) + p_102100_.f_104204_;
        $$4.f_104203_ = -1.5707964f + p_102100_.f_104203_ + 0.1f;
        $$5.f_104203_ = -1.5f + p_102100_.f_104203_;
    }

    public static void m_102086_(ModelPart p_102087_, ModelPart p_102088_, LivingEntity p_102089_, boolean p_102090_) {
        ModelPart $$4 = p_102090_ ? p_102087_ : p_102088_;
        ModelPart $$5 = p_102090_ ? p_102088_ : p_102087_;
        $$4.f_104204_ = p_102090_ ? -0.8f : 0.8f;
        $$5.f_104203_ = $$4.f_104203_ = -0.97079635f;
        float $$6 = CrossbowItem.m_40939_(p_102089_.m_21211_());
        float $$7 = Mth.m_14036_(p_102089_.m_21252_(), 0.0f, $$6);
        float $$8 = $$7 / $$6;
        $$5.f_104204_ = Mth.m_14179_($$8, 0.4f, 0.85f) * (float)(p_102090_ ? 1 : -1);
        $$5.f_104203_ = Mth.m_14179_($$8, $$5.f_104203_, -1.5707964f);
    }

    public static <T extends Mob> void m_102091_(ModelPart p_102092_, ModelPart p_102093_, T p_102094_, float p_102095_, float p_102096_) {
        float $$5 = Mth.m_14031_(p_102095_ * (float)Math.PI);
        float $$6 = Mth.m_14031_((1.0f - (1.0f - p_102095_) * (1.0f - p_102095_)) * (float)Math.PI);
        p_102092_.f_104205_ = 0.0f;
        p_102093_.f_104205_ = 0.0f;
        p_102092_.f_104204_ = 0.15707964f;
        p_102093_.f_104204_ = -0.15707964f;
        if (p_102094_.m_5737_() == HumanoidArm.RIGHT) {
            p_102092_.f_104203_ = -1.8849558f + Mth.m_14089_(p_102096_ * 0.09f) * 0.15f;
            p_102093_.f_104203_ = -0.0f + Mth.m_14089_(p_102096_ * 0.19f) * 0.5f;
            p_102092_.f_104203_ += $$5 * 2.2f - $$6 * 0.4f;
            p_102093_.f_104203_ += $$5 * 1.2f - $$6 * 0.4f;
        } else {
            p_102092_.f_104203_ = -0.0f + Mth.m_14089_(p_102096_ * 0.19f) * 0.5f;
            p_102093_.f_104203_ = -1.8849558f + Mth.m_14089_(p_102096_ * 0.09f) * 0.15f;
            p_102092_.f_104203_ += $$5 * 1.2f - $$6 * 0.4f;
            p_102093_.f_104203_ += $$5 * 2.2f - $$6 * 0.4f;
        }
        AnimationUtils.m_102082_(p_102092_, p_102093_, p_102096_);
    }

    public static void m_170341_(ModelPart p_170342_, float p_170343_, float p_170344_) {
        p_170342_.f_104205_ += p_170344_ * (Mth.m_14089_(p_170343_ * 0.09f) * 0.05f + 0.05f);
        p_170342_.f_104203_ += p_170344_ * (Mth.m_14031_(p_170343_ * 0.067f) * 0.05f);
    }

    public static void m_102082_(ModelPart p_102083_, ModelPart p_102084_, float p_102085_) {
        AnimationUtils.m_170341_(p_102083_, p_102085_, 1.0f);
        AnimationUtils.m_170341_(p_102084_, p_102085_, -1.0f);
    }

    public static void m_102102_(ModelPart p_102103_, ModelPart p_102104_, boolean p_102105_, float p_102106_, float p_102107_) {
        float $$7;
        float $$5 = Mth.m_14031_(p_102106_ * (float)Math.PI);
        float $$6 = Mth.m_14031_((1.0f - (1.0f - p_102106_) * (1.0f - p_102106_)) * (float)Math.PI);
        p_102104_.f_104205_ = 0.0f;
        p_102103_.f_104205_ = 0.0f;
        p_102104_.f_104204_ = -(0.1f - $$5 * 0.6f);
        p_102103_.f_104204_ = 0.1f - $$5 * 0.6f;
        p_102104_.f_104203_ = $$7 = (float)(-Math.PI) / (p_102105_ ? 1.5f : 2.25f);
        p_102103_.f_104203_ = $$7;
        p_102104_.f_104203_ += $$5 * 1.2f - $$6 * 0.4f;
        p_102103_.f_104203_ += $$5 * 1.2f - $$6 * 0.4f;
        AnimationUtils.m_102082_(p_102104_, p_102103_, p_102107_);
    }
}

