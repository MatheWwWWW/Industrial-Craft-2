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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;

public class HoglinModel<T extends Mob>
extends AgeableListModel<T> {
    private static final float f_170633_ = 0.87266463f;
    private static final float f_170634_ = -0.34906584f;
    private final ModelPart f_102725_;
    private final ModelPart f_102726_;
    private final ModelPart f_102727_;
    private final ModelPart f_102728_;
    private final ModelPart f_170635_;
    private final ModelPart f_170636_;
    private final ModelPart f_170637_;
    private final ModelPart f_170638_;
    private final ModelPart f_102733_;

    public HoglinModel(ModelPart p_170640_) {
        super(true, 8.0f, 6.0f, 1.9f, 2.0f, 24.0f);
        this.f_102728_ = p_170640_.m_171324_("body");
        this.f_102733_ = this.f_102728_.m_171324_("mane");
        this.f_102725_ = p_170640_.m_171324_("head");
        this.f_102726_ = this.f_102725_.m_171324_("right_ear");
        this.f_102727_ = this.f_102725_.m_171324_("left_ear");
        this.f_170635_ = p_170640_.m_171324_("right_front_leg");
        this.f_170636_ = p_170640_.m_171324_("left_front_leg");
        this.f_170637_ = p_170640_.m_171324_("right_hind_leg");
        this.f_170638_ = p_170640_.m_171324_("left_hind_leg");
    }

    public static LayerDefinition m_170641_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171481_(-8.0f, -7.0f, -13.0f, 16.0f, 14.0f, 26.0f), PartPose.m_171419_(0.0f, 7.0f, 0.0f));
        $$2.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(90, 33).m_171488_(0.0f, 0.0f, -9.0f, 0.0f, 10.0f, 19.0f, new CubeDeformation(0.001f)), PartPose.m_171419_(0.0f, -14.0f, -5.0f));
        PartDefinition $$3 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(61, 1).m_171481_(-7.0f, -3.0f, -19.0f, 14.0f, 6.0f, 19.0f), PartPose.m_171423_(0.0f, 2.0f, -12.0f, 0.87266463f, 0.0f, 0.0f));
        $$3.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171481_(-6.0f, -1.0f, -2.0f, 6.0f, 1.0f, 4.0f), PartPose.m_171423_(-6.0f, -2.0f, -3.0f, 0.0f, 0.0f, -0.6981317f));
        $$3.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(1, 6).m_171481_(0.0f, -1.0f, -2.0f, 6.0f, 1.0f, 4.0f), PartPose.m_171423_(6.0f, -2.0f, -3.0f, 0.0f, 0.0f, 0.6981317f));
        $$3.m_171599_("right_horn", CubeListBuilder.m_171558_().m_171514_(10, 13).m_171481_(-1.0f, -11.0f, -1.0f, 2.0f, 11.0f, 2.0f), PartPose.m_171419_(-7.0f, 2.0f, -12.0f));
        $$3.m_171599_("left_horn", CubeListBuilder.m_171558_().m_171514_(1, 13).m_171481_(-1.0f, -11.0f, -1.0f, 2.0f, 11.0f, 2.0f), PartPose.m_171419_(7.0f, 2.0f, -12.0f));
        int $$4 = 14;
        int $$5 = 11;
        $$1.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(66, 42).m_171481_(-3.0f, 0.0f, -3.0f, 6.0f, 14.0f, 6.0f), PartPose.m_171419_(-4.0f, 10.0f, -8.5f));
        $$1.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(41, 42).m_171481_(-3.0f, 0.0f, -3.0f, 6.0f, 14.0f, 6.0f), PartPose.m_171419_(4.0f, 10.0f, -8.5f));
        $$1.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(21, 45).m_171481_(-2.5f, 0.0f, -2.5f, 5.0f, 11.0f, 5.0f), PartPose.m_171419_(-5.0f, 13.0f, 10.0f));
        $$1.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 45).m_171481_(-2.5f, 0.0f, -2.5f, 5.0f, 11.0f, 5.0f), PartPose.m_171419_(5.0f, 13.0f, 10.0f));
        return LayerDefinition.m_171565_($$0, 128, 64);
    }

    @Override
    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_102725_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102728_, (Object)this.f_170635_, (Object)this.f_170636_, (Object)this.f_170637_, (Object)this.f_170638_);
    }

    @Override
    public void m_6973_(T p_102744_, float p_102745_, float p_102746_, float p_102747_, float p_102748_, float p_102749_) {
        this.f_102726_.f_104205_ = -0.6981317f - p_102746_ * Mth.m_14031_(p_102745_);
        this.f_102727_.f_104205_ = 0.6981317f + p_102746_ * Mth.m_14031_(p_102745_);
        this.f_102725_.f_104204_ = p_102748_ * ((float)Math.PI / 180);
        int $$6 = ((HoglinBase)p_102744_).m_7575_();
        float $$7 = 1.0f - (float)Mth.m_14040_(10 - 2 * $$6) / 10.0f;
        this.f_102725_.f_104203_ = Mth.m_14179_($$7, 0.87266463f, -0.34906584f);
        if (((LivingEntity)p_102744_).m_6162_()) {
            this.f_102725_.f_104201_ = Mth.m_14179_($$7, 2.0f, 5.0f);
            this.f_102733_.f_104202_ = -3.0f;
        } else {
            this.f_102725_.f_104201_ = 2.0f;
            this.f_102733_.f_104202_ = -7.0f;
        }
        float $$8 = 1.2f;
        this.f_170635_.f_104203_ = Mth.m_14089_(p_102745_) * 1.2f * p_102746_;
        this.f_170637_.f_104203_ = this.f_170636_.f_104203_ = Mth.m_14089_(p_102745_ + (float)Math.PI) * 1.2f * p_102746_;
        this.f_170638_.f_104203_ = this.f_170635_.f_104203_;
    }
}

