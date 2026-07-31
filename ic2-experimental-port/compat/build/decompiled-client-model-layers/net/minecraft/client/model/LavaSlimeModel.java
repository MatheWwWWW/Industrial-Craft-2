/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;

public class LavaSlimeModel<T extends Slime>
extends HierarchicalModel<T> {
    private static final int f_170700_ = 8;
    private final ModelPart f_170701_;
    private final ModelPart[] f_102969_ = new ModelPart[8];

    public LavaSlimeModel(ModelPart p_170703_) {
        this.f_170701_ = p_170703_;
        Arrays.setAll(this.f_102969_, p_170709_ -> p_170703_.m_171324_(LavaSlimeModel.m_170705_(p_170709_)));
    }

    private static String m_170705_(int p_170706_) {
        return "cube" + p_170706_;
    }

    public static LayerDefinition m_170704_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        for (int $$2 = 0; $$2 < 8; ++$$2) {
            int $$3 = 0;
            int $$4 = $$2;
            if ($$2 == 2) {
                $$3 = 24;
                $$4 = 10;
            } else if ($$2 == 3) {
                $$3 = 24;
                $$4 = 19;
            }
            $$1.m_171599_(LavaSlimeModel.m_170705_($$2), CubeListBuilder.m_171558_().m_171514_($$3, $$4).m_171481_(-4.0f, 16 + $$2, -4.0f, 8.0f, 1.0f, 8.0f), PartPose.f_171404_);
        }
        $$1.m_171599_("inside_cube", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-2.0f, 18.0f, -2.0f, 4.0f, 4.0f, 4.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_102992_, float p_102993_, float p_102994_, float p_102995_, float p_102996_, float p_102997_) {
    }

    @Override
    public void m_6839_(T p_102987_, float p_102988_, float p_102989_, float p_102990_) {
        float $$4 = Mth.m_14179_(p_102990_, ((Slime)p_102987_).f_33585_, ((Slime)p_102987_).f_33584_);
        if ($$4 < 0.0f) {
            $$4 = 0.0f;
        }
        for (int $$5 = 0; $$5 < this.f_102969_.length; ++$$5) {
            this.f_102969_[$$5].f_104201_ = (float)(-(4 - $$5)) * $$4 * 1.7f;
        }
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170701_;
    }
}

