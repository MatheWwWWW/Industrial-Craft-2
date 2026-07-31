/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ElytraModel<T extends LivingEntity>
extends AgeableListModel<T> {
    private final ModelPart f_102532_;
    private final ModelPart f_102533_;

    public ElytraModel(ModelPart p_170538_) {
        this.f_102533_ = p_170538_.m_171324_("left_wing");
        this.f_102532_ = p_170538_.m_171324_("right_wing");
    }

    public static LayerDefinition m_170539_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        CubeDeformation $$2 = new CubeDeformation(1.0f);
        $$1.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171488_(-10.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, $$2), PartPose.m_171423_(5.0f, 0.0f, 0.0f, 0.2617994f, 0.0f, -0.2617994f));
        $$1.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171480_().m_171488_(0.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, $$2), PartPose.m_171423_(-5.0f, 0.0f, 0.0f, 0.2617994f, 0.0f, 0.2617994f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102533_, (Object)this.f_102532_);
    }

    @Override
    public void m_6973_(T p_102544_, float p_102545_, float p_102546_, float p_102547_, float p_102548_, float p_102549_) {
        float $$6 = 0.2617994f;
        float $$7 = -0.2617994f;
        float $$8 = 0.0f;
        float $$9 = 0.0f;
        if (((LivingEntity)p_102544_).m_21255_()) {
            float $$10 = 1.0f;
            Vec3 $$11 = ((Entity)p_102544_).m_20184_();
            if ($$11.f_82480_ < 0.0) {
                Vec3 $$12 = $$11.m_82541_();
                $$10 = 1.0f - (float)Math.pow(-$$12.f_82480_, 1.5);
            }
            $$6 = $$10 * 0.34906584f + (1.0f - $$10) * $$6;
            $$7 = $$10 * -1.5707964f + (1.0f - $$10) * $$7;
        } else if (((Entity)p_102544_).m_6047_()) {
            $$6 = 0.6981317f;
            $$7 = -0.7853982f;
            $$8 = 3.0f;
            $$9 = 0.08726646f;
        }
        this.f_102533_.f_104201_ = $$8;
        if (p_102544_ instanceof AbstractClientPlayer) {
            AbstractClientPlayer $$13 = (AbstractClientPlayer)p_102544_;
            $$13.f_108542_ += ($$6 - $$13.f_108542_) * 0.1f;
            $$13.f_108543_ += ($$9 - $$13.f_108543_) * 0.1f;
            $$13.f_108544_ += ($$7 - $$13.f_108544_) * 0.1f;
            this.f_102533_.f_104203_ = $$13.f_108542_;
            this.f_102533_.f_104204_ = $$13.f_108543_;
            this.f_102533_.f_104205_ = $$13.f_108544_;
        } else {
            this.f_102533_.f_104203_ = $$6;
            this.f_102533_.f_104205_ = $$7;
            this.f_102533_.f_104204_ = $$9;
        }
        this.f_102532_.f_104204_ = -this.f_102533_.f_104204_;
        this.f_102532_.f_104201_ = this.f_102533_.f_104201_;
        this.f_102532_.f_104203_ = this.f_102533_.f_104203_;
        this.f_102532_.f_104205_ = -this.f_102533_.f_104205_;
    }
}

