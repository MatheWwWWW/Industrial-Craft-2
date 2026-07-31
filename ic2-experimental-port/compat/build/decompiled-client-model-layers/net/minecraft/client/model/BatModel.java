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
import net.minecraft.world.entity.ambient.Bat;

public class BatModel
extends HierarchicalModel<Bat> {
    private final ModelPart f_170425_;
    private final ModelPart f_102184_;
    private final ModelPart f_102185_;
    private final ModelPart f_102186_;
    private final ModelPart f_102187_;
    private final ModelPart f_102188_;
    private final ModelPart f_102189_;

    public BatModel(ModelPart p_170427_) {
        this.f_170425_ = p_170427_;
        this.f_102184_ = p_170427_.m_171324_("head");
        this.f_102185_ = p_170427_.m_171324_("body");
        this.f_102186_ = this.f_102185_.m_171324_("right_wing");
        this.f_102188_ = this.f_102186_.m_171324_("right_wing_tip");
        this.f_102187_ = this.f_102185_.m_171324_("left_wing");
        this.f_102189_ = this.f_102187_.m_171324_("left_wing_tip");
    }

    public static LayerDefinition m_170428_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.f_171404_);
        $$2.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-4.0f, -6.0f, -2.0f, 3.0f, 4.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171480_().m_171481_(1.0f, -6.0f, -2.0f, 3.0f, 4.0f, 1.0f), PartPose.f_171404_);
        PartDefinition $$3 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-3.0f, 4.0f, -3.0f, 6.0f, 12.0f, 6.0f).m_171514_(0, 34).m_171481_(-5.0f, 16.0f, 0.0f, 10.0f, 6.0f, 1.0f), PartPose.f_171404_);
        PartDefinition $$4 = $$3.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(42, 0).m_171481_(-12.0f, 1.0f, 1.5f, 10.0f, 16.0f, 1.0f), PartPose.f_171404_);
        $$4.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171514_(24, 16).m_171481_(-8.0f, 1.0f, 0.0f, 8.0f, 12.0f, 1.0f), PartPose.m_171419_(-12.0f, 1.0f, 1.5f));
        PartDefinition $$5 = $$3.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(42, 0).m_171480_().m_171481_(2.0f, 1.0f, 1.5f, 10.0f, 16.0f, 1.0f), PartPose.f_171404_);
        $$5.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171514_(24, 16).m_171480_().m_171481_(0.0f, 1.0f, 0.0f, 8.0f, 12.0f, 1.0f), PartPose.m_171419_(12.0f, 1.0f, 1.5f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170425_;
    }

    @Override
    public void m_6973_(Bat p_102200_, float p_102201_, float p_102202_, float p_102203_, float p_102204_, float p_102205_) {
        if (p_102200_.m_27452_()) {
            this.f_102184_.f_104203_ = p_102205_ * ((float)Math.PI / 180);
            this.f_102184_.f_104204_ = (float)Math.PI - p_102204_ * ((float)Math.PI / 180);
            this.f_102184_.f_104205_ = (float)Math.PI;
            this.f_102184_.m_104227_(0.0f, -2.0f, 0.0f);
            this.f_102186_.m_104227_(-3.0f, 0.0f, 3.0f);
            this.f_102187_.m_104227_(3.0f, 0.0f, 3.0f);
            this.f_102185_.f_104203_ = (float)Math.PI;
            this.f_102186_.f_104203_ = -0.15707964f;
            this.f_102186_.f_104204_ = -1.2566371f;
            this.f_102188_.f_104204_ = -1.7278761f;
            this.f_102187_.f_104203_ = this.f_102186_.f_104203_;
            this.f_102187_.f_104204_ = -this.f_102186_.f_104204_;
            this.f_102189_.f_104204_ = -this.f_102188_.f_104204_;
        } else {
            this.f_102184_.f_104203_ = p_102205_ * ((float)Math.PI / 180);
            this.f_102184_.f_104204_ = p_102204_ * ((float)Math.PI / 180);
            this.f_102184_.f_104205_ = 0.0f;
            this.f_102184_.m_104227_(0.0f, 0.0f, 0.0f);
            this.f_102186_.m_104227_(0.0f, 0.0f, 0.0f);
            this.f_102187_.m_104227_(0.0f, 0.0f, 0.0f);
            this.f_102185_.f_104203_ = 0.7853982f + Mth.m_14089_(p_102203_ * 0.1f) * 0.15f;
            this.f_102185_.f_104204_ = 0.0f;
            this.f_102186_.f_104204_ = Mth.m_14089_(p_102203_ * 74.48451f * ((float)Math.PI / 180)) * (float)Math.PI * 0.25f;
            this.f_102187_.f_104204_ = -this.f_102186_.f_104204_;
            this.f_102188_.f_104204_ = this.f_102186_.f_104204_ * 0.5f;
            this.f_102189_.f_104204_ = -this.f_102186_.f_104204_ * 0.5f;
        }
    }
}

