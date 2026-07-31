/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;

public class WolfModel<T extends Wolf>
extends ColorableAgeableListModel<T> {
    private static final String f_171078_ = "real_head";
    private static final String f_171079_ = "upper_body";
    private static final String f_171080_ = "real_tail";
    private final ModelPart f_104107_;
    private final ModelPart f_104108_;
    private final ModelPart f_104109_;
    private final ModelPart f_171081_;
    private final ModelPart f_171082_;
    private final ModelPart f_171083_;
    private final ModelPart f_171084_;
    private final ModelPart f_104114_;
    private final ModelPart f_104115_;
    private final ModelPart f_104116_;
    private static final int f_171085_ = 8;

    public WolfModel(ModelPart p_171087_) {
        this.f_104107_ = p_171087_.m_171324_("head");
        this.f_104108_ = this.f_104107_.m_171324_(f_171078_);
        this.f_104109_ = p_171087_.m_171324_("body");
        this.f_104116_ = p_171087_.m_171324_(f_171079_);
        this.f_171081_ = p_171087_.m_171324_("right_hind_leg");
        this.f_171082_ = p_171087_.m_171324_("left_hind_leg");
        this.f_171083_ = p_171087_.m_171324_("right_front_leg");
        this.f_171084_ = p_171087_.m_171324_("left_front_leg");
        this.f_104114_ = p_171087_.m_171324_("tail");
        this.f_104115_ = this.f_104114_.m_171324_(f_171080_);
    }

    public static LayerDefinition m_171088_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = 13.5f;
        PartDefinition $$3 = $$1.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(-1.0f, 13.5f, -7.0f));
        $$3.m_171599_(f_171078_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-2.0f, -3.0f, -2.0f, 6.0f, 6.0f, 4.0f).m_171514_(16, 14).m_171481_(-2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f).m_171514_(16, 14).m_171481_(2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f).m_171514_(0, 10).m_171481_(-0.5f, -0.001f, -5.0f, 3.0f, 3.0f, 4.0f), PartPose.f_171404_);
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(18, 14).m_171481_(-3.0f, -2.0f, -3.0f, 6.0f, 9.0f, 6.0f), PartPose.m_171423_(0.0f, 14.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.m_171599_(f_171079_, CubeListBuilder.m_171558_().m_171514_(21, 0).m_171481_(-3.0f, -3.0f, -3.0f, 8.0f, 6.0f, 7.0f), PartPose.m_171423_(-1.0f, 14.0f, -3.0f, 1.5707964f, 0.0f, 0.0f));
        CubeListBuilder $$4 = CubeListBuilder.m_171558_().m_171514_(0, 18).m_171481_(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f);
        $$1.m_171599_("right_hind_leg", $$4, PartPose.m_171419_(-2.5f, 16.0f, 7.0f));
        $$1.m_171599_("left_hind_leg", $$4, PartPose.m_171419_(0.5f, 16.0f, 7.0f));
        $$1.m_171599_("right_front_leg", $$4, PartPose.m_171419_(-2.5f, 16.0f, -4.0f));
        $$1.m_171599_("left_front_leg", $$4, PartPose.m_171419_(0.5f, 16.0f, -4.0f));
        PartDefinition $$5 = $$1.m_171599_("tail", CubeListBuilder.m_171558_(), PartPose.m_171423_(-1.0f, 12.0f, 8.0f, 0.62831855f, 0.0f, 0.0f));
        $$5.m_171599_(f_171080_, CubeListBuilder.m_171558_().m_171514_(9, 18).m_171481_(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_104107_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_104109_, (Object)this.f_171081_, (Object)this.f_171082_, (Object)this.f_171083_, (Object)this.f_171084_, (Object)this.f_104114_, (Object)this.f_104116_);
    }

    @Override
    public void m_6839_(T p_104132_, float p_104133_, float p_104134_, float p_104135_) {
        this.f_104114_.f_104204_ = p_104132_.m_21660_() ? 0.0f : Mth.m_14089_(p_104133_ * 0.6662f) * 1.4f * p_104134_;
        if (((TamableAnimal)p_104132_).m_21825_()) {
            this.f_104116_.m_104227_(-1.0f, 16.0f, -3.0f);
            this.f_104116_.f_104203_ = 1.2566371f;
            this.f_104116_.f_104204_ = 0.0f;
            this.f_104109_.m_104227_(0.0f, 18.0f, 0.0f);
            this.f_104109_.f_104203_ = 0.7853982f;
            this.f_104114_.m_104227_(-1.0f, 21.0f, 6.0f);
            this.f_171081_.m_104227_(-2.5f, 22.7f, 2.0f);
            this.f_171081_.f_104203_ = 4.712389f;
            this.f_171082_.m_104227_(0.5f, 22.7f, 2.0f);
            this.f_171082_.f_104203_ = 4.712389f;
            this.f_171083_.f_104203_ = 5.811947f;
            this.f_171083_.m_104227_(-2.49f, 17.0f, -4.0f);
            this.f_171084_.f_104203_ = 5.811947f;
            this.f_171084_.m_104227_(0.51f, 17.0f, -4.0f);
        } else {
            this.f_104109_.m_104227_(0.0f, 14.0f, 2.0f);
            this.f_104109_.f_104203_ = 1.5707964f;
            this.f_104116_.m_104227_(-1.0f, 14.0f, -3.0f);
            this.f_104116_.f_104203_ = this.f_104109_.f_104203_;
            this.f_104114_.m_104227_(-1.0f, 12.0f, 8.0f);
            this.f_171081_.m_104227_(-2.5f, 16.0f, 7.0f);
            this.f_171082_.m_104227_(0.5f, 16.0f, 7.0f);
            this.f_171083_.m_104227_(-2.5f, 16.0f, -4.0f);
            this.f_171084_.m_104227_(0.5f, 16.0f, -4.0f);
            this.f_171081_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662f) * 1.4f * p_104134_;
            this.f_171082_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662f + (float)Math.PI) * 1.4f * p_104134_;
            this.f_171083_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662f + (float)Math.PI) * 1.4f * p_104134_;
            this.f_171084_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662f) * 1.4f * p_104134_;
        }
        this.f_104108_.f_104205_ = ((Wolf)p_104132_).m_30448_(p_104135_) + ((Wolf)p_104132_).m_30432_(p_104135_, 0.0f);
        this.f_104116_.f_104205_ = ((Wolf)p_104132_).m_30432_(p_104135_, -0.08f);
        this.f_104109_.f_104205_ = ((Wolf)p_104132_).m_30432_(p_104135_, -0.16f);
        this.f_104115_.f_104205_ = ((Wolf)p_104132_).m_30432_(p_104135_, -0.2f);
    }

    @Override
    public void m_6973_(T p_104137_, float p_104138_, float p_104139_, float p_104140_, float p_104141_, float p_104142_) {
        this.f_104107_.f_104203_ = p_104142_ * ((float)Math.PI / 180);
        this.f_104107_.f_104204_ = p_104141_ * ((float)Math.PI / 180);
        this.f_104114_.f_104203_ = p_104140_;
    }
}

