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
import net.minecraft.world.entity.Entity;

public class SilverfishModel<T extends Entity>
extends HierarchicalModel<T> {
    private static final int f_170924_ = 7;
    private final ModelPart f_170925_;
    private final ModelPart[] f_103744_ = new ModelPart[7];
    private final ModelPart[] f_103745_ = new ModelPart[3];
    private static final int[][] f_103748_ = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
    private static final int[][] f_103749_ = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

    public SilverfishModel(ModelPart p_170927_) {
        this.f_170925_ = p_170927_;
        Arrays.setAll(this.f_103744_, p_170939_ -> p_170927_.m_171324_(SilverfishModel.m_170935_(p_170939_)));
        Arrays.setAll(this.f_103745_, p_170933_ -> p_170927_.m_171324_(SilverfishModel.m_170929_(p_170933_)));
    }

    private static String m_170929_(int p_170930_) {
        return "layer" + p_170930_;
    }

    private static String m_170935_(int p_170936_) {
        return "segment" + p_170936_;
    }

    public static LayerDefinition m_170928_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float[] $$2 = new float[7];
        float $$3 = -3.5f;
        for (int $$4 = 0; $$4 < 7; ++$$4) {
            $$1.m_171599_(SilverfishModel.m_170935_($$4), CubeListBuilder.m_171558_().m_171514_(f_103749_[$$4][0], f_103749_[$$4][1]).m_171481_((float)f_103748_[$$4][0] * -0.5f, 0.0f, (float)f_103748_[$$4][2] * -0.5f, f_103748_[$$4][0], f_103748_[$$4][1], f_103748_[$$4][2]), PartPose.m_171419_(0.0f, 24 - f_103748_[$$4][1], $$3));
            $$2[$$4] = $$3;
            if ($$4 >= 6) continue;
            $$3 += (float)(f_103748_[$$4][2] + f_103748_[$$4 + 1][2]) * 0.5f;
        }
        $$1.m_171599_(SilverfishModel.m_170929_(0), CubeListBuilder.m_171558_().m_171514_(20, 0).m_171481_(-5.0f, 0.0f, (float)f_103748_[2][2] * -0.5f, 10.0f, 8.0f, f_103748_[2][2]), PartPose.m_171419_(0.0f, 16.0f, $$2[2]));
        $$1.m_171599_(SilverfishModel.m_170929_(1), CubeListBuilder.m_171558_().m_171514_(20, 11).m_171481_(-3.0f, 0.0f, (float)f_103748_[4][2] * -0.5f, 6.0f, 4.0f, f_103748_[4][2]), PartPose.m_171419_(0.0f, 20.0f, $$2[4]));
        $$1.m_171599_(SilverfishModel.m_170929_(2), CubeListBuilder.m_171558_().m_171514_(20, 18).m_171481_(-3.0f, 0.0f, (float)f_103748_[4][2] * -0.5f, 6.0f, 5.0f, f_103748_[1][2]), PartPose.m_171419_(0.0f, 19.0f, $$2[1]));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170925_;
    }

    @Override
    public void m_6973_(T p_103754_, float p_103755_, float p_103756_, float p_103757_, float p_103758_, float p_103759_) {
        for (int $$6 = 0; $$6 < this.f_103744_.length; ++$$6) {
            this.f_103744_[$$6].f_104204_ = Mth.m_14089_(p_103757_ * 0.9f + (float)$$6 * 0.15f * (float)Math.PI) * (float)Math.PI * 0.05f * (float)(1 + Math.abs($$6 - 2));
            this.f_103744_[$$6].f_104200_ = Mth.m_14031_(p_103757_ * 0.9f + (float)$$6 * 0.15f * (float)Math.PI) * (float)Math.PI * 0.2f * (float)Math.abs($$6 - 2);
        }
        this.f_103745_[0].f_104204_ = this.f_103744_[2].f_104204_;
        this.f_103745_[1].f_104204_ = this.f_103744_[4].f_104204_;
        this.f_103745_[1].f_104200_ = this.f_103744_[4].f_104200_;
        this.f_103745_[2].f_104204_ = this.f_103744_[1].f_104204_;
        this.f_103745_[2].f_104200_ = this.f_103744_[1].f_104200_;
    }
}

