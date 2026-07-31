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

public class CreeperModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170517_;
    private final ModelPart f_102451_;
    private final ModelPart f_170518_;
    private final ModelPart f_170519_;
    private final ModelPart f_170520_;
    private final ModelPart f_170521_;
    private static final int f_170522_ = 6;

    public CreeperModel(ModelPart p_170524_) {
        this.f_170517_ = p_170524_;
        this.f_102451_ = p_170524_.m_171324_("head");
        this.f_170519_ = p_170524_.m_171324_("right_hind_leg");
        this.f_170518_ = p_170524_.m_171324_("left_hind_leg");
        this.f_170521_ = p_170524_.m_171324_("right_front_leg");
        this.f_170520_ = p_170524_.m_171324_("left_front_leg");
    }

    public static LayerDefinition m_170525_(CubeDeformation p_170526_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, p_170526_), PartPose.m_171419_(0.0f, 6.0f, 0.0f));
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, p_170526_), PartPose.m_171419_(0.0f, 6.0f, 0.0f));
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, p_170526_);
        $$2.m_171599_("right_hind_leg", $$3, PartPose.m_171419_(-2.0f, 18.0f, 4.0f));
        $$2.m_171599_("left_hind_leg", $$3, PartPose.m_171419_(2.0f, 18.0f, 4.0f));
        $$2.m_171599_("right_front_leg", $$3, PartPose.m_171419_(-2.0f, 18.0f, -4.0f));
        $$2.m_171599_("left_front_leg", $$3, PartPose.m_171419_(2.0f, 18.0f, -4.0f));
        return LayerDefinition.m_171565_($$1, 64, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170517_;
    }

    @Override
    public void m_6973_(T p_102463_, float p_102464_, float p_102465_, float p_102466_, float p_102467_, float p_102468_) {
        this.f_102451_.f_104204_ = p_102467_ * ((float)Math.PI / 180);
        this.f_102451_.f_104203_ = p_102468_ * ((float)Math.PI / 180);
        this.f_170518_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662f) * 1.4f * p_102465_;
        this.f_170519_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662f + (float)Math.PI) * 1.4f * p_102465_;
        this.f_170520_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662f + (float)Math.PI) * 1.4f * p_102465_;
        this.f_170521_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662f) * 1.4f * p_102465_;
    }
}

