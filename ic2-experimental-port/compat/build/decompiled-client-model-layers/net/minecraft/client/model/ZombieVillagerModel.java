/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieVillagerModel<T extends Zombie>
extends HumanoidModel<T>
implements VillagerHeadModel {
    private final ModelPart f_104156_;

    public ZombieVillagerModel(ModelPart p_171092_) {
        super(p_171092_);
        this.f_104156_ = this.f_102809_.m_171324_("hat_rim");
    }

    public static LayerDefinition m_171095_() {
        MeshDefinition $$0 = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0f);
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", new CubeListBuilder().m_171514_(0, 0).m_171481_(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f).m_171514_(24, 0).m_171481_(-1.0f, -3.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.f_171404_);
        PartDefinition $$2 = $$1.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.f_171404_);
        $$2.m_171599_("hat_rim", CubeListBuilder.m_171558_().m_171514_(30, 47).m_171481_(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f), PartPose.m_171430_(-1.5707964f, 0.0f, 0.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171481_(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).m_171514_(0, 38).m_171488_(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new CubeDeformation(0.05f)), PartPose.f_171404_);
        $$1.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171481_(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(-5.0f, 2.0f, 0.0f));
        $$1.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171480_().m_171481_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(5.0f, 2.0f, 0.0f));
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(-2.0f, 12.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(2.0f, 12.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    public static LayerDefinition m_171093_(CubeDeformation p_171094_) {
        MeshDefinition $$1 = HumanoidModel.m_170681_(p_171094_, 0.0f);
        PartDefinition $$2 = $$1.m_171576_();
        $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, p_171094_), PartPose.f_171404_);
        $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, p_171094_.m_171469_(0.1f)), PartPose.f_171404_);
        $$2.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_171094_.m_171469_(0.1f)), PartPose.m_171419_(-2.0f, 12.0f, 0.0f));
        $$2.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, p_171094_.m_171469_(0.1f)), PartPose.m_171419_(2.0f, 12.0f, 0.0f));
        $$2.m_171597_("hat").m_171599_("hat_rim", CubeListBuilder.m_171558_(), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$1, 64, 32);
    }

    @Override
    public void m_6973_(T p_104175_, float p_104176_, float p_104177_, float p_104178_, float p_104179_, float p_104180_) {
        super.m_6973_(p_104175_, p_104176_, p_104177_, p_104178_, p_104179_, p_104180_);
        AnimationUtils.m_102102_(this.f_102812_, this.f_102811_, ((Mob)p_104175_).m_5912_(), this.f_102608_, p_104178_);
    }

    @Override
    public void m_7491_(boolean p_104182_) {
        this.f_102808_.f_104207_ = p_104182_;
        this.f_102809_.f_104207_ = p_104182_;
        this.f_104156_.f_104207_ = p_104182_;
    }
}

