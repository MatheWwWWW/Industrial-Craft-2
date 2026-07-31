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
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class QuadrupedModel<T extends Entity>
extends AgeableListModel<T> {
    protected final ModelPart f_103492_;
    protected final ModelPart f_103493_;
    protected final ModelPart f_170852_;
    protected final ModelPart f_170853_;
    protected final ModelPart f_170854_;
    protected final ModelPart f_170855_;

    protected QuadrupedModel(ModelPart p_170857_, boolean p_170858_, float p_170859_, float p_170860_, float p_170861_, float p_170862_, int p_170863_) {
        super(p_170858_, p_170859_, p_170860_, p_170861_, p_170862_, p_170863_);
        this.f_103492_ = p_170857_.m_171324_("head");
        this.f_103493_ = p_170857_.m_171324_("body");
        this.f_170852_ = p_170857_.m_171324_("right_hind_leg");
        this.f_170853_ = p_170857_.m_171324_("left_hind_leg");
        this.f_170854_ = p_170857_.m_171324_("right_front_leg");
        this.f_170855_ = p_170857_.m_171324_("left_front_leg");
    }

    public static MeshDefinition m_170864_(int p_170865_, CubeDeformation p_170866_) {
        MeshDefinition $$2 = new MeshDefinition();
        PartDefinition $$3 = $$2.m_171576_();
        $$3.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, p_170866_), PartPose.m_171419_(0.0f, 18 - p_170865_, -6.0f));
        $$3.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(28, 8).m_171488_(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f, p_170866_), PartPose.m_171423_(0.0f, 17 - p_170865_, 2.0f, 1.5707964f, 0.0f, 0.0f));
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, p_170865_, 4.0f, p_170866_);
        $$3.m_171599_("right_hind_leg", $$4, PartPose.m_171419_(-3.0f, 24 - p_170865_, 7.0f));
        $$3.m_171599_("left_hind_leg", $$4, PartPose.m_171419_(3.0f, 24 - p_170865_, 7.0f));
        $$3.m_171599_("right_front_leg", $$4, PartPose.m_171419_(-3.0f, 24 - p_170865_, -5.0f));
        $$3.m_171599_("left_front_leg", $$4, PartPose.m_171419_(3.0f, 24 - p_170865_, -5.0f));
        return $$2;
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_103492_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_103493_, (Object)this.f_170852_, (Object)this.f_170853_, (Object)this.f_170854_, (Object)this.f_170855_);
    }

    @Override
    public void m_6973_(T p_103509_, float p_103510_, float p_103511_, float p_103512_, float p_103513_, float p_103514_) {
        this.f_103492_.f_104203_ = p_103514_ * ((float)Math.PI / 180);
        this.f_103492_.f_104204_ = p_103513_ * ((float)Math.PI / 180);
        this.f_170852_.f_104203_ = Mth.m_14089_(p_103510_ * 0.6662f) * 1.4f * p_103511_;
        this.f_170853_.f_104203_ = Mth.m_14089_(p_103510_ * 0.6662f + (float)Math.PI) * 1.4f * p_103511_;
        this.f_170854_.f_104203_ = Mth.m_14089_(p_103510_ * 0.6662f + (float)Math.PI) * 1.4f * p_103511_;
        this.f_170855_.f_104203_ = Mth.m_14089_(p_103510_ * 0.6662f) * 1.4f * p_103511_;
    }
}

