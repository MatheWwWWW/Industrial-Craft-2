/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SpinAttackEffectLayer<T extends LivingEntity>
extends RenderLayer<T, PlayerModel<T>> {
    public static final ResourceLocation f_117509_ = new ResourceLocation("textures/entity/trident_riptide.png");
    public static final String f_174538_ = "box";
    private final ModelPart f_117510_;

    public SpinAttackEffectLayer(RenderLayerParent<T, PlayerModel<T>> p_174540_, EntityModelSet p_174541_) {
        super(p_174540_);
        ModelPart $$2 = p_174541_.m_171103_(ModelLayers.f_171169_);
        this.f_117510_ = $$2.m_171324_(f_174538_);
    }

    public static LayerDefinition m_174542_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_174538_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -16.0f, -8.0f, 16.0f, 32.0f, 16.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6494_(PoseStack p_117526_, MultiBufferSource p_117527_, int p_117528_, T p_117529_, float p_117530_, float p_117531_, float p_117532_, float p_117533_, float p_117534_, float p_117535_) {
        if (!((LivingEntity)p_117529_).m_21209_()) {
            return;
        }
        VertexConsumer $$10 = p_117527_.m_6299_(RenderType.m_110458_(f_117509_));
        for (int $$11 = 0; $$11 < 3; ++$$11) {
            p_117526_.m_85836_();
            float $$12 = p_117533_ * (float)(-(45 + $$11 * 5));
            p_117526_.m_85845_(Vector3f.f_122225_.m_122240_($$12));
            float $$13 = 0.75f * (float)$$11;
            p_117526_.m_85841_($$13, $$13, $$13);
            p_117526_.m_85837_(0.0, -0.2f + 0.6f * (float)$$11, 0.0);
            this.f_117510_.m_104301_(p_117526_, $$10, p_117528_, OverlayTexture.f_118083_);
            p_117526_.m_85849_();
        }
    }
}

