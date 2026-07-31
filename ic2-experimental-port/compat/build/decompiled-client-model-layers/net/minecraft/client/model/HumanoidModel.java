/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class HumanoidModel<T extends LivingEntity>
extends AgeableListModel<T>
implements ArmedModel,
HeadedModel {
    public static final float f_170673_ = 0.25f;
    public static final float f_170674_ = 0.5f;
    private static final float f_170671_ = 0.2617994f;
    private static final float f_170672_ = 1.9198622f;
    private static final float f_170675_ = 0.2617994f;
    public static final float f_233401_ = 1.4835298f;
    public static final float f_233402_ = 0.5235988f;
    public final ModelPart f_102808_;
    public final ModelPart f_102809_;
    public final ModelPart f_102810_;
    public final ModelPart f_102811_;
    public final ModelPart f_102812_;
    public final ModelPart f_102813_;
    public final ModelPart f_102814_;
    public ArmPose f_102815_ = ArmPose.EMPTY;
    public ArmPose f_102816_ = ArmPose.EMPTY;
    public boolean f_102817_;
    public float f_102818_;

    public HumanoidModel(ModelPart p_170677_) {
        this(p_170677_, RenderType::m_110458_);
    }

    public HumanoidModel(ModelPart p_170679_, Function<ResourceLocation, RenderType> p_170680_) {
        super(p_170680_, true, 16.0f, 0.0f, 2.0f, 2.0f, 24.0f);
        this.f_102808_ = p_170679_.m_171324_("head");
        this.f_102809_ = p_170679_.m_171324_("hat");
        this.f_102810_ = p_170679_.m_171324_("body");
        this.f_102811_ = p_170679_.m_171324_("right_arm");
        this.f_102812_ = p_170679_.m_171324_("left_arm");
        this.f_102813_ = p_170679_.m_171324_("right_leg");
        this.f_102814_ = p_170679_.m_171324_("left_leg");
    }

    public static MeshDefinition m_170681_(CubeDeformation p_170682_, float p_170683_) {
        MeshDefinition $$2 = new MeshDefinition();
        PartDefinition $$3 = $$2.m_171576_();
        $$3.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, p_170682_), PartPose.m_171419_(0.0f, 0.0f + p_170683_, 0.0f));
        $$3.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, p_170682_.m_171469_(0.5f)), PartPose.m_171419_(0.0f, 0.0f + p_170683_, 0.0f));
        $$3.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, p_170682_), PartPose.m_171419_(0.0f, 0.0f + p_170683_, 0.0f));
        $$3.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170682_), PartPose.m_171419_(-5.0f, 2.0f + p_170683_, 0.0f));
        $$3.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171480_().m_171488_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170682_), PartPose.m_171419_(5.0f, 2.0f + p_170683_, 0.0f));
        $$3.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170682_), PartPose.m_171419_(-1.9f, 12.0f + p_170683_, 0.0f));
        $$3.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170682_), PartPose.m_171419_(1.9f, 12.0f + p_170683_, 0.0f));
        return $$2;
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_102808_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102810_, (Object)this.f_102811_, (Object)this.f_102812_, (Object)this.f_102813_, (Object)this.f_102814_, (Object)this.f_102809_);
    }

    @Override
    public void m_6839_(T p_102861_, float p_102862_, float p_102863_, float p_102864_) {
        this.f_102818_ = ((LivingEntity)p_102861_).m_20998_(p_102864_);
        super.m_6839_(p_102861_, p_102862_, p_102863_, p_102864_);
    }

    @Override
    public void m_6973_(T p_102866_, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {
        boolean $$9;
        boolean $$6 = ((LivingEntity)p_102866_).m_21256_() > 4;
        boolean $$7 = ((LivingEntity)p_102866_).m_6067_();
        this.f_102808_.f_104204_ = p_102870_ * ((float)Math.PI / 180);
        this.f_102808_.f_104203_ = $$6 ? -0.7853982f : (this.f_102818_ > 0.0f ? ($$7 ? this.m_102835_(this.f_102818_, this.f_102808_.f_104203_, -0.7853982f) : this.m_102835_(this.f_102818_, this.f_102808_.f_104203_, p_102871_ * ((float)Math.PI / 180))) : p_102871_ * ((float)Math.PI / 180));
        this.f_102810_.f_104204_ = 0.0f;
        this.f_102811_.f_104202_ = 0.0f;
        this.f_102811_.f_104200_ = -5.0f;
        this.f_102812_.f_104202_ = 0.0f;
        this.f_102812_.f_104200_ = 5.0f;
        float $$8 = 1.0f;
        if ($$6) {
            $$8 = (float)((Entity)p_102866_).m_20184_().m_82556_();
            $$8 /= 0.2f;
            $$8 *= $$8 * $$8;
        }
        if ($$8 < 1.0f) {
            $$8 = 1.0f;
        }
        this.f_102811_.f_104203_ = Mth.m_14089_(p_102867_ * 0.6662f + (float)Math.PI) * 2.0f * p_102868_ * 0.5f / $$8;
        this.f_102812_.f_104203_ = Mth.m_14089_(p_102867_ * 0.6662f) * 2.0f * p_102868_ * 0.5f / $$8;
        this.f_102811_.f_104205_ = 0.0f;
        this.f_102812_.f_104205_ = 0.0f;
        this.f_102813_.f_104203_ = Mth.m_14089_(p_102867_ * 0.6662f) * 1.4f * p_102868_ / $$8;
        this.f_102814_.f_104203_ = Mth.m_14089_(p_102867_ * 0.6662f + (float)Math.PI) * 1.4f * p_102868_ / $$8;
        this.f_102813_.f_104204_ = 0.0f;
        this.f_102814_.f_104204_ = 0.0f;
        this.f_102813_.f_104205_ = 0.0f;
        this.f_102814_.f_104205_ = 0.0f;
        if (this.f_102609_) {
            this.f_102811_.f_104203_ += -0.62831855f;
            this.f_102812_.f_104203_ += -0.62831855f;
            this.f_102813_.f_104203_ = -1.4137167f;
            this.f_102813_.f_104204_ = 0.31415927f;
            this.f_102813_.f_104205_ = 0.07853982f;
            this.f_102814_.f_104203_ = -1.4137167f;
            this.f_102814_.f_104204_ = -0.31415927f;
            this.f_102814_.f_104205_ = -0.07853982f;
        }
        this.f_102811_.f_104204_ = 0.0f;
        this.f_102812_.f_104204_ = 0.0f;
        boolean bl = $$9 = ((LivingEntity)p_102866_).m_5737_() == HumanoidArm.RIGHT;
        if (((LivingEntity)p_102866_).m_6117_()) {
            boolean $$10;
            boolean bl2 = $$10 = ((LivingEntity)p_102866_).m_7655_() == InteractionHand.MAIN_HAND;
            if ($$10 == $$9) {
                this.m_102875_(p_102866_);
            } else {
                this.m_102878_(p_102866_);
            }
        } else {
            boolean $$11;
            boolean bl3 = $$11 = $$9 ? this.f_102815_.m_102897_() : this.f_102816_.m_102897_();
            if ($$9 != $$11) {
                this.m_102878_(p_102866_);
                this.m_102875_(p_102866_);
            } else {
                this.m_102875_(p_102866_);
                this.m_102878_(p_102866_);
            }
        }
        this.m_7884_(p_102866_, p_102869_);
        if (this.f_102817_) {
            this.f_102810_.f_104203_ = 0.5f;
            this.f_102811_.f_104203_ += 0.4f;
            this.f_102812_.f_104203_ += 0.4f;
            this.f_102813_.f_104202_ = 4.0f;
            this.f_102814_.f_104202_ = 4.0f;
            this.f_102813_.f_104201_ = 12.2f;
            this.f_102814_.f_104201_ = 12.2f;
            this.f_102808_.f_104201_ = 4.2f;
            this.f_102810_.f_104201_ = 3.2f;
            this.f_102812_.f_104201_ = 5.2f;
            this.f_102811_.f_104201_ = 5.2f;
        } else {
            this.f_102810_.f_104203_ = 0.0f;
            this.f_102813_.f_104202_ = 0.1f;
            this.f_102814_.f_104202_ = 0.1f;
            this.f_102813_.f_104201_ = 12.0f;
            this.f_102814_.f_104201_ = 12.0f;
            this.f_102808_.f_104201_ = 0.0f;
            this.f_102810_.f_104201_ = 0.0f;
            this.f_102812_.f_104201_ = 2.0f;
            this.f_102811_.f_104201_ = 2.0f;
        }
        if (this.f_102816_ != ArmPose.SPYGLASS) {
            AnimationUtils.m_170341_(this.f_102811_, p_102869_, 1.0f);
        }
        if (this.f_102815_ != ArmPose.SPYGLASS) {
            AnimationUtils.m_170341_(this.f_102812_, p_102869_, -1.0f);
        }
        if (this.f_102818_ > 0.0f) {
            float $$15;
            float $$12 = p_102867_ % 26.0f;
            HumanoidArm $$13 = this.m_102856_(p_102866_);
            float $$14 = $$13 == HumanoidArm.RIGHT && this.f_102608_ > 0.0f ? 0.0f : this.f_102818_;
            float f = $$15 = $$13 == HumanoidArm.LEFT && this.f_102608_ > 0.0f ? 0.0f : this.f_102818_;
            if (!((LivingEntity)p_102866_).m_6117_()) {
                if ($$12 < 14.0f) {
                    this.f_102812_.f_104203_ = this.m_102835_($$15, this.f_102812_.f_104203_, 0.0f);
                    this.f_102811_.f_104203_ = Mth.m_14179_($$14, this.f_102811_.f_104203_, 0.0f);
                    this.f_102812_.f_104204_ = this.m_102835_($$15, this.f_102812_.f_104204_, (float)Math.PI);
                    this.f_102811_.f_104204_ = Mth.m_14179_($$14, this.f_102811_.f_104204_, (float)Math.PI);
                    this.f_102812_.f_104205_ = this.m_102835_($$15, this.f_102812_.f_104205_, (float)Math.PI + 1.8707964f * this.m_102833_($$12) / this.m_102833_(14.0f));
                    this.f_102811_.f_104205_ = Mth.m_14179_($$14, this.f_102811_.f_104205_, (float)Math.PI - 1.8707964f * this.m_102833_($$12) / this.m_102833_(14.0f));
                } else if ($$12 >= 14.0f && $$12 < 22.0f) {
                    float $$16 = ($$12 - 14.0f) / 8.0f;
                    this.f_102812_.f_104203_ = this.m_102835_($$15, this.f_102812_.f_104203_, 1.5707964f * $$16);
                    this.f_102811_.f_104203_ = Mth.m_14179_($$14, this.f_102811_.f_104203_, 1.5707964f * $$16);
                    this.f_102812_.f_104204_ = this.m_102835_($$15, this.f_102812_.f_104204_, (float)Math.PI);
                    this.f_102811_.f_104204_ = Mth.m_14179_($$14, this.f_102811_.f_104204_, (float)Math.PI);
                    this.f_102812_.f_104205_ = this.m_102835_($$15, this.f_102812_.f_104205_, 5.012389f - 1.8707964f * $$16);
                    this.f_102811_.f_104205_ = Mth.m_14179_($$14, this.f_102811_.f_104205_, 1.2707963f + 1.8707964f * $$16);
                } else if ($$12 >= 22.0f && $$12 < 26.0f) {
                    float $$17 = ($$12 - 22.0f) / 4.0f;
                    this.f_102812_.f_104203_ = this.m_102835_($$15, this.f_102812_.f_104203_, 1.5707964f - 1.5707964f * $$17);
                    this.f_102811_.f_104203_ = Mth.m_14179_($$14, this.f_102811_.f_104203_, 1.5707964f - 1.5707964f * $$17);
                    this.f_102812_.f_104204_ = this.m_102835_($$15, this.f_102812_.f_104204_, (float)Math.PI);
                    this.f_102811_.f_104204_ = Mth.m_14179_($$14, this.f_102811_.f_104204_, (float)Math.PI);
                    this.f_102812_.f_104205_ = this.m_102835_($$15, this.f_102812_.f_104205_, (float)Math.PI);
                    this.f_102811_.f_104205_ = Mth.m_14179_($$14, this.f_102811_.f_104205_, (float)Math.PI);
                }
            }
            float $$18 = 0.3f;
            float $$19 = 0.33333334f;
            this.f_102814_.f_104203_ = Mth.m_14179_(this.f_102818_, this.f_102814_.f_104203_, 0.3f * Mth.m_14089_(p_102867_ * 0.33333334f + (float)Math.PI));
            this.f_102813_.f_104203_ = Mth.m_14179_(this.f_102818_, this.f_102813_.f_104203_, 0.3f * Mth.m_14089_(p_102867_ * 0.33333334f));
        }
        this.f_102809_.m_104315_(this.f_102808_);
    }

    private void m_102875_(T p_102876_) {
        switch (this.f_102816_) {
            case EMPTY: {
                this.f_102811_.f_104204_ = 0.0f;
                break;
            }
            case BLOCK: {
                this.f_102811_.f_104203_ = this.f_102811_.f_104203_ * 0.5f - 0.9424779f;
                this.f_102811_.f_104204_ = -0.5235988f;
                break;
            }
            case ITEM: {
                this.f_102811_.f_104203_ = this.f_102811_.f_104203_ * 0.5f - 0.31415927f;
                this.f_102811_.f_104204_ = 0.0f;
                break;
            }
            case THROW_SPEAR: {
                this.f_102811_.f_104203_ = this.f_102811_.f_104203_ * 0.5f - (float)Math.PI;
                this.f_102811_.f_104204_ = 0.0f;
                break;
            }
            case BOW_AND_ARROW: {
                this.f_102811_.f_104204_ = -0.1f + this.f_102808_.f_104204_;
                this.f_102812_.f_104204_ = 0.1f + this.f_102808_.f_104204_ + 0.4f;
                this.f_102811_.f_104203_ = -1.5707964f + this.f_102808_.f_104203_;
                this.f_102812_.f_104203_ = -1.5707964f + this.f_102808_.f_104203_;
                break;
            }
            case CROSSBOW_CHARGE: {
                AnimationUtils.m_102086_(this.f_102811_, this.f_102812_, p_102876_, true);
                break;
            }
            case CROSSBOW_HOLD: {
                AnimationUtils.m_102097_(this.f_102811_, this.f_102812_, this.f_102808_, true);
                break;
            }
            case SPYGLASS: {
                this.f_102811_.f_104203_ = Mth.m_14036_(this.f_102808_.f_104203_ - 1.9198622f - (((Entity)p_102876_).m_6047_() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.f_102811_.f_104204_ = this.f_102808_.f_104204_ - 0.2617994f;
                break;
            }
            case TOOT_HORN: {
                this.f_102811_.f_104203_ = Mth.m_14036_(this.f_102808_.f_104203_, -1.2f, 1.2f) - 1.4835298f;
                this.f_102811_.f_104204_ = this.f_102808_.f_104204_ - 0.5235988f;
            }
        }
    }

    private void m_102878_(T p_102879_) {
        switch (this.f_102815_) {
            case EMPTY: {
                this.f_102812_.f_104204_ = 0.0f;
                break;
            }
            case BLOCK: {
                this.f_102812_.f_104203_ = this.f_102812_.f_104203_ * 0.5f - 0.9424779f;
                this.f_102812_.f_104204_ = 0.5235988f;
                break;
            }
            case ITEM: {
                this.f_102812_.f_104203_ = this.f_102812_.f_104203_ * 0.5f - 0.31415927f;
                this.f_102812_.f_104204_ = 0.0f;
                break;
            }
            case THROW_SPEAR: {
                this.f_102812_.f_104203_ = this.f_102812_.f_104203_ * 0.5f - (float)Math.PI;
                this.f_102812_.f_104204_ = 0.0f;
                break;
            }
            case BOW_AND_ARROW: {
                this.f_102811_.f_104204_ = -0.1f + this.f_102808_.f_104204_ - 0.4f;
                this.f_102812_.f_104204_ = 0.1f + this.f_102808_.f_104204_;
                this.f_102811_.f_104203_ = -1.5707964f + this.f_102808_.f_104203_;
                this.f_102812_.f_104203_ = -1.5707964f + this.f_102808_.f_104203_;
                break;
            }
            case CROSSBOW_CHARGE: {
                AnimationUtils.m_102086_(this.f_102811_, this.f_102812_, p_102879_, false);
                break;
            }
            case CROSSBOW_HOLD: {
                AnimationUtils.m_102097_(this.f_102811_, this.f_102812_, this.f_102808_, false);
                break;
            }
            case SPYGLASS: {
                this.f_102812_.f_104203_ = Mth.m_14036_(this.f_102808_.f_104203_ - 1.9198622f - (((Entity)p_102879_).m_6047_() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.f_102812_.f_104204_ = this.f_102808_.f_104204_ + 0.2617994f;
                break;
            }
            case TOOT_HORN: {
                this.f_102812_.f_104203_ = Mth.m_14036_(this.f_102808_.f_104203_, -1.2f, 1.2f) - 1.4835298f;
                this.f_102812_.f_104204_ = this.f_102808_.f_104204_ + 0.5235988f;
            }
        }
    }

    protected void m_7884_(T p_102858_, float p_102859_) {
        if (this.f_102608_ <= 0.0f) {
            return;
        }
        HumanoidArm $$2 = this.m_102856_(p_102858_);
        ModelPart $$3 = this.m_102851_($$2);
        float $$4 = this.f_102608_;
        this.f_102810_.f_104204_ = Mth.m_14031_(Mth.m_14116_($$4) * ((float)Math.PI * 2)) * 0.2f;
        if ($$2 == HumanoidArm.LEFT) {
            this.f_102810_.f_104204_ *= -1.0f;
        }
        this.f_102811_.f_104202_ = Mth.m_14031_(this.f_102810_.f_104204_) * 5.0f;
        this.f_102811_.f_104200_ = -Mth.m_14089_(this.f_102810_.f_104204_) * 5.0f;
        this.f_102812_.f_104202_ = -Mth.m_14031_(this.f_102810_.f_104204_) * 5.0f;
        this.f_102812_.f_104200_ = Mth.m_14089_(this.f_102810_.f_104204_) * 5.0f;
        this.f_102811_.f_104204_ += this.f_102810_.f_104204_;
        this.f_102812_.f_104204_ += this.f_102810_.f_104204_;
        this.f_102812_.f_104203_ += this.f_102810_.f_104204_;
        $$4 = 1.0f - this.f_102608_;
        $$4 *= $$4;
        $$4 *= $$4;
        $$4 = 1.0f - $$4;
        float $$5 = Mth.m_14031_($$4 * (float)Math.PI);
        float $$6 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.f_102808_.f_104203_ - 0.7f) * 0.75f;
        $$3.f_104203_ -= $$5 * 1.2f + $$6;
        $$3.f_104204_ += this.f_102810_.f_104204_ * 2.0f;
        $$3.f_104205_ += Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.4f;
    }

    protected float m_102835_(float p_102836_, float p_102837_, float p_102838_) {
        float $$3 = (p_102838_ - p_102837_) % ((float)Math.PI * 2);
        if ($$3 < (float)(-Math.PI)) {
            $$3 += (float)Math.PI * 2;
        }
        if ($$3 >= (float)Math.PI) {
            $$3 -= (float)Math.PI * 2;
        }
        return p_102837_ + p_102836_ * $$3;
    }

    private float m_102833_(float p_102834_) {
        return -65.0f * p_102834_ + p_102834_ * p_102834_;
    }

    public void m_102872_(HumanoidModel<T> p_102873_) {
        super.m_102624_(p_102873_);
        p_102873_.f_102815_ = this.f_102815_;
        p_102873_.f_102816_ = this.f_102816_;
        p_102873_.f_102817_ = this.f_102817_;
        p_102873_.f_102808_.m_104315_(this.f_102808_);
        p_102873_.f_102809_.m_104315_(this.f_102809_);
        p_102873_.f_102810_.m_104315_(this.f_102810_);
        p_102873_.f_102811_.m_104315_(this.f_102811_);
        p_102873_.f_102812_.m_104315_(this.f_102812_);
        p_102873_.f_102813_.m_104315_(this.f_102813_);
        p_102873_.f_102814_.m_104315_(this.f_102814_);
    }

    public void m_8009_(boolean p_102880_) {
        this.f_102808_.f_104207_ = p_102880_;
        this.f_102809_.f_104207_ = p_102880_;
        this.f_102810_.f_104207_ = p_102880_;
        this.f_102811_.f_104207_ = p_102880_;
        this.f_102812_.f_104207_ = p_102880_;
        this.f_102813_.f_104207_ = p_102880_;
        this.f_102814_.f_104207_ = p_102880_;
    }

    @Override
    public void m_6002_(HumanoidArm p_102854_, PoseStack p_102855_) {
        this.m_102851_(p_102854_).m_104299_(p_102855_);
    }

    protected ModelPart m_102851_(HumanoidArm p_102852_) {
        if (p_102852_ == HumanoidArm.LEFT) {
            return this.f_102812_;
        }
        return this.f_102811_;
    }

    @Override
    public ModelPart m_5585_() {
        return this.f_102808_;
    }

    private HumanoidArm m_102856_(T p_102857_) {
        HumanoidArm $$1 = ((LivingEntity)p_102857_).m_5737_();
        return ((LivingEntity)p_102857_).f_20912_ == InteractionHand.MAIN_HAND ? $$1 : $$1.m_20828_();
    }

    public static final class ArmPose
    extends Enum<ArmPose> {
        public static final /* enum */ ArmPose EMPTY = new ArmPose(false);
        public static final /* enum */ ArmPose ITEM = new ArmPose(false);
        public static final /* enum */ ArmPose BLOCK = new ArmPose(false);
        public static final /* enum */ ArmPose BOW_AND_ARROW = new ArmPose(true);
        public static final /* enum */ ArmPose THROW_SPEAR = new ArmPose(false);
        public static final /* enum */ ArmPose CROSSBOW_CHARGE = new ArmPose(true);
        public static final /* enum */ ArmPose CROSSBOW_HOLD = new ArmPose(true);
        public static final /* enum */ ArmPose SPYGLASS = new ArmPose(false);
        public static final /* enum */ ArmPose TOOT_HORN = new ArmPose(false);
        private final boolean f_102890_;
        private static final /* synthetic */ ArmPose[] $VALUES;

        public static ArmPose[] values() {
            return (ArmPose[])$VALUES.clone();
        }

        public static ArmPose valueOf(String p_102899_) {
            return Enum.valueOf(ArmPose.class, p_102899_);
        }

        private ArmPose(boolean p_102896_) {
            this.f_102890_ = p_102896_;
        }

        public boolean m_102897_() {
            return this.f_102890_;
        }

        private static /* synthetic */ ArmPose[] m_170685_() {
            return new ArmPose[]{EMPTY, ITEM, BLOCK, BOW_AND_ARROW, THROW_SPEAR, CROSSBOW_CHARGE, CROSSBOW_HOLD, SPYGLASS, TOOT_HORN};
        }

        static {
            $VALUES = ArmPose.m_170685_();
        }
    }
}

