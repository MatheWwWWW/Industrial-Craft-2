/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingRenderer
extends EntityRenderer<Painting> {
    public PaintingRenderer(EntityRendererProvider.Context p_174332_) {
        super(p_174332_);
    }

    @Override
    public void m_7392_(Painting p_115552_, float p_115553_, float p_115554_, PoseStack p_115555_, MultiBufferSource p_115556_, int p_115557_) {
        p_115555_.m_85836_();
        p_115555_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - p_115553_));
        PaintingVariant $$6 = p_115552_.m_218901_().m_203334_();
        float $$7 = 0.0625f;
        p_115555_.m_85841_(0.0625f, 0.0625f, 0.0625f);
        VertexConsumer $$8 = p_115556_.m_6299_(RenderType.m_110446_(this.m_5478_(p_115552_)));
        PaintingTextureManager $$9 = Minecraft.m_91087_().m_91305_();
        this.m_115558_(p_115555_, $$8, p_115552_, $$6.m_218908_(), $$6.m_218909_(), $$9.m_235033_($$6), $$9.m_118806_());
        p_115555_.m_85849_();
        super.m_7392_(p_115552_, p_115553_, p_115554_, p_115555_, p_115556_, p_115557_);
    }

    @Override
    public ResourceLocation m_5478_(Painting p_115550_) {
        return Minecraft.m_91087_().m_91305_().m_118806_().m_118414_().m_118330_();
    }

    private void m_115558_(PoseStack p_115559_, VertexConsumer p_115560_, Painting p_115561_, int p_115562_, int p_115563_, TextureAtlasSprite p_115564_, TextureAtlasSprite p_115565_) {
        PoseStack.Pose $$7 = p_115559_.m_85850_();
        Matrix4f $$8 = $$7.m_85861_();
        Matrix3f $$9 = $$7.m_85864_();
        float $$10 = (float)(-p_115562_) / 2.0f;
        float $$11 = (float)(-p_115563_) / 2.0f;
        float $$12 = 0.5f;
        float $$13 = p_115565_.m_118409_();
        float $$14 = p_115565_.m_118410_();
        float $$15 = p_115565_.m_118411_();
        float $$16 = p_115565_.m_118412_();
        float $$17 = p_115565_.m_118409_();
        float $$18 = p_115565_.m_118410_();
        float $$19 = p_115565_.m_118411_();
        float $$20 = p_115565_.m_118393_(1.0);
        float $$21 = p_115565_.m_118409_();
        float $$22 = p_115565_.m_118367_(1.0);
        float $$23 = p_115565_.m_118411_();
        float $$24 = p_115565_.m_118412_();
        int $$25 = p_115562_ / 16;
        int $$26 = p_115563_ / 16;
        double $$27 = 16.0 / (double)$$25;
        double $$28 = 16.0 / (double)$$26;
        for (int $$29 = 0; $$29 < $$25; ++$$29) {
            for (int $$30 = 0; $$30 < $$26; ++$$30) {
                float $$31 = $$10 + (float)(($$29 + 1) * 16);
                float $$32 = $$10 + (float)($$29 * 16);
                float $$33 = $$11 + (float)(($$30 + 1) * 16);
                float $$34 = $$11 + (float)($$30 * 16);
                int $$35 = p_115561_.m_146903_();
                int $$36 = Mth.m_14107_(p_115561_.m_20186_() + (double)(($$33 + $$34) / 2.0f / 16.0f));
                int $$37 = p_115561_.m_146907_();
                Direction $$38 = p_115561_.m_6350_();
                if ($$38 == Direction.NORTH) {
                    $$35 = Mth.m_14107_(p_115561_.m_20185_() + (double)(($$31 + $$32) / 2.0f / 16.0f));
                }
                if ($$38 == Direction.WEST) {
                    $$37 = Mth.m_14107_(p_115561_.m_20189_() - (double)(($$31 + $$32) / 2.0f / 16.0f));
                }
                if ($$38 == Direction.SOUTH) {
                    $$35 = Mth.m_14107_(p_115561_.m_20185_() - (double)(($$31 + $$32) / 2.0f / 16.0f));
                }
                if ($$38 == Direction.EAST) {
                    $$37 = Mth.m_14107_(p_115561_.m_20189_() + (double)(($$31 + $$32) / 2.0f / 16.0f));
                }
                int $$39 = LevelRenderer.m_109541_(p_115561_.f_19853_, new BlockPos($$35, $$36, $$37));
                float $$40 = p_115564_.m_118367_($$27 * (double)($$25 - $$29));
                float $$41 = p_115564_.m_118367_($$27 * (double)($$25 - ($$29 + 1)));
                float $$42 = p_115564_.m_118393_($$28 * (double)($$26 - $$30));
                float $$43 = p_115564_.m_118393_($$28 * (double)($$26 - ($$30 + 1)));
                this.m_115536_($$8, $$9, p_115560_, $$31, $$34, $$41, $$42, -0.5f, 0, 0, -1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$34, $$40, $$42, -0.5f, 0, 0, -1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$33, $$40, $$43, -0.5f, 0, 0, -1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$33, $$41, $$43, -0.5f, 0, 0, -1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$33, $$14, $$15, 0.5f, 0, 0, 1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$33, $$13, $$15, 0.5f, 0, 0, 1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$34, $$13, $$16, 0.5f, 0, 0, 1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$34, $$14, $$16, 0.5f, 0, 0, 1, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$33, $$17, $$19, -0.5f, 0, 1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$33, $$18, $$19, -0.5f, 0, 1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$33, $$18, $$20, 0.5f, 0, 1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$33, $$17, $$20, 0.5f, 0, 1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$34, $$17, $$19, 0.5f, 0, -1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$34, $$18, $$19, 0.5f, 0, -1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$34, $$18, $$20, -0.5f, 0, -1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$34, $$17, $$20, -0.5f, 0, -1, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$33, $$22, $$23, 0.5f, -1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$34, $$22, $$24, 0.5f, -1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$34, $$21, $$24, -0.5f, -1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$31, $$33, $$21, $$23, -0.5f, -1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$33, $$22, $$23, -0.5f, 1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$34, $$22, $$24, -0.5f, 1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$34, $$21, $$24, 0.5f, 1, 0, 0, $$39);
                this.m_115536_($$8, $$9, p_115560_, $$32, $$33, $$21, $$23, 0.5f, 1, 0, 0, $$39);
            }
        }
    }

    private void m_115536_(Matrix4f p_115537_, Matrix3f p_115538_, VertexConsumer p_115539_, float p_115540_, float p_115541_, float p_115542_, float p_115543_, float p_115544_, int p_115545_, int p_115546_, int p_115547_, int p_115548_) {
        p_115539_.m_85982_(p_115537_, p_115540_, p_115541_, p_115544_).m_6122_(255, 255, 255, 255).m_7421_(p_115542_, p_115543_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_115548_).m_85977_(p_115538_, p_115545_, p_115546_, p_115547_).m_5752_();
    }
}

