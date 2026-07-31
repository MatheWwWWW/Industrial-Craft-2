/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;

public class FireworkEntityRenderer
extends EntityRenderer<FireworkRocketEntity> {
    private final ItemRenderer f_114640_;

    public FireworkEntityRenderer(EntityRendererProvider.Context p_174114_) {
        super(p_174114_);
        this.f_114640_ = p_174114_.m_174025_();
    }

    @Override
    public void m_7392_(FireworkRocketEntity p_114656_, float p_114657_, float p_114658_, PoseStack p_114659_, MultiBufferSource p_114660_, int p_114661_) {
        p_114659_.m_85836_();
        p_114659_.m_85845_(this.f_114476_.m_114470_());
        p_114659_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        if (p_114656_.m_37079_()) {
            p_114659_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
            p_114659_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
            p_114659_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
        }
        this.f_114640_.m_174269_(p_114656_.m_7846_(), ItemTransforms.TransformType.GROUND, p_114661_, OverlayTexture.f_118083_, p_114659_, p_114660_, p_114656_.m_19879_());
        p_114659_.m_85849_();
        super.m_7392_(p_114656_, p_114657_, p_114658_, p_114659_, p_114660_, p_114661_);
    }

    @Override
    public ResourceLocation m_5478_(FireworkRocketEntity p_114654_) {
        return TextureAtlas.f_118259_;
    }
}

