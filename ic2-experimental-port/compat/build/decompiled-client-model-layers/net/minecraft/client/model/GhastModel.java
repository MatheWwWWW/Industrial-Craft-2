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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class GhastModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170568_;
    private final ModelPart[] f_102676_ = new ModelPart[9];

    public GhastModel(ModelPart p_170570_) {
        this.f_170568_ = p_170570_;
        for (int $$1 = 0; $$1 < this.f_102676_.length; ++$$1) {
            this.f_102676_[$$1] = p_170570_.m_171324_(GhastModel.m_170572_($$1));
        }
    }

    private static String m_170572_(int p_170573_) {
        return "tentacle" + p_170573_;
    }

    public static LayerDefinition m_170571_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f), PartPose.m_171419_(0.0f, 17.6f, 0.0f));
        RandomSource $$2 = RandomSource.m_216335_(1660L);
        for (int $$3 = 0; $$3 < 9; ++$$3) {
            float $$4 = (((float)($$3 % 3) - (float)($$3 / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float $$5 = ((float)($$3 / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int $$6 = $$2.m_188503_(7) + 8;
            $$1.m_171599_(GhastModel.m_170572_($$3), CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, $$6, 2.0f), PartPose.m_171419_($$4, 24.6f, $$5));
        }
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_102681_, float p_102682_, float p_102683_, float p_102684_, float p_102685_, float p_102686_) {
        for (int $$6 = 0; $$6 < this.f_102676_.length; ++$$6) {
            this.f_102676_[$$6].f_104203_ = 0.2f * Mth.m_14031_(p_102684_ * 0.3f + (float)$$6) + 0.4f;
        }
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170568_;
    }
}

