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

public class BlazeModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart f_170441_;
    private final ModelPart[] f_102244_;
    private final ModelPart f_102245_;

    public BlazeModel(ModelPart p_170443_) {
        this.f_170441_ = p_170443_;
        this.f_102245_ = p_170443_.m_171324_("head");
        this.f_102244_ = new ModelPart[12];
        Arrays.setAll(this.f_102244_, p_170449_ -> p_170443_.m_171324_(BlazeModel.m_170445_(p_170449_)));
    }

    private static String m_170445_(int p_170446_) {
        return "part" + p_170446_;
    }

    public static LayerDefinition m_170444_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        float $$2 = 0.0f;
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 8.0f, 2.0f);
        for (int $$4 = 0; $$4 < 4; ++$$4) {
            float $$5 = Mth.m_14089_($$2) * 9.0f;
            float $$6 = -2.0f + Mth.m_14089_((float)($$4 * 2) * 0.25f);
            float $$7 = Mth.m_14031_($$2) * 9.0f;
            $$1.m_171599_(BlazeModel.m_170445_($$4), $$3, PartPose.m_171419_($$5, $$6, $$7));
            $$2 += 1.5707964f;
        }
        $$2 = 0.7853982f;
        for (int $$8 = 4; $$8 < 8; ++$$8) {
            float $$9 = Mth.m_14089_($$2) * 7.0f;
            float $$10 = 2.0f + Mth.m_14089_((float)($$8 * 2) * 0.25f);
            float $$11 = Mth.m_14031_($$2) * 7.0f;
            $$1.m_171599_(BlazeModel.m_170445_($$8), $$3, PartPose.m_171419_($$9, $$10, $$11));
            $$2 += 1.5707964f;
        }
        $$2 = 0.47123894f;
        for (int $$12 = 8; $$12 < 12; ++$$12) {
            float $$13 = Mth.m_14089_($$2) * 5.0f;
            float $$14 = 11.0f + Mth.m_14089_((float)$$12 * 1.5f * 0.5f);
            float $$15 = Mth.m_14031_($$2) * 5.0f;
            $$1.m_171599_(BlazeModel.m_170445_($$12), $$3, PartPose.m_171419_($$13, $$14, $$15));
            $$2 += 1.5707964f;
        }
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170441_;
    }

    @Override
    public void m_6973_(T p_102250_, float p_102251_, float p_102252_, float p_102253_, float p_102254_, float p_102255_) {
        float $$6 = p_102253_ * (float)Math.PI * -0.1f;
        for (int $$7 = 0; $$7 < 4; ++$$7) {
            this.f_102244_[$$7].f_104201_ = -2.0f + Mth.m_14089_(((float)($$7 * 2) + p_102253_) * 0.25f);
            this.f_102244_[$$7].f_104200_ = Mth.m_14089_($$6) * 9.0f;
            this.f_102244_[$$7].f_104202_ = Mth.m_14031_($$6) * 9.0f;
            $$6 += 1.5707964f;
        }
        $$6 = 0.7853982f + p_102253_ * (float)Math.PI * 0.03f;
        for (int $$8 = 4; $$8 < 8; ++$$8) {
            this.f_102244_[$$8].f_104201_ = 2.0f + Mth.m_14089_(((float)($$8 * 2) + p_102253_) * 0.25f);
            this.f_102244_[$$8].f_104200_ = Mth.m_14089_($$6) * 7.0f;
            this.f_102244_[$$8].f_104202_ = Mth.m_14031_($$6) * 7.0f;
            $$6 += 1.5707964f;
        }
        $$6 = 0.47123894f + p_102253_ * (float)Math.PI * -0.05f;
        for (int $$9 = 8; $$9 < 12; ++$$9) {
            this.f_102244_[$$9].f_104201_ = 11.0f + Mth.m_14089_(((float)$$9 * 1.5f + p_102253_) * 0.5f);
            this.f_102244_[$$9].f_104200_ = Mth.m_14089_($$6) * 5.0f;
            this.f_102244_[$$9].f_104202_ = Mth.m_14031_($$6) * 5.0f;
            $$6 += 1.5707964f;
        }
        this.f_102245_.f_104204_ = p_102254_ * ((float)Math.PI / 180);
        this.f_102245_.f_104203_ = p_102255_ * ((float)Math.PI / 180);
    }
}

