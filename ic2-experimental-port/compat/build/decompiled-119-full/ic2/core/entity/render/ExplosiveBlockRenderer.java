/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.block.BlockRenderDispatcher
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.TntMinecartRenderer
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import ic2.api.entity.block.ExplosiveEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosiveBlockRenderer
extends EntityRenderer<ExplosiveEntity> {
    private final BlockRenderDispatcher blockRenderManager;

    public ExplosiveBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.f_114477_ = 0.5f;
        this.blockRenderManager = context.m_234597_();
    }

    public void render(ExplosiveEntity explosiveEntity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.m_85836_();
        poseStack.m_85837_(0.0, 0.5, 0.0);
        int n2 = explosiveEntity.getFuse();
        if ((float)n2 - f2 + 1.0f < 10.0f) {
            float f3 = 1.0f - ((float)n2 - f2 + 1.0f) / 10.0f;
            f3 = Mth.m_14036_((float)f3, (float)0.0f, (float)1.0f);
            f3 *= f3;
            f3 *= f3;
            float f4 = 1.0f + f3 * 0.3f;
            poseStack.m_85841_(f4, f4, f4);
        }
        poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0f));
        poseStack.m_85837_(-0.5, -0.5, 0.5);
        poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        TntMinecartRenderer.m_234661_((BlockRenderDispatcher)this.blockRenderManager, (BlockState)explosiveEntity.renderBlockState, (PoseStack)poseStack, (MultiBufferSource)multiBufferSource, (int)n, (n2 / 5 % 2 == 0 ? 1 : 0) != 0);
        poseStack.m_85849_();
        super.m_7392_((Entity)explosiveEntity, f, f2, poseStack, multiBufferSource, n);
    }

    public ResourceLocation getTexture(ExplosiveEntity explosiveEntity) {
        return TextureAtlas.f_118259_;
    }
}

