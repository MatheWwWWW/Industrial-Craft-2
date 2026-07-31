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
import net.minecraft.world.entity.Entity;

public class SquidModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart[] f_103873_ = new ModelPart[8];
    private final ModelPart f_170987_;

    public SquidModel(ModelPart p_170989_) {
        this.f_170987_ = p_170989_;
        Arrays.setAll(this.f_103873_, p_170995_ -> p_170989_.m_171324_(SquidModel.m_170991_(p_170995_)));
    }

    private static String m_170991_(int p_170992_) {
        return "tentacle" + p_170992_;
    }

    public static LayerDefinition m_170990_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = -16;
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0f, -8.0f, -6.0f, 12.0f, 16.0f, 12.0f), PartPose.m_171419_(0.0f, 8.0f, 0.0f));
        int $$3 = 8;
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(48, 0).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 18.0f, 2.0f);
        for (int $$5 = 0; $$5 < 8; ++$$5) {
            double $$6 = (double)$$5 * Math.PI * 2.0 / 8.0;
            float $$7 = (float)Math.cos($$6) * 5.0f;
            float $$8 = 15.0f;
            float $$9 = (float)Math.sin($$6) * 5.0f;
            $$6 = (double)$$5 * Math.PI * -2.0 / 8.0 + 1.5707963267948966;
            float $$10 = (float)$$6;
            $$1.m_171599_(SquidModel.m_170991_($$5), $$4, PartPose.m_171423_($$7, 15.0f, $$9, 0.0f, $$10, 0.0f));
        }
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6973_(T p_103878_, float p_103879_, float p_103880_, float p_103881_, float p_103882_, float p_103883_) {
        for (ModelPart $$6 : this.f_103873_) {
            $$6.f_104203_ = p_103881_;
        }
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170987_;
    }
}

