/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;

public class BeeModel<T extends Bee>
extends AgeableListModel<T> {
    private static final float f_170430_ = 19.0f;
    private static final String f_170431_ = "bone";
    private static final String f_170432_ = "stinger";
    private static final String f_170433_ = "left_antenna";
    private static final String f_170434_ = "right_antenna";
    private static final String f_170435_ = "front_legs";
    private static final String f_170436_ = "middle_legs";
    private static final String f_170437_ = "back_legs";
    private final ModelPart f_102206_;
    private final ModelPart f_102208_;
    private final ModelPart f_102209_;
    private final ModelPart f_102210_;
    private final ModelPart f_102211_;
    private final ModelPart f_102212_;
    private final ModelPart f_102213_;
    private final ModelPart f_102214_;
    private final ModelPart f_102215_;
    private float f_102216_;

    public BeeModel(ModelPart p_170439_) {
        super(false, 24.0f, 0.0f);
        this.f_102206_ = p_170439_.m_171324_(f_170431_);
        ModelPart $$1 = this.f_102206_.m_171324_("body");
        this.f_102213_ = $$1.m_171324_(f_170432_);
        this.f_102214_ = $$1.m_171324_(f_170433_);
        this.f_102215_ = $$1.m_171324_(f_170434_);
        this.f_102208_ = this.f_102206_.m_171324_("right_wing");
        this.f_102209_ = this.f_102206_.m_171324_("left_wing");
        this.f_102210_ = this.f_102206_.m_171324_(f_170435_);
        this.f_102211_ = this.f_102206_.m_171324_(f_170436_);
        this.f_102212_ = this.f_102206_.m_171324_(f_170437_);
    }

    public static LayerDefinition m_170440_() {
        float $$0 = 19.0f;
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        PartDefinition $$3 = $$2.m_171599_(f_170431_, CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0f, 19.0f, 0.0f));
        PartDefinition $$4 = $$3.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.5f, -4.0f, -5.0f, 7.0f, 7.0f, 10.0f), PartPose.f_171404_);
        $$4.m_171599_(f_170432_, CubeListBuilder.m_171558_().m_171514_(26, 7).m_171481_(0.0f, -1.0f, 5.0f, 0.0f, 1.0f, 2.0f), PartPose.f_171404_);
        $$4.m_171599_(f_170433_, CubeListBuilder.m_171558_().m_171514_(2, 0).m_171481_(1.5f, -2.0f, -3.0f, 1.0f, 2.0f, 3.0f), PartPose.m_171419_(0.0f, -2.0f, -5.0f));
        $$4.m_171599_(f_170434_, CubeListBuilder.m_171558_().m_171514_(2, 3).m_171481_(-2.5f, -2.0f, -3.0f, 1.0f, 2.0f, 3.0f), PartPose.m_171419_(0.0f, -2.0f, -5.0f));
        CubeDeformation $$5 = new CubeDeformation(0.001f);
        $$3.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-9.0f, 0.0f, 0.0f, 9.0f, 0.0f, 6.0f, $$5), PartPose.m_171423_(-1.5f, -4.0f, -3.0f, 0.0f, -0.2618f, 0.0f));
        $$3.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171480_().m_171488_(0.0f, 0.0f, 0.0f, 9.0f, 0.0f, 6.0f, $$5), PartPose.m_171423_(1.5f, -4.0f, -3.0f, 0.0f, 0.2618f, 0.0f));
        $$3.m_171599_(f_170435_, CubeListBuilder.m_171558_().m_171534_(f_170435_, -5.0f, 0.0f, 0.0f, 7, 2, 0, 26, 1), PartPose.m_171419_(1.5f, 3.0f, -2.0f));
        $$3.m_171599_(f_170436_, CubeListBuilder.m_171558_().m_171534_(f_170436_, -5.0f, 0.0f, 0.0f, 7, 2, 0, 26, 3), PartPose.m_171419_(1.5f, 3.0f, 0.0f));
        $$3.m_171599_(f_170437_, CubeListBuilder.m_171558_().m_171534_(f_170437_, -5.0f, 0.0f, 0.0f, 7, 2, 0, 26, 5), PartPose.m_171419_(1.5f, 3.0f, 2.0f));
        return LayerDefinition.m_171565_($$1, 64, 64);
    }

    @Override
    public void m_6839_(T p_102232_, float p_102233_, float p_102234_, float p_102235_) {
        super.m_6839_(p_102232_, p_102233_, p_102234_, p_102235_);
        this.f_102216_ = ((Bee)p_102232_).m_27935_(p_102235_);
        this.f_102213_.f_104207_ = !((Bee)p_102232_).m_27857_();
    }

    @Override
    public void m_6973_(T p_102237_, float p_102238_, float p_102239_, float p_102240_, float p_102241_, float p_102242_) {
        boolean $$6;
        this.f_102208_.f_104203_ = 0.0f;
        this.f_102214_.f_104203_ = 0.0f;
        this.f_102215_.f_104203_ = 0.0f;
        this.f_102206_.f_104203_ = 0.0f;
        boolean bl = $$6 = ((Entity)p_102237_).m_20096_() && ((Entity)p_102237_).m_20184_().m_82556_() < 1.0E-7;
        if ($$6) {
            this.f_102208_.f_104204_ = -0.2618f;
            this.f_102208_.f_104205_ = 0.0f;
            this.f_102209_.f_104203_ = 0.0f;
            this.f_102209_.f_104204_ = 0.2618f;
            this.f_102209_.f_104205_ = 0.0f;
            this.f_102210_.f_104203_ = 0.0f;
            this.f_102211_.f_104203_ = 0.0f;
            this.f_102212_.f_104203_ = 0.0f;
        } else {
            float $$7 = p_102240_ * 120.32113f * ((float)Math.PI / 180);
            this.f_102208_.f_104204_ = 0.0f;
            this.f_102208_.f_104205_ = Mth.m_14089_($$7) * (float)Math.PI * 0.15f;
            this.f_102209_.f_104203_ = this.f_102208_.f_104203_;
            this.f_102209_.f_104204_ = this.f_102208_.f_104204_;
            this.f_102209_.f_104205_ = -this.f_102208_.f_104205_;
            this.f_102210_.f_104203_ = 0.7853982f;
            this.f_102211_.f_104203_ = 0.7853982f;
            this.f_102212_.f_104203_ = 0.7853982f;
            this.f_102206_.f_104203_ = 0.0f;
            this.f_102206_.f_104204_ = 0.0f;
            this.f_102206_.f_104205_ = 0.0f;
        }
        if (!p_102237_.m_21660_()) {
            this.f_102206_.f_104203_ = 0.0f;
            this.f_102206_.f_104204_ = 0.0f;
            this.f_102206_.f_104205_ = 0.0f;
            if (!$$6) {
                float $$8 = Mth.m_14089_(p_102240_ * 0.18f);
                this.f_102206_.f_104203_ = 0.1f + $$8 * (float)Math.PI * 0.025f;
                this.f_102214_.f_104203_ = $$8 * (float)Math.PI * 0.03f;
                this.f_102215_.f_104203_ = $$8 * (float)Math.PI * 0.03f;
                this.f_102210_.f_104203_ = -$$8 * (float)Math.PI * 0.1f + 0.3926991f;
                this.f_102212_.f_104203_ = -$$8 * (float)Math.PI * 0.05f + 0.7853982f;
                this.f_102206_.f_104201_ = 19.0f - Mth.m_14089_(p_102240_ * 0.18f) * 0.9f;
            }
        }
        if (this.f_102216_ > 0.0f) {
            this.f_102206_.f_104203_ = ModelUtils.m_103125_(this.f_102206_.f_104203_, 3.0915928f, this.f_102216_);
        }
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102206_);
    }
}

