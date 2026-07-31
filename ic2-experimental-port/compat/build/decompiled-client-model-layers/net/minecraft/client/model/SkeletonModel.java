/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SkeletonModel<T extends Mob>
extends HumanoidModel<T> {
    public SkeletonModel(ModelPart p_170941_) {
        super(p_170941_);
    }

    public static LayerDefinition m_170942_() {
        MeshDefinition $$0 = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0f);
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171481_(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.m_171419_(-5.0f, 2.0f, 0.0f));
        $$1.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171480_().m_171481_(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.m_171419_(-2.0f, 12.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.m_171419_(2.0f, 12.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6839_(T p_103793_, float p_103794_, float p_103795_, float p_103796_) {
        this.f_102816_ = HumanoidModel.ArmPose.EMPTY;
        this.f_102815_ = HumanoidModel.ArmPose.EMPTY;
        ItemStack $$4 = ((LivingEntity)p_103793_).m_21120_(InteractionHand.MAIN_HAND);
        if ($$4.m_150930_(Items.f_42411_) && ((Mob)p_103793_).m_5912_()) {
            if (((Mob)p_103793_).m_5737_() == HumanoidArm.RIGHT) {
                this.f_102816_ = HumanoidModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.f_102815_ = HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
        }
        super.m_6839_(p_103793_, p_103794_, p_103795_, p_103796_);
    }

    @Override
    public void m_6973_(T p_103798_, float p_103799_, float p_103800_, float p_103801_, float p_103802_, float p_103803_) {
        super.m_6973_(p_103798_, p_103799_, p_103800_, p_103801_, p_103802_, p_103803_);
        ItemStack $$6 = ((LivingEntity)p_103798_).m_21205_();
        if (((Mob)p_103798_).m_5912_() && ($$6.m_41619_() || !$$6.m_150930_(Items.f_42411_))) {
            float $$7 = Mth.m_14031_(this.f_102608_ * (float)Math.PI);
            float $$8 = Mth.m_14031_((1.0f - (1.0f - this.f_102608_) * (1.0f - this.f_102608_)) * (float)Math.PI);
            this.f_102811_.f_104205_ = 0.0f;
            this.f_102812_.f_104205_ = 0.0f;
            this.f_102811_.f_104204_ = -(0.1f - $$7 * 0.6f);
            this.f_102812_.f_104204_ = 0.1f - $$7 * 0.6f;
            this.f_102811_.f_104203_ = -1.5707964f;
            this.f_102812_.f_104203_ = -1.5707964f;
            this.f_102811_.f_104203_ -= $$7 * 1.2f - $$8 * 0.4f;
            this.f_102812_.f_104203_ -= $$7 * 1.2f - $$8 * 0.4f;
            AnimationUtils.m_102082_(this.f_102811_, this.f_102812_, p_103801_);
        }
    }

    @Override
    public void m_6002_(HumanoidArm p_103778_, PoseStack p_103779_) {
        float $$2 = p_103778_ == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        ModelPart $$3 = this.m_102851_(p_103778_);
        $$3.f_104200_ += $$2;
        $$3.m_104299_(p_103779_);
        $$3.f_104200_ -= $$2;
    }
}

