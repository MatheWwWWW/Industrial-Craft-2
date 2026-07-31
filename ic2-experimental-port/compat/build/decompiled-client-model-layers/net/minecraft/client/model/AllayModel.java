/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.animal.allay.Allay;

public class AllayModel
extends HierarchicalModel<Allay>
implements ArmedModel {
    private final ModelPart f_233302_;
    private final ModelPart f_238177_;
    private final ModelPart f_233303_;
    private final ModelPart f_233304_;
    private final ModelPart f_233305_;
    private final ModelPart f_233306_;
    private final ModelPart f_233307_;
    private static final float f_233308_ = 0.6981317f;
    private static final float f_233309_ = -0.7853982f;
    private static final float f_233310_ = -1.0471976f;

    public AllayModel(ModelPart p_233312_) {
        this.f_233302_ = p_233312_.m_171324_("root");
        this.f_238177_ = this.f_233302_.m_171324_("head");
        this.f_233303_ = this.f_233302_.m_171324_("body");
        this.f_233304_ = this.f_233303_.m_171324_("right_arm");
        this.f_233305_ = this.f_233303_.m_171324_("left_arm");
        this.f_233306_ = this.f_233303_.m_171324_("right_wing");
        this.f_233307_ = this.f_233303_.m_171324_("left_wing");
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_233302_;
    }

    public static LayerDefinition m_233340_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0f, 23.5f, 0.0f));
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5f, -5.0f, -2.5f, 5.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_(0.0f, -3.99f, 0.0f));
        PartDefinition $$3 = $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 10).m_171488_(-1.5f, 0.0f, -1.0f, 3.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(0, 16).m_171488_(-1.5f, 0.0f, -1.0f, 3.0f, 5.0f, 2.0f, new CubeDeformation(-0.2f)), PartPose.m_171419_(0.0f, -4.0f, 0.0f));
        $$3.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(23, 0).m_171488_(-0.75f, -0.5f, -1.0f, 1.0f, 4.0f, 2.0f, new CubeDeformation(-0.01f)), PartPose.m_171419_(-1.75f, 0.5f, 0.0f));
        $$3.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(23, 6).m_171488_(-0.25f, -0.5f, -1.0f, 1.0f, 4.0f, 2.0f, new CubeDeformation(-0.01f)), PartPose.m_171419_(1.75f, 0.5f, 0.0f));
        $$3.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(16, 14).m_171488_(0.0f, 1.0f, 0.0f, 0.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.m_171419_(-0.5f, 0.0f, 0.65f));
        $$3.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(16, 14).m_171488_(0.0f, 1.0f, 0.0f, 0.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.m_171419_(0.5f, 0.0f, 0.65f));
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public void m_6973_(Allay p_233325_, float p_233326_, float p_233327_, float p_233328_, float p_233329_, float p_233330_) {
        float $$18;
        this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
        float $$6 = p_233328_ * 20.0f * ((float)Math.PI / 180) + p_233327_;
        float $$7 = Mth.m_14089_($$6) * (float)Math.PI * 0.15f;
        float $$8 = p_233328_ - (float)p_233325_.f_19797_;
        float $$9 = p_233328_ * 9.0f * ((float)Math.PI / 180);
        float $$10 = Math.min(p_233327_ / 0.3f, 1.0f);
        float $$11 = 1.0f - $$10;
        float $$12 = p_233325_.m_218394_($$8);
        if (p_233325_.m_239559_()) {
            float $$13 = p_233328_ * 8.0f * ((float)Math.PI / 180) + p_233327_;
            float $$14 = Mth.m_14089_($$13) * 16.0f * ((float)Math.PI / 180);
            float $$15 = p_233325_.m_240056_($$8);
            float $$16 = Mth.m_14089_($$13) * 14.0f * ((float)Math.PI / 180);
            float $$17 = Mth.m_14089_($$13) * 30.0f * ((float)Math.PI / 180);
            this.f_233302_.f_104204_ = p_233325_.m_239302_() ? (float)Math.PI * 4 * $$15 : this.f_233302_.f_104204_;
            this.f_233302_.f_104205_ = $$14 * (1.0f - $$15);
            this.f_238177_.f_104204_ = $$17 * (1.0f - $$15);
            this.f_238177_.f_104205_ = $$16 * (1.0f - $$15);
        } else {
            this.f_238177_.f_104203_ = p_233330_ * ((float)Math.PI / 180);
            this.f_238177_.f_104204_ = p_233329_ * ((float)Math.PI / 180);
        }
        this.f_233306_.f_104203_ = 0.43633232f;
        this.f_233306_.f_104204_ = -0.61086524f + $$7;
        this.f_233307_.f_104203_ = 0.43633232f;
        this.f_233307_.f_104204_ = 0.61086524f - $$7;
        this.f_233303_.f_104203_ = $$18 = $$10 * 0.6981317f;
        float $$19 = Mth.m_14179_($$12, $$18, Mth.m_14179_($$10, -1.0471976f, -0.7853982f));
        this.f_233302_.f_104201_ += (float)Math.cos($$9) * 0.25f * $$11;
        this.f_233304_.f_104203_ = $$19;
        this.f_233305_.f_104203_ = $$19;
        float $$20 = $$11 * (1.0f - $$12);
        float $$21 = 0.43633232f - Mth.m_14089_($$9 + 4.712389f) * (float)Math.PI * 0.075f * $$20;
        this.f_233305_.f_104205_ = -$$21;
        this.f_233304_.f_104205_ = $$21;
        this.f_233304_.f_104204_ = 0.27925268f * $$12;
        this.f_233305_.f_104204_ = -0.27925268f * $$12;
    }

    @Override
    public void m_7695_(PoseStack p_233332_, VertexConsumer p_233333_, int p_233334_, int p_233335_, float p_233336_, float p_233337_, float p_233338_, float p_233339_) {
        this.f_233302_.m_104301_(p_233332_, p_233333_, p_233334_, p_233335_);
    }

    @Override
    public void m_6002_(HumanoidArm p_233322_, PoseStack p_233323_) {
        float $$2 = -1.5f;
        float $$3 = 1.5f;
        this.f_233302_.m_104299_(p_233323_);
        this.f_233303_.m_104299_(p_233323_);
        p_233323_.m_85837_(0.0, -0.09375, 0.09375);
        p_233323_.m_85845_(Vector3f.f_122223_.m_122270_(this.f_233304_.f_104203_ + 0.43633232f));
        p_233323_.m_85841_(0.7f, 0.7f, 0.7f);
        p_233323_.m_85837_(0.0625, 0.0, 0.0);
    }
}

