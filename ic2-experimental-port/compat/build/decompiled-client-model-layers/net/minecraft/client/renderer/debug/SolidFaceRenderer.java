/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SolidFaceRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113666_;

    public SolidFaceRenderer(Minecraft p_113668_) {
        this.f_113666_ = p_113668_;
    }

    @Override
    public void m_7790_(PoseStack p_113670_, MultiBufferSource p_113671_, double p_113672_, double p_113673_, double p_113674_) {
        Level $$5 = this.f_113666_.f_91074_.f_19853_;
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69832_(2.0f);
        RenderSystem.m_69472_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        BlockPos $$6 = new BlockPos(p_113672_, p_113673_, p_113674_);
        for (BlockPos $$7 : BlockPos.m_121940_($$6.m_7918_(-6, -6, -6), $$6.m_7918_(6, 6, 6))) {
            BlockState $$8 = $$5.m_8055_($$7);
            if ($$8.m_60713_(Blocks.f_50016_)) continue;
            VoxelShape $$9 = $$8.m_60808_($$5, $$7);
            for (AABB $$10 : $$9.m_83299_()) {
                AABB $$11 = $$10.m_82338_($$7).m_82400_(0.002).m_82386_(-p_113672_, -p_113673_, -p_113674_);
                double $$12 = $$11.f_82288_;
                double $$13 = $$11.f_82289_;
                double $$14 = $$11.f_82290_;
                double $$15 = $$11.f_82291_;
                double $$16 = $$11.f_82292_;
                double $$17 = $$11.f_82293_;
                float $$18 = 1.0f;
                float $$19 = 0.0f;
                float $$20 = 0.0f;
                float $$21 = 0.5f;
                if ($$8.m_60783_($$5, $$7, Direction.WEST)) {
                    Tesselator $$22 = Tesselator.m_85913_();
                    BufferBuilder $$23 = $$22.m_85915_();
                    $$23.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                    $$23.m_5483_($$12, $$13, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$23.m_5483_($$12, $$13, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$23.m_5483_($$12, $$16, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$23.m_5483_($$12, $$16, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$22.m_85914_();
                }
                if ($$8.m_60783_($$5, $$7, Direction.SOUTH)) {
                    Tesselator $$24 = Tesselator.m_85913_();
                    BufferBuilder $$25 = $$24.m_85915_();
                    $$25.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                    $$25.m_5483_($$12, $$16, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$25.m_5483_($$12, $$13, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$25.m_5483_($$15, $$16, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$25.m_5483_($$15, $$13, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$24.m_85914_();
                }
                if ($$8.m_60783_($$5, $$7, Direction.EAST)) {
                    Tesselator $$26 = Tesselator.m_85913_();
                    BufferBuilder $$27 = $$26.m_85915_();
                    $$27.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                    $$27.m_5483_($$15, $$13, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$27.m_5483_($$15, $$13, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$27.m_5483_($$15, $$16, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$27.m_5483_($$15, $$16, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$26.m_85914_();
                }
                if ($$8.m_60783_($$5, $$7, Direction.NORTH)) {
                    Tesselator $$28 = Tesselator.m_85913_();
                    BufferBuilder $$29 = $$28.m_85915_();
                    $$29.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                    $$29.m_5483_($$15, $$16, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$29.m_5483_($$15, $$13, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$29.m_5483_($$12, $$16, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$29.m_5483_($$12, $$13, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$28.m_85914_();
                }
                if ($$8.m_60783_($$5, $$7, Direction.DOWN)) {
                    Tesselator $$30 = Tesselator.m_85913_();
                    BufferBuilder $$31 = $$30.m_85915_();
                    $$31.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                    $$31.m_5483_($$12, $$13, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$31.m_5483_($$15, $$13, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$31.m_5483_($$12, $$13, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$31.m_5483_($$15, $$13, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                    $$30.m_85914_();
                }
                if (!$$8.m_60783_($$5, $$7, Direction.UP)) continue;
                Tesselator $$32 = Tesselator.m_85913_();
                BufferBuilder $$33 = $$32.m_85915_();
                $$33.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                $$33.m_5483_($$12, $$16, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                $$33.m_5483_($$12, $$16, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                $$33.m_5483_($$15, $$16, $$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                $$33.m_5483_($$15, $$16, $$17).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                $$32.m_85914_();
            }
        }
        RenderSystem.m_69458_(true);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }
}

