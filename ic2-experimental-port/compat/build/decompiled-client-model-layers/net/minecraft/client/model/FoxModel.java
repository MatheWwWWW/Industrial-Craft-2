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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;

public class FoxModel<T extends Fox>
extends AgeableListModel<T> {
    public final ModelPart f_102638_;
    private final ModelPart f_102642_;
    private final ModelPart f_170558_;
    private final ModelPart f_170559_;
    private final ModelPart f_170560_;
    private final ModelPart f_170561_;
    private final ModelPart f_102647_;
    private static final int f_170562_ = 6;
    private static final float f_170563_ = 16.5f;
    private static final float f_170564_ = 17.5f;
    private float f_102648_;

    public FoxModel(ModelPart p_170566_) {
        super(true, 8.0f, 3.35f);
        this.f_102638_ = p_170566_.m_171324_("head");
        this.f_102642_ = p_170566_.m_171324_("body");
        this.f_170558_ = p_170566_.m_171324_("right_hind_leg");
        this.f_170559_ = p_170566_.m_171324_("left_hind_leg");
        this.f_170560_ = p_170566_.m_171324_("right_front_leg");
        this.f_170561_ = p_170566_.m_171324_("left_front_leg");
        this.f_102647_ = this.f_102642_.m_171324_("tail");
    }

    public static LayerDefinition m_170567_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(1, 5).m_171481_(-3.0f, -2.0f, -5.0f, 8.0f, 6.0f, 6.0f), PartPose.m_171419_(-1.0f, 16.5f, -3.0f));
        $$2.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(8, 1).m_171481_(-3.0f, -4.0f, -4.0f, 2.0f, 2.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(15, 1).m_171481_(3.0f, -4.0f, -4.0f, 2.0f, 2.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(6, 18).m_171481_(-1.0f, 2.01f, -8.0f, 4.0f, 2.0f, 3.0f), PartPose.f_171404_);
        PartDefinition $$3 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(24, 15).m_171481_(-3.0f, 3.999f, -3.5f, 6.0f, 11.0f, 6.0f), PartPose.m_171423_(0.0f, 16.0f, -6.0f, 1.5707964f, 0.0f, 0.0f));
        CubeDeformation $$4 = new CubeDeformation(0.001f);
        CubeListBuilder $$5 = CubeListBuilder.m_171558_().m_171514_(4, 24).m_171488_(2.0f, 0.5f, -1.0f, 2.0f, 6.0f, 2.0f, $$4);
        CubeListBuilder $$6 = CubeListBuilder.m_171558_().m_171514_(13, 24).m_171488_(2.0f, 0.5f, -1.0f, 2.0f, 6.0f, 2.0f, $$4);
        $$1.m_171599_("right_hind_leg", $$6, PartPose.m_171419_(-5.0f, 17.5f, 7.0f));
        $$1.m_171599_("left_hind_leg", $$5, PartPose.m_171419_(-1.0f, 17.5f, 7.0f));
        $$1.m_171599_("right_front_leg", $$6, PartPose.m_171419_(-5.0f, 17.5f, 0.0f));
        $$1.m_171599_("left_front_leg", $$5, PartPose.m_171419_(-1.0f, 17.5f, 0.0f));
        $$3.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(30, 0).m_171481_(2.0f, 0.0f, -1.0f, 4.0f, 9.0f, 5.0f), PartPose.m_171423_(-4.0f, 15.0f, -1.0f, -0.05235988f, 0.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 48, 32);
    }

    @Override
    public void m_6839_(T p_102664_, float p_102665_, float p_102666_, float p_102667_) {
        this.f_102642_.f_104203_ = 1.5707964f;
        this.f_102647_.f_104203_ = -0.05235988f;
        this.f_170558_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662f) * 1.4f * p_102666_;
        this.f_170559_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662f + (float)Math.PI) * 1.4f * p_102666_;
        this.f_170560_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662f + (float)Math.PI) * 1.4f * p_102666_;
        this.f_170561_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662f) * 1.4f * p_102666_;
        this.f_102638_.m_104227_(-1.0f, 16.5f, -3.0f);
        this.f_102638_.f_104204_ = 0.0f;
        this.f_102638_.f_104205_ = ((Fox)p_102664_).m_28620_(p_102667_);
        this.f_170558_.f_104207_ = true;
        this.f_170559_.f_104207_ = true;
        this.f_170560_.f_104207_ = true;
        this.f_170561_.f_104207_ = true;
        this.f_102642_.m_104227_(0.0f, 16.0f, -6.0f);
        this.f_102642_.f_104205_ = 0.0f;
        this.f_170558_.m_104227_(-5.0f, 17.5f, 7.0f);
        this.f_170559_.m_104227_(-1.0f, 17.5f, 7.0f);
        if (((Fox)p_102664_).m_6047_()) {
            this.f_102642_.f_104203_ = 1.6755161f;
            float $$4 = ((Fox)p_102664_).m_28624_(p_102667_);
            this.f_102642_.m_104227_(0.0f, 16.0f + ((Fox)p_102664_).m_28624_(p_102667_), -6.0f);
            this.f_102638_.m_104227_(-1.0f, 16.5f + $$4, -3.0f);
            this.f_102638_.f_104204_ = 0.0f;
        } else if (((Fox)p_102664_).m_5803_()) {
            this.f_102642_.f_104205_ = -1.5707964f;
            this.f_102642_.m_104227_(0.0f, 21.0f, -6.0f);
            this.f_102647_.f_104203_ = -2.6179938f;
            if (this.f_102610_) {
                this.f_102647_.f_104203_ = -2.1816616f;
                this.f_102642_.m_104227_(0.0f, 21.0f, -2.0f);
            }
            this.f_102638_.m_104227_(1.0f, 19.49f, -3.0f);
            this.f_102638_.f_104203_ = 0.0f;
            this.f_102638_.f_104204_ = -2.0943952f;
            this.f_102638_.f_104205_ = 0.0f;
            this.f_170558_.f_104207_ = false;
            this.f_170559_.f_104207_ = false;
            this.f_170560_.f_104207_ = false;
            this.f_170561_.f_104207_ = false;
        } else if (((Fox)p_102664_).m_28555_()) {
            this.f_102642_.f_104203_ = 0.5235988f;
            this.f_102642_.m_104227_(0.0f, 9.0f, -3.0f);
            this.f_102647_.f_104203_ = 0.7853982f;
            this.f_102647_.m_104227_(-4.0f, 15.0f, -2.0f);
            this.f_102638_.m_104227_(-1.0f, 10.0f, -0.25f);
            this.f_102638_.f_104203_ = 0.0f;
            this.f_102638_.f_104204_ = 0.0f;
            if (this.f_102610_) {
                this.f_102638_.m_104227_(-1.0f, 13.0f, -3.75f);
            }
            this.f_170558_.f_104203_ = -1.3089969f;
            this.f_170558_.m_104227_(-5.0f, 21.5f, 6.75f);
            this.f_170559_.f_104203_ = -1.3089969f;
            this.f_170559_.m_104227_(-1.0f, 21.5f, 6.75f);
            this.f_170560_.f_104203_ = -0.2617994f;
            this.f_170561_.f_104203_ = -0.2617994f;
        }
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_102638_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102642_, (Object)this.f_170558_, (Object)this.f_170559_, (Object)this.f_170560_, (Object)this.f_170561_);
    }

    @Override
    public void m_6973_(T p_102669_, float p_102670_, float p_102671_, float p_102672_, float p_102673_, float p_102674_) {
        if (!(((Fox)p_102669_).m_5803_() || ((Fox)p_102669_).m_28556_() || ((Fox)p_102669_).m_6047_())) {
            this.f_102638_.f_104203_ = p_102674_ * ((float)Math.PI / 180);
            this.f_102638_.f_104204_ = p_102673_ * ((float)Math.PI / 180);
        }
        if (((Fox)p_102669_).m_5803_()) {
            this.f_102638_.f_104203_ = 0.0f;
            this.f_102638_.f_104204_ = -2.0943952f;
            this.f_102638_.f_104205_ = Mth.m_14089_(p_102672_ * 0.027f) / 22.0f;
        }
        if (((Fox)p_102669_).m_6047_()) {
            float $$6;
            this.f_102642_.f_104204_ = $$6 = Mth.m_14089_(p_102672_) * 0.01f;
            this.f_170558_.f_104205_ = $$6;
            this.f_170559_.f_104205_ = $$6;
            this.f_170560_.f_104205_ = $$6 / 2.0f;
            this.f_170561_.f_104205_ = $$6 / 2.0f;
        }
        if (((Fox)p_102669_).m_28556_()) {
            float $$7 = 0.1f;
            this.f_102648_ += 0.67f;
            this.f_170558_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662f) * 0.1f;
            this.f_170559_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662f + (float)Math.PI) * 0.1f;
            this.f_170560_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662f + (float)Math.PI) * 0.1f;
            this.f_170561_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662f) * 0.1f;
        }
    }
}

