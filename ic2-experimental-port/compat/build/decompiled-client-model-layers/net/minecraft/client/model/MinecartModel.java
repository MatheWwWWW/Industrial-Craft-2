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

public class MinecartModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170733_;

    public MinecartModel(ModelPart p_170737_) {
        this.f_170733_ = p_170737_;
    }

    public static LayerDefinition m_170738_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 20;
        int $$3 = 8;
        int $$4 = 16;
        int $$5 = 4;
        $$1.m_171599_("bottom", CubeListBuilder.m_171558_().m_171514_(0, 10).m_171481_(-10.0f, -8.0f, -1.0f, 20.0f, 16.0f, 2.0f), PartPose.m_171423_(0.0f, 4.0f, 0.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.m_171599_("front", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.m_171423_(-9.0f, 4.0f, 0.0f, 0.0f, 4.712389f, 0.0f));
        $$1.m_171599_("back", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.m_171423_(9.0f, 4.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
        $$1.m_171599_("left", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.m_171423_(0.0f, 4.0f, -7.0f, 0.0f, (float)Math.PI, 0.0f));
        $$1.m_171599_("right", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.m_171419_(0.0f, 4.0f, 7.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_103100_, float p_103101_, float p_103102_, float p_103103_, float p_103104_, float p_103105_) {
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170733_;
    }
}

