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
import net.minecraft.world.entity.Entity;

public class SnowGolemModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170959_ = "upper_body";
    private final ModelPart f_170960_;
    private final ModelPart f_170961_;
    private final ModelPart f_103839_;
    private final ModelPart f_170962_;
    private final ModelPart f_170963_;

    public SnowGolemModel(ModelPart p_170965_) {
        this.f_170960_ = p_170965_;
        this.f_103839_ = p_170965_.m_171324_("head");
        this.f_170962_ = p_170965_.m_171324_("left_arm");
        this.f_170963_ = p_170965_.m_171324_("right_arm");
        this.f_170961_ = p_170965_.m_171324_(f_170959_);
    }

    public static LayerDefinition m_170966_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = 4.0f;
        CubeDeformation $$3 = new CubeDeformation(-0.5f);
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, $$3), PartPose.m_171419_(0.0f, 4.0f, 0.0f));
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-1.0f, 0.0f, -1.0f, 12.0f, 2.0f, 2.0f, $$3);
        $$1.m_171599_("left_arm", $$4, PartPose.m_171423_(5.0f, 6.0f, 1.0f, 0.0f, 0.0f, 1.0f));
        $$1.m_171599_("right_arm", $$4, PartPose.m_171423_(-5.0f, 6.0f, -1.0f, 0.0f, (float)Math.PI, -1.0f));
        $$1.m_171599_(f_170959_, CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-5.0f, -10.0f, -5.0f, 10.0f, 10.0f, 10.0f, $$3), PartPose.m_171419_(0.0f, 13.0f, 0.0f));
        $$1.m_171599_("lower_body", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-6.0f, -12.0f, -6.0f, 12.0f, 12.0f, 12.0f, $$3), PartPose.m_171419_(0.0f, 24.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6973_(T p_103845_, float p_103846_, float p_103847_, float p_103848_, float p_103849_, float p_103850_) {
        this.f_103839_.f_104204_ = p_103849_ * ((float)Math.PI / 180);
        this.f_103839_.f_104203_ = p_103850_ * ((float)Math.PI / 180);
        this.f_170961_.f_104204_ = p_103849_ * ((float)Math.PI / 180) * 0.25f;
        float $$6 = Mth.m_14031_(this.f_170961_.f_104204_);
        float $$7 = Mth.m_14089_(this.f_170961_.f_104204_);
        this.f_170962_.f_104204_ = this.f_170961_.f_104204_;
        this.f_170963_.f_104204_ = this.f_170961_.f_104204_ + (float)Math.PI;
        this.f_170962_.f_104200_ = $$7 * 5.0f;
        this.f_170962_.f_104202_ = -$$6 * 5.0f;
        this.f_170963_.f_104200_ = -$$7 * 5.0f;
        this.f_170963_.f_104202_ = $$6 * 5.0f;
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170960_;
    }

    public ModelPart m_103851_() {
        return this.f_103839_;
    }
}

