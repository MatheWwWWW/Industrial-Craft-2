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
import net.minecraft.world.entity.monster.Vex;

public class VexModel
extends HumanoidModel<Vex> {
    private final ModelPart f_104010_;
    private final ModelPart f_104011_;

    public VexModel(ModelPart p_171045_) {
        super(p_171045_);
        this.f_102814_.f_104207_ = false;
        this.f_102809_.f_104207_ = false;
        this.f_104011_ = p_171045_.m_171324_("right_wing");
        this.f_104010_ = p_171045_.m_171324_("left_wing");
    }

    public static LayerDefinition m_171046_() {
        MeshDefinition $$0 = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0f);
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-1.0f, -1.0f, -2.0f, 6.0f, 10.0f, 4.0f), PartPose.m_171419_(-1.9f, 12.0f, 0.0f));
        $$1.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-20.0f, 0.0f, 0.0f, 20.0f, 12.0f, 1.0f), PartPose.f_171404_);
        $$1.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 20.0f, 12.0f, 1.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return Iterables.concat(super.m_5608_(), (Iterable)ImmutableList.of((Object)this.f_104011_, (Object)this.f_104010_));
    }

    @Override
    public void m_6973_(Vex p_104028_, float p_104029_, float p_104030_, float p_104031_, float p_104032_, float p_104033_) {
        super.m_6973_(p_104028_, p_104029_, p_104030_, p_104031_, p_104032_, p_104033_);
        if (p_104028_.m_34028_()) {
            if (p_104028_.m_21205_().m_41619_()) {
                this.f_102811_.f_104203_ = 4.712389f;
                this.f_102812_.f_104203_ = 4.712389f;
            } else if (p_104028_.m_5737_() == HumanoidArm.RIGHT) {
                this.f_102811_.f_104203_ = 3.7699115f;
            } else {
                this.f_102812_.f_104203_ = 3.7699115f;
            }
        }
        this.f_102813_.f_104203_ += 0.62831855f;
        this.f_104011_.f_104202_ = 2.0f;
        this.f_104010_.f_104202_ = 2.0f;
        this.f_104011_.f_104201_ = 1.0f;
        this.f_104010_.f_104201_ = 1.0f;
        this.f_104011_.f_104204_ = 0.47123894f + Mth.m_14089_(p_104031_ * 45.836624f * ((float)Math.PI / 180)) * (float)Math.PI * 0.05f;
        this.f_104010_.f_104204_ = -this.f_104011_.f_104204_;
        this.f_104010_.f_104205_ = -0.47123894f;
        this.f_104010_.f_104203_ = 0.47123894f;
        this.f_104011_.f_104203_ = 0.47123894f;
        this.f_104011_.f_104205_ = 0.47123894f;
    }
}

