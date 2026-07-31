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

public class SheepFurModel<T extends Sheep>
extends QuadrupedModel<T> {
    private float f_103646_;

    public SheepFurModel(ModelPart p_170900_) {
        super(p_170900_, false, 8.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    public static LayerDefinition m_170901_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0f, -4.0f, -4.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.6f)), PartPose.m_171419_(0.0f, 6.0f, -8.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(28, 8).m_171488_(-4.0f, -10.0f, -7.0f, 8.0f, 16.0f, 6.0f, new CubeDeformation(1.75f)), PartPose.m_171423_(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        CubeListBuilder $$2 = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, new CubeDeformation(0.5f));
        $$1.m_171599_("right_hind_leg", $$2, PartPose.m_171419_(-3.0f, 12.0f, 7.0f));
        $$1.m_171599_("left_hind_leg", $$2, PartPose.m_171419_(3.0f, 12.0f, 7.0f));
        $$1.m_171599_("right_front_leg", $$2, PartPose.m_171419_(-3.0f, 12.0f, -5.0f));
        $$1.m_171599_("left_front_leg", $$2, PartPose.m_171419_(3.0f, 12.0f, -5.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6839_(T p_103661_, float p_103662_, float p_103663_, float p_103664_) {
        super.m_6839_(p_103661_, p_103662_, p_103663_, p_103664_);
        this.f_103492_.f_104201_ = 6.0f + ((Sheep)p_103661_).m_29880_(p_103664_) * 9.0f;
        this.f_103646_ = ((Sheep)p_103661_).m_29882_(p_103664_);
    }

    @Override
    public void m_6973_(T p_103666_, float p_103667_, float p_103668_, float p_103669_, float p_103670_, float p_103671_) {
        super.m_6973_(p_103666_, p_103667_, p_103668_, p_103669_, p_103670_, p_103671_);
        this.f_103492_.f_104203_ = this.f_103646_;
    }
}

