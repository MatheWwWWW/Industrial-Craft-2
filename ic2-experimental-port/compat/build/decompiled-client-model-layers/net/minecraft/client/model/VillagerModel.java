/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;

public class VillagerModel<T extends Entity>
extends HierarchicalModel<T>
implements HeadedModel,
VillagerHeadModel {
    private final ModelPart f_171047_;
    private final ModelPart f_104036_;
    private final ModelPart f_104037_;
    private final ModelPart f_104038_;
    private final ModelPart f_171048_;
    private final ModelPart f_171049_;
    protected final ModelPart f_104044_;

    public VillagerModel(ModelPart p_171051_) {
        this.f_171047_ = p_171051_;
        this.f_104036_ = p_171051_.m_171324_("head");
        this.f_104037_ = this.f_104036_.m_171324_("hat");
        this.f_104038_ = this.f_104037_.m_171324_("hat_rim");
        this.f_104044_ = this.f_104036_.m_171324_("nose");
        this.f_171048_ = p_171051_.m_171324_("right_leg");
        this.f_171049_ = p_171051_.m_171324_("left_leg");
    }

    public static MeshDefinition m_171052_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = 0.5f;
        PartDefinition $$3 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.f_171404_);
        PartDefinition $$4 = $$3.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, new CubeDeformation(0.51f)), PartPose.f_171404_);
        $$4.m_171599_("hat_rim", CubeListBuilder.m_171558_().m_171514_(30, 47).m_171481_(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f), PartPose.m_171430_(-1.5707964f, 0.0f, 0.0f));
        $$3.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.m_171419_(0.0f, -2.0f, 0.0f));
        PartDefinition $$5 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171481_(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f), PartPose.f_171404_);
        $$5.m_171599_("jacket", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.f_171404_);
        $$1.m_171599_("arms", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171481_(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).m_171514_(44, 22).m_171506_(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, true).m_171514_(40, 38).m_171481_(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), PartPose.m_171423_(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(-2.0f, 12.0f, 0.0f));
        $$1.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171481_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.m_171419_(2.0f, 12.0f, 0.0f));
        return $$0;
    }

    @Override
    public ModelPart m_142109_() {
        return this.f_171047_;
    }

    @Override
    public void m_6973_(T p_104053_, float p_104054_, float p_104055_, float p_104056_, float p_104057_, float p_104058_) {
        boolean $$6 = false;
        if (p_104053_ instanceof AbstractVillager) {
            $$6 = ((AbstractVillager)p_104053_).m_35303_() > 0;
        }
        this.f_104036_.f_104204_ = p_104057_ * ((float)Math.PI / 180);
        this.f_104036_.f_104203_ = p_104058_ * ((float)Math.PI / 180);
        if ($$6) {
            this.f_104036_.f_104205_ = 0.3f * Mth.m_14031_(0.45f * p_104056_);
            this.f_104036_.f_104203_ = 0.4f;
        } else {
            this.f_104036_.f_104205_ = 0.0f;
        }
        this.f_171048_.f_104203_ = Mth.m_14089_(p_104054_ * 0.6662f) * 1.4f * p_104055_ * 0.5f;
        this.f_171049_.f_104203_ = Mth.m_14089_(p_104054_ * 0.6662f + (float)Math.PI) * 1.4f * p_104055_ * 0.5f;
        this.f_171048_.f_104204_ = 0.0f;
        this.f_171049_.f_104204_ = 0.0f;
    }

    @Override
    public ModelPart m_5585_() {
        return this.f_104036_;
    }

    @Override
    public void m_7491_(boolean p_104060_) {
        this.f_104036_.f_104207_ = p_104060_;
        this.f_104037_.f_104207_ = p_104060_;
        this.f_104038_.f_104207_ = p_104060_;
    }
}

