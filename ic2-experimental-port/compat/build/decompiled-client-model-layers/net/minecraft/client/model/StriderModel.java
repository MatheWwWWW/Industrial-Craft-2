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
import net.minecraft.world.entity.monster.Strider;

public class StriderModel<T extends Strider>
extends HierarchicalModel<T> {
    private static final String f_170997_ = "right_bottom_bristle";
    private static final String f_170998_ = "right_middle_bristle";
    private static final String f_170999_ = "right_top_bristle";
    private static final String f_171000_ = "left_top_bristle";
    private static final String f_171001_ = "left_middle_bristle";
    private static final String f_171002_ = "left_bottom_bristle";
    private final ModelPart f_171003_;
    private final ModelPart f_103884_;
    private final ModelPart f_103885_;
    private final ModelPart f_103886_;
    private final ModelPart f_171004_;
    private final ModelPart f_171005_;
    private final ModelPart f_171006_;
    private final ModelPart f_171007_;
    private final ModelPart f_171008_;
    private final ModelPart f_171009_;

    public StriderModel(ModelPart p_171011_) {
        this.f_171003_ = p_171011_;
        this.f_103884_ = p_171011_.m_171324_("right_leg");
        this.f_103885_ = p_171011_.m_171324_("left_leg");
        this.f_103886_ = p_171011_.m_171324_("body");
        this.f_171004_ = this.f_103886_.m_171324_(f_170997_);
        this.f_171005_ = this.f_103886_.m_171324_(f_170998_);
        this.f_171006_ = this.f_103886_.m_171324_(f_170999_);
        this.f_171007_ = this.f_103886_.m_171324_(f_171000_);
        this.f_171008_ = this.f_103886_.m_171324_(f_171001_);
        this.f_171009_ = this.f_103886_.m_171324_(f_171002_);
    }

    public static LayerDefinition m_171012_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 16.0f, 4.0f), PartPose.m_171419_(-4.0f, 8.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 16.0f, 4.0f), PartPose.m_171419_(4.0f, 8.0f, 0.0f));
        PartDefinition $$2 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -6.0f, -8.0f, 16.0f, 14.0f, 16.0f), PartPose.m_171419_(0.0f, 1.0f, 0.0f));
        $$2.m_171599_(f_170997_, CubeListBuilder.m_171558_().m_171514_(16, 65).m_171506_(-12.0f, 0.0f, 0.0f, 12.0f, 0.0f, 16.0f, true), PartPose.m_171423_(-8.0f, 4.0f, -8.0f, 0.0f, 0.0f, -1.2217305f));
        $$2.m_171599_(f_170998_, CubeListBuilder.m_171558_().m_171514_(16, 49).m_171506_(-12.0f, 0.0f, 0.0f, 12.0f, 0.0f, 16.0f, true), PartPose.m_171423_(-8.0f, -1.0f, -8.0f, 0.0f, 0.0f, -1.134464f));
        $$2.m_171599_(f_170999_, CubeListBuilder.m_171558_().m_171514_(16, 33).m_171506_(-12.0f, 0.0f, 0.0f, 12.0f, 0.0f, 16.0f, true), PartPose.m_171423_(-8.0f, -5.0f, -8.0f, 0.0f, 0.0f, -0.87266463f));
        $$2.m_171599_(f_171000_, CubeListBuilder.m_171558_().m_171514_(16, 33).m_171481_(0.0f, 0.0f, 0.0f, 12.0f, 0.0f, 16.0f), PartPose.m_171423_(8.0f, -6.0f, -8.0f, 0.0f, 0.0f, 0.87266463f));
        $$2.m_171599_(f_171001_, CubeListBuilder.m_171558_().m_171514_(16, 49).m_171481_(0.0f, 0.0f, 0.0f, 12.0f, 0.0f, 16.0f), PartPose.m_171423_(8.0f, -2.0f, -8.0f, 0.0f, 0.0f, 1.134464f));
        $$2.m_171599_(f_171002_, CubeListBuilder.m_171558_().m_171514_(16, 65).m_171481_(0.0f, 0.0f, 0.0f, 12.0f, 0.0f, 16.0f), PartPose.m_171423_(8.0f, 3.0f, -8.0f, 0.0f, 0.0f, 1.2217305f));
        return LayerDefinition.m_171565_($$0, 64, 128);
    }

    @Override
    public void m_6973_(Strider p_103903_, float p_103904_, float p_103905_, float p_103906_, float p_103907_, float p_103908_) {
        p_103905_ = Math.min(0.25f, p_103905_);
        if (!p_103903_.m_20160_()) {
            this.f_103886_.f_104203_ = p_103908_ * ((float)Math.PI / 180);
            this.f_103886_.f_104204_ = p_103907_ * ((float)Math.PI / 180);
        } else {
            this.f_103886_.f_104203_ = 0.0f;
            this.f_103886_.f_104204_ = 0.0f;
        }
        float $$6 = 1.5f;
        this.f_103886_.f_104205_ = 0.1f * Mth.m_14031_(p_103904_ * 1.5f) * 4.0f * p_103905_;
        this.f_103886_.f_104201_ = 2.0f;
        this.f_103886_.f_104201_ -= 2.0f * Mth.m_14089_(p_103904_ * 1.5f) * 2.0f * p_103905_;
        this.f_103885_.f_104203_ = Mth.m_14031_(p_103904_ * 1.5f * 0.5f) * 2.0f * p_103905_;
        this.f_103884_.f_104203_ = Mth.m_14031_(p_103904_ * 1.5f * 0.5f + (float)Math.PI) * 2.0f * p_103905_;
        this.f_103885_.f_104205_ = 0.17453292f * Mth.m_14089_(p_103904_ * 1.5f * 0.5f) * p_103905_;
        this.f_103884_.f_104205_ = 0.17453292f * Mth.m_14089_(p_103904_ * 1.5f * 0.5f + (float)Math.PI) * p_103905_;
        this.f_103885_.f_104201_ = 8.0f + 2.0f * Mth.m_14031_(p_103904_ * 1.5f * 0.5f + (float)Math.PI) * 2.0f * p_103905_;
        this.f_103884_.f_104201_ = 8.0f + 2.0f * Mth.m_14031_(p_103904_ * 1.5f * 0.5f) * 2.0f * p_103905_;
        this.f_171004_.f_104205_ = -1.2217305f;
        this.f_171005_.f_104205_ = -1.134464f;
        this.f_171006_.f_104205_ = -0.87266463f;
        this.f_171007_.f_104205_ = 0.87266463f;
        this.f_171008_.f_104205_ = 1.134464f;
        this.f_171009_.f_104205_ = 1.2217305f;
        float $$7 = Mth.m_14089_(p_103904_ * 1.5f + (float)Math.PI) * p_103905_;
        this.f_171004_.f_104205_ += $$7 * 1.3f;
        this.f_171005_.f_104205_ += $$7 * 1.2f;
        this.f_171006_.f_104205_ += $$7 * 0.6f;
        this.f_171007_.f_104205_ += $$7 * 0.6f;
        this.f_171008_.f_104205_ += $$7 * 1.2f;
        this.f_171009_.f_104205_ += $$7 * 1.3f;
        float $$8 = 1.0f;
        float $$9 = 1.0f;
        this.f_171004_.f_104205_ += 0.05f * Mth.m_14031_(p_103906_ * 1.0f * -0.4f);
        this.f_171005_.f_104205_ += 0.1f * Mth.m_14031_(p_103906_ * 1.0f * 0.2f);
        this.f_171006_.f_104205_ += 0.1f * Mth.m_14031_(p_103906_ * 1.0f * 0.4f);
        this.f_171007_.f_104205_ += 0.1f * Mth.m_14031_(p_103906_ * 1.0f * 0.4f);
        this.f_171008_.f_104205_ += 0.1f * Mth.m_14031_(p_103906_ * 1.0f * 0.2f);
        this.f_171009_.f_104205_ += 0.05f * Mth.m_14031_(p_103906_ * 1.0f * -0.4f);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_171003_;
    }
}

