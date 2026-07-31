/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Rabbit;

public class RabbitModel<T extends Rabbit>
extends EntityModel<T> {
    private static final float f_170867_ = 50.0f;
    private static final float f_170868_ = -40.0f;
    private static final String f_170869_ = "left_haunch";
    private static final String f_170870_ = "right_haunch";
    private final ModelPart f_170871_;
    private final ModelPart f_170872_;
    private final ModelPart f_170873_;
    private final ModelPart f_170874_;
    private final ModelPart f_103520_;
    private final ModelPart f_170875_;
    private final ModelPart f_170876_;
    private final ModelPart f_103523_;
    private final ModelPart f_170877_;
    private final ModelPart f_170878_;
    private final ModelPart f_103526_;
    private final ModelPart f_103527_;
    private float f_103528_;
    private static final float f_170879_ = 0.6f;

    public RabbitModel(ModelPart p_170881_) {
        this.f_170871_ = p_170881_.m_171324_("left_hind_foot");
        this.f_170872_ = p_170881_.m_171324_("right_hind_foot");
        this.f_170873_ = p_170881_.m_171324_(f_170869_);
        this.f_170874_ = p_170881_.m_171324_(f_170870_);
        this.f_103520_ = p_170881_.m_171324_("body");
        this.f_170875_ = p_170881_.m_171324_("left_front_leg");
        this.f_170876_ = p_170881_.m_171324_("right_front_leg");
        this.f_103523_ = p_170881_.m_171324_("head");
        this.f_170877_ = p_170881_.m_171324_("right_ear");
        this.f_170878_ = p_170881_.m_171324_("left_ear");
        this.f_103526_ = p_170881_.m_171324_("tail");
        this.f_103527_ = p_170881_.m_171324_("nose");
    }

    public static LayerDefinition m_170882_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("left_hind_foot", CubeListBuilder.m_171558_().m_171514_(26, 24).m_171481_(-1.0f, 5.5f, -3.7f, 2.0f, 1.0f, 7.0f), PartPose.m_171419_(3.0f, 17.5f, 3.7f));
        $$1.m_171599_("right_hind_foot", CubeListBuilder.m_171558_().m_171514_(8, 24).m_171481_(-1.0f, 5.5f, -3.7f, 2.0f, 1.0f, 7.0f), PartPose.m_171419_(-3.0f, 17.5f, 3.7f));
        $$1.m_171599_(f_170869_, CubeListBuilder.m_171558_().m_171514_(30, 15).m_171481_(-1.0f, 0.0f, 0.0f, 2.0f, 4.0f, 5.0f), PartPose.m_171423_(3.0f, 17.5f, 3.7f, -0.34906584f, 0.0f, 0.0f));
        $$1.m_171599_(f_170870_, CubeListBuilder.m_171558_().m_171514_(16, 15).m_171481_(-1.0f, 0.0f, 0.0f, 2.0f, 4.0f, 5.0f), PartPose.m_171423_(-3.0f, 17.5f, 3.7f, -0.34906584f, 0.0f, 0.0f));
        $$1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -2.0f, -10.0f, 6.0f, 5.0f, 10.0f), PartPose.m_171423_(0.0f, 19.0f, 8.0f, -0.34906584f, 0.0f, 0.0f));
        $$1.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(8, 15).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.m_171423_(3.0f, 17.0f, -1.0f, -0.17453292f, 0.0f, 0.0f));
        $$1.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171481_(-1.0f, 0.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.m_171423_(-3.0f, 17.0f, -1.0f, -0.17453292f, 0.0f, 0.0f));
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-2.5f, -4.0f, -5.0f, 5.0f, 4.0f, 5.0f), PartPose.m_171419_(0.0f, 16.0f, -1.0f));
        $$1.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(52, 0).m_171481_(-2.5f, -9.0f, -1.0f, 2.0f, 5.0f, 1.0f), PartPose.m_171423_(0.0f, 16.0f, -1.0f, 0.0f, -0.2617994f, 0.0f));
        $$1.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(58, 0).m_171481_(0.5f, -9.0f, -1.0f, 2.0f, 5.0f, 1.0f), PartPose.m_171423_(0.0f, 16.0f, -1.0f, 0.0f, 0.2617994f, 0.0f));
        $$1.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(52, 6).m_171481_(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 2.0f), PartPose.m_171423_(0.0f, 20.0f, 7.0f, -0.3490659f, 0.0f, 0.0f));
        $$1.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(32, 9).m_171481_(-0.5f, -2.5f, -5.5f, 1.0f, 1.0f, 1.0f), PartPose.m_171419_(0.0f, 16.0f, -1.0f));
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_7695_(PoseStack p_103555_, VertexConsumer p_103556_, int p_103557_, int p_103558_, float p_103559_, float p_103560_, float p_103561_, float p_103562_) {
        if (this.f_102610_) {
            float $$8 = 1.5f;
            p_103555_.m_85836_();
            p_103555_.m_85841_(0.56666666f, 0.56666666f, 0.56666666f);
            p_103555_.m_85837_(0.0, 1.375, 0.125);
            ImmutableList.of((Object)this.f_103523_, (Object)this.f_170878_, (Object)this.f_170877_, (Object)this.f_103527_).forEach(p_103597_ -> p_103597_.m_104306_(p_103555_, p_103556_, p_103557_, p_103558_, p_103559_, p_103560_, p_103561_, p_103562_));
            p_103555_.m_85849_();
            p_103555_.m_85836_();
            p_103555_.m_85841_(0.4f, 0.4f, 0.4f);
            p_103555_.m_85837_(0.0, 2.25, 0.0);
            ImmutableList.of((Object)this.f_170871_, (Object)this.f_170872_, (Object)this.f_170873_, (Object)this.f_170874_, (Object)this.f_103520_, (Object)this.f_170875_, (Object)this.f_170876_, (Object)this.f_103526_).forEach(p_103587_ -> p_103587_.m_104306_(p_103555_, p_103556_, p_103557_, p_103558_, p_103559_, p_103560_, p_103561_, p_103562_));
            p_103555_.m_85849_();
        } else {
            p_103555_.m_85836_();
            p_103555_.m_85841_(0.6f, 0.6f, 0.6f);
            p_103555_.m_85837_(0.0, 1.0, 0.0);
            ImmutableList.of((Object)this.f_170871_, (Object)this.f_170872_, (Object)this.f_170873_, (Object)this.f_170874_, (Object)this.f_103520_, (Object)this.f_170875_, (Object)this.f_170876_, (Object)this.f_103523_, (Object)this.f_170877_, (Object)this.f_170878_, (Object)this.f_103526_, (Object)this.f_103527_, (Object[])new ModelPart[0]).forEach(p_103572_ -> p_103572_.m_104306_(p_103555_, p_103556_, p_103557_, p_103558_, p_103559_, p_103560_, p_103561_, p_103562_));
            p_103555_.m_85849_();
        }
    }

    @Override
    public void m_6973_(T p_103548_, float p_103549_, float p_103550_, float p_103551_, float p_103552_, float p_103553_) {
        float $$6 = p_103551_ - (float)((Rabbit)p_103548_).f_19797_;
        this.f_103527_.f_104203_ = p_103553_ * ((float)Math.PI / 180);
        this.f_103523_.f_104203_ = p_103553_ * ((float)Math.PI / 180);
        this.f_170877_.f_104203_ = p_103553_ * ((float)Math.PI / 180);
        this.f_170878_.f_104203_ = p_103553_ * ((float)Math.PI / 180);
        this.f_103527_.f_104204_ = p_103552_ * ((float)Math.PI / 180);
        this.f_103523_.f_104204_ = p_103552_ * ((float)Math.PI / 180);
        this.f_170877_.f_104204_ = this.f_103527_.f_104204_ - 0.2617994f;
        this.f_170878_.f_104204_ = this.f_103527_.f_104204_ + 0.2617994f;
        this.f_103528_ = Mth.m_14031_(((Rabbit)p_103548_).m_29735_($$6) * (float)Math.PI);
        this.f_170873_.f_104203_ = (this.f_103528_ * 50.0f - 21.0f) * ((float)Math.PI / 180);
        this.f_170874_.f_104203_ = (this.f_103528_ * 50.0f - 21.0f) * ((float)Math.PI / 180);
        this.f_170871_.f_104203_ = this.f_103528_ * 50.0f * ((float)Math.PI / 180);
        this.f_170872_.f_104203_ = this.f_103528_ * 50.0f * ((float)Math.PI / 180);
        this.f_170875_.f_104203_ = (this.f_103528_ * -40.0f - 11.0f) * ((float)Math.PI / 180);
        this.f_170876_.f_104203_ = (this.f_103528_ * -40.0f - 11.0f) * ((float)Math.PI / 180);
    }

    @Override
    public void m_6839_(T p_103543_, float p_103544_, float p_103545_, float p_103546_) {
        super.m_6839_(p_103543_, p_103544_, p_103545_, p_103546_);
        this.f_103528_ = Mth.m_14031_(((Rabbit)p_103543_).m_29735_(p_103546_) * (float)Math.PI);
    }
}

