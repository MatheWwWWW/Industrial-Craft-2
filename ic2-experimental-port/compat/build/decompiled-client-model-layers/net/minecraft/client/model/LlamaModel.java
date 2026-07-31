/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

public class LlamaModel<T extends AbstractChestedHorse>
extends EntityModel<T> {
    private final ModelPart f_103031_;
    private final ModelPart f_103032_;
    private final ModelPart f_170717_;
    private final ModelPart f_170718_;
    private final ModelPart f_170719_;
    private final ModelPart f_170720_;
    private final ModelPart f_170721_;
    private final ModelPart f_170722_;

    public LlamaModel(ModelPart p_170724_) {
        this.f_103031_ = p_170724_.m_171324_("head");
        this.f_103032_ = p_170724_.m_171324_("body");
        this.f_170721_ = p_170724_.m_171324_("right_chest");
        this.f_170722_ = p_170724_.m_171324_("left_chest");
        this.f_170717_ = p_170724_.m_171324_("right_hind_leg");
        this.f_170718_ = p_170724_.m_171324_("left_hind_leg");
        this.f_170719_ = p_170724_.m_171324_("right_front_leg");
        this.f_170720_ = p_170724_.m_171324_("left_front_leg");
    }

    public static LayerDefinition m_170725_(CubeDeformation p_170726_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0f, -14.0f, -10.0f, 4.0f, 4.0f, 9.0f, p_170726_).m_171514_(0, 14).m_171525_("neck", -4.0f, -16.0f, -6.0f, 8.0f, 18.0f, 6.0f, p_170726_).m_171514_(17, 0).m_171525_("ear", -4.0f, -19.0f, -4.0f, 3.0f, 3.0f, 2.0f, p_170726_).m_171514_(17, 0).m_171525_("ear", 1.0f, -19.0f, -4.0f, 3.0f, 3.0f, 2.0f, p_170726_), PartPose.m_171419_(0.0f, 7.0f, -6.0f));
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(29, 0).m_171488_(-6.0f, -10.0f, -7.0f, 12.0f, 18.0f, 10.0f, p_170726_), PartPose.m_171423_(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        $$2.m_171599_("right_chest", CubeListBuilder.m_171558_().m_171514_(45, 28).m_171488_(-3.0f, 0.0f, 0.0f, 8.0f, 8.0f, 3.0f, p_170726_), PartPose.m_171423_(-8.5f, 3.0f, 3.0f, 0.0f, 1.5707964f, 0.0f));
        $$2.m_171599_("left_chest", CubeListBuilder.m_171558_().m_171514_(45, 41).m_171488_(-3.0f, 0.0f, 0.0f, 8.0f, 8.0f, 3.0f, p_170726_), PartPose.m_171423_(5.5f, 3.0f, 3.0f, 0.0f, 1.5707964f, 0.0f));
        int $$3 = 4;
        int $$4 = 14;
        CubeListBuilder $$5 = CubeListBuilder.m_171558_().m_171514_(29, 29).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 14.0f, 4.0f, p_170726_);
        $$2.m_171599_("right_hind_leg", $$5, PartPose.m_171419_(-3.5f, 10.0f, 6.0f));
        $$2.m_171599_("left_hind_leg", $$5, PartPose.m_171419_(3.5f, 10.0f, 6.0f));
        $$2.m_171599_("right_front_leg", $$5, PartPose.m_171419_(-3.5f, 10.0f, -5.0f));
        $$2.m_171599_("left_front_leg", $$5, PartPose.m_171419_(3.5f, 10.0f, -5.0f));
        return LayerDefinition.m_171565_($$1, 128, 64);
    }

    @Override
    public void m_6973_(T p_103049_, float p_103050_, float p_103051_, float p_103052_, float p_103053_, float p_103054_) {
        boolean $$6;
        this.f_103031_.f_104203_ = p_103054_ * ((float)Math.PI / 180);
        this.f_103031_.f_104204_ = p_103053_ * ((float)Math.PI / 180);
        this.f_170717_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662f) * 1.4f * p_103051_;
        this.f_170718_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662f + (float)Math.PI) * 1.4f * p_103051_;
        this.f_170719_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662f + (float)Math.PI) * 1.4f * p_103051_;
        this.f_170720_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662f) * 1.4f * p_103051_;
        this.f_170721_.f_104207_ = $$6 = !((AgeableMob)p_103049_).m_6162_() && ((AbstractChestedHorse)p_103049_).m_30502_();
        this.f_170722_.f_104207_ = $$6;
    }

    @Override
    public void m_7695_(PoseStack p_103056_, VertexConsumer p_103057_, int p_103058_, int p_103059_, float p_103060_, float p_103061_, float p_103062_, float p_103063_) {
        if (this.f_102610_) {
            float $$8 = 2.0f;
            p_103056_.m_85836_();
            float $$9 = 0.7f;
            p_103056_.m_85841_(0.71428573f, 0.64935064f, 0.7936508f);
            p_103056_.m_85837_(0.0, 1.3125, 0.22f);
            this.f_103031_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_);
            p_103056_.m_85849_();
            p_103056_.m_85836_();
            float $$10 = 1.1f;
            p_103056_.m_85841_(0.625f, 0.45454544f, 0.45454544f);
            p_103056_.m_85837_(0.0, 2.0625, 0.0);
            this.f_103032_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_);
            p_103056_.m_85849_();
            p_103056_.m_85836_();
            p_103056_.m_85841_(0.45454544f, 0.41322312f, 0.45454544f);
            p_103056_.m_85837_(0.0, 2.0625, 0.0);
            ImmutableList.of((Object)this.f_170717_, (Object)this.f_170718_, (Object)this.f_170719_, (Object)this.f_170720_, (Object)this.f_170721_, (Object)this.f_170722_).forEach(p_103083_ -> p_103083_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_));
            p_103056_.m_85849_();
        } else {
            ImmutableList.of((Object)this.f_103031_, (Object)this.f_103032_, (Object)this.f_170717_, (Object)this.f_170718_, (Object)this.f_170719_, (Object)this.f_170720_, (Object)this.f_170721_, (Object)this.f_170722_).forEach(p_103073_ -> p_103073_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_));
        }
    }
}

