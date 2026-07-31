/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class CowModel<T extends Entity>
extends QuadrupedModel<T> {
    public CowModel(ModelPart p_170515_) {
        super(p_170515_, false, 10.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    public static LayerDefinition m_170516_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 12;
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -6.0f, 8.0f, 8.0f, 6.0f).m_171514_(22, 0).m_171517_("right_horn", -5.0f, -5.0f, -4.0f, 1.0f, 3.0f, 1.0f).m_171514_(22, 0).m_171517_("left_horn", 4.0f, -5.0f, -4.0f, 1.0f, 3.0f, 1.0f), PartPose.m_171419_(0.0f, 4.0f, -8.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(18, 4).m_171481_(-6.0f, -10.0f, -7.0f, 12.0f, 18.0f, 10.0f).m_171514_(52, 0).m_171481_(-2.0f, 2.0f, -8.0f, 4.0f, 6.0f, 1.0f), PartPose.m_171423_(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f);
        $$1.m_171599_("right_hind_leg", $$3, PartPose.m_171419_(-4.0f, 12.0f, 7.0f));
        $$1.m_171599_("left_hind_leg", $$3, PartPose.m_171419_(4.0f, 12.0f, 7.0f));
        $$1.m_171599_("right_front_leg", $$3, PartPose.m_171419_(-4.0f, 12.0f, -6.0f));
        $$1.m_171599_("left_front_leg", $$3, PartPose.m_171419_(4.0f, 12.0f, -6.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    public ModelPart m_102450_() {
        return this.f_103492_;
    }
}

