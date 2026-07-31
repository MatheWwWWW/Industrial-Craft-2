/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;

public class WitchRenderer
extends MobRenderer<Witch, WitchModel<Witch>> {
    private static final ResourceLocation f_116378_ = new ResourceLocation("textures/entity/witch.png");

    public WitchRenderer(EntityRendererProvider.Context p_174443_) {
        super(p_174443_, new WitchModel(p_174443_.m_174023_(ModelLayers.f_171213_)), 0.5f);
        this.m_115326_(new WitchItemLayer<Witch>(this, p_174443_.m_234598_()));
    }

    @Override
    public void m_7392_(Witch p_116412_, float p_116413_, float p_116414_, PoseStack p_116415_, MultiBufferSource p_116416_, int p_116417_) {
        ((WitchModel)this.f_115290_).m_104074_(!p_116412_.m_21205_().m_41619_());
        super.m_7392_(p_116412_, p_116413_, p_116414_, p_116415_, p_116416_, p_116417_);
    }

    @Override
    public ResourceLocation m_5478_(Witch p_116410_) {
        return f_116378_;
    }

    @Override
    protected void m_7546_(Witch p_116419_, PoseStack p_116420_, float p_116421_) {
        float $$3 = 0.9375f;
        p_116420_.m_85841_(0.9375f, 0.9375f, 0.9375f);
    }
}

