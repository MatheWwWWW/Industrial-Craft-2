/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TridentModel
extends Model {
    public static final ResourceLocation f_103914_ = new ResourceLocation("textures/entity/trident.png");
    private final ModelPart f_171014_;

    public TridentModel(ModelPart p_171016_) {
        super(RenderType::m_110446_);
        this.f_171014_ = p_171016_;
    }

    public static LayerDefinition m_171017_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        PartDefinition $$2 = $$1.m_171599_("pole", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171481_(-0.5f, 2.0f, -0.5f, 1.0f, 25.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(4, 0).m_171481_(-1.5f, 0.0f, -0.5f, 3.0f, 2.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("left_spike", CubeListBuilder.m_171558_().m_171514_(4, 3).m_171481_(-2.5f, -3.0f, -0.5f, 1.0f, 4.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("middle_spike", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-0.5f, -4.0f, -0.5f, 1.0f, 4.0f, 1.0f), PartPose.f_171404_);
        $$2.m_171599_("right_spike", CubeListBuilder.m_171558_().m_171514_(4, 3).m_171480_().m_171481_(1.5f, -3.0f, -0.5f, 1.0f, 4.0f, 1.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 32, 32);
    }

    @Override
    public void m_7695_(PoseStack p_103919_, VertexConsumer p_103920_, int p_103921_, int p_103922_, float p_103923_, float p_103924_, float p_103925_, float p_103926_) {
        this.f_171014_.m_104306_(p_103919_, p_103920_, p_103921_, p_103922_, p_103923_, p_103924_, p_103925_, p_103926_);
    }
}

