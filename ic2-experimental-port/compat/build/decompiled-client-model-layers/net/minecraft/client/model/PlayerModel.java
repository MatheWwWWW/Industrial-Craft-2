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
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class PlayerModel<T extends LivingEntity>
extends HumanoidModel<T> {
    private static final String f_170816_ = "ear";
    private static final String f_170817_ = "cloak";
    private static final String f_170818_ = "left_sleeve";
    private static final String f_170819_ = "right_sleeve";
    private static final String f_170813_ = "left_pants";
    private static final String f_170814_ = "right_pants";
    private final List<ModelPart> f_170815_;
    public final ModelPart f_103374_;
    public final ModelPart f_103375_;
    public final ModelPart f_103376_;
    public final ModelPart f_103377_;
    public final ModelPart f_103378_;
    private final ModelPart f_103373_;
    private final ModelPart f_103379_;
    private final boolean f_103380_;

    public PlayerModel(ModelPart p_170821_, boolean p_170822_) {
        super(p_170821_, RenderType::m_110473_);
        this.f_103380_ = p_170822_;
        this.f_103379_ = p_170821_.m_171324_(f_170816_);
        this.f_103373_ = p_170821_.m_171324_(f_170817_);
        this.f_103374_ = p_170821_.m_171324_(f_170818_);
        this.f_103375_ = p_170821_.m_171324_(f_170819_);
        this.f_103376_ = p_170821_.m_171324_(f_170813_);
        this.f_103377_ = p_170821_.m_171324_(f_170814_);
        this.f_103378_ = p_170821_.m_171324_("jacket");
        this.f_170815_ = (List)p_170821_.m_171331_().filter(p_170824_ -> !p_170824_.m_171326_()).collect(ImmutableList.toImmutableList());
    }

    public static MeshDefinition m_170825_(CubeDeformation p_170826_, boolean p_170827_) {
        MeshDefinition $$2 = HumanoidModel.m_170681_(p_170826_, 0.0f);
        PartDefinition $$3 = $$2.m_171576_();
        $$3.m_171599_(f_170816_, CubeListBuilder.m_171558_().m_171514_(24, 0).m_171488_(-3.0f, -6.0f, -1.0f, 6.0f, 6.0f, 1.0f, p_170826_), PartPose.f_171404_);
        $$3.m_171599_(f_170817_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171496_(-5.0f, 0.0f, -1.0f, 10.0f, 16.0f, 1.0f, p_170826_, 1.0f, 0.5f), PartPose.m_171419_(0.0f, 0.0f, 0.0f));
        float $$4 = 0.25f;
        if (p_170827_) {
            $$3.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, p_170826_), PartPose.m_171419_(5.0f, 2.5f, 0.0f));
            $$3.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, p_170826_), PartPose.m_171419_(-5.0f, 2.5f, 0.0f));
            $$3.m_171599_(f_170818_, CubeListBuilder.m_171558_().m_171514_(48, 48).m_171488_(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.m_171419_(5.0f, 2.5f, 0.0f));
            $$3.m_171599_(f_170819_, CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.m_171419_(-5.0f, 2.5f, 0.0f));
        } else {
            $$3.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170826_), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
            $$3.m_171599_(f_170818_, CubeListBuilder.m_171558_().m_171514_(48, 48).m_171488_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
            $$3.m_171599_(f_170819_, CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.m_171419_(-5.0f, 2.0f, 0.0f));
        }
        $$3.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170826_), PartPose.m_171419_(1.9f, 12.0f, 0.0f));
        $$3.m_171599_(f_170813_, CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.m_171419_(1.9f, 12.0f, 0.0f));
        $$3.m_171599_(f_170814_, CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.m_171419_(-1.9f, 12.0f, 0.0f));
        $$3.m_171599_("jacket", CubeListBuilder.m_171558_().m_171514_(16, 32).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, p_170826_.m_171469_(0.25f)), PartPose.f_171404_);
        return $$2;
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return Iterables.concat(super.m_5608_(), (Iterable)ImmutableList.of((Object)this.f_103376_, (Object)this.f_103377_, (Object)this.f_103374_, (Object)this.f_103375_, (Object)this.f_103378_));
    }

    public void m_103401_(PoseStack p_103402_, VertexConsumer p_103403_, int p_103404_, int p_103405_) {
        this.f_103379_.m_104315_(this.f_102808_);
        this.f_103379_.f_104200_ = 0.0f;
        this.f_103379_.f_104201_ = 0.0f;
        this.f_103379_.m_104301_(p_103402_, p_103403_, p_103404_, p_103405_);
    }

    public void m_103411_(PoseStack p_103412_, VertexConsumer p_103413_, int p_103414_, int p_103415_) {
        this.f_103373_.m_104301_(p_103412_, p_103413_, p_103414_, p_103415_);
    }

    @Override
    public void m_6973_(T p_103395_, float p_103396_, float p_103397_, float p_103398_, float p_103399_, float p_103400_) {
        super.m_6973_(p_103395_, p_103396_, p_103397_, p_103398_, p_103399_, p_103400_);
        this.f_103376_.m_104315_(this.f_102814_);
        this.f_103377_.m_104315_(this.f_102813_);
        this.f_103374_.m_104315_(this.f_102812_);
        this.f_103375_.m_104315_(this.f_102811_);
        this.f_103378_.m_104315_(this.f_102810_);
        if (((LivingEntity)p_103395_).m_6844_(EquipmentSlot.CHEST).m_41619_()) {
            if (((Entity)p_103395_).m_6047_()) {
                this.f_103373_.f_104202_ = 1.4f;
                this.f_103373_.f_104201_ = 1.85f;
            } else {
                this.f_103373_.f_104202_ = 0.0f;
                this.f_103373_.f_104201_ = 0.0f;
            }
        } else if (((Entity)p_103395_).m_6047_()) {
            this.f_103373_.f_104202_ = 0.3f;
            this.f_103373_.f_104201_ = 0.8f;
        } else {
            this.f_103373_.f_104202_ = -1.1f;
            this.f_103373_.f_104201_ = -0.85f;
        }
    }

    @Override
    public void m_8009_(boolean p_103419_) {
        super.m_8009_(p_103419_);
        this.f_103374_.f_104207_ = p_103419_;
        this.f_103375_.f_104207_ = p_103419_;
        this.f_103376_.f_104207_ = p_103419_;
        this.f_103377_.f_104207_ = p_103419_;
        this.f_103378_.f_104207_ = p_103419_;
        this.f_103373_.f_104207_ = p_103419_;
        this.f_103379_.f_104207_ = p_103419_;
    }

    @Override
    public void m_6002_(HumanoidArm p_103392_, PoseStack p_103393_) {
        ModelPart $$2 = this.m_102851_(p_103392_);
        if (this.f_103380_) {
            float $$3 = 0.5f * (float)(p_103392_ == HumanoidArm.RIGHT ? 1 : -1);
            $$2.f_104200_ += $$3;
            $$2.m_104299_(p_103393_);
            $$2.f_104200_ -= $$3;
        } else {
            $$2.m_104299_(p_103393_);
        }
    }

    public ModelPart m_233438_(RandomSource p_233439_) {
        return this.f_170815_.get(p_233439_.m_188503_(this.f_170815_.size()));
    }
}

