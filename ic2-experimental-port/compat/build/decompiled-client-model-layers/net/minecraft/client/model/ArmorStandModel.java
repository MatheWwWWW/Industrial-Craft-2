/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterables
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandModel
extends ArmorStandArmorModel {
    private static final String f_170349_ = "right_body_stick";
    private static final String f_170350_ = "left_body_stick";
    private static final String f_170351_ = "shoulder_stick";
    private static final String f_170352_ = "base_plate";
    private final ModelPart f_170353_;
    private final ModelPart f_170354_;
    private final ModelPart f_102139_;
    private final ModelPart f_102140_;

    public ArmorStandModel(ModelPart p_170356_) {
        super(p_170356_);
        this.f_170353_ = p_170356_.m_171324_(f_170349_);
        this.f_170354_ = p_170356_.m_171324_(f_170350_);
        this.f_102139_ = p_170356_.m_171324_(f_170351_);
        this.f_102140_ = p_170356_.m_171324_(f_170352_);
        this.f_102809_.f_104207_ = false;
    }

    public static LayerDefinition m_170357_() {
        MeshDefinition $$0 = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0f);
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0f, -7.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.m_171419_(0.0f, 1.0f, 0.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 26).m_171481_(-6.0f, 0.0f, -1.5f, 12.0f, 3.0f, 3.0f), PartPose.f_171404_);
        $$1.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-2.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.m_171419_(-5.0f, 2.0f, 0.0f));
        $$1.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 16).m_171480_().m_171481_(0.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(8, 0).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 11.0f, 2.0f), PartPose.m_171419_(-1.9f, 12.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171480_().m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 11.0f, 2.0f), PartPose.m_171419_(1.9f, 12.0f, 0.0f));
        $$1.m_171599_(f_170349_, CubeListBuilder.m_171558_().m_171514_(16, 0).m_171481_(-3.0f, 3.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_(f_170350_, CubeListBuilder.m_171558_().m_171514_(48, 16).m_171481_(1.0f, 3.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_(f_170351_, CubeListBuilder.m_171558_().m_171514_(0, 48).m_171481_(-4.0f, 10.0f, -1.0f, 8.0f, 2.0f, 2.0f), PartPose.f_171404_);
        $$1.m_171599_(f_170352_, CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-6.0f, 11.0f, -6.0f, 12.0f, 1.0f, 12.0f), PartPose.m_171419_(0.0f, 12.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6839_(ArmorStand p_102172_, float p_102173_, float p_102174_, float p_102175_) {
        this.f_102140_.f_104203_ = 0.0f;
        this.f_102140_.f_104204_ = (float)Math.PI / 180 * -Mth.m_14189_(p_102175_, p_102172_.f_19859_, p_102172_.m_146908_());
        this.f_102140_.f_104205_ = 0.0f;
    }

    @Override
    public void m_6973_(ArmorStand p_102177_, float p_102178_, float p_102179_, float p_102180_, float p_102181_, float p_102182_) {
        super.m_6973_(p_102177_, p_102178_, p_102179_, p_102180_, p_102181_, p_102182_);
        this.f_102812_.f_104207_ = p_102177_.m_31671_();
        this.f_102811_.f_104207_ = p_102177_.m_31671_();
        this.f_102140_.f_104207_ = !p_102177_.m_31674_();
        this.f_170353_.f_104203_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123156_();
        this.f_170353_.f_104204_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123157_();
        this.f_170353_.f_104205_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123158_();
        this.f_170354_.f_104203_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123156_();
        this.f_170354_.f_104204_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123157_();
        this.f_170354_.f_104205_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123158_();
        this.f_102139_.f_104203_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123156_();
        this.f_102139_.f_104204_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123157_();
        this.f_102139_.f_104205_ = (float)Math.PI / 180 * p_102177_.m_31685_().m_123158_();
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return Iterables.concat(super.m_5608_(), (Iterable)ImmutableList.of((Object)this.f_170353_, (Object)this.f_170354_, (Object)this.f_102139_, (Object)this.f_102140_));
    }

    @Override
    public void m_6002_(HumanoidArm p_102157_, PoseStack p_102158_) {
        ModelPart $$2 = this.m_102851_(p_102157_);
        boolean $$3 = $$2.f_104207_;
        $$2.f_104207_ = true;
        super.m_6002_(p_102157_, p_102158_);
        $$2.f_104207_ = $$3;
    }
}

