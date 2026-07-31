/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.PolarBear;

public class PolarBearModel<T extends PolarBear>
extends QuadrupedModel<T> {
    public PolarBearModel(ModelPart p_170829_) {
        super(p_170829_, true, 16.0f, 4.0f, 2.25f, 2.0f, 24);
    }

    public static LayerDefinition m_170830_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.5f, -3.0f, -3.0f, 7.0f, 7.0f, 7.0f).m_171514_(0, 44).m_171517_("mouth", -2.5f, 1.0f, -6.0f, 5.0f, 3.0f, 3.0f).m_171514_(26, 0).m_171517_("right_ear", -4.5f, -4.0f, -1.0f, 2.0f, 2.0f, 1.0f).m_171514_(26, 0).m_171480_().m_171517_("left_ear", 2.5f, -4.0f, -1.0f, 2.0f, 2.0f, 1.0f), PartPose.m_171419_(0.0f, 10.0f, -16.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(-5.0f, -13.0f, -7.0f, 14.0f, 14.0f, 11.0f).m_171514_(39, 0).m_171481_(-4.0f, -25.0f, -7.0f, 12.0f, 12.0f, 10.0f), PartPose.m_171423_(-2.0f, 9.0f, 12.0f, 1.5707964f, 0.0f, 0.0f));
        int $$2 = 10;
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(50, 22).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 8.0f);
        $$1.m_171599_("right_hind_leg", $$3, PartPose.m_171419_(-4.5f, 14.0f, 6.0f));
        $$1.m_171599_("left_hind_leg", $$3, PartPose.m_171419_(4.5f, 14.0f, 6.0f));
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(50, 40).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 6.0f);
        $$1.m_171599_("right_front_leg", $$4, PartPose.m_171419_(-3.5f, 14.0f, -8.0f));
        $$1.m_171599_("left_front_leg", $$4, PartPose.m_171419_(3.5f, 14.0f, -8.0f));
        return LayerDefinition.m_171565_($$0, 128, 64);
    }

    @Override
    public void m_6973_(T p_103429_, float p_103430_, float p_103431_, float p_103432_, float p_103433_, float p_103434_) {
        super.m_6973_(p_103429_, p_103430_, p_103431_, p_103432_, p_103433_, p_103434_);
        float $$6 = p_103432_ - (float)((PolarBear)p_103429_).f_19797_;
        float $$7 = ((PolarBear)p_103429_).m_29569_($$6);
        $$7 *= $$7;
        float $$8 = 1.0f - $$7;
        this.f_103493_.f_104203_ = 1.5707964f - $$7 * (float)Math.PI * 0.35f;
        this.f_103493_.f_104201_ = 9.0f * $$8 + 11.0f * $$7;
        this.f_170854_.f_104201_ = 14.0f * $$8 - 6.0f * $$7;
        this.f_170854_.f_104202_ = -8.0f * $$8 - 4.0f * $$7;
        this.f_170854_.f_104203_ -= $$7 * (float)Math.PI * 0.45f;
        this.f_170855_.f_104201_ = this.f_170854_.f_104201_;
        this.f_170855_.f_104202_ = this.f_170854_.f_104202_;
        this.f_170855_.f_104203_ -= $$7 * (float)Math.PI * 0.45f;
        if (this.f_102610_) {
            this.f_103492_.f_104201_ = 10.0f * $$8 - 9.0f * $$7;
            this.f_103492_.f_104202_ = -16.0f * $$8 - 7.0f * $$7;
        } else {
            this.f_103492_.f_104201_ = 10.0f * $$8 - 14.0f * $$7;
            this.f_103492_.f_104202_ = -16.0f * $$8 - 3.0f * $$7;
        }
        this.f_103492_.f_104203_ += $$7 * (float)Math.PI * 0.15f;
    }
}

