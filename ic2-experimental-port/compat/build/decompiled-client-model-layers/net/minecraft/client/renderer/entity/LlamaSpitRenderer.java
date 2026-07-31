/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.LlamaSpitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.LlamaSpit;

public class LlamaSpitRenderer
extends EntityRenderer<LlamaSpit> {
    private static final ResourceLocation f_115356_ = new ResourceLocation("textures/entity/llama/spit.png");
    private final LlamaSpitModel<LlamaSpit> f_115357_;

    public LlamaSpitRenderer(EntityRendererProvider.Context p_174296_) {
        super(p_174296_);
        this.f_115357_ = new LlamaSpitModel(p_174296_.m_174023_(ModelLayers.f_171196_));
    }

    @Override
    public void m_7392_(LlamaSpit p_115373_, float p_115374_, float p_115375_, PoseStack p_115376_, MultiBufferSource p_115377_, int p_115378_) {
        p_115376_.m_85836_();
        p_115376_.m_85837_(0.0, 0.15f, 0.0);
        p_115376_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(p_115375_, p_115373_.f_19859_, p_115373_.m_146908_()) - 90.0f));
        p_115376_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(p_115375_, p_115373_.f_19860_, p_115373_.m_146909_())));
        this.f_115357_.m_6973_(p_115373_, p_115375_, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer $$6 = p_115377_.m_6299_(this.f_115357_.m_103119_(f_115356_));
        this.f_115357_.m_7695_(p_115376_, $$6, p_115378_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_115376_.m_85849_();
        super.m_7392_(p_115373_, p_115374_, p_115375_, p_115376_, p_115377_, p_115378_);
    }

    @Override
    public ResourceLocation m_5478_(LlamaSpit p_115371_) {
        return f_115356_;
    }
}

