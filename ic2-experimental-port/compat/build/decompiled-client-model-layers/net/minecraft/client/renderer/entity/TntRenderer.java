/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Blocks;

public class TntRenderer
extends EntityRenderer<PrimedTnt> {
    private final BlockRenderDispatcher f_234668_;

    public TntRenderer(EntityRendererProvider.Context p_174426_) {
        super(p_174426_);
        this.f_114477_ = 0.5f;
        this.f_234668_ = p_174426_.m_234597_();
    }

    @Override
    public void m_7392_(PrimedTnt p_116177_, float p_116178_, float p_116179_, PoseStack p_116180_, MultiBufferSource p_116181_, int p_116182_) {
        p_116180_.m_85836_();
        p_116180_.m_85837_(0.0, 0.5, 0.0);
        int $$6 = p_116177_.m_32100_();
        if ((float)$$6 - p_116179_ + 1.0f < 10.0f) {
            float $$7 = 1.0f - ((float)$$6 - p_116179_ + 1.0f) / 10.0f;
            $$7 = Mth.m_14036_($$7, 0.0f, 1.0f);
            $$7 *= $$7;
            $$7 *= $$7;
            float $$8 = 1.0f + $$7 * 0.3f;
            p_116180_.m_85841_($$8, $$8, $$8);
        }
        p_116180_.m_85845_(Vector3f.f_122225_.m_122240_(-90.0f));
        p_116180_.m_85837_(-0.5, -0.5, 0.5);
        p_116180_.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        TntMinecartRenderer.m_234661_(this.f_234668_, Blocks.f_50077_.m_49966_(), p_116180_, p_116181_, p_116182_, $$6 / 5 % 2 == 0);
        p_116180_.m_85849_();
        super.m_7392_(p_116177_, p_116178_, p_116179_, p_116180_, p_116181_, p_116182_);
    }

    @Override
    public ResourceLocation m_5478_(PrimedTnt p_116175_) {
        return TextureAtlas.f_118259_;
    }
}

