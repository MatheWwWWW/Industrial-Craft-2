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

public class PufferfishSmallModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170845_;
    private final ModelPart f_170846_;
    private final ModelPart f_170847_;

    public PufferfishSmallModel(ModelPart p_170849_) {
        this.f_170845_ = p_170849_;
        this.f_170846_ = p_170849_.m_171324_("left_fin");
        this.f_170847_ = p_170849_.m_171324_("right_fin");
    }

    public static LayerDefinition m_170850_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 23;
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 27).m_171481_(-1.5f, -2.0f, -1.5f, 3.0f, 2.0f, 3.0f), PartPose.m_171419_(0.0f, 23.0f, 0.0f));
        $$1.m_171599_("right_eye", CubeListBuilder.m_171558_().m_171514_(24, 6).m_171481_(-1.5f, 0.0f, -1.5f, 1.0f, 1.0f, 1.0f), PartPose.m_171419_(0.0f, 20.0f, 0.0f));
        $$1.m_171599_("left_eye", CubeListBuilder.m_171558_().m_171514_(28, 6).m_171481_(0.5f, 0.0f, -1.5f, 1.0f, 1.0f, 1.0f), PartPose.m_171419_(0.0f, 20.0f, 0.0f));
        $$1.m_171599_("back_fin", CubeListBuilder.m_171558_().m_171514_(-3, 0).m_171481_(-1.5f, 0.0f, 0.0f, 3.0f, 0.0f, 3.0f), PartPose.m_171419_(0.0f, 22.0f, 1.5f));
        $$1.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171481_(-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f), PartPose.m_171419_(-1.5f, 22.0f, -1.5f));
        $$1.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171481_(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f), PartPose.m_171419_(1.5f, 22.0f, -1.5f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170845_;
    }

    @Override
    public void m_6973_(T p_103486_, float p_103487_, float p_103488_, float p_103489_, float p_103490_, float p_103491_) {
        this.f_170847_.f_104205_ = -0.2f + 0.4f * Mth.m_14031_(p_103489_ * 0.2f);
        this.f_170846_.f_104205_ = 0.2f - 0.4f * Mth.m_14031_(p_103489_ * 0.2f);
    }
}

