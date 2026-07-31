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
import net.minecraft.world.entity.boss.wither.WitherBoss;

public class WitherBossModel<T extends WitherBoss>
extends HierarchicalModel<T> {
    private static final String f_171057_ = "ribcage";
    private static final String f_171058_ = "center_head";
    private static final String f_171059_ = "right_head";
    private static final String f_171060_ = "left_head";
    private static final float f_171061_ = 0.065f;
    private static final float f_171062_ = 0.265f;
    private final ModelPart f_171063_;
    private final ModelPart f_171064_;
    private final ModelPart f_171065_;
    private final ModelPart f_171066_;
    private final ModelPart f_171067_;
    private final ModelPart f_171068_;

    public WitherBossModel(ModelPart p_171070_) {
        this.f_171063_ = p_171070_;
        this.f_171067_ = p_171070_.m_171324_(f_171057_);
        this.f_171068_ = p_171070_.m_171324_("tail");
        this.f_171064_ = p_171070_.m_171324_(f_171058_);
        this.f_171065_ = p_171070_.m_171324_(f_171059_);
        this.f_171066_ = p_171070_.m_171324_(f_171060_);
    }

    public static LayerDefinition m_171075_(CubeDeformation p_171076_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("shoulders", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-10.0f, 3.9f, -0.5f, 20.0f, 3.0f, 3.0f, p_171076_), PartPose.f_171404_);
        float $$3 = 0.20420352f;
        $$2.m_171599_(f_171057_, CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(0.0f, 0.0f, 0.0f, 3.0f, 10.0f, 3.0f, p_171076_).m_171514_(24, 22).m_171488_(-4.0f, 1.5f, 0.5f, 11.0f, 2.0f, 2.0f, p_171076_).m_171514_(24, 22).m_171488_(-4.0f, 4.0f, 0.5f, 11.0f, 2.0f, 2.0f, p_171076_).m_171514_(24, 22).m_171488_(-4.0f, 6.5f, 0.5f, 11.0f, 2.0f, 2.0f, p_171076_), PartPose.m_171423_(-2.0f, 6.9f, -0.5f, 0.20420352f, 0.0f, 0.0f));
        $$2.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(12, 22).m_171488_(0.0f, 0.0f, 0.0f, 3.0f, 6.0f, 3.0f, p_171076_), PartPose.m_171423_(-2.0f, 6.9f + Mth.m_14089_(0.20420352f) * 10.0f, -0.5f + Mth.m_14031_(0.20420352f) * 10.0f, 0.83252203f, 0.0f, 0.0f));
        $$2.m_171599_(f_171058_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f, p_171076_), PartPose.f_171404_);
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -4.0f, -4.0f, 6.0f, 6.0f, 6.0f, p_171076_);
        $$2.m_171599_(f_171059_, $$4, PartPose.m_171419_(-8.0f, 4.0f, 0.0f));
        $$2.m_171599_(f_171060_, $$4, PartPose.m_171419_(10.0f, 4.0f, 0.0f));
        return LayerDefinition.m_171565_($$1, 64, 64);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_171063_;
    }

    @Override
    public void m_6973_(T p_104100_, float p_104101_, float p_104102_, float p_104103_, float p_104104_, float p_104105_) {
        float $$6 = Mth.m_14089_(p_104103_ * 0.1f);
        this.f_171067_.f_104203_ = (0.065f + 0.05f * $$6) * (float)Math.PI;
        this.f_171068_.m_104227_(-2.0f, 6.9f + Mth.m_14089_(this.f_171067_.f_104203_) * 10.0f, -0.5f + Mth.m_14031_(this.f_171067_.f_104203_) * 10.0f);
        this.f_171068_.f_104203_ = (0.265f + 0.1f * $$6) * (float)Math.PI;
        this.f_171064_.f_104204_ = p_104104_ * ((float)Math.PI / 180);
        this.f_171064_.f_104203_ = p_104105_ * ((float)Math.PI / 180);
    }

    @Override
    public void m_6839_(T p_104095_, float p_104096_, float p_104097_, float p_104098_) {
        WitherBossModel.m_171071_(p_104095_, this.f_171065_, 0);
        WitherBossModel.m_171071_(p_104095_, this.f_171066_, 1);
    }

    private static <T extends WitherBoss> void m_171071_(T p_171072_, ModelPart p_171073_, int p_171074_) {
        p_171073_.f_104204_ = (p_171072_.m_31446_(p_171074_) - p_171072_.f_20883_) * ((float)Math.PI / 180);
        p_171073_.f_104203_ = p_171072_.m_31480_(p_171074_) * ((float)Math.PI / 180);
    }
}

