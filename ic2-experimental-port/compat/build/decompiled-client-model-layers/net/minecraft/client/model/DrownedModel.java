/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DrownedModel<T extends Zombie>
extends ZombieModel<T> {
    public DrownedModel(ModelPart p_170534_) {
        super(p_170534_);
    }

    public static LayerDefinition m_170535_(CubeDeformation p_170536_) {
        MeshDefinition $$1 = HumanoidModel.m_170681_(p_170536_, 0.0f);
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170536_), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
        $$2.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_170536_), PartPose.m_171419_(1.9f, 12.0f, 0.0f));
        return LayerDefinition.m_171565_($$1, 64, 64);
    }

    @Override
    public void m_6839_(T p_102521_, float p_102522_, float p_102523_, float p_102524_) {
        this.f_102816_ = HumanoidModel.ArmPose.EMPTY;
        this.f_102815_ = HumanoidModel.ArmPose.EMPTY;
        ItemStack $$4 = ((LivingEntity)p_102521_).m_21120_(InteractionHand.MAIN_HAND);
        if ($$4.m_150930_(Items.f_42713_) && ((Mob)p_102521_).m_5912_()) {
            if (((Mob)p_102521_).m_5737_() == HumanoidArm.RIGHT) {
                this.f_102816_ = HumanoidModel.ArmPose.THROW_SPEAR;
            } else {
                this.f_102815_ = HumanoidModel.ArmPose.THROW_SPEAR;
            }
        }
        super.m_6839_(p_102521_, p_102522_, p_102523_, p_102524_);
    }

    @Override
    public void m_6973_(T p_102526_, float p_102527_, float p_102528_, float p_102529_, float p_102530_, float p_102531_) {
        super.m_6973_(p_102526_, p_102527_, p_102528_, p_102529_, p_102530_, p_102531_);
        if (this.f_102815_ == HumanoidModel.ArmPose.THROW_SPEAR) {
            this.f_102812_.f_104203_ = this.f_102812_.f_104203_ * 0.5f - (float)Math.PI;
            this.f_102812_.f_104204_ = 0.0f;
        }
        if (this.f_102816_ == HumanoidModel.ArmPose.THROW_SPEAR) {
            this.f_102811_.f_104203_ = this.f_102811_.f_104203_ * 0.5f - (float)Math.PI;
            this.f_102811_.f_104204_ = 0.0f;
        }
        if (this.f_102818_ > 0.0f) {
            this.f_102811_.f_104203_ = this.m_102835_(this.f_102818_, this.f_102811_.f_104203_, -2.5132742f) + this.f_102818_ * 0.35f * Mth.m_14031_(0.1f * p_102529_);
            this.f_102812_.f_104203_ = this.m_102835_(this.f_102818_, this.f_102812_.f_104203_, -2.5132742f) - this.f_102818_ * 0.35f * Mth.m_14031_(0.1f * p_102529_);
            this.f_102811_.f_104205_ = this.m_102835_(this.f_102818_, this.f_102811_.f_104205_, -0.15f);
            this.f_102812_.f_104205_ = this.m_102835_(this.f_102818_, this.f_102812_.f_104205_, 0.15f);
            this.f_102814_.f_104203_ -= this.f_102818_ * 0.55f * Mth.m_14031_(0.1f * p_102529_);
            this.f_102813_.f_104203_ += this.f_102818_ * 0.55f * Mth.m_14031_(0.1f * p_102529_);
            this.f_102808_.f_104203_ = 0.0f;
        }
    }
}

