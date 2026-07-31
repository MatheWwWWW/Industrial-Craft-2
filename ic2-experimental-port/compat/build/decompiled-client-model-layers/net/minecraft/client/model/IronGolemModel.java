/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.IronGolem;

public class IronGolemModel<T extends IronGolem>
extends HierarchicalModel<T> {
    private final ModelPart f_170691_;
    private final ModelPart f_102936_;
    private final ModelPart f_170692_;
    private final ModelPart f_170693_;
    private final ModelPart f_170694_;
    private final ModelPart f_170695_;

    public IronGolemModel(ModelPart p_170697_) {
        this.f_170691_ = p_170697_;
        this.f_102936_ = p_170697_.m_171324_("head");
        this.f_170692_ = p_170697_.m_171324_("right_arm");
        this.f_170693_ = p_170697_.m_171324_("left_arm");
        this.f_170694_ = p_170697_.m_171324_("right_leg");
        this.f_170695_ = p_170697_.m_171324_("left_leg");
    }

    public static LayerDefinition m_170698_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -12.0f, -5.5f, 8.0f, 10.0f, 8.0f).m_171514_(24, 0).m_171481_(-1.0f, -5.0f, -7.5f, 2.0f, 4.0f, 2.0f), PartPose.m_171419_(0.0f, -7.0f, -2.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 40).m_171481_(-9.0f, -2.0f, -6.0f, 18.0f, 12.0f, 11.0f).m_171514_(0, 70).m_171488_(-4.5f, 10.0f, -3.0f, 9.0f, 5.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.m_171419_(0.0f, -7.0f, 0.0f));
        $$1.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(60, 21).m_171481_(-13.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f), PartPose.m_171419_(0.0f, -7.0f, 0.0f));
        $$1.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(60, 58).m_171481_(9.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f), PartPose.m_171419_(0.0f, -7.0f, 0.0f));
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(37, 0).m_171481_(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f), PartPose.m_171419_(-4.0f, 11.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(60, 0).m_171480_().m_171481_(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f), PartPose.m_171419_(5.0f, 11.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 128, 128);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170691_;
    }

    @Override
    public void m_6973_(T p_102962_, float p_102963_, float p_102964_, float p_102965_, float p_102966_, float p_102967_) {
        this.f_102936_.f_104204_ = p_102966_ * ((float)Math.PI / 180);
        this.f_102936_.f_104203_ = p_102967_ * ((float)Math.PI / 180);
        this.f_170694_.f_104203_ = -1.5f * Mth.m_14156_(p_102963_, 13.0f) * p_102964_;
        this.f_170695_.f_104203_ = 1.5f * Mth.m_14156_(p_102963_, 13.0f) * p_102964_;
        this.f_170694_.f_104204_ = 0.0f;
        this.f_170695_.f_104204_ = 0.0f;
    }

    @Override
    public void m_6839_(T p_102957_, float p_102958_, float p_102959_, float p_102960_) {
        int $$4 = ((IronGolem)p_102957_).m_28874_();
        if ($$4 > 0) {
            this.f_170692_.f_104203_ = -2.0f + 1.5f * Mth.m_14156_((float)$$4 - p_102960_, 10.0f);
            this.f_170693_.f_104203_ = -2.0f + 1.5f * Mth.m_14156_((float)$$4 - p_102960_, 10.0f);
        } else {
            int $$5 = ((IronGolem)p_102957_).m_28875_();
            if ($$5 > 0) {
                this.f_170692_.f_104203_ = -0.8f + 0.025f * Mth.m_14156_($$5, 70.0f);
                this.f_170693_.f_104203_ = 0.0f;
            } else {
                this.f_170692_.f_104203_ = (-0.2f + 1.5f * Mth.m_14156_(p_102958_, 13.0f)) * p_102959_;
                this.f_170693_.f_104203_ = (-0.2f - 1.5f * Mth.m_14156_(p_102958_, 13.0f)) * p_102959_;
            }
        }
    }

    public ModelPart m_102968_() {
        return this.f_170692_;
    }
}

