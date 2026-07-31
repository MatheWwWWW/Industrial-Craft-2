/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.goat.Goat;

public class GoatModel<T extends Goat>
extends QuadrupedModel<T> {
    public GoatModel(ModelPart p_170578_) {
        super(p_170578_, true, 19.0f, 1.0f, 2.5f, 2.0f, 24);
    }

    public static LayerDefinition m_170593_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(2, 61).m_171517_("right ear", -6.0f, -11.0f, -10.0f, 3.0f, 2.0f, 1.0f).m_171514_(2, 61).m_171480_().m_171517_("left ear", 2.0f, -11.0f, -10.0f, 3.0f, 2.0f, 1.0f).m_171514_(23, 52).m_171517_("goatee", -0.5f, -3.0f, -14.0f, 0.0f, 7.0f, 5.0f), PartPose.m_171419_(1.0f, 14.0f, 0.0f));
        $$2.m_171599_("left_horn", CubeListBuilder.m_171558_().m_171514_(12, 55).m_171481_(-0.01f, -16.0f, -10.0f, 2.0f, 7.0f, 2.0f), PartPose.m_171419_(0.0f, 0.0f, 0.0f));
        $$2.m_171599_("right_horn", CubeListBuilder.m_171558_().m_171514_(12, 55).m_171481_(-2.99f, -16.0f, -10.0f, 2.0f, 7.0f, 2.0f), PartPose.m_171419_(0.0f, 0.0f, 0.0f));
        $$2.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(34, 46).m_171481_(-3.0f, -4.0f, -8.0f, 5.0f, 7.0f, 10.0f), PartPose.m_171423_(0.0f, -8.0f, -8.0f, 0.9599f, 0.0f, 0.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171481_(-4.0f, -17.0f, -7.0f, 9.0f, 11.0f, 16.0f).m_171514_(0, 28).m_171481_(-5.0f, -18.0f, -8.0f, 11.0f, 14.0f, 11.0f), PartPose.m_171419_(0.0f, 24.0f, 0.0f));
        $$1.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(36, 29).m_171481_(0.0f, 4.0f, 0.0f, 3.0f, 6.0f, 3.0f), PartPose.m_171419_(1.0f, 14.0f, 4.0f));
        $$1.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(49, 29).m_171481_(0.0f, 4.0f, 0.0f, 3.0f, 6.0f, 3.0f), PartPose.m_171419_(-3.0f, 14.0f, 4.0f));
        $$1.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(49, 2).m_171481_(0.0f, 0.0f, 0.0f, 3.0f, 10.0f, 3.0f), PartPose.m_171419_(1.0f, 14.0f, -6.0f));
        $$1.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(35, 2).m_171481_(0.0f, 0.0f, 0.0f, 3.0f, 10.0f, 3.0f), PartPose.m_171419_(-3.0f, 14.0f, -6.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6973_(T p_170587_, float p_170588_, float p_170589_, float p_170590_, float p_170591_, float p_170592_) {
        this.f_103492_.m_171324_((String)"left_horn").f_104207_ = ((Goat)p_170587_).m_218758_();
        this.f_103492_.m_171324_((String)"right_horn").f_104207_ = ((Goat)p_170587_).m_218759_();
        super.m_6973_(p_170587_, p_170588_, p_170589_, p_170590_, p_170591_, p_170592_);
        float $$6 = ((Goat)p_170587_).m_149398_();
        if ($$6 != 0.0f) {
            this.f_103492_.f_104203_ = $$6;
        }
    }
}

