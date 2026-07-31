/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class AxolotlModel<T extends Axolotl>
extends AgeableListModel<T> {
    public static final float f_170358_ = 1.8849558f;
    private final ModelPart f_170359_;
    private final ModelPart f_170360_;
    private final ModelPart f_170361_;
    private final ModelPart f_170362_;
    private final ModelPart f_170363_;
    private final ModelPart f_170364_;
    private final ModelPart f_170365_;
    private final ModelPart f_170366_;
    private final ModelPart f_170367_;
    private final ModelPart f_170368_;

    public AxolotlModel(ModelPart p_170370_) {
        super(true, 8.0f, 3.35f);
        this.f_170364_ = p_170370_.m_171324_("body");
        this.f_170365_ = this.f_170364_.m_171324_("head");
        this.f_170361_ = this.f_170364_.m_171324_("right_hind_leg");
        this.f_170360_ = this.f_170364_.m_171324_("left_hind_leg");
        this.f_170363_ = this.f_170364_.m_171324_("right_front_leg");
        this.f_170362_ = this.f_170364_.m_171324_("left_front_leg");
        this.f_170359_ = this.f_170364_.m_171324_("tail");
        this.f_170366_ = this.f_170365_.m_171324_("top_gills");
        this.f_170367_ = this.f_170365_.m_171324_("left_gills");
        this.f_170368_ = this.f_170365_.m_171324_("right_gills");
    }

    public static LayerDefinition m_170417_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171481_(-4.0f, -2.0f, -9.0f, 8.0f, 4.0f, 10.0f).m_171514_(2, 17).m_171481_(0.0f, -3.0f, -8.0f, 0.0f, 5.0f, 9.0f), PartPose.m_171419_(0.0f, 20.0f, 5.0f));
        CubeDeformation $$3 = new CubeDeformation(0.001f);
        PartDefinition $$4 = $$2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171488_(-4.0f, -3.0f, -5.0f, 8.0f, 5.0f, 5.0f, $$3), PartPose.m_171419_(0.0f, 0.0f, -9.0f));
        CubeListBuilder $$5 = CubeListBuilder.m_171558_().m_171514_(3, 37).m_171488_(-4.0f, -3.0f, 0.0f, 8.0f, 3.0f, 0.0f, $$3);
        CubeListBuilder $$6 = CubeListBuilder.m_171558_().m_171514_(0, 40).m_171488_(-3.0f, -5.0f, 0.0f, 3.0f, 7.0f, 0.0f, $$3);
        CubeListBuilder $$7 = CubeListBuilder.m_171558_().m_171514_(11, 40).m_171488_(0.0f, -5.0f, 0.0f, 3.0f, 7.0f, 0.0f, $$3);
        $$4.m_171599_("top_gills", $$5, PartPose.m_171419_(0.0f, -3.0f, -1.0f));
        $$4.m_171599_("left_gills", $$6, PartPose.m_171419_(-4.0f, 0.0f, -1.0f));
        $$4.m_171599_("right_gills", $$7, PartPose.m_171419_(4.0f, 0.0f, -1.0f));
        CubeListBuilder $$8 = CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(-1.0f, 0.0f, 0.0f, 3.0f, 5.0f, 0.0f, $$3);
        CubeListBuilder $$9 = CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(-2.0f, 0.0f, 0.0f, 3.0f, 5.0f, 0.0f, $$3);
        $$2.m_171599_("right_hind_leg", $$9, PartPose.m_171419_(-3.5f, 1.0f, -1.0f));
        $$2.m_171599_("left_hind_leg", $$8, PartPose.m_171419_(3.5f, 1.0f, -1.0f));
        $$2.m_171599_("right_front_leg", $$9, PartPose.m_171419_(-3.5f, 1.0f, -8.0f));
        $$2.m_171599_("left_front_leg", $$8, PartPose.m_171419_(3.5f, 1.0f, -8.0f));
        $$2.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(2, 19).m_171481_(0.0f, -3.0f, 0.0f, 0.0f, 5.0f, 12.0f), PartPose.m_171419_(0.0f, 0.0f, 1.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_170364_);
    }

    @Override
    public void m_6973_(T p_170395_, float p_170396_, float p_170397_, float p_170398_, float p_170399_, float p_170400_) {
        boolean $$6;
        this.m_170390_(p_170395_, p_170399_, p_170400_);
        if (((Axolotl)p_170395_).m_149175_()) {
            this.m_170412_(p_170399_);
            this.m_170388_(p_170395_);
            return;
        }
        boolean bl = $$6 = ((Entity)p_170395_).m_20184_().m_165925_() > 1.0E-7 || ((Entity)p_170395_).m_146909_() != ((Axolotl)p_170395_).f_19860_ || ((Entity)p_170395_).m_146908_() != ((Axolotl)p_170395_).f_19859_ || ((Axolotl)p_170395_).f_19790_ != ((Entity)p_170395_).m_20185_() || ((Axolotl)p_170395_).f_19792_ != ((Entity)p_170395_).m_20189_();
        if (((Entity)p_170395_).m_20072_()) {
            if ($$6) {
                this.m_170422_(p_170398_, p_170400_);
            } else {
                this.m_170372_(p_170398_);
            }
            this.m_170388_(p_170395_);
            return;
        }
        if (((Entity)p_170395_).m_20096_()) {
            if ($$6) {
                this.m_170418_(p_170398_, p_170399_);
            } else {
                this.m_170414_(p_170398_, p_170399_);
            }
        }
        this.m_170388_(p_170395_);
    }

    private void m_170388_(T p_170389_) {
        Map<String, Vector3f> $$1 = ((Axolotl)p_170389_).m_142115_();
        $$1.put("body", this.m_170401_(this.f_170364_));
        $$1.put("head", this.m_170401_(this.f_170365_));
        $$1.put("right_hind_leg", this.m_170401_(this.f_170361_));
        $$1.put("left_hind_leg", this.m_170401_(this.f_170360_));
        $$1.put("right_front_leg", this.m_170401_(this.f_170363_));
        $$1.put("left_front_leg", this.m_170401_(this.f_170362_));
        $$1.put("tail", this.m_170401_(this.f_170359_));
        $$1.put("top_gills", this.m_170401_(this.f_170366_));
        $$1.put("left_gills", this.m_170401_(this.f_170367_));
        $$1.put("right_gills", this.m_170401_(this.f_170368_));
    }

    private Vector3f m_170401_(ModelPart p_170402_) {
        return new Vector3f(p_170402_.f_104203_, p_170402_.f_104204_, p_170402_.f_104205_);
    }

    private void m_170408_(ModelPart p_170409_, Vector3f p_170410_) {
        p_170409_.m_171327_(p_170410_.m_122239_(), p_170410_.m_122260_(), p_170410_.m_122269_());
    }

    private void m_170390_(T p_170391_, float p_170392_, float p_170393_) {
        this.f_170364_.f_104200_ = 0.0f;
        this.f_170365_.f_104201_ = 0.0f;
        this.f_170364_.f_104201_ = 20.0f;
        Map<String, Vector3f> $$3 = ((Axolotl)p_170391_).m_142115_();
        if ($$3.isEmpty()) {
            this.f_170364_.m_171327_(p_170393_ * ((float)Math.PI / 180), p_170392_ * ((float)Math.PI / 180), 0.0f);
            this.f_170365_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170360_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170361_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170362_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170363_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170367_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170368_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170366_.m_171327_(0.0f, 0.0f, 0.0f);
            this.f_170359_.m_171327_(0.0f, 0.0f, 0.0f);
        } else {
            this.m_170408_(this.f_170364_, $$3.get("body"));
            this.m_170408_(this.f_170365_, $$3.get("head"));
            this.m_170408_(this.f_170360_, $$3.get("left_hind_leg"));
            this.m_170408_(this.f_170361_, $$3.get("right_hind_leg"));
            this.m_170408_(this.f_170362_, $$3.get("left_front_leg"));
            this.m_170408_(this.f_170363_, $$3.get("right_front_leg"));
            this.m_170408_(this.f_170367_, $$3.get("left_gills"));
            this.m_170408_(this.f_170368_, $$3.get("right_gills"));
            this.m_170408_(this.f_170366_, $$3.get("top_gills"));
            this.m_170408_(this.f_170359_, $$3.get("tail"));
        }
    }

    private float m_170374_(float p_170375_, float p_170376_) {
        return this.m_170377_(0.05f, p_170375_, p_170376_);
    }

    private float m_170377_(float p_170378_, float p_170379_, float p_170380_) {
        return Mth.m_14189_(p_170378_, p_170379_, p_170380_);
    }

    private void m_170403_(ModelPart p_170404_, float p_170405_, float p_170406_, float p_170407_) {
        p_170404_.m_171327_(this.m_170374_(p_170404_.f_104203_, p_170405_), this.m_170374_(p_170404_.f_104204_, p_170406_), this.m_170374_(p_170404_.f_104205_, p_170407_));
    }

    private void m_170414_(float p_170415_, float p_170416_) {
        float $$2 = p_170415_ * 0.09f;
        float $$3 = Mth.m_14031_($$2);
        float $$4 = Mth.m_14089_($$2);
        float $$5 = $$3 * $$3 - 2.0f * $$3;
        float $$6 = $$4 * $$4 - 3.0f * $$3;
        this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, -0.09f * $$5);
        this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0f);
        this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, -0.2f);
        this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, -0.1f + 0.1f * $$5);
        this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, 0.6f + 0.05f * $$6);
        this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, -this.f_170366_.f_104203_);
        this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
        this.m_170403_(this.f_170360_, 1.1f, 1.0f, 0.0f);
        this.m_170403_(this.f_170362_, 0.8f, 2.3f, -0.5f);
        this.m_170421_();
        this.f_170364_.f_104203_ = this.m_170377_(0.2f, this.f_170364_.f_104203_, 0.0f);
        this.f_170364_.f_104204_ = this.m_170374_(this.f_170364_.f_104204_, p_170416_ * ((float)Math.PI / 180));
        this.f_170364_.f_104205_ = this.m_170374_(this.f_170364_.f_104205_, 0.0f);
    }

    private void m_170418_(float p_170419_, float p_170420_) {
        float $$2 = p_170419_ * 0.11f;
        float $$3 = Mth.m_14089_($$2);
        float $$4 = ($$3 * $$3 - 2.0f * $$3) / 5.0f;
        float $$5 = 0.7f * $$3;
        this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, 0.0f);
        this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.09f * $$3);
        this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0f);
        this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, this.f_170365_.f_104204_);
        this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, 0.6f - 0.08f * ($$3 * $$3 + 2.0f * Mth.m_14031_($$2)));
        this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, -this.f_170366_.f_104203_);
        this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
        this.m_170403_(this.f_170360_, 0.9424779f, 1.5f - $$4, -0.1f);
        this.m_170403_(this.f_170362_, 1.0995574f, 1.5707964f - $$5, 0.0f);
        this.m_170403_(this.f_170361_, this.f_170360_.f_104203_, -1.0f - $$4, 0.0f);
        this.m_170403_(this.f_170363_, this.f_170362_.f_104203_, -1.5707964f - $$5, 0.0f);
        this.f_170364_.f_104203_ = this.m_170377_(0.2f, this.f_170364_.f_104203_, 0.0f);
        this.f_170364_.f_104204_ = this.m_170374_(this.f_170364_.f_104204_, p_170420_ * ((float)Math.PI / 180));
        this.f_170364_.f_104205_ = this.m_170374_(this.f_170364_.f_104205_, 0.0f);
    }

    private void m_170372_(float p_170373_) {
        float $$1 = p_170373_ * 0.075f;
        float $$2 = Mth.m_14089_($$1);
        float $$3 = Mth.m_14031_($$1) * 0.15f;
        this.f_170364_.f_104203_ = this.m_170374_(this.f_170364_.f_104203_, -0.15f + 0.075f * $$2);
        this.f_170364_.f_104201_ -= $$3;
        this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, -this.f_170364_.f_104203_);
        this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, 0.2f * $$2);
        this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, -0.3f * $$2 - 0.19f);
        this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
        this.m_170403_(this.f_170360_, 2.3561945f - $$2 * 0.11f, 0.47123894f, 1.7278761f);
        this.m_170403_(this.f_170362_, 0.7853982f - $$2 * 0.2f, 2.042035f, 0.0f);
        this.m_170421_();
        this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, 0.5f * $$2);
        this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0f);
        this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0f);
    }

    private void m_170422_(float p_170423_, float p_170424_) {
        float $$2 = p_170423_ * 0.33f;
        float $$3 = Mth.m_14031_($$2);
        float $$4 = Mth.m_14089_($$2);
        float $$5 = 0.13f * $$3;
        this.f_170364_.f_104203_ = this.m_170377_(0.1f, this.f_170364_.f_104203_, p_170424_ * ((float)Math.PI / 180) + $$5);
        this.f_170365_.f_104203_ = -$$5 * 1.8f;
        this.f_170364_.f_104201_ -= 0.45f * $$4;
        this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, -0.5f * $$3 - 0.8f);
        this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, 0.3f * $$3 + 0.9f);
        this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
        this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, 0.3f * Mth.m_14089_($$2 * 0.9f));
        this.m_170403_(this.f_170360_, 1.8849558f, -0.4f * $$3, 1.5707964f);
        this.m_170403_(this.f_170362_, 1.8849558f, -0.2f * $$4 - 0.1f, 1.5707964f);
        this.m_170421_();
        this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0f);
        this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0f);
    }

    private void m_170412_(float p_170413_) {
        this.m_170403_(this.f_170360_, 1.4137167f, 1.0995574f, 0.7853982f);
        this.m_170403_(this.f_170362_, 0.7853982f, 2.042035f, 0.0f);
        this.f_170364_.f_104203_ = this.m_170374_(this.f_170364_.f_104203_, -0.15f);
        this.f_170364_.f_104205_ = this.m_170374_(this.f_170364_.f_104205_, 0.35f);
        this.m_170421_();
        this.f_170364_.f_104204_ = this.m_170374_(this.f_170364_.f_104204_, p_170413_ * ((float)Math.PI / 180));
        this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, 0.0f);
        this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0f);
        this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0f);
        this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, 0.0f);
        this.m_170403_(this.f_170366_, 0.0f, 0.0f, 0.0f);
        this.m_170403_(this.f_170367_, 0.0f, 0.0f, 0.0f);
        this.m_170403_(this.f_170368_, 0.0f, 0.0f, 0.0f);
    }

    private void m_170421_() {
        this.m_170403_(this.f_170361_, this.f_170360_.f_104203_, -this.f_170360_.f_104204_, -this.f_170360_.f_104205_);
        this.m_170403_(this.f_170363_, this.f_170362_.f_104203_, -this.f_170362_.f_104204_, -this.f_170362_.f_104205_);
    }
}

