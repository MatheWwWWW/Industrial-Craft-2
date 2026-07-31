/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.Sheep;

public class SheepModel<T extends Sheep>
extends QuadrupedModel<T> {
    private float f_103672_;

    public SheepModel(ModelPart p_170903_) {
        super(p_170903_, false, 8.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    public static LayerDefinition m_170904_() {
        MeshDefinition $$0 = QuadrupedModel.m_170864_(12, CubeDeformation.f_171458_);
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -4.0f, -6.0f, 6.0f, 6.0f, 8.0f), PartPose.m_171419_(0.0f, 6.0f, -8.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(28, 8).m_171481_(-4.0f, -10.0f, -7.0f, 8.0f, 16.0f, 6.0f), PartPose.m_171423_(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6839_(T p_103687_, float p_103688_, float p_103689_, float p_103690_) {
        super.m_6839_(p_103687_, p_103688_, p_103689_, p_103690_);
        this.f_103492_.f_104201_ = 6.0f + ((Sheep)p_103687_).m_29880_(p_103690_) * 9.0f;
        this.f_103672_ = ((Sheep)p_103687_).m_29882_(p_103690_);
    }

    @Override
    public void m_6973_(T p_103692_, float p_103693_, float p_103694_, float p_103695_, float p_103696_, float p_103697_) {
        super.m_6973_(p_103692_, p_103693_, p_103694_, p_103695_, p_103696_, p_103697_);
        this.f_103492_.f_104203_ = this.f_103672_;
    }
}

