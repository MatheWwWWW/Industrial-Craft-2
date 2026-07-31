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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class ThrownItemRenderer<T extends Entity>
extends EntityRenderer<T> {
    private static final float f_174412_ = 12.25f;
    private final ItemRenderer f_116071_;
    private final float f_116072_;
    private final boolean f_116073_;

    public ThrownItemRenderer(EntityRendererProvider.Context p_174416_, float p_174417_, boolean p_174418_) {
        super(p_174416_);
        this.f_116071_ = p_174416_.m_174025_();
        this.f_116072_ = p_174417_;
        this.f_116073_ = p_174418_;
    }

    public ThrownItemRenderer(EntityRendererProvider.Context p_174414_) {
        this(p_174414_, 1.0f, false);
    }

    @Override
    protected int m_6086_(T p_116092_, BlockPos p_116093_) {
        return this.f_116073_ ? 15 : super.m_6086_(p_116092_, p_116093_);
    }

    @Override
    public void m_7392_(T p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_) {
        if (((Entity)p_116085_).f_19797_ < 2 && this.f_114476_.f_114358_.m_90592_().m_20280_((Entity)p_116085_) < 12.25) {
            return;
        }
        p_116088_.m_85836_();
        p_116088_.m_85841_(this.f_116072_, this.f_116072_, this.f_116072_);
        p_116088_.m_85845_(this.f_114476_.m_114470_());
        p_116088_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        this.f_116071_.m_174269_(((ItemSupplier)p_116085_).m_7846_(), ItemTransforms.TransformType.GROUND, p_116090_, OverlayTexture.f_118083_, p_116088_, p_116089_, ((Entity)p_116085_).m_19879_());
        p_116088_.m_85849_();
        super.m_7392_(p_116085_, p_116086_, p_116087_, p_116088_, p_116089_, p_116090_);
    }

    @Override
    public ResourceLocation m_5478_(Entity p_116083_) {
        return TextureAtlas.f_118259_;
    }
}

