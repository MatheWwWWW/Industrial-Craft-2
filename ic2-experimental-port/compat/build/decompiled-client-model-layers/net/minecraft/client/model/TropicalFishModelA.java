/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class TropicalFishModelA<T extends Entity>
extends ColorableHierarchicalModel<T> {
    private final ModelPart f_171018_;
    private final ModelPart f_103953_;

    public TropicalFishModelA(ModelPart p_171020_) {
        this.f_171018_ = p_171020_;
        this.f_103953_ = p_171020_.m_171324_("tail");
    }

    public static LayerDefinition m_171021_(CubeDeformation p_171022_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        int $$3 = 22;
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0f, -1.5f, -3.0f, 2.0f, 3.0f, 6.0f, p_171022_), PartPose.m_171419_(0.0f, 22.0f, 0.0f));
        $$2.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(22, -6).m_171488_(0.0f, -1.5f, 0.0f, 0.0f, 3.0f, 6.0f, p_171022_), PartPose.m_171419_(0.0f, 22.0f, 3.0f));
        $$2.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(2, 16).m_171488_(-2.0f, -1.0f, 0.0f, 2.0f, 2.0f, 0.0f, p_171022_), PartPose.m_171423_(-1.0f, 22.5f, 0.0f, 0.0f, 0.7853982f, 0.0f));
        $$2.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(2, 12).m_171488_(0.0f, -1.0f, 0.0f, 2.0f, 2.0f, 0.0f, p_171022_), PartPose.m_171423_(1.0f, 22.5f, 0.0f, 0.0f, -0.7853982f, 0.0f));
        $$2.m_171599_("top_fin", CubeListBuilder.m_171558_().m_171514_(10, -5).m_171488_(0.0f, -3.0f, 0.0f, 0.0f, 3.0f, 6.0f, p_171022_), PartPose.m_171419_(0.0f, 20.5f, -3.0f));
        return LayerDefinition.m_171565_($$1, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_171018_;
    }

    @Override
    public void m_6973_(T p_103961_, float p_103962_, float p_103963_, float p_103964_, float p_103965_, float p_103966_) {
        float $$6 = 1.0f;
        if (!((Entity)p_103961_).m_20069_()) {
            $$6 = 1.5f;
        }
        this.f_103953_.f_104204_ = -$$6 * 0.45f * Mth.m_14031_(0.6f * p_103964_);
    }
}

