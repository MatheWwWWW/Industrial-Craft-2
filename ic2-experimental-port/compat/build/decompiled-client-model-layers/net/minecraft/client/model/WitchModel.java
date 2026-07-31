/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class WitchModel<T extends Entity>
extends VillagerModel<T> {
    private boolean f_104062_;

    public WitchModel(ModelPart p_171055_) {
        super(p_171055_);
    }

    public static LayerDefinition m_171056_() {
        MeshDefinition $$0 = VillagerModel.m_171052_();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.f_171404_);
        PartDefinition $$3 = $$2.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(0, 64).m_171481_(0.0f, 0.0f, 0.0f, 10.0f, 2.0f, 10.0f), PartPose.m_171419_(-5.0f, -10.03125f, -5.0f));
        PartDefinition $$4 = $$3.m_171599_("hat2", CubeListBuilder.m_171558_().m_171514_(0, 76).m_171481_(0.0f, 0.0f, 0.0f, 7.0f, 4.0f, 7.0f), PartPose.m_171423_(1.75f, -4.0f, 2.0f, -0.05235988f, 0.0f, 0.02617994f));
        PartDefinition $$5 = $$4.m_171599_("hat3", CubeListBuilder.m_171558_().m_171514_(0, 87).m_171481_(0.0f, 0.0f, 0.0f, 4.0f, 4.0f, 4.0f), PartPose.m_171423_(1.75f, -4.0f, 2.0f, -0.10471976f, 0.0f, 0.05235988f));
        $$5.m_171599_("hat4", CubeListBuilder.m_171558_().m_171514_(0, 95).m_171488_(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f, new CubeDeformation(0.25f)), PartPose.m_171423_(1.75f, -2.0f, 2.0f, -0.20943952f, 0.0f, 0.10471976f));
        PartDefinition $$6 = $$2.m_171597_("nose");
        $$6.m_171599_("mole", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0f, 3.0f, -6.75f, 1.0f, 1.0f, 1.0f, new CubeDeformation(-0.25f)), PartPose.m_171419_(0.0f, -2.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 128);
    }

    @Override
    public void m_6973_(T p_104067_, float p_104068_, float p_104069_, float p_104070_, float p_104071_, float p_104072_) {
        super.m_6973_(p_104067_, p_104068_, p_104069_, p_104070_, p_104071_, p_104072_);
        this.f_104044_.m_104227_(0.0f, -2.0f, 0.0f);
        float $$6 = 0.01f * (float)(((Entity)p_104067_).m_19879_() % 10);
        this.f_104044_.f_104203_ = Mth.m_14031_((float)((Entity)p_104067_).f_19797_ * $$6) * 4.5f * ((float)Math.PI / 180);
        this.f_104044_.f_104204_ = 0.0f;
        this.f_104044_.f_104205_ = Mth.m_14089_((float)((Entity)p_104067_).f_19797_ * $$6) * 2.5f * ((float)Math.PI / 180);
        if (this.f_104062_) {
            this.f_104044_.m_104227_(0.0f, 1.0f, -1.5f);
            this.f_104044_.f_104203_ = -0.9f;
        }
    }

    public ModelPart m_104073_() {
        return this.f_104044_;
    }

    public void m_104074_(boolean p_104075_) {
        this.f_104062_ = p_104075_;
    }
}

