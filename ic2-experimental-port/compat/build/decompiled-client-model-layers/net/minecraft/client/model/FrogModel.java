/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.animation.definitions.FrogAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.frog.Frog;

public class FrogModel<T extends Frog>
extends HierarchicalModel<T> {
    private static final float f_233350_ = 200.0f;
    public static final float f_233349_ = 8.0f;
    private final ModelPart f_233351_;
    private final ModelPart f_233352_;
    private final ModelPart f_233353_;
    private final ModelPart f_233354_;
    private final ModelPart f_233355_;
    private final ModelPart f_233356_;
    private final ModelPart f_233357_;
    private final ModelPart f_233358_;
    private final ModelPart f_233359_;
    private final ModelPart f_233360_;

    public FrogModel(ModelPart p_233362_) {
        this.f_233351_ = p_233362_.m_171324_("root");
        this.f_233352_ = this.f_233351_.m_171324_("body");
        this.f_233353_ = this.f_233352_.m_171324_("head");
        this.f_233354_ = this.f_233353_.m_171324_("eyes");
        this.f_233355_ = this.f_233352_.m_171324_("tongue");
        this.f_233356_ = this.f_233352_.m_171324_("left_arm");
        this.f_233357_ = this.f_233352_.m_171324_("right_arm");
        this.f_233358_ = this.f_233351_.m_171324_("left_leg");
        this.f_233359_ = this.f_233351_.m_171324_("right_leg");
        this.f_233360_ = this.f_233352_.m_171324_("croaking_body");
    }

    public static LayerDefinition m_233378_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0f, 24.0f, 0.0f));
        PartDefinition $$3 = $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(3, 1).m_171481_(-3.5f, -2.0f, -8.0f, 7.0f, 3.0f, 9.0f).m_171514_(23, 22).m_171481_(-3.5f, -1.0f, -8.0f, 7.0f, 0.0f, 9.0f), PartPose.m_171419_(0.0f, -2.0f, 4.0f));
        PartDefinition $$4 = $$3.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(23, 13).m_171481_(-3.5f, -1.0f, -7.0f, 7.0f, 0.0f, 9.0f).m_171514_(0, 13).m_171481_(-3.5f, -2.0f, -7.0f, 7.0f, 3.0f, 9.0f), PartPose.m_171419_(0.0f, -2.0f, -1.0f));
        PartDefinition $$5 = $$4.m_171599_("eyes", CubeListBuilder.m_171558_(), PartPose.m_171419_(-0.5f, 0.0f, 2.0f));
        $$5.m_171599_("right_eye", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.5f, -1.0f, -1.5f, 3.0f, 2.0f, 3.0f), PartPose.m_171419_(-1.5f, -3.0f, -6.5f));
        $$5.m_171599_("left_eye", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171481_(-1.5f, -1.0f, -1.5f, 3.0f, 2.0f, 3.0f), PartPose.m_171419_(2.5f, -3.0f, -6.5f));
        $$3.m_171599_("croaking_body", CubeListBuilder.m_171558_().m_171514_(26, 5).m_171488_(-3.5f, -0.1f, -2.9f, 7.0f, 2.0f, 3.0f, new CubeDeformation(-0.1f)), PartPose.m_171419_(0.0f, -1.0f, -5.0f));
        PartDefinition $$6 = $$3.m_171599_("tongue", CubeListBuilder.m_171558_().m_171514_(17, 13).m_171481_(-2.0f, 0.0f, -7.1f, 4.0f, 0.0f, 7.0f), PartPose.m_171419_(0.0f, -1.01f, 1.0f));
        PartDefinition $$7 = $$3.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 3.0f, 3.0f), PartPose.m_171419_(4.0f, -1.0f, -6.5f));
        $$7.m_171599_("left_hand", CubeListBuilder.m_171558_().m_171514_(18, 40).m_171481_(-4.0f, 0.01f, -4.0f, 8.0f, 0.0f, 8.0f), PartPose.m_171419_(0.0f, 3.0f, -1.0f));
        PartDefinition $$8 = $$3.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 3.0f, 3.0f), PartPose.m_171419_(-4.0f, -1.0f, -6.5f));
        $$8.m_171599_("right_hand", CubeListBuilder.m_171558_().m_171514_(2, 40).m_171481_(-4.0f, 0.01f, -5.0f, 8.0f, 0.0f, 8.0f), PartPose.m_171419_(0.0f, 3.0f, 0.0f));
        PartDefinition $$9 = $$2.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(14, 25).m_171481_(-1.0f, 0.0f, -2.0f, 3.0f, 3.0f, 4.0f), PartPose.m_171419_(3.5f, -3.0f, 4.0f));
        $$9.m_171599_("left_foot", CubeListBuilder.m_171558_().m_171514_(2, 32).m_171481_(-4.0f, 0.01f, -4.0f, 8.0f, 0.0f, 8.0f), PartPose.m_171419_(2.0f, 3.0f, 0.0f));
        PartDefinition $$10 = $$2.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171481_(-2.0f, 0.0f, -2.0f, 3.0f, 3.0f, 4.0f), PartPose.m_171419_(-3.5f, -3.0f, 4.0f));
        $$10.m_171599_("right_foot", CubeListBuilder.m_171558_().m_171514_(18, 32).m_171481_(-4.0f, 0.01f, -4.0f, 8.0f, 0.0f, 8.0f), PartPose.m_171419_(-2.0f, 3.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 48, 48);
    }

    @Override
    public void m_6973_(T p_233372_, float p_233373_, float p_233374_, float p_233375_, float p_233376_, float p_233377_) {
        this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
        float $$6 = Math.min((float)((Entity)p_233372_).m_20184_().m_82556_() * 200.0f, 8.0f);
        this.m_233381_(((Frog)p_233372_).f_218459_, FrogAnimation.f_232337_, p_233375_);
        this.m_233381_(((Frog)p_233372_).f_218460_, FrogAnimation.f_232335_, p_233375_);
        this.m_233381_(((Frog)p_233372_).f_218461_, FrogAnimation.f_232338_, p_233375_);
        this.m_233385_(((Frog)p_233372_).f_218462_, FrogAnimation.f_232336_, p_233375_, $$6);
        this.m_233381_(((Frog)p_233372_).f_218463_, FrogAnimation.f_232339_, p_233375_);
        this.m_233381_(((Frog)p_233372_).f_218464_, FrogAnimation.f_232340_, p_233375_);
        this.f_233360_.f_104207_ = ((Frog)p_233372_).f_218460_.m_216984_();
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_233351_;
    }
}

