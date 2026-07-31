/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SkullModel
extends SkullModelBase {
    private final ModelPart f_170943_;
    protected final ModelPart f_103804_;

    public SkullModel(ModelPart p_170945_) {
        this.f_170943_ = p_170945_;
        this.f_103804_ = p_170945_.m_171324_("head");
    }

    public static MeshDefinition m_170946_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        return $$0;
    }

    public static LayerDefinition m_170947_() {
        MeshDefinition $$0 = SkullModel.m_170946_();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171597_("head").m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.25f)), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    public static LayerDefinition m_170948_() {
        MeshDefinition $$0 = SkullModel.m_170946_();
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_6251_(float p_103811_, float p_103812_, float p_103813_) {
        this.f_103804_.f_104204_ = p_103812_ * ((float)Math.PI / 180);
        this.f_103804_.f_104203_ = p_103813_ * ((float)Math.PI / 180);
    }

    @Override
    public void m_7695_(PoseStack p_103815_, VertexConsumer p_103816_, int p_103817_, int p_103818_, float p_103819_, float p_103820_, float p_103821_, float p_103822_) {
        this.f_170943_.m_104306_(p_103815_, p_103816_, p_103817_, p_103818_, p_103819_, p_103820_, p_103821_, p_103822_);
    }
}

