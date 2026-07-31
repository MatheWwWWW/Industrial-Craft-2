/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class EndermanModel<T extends LivingEntity>
extends HumanoidModel<T> {
    public boolean f_102576_;
    public boolean f_102577_;

    public EndermanModel(ModelPart p_170541_) {
        super(p_170541_);
    }

    public static LayerDefinition m_170542_() {
        float $$0 = -14.0f;
        MeshDefinition $$1 = HumanoidModel.m_170681_(CubeDeformation.f_171458_, -14.0f);
        PartDefinition $$2 = $$1.m_171576_();
        PartPose $$3 = PartPose.m_171419_(0.0f, -13.0f, 0.0f);
        $$2.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(-0.5f)), $$3);
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f), $$3);
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(32, 16).m_171481_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f), PartPose.m_171419_(0.0f, -14.0f, 0.0f));
        $$2.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(56, 0).m_171481_(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.m_171419_(-5.0f, -12.0f, 0.0f));
        $$2.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(56, 0).m_171480_().m_171481_(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.m_171419_(5.0f, -12.0f, 0.0f));
        $$2.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(56, 0).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.m_171419_(-2.0f, -5.0f, 0.0f));
        $$2.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(56, 0).m_171480_().m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.m_171419_(2.0f, -5.0f, 0.0f));
        return LayerDefinition.m_171565_($$1, 64, 32);
    }

    @Override
    public void m_6973_(T p_102588_, float p_102589_, float p_102590_, float p_102591_, float p_102592_, float p_102593_) {
        super.m_6973_(p_102588_, p_102589_, p_102590_, p_102591_, p_102592_, p_102593_);
        this.f_102808_.f_104207_ = true;
        int $$6 = -14;
        this.f_102810_.f_104203_ = 0.0f;
        this.f_102810_.f_104201_ = -14.0f;
        this.f_102810_.f_104202_ = -0.0f;
        this.f_102813_.f_104203_ -= 0.0f;
        this.f_102814_.f_104203_ -= 0.0f;
        this.f_102811_.f_104203_ *= 0.5f;
        this.f_102812_.f_104203_ *= 0.5f;
        this.f_102813_.f_104203_ *= 0.5f;
        this.f_102814_.f_104203_ *= 0.5f;
        float $$7 = 0.4f;
        if (this.f_102811_.f_104203_ > 0.4f) {
            this.f_102811_.f_104203_ = 0.4f;
        }
        if (this.f_102812_.f_104203_ > 0.4f) {
            this.f_102812_.f_104203_ = 0.4f;
        }
        if (this.f_102811_.f_104203_ < -0.4f) {
            this.f_102811_.f_104203_ = -0.4f;
        }
        if (this.f_102812_.f_104203_ < -0.4f) {
            this.f_102812_.f_104203_ = -0.4f;
        }
        if (this.f_102813_.f_104203_ > 0.4f) {
            this.f_102813_.f_104203_ = 0.4f;
        }
        if (this.f_102814_.f_104203_ > 0.4f) {
            this.f_102814_.f_104203_ = 0.4f;
        }
        if (this.f_102813_.f_104203_ < -0.4f) {
            this.f_102813_.f_104203_ = -0.4f;
        }
        if (this.f_102814_.f_104203_ < -0.4f) {
            this.f_102814_.f_104203_ = -0.4f;
        }
        if (this.f_102576_) {
            this.f_102811_.f_104203_ = -0.5f;
            this.f_102812_.f_104203_ = -0.5f;
            this.f_102811_.f_104205_ = 0.05f;
            this.f_102812_.f_104205_ = -0.05f;
        }
        this.f_102813_.f_104202_ = 0.0f;
        this.f_102814_.f_104202_ = 0.0f;
        this.f_102813_.f_104201_ = -5.0f;
        this.f_102814_.f_104201_ = -5.0f;
        this.f_102808_.f_104202_ = -0.0f;
        this.f_102808_.f_104201_ = -13.0f;
        this.f_102809_.f_104200_ = this.f_102808_.f_104200_;
        this.f_102809_.f_104201_ = this.f_102808_.f_104201_;
        this.f_102809_.f_104202_ = this.f_102808_.f_104202_;
        this.f_102809_.f_104203_ = this.f_102808_.f_104203_;
        this.f_102809_.f_104204_ = this.f_102808_.f_104204_;
        this.f_102809_.f_104205_ = this.f_102808_.f_104205_;
        if (this.f_102577_) {
            float $$8 = 1.0f;
            this.f_102808_.f_104201_ -= 5.0f;
        }
        int $$9 = -14;
        this.f_102811_.m_104227_(-5.0f, -12.0f, 0.0f);
        this.f_102812_.m_104227_(5.0f, -12.0f, 0.0f);
    }
}

