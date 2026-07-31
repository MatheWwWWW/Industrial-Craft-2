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

public class LlamaSpitModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170727_ = "main";
    private final ModelPart f_170728_;

    public LlamaSpitModel(ModelPart p_170730_) {
        this.f_170728_ = p_170730_;
    }

    public static LayerDefinition m_170731_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 2;
        $$1.m_171599_(f_170727_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f).m_171481_(0.0f, -4.0f, 0.0f, 2.0f, 2.0f, 2.0f).m_171481_(0.0f, 0.0f, -4.0f, 2.0f, 2.0f, 2.0f).m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f).m_171481_(2.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f).m_171481_(0.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f).m_171481_(0.0f, 0.0f, 2.0f, 2.0f, 2.0f, 2.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_103090_, float p_103091_, float p_103092_, float p_103093_, float p_103094_, float p_103095_) {
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170728_;
    }
}

