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

public class DolphinModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170528_;
    private final ModelPart f_102469_;
    private final ModelPart f_102470_;
    private final ModelPart f_102471_;

    public DolphinModel(ModelPart p_170530_) {
        this.f_170528_ = p_170530_;
        this.f_102469_ = p_170530_.m_171324_("body");
        this.f_102470_ = this.f_102469_.m_171324_("tail");
        this.f_102471_ = this.f_102470_.m_171324_("tail_fin");
    }

    public static LayerDefinition m_170531_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = 18.0f;
        float $$3 = -8.0f;
        PartDefinition $$4 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171481_(-4.0f, -7.0f, 0.0f, 8.0f, 7.0f, 13.0f), PartPose.m_171419_(0.0f, 22.0f, -5.0f));
        $$4.m_171599_("back_fin", CubeListBuilder.m_171558_().m_171514_(51, 0).m_171481_(-0.5f, 0.0f, 8.0f, 1.0f, 4.0f, 5.0f), PartPose.m_171430_(1.0471976f, 0.0f, 0.0f));
        $$4.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(48, 20).m_171480_().m_171481_(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f), PartPose.m_171423_(2.0f, -2.0f, 4.0f, 1.0471976f, 0.0f, 2.0943952f));
        $$4.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(48, 20).m_171481_(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f), PartPose.m_171423_(-2.0f, -2.0f, 4.0f, 1.0471976f, 0.0f, -2.0943952f));
        PartDefinition $$5 = $$4.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(-2.0f, -2.5f, 0.0f, 4.0f, 5.0f, 11.0f), PartPose.m_171423_(0.0f, -2.5f, 11.0f, -0.10471976f, 0.0f, 0.0f));
        $$5.m_171599_("tail_fin", CubeListBuilder.m_171558_().m_171514_(19, 20).m_171481_(-5.0f, -0.5f, 0.0f, 10.0f, 1.0f, 6.0f), PartPose.m_171419_(0.0f, 0.0f, 9.0f));
        PartDefinition $$6 = $$4.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -3.0f, -3.0f, 8.0f, 7.0f, 6.0f), PartPose.m_171419_(0.0f, -4.0f, -3.0f));
        $$6.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171481_(-1.0f, 2.0f, -7.0f, 2.0f, 2.0f, 4.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170528_;
    }

    @Override
    public void m_6973_(T p_102475_, float p_102476_, float p_102477_, float p_102478_, float p_102479_, float p_102480_) {
        this.f_102469_.f_104203_ = p_102480_ * ((float)Math.PI / 180);
        this.f_102469_.f_104204_ = p_102479_ * ((float)Math.PI / 180);
        if (((Entity)p_102475_).m_20184_().m_165925_() > 1.0E-7) {
            this.f_102469_.f_104203_ += -0.05f - 0.05f * Mth.m_14089_(p_102478_ * 0.3f);
            this.f_102470_.f_104203_ = -0.1f * Mth.m_14089_(p_102478_ * 0.3f);
            this.f_102471_.f_104203_ = -0.2f * Mth.m_14089_(p_102478_ * 0.3f);
        }
    }
}

