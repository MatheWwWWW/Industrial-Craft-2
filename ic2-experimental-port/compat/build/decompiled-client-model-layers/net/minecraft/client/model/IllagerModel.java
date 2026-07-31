/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;

public class IllagerModel<T extends AbstractIllager>
extends HierarchicalModel<T>
implements ArmedModel,
HeadedModel {
    private final ModelPart f_170686_;
    private final ModelPart f_102901_;
    private final ModelPart f_102902_;
    private final ModelPart f_102904_;
    private final ModelPart f_102905_;
    private final ModelPart f_102906_;
    private final ModelPart f_102907_;
    private final ModelPart f_102908_;

    public IllagerModel(ModelPart p_170688_) {
        this.f_170686_ = p_170688_;
        this.f_102901_ = p_170688_.m_171324_("head");
        this.f_102902_ = this.f_102901_.m_171324_("hat");
        this.f_102902_.f_104207_ = false;
        this.f_102904_ = p_170688_.m_171324_("arms");
        this.f_102905_ = p_170688_.m_171324_("left_leg");
        this.f_102906_ = p_170688_.m_171324_("right_leg");
        this.f_102908_ = p_170688_.m_171324_("left_arm");
        this.f_102907_ = p_170688_.m_171324_("right_arm");
    }

    public static LayerDefinition m_170689_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.m_171419_(0.0f, 0.0f, 0.0f));
        $$2.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, new CubeDeformation(0.45f)), PartPose.f_171404_);
        $$2.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.m_171419_(0.0f, -2.0f, 0.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171481_(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).m_171514_(0, 38).m_171488_(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.m_171419_(0.0f, 0.0f, 0.0f));
        PartDefinition $$3 = $$1.m_171599_("arms", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171481_(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).m_171514_(40, 38).m_171481_(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), PartPose.m_171423_(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        $$3.m_171599_("left_shoulder", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171480_().m_171481_(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f), PartPose.f_171404_);
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(-2.0f, 12.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(2.0f, 12.0f, 0.0f));
        $$1.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 46).m_171481_(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(-5.0f, 2.0f, 0.0f));
        $$1.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 46).m_171480_().m_171481_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170686_;
    }

    @Override
    public void m_6973_(T p_102928_, float p_102929_, float p_102930_, float p_102931_, float p_102932_, float p_102933_) {
        boolean $$7;
        this.f_102901_.f_104204_ = p_102932_ * ((float)Math.PI / 180);
        this.f_102901_.f_104203_ = p_102933_ * ((float)Math.PI / 180);
        if (this.f_102609_) {
            this.f_102907_.f_104203_ = -0.62831855f;
            this.f_102907_.f_104204_ = 0.0f;
            this.f_102907_.f_104205_ = 0.0f;
            this.f_102908_.f_104203_ = -0.62831855f;
            this.f_102908_.f_104204_ = 0.0f;
            this.f_102908_.f_104205_ = 0.0f;
            this.f_102906_.f_104203_ = -1.4137167f;
            this.f_102906_.f_104204_ = 0.31415927f;
            this.f_102906_.f_104205_ = 0.07853982f;
            this.f_102905_.f_104203_ = -1.4137167f;
            this.f_102905_.f_104204_ = -0.31415927f;
            this.f_102905_.f_104205_ = -0.07853982f;
        } else {
            this.f_102907_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662f + (float)Math.PI) * 2.0f * p_102930_ * 0.5f;
            this.f_102907_.f_104204_ = 0.0f;
            this.f_102907_.f_104205_ = 0.0f;
            this.f_102908_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662f) * 2.0f * p_102930_ * 0.5f;
            this.f_102908_.f_104204_ = 0.0f;
            this.f_102908_.f_104205_ = 0.0f;
            this.f_102906_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662f) * 1.4f * p_102930_ * 0.5f;
            this.f_102906_.f_104204_ = 0.0f;
            this.f_102906_.f_104205_ = 0.0f;
            this.f_102905_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662f + (float)Math.PI) * 1.4f * p_102930_ * 0.5f;
            this.f_102905_.f_104204_ = 0.0f;
            this.f_102905_.f_104205_ = 0.0f;
        }
        AbstractIllager.IllagerArmPose $$6 = ((AbstractIllager)p_102928_).m_6768_();
        if ($$6 == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (((LivingEntity)p_102928_).m_21205_().m_41619_()) {
                AnimationUtils.m_102102_(this.f_102908_, this.f_102907_, true, this.f_102608_, p_102931_);
            } else {
                AnimationUtils.m_102091_(this.f_102907_, this.f_102908_, p_102928_, this.f_102608_, p_102931_);
            }
        } else if ($$6 == AbstractIllager.IllagerArmPose.SPELLCASTING) {
            this.f_102907_.f_104202_ = 0.0f;
            this.f_102907_.f_104200_ = -5.0f;
            this.f_102908_.f_104202_ = 0.0f;
            this.f_102908_.f_104200_ = 5.0f;
            this.f_102907_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662f) * 0.25f;
            this.f_102908_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662f) * 0.25f;
            this.f_102907_.f_104205_ = 2.3561945f;
            this.f_102908_.f_104205_ = -2.3561945f;
            this.f_102907_.f_104204_ = 0.0f;
            this.f_102908_.f_104204_ = 0.0f;
        } else if ($$6 == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
            this.f_102907_.f_104204_ = -0.1f + this.f_102901_.f_104204_;
            this.f_102907_.f_104203_ = -1.5707964f + this.f_102901_.f_104203_;
            this.f_102908_.f_104203_ = -0.9424779f + this.f_102901_.f_104203_;
            this.f_102908_.f_104204_ = this.f_102901_.f_104204_ - 0.4f;
            this.f_102908_.f_104205_ = 1.5707964f;
        } else if ($$6 == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
            AnimationUtils.m_102097_(this.f_102907_, this.f_102908_, this.f_102901_, true);
        } else if ($$6 == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.m_102086_(this.f_102907_, this.f_102908_, p_102928_, true);
        } else if ($$6 == AbstractIllager.IllagerArmPose.CELEBRATING) {
            this.f_102907_.f_104202_ = 0.0f;
            this.f_102907_.f_104200_ = -5.0f;
            this.f_102907_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662f) * 0.05f;
            this.f_102907_.f_104205_ = 2.670354f;
            this.f_102907_.f_104204_ = 0.0f;
            this.f_102908_.f_104202_ = 0.0f;
            this.f_102908_.f_104200_ = 5.0f;
            this.f_102908_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662f) * 0.05f;
            this.f_102908_.f_104205_ = -2.3561945f;
            this.f_102908_.f_104204_ = 0.0f;
        }
        this.f_102904_.f_104207_ = $$7 = $$6 == AbstractIllager.IllagerArmPose.CROSSED;
        this.f_102908_.f_104207_ = !$$7;
        this.f_102907_.f_104207_ = !$$7;
    }

    private ModelPart m_102922_(HumanoidArm p_102923_) {
        if (p_102923_ == HumanoidArm.LEFT) {
            return this.f_102908_;
        }
        return this.f_102907_;
    }

    public ModelPart m_102934_() {
        return this.f_102902_;
    }

    @Override
    public ModelPart m_5585_() {
        return this.f_102901_;
    }

    @Override
    public void m_6002_(HumanoidArm p_102925_, PoseStack p_102926_) {
        this.m_102922_(p_102925_).m_104299_(p_102926_);
    }
}

