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

public class SpiderModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170968_ = "body0";
    private static final String f_170969_ = "body1";
    private static final String f_170970_ = "right_middle_front_leg";
    private static final String f_170971_ = "left_middle_front_leg";
    private static final String f_170972_ = "right_middle_hind_leg";
    private static final String f_170973_ = "left_middle_hind_leg";
    private final ModelPart f_170974_;
    private final ModelPart f_103852_;
    private final ModelPart f_170975_;
    private final ModelPart f_170976_;
    private final ModelPart f_170977_;
    private final ModelPart f_170978_;
    private final ModelPart f_170979_;
    private final ModelPart f_170980_;
    private final ModelPart f_170981_;
    private final ModelPart f_170982_;

    public SpiderModel(ModelPart p_170984_) {
        this.f_170974_ = p_170984_;
        this.f_103852_ = p_170984_.m_171324_("head");
        this.f_170975_ = p_170984_.m_171324_("right_hind_leg");
        this.f_170976_ = p_170984_.m_171324_("left_hind_leg");
        this.f_170977_ = p_170984_.m_171324_(f_170972_);
        this.f_170978_ = p_170984_.m_171324_(f_170973_);
        this.f_170979_ = p_170984_.m_171324_(f_170970_);
        this.f_170980_ = p_170984_.m_171324_(f_170971_);
        this.f_170981_ = p_170984_.m_171324_("right_front_leg");
        this.f_170982_ = p_170984_.m_171324_("left_front_leg");
    }

    public static LayerDefinition m_170985_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 15;
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(32, 4).m_171481_(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f), PartPose.m_171419_(0.0f, 15.0f, -3.0f));
        $$1.m_171599_(f_170968_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.m_171419_(0.0f, 15.0f, 0.0f));
        $$1.m_171599_(f_170969_, CubeListBuilder.m_171558_().m_171514_(0, 12).m_171481_(-5.0f, -4.0f, -6.0f, 10.0f, 8.0f, 12.0f), PartPose.m_171419_(0.0f, 15.0f, 9.0f));
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(18, 0).m_171481_(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(18, 0).m_171480_().m_171481_(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
        $$1.m_171599_("right_hind_leg", $$3, PartPose.m_171419_(-4.0f, 15.0f, 2.0f));
        $$1.m_171599_("left_hind_leg", $$4, PartPose.m_171419_(4.0f, 15.0f, 2.0f));
        $$1.m_171599_(f_170972_, $$3, PartPose.m_171419_(-4.0f, 15.0f, 1.0f));
        $$1.m_171599_(f_170973_, $$4, PartPose.m_171419_(4.0f, 15.0f, 1.0f));
        $$1.m_171599_(f_170970_, $$3, PartPose.m_171419_(-4.0f, 15.0f, 0.0f));
        $$1.m_171599_(f_170971_, $$4, PartPose.m_171419_(4.0f, 15.0f, 0.0f));
        $$1.m_171599_("right_front_leg", $$3, PartPose.m_171419_(-4.0f, 15.0f, -1.0f));
        $$1.m_171599_("left_front_leg", $$4, PartPose.m_171419_(4.0f, 15.0f, -1.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170974_;
    }

    @Override
    public void m_6973_(T p_103866_, float p_103867_, float p_103868_, float p_103869_, float p_103870_, float p_103871_) {
        this.f_103852_.f_104204_ = p_103870_ * ((float)Math.PI / 180);
        this.f_103852_.f_104203_ = p_103871_ * ((float)Math.PI / 180);
        float $$6 = 0.7853982f;
        this.f_170975_.f_104205_ = -0.7853982f;
        this.f_170976_.f_104205_ = 0.7853982f;
        this.f_170977_.f_104205_ = -0.58119464f;
        this.f_170978_.f_104205_ = 0.58119464f;
        this.f_170979_.f_104205_ = -0.58119464f;
        this.f_170980_.f_104205_ = 0.58119464f;
        this.f_170981_.f_104205_ = -0.7853982f;
        this.f_170982_.f_104205_ = 0.7853982f;
        float $$7 = -0.0f;
        float $$8 = 0.3926991f;
        this.f_170975_.f_104204_ = 0.7853982f;
        this.f_170976_.f_104204_ = -0.7853982f;
        this.f_170977_.f_104204_ = 0.3926991f;
        this.f_170978_.f_104204_ = -0.3926991f;
        this.f_170979_.f_104204_ = -0.3926991f;
        this.f_170980_.f_104204_ = 0.3926991f;
        this.f_170981_.f_104204_ = -0.7853982f;
        this.f_170982_.f_104204_ = 0.7853982f;
        float $$9 = -(Mth.m_14089_(p_103867_ * 0.6662f * 2.0f + 0.0f) * 0.4f) * p_103868_;
        float $$10 = -(Mth.m_14089_(p_103867_ * 0.6662f * 2.0f + (float)Math.PI) * 0.4f) * p_103868_;
        float $$11 = -(Mth.m_14089_(p_103867_ * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * p_103868_;
        float $$12 = -(Mth.m_14089_(p_103867_ * 0.6662f * 2.0f + 4.712389f) * 0.4f) * p_103868_;
        float $$13 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662f + 0.0f) * 0.4f) * p_103868_;
        float $$14 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662f + (float)Math.PI) * 0.4f) * p_103868_;
        float $$15 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662f + 1.5707964f) * 0.4f) * p_103868_;
        float $$16 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662f + 4.712389f) * 0.4f) * p_103868_;
        this.f_170975_.f_104204_ += $$9;
        this.f_170976_.f_104204_ += -$$9;
        this.f_170977_.f_104204_ += $$10;
        this.f_170978_.f_104204_ += -$$10;
        this.f_170979_.f_104204_ += $$11;
        this.f_170980_.f_104204_ += -$$11;
        this.f_170981_.f_104204_ += $$12;
        this.f_170982_.f_104204_ += -$$12;
        this.f_170975_.f_104205_ += $$13;
        this.f_170976_.f_104205_ += -$$13;
        this.f_170977_.f_104205_ += $$14;
        this.f_170978_.f_104205_ += -$$14;
        this.f_170979_.f_104205_ += $$15;
        this.f_170980_.f_104205_ += -$$15;
        this.f_170981_.f_104205_ += $$16;
        this.f_170982_.f_104205_ += -$$16;
    }
}

