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

public class ShulkerBulletModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final String f_170913_ = "main";
    private final ModelPart f_170914_;
    private final ModelPart f_103712_;

    public ShulkerBulletModel(ModelPart p_170916_) {
        this.f_170914_ = p_170916_;
        this.f_103712_ = p_170916_.m_171324_(f_170913_);
    }

    public static LayerDefinition m_170917_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_170913_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -1.0f, 8.0f, 8.0f, 2.0f).m_171514_(0, 10).m_171481_(-1.0f, -4.0f, -4.0f, 2.0f, 8.0f, 8.0f).m_171514_(20, 0).m_171481_(-4.0f, -1.0f, -4.0f, 8.0f, 2.0f, 8.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170914_;
    }

    @Override
    public void m_6973_(T p_103716_, float p_103717_, float p_103718_, float p_103719_, float p_103720_, float p_103721_) {
        this.f_103712_.f_104204_ = p_103720_ * ((float)Math.PI / 180);
        this.f_103712_.f_104203_ = p_103721_ * ((float)Math.PI / 180);
    }
}

