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
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Turtle;

public class TurtleModel<T extends Turtle>
extends QuadrupedModel<T> {
    private static final String f_171040_ = "egg_belly";
    private final ModelPart f_103983_;

    public TurtleModel(ModelPart p_171042_) {
        super(p_171042_, true, 120.0f, 0.0f, 9.0f, 6.0f, 120);
        this.f_103983_ = p_171042_.m_171324_(f_171040_);
    }

    public static LayerDefinition m_171043_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(3, 0).m_171481_(-3.0f, -1.0f, -3.0f, 6.0f, 5.0f, 6.0f), PartPose.m_171419_(0.0f, 19.0f, -10.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(7, 37).m_171517_("shell", -9.5f, 3.0f, -10.0f, 19.0f, 20.0f, 6.0f).m_171514_(31, 1).m_171517_("belly", -5.5f, 3.0f, -13.0f, 11.0f, 18.0f, 3.0f), PartPose.m_171423_(0.0f, 11.0f, -10.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.m_171599_(f_171040_, CubeListBuilder.m_171558_().m_171514_(70, 33).m_171481_(-4.5f, 3.0f, -14.0f, 9.0f, 18.0f, 1.0f), PartPose.m_171423_(0.0f, 11.0f, -10.0f, 1.5707964f, 0.0f, 0.0f));
        boolean $$2 = true;
        $$1.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(1, 23).m_171481_(-2.0f, 0.0f, 0.0f, 4.0f, 1.0f, 10.0f), PartPose.m_171419_(-3.5f, 22.0f, 11.0f));
        $$1.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(1, 12).m_171481_(-2.0f, 0.0f, 0.0f, 4.0f, 1.0f, 10.0f), PartPose.m_171419_(3.5f, 22.0f, 11.0f));
        $$1.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(27, 30).m_171481_(-13.0f, 0.0f, -2.0f, 13.0f, 1.0f, 5.0f), PartPose.m_171419_(-5.0f, 21.0f, -4.0f));
        $$1.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(27, 24).m_171481_(0.0f, 0.0f, -2.0f, 13.0f, 1.0f, 5.0f), PartPose.m_171419_(5.0f, 21.0f, -4.0f));
        return LayerDefinition.m_171565_($$0, 128, 64);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return Iterables.concat(super.m_5608_(), (Iterable)ImmutableList.of((Object)this.f_103983_));
    }

    @Override
    public void m_6973_(T p_103994_, float p_103995_, float p_103996_, float p_103997_, float p_103998_, float p_103999_) {
        super.m_6973_(p_103994_, p_103995_, p_103996_, p_103997_, p_103998_, p_103999_);
        this.f_170852_.f_104203_ = Mth.m_14089_(p_103995_ * 0.6662f * 0.6f) * 0.5f * p_103996_;
        this.f_170853_.f_104203_ = Mth.m_14089_(p_103995_ * 0.6662f * 0.6f + (float)Math.PI) * 0.5f * p_103996_;
        this.f_170854_.f_104205_ = Mth.m_14089_(p_103995_ * 0.6662f * 0.6f + (float)Math.PI) * 0.5f * p_103996_;
        this.f_170855_.f_104205_ = Mth.m_14089_(p_103995_ * 0.6662f * 0.6f) * 0.5f * p_103996_;
        this.f_170854_.f_104203_ = 0.0f;
        this.f_170855_.f_104203_ = 0.0f;
        this.f_170854_.f_104204_ = 0.0f;
        this.f_170855_.f_104204_ = 0.0f;
        this.f_170852_.f_104204_ = 0.0f;
        this.f_170853_.f_104204_ = 0.0f;
        if (!((Entity)p_103994_).m_20069_() && ((Entity)p_103994_).m_20096_()) {
            float $$6 = ((Turtle)p_103994_).m_30206_() ? 4.0f : 1.0f;
            float $$7 = ((Turtle)p_103994_).m_30206_() ? 2.0f : 1.0f;
            float $$8 = 5.0f;
            this.f_170854_.f_104204_ = Mth.m_14089_($$6 * p_103995_ * 5.0f + (float)Math.PI) * 8.0f * p_103996_ * $$7;
            this.f_170854_.f_104205_ = 0.0f;
            this.f_170855_.f_104204_ = Mth.m_14089_($$6 * p_103995_ * 5.0f) * 8.0f * p_103996_ * $$7;
            this.f_170855_.f_104205_ = 0.0f;
            this.f_170852_.f_104204_ = Mth.m_14089_(p_103995_ * 5.0f + (float)Math.PI) * 3.0f * p_103996_;
            this.f_170852_.f_104203_ = 0.0f;
            this.f_170853_.f_104204_ = Mth.m_14089_(p_103995_ * 5.0f) * 3.0f * p_103996_;
            this.f_170853_.f_104203_ = 0.0f;
        }
        this.f_103983_.f_104207_ = !this.f_102610_ && ((Turtle)p_103994_).m_30205_();
    }

    @Override
    public void m_7695_(PoseStack p_104001_, VertexConsumer p_104002_, int p_104003_, int p_104004_, float p_104005_, float p_104006_, float p_104007_, float p_104008_) {
        boolean $$8 = this.f_103983_.f_104207_;
        if ($$8) {
            p_104001_.m_85836_();
            p_104001_.m_85837_(0.0, -0.08f, 0.0);
        }
        super.m_7695_(p_104001_, p_104002_, p_104003_, p_104004_, p_104005_, p_104006_, p_104007_, p_104008_);
        if ($$8) {
            p_104001_.m_85849_();
        }
    }
}

