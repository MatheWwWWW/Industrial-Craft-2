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

public class CodModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170492_;
    private final ModelPart f_102405_;

    public CodModel(ModelPart p_170494_) {
        this.f_170492_ = p_170494_;
        this.f_102405_ = p_170494_.m_171324_("tail_fin");
    }

    public static LayerDefinition m_170495_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 22;
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0f, -2.0f, 0.0f, 2.0f, 4.0f, 7.0f), PartPose.m_171419_(0.0f, 22.0f, 0.0f));
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(11, 0).m_171481_(-1.0f, -2.0f, -3.0f, 2.0f, 4.0f, 3.0f), PartPose.m_171419_(0.0f, 22.0f, 0.0f));
        $$1.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0f, -2.0f, -1.0f, 2.0f, 3.0f, 1.0f), PartPose.m_171419_(0.0f, 22.0f, -3.0f));
        $$1.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(22, 1).m_171481_(-2.0f, 0.0f, -1.0f, 2.0f, 0.0f, 2.0f), PartPose.m_171423_(-1.0f, 23.0f, 0.0f, 0.0f, 0.0f, -0.7853982f));
        $$1.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(22, 4).m_171481_(0.0f, 0.0f, -1.0f, 2.0f, 0.0f, 2.0f), PartPose.m_171423_(1.0f, 23.0f, 0.0f, 0.0f, 0.0f, 0.7853982f));
        $$1.m_171599_("tail_fin", CubeListBuilder.m_171558_().m_171514_(22, 3).m_171481_(0.0f, -2.0f, 0.0f, 0.0f, 4.0f, 4.0f), PartPose.m_171419_(0.0f, 22.0f, 7.0f));
        $$1.m_171599_("top_fin", CubeListBuilder.m_171558_().m_171514_(20, -6).m_171481_(0.0f, -1.0f, -1.0f, 0.0f, 1.0f, 6.0f), PartPose.m_171419_(0.0f, 20.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170492_;
    }

    @Override
    public void m_6973_(T p_102409_, float p_102410_, float p_102411_, float p_102412_, float p_102413_, float p_102414_) {
        float $$6 = 1.0f;
        if (!((Entity)p_102409_).m_20069_()) {
            $$6 = 1.5f;
        }
        this.f_102405_.f_104204_ = -$$6 * 0.45f * Mth.m_14031_(0.6f * p_102412_);
    }
}

