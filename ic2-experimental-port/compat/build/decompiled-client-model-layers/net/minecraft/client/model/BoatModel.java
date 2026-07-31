/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class BoatModel
extends ListModel<Boat> {
    private static final String f_170451_ = "left_paddle";
    private static final String f_170452_ = "right_paddle";
    private static final String f_170453_ = "water_patch";
    private static final String f_170454_ = "bottom";
    private static final String f_170455_ = "back";
    private static final String f_170456_ = "front";
    private static final String f_170457_ = "right";
    private static final String f_170458_ = "left";
    private static final String f_233341_ = "chest_bottom";
    private static final String f_233342_ = "chest_lid";
    private static final String f_233343_ = "chest_lock";
    private final ModelPart f_170459_;
    private final ModelPart f_170460_;
    private final ModelPart f_102257_;
    private final ImmutableList<ModelPart> f_102258_;

    public BoatModel(ModelPart p_233345_, boolean p_233346_) {
        this.f_170459_ = p_233345_.m_171324_(f_170451_);
        this.f_170460_ = p_233345_.m_171324_(f_170452_);
        this.f_102257_ = p_233345_.m_171324_(f_170453_);
        ImmutableList.Builder $$2 = new ImmutableList.Builder();
        $$2.add((Object[])new ModelPart[]{p_233345_.m_171324_(f_170454_), p_233345_.m_171324_(f_170455_), p_233345_.m_171324_(f_170456_), p_233345_.m_171324_(f_170457_), p_233345_.m_171324_(f_170458_), this.f_170459_, this.f_170460_});
        if (p_233346_) {
            $$2.add((Object)p_233345_.m_171324_(f_233341_));
            $$2.add((Object)p_233345_.m_171324_(f_233342_));
            $$2.add((Object)p_233345_.m_171324_(f_233343_));
        }
        this.f_102258_ = $$2.build();
    }

    public static LayerDefinition m_233347_(boolean p_233348_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        int $$3 = 32;
        int $$4 = 6;
        int $$5 = 20;
        int $$6 = 4;
        int $$7 = 28;
        $$2.m_171599_(f_170454_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-14.0f, -9.0f, -3.0f, 28.0f, 16.0f, 3.0f), PartPose.m_171423_(0.0f, 3.0f, 1.0f, 1.5707964f, 0.0f, 0.0f));
        $$2.m_171599_(f_170455_, CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(-13.0f, -7.0f, -1.0f, 18.0f, 6.0f, 2.0f), PartPose.m_171423_(-15.0f, 4.0f, 4.0f, 0.0f, 4.712389f, 0.0f));
        $$2.m_171599_(f_170456_, CubeListBuilder.m_171558_().m_171514_(0, 27).m_171481_(-8.0f, -7.0f, -1.0f, 16.0f, 6.0f, 2.0f), PartPose.m_171423_(15.0f, 4.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
        $$2.m_171599_(f_170457_, CubeListBuilder.m_171558_().m_171514_(0, 35).m_171481_(-14.0f, -7.0f, -1.0f, 28.0f, 6.0f, 2.0f), PartPose.m_171423_(0.0f, 4.0f, -9.0f, 0.0f, (float)Math.PI, 0.0f));
        $$2.m_171599_(f_170458_, CubeListBuilder.m_171558_().m_171514_(0, 43).m_171481_(-14.0f, -7.0f, -1.0f, 28.0f, 6.0f, 2.0f), PartPose.m_171419_(0.0f, 4.0f, 9.0f));
        if (p_233348_) {
            $$2.m_171599_(f_233341_, CubeListBuilder.m_171558_().m_171514_(0, 76).m_171481_(0.0f, 0.0f, 0.0f, 12.0f, 8.0f, 12.0f), PartPose.m_171423_(-2.0f, -5.0f, -6.0f, 0.0f, -1.5707964f, 0.0f));
            $$2.m_171599_(f_233342_, CubeListBuilder.m_171558_().m_171514_(0, 59).m_171481_(0.0f, 0.0f, 0.0f, 12.0f, 4.0f, 12.0f), PartPose.m_171423_(-2.0f, -9.0f, -6.0f, 0.0f, -1.5707964f, 0.0f));
            $$2.m_171599_(f_233343_, CubeListBuilder.m_171558_().m_171514_(0, 59).m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 4.0f, 1.0f), PartPose.m_171423_(-1.0f, -6.0f, -1.0f, 0.0f, -1.5707964f, 0.0f));
        }
        int $$8 = 20;
        int $$9 = 7;
        int $$10 = 6;
        float $$11 = -5.0f;
        $$2.m_171599_(f_170451_, CubeListBuilder.m_171558_().m_171514_(62, 0).m_171481_(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f).m_171481_(-1.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f), PartPose.m_171423_(3.0f, -5.0f, 9.0f, 0.0f, 0.0f, 0.19634955f));
        $$2.m_171599_(f_170452_, CubeListBuilder.m_171558_().m_171514_(62, 20).m_171481_(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f).m_171481_(0.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f), PartPose.m_171423_(3.0f, -5.0f, -9.0f, 0.0f, (float)Math.PI, 0.19634955f));
        $$2.m_171599_(f_170453_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-14.0f, -9.0f, -3.0f, 28.0f, 16.0f, 3.0f), PartPose.m_171423_(0.0f, -3.0f, 1.0f, 1.5707964f, 0.0f, 0.0f));
        return LayerDefinition.m_171565_($$1, 128, p_233348_ ? 128 : 64);
    }

    @Override
    public void m_6973_(Boat p_102269_, float p_102270_, float p_102271_, float p_102272_, float p_102273_, float p_102274_) {
        BoatModel.m_170464_(p_102269_, 0, this.f_170459_, p_102270_);
        BoatModel.m_170464_(p_102269_, 1, this.f_170460_, p_102270_);
    }

    public ImmutableList<ModelPart> m_6195_() {
        return this.f_102258_;
    }

    public ModelPart m_102282_() {
        return this.f_102257_;
    }

    private static void m_170464_(Boat p_170465_, int p_170466_, ModelPart p_170467_, float p_170468_) {
        float $$4 = p_170465_.m_38315_(p_170466_, p_170468_);
        p_170467_.f_104203_ = Mth.m_144920_(-1.0471976f, -0.2617994f, (Mth.m_14031_(-$$4) + 1.0f) / 2.0f);
        p_170467_.f_104204_ = Mth.m_144920_(-0.7853982f, 0.7853982f, (Mth.m_14031_(-$$4 + 1.0f) + 1.0f) / 2.0f);
        if (p_170466_ == 1) {
            p_170467_.f_104204_ = (float)Math.PI - p_170467_.f_104204_;
        }
    }

    @Override
    public /* synthetic */ Iterable m_6195_() {
        return this.m_6195_();
    }
}

