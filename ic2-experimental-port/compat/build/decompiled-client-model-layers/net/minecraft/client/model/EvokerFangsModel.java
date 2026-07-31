/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class EvokerFangsModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170550_ = "base";
    private static final String f_170551_ = "upper_jaw";
    private static final String f_170552_ = "lower_jaw";
    private final ModelPart f_170553_;
    private final ModelPart f_102626_;
    private final ModelPart f_102627_;
    private final ModelPart f_102628_;

    public EvokerFangsModel(ModelPart p_170555_) {
        this.f_170553_ = p_170555_;
        this.f_102626_ = p_170555_.m_171324_(f_170550_);
        this.f_102627_ = p_170555_.m_171324_(f_170551_);
        this.f_102628_ = p_170555_.m_171324_(f_170552_);
    }

    public static LayerDefinition m_170556_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_170550_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, 0.0f, 10.0f, 12.0f, 10.0f), PartPose.m_171419_(-5.0f, 24.0f, -5.0f));
        CubeListBuilder $$2 = CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(0.0f, 0.0f, 0.0f, 4.0f, 14.0f, 8.0f);
        $$1.m_171599_(f_170551_, $$2, PartPose.m_171419_(1.5f, 24.0f, -4.0f));
        $$1.m_171599_(f_170552_, $$2, PartPose.m_171423_(-1.5f, 24.0f, 4.0f, 0.0f, (float)Math.PI, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_102632_, float p_102633_, float p_102634_, float p_102635_, float p_102636_, float p_102637_) {
        float $$6 = p_102633_ * 2.0f;
        if ($$6 > 1.0f) {
            $$6 = 1.0f;
        }
        $$6 = 1.0f - $$6 * $$6 * $$6;
        this.f_102627_.f_104205_ = (float)Math.PI - $$6 * 0.35f * (float)Math.PI;
        this.f_102628_.f_104205_ = (float)Math.PI + $$6 * 0.35f * (float)Math.PI;
        float $$7 = (p_102633_ + Mth.m_14031_(p_102633_ * 2.7f)) * 0.6f * 12.0f;
        this.f_102628_.f_104201_ = this.f_102627_.f_104201_ = 24.0f - $$7;
        this.f_102626_.f_104201_ = this.f_102627_.f_104201_;
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170553_;
    }
}

