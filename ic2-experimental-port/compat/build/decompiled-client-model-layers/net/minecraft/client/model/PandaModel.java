/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Panda;

public class PandaModel<T extends Panda>
extends QuadrupedModel<T> {
    private float f_103154_;
    private float f_103155_;
    private float f_103156_;

    public PandaModel(ModelPart p_170771_) {
        super(p_170771_, true, 23.0f, 4.8f, 2.7f, 3.0f, 49);
    }

    public static LayerDefinition m_170772_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171481_(-6.5f, -5.0f, -4.0f, 13.0f, 10.0f, 9.0f).m_171514_(45, 16).m_171517_("nose", -3.5f, 0.0f, -6.0f, 7.0f, 5.0f, 2.0f).m_171514_(52, 25).m_171517_("left_ear", 3.5f, -8.0f, -1.0f, 5.0f, 4.0f, 1.0f).m_171514_(52, 25).m_171517_("right_ear", -8.5f, -8.0f, -1.0f, 5.0f, 4.0f, 1.0f), PartPose.m_171419_(0.0f, 11.5f, -17.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171481_(-9.5f, -13.0f, -6.5f, 19.0f, 26.0f, 13.0f), PartPose.m_171423_(0.0f, 10.0f, 0.0f, 1.5707964f, 0.0f, 0.0f));
        int $$2 = 9;
        int $$3 = 6;
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(-3.0f, 0.0f, -3.0f, 6.0f, 9.0f, 6.0f);
        $$1.m_171599_("right_hind_leg", $$4, PartPose.m_171419_(-5.5f, 15.0f, 9.0f));
        $$1.m_171599_("left_hind_leg", $$4, PartPose.m_171419_(5.5f, 15.0f, 9.0f));
        $$1.m_171599_("right_front_leg", $$4, PartPose.m_171419_(-5.5f, 15.0f, -9.0f));
        $$1.m_171599_("left_front_leg", $$4, PartPose.m_171419_(5.5f, 15.0f, -9.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6839_(T p_103173_, float p_103174_, float p_103175_, float p_103176_) {
        super.m_6839_(p_103173_, p_103174_, p_103175_, p_103176_);
        this.f_103154_ = ((Panda)p_103173_).m_29224_(p_103176_);
        this.f_103155_ = ((Panda)p_103173_).m_29226_(p_103176_);
        this.f_103156_ = ((AgeableMob)p_103173_).m_6162_() ? 0.0f : ((Panda)p_103173_).m_29088_(p_103176_);
    }

    @Override
    public void m_6973_(T p_103178_, float p_103179_, float p_103180_, float p_103181_, float p_103182_, float p_103183_) {
        super.m_6973_(p_103178_, p_103179_, p_103180_, p_103181_, p_103182_, p_103183_);
        boolean $$6 = ((Panda)p_103178_).m_29148_() > 0;
        boolean $$7 = ((Panda)p_103178_).m_29149_();
        int $$8 = ((Panda)p_103178_).m_29153_();
        boolean $$9 = ((Panda)p_103178_).m_29152_();
        boolean $$10 = ((Panda)p_103178_).m_29165_();
        if ($$6) {
            this.f_103492_.f_104204_ = 0.35f * Mth.m_14031_(0.6f * p_103181_);
            this.f_103492_.f_104205_ = 0.35f * Mth.m_14031_(0.6f * p_103181_);
            this.f_170854_.f_104203_ = -0.75f * Mth.m_14031_(0.3f * p_103181_);
            this.f_170855_.f_104203_ = 0.75f * Mth.m_14031_(0.3f * p_103181_);
        } else {
            this.f_103492_.f_104205_ = 0.0f;
        }
        if ($$7) {
            if ($$8 < 15) {
                this.f_103492_.f_104203_ = -0.7853982f * (float)$$8 / 14.0f;
            } else if ($$8 < 20) {
                float $$11 = ($$8 - 15) / 5;
                this.f_103492_.f_104203_ = -0.7853982f + 0.7853982f * $$11;
            }
        }
        if (this.f_103154_ > 0.0f) {
            this.f_103493_.f_104203_ = ModelUtils.m_103125_(this.f_103493_.f_104203_, 1.7407963f, this.f_103154_);
            this.f_103492_.f_104203_ = ModelUtils.m_103125_(this.f_103492_.f_104203_, 1.5707964f, this.f_103154_);
            this.f_170854_.f_104205_ = -0.27079642f;
            this.f_170855_.f_104205_ = 0.27079642f;
            this.f_170852_.f_104205_ = 0.5707964f;
            this.f_170853_.f_104205_ = -0.5707964f;
            if ($$9) {
                this.f_103492_.f_104203_ = 1.5707964f + 0.2f * Mth.m_14031_(p_103181_ * 0.6f);
                this.f_170854_.f_104203_ = -0.4f - 0.2f * Mth.m_14031_(p_103181_ * 0.6f);
                this.f_170855_.f_104203_ = -0.4f - 0.2f * Mth.m_14031_(p_103181_ * 0.6f);
            }
            if ($$10) {
                this.f_103492_.f_104203_ = 2.1707964f;
                this.f_170854_.f_104203_ = -0.9f;
                this.f_170855_.f_104203_ = -0.9f;
            }
        } else {
            this.f_170852_.f_104205_ = 0.0f;
            this.f_170853_.f_104205_ = 0.0f;
            this.f_170854_.f_104205_ = 0.0f;
            this.f_170855_.f_104205_ = 0.0f;
        }
        if (this.f_103155_ > 0.0f) {
            this.f_170852_.f_104203_ = -0.6f * Mth.m_14031_(p_103181_ * 0.15f);
            this.f_170853_.f_104203_ = 0.6f * Mth.m_14031_(p_103181_ * 0.15f);
            this.f_170854_.f_104203_ = 0.3f * Mth.m_14031_(p_103181_ * 0.25f);
            this.f_170855_.f_104203_ = -0.3f * Mth.m_14031_(p_103181_ * 0.25f);
            this.f_103492_.f_104203_ = ModelUtils.m_103125_(this.f_103492_.f_104203_, 1.5707964f, this.f_103155_);
        }
        if (this.f_103156_ > 0.0f) {
            this.f_103492_.f_104203_ = ModelUtils.m_103125_(this.f_103492_.f_104203_, 2.0561945f, this.f_103156_);
            this.f_170852_.f_104203_ = -0.5f * Mth.m_14031_(p_103181_ * 0.5f);
            this.f_170853_.f_104203_ = 0.5f * Mth.m_14031_(p_103181_ * 0.5f);
            this.f_170854_.f_104203_ = 0.5f * Mth.m_14031_(p_103181_ * 0.5f);
            this.f_170855_.f_104203_ = -0.5f * Mth.m_14031_(p_103181_ * 0.5f);
        }
    }
}

