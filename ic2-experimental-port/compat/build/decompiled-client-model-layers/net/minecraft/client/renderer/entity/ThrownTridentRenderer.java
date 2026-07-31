/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;

public class ThrownTridentRenderer
extends EntityRenderer<ThrownTrident> {
    public static final ResourceLocation f_116094_ = new ResourceLocation("textures/entity/trident.png");
    private final TridentModel f_116095_;

    public ThrownTridentRenderer(EntityRendererProvider.Context p_174420_) {
        super(p_174420_);
        this.f_116095_ = new TridentModel(p_174420_.m_174023_(ModelLayers.f_171255_));
    }

    @Override
    public void m_7392_(ThrownTrident p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, MultiBufferSource p_116115_, int p_116116_) {
        p_116114_.m_85836_();
        p_116114_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(p_116113_, p_116111_.f_19859_, p_116111_.m_146908_()) - 90.0f));
        p_116114_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(p_116113_, p_116111_.f_19860_, p_116111_.m_146909_()) + 90.0f));
        VertexConsumer $$6 = ItemRenderer.m_115222_(p_116115_, this.f_116095_.m_103119_(this.m_5478_(p_116111_)), false, p_116111_.m_37593_());
        this.f_116095_.m_7695_(p_116114_, $$6, p_116116_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_116114_.m_85849_();
        super.m_7392_(p_116111_, p_116112_, p_116113_, p_116114_, p_116115_, p_116116_);
    }

    @Override
    public ResourceLocation m_5478_(ThrownTrident p_116109_) {
        return f_116094_;
    }
}

