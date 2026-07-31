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

public class SalmonModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170892_ = "body_front";
    private static final String f_170893_ = "body_back";
    private final ModelPart f_170894_;
    private final ModelPart f_103633_;

    public SalmonModel(ModelPart p_170896_) {
        this.f_170894_ = p_170896_;
        this.f_103633_ = p_170896_.m_171324_(f_170893_);
    }

    public static LayerDefinition m_170897_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 20;
        PartDefinition $$3 = $$1.m_171599_(f_170892_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.5f, -2.5f, 0.0f, 3.0f, 5.0f, 8.0f), PartPose.m_171419_(0.0f, 20.0f, 0.0f));
        PartDefinition $$4 = $$1.m_171599_(f_170893_, CubeListBuilder.m_171558_().m_171514_(0, 13).m_171481_(-1.5f, -2.5f, 0.0f, 3.0f, 5.0f, 8.0f), PartPose.m_171419_(0.0f, 20.0f, 8.0f));
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171481_(-1.0f, -2.0f, -3.0f, 2.0f, 4.0f, 3.0f), PartPose.m_171419_(0.0f, 20.0f, 0.0f));
        $$4.m_171599_("back_fin", CubeListBuilder.m_171558_().m_171514_(20, 10).m_171481_(0.0f, -2.5f, 0.0f, 0.0f, 5.0f, 6.0f), PartPose.m_171419_(0.0f, 0.0f, 8.0f));
        $$3.m_171599_("top_front_fin", CubeListBuilder.m_171558_().m_171514_(2, 1).m_171481_(0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 3.0f), PartPose.m_171419_(0.0f, -4.5f, 5.0f));
        $$4.m_171599_("top_back_fin", CubeListBuilder.m_171558_().m_171514_(0, 2).m_171481_(0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 4.0f), PartPose.m_171419_(0.0f, -4.5f, -1.0f));
        $$1.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(-4, 0).m_171481_(-2.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f), PartPose.m_171423_(-1.5f, 21.5f, 0.0f, 0.0f, 0.0f, -0.7853982f));
        $$1.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f), PartPose.m_171423_(1.5f, 21.5f, 0.0f, 0.0f, 0.0f, 0.7853982f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170894_;
    }

    @Override
    public void m_6973_(T p_103640_, float p_103641_, float p_103642_, float p_103643_, float p_103644_, float p_103645_) {
        float $$6 = 1.0f;
        float $$7 = 1.0f;
        if (!((Entity)p_103640_).m_20069_()) {
            $$6 = 1.3f;
            $$7 = 1.7f;
        }
        this.f_103633_.f_104204_ = -$$6 * 0.25f * Mth.m_14031_($$7 * 0.6f * p_103643_);
    }
}

