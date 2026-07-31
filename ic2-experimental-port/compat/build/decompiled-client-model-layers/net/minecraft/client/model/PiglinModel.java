/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;

public class PiglinModel<T extends Mob>
extends PlayerModel<T> {
    public final ModelPart f_170807_;
    private final ModelPart f_170808_;
    private final PartPose f_103337_;
    private final PartPose f_103338_;
    private final PartPose f_103333_;
    private final PartPose f_103334_;

    public PiglinModel(ModelPart p_170810_) {
        super(p_170810_, false);
        this.f_170807_ = this.f_102808_.m_171324_("right_ear");
        this.f_170808_ = this.f_102808_.m_171324_("left_ear");
        this.f_103337_ = this.f_102810_.m_171308_();
        this.f_103338_ = this.f_102808_.m_171308_();
        this.f_103333_ = this.f_102812_.m_171308_();
        this.f_103334_ = this.f_102811_.m_171308_();
    }

    public static MeshDefinition m_170811_(CubeDeformation p_170812_) {
        MeshDefinition $$1 = PlayerModel.m_170825_(p_170812_, false);
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, p_170812_), PartPose.f_171404_);
        PartDefinition $$3 = $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0f, -8.0f, -4.0f, 10.0f, 8.0f, 8.0f, p_170812_).m_171514_(31, 1).m_171488_(-2.0f, -4.0f, -5.0f, 4.0f, 4.0f, 1.0f, p_170812_).m_171514_(2, 4).m_171488_(2.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, p_170812_).m_171514_(2, 0).m_171488_(-3.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, p_170812_), PartPose.f_171404_);
        $$3.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(51, 6).m_171488_(0.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, p_170812_), PartPose.m_171423_(4.5f, -6.0f, 0.0f, 0.0f, 0.0f, -0.5235988f));
        $$3.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(39, 6).m_171488_(-1.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, p_170812_), PartPose.m_171423_(-4.5f, -6.0f, 0.0f, 0.0f, 0.0f, 0.5235988f));
        $$2.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.f_171404_);
        return $$1;
    }

    @Override
    public void m_6973_(T p_103366_, float p_103367_, float p_103368_, float p_103369_, float p_103370_, float p_103371_) {
        this.f_102810_.m_171322_(this.f_103337_);
        this.f_102808_.m_171322_(this.f_103338_);
        this.f_102812_.m_171322_(this.f_103333_);
        this.f_102811_.m_171322_(this.f_103334_);
        super.m_6973_(p_103366_, p_103367_, p_103368_, p_103369_, p_103370_, p_103371_);
        float $$6 = 0.5235988f;
        float $$7 = p_103369_ * 0.1f + p_103367_ * 0.5f;
        float $$8 = 0.08f + p_103368_ * 0.4f;
        this.f_170808_.f_104205_ = -0.5235988f - Mth.m_14089_($$7 * 1.2f) * $$8;
        this.f_170807_.f_104205_ = 0.5235988f + Mth.m_14089_($$7) * $$8;
        if (p_103366_ instanceof AbstractPiglin) {
            AbstractPiglin $$9 = (AbstractPiglin)p_103366_;
            PiglinArmPose $$10 = $$9.m_6389_();
            if ($$10 == PiglinArmPose.DANCING) {
                float $$11 = p_103369_ / 60.0f;
                this.f_170807_.f_104205_ = 0.5235988f + (float)Math.PI / 180 * Mth.m_14031_($$11 * 30.0f) * 10.0f;
                this.f_170808_.f_104205_ = -0.5235988f - (float)Math.PI / 180 * Mth.m_14089_($$11 * 30.0f) * 10.0f;
                this.f_102808_.f_104200_ = Mth.m_14031_($$11 * 10.0f);
                this.f_102808_.f_104201_ = Mth.m_14031_($$11 * 40.0f) + 0.4f;
                this.f_102811_.f_104205_ = (float)Math.PI / 180 * (70.0f + Mth.m_14089_($$11 * 40.0f) * 10.0f);
                this.f_102812_.f_104205_ = this.f_102811_.f_104205_ * -1.0f;
                this.f_102811_.f_104201_ = Mth.m_14031_($$11 * 40.0f) * 0.5f + 1.5f;
                this.f_102812_.f_104201_ = Mth.m_14031_($$11 * 40.0f) * 0.5f + 1.5f;
                this.f_102810_.f_104201_ = Mth.m_14031_($$11 * 40.0f) * 0.35f;
            } else if ($$10 == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON && this.f_102608_ == 0.0f) {
                this.m_103360_(p_103366_);
            } else if ($$10 == PiglinArmPose.CROSSBOW_HOLD) {
                AnimationUtils.m_102097_(this.f_102811_, this.f_102812_, this.f_102808_, !((Mob)p_103366_).m_21526_());
            } else if ($$10 == PiglinArmPose.CROSSBOW_CHARGE) {
                AnimationUtils.m_102086_(this.f_102811_, this.f_102812_, p_103366_, !((Mob)p_103366_).m_21526_());
            } else if ($$10 == PiglinArmPose.ADMIRING_ITEM) {
                this.f_102808_.f_104203_ = 0.5f;
                this.f_102808_.f_104204_ = 0.0f;
                if (((Mob)p_103366_).m_21526_()) {
                    this.f_102811_.f_104204_ = -0.5f;
                    this.f_102811_.f_104203_ = -0.9f;
                } else {
                    this.f_102812_.f_104204_ = 0.5f;
                    this.f_102812_.f_104203_ = -0.9f;
                }
            }
        } else if (((Entity)p_103366_).m_6095_() == EntityType.f_20531_) {
            AnimationUtils.m_102102_(this.f_102812_, this.f_102811_, ((Mob)p_103366_).m_5912_(), this.f_102608_, p_103369_);
        }
        this.f_103376_.m_104315_(this.f_102814_);
        this.f_103377_.m_104315_(this.f_102813_);
        this.f_103374_.m_104315_(this.f_102812_);
        this.f_103375_.m_104315_(this.f_102811_);
        this.f_103378_.m_104315_(this.f_102810_);
        this.f_102809_.m_104315_(this.f_102808_);
    }

    @Override
    protected void m_7884_(T p_103363_, float p_103364_) {
        if (this.f_102608_ > 0.0f && p_103363_ instanceof Piglin && ((Piglin)p_103363_).m_6389_() == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON) {
            AnimationUtils.m_102091_(this.f_102811_, this.f_102812_, p_103363_, this.f_102608_, p_103364_);
            return;
        }
        super.m_7884_(p_103363_, p_103364_);
    }

    private void m_103360_(T p_103361_) {
        if (((Mob)p_103361_).m_21526_()) {
            this.f_102812_.f_104203_ = -1.8f;
        } else {
            this.f_102811_.f_104203_ = -1.8f;
        }
    }
}

