/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;

public class GuardianModel
extends HierarchicalModel<Guardian> {
    private static final float[] f_102695_ = new float[]{1.75f, 0.25f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 1.25f, 0.75f, 0.0f, 0.0f};
    private static final float[] f_102696_ = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 1.75f, 1.25f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f};
    private static final float[] f_102697_ = new float[]{0.0f, 0.0f, 0.25f, 1.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.75f, 1.25f};
    private static final float[] f_102698_ = new float[]{0.0f, 0.0f, 8.0f, -8.0f, -8.0f, 8.0f, 8.0f, -8.0f, 0.0f, 0.0f, 8.0f, -8.0f};
    private static final float[] f_102699_ = new float[]{-8.0f, -8.0f, -8.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 8.0f, 8.0f, 8.0f, 8.0f};
    private static final float[] f_102700_ = new float[]{8.0f, -8.0f, 0.0f, 0.0f, -8.0f, -8.0f, 8.0f, 8.0f, 8.0f, -8.0f, 0.0f, 0.0f};
    private static final String f_170594_ = "eye";
    private static final String f_170595_ = "tail0";
    private static final String f_170596_ = "tail1";
    private static final String f_170597_ = "tail2";
    private final ModelPart f_170598_;
    private final ModelPart f_102701_;
    private final ModelPart f_102702_;
    private final ModelPart[] f_102703_;
    private final ModelPart[] f_102704_;

    public GuardianModel(ModelPart p_170600_) {
        this.f_170598_ = p_170600_;
        this.f_102703_ = new ModelPart[12];
        this.f_102701_ = p_170600_.m_171324_("head");
        for (int $$1 = 0; $$1 < this.f_102703_.length; ++$$1) {
            this.f_102703_[$$1] = this.f_102701_.m_171324_(GuardianModel.m_170602_($$1));
        }
        this.f_102702_ = this.f_102701_.m_171324_(f_170594_);
        this.f_102704_ = new ModelPart[3];
        this.f_102704_[0] = this.f_102701_.m_171324_(f_170595_);
        this.f_102704_[1] = this.f_102704_[0].m_171324_(f_170596_);
        this.f_102704_[2] = this.f_102704_[1].m_171324_(f_170597_);
    }

    private static String m_170602_(int p_170603_) {
        return "spike" + p_170603_;
    }

    public static LayerDefinition m_170601_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0f, 10.0f, -8.0f, 12.0f, 12.0f, 16.0f).m_171514_(0, 28).m_171481_(-8.0f, 10.0f, -6.0f, 2.0f, 12.0f, 12.0f).m_171514_(0, 28).m_171506_(6.0f, 10.0f, -6.0f, 2.0f, 12.0f, 12.0f, true).m_171514_(16, 40).m_171481_(-6.0f, 8.0f, -6.0f, 12.0f, 2.0f, 12.0f).m_171514_(16, 40).m_171481_(-6.0f, 22.0f, -6.0f, 12.0f, 2.0f, 12.0f), PartPose.f_171404_);
        CubeListBuilder $$3 = CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0f, -4.5f, -1.0f, 2.0f, 9.0f, 2.0f);
        for (int $$4 = 0; $$4 < 12; ++$$4) {
            float $$5 = GuardianModel.m_170609_($$4, 0.0f, 0.0f);
            float $$6 = GuardianModel.m_170613_($$4, 0.0f, 0.0f);
            float $$7 = GuardianModel.m_170617_($$4, 0.0f, 0.0f);
            float $$8 = (float)Math.PI * f_102695_[$$4];
            float $$9 = (float)Math.PI * f_102696_[$$4];
            float $$10 = (float)Math.PI * f_102697_[$$4];
            $$2.m_171599_(GuardianModel.m_170602_($$4), $$3, PartPose.m_171423_($$5, $$6, $$7, $$8, $$9, $$10));
        }
        $$2.m_171599_(f_170594_, CubeListBuilder.m_171558_().m_171514_(8, 0).m_171481_(-1.0f, 15.0f, 0.0f, 2.0f, 2.0f, 1.0f), PartPose.m_171419_(0.0f, 0.0f, -8.25f));
        PartDefinition $$11 = $$2.m_171599_(f_170595_, CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(-2.0f, 14.0f, 7.0f, 4.0f, 4.0f, 8.0f), PartPose.f_171404_);
        PartDefinition $$12 = $$11.m_171599_(f_170596_, CubeListBuilder.m_171558_().m_171514_(0, 54).m_171481_(0.0f, 14.0f, 0.0f, 3.0f, 3.0f, 7.0f), PartPose.m_171419_(-1.5f, 0.5f, 14.0f));
        $$12.m_171599_(f_170597_, CubeListBuilder.m_171558_().m_171514_(41, 32).m_171481_(0.0f, 14.0f, 0.0f, 2.0f, 2.0f, 6.0f).m_171514_(25, 19).m_171481_(1.0f, 10.5f, 3.0f, 1.0f, 9.0f, 9.0f), PartPose.m_171419_(0.5f, 0.5f, 6.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_170598_;
    }

    @Override
    public void m_6973_(Guardian p_102719_, float p_102720_, float p_102721_, float p_102722_, float p_102723_, float p_102724_) {
        float $$6 = p_102722_ - (float)p_102719_.f_19797_;
        this.f_102701_.f_104204_ = p_102723_ * ((float)Math.PI / 180);
        this.f_102701_.f_104203_ = p_102724_ * ((float)Math.PI / 180);
        float $$7 = (1.0f - p_102719_.m_32865_($$6)) * 0.55f;
        this.m_102708_(p_102722_, $$7);
        Entity $$8 = Minecraft.m_91087_().m_91288_();
        if (p_102719_.m_32855_()) {
            $$8 = p_102719_.m_32856_();
        }
        if ($$8 != null) {
            Vec3 $$9 = $$8.m_20299_(0.0f);
            Vec3 $$10 = p_102719_.m_20299_(0.0f);
            double $$11 = $$9.f_82480_ - $$10.f_82480_;
            this.f_102702_.f_104201_ = $$11 > 0.0 ? 0.0f : 1.0f;
            Vec3 $$12 = p_102719_.m_20252_(0.0f);
            $$12 = new Vec3($$12.f_82479_, 0.0, $$12.f_82481_);
            Vec3 $$13 = new Vec3($$10.f_82479_ - $$9.f_82479_, 0.0, $$10.f_82481_ - $$9.f_82481_).m_82541_().m_82524_(1.5707964f);
            double $$14 = $$12.m_82526_($$13);
            this.f_102702_.f_104200_ = Mth.m_14116_((float)Math.abs($$14)) * 2.0f * (float)Math.signum($$14);
        }
        this.f_102702_.f_104207_ = true;
        float $$15 = p_102719_.m_32863_($$6);
        this.f_102704_[0].f_104204_ = Mth.m_14031_($$15) * (float)Math.PI * 0.05f;
        this.f_102704_[1].f_104204_ = Mth.m_14031_($$15) * (float)Math.PI * 0.1f;
        this.f_102704_[2].f_104204_ = Mth.m_14031_($$15) * (float)Math.PI * 0.15f;
    }

    private void m_102708_(float p_102709_, float p_102710_) {
        for (int $$2 = 0; $$2 < 12; ++$$2) {
            this.f_102703_[$$2].f_104200_ = GuardianModel.m_170609_($$2, p_102709_, p_102710_);
            this.f_102703_[$$2].f_104201_ = GuardianModel.m_170613_($$2, p_102709_, p_102710_);
            this.f_102703_[$$2].f_104202_ = GuardianModel.m_170617_($$2, p_102709_, p_102710_);
        }
    }

    private static float m_170604_(int p_170605_, float p_170606_, float p_170607_) {
        return 1.0f + Mth.m_14089_(p_170606_ * 1.5f + (float)p_170605_) * 0.01f - p_170607_;
    }

    private static float m_170609_(int p_170610_, float p_170611_, float p_170612_) {
        return f_102698_[p_170610_] * GuardianModel.m_170604_(p_170610_, p_170611_, p_170612_);
    }

    private static float m_170613_(int p_170614_, float p_170615_, float p_170616_) {
        return 16.0f + f_102699_[p_170614_] * GuardianModel.m_170604_(p_170614_, p_170615_, p_170616_);
    }

    private static float m_170617_(int p_170618_, float p_170619_, float p_170620_) {
        return f_102700_[p_170618_] * GuardianModel.m_170604_(p_170618_, p_170619_, p_170620_);
    }
}

