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

public class PufferfishMidModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170838_;
    private final ModelPart f_170839_;
    private final ModelPart f_170840_;

    public PufferfishMidModel(ModelPart p_170842_) {
        this.f_170838_ = p_170842_;
        this.f_170839_ = p_170842_.m_171324_("left_blue_fin");
        this.f_170840_ = p_170842_.m_171324_("right_blue_fin");
    }

    public static LayerDefinition m_170843_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 22;
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(12, 22).m_171481_(-2.5f, -5.0f, -2.5f, 5.0f, 5.0f, 5.0f), PartPose.m_171419_(0.0f, 22.0f, 0.0f));
        $$1.m_171599_("right_blue_fin", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-2.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f), PartPose.m_171419_(-2.5f, 17.0f, -1.5f));
        $$1.m_171599_("left_blue_fin", CubeListBuilder.m_171558_().m_171514_(24, 3).m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f), PartPose.m_171419_(2.5f, 17.0f, -1.5f));
        $$1.m_171599_("top_front_fin", CubeListBuilder.m_171558_().m_171514_(15, 16).m_171481_(-2.5f, -1.0f, 0.0f, 5.0f, 1.0f, 1.0f), PartPose.m_171423_(0.0f, 17.0f, -2.5f, 0.7853982f, 0.0f, 0.0f));
        $$1.m_171599_("top_back_fin", CubeListBuilder.m_171558_().m_171514_(10, 16).m_171481_(-2.5f, -1.0f, -1.0f, 5.0f, 1.0f, 1.0f), PartPose.m_171423_(0.0f, 17.0f, 2.5f, -0.7853982f, 0.0f, 0.0f));
        $$1.m_171599_("right_front_fin", CubeListBuilder.m_171558_().m_171514_(8, 16).m_171481_(-1.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f), PartPose.m_171423_(-2.5f, 22.0f, -2.5f, 0.0f, -0.7853982f, 0.0f));
        $$1.m_171599_("right_back_fin", CubeListBuilder.m_171558_().m_171514_(8, 16).m_171481_(-1.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f), PartPose.m_171423_(-2.5f, 22.0f, 2.5f, 0.0f, 0.7853982f, 0.0f));
        $$1.m_171599_("left_back_fin", CubeListBuilder.m_171558_().m_171514_(4, 16).m_171481_(0.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f), PartPose.m_171423_(2.5f, 22.0f, 2.5f, 0.0f, -0.7853982f, 0.0f));
        $$1.m_171599_("left_front_fin", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(0.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f), PartPose.m_171423_(2.5f, 22.0f, -2.5f, 0.0f, 0.7853982f, 0.0f));
        $$1.m_171599_("bottom_back_fin", CubeListBuilder.m_171558_().m_171514_(8, 22).m_171481_(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f), PartPose.m_171423_(0.5f, 22.0f, 2.5f, 0.7853982f, 0.0f, 0.0f));
        $$1.m_171599_("bottom_front_fin", CubeListBuilder.m_171558_().m_171514_(17, 21).m_171481_(-2.5f, 0.0f, 0.0f, 5.0f, 1.0f, 1.0f), PartPose.m_171423_(0.0f, 22.0f, -2.5f, -0.7853982f, 0.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170838_;
    }

    @Override
    public void m_6973_(T p_103471_, float p_103472_, float p_103473_, float p_103474_, float p_103475_, float p_103476_) {
        this.f_170840_.f_104205_ = -0.2f + 0.4f * Mth.m_14031_(p_103474_ * 0.2f);
        this.f_170839_.f_104205_ = 0.2f - 0.4f * Mth.m_14031_(p_103474_ * 0.2f);
    }
}

