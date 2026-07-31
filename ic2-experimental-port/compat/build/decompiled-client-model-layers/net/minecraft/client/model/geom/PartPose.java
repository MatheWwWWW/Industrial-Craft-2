/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.geom;

public class PartPose {
    public static final PartPose f_171404_ = PartPose.m_171423_(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    public final float f_171405_;
    public final float f_171406_;
    public final float f_171407_;
    public final float f_171408_;
    public final float f_171409_;
    public final float f_171410_;

    private PartPose(float p_171413_, float p_171414_, float p_171415_, float p_171416_, float p_171417_, float p_171418_) {
        this.f_171405_ = p_171413_;
        this.f_171406_ = p_171414_;
        this.f_171407_ = p_171415_;
        this.f_171408_ = p_171416_;
        this.f_171409_ = p_171417_;
        this.f_171410_ = p_171418_;
    }

    public static PartPose m_171419_(float p_171420_, float p_171421_, float p_171422_) {
        return PartPose.m_171423_(p_171420_, p_171421_, p_171422_, 0.0f, 0.0f, 0.0f);
    }

    public static PartPose m_171430_(float p_171431_, float p_171432_, float p_171433_) {
        return PartPose.m_171423_(0.0f, 0.0f, 0.0f, p_171431_, p_171432_, p_171433_);
    }

    public static PartPose m_171423_(float p_171424_, float p_171425_, float p_171426_, float p_171427_, float p_171428_, float p_171429_) {
        return new PartPose(p_171424_, p_171425_, p_171426_, p_171427_, p_171428_, p_171429_);
    }
}

