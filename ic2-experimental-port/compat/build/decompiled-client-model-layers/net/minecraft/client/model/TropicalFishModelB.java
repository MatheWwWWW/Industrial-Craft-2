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

public class TropicalFishModelB<T extends Entity>
extends ColorableHierarchicalModel<T> {
    private final ModelPart f_171034_;
    private final ModelPart f_103968_;

    public TropicalFishModelB(ModelPart p_171036_) {
        this.f_171034_ = p_171036_;
        this.f_103968_ = p_171036_.m_171324_("tail");
    }

    public static LayerDefinition m_171037_(CubeDeformation p_171038_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        int $$3 = 19;
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 20).m_171488_(-1.0f, -3.0f, -3.0f, 2.0f, 6.0f, 6.0f, p_171038_), PartPose.m_171419_(0.0f, 19.0f, 0.0f));
        $$2.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(21, 16).m_171488_(0.0f, -3.0f, 0.0f, 0.0f, 6.0f, 5.0f, p_171038_), PartPose.m_171419_(0.0f, 19.0f, 3.0f));
        $$2.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(2, 16).m_171488_(-2.0f, 0.0f, 0.0f, 2.0f, 2.0f, 0.0f, p_171038_), PartPose.m_171423_(-1.0f, 20.0f, 0.0f, 0.0f, 0.7853982f, 0.0f));
        $$2.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(2, 12).m_171488_(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 0.0f, p_171038_), PartPose.m_171423_(1.0f, 20.0f, 0.0f, 0.0f, -0.7853982f, 0.0f));
        $$2.m_171599_("top_fin", CubeListBuilder.m_171558_().m_171514_(20, 11).m_171488_(0.0f, -4.0f, 0.0f, 0.0f, 4.0f, 6.0f, p_171038_), PartPose.m_171419_(0.0f, 16.0f, -3.0f));
        $$2.m_171599_("bottom_fin", CubeListBuilder.m_171558_().m_171514_(20, 21).m_171488_(0.0f, 0.0f, 0.0f, 0.0f, 4.0f, 6.0f, p_171038_), PartPose.m_171419_(0.0f, 22.0f, -3.0f));
        return LayerDefinition.m_171565_($$1, 32, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_171034_;
    }

    @Override
    public void m_6973_(T p_103977_, float p_103978_, float p_103979_, float p_103980_, float p_103981_, float p_103982_) {
        float $$6 = 1.0f;
        if (!((Entity)p_103977_).m_20069_()) {
            $$6 = 1.5f;
        }
        this.f_103968_.f_104204_ = -$$6 * 0.45f * Mth.m_14031_(0.6f * p_103980_);
    }
}

