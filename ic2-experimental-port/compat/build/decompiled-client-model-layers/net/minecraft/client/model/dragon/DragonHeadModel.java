/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DragonHeadModel
extends SkullModelBase {
    private final ModelPart f_104183_;
    private final ModelPart f_104184_;

    public DragonHeadModel(ModelPart p_171097_) {
        this.f_104183_ = p_171097_.m_171324_("head");
        this.f_104184_ = this.f_104183_.m_171324_("jaw");
    }

    public static LayerDefinition m_171098_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        float $$2 = -16.0f;
        PartDefinition $$3 = $$1.m_171599_("head", CubeListBuilder.m_171558_().m_171534_("upper_lip", -6.0f, -1.0f, -24.0f, 12, 5, 16, 176, 44).m_171534_("upper_head", -8.0f, -8.0f, -10.0f, 16, 16, 16, 112, 30).m_171555_(true).m_171534_("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, 0, 0).m_171534_("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, 112, 0).m_171555_(false).m_171534_("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, 0, 0).m_171534_("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, 112, 0), PartPose.f_171404_);
        $$3.m_171599_("jaw", CubeListBuilder.m_171558_().m_171514_(176, 65).m_171517_("jaw", -6.0f, 0.0f, -16.0f, 12.0f, 4.0f, 16.0f), PartPose.m_171419_(0.0f, 4.0f, -8.0f));
        return LayerDefinition.m_171565_($$0, 256, 256);
    }

    @Override
    public void m_6251_(float p_104188_, float p_104189_, float p_104190_) {
        this.f_104184_.f_104203_ = (float)(Math.sin(p_104188_ * (float)Math.PI * 0.2f) + 1.0) * 0.2f;
        this.f_104183_.f_104204_ = p_104189_ * ((float)Math.PI / 180);
        this.f_104183_.f_104203_ = p_104190_ * ((float)Math.PI / 180);
    }

    @Override
    public void m_7695_(PoseStack p_104192_, VertexConsumer p_104193_, int p_104194_, int p_104195_, float p_104196_, float p_104197_, float p_104198_, float p_104199_) {
        p_104192_.m_85836_();
        p_104192_.m_85837_(0.0, -0.374375f, 0.0);
        p_104192_.m_85841_(0.75f, 0.75f, 0.75f);
        this.f_104183_.m_104306_(p_104192_, p_104193_, p_104194_, p_104195_, p_104196_, p_104197_, p_104198_, p_104199_);
        p_104192_.m_85849_();
    }
}

