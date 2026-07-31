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
import net.minecraft.world.entity.Entity;

public class SlimeModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170953_;

    public SlimeModel(ModelPart p_170955_) {
        this.f_170953_ = p_170955_;
    }

    public static LayerDefinition m_170956_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("cube", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, 16.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    public static LayerDefinition m_170958_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("cube", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-3.0f, 17.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.f_171404_);
        $$1.m_171599_("right_eye", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-3.25f, 18.0f, -3.5f, 2.0f, 2.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_("left_eye", CubeListBuilder.m_171558_().m_171514_(32, 4).m_171481_(1.25f, 18.0f, -3.5f, 2.0f, 2.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(32, 8).m_171481_(0.0f, 21.0f, -3.5f, 1.0f, 1.0f, 1.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_103831_, float p_103832_, float p_103833_, float p_103834_, float p_103835_, float p_103836_) {
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170953_;
    }
}

