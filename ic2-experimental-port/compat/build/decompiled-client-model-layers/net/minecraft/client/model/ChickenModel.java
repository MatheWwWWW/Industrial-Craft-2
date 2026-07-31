/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ChickenModel<T extends Entity>
extends AgeableListModel<T> {
    public static final String f_170484_ = "red_thing";
    private final ModelPart f_102381_;
    private final ModelPart f_102382_;
    private final ModelPart f_170485_;
    private final ModelPart f_170486_;
    private final ModelPart f_170487_;
    private final ModelPart f_170488_;
    private final ModelPart f_102387_;
    private final ModelPart f_102388_;

    public ChickenModel(ModelPart p_170490_) {
        this.f_102381_ = p_170490_.m_171324_("head");
        this.f_102387_ = p_170490_.m_171324_("beak");
        this.f_102388_ = p_170490_.m_171324_(f_170484_);
        this.f_102382_ = p_170490_.m_171324_("body");
        this.f_170485_ = p_170490_.m_171324_("right_leg");
        this.f_170486_ = p_170490_.m_171324_("left_leg");
        this.f_170487_ = p_170490_.m_171324_("right_wing");
        this.f_170488_ = p_170490_.m_171324_("left_wing");
    }

    public static LayerDefinition m_170491_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 16;
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-2.0f, -6.0f, -2.0f, 4.0f, 6.0f, 3.0f), PartPose.m_171419_(0.0f, 15.0f, -4.0f));
        $$1.m_171599_("beak", CubeListBuilder.m_171558_().m_171514_(14, 0).m_171481_(-2.0f, -4.0f, -4.0f, 4.0f, 2.0f, 2.0f), PartPose.m_171419_(0.0f, 15.0f, -4.0f));
        $$1.m_171599_(f_170484_, CubeListBuilder.m_171558_().m_171514_(14, 4).m_171481_(-1.0f, -2.0f, -3.0f, 2.0f, 2.0f, 2.0f), PartPose.m_171419_(0.0f, 15.0f, -4.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171481_(-3.0f, -4.0f, -3.0f, 6.0f, 8.0f, 6.0f), PartPose.m_171423_(0.0f, 16.0f, 0.0f, 1.5707964f, 0.0f, 0.0f));
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(26, 0).m_171481_(-1.0f, 0.0f, -3.0f, 3.0f, 5.0f, 3.0f);
        $$1.m_171599_("right_leg", $$3, PartPose.m_171419_(-2.0f, 19.0f, 1.0f));
        $$1.m_171599_("left_leg", $$3, PartPose.m_171419_(1.0f, 19.0f, 1.0f));
        $$1.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(24, 13).m_171481_(0.0f, 0.0f, -3.0f, 1.0f, 4.0f, 6.0f), PartPose.m_171419_(-4.0f, 13.0f, 0.0f));
        $$1.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(24, 13).m_171481_(-1.0f, 0.0f, -3.0f, 1.0f, 4.0f, 6.0f), PartPose.m_171419_(4.0f, 13.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_102381_, (Object)this.f_102387_, (Object)this.f_102388_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102382_, (Object)this.f_170485_, (Object)this.f_170486_, (Object)this.f_170487_, (Object)this.f_170488_);
    }

    @Override
    public void m_6973_(T p_102392_, float p_102393_, float p_102394_, float p_102395_, float p_102396_, float p_102397_) {
        this.f_102381_.f_104203_ = p_102397_ * ((float)Math.PI / 180);
        this.f_102381_.f_104204_ = p_102396_ * ((float)Math.PI / 180);
        this.f_102387_.f_104203_ = this.f_102381_.f_104203_;
        this.f_102387_.f_104204_ = this.f_102381_.f_104204_;
        this.f_102388_.f_104203_ = this.f_102381_.f_104203_;
        this.f_102388_.f_104204_ = this.f_102381_.f_104204_;
        this.f_170485_.f_104203_ = Mth.m_14089_(p_102393_ * 0.6662f) * 1.4f * p_102394_;
        this.f_170486_.f_104203_ = Mth.m_14089_(p_102393_ * 0.6662f + (float)Math.PI) * 1.4f * p_102394_;
        this.f_170487_.f_104205_ = p_102395_;
        this.f_170488_.f_104205_ = -p_102395_;
    }
}

