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
import net.minecraft.world.entity.Entity;

public class EndermiteModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final int f_102596_ = 4;
    private static final int[][] f_102594_ = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
    private static final int[][] f_102595_ = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
    private final ModelPart f_170543_;
    private final ModelPart[] f_102597_;

    public EndermiteModel(ModelPart p_170545_) {
        this.f_170543_ = p_170545_;
        this.f_102597_ = new ModelPart[4];
        for (int $$1 = 0; $$1 < 4; ++$$1) {
            this.f_102597_[$$1] = p_170545_.m_171324_(EndermiteModel.m_170547_($$1));
        }
    }

    private static String m_170547_(int p_170548_) {
        return "segment" + p_170548_;
    }

    public static LayerDefinition m_170546_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = -3.5f;
        for (int $$3 = 0; $$3 < 4; ++$$3) {
            $$1.m_171599_(EndermiteModel.m_170547_($$3), CubeListBuilder.m_171558_().m_171514_(f_102595_[$$3][0], f_102595_[$$3][1]).m_171481_((float)f_102594_[$$3][0] * -0.5f, 0.0f, (float)f_102594_[$$3][2] * -0.5f, f_102594_[$$3][0], f_102594_[$$3][1], f_102594_[$$3][2]), PartPose.m_171419_(0.0f, 24 - f_102594_[$$3][1], $$2));
            if ($$3 >= 3) continue;
            $$2 += (float)(f_102594_[$$3][2] + f_102594_[$$3 + 1][2]) * 0.5f;
        }
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170543_;
    }

    @Override
    public void m_6973_(T p_102602_, float p_102603_, float p_102604_, float p_102605_, float p_102606_, float p_102607_) {
        for (int $$6 = 0; $$6 < this.f_102597_.length; ++$$6) {
            this.f_102597_[$$6].f_104204_ = Mth.m_14089_(p_102605_ * 0.9f + (float)$$6 * 0.15f * (float)Math.PI) * (float)Math.PI * 0.01f * (float)(1 + Math.abs($$6 - 2));
            this.f_102597_[$$6].f_104200_ = Mth.m_14031_(p_102605_ * 0.9f + (float)$$6 * 0.15f * (float)Math.PI) * (float)Math.PI * 0.1f * (float)Math.abs($$6 - 2);
        }
    }
}

