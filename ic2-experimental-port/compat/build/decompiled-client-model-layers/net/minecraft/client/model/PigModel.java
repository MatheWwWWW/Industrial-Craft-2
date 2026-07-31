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
import net.minecraft.world.entity.Entity;

public class PigModel<T extends Entity>
extends QuadrupedModel<T> {
    public PigModel(ModelPart p_170799_) {
        super(p_170799_, false, 4.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    public static LayerDefinition m_170800_(CubeDeformation p_170801_) {
        MeshDefinition $$1 = QuadrupedModel.m_170864_(6, p_170801_);
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, p_170801_).m_171514_(16, 16).m_171488_(-2.0f, 0.0f, -9.0f, 4.0f, 3.0f, 1.0f, p_170801_), PartPose.m_171419_(0.0f, 12.0f, -6.0f));
        return LayerDefinition.m_171565_($$1, 64, 32);
    }
}

