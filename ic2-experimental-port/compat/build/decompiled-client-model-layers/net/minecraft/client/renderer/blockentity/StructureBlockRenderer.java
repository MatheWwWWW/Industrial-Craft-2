/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;

public class StructureBlockRenderer
implements BlockEntityRenderer<StructureBlockEntity> {
    public StructureBlockRenderer(BlockEntityRendererProvider.Context p_173675_) {
    }

    @Override
    public void m_6922_(StructureBlockEntity p_112583_, float p_112584_, PoseStack p_112585_, MultiBufferSource p_112586_, int p_112587_, int p_112588_) {
        double $$33;
        double $$32;
        double $$31;
        double $$30;
        double $$17;
        double $$16;
        if (!Minecraft.m_91087_().f_91074_.m_36337_() && !Minecraft.m_91087_().f_91074_.m_5833_()) {
            return;
        }
        BlockPos $$6 = p_112583_.m_59902_();
        Vec3i $$7 = p_112583_.m_155805_();
        if ($$7.m_123341_() < 1 || $$7.m_123342_() < 1 || $$7.m_123343_() < 1) {
            return;
        }
        if (p_112583_.m_59908_() != StructureMode.SAVE && p_112583_.m_59908_() != StructureMode.LOAD) {
            return;
        }
        double $$8 = $$6.m_123341_();
        double $$9 = $$6.m_123343_();
        double $$10 = $$6.m_123342_();
        double $$11 = $$10 + (double)$$7.m_123342_();
        switch (p_112583_.m_59905_()) {
            case LEFT_RIGHT: {
                double $$12 = $$7.m_123341_();
                double $$13 = -$$7.m_123343_();
                break;
            }
            case FRONT_BACK: {
                double $$14 = -$$7.m_123341_();
                double $$15 = $$7.m_123343_();
                break;
            }
            default: {
                $$16 = $$7.m_123341_();
                $$17 = $$7.m_123343_();
            }
        }
        switch (p_112583_.m_59906_()) {
            case CLOCKWISE_90: {
                double $$18 = $$17 < 0.0 ? $$8 : $$8 + 1.0;
                double $$19 = $$16 < 0.0 ? $$9 + 1.0 : $$9;
                double $$20 = $$18 - $$17;
                double $$21 = $$19 + $$16;
                break;
            }
            case CLOCKWISE_180: {
                double $$22 = $$16 < 0.0 ? $$8 : $$8 + 1.0;
                double $$23 = $$17 < 0.0 ? $$9 : $$9 + 1.0;
                double $$24 = $$22 - $$16;
                double $$25 = $$23 - $$17;
                break;
            }
            case COUNTERCLOCKWISE_90: {
                double $$26 = $$17 < 0.0 ? $$8 + 1.0 : $$8;
                double $$27 = $$16 < 0.0 ? $$9 : $$9 + 1.0;
                double $$28 = $$26 + $$17;
                double $$29 = $$27 - $$16;
                break;
            }
            default: {
                $$30 = $$16 < 0.0 ? $$8 + 1.0 : $$8;
                $$31 = $$17 < 0.0 ? $$9 + 1.0 : $$9;
                $$32 = $$30 + $$16;
                $$33 = $$31 + $$17;
            }
        }
        float $$34 = 1.0f;
        float $$35 = 0.9f;
        float $$36 = 0.5f;
        VertexConsumer $$37 = p_112586_.m_6299_(RenderType.m_110504_());
        if (p_112583_.m_59908_() == StructureMode.SAVE || p_112583_.m_59835_()) {
            LevelRenderer.m_109621_(p_112585_, $$37, $$30, $$10, $$31, $$32, $$11, $$33, 0.9f, 0.9f, 0.9f, 1.0f, 0.5f, 0.5f, 0.5f);
        }
        if (p_112583_.m_59908_() == StructureMode.SAVE && p_112583_.m_59834_()) {
            this.m_173676_(p_112583_, $$37, $$6, p_112585_);
        }
    }

    private void m_173676_(StructureBlockEntity p_173677_, VertexConsumer p_173678_, BlockPos p_173679_, PoseStack p_173680_) {
        Level $$4 = p_173677_.m_58904_();
        BlockPos $$5 = p_173677_.m_58899_();
        BlockPos $$6 = $$5.m_121955_(p_173679_);
        for (BlockPos $$7 : BlockPos.m_121940_($$6, $$6.m_121955_(p_173677_.m_155805_()).m_7918_(-1, -1, -1))) {
            boolean $$13;
            BlockState $$8 = $$4.m_8055_($$7);
            boolean $$9 = $$8.m_60795_();
            boolean $$10 = $$8.m_60713_(Blocks.f_50454_);
            boolean $$11 = $$8.m_60713_(Blocks.f_50375_);
            boolean $$12 = $$8.m_60713_(Blocks.f_152480_);
            boolean bl = $$13 = $$10 || $$11 || $$12;
            if (!$$9 && !$$13) continue;
            float $$14 = $$9 ? 0.05f : 0.0f;
            double $$15 = (float)($$7.m_123341_() - $$5.m_123341_()) + 0.45f - $$14;
            double $$16 = (float)($$7.m_123342_() - $$5.m_123342_()) + 0.45f - $$14;
            double $$17 = (float)($$7.m_123343_() - $$5.m_123343_()) + 0.45f - $$14;
            double $$18 = (float)($$7.m_123341_() - $$5.m_123341_()) + 0.55f + $$14;
            double $$19 = (float)($$7.m_123342_() - $$5.m_123342_()) + 0.55f + $$14;
            double $$20 = (float)($$7.m_123343_() - $$5.m_123343_()) + 0.55f + $$14;
            if ($$9) {
                LevelRenderer.m_109621_(p_173680_, p_173678_, $$15, $$16, $$17, $$18, $$19, $$20, 0.5f, 0.5f, 1.0f, 1.0f, 0.5f, 0.5f, 1.0f);
                continue;
            }
            if ($$10) {
                LevelRenderer.m_109621_(p_173680_, p_173678_, $$15, $$16, $$17, $$18, $$19, $$20, 1.0f, 0.75f, 0.75f, 1.0f, 1.0f, 0.75f, 0.75f);
                continue;
            }
            if ($$11) {
                LevelRenderer.m_109621_(p_173680_, p_173678_, $$15, $$16, $$17, $$18, $$19, $$20, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f);
                continue;
            }
            if (!$$12) continue;
            LevelRenderer.m_109621_(p_173680_, p_173678_, $$15, $$16, $$17, $$18, $$19, $$20, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f);
        }
    }

    @Override
    public boolean m_5932_(StructureBlockEntity p_112581_) {
        return true;
    }

    @Override
    public int m_142163_() {
        return 96;
    }
}

