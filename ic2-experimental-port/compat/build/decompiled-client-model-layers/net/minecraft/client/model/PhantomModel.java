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
import net.minecraft.world.entity.monster.Phantom;

public class PhantomModel<T extends Phantom>
extends HierarchicalModel<T> {
    private static final String f_170784_ = "tail_base";
    private static final String f_170785_ = "tail_tip";
    private final ModelPart f_170786_;
    private final ModelPart f_103315_;
    private final ModelPart f_103316_;
    private final ModelPart f_103317_;
    private final ModelPart f_103318_;
    private final ModelPart f_103319_;
    private final ModelPart f_103320_;

    public PhantomModel(ModelPart p_170788_) {
        this.f_170786_ = p_170788_;
        ModelPart $$1 = p_170788_.m_171324_("body");
        this.f_103319_ = $$1.m_171324_(f_170784_);
        this.f_103320_ = this.f_103319_.m_171324_(f_170785_);
        this.f_103315_ = $$1.m_171324_("left_wing_base");
        this.f_103316_ = this.f_103315_.m_171324_("left_wing_tip");
        this.f_103317_ = $$1.m_171324_("right_wing_base");
        this.f_103318_ = this.f_103317_.m_171324_("right_wing_tip");
    }

    public static LayerDefinition m_170789_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171481_(-3.0f, -2.0f, -8.0f, 5.0f, 3.0f, 9.0f), PartPose.m_171430_(-0.1f, 0.0f, 0.0f));
        PartDefinition $$3 = $$2.m_171599_(f_170784_, CubeListBuilder.m_171558_().m_171514_(3, 20).m_171481_(-2.0f, 0.0f, 0.0f, 3.0f, 2.0f, 6.0f), PartPose.m_171419_(0.0f, -2.0f, 1.0f));
        $$3.m_171599_(f_170785_, CubeListBuilder.m_171558_().m_171514_(4, 29).m_171481_(-1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 6.0f), PartPose.m_171419_(0.0f, 0.5f, 6.0f));
        PartDefinition $$4 = $$2.m_171599_("left_wing_base", CubeListBuilder.m_171558_().m_171514_(23, 12).m_171481_(0.0f, 0.0f, 0.0f, 6.0f, 2.0f, 9.0f), PartPose.m_171423_(2.0f, -2.0f, -8.0f, 0.0f, 0.0f, 0.1f));
        $$4.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171514_(16, 24).m_171481_(0.0f, 0.0f, 0.0f, 13.0f, 1.0f, 9.0f), PartPose.m_171423_(6.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f));
        PartDefinition $$5 = $$2.m_171599_("right_wing_base", CubeListBuilder.m_171558_().m_171514_(23, 12).m_171480_().m_171481_(-6.0f, 0.0f, 0.0f, 6.0f, 2.0f, 9.0f), PartPose.m_171423_(-3.0f, -2.0f, -8.0f, 0.0f, 0.0f, -0.1f));
        $$5.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171514_(16, 24).m_171480_().m_171481_(-13.0f, 0.0f, 0.0f, 13.0f, 1.0f, 9.0f), PartPose.m_171423_(-6.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.1f));
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -2.0f, -5.0f, 7.0f, 3.0f, 5.0f), PartPose.m_171423_(0.0f, 1.0f, -7.0f, 0.2f, 0.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170786_;
    }

    @Override
    public void m_6973_(T p_170791_, float p_170792_, float p_170793_, float p_170794_, float p_170795_, float p_170796_) {
        float $$6 = ((float)((Phantom)p_170791_).m_149736_() + p_170794_) * 7.448451f * ((float)Math.PI / 180);
        float $$7 = 16.0f;
        this.f_103315_.f_104205_ = Mth.m_14089_($$6) * 16.0f * ((float)Math.PI / 180);
        this.f_103316_.f_104205_ = Mth.m_14089_($$6) * 16.0f * ((float)Math.PI / 180);
        this.f_103317_.f_104205_ = -this.f_103315_.f_104205_;
        this.f_103318_.f_104205_ = -this.f_103316_.f_104205_;
        this.f_103319_.f_104203_ = -(5.0f + Mth.m_14089_($$6 * 2.0f) * 5.0f) * ((float)Math.PI / 180);
        this.f_103320_.f_104203_ = -(5.0f + Mth.m_14089_($$6 * 2.0f) * 5.0f) * ((float)Math.PI / 180);
    }
}

