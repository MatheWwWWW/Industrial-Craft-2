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
import net.minecraft.world.entity.monster.Ravager;

public class RavagerModel
extends HierarchicalModel<Ravager> {
    private final ModelPart f_170883_;
    private final ModelPart f_103598_;
    private final ModelPart f_103599_;
    private final ModelPart f_170884_;
    private final ModelPart f_170885_;
    private final ModelPart f_170886_;
    private final ModelPart f_170887_;
    private final ModelPart f_103605_;

    public RavagerModel(ModelPart p_170889_) {
        this.f_170883_ = p_170889_;
        this.f_103605_ = p_170889_.m_171324_("neck");
        this.f_103598_ = this.f_103605_.m_171324_("head");
        this.f_103599_ = this.f_103598_.m_171324_("mouth");
        this.f_170884_ = p_170889_.m_171324_("right_hind_leg");
        this.f_170885_ = p_170889_.m_171324_("left_hind_leg");
        this.f_170886_ = p_170889_.m_171324_("right_front_leg");
        this.f_170887_ = p_170889_.m_171324_("left_front_leg");
    }

    public static LayerDefinition m_170890_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        int $$2 = 16;
        PartDefinition $$3 = $$1.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(68, 73).m_171481_(-5.0f, -1.0f, -18.0f, 10.0f, 10.0f, 18.0f), PartPose.m_171419_(0.0f, -7.0f, 5.5f));
        PartDefinition $$4 = $$3.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -20.0f, -14.0f, 16.0f, 20.0f, 16.0f).m_171514_(0, 0).m_171481_(-2.0f, -6.0f, -18.0f, 4.0f, 8.0f, 4.0f), PartPose.m_171419_(0.0f, 16.0f, -17.0f));
        $$4.m_171599_("right_horn", CubeListBuilder.m_171558_().m_171514_(74, 55).m_171481_(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f), PartPose.m_171423_(-10.0f, -14.0f, -8.0f, 1.0995574f, 0.0f, 0.0f));
        $$4.m_171599_("left_horn", CubeListBuilder.m_171558_().m_171514_(74, 55).m_171480_().m_171481_(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f), PartPose.m_171423_(8.0f, -14.0f, -8.0f, 1.0995574f, 0.0f, 0.0f));
        $$4.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171481_(-8.0f, 0.0f, -16.0f, 16.0f, 3.0f, 16.0f), PartPose.m_171419_(0.0f, -2.0f, 2.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171481_(-7.0f, -10.0f, -7.0f, 14.0f, 16.0f, 20.0f).m_171514_(0, 91).m_171481_(-6.0f, 6.0f, -7.0f, 12.0f, 13.0f, 18.0f), PartPose.m_171423_(0.0f, 1.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(96, 0).m_171481_(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.m_171419_(-8.0f, -13.0f, 18.0f));
        $$1.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(96, 0).m_171480_().m_171481_(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.m_171419_(8.0f, -13.0f, 18.0f));
        $$1.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(64, 0).m_171481_(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.m_171419_(-8.0f, -13.0f, -5.0f));
        $$1.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(64, 0).m_171480_().m_171481_(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.m_171419_(8.0f, -13.0f, -5.0f));
        return LayerDefinition.m_171565_($$0, 128, 128);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170883_;
    }

    @Override
    public void m_6973_(Ravager p_103626_, float p_103627_, float p_103628_, float p_103629_, float p_103630_, float p_103631_) {
        this.f_103598_.f_104203_ = p_103631_ * ((float)Math.PI / 180);
        this.f_103598_.f_104204_ = p_103630_ * ((float)Math.PI / 180);
        float $$6 = 0.4f * p_103628_;
        this.f_170884_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662f) * $$6;
        this.f_170885_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662f + (float)Math.PI) * $$6;
        this.f_170886_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662f + (float)Math.PI) * $$6;
        this.f_170887_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662f) * $$6;
    }

    @Override
    public void m_6839_(Ravager p_103621_, float p_103622_, float p_103623_, float p_103624_) {
        super.m_6839_(p_103621_, p_103622_, p_103623_, p_103624_);
        int $$4 = p_103621_.m_33364_();
        int $$5 = p_103621_.m_33366_();
        int $$6 = 20;
        int $$7 = p_103621_.m_33362_();
        int $$8 = 10;
        if ($$7 > 0) {
            float $$9 = Mth.m_14156_((float)$$7 - p_103624_, 10.0f);
            float $$10 = (1.0f + $$9) * 0.5f;
            float $$11 = $$10 * $$10 * $$10 * 12.0f;
            float $$12 = $$11 * Mth.m_14031_(this.f_103605_.f_104203_);
            this.f_103605_.f_104202_ = -6.5f + $$11;
            this.f_103605_.f_104201_ = -7.0f - $$12;
            float $$13 = Mth.m_14031_(((float)$$7 - p_103624_) / 10.0f * (float)Math.PI * 0.25f);
            this.f_103599_.f_104203_ = 1.5707964f * $$13;
            this.f_103599_.f_104203_ = $$7 > 5 ? Mth.m_14031_(((float)(-4 + $$7) - p_103624_) / 4.0f) * (float)Math.PI * 0.4f : 0.15707964f * Mth.m_14031_((float)Math.PI * ((float)$$7 - p_103624_) / 10.0f);
        } else {
            float $$14 = -1.0f;
            float $$15 = -1.0f * Mth.m_14031_(this.f_103605_.f_104203_);
            this.f_103605_.f_104200_ = 0.0f;
            this.f_103605_.f_104201_ = -7.0f - $$15;
            this.f_103605_.f_104202_ = 5.5f;
            boolean $$16 = $$4 > 0;
            this.f_103605_.f_104203_ = $$16 ? 0.21991149f : 0.0f;
            this.f_103599_.f_104203_ = (float)Math.PI * ($$16 ? 0.05f : 0.01f);
            if ($$16) {
                double $$17 = (double)$$4 / 40.0;
                this.f_103605_.f_104200_ = (float)Math.sin($$17 * 10.0) * 3.0f;
            } else if ($$5 > 0) {
                float $$18 = Mth.m_14031_(((float)(20 - $$5) - p_103624_) / 20.0f * (float)Math.PI * 0.25f);
                this.f_103599_.f_104203_ = 1.5707964f * $$18;
            }
        }
    }
}

