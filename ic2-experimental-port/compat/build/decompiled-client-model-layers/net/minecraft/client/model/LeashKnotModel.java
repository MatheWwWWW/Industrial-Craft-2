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

public class LeashKnotModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170711_ = "knot";
    private final ModelPart f_170712_;
    private final ModelPart f_102999_;

    public LeashKnotModel(ModelPart p_170714_) {
        this.f_170712_ = p_170714_;
        this.f_102999_ = p_170714_.m_171324_(f_170711_);
    }

    public static LayerDefinition m_170715_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_170711_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -8.0f, -3.0f, 6.0f, 8.0f, 6.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170712_;
    }

    @Override
    public void m_6973_(T p_103003_, float p_103004_, float p_103005_, float p_103006_, float p_103007_, float p_103008_) {
        this.f_102999_.f_104204_ = p_103007_ * ((float)Math.PI / 180);
        this.f_102999_.f_104203_ = p_103008_ * ((float)Math.PI / 180);
    }
}

