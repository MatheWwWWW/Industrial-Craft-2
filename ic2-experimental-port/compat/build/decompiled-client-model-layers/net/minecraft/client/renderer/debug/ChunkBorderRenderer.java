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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;

public class ChunkBorderRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113354_;
    private static final int f_194450_ = FastColor.ARGB32.m_13660_(255, 0, 155, 155);
    private static final int f_194451_ = FastColor.ARGB32.m_13660_(255, 255, 255, 0);

    public ChunkBorderRenderer(Minecraft p_113356_) {
        this.f_113354_ = p_113356_;
    }

    @Override
    public void m_7790_(PoseStack p_113358_, MultiBufferSource p_113359_, double p_113360_, double p_113361_, double p_113362_) {
        RenderSystem.m_69482_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        Entity $$5 = this.f_113354_.f_91063_.m_109153_().m_90592_();
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        double $$8 = (double)this.f_113354_.f_91073_.m_141937_() - p_113361_;
        double $$9 = (double)this.f_113354_.f_91073_.m_151558_() - p_113361_;
        RenderSystem.m_69472_();
        RenderSystem.m_69461_();
        ChunkPos $$10 = $$5.m_146902_();
        double $$11 = (double)$$10.m_45604_() - p_113360_;
        double $$12 = (double)$$10.m_45605_() - p_113362_;
        RenderSystem.m_69832_(1.0f);
        $$7.m_166779_(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.f_85815_);
        for (int $$13 = -16; $$13 <= 32; $$13 += 16) {
            for (int $$14 = -16; $$14 <= 32; $$14 += 16) {
                $$7.m_5483_($$11 + (double)$$13, $$8, $$12 + (double)$$14).m_85950_(1.0f, 0.0f, 0.0f, 0.0f).m_5752_();
                $$7.m_5483_($$11 + (double)$$13, $$8, $$12 + (double)$$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                $$7.m_5483_($$11 + (double)$$13, $$9, $$12 + (double)$$14).m_85950_(1.0f, 0.0f, 0.0f, 0.5f).m_5752_();
                $$7.m_5483_($$11 + (double)$$13, $$9, $$12 + (double)$$14).m_85950_(1.0f, 0.0f, 0.0f, 0.0f).m_5752_();
            }
        }
        for (int $$15 = 2; $$15 < 16; $$15 += 2) {
            int $$16 = $$15 % 4 == 0 ? f_194450_ : f_194451_;
            $$7.m_5483_($$11 + (double)$$15, $$8, $$12).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$8, $$12).m_193479_($$16).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$9, $$12).m_193479_($$16).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$9, $$12).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$8, $$12 + 16.0).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$8, $$12 + 16.0).m_193479_($$16).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$9, $$12 + 16.0).m_193479_($$16).m_5752_();
            $$7.m_5483_($$11 + (double)$$15, $$9, $$12 + 16.0).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
        }
        for (int $$17 = 2; $$17 < 16; $$17 += 2) {
            int $$18 = $$17 % 4 == 0 ? f_194450_ : f_194451_;
            $$7.m_5483_($$11, $$8, $$12 + (double)$$17).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11, $$8, $$12 + (double)$$17).m_193479_($$18).m_5752_();
            $$7.m_5483_($$11, $$9, $$12 + (double)$$17).m_193479_($$18).m_5752_();
            $$7.m_5483_($$11, $$9, $$12 + (double)$$17).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$8, $$12 + (double)$$17).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$8, $$12 + (double)$$17).m_193479_($$18).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$9, $$12 + (double)$$17).m_193479_($$18).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$9, $$12 + (double)$$17).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
        }
        for (int $$19 = this.f_113354_.f_91073_.m_141937_(); $$19 <= this.f_113354_.f_91073_.m_151558_(); $$19 += 2) {
            double $$20 = (double)$$19 - p_113361_;
            int $$21 = $$19 % 8 == 0 ? f_194450_ : f_194451_;
            $$7.m_5483_($$11, $$20, $$12).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11, $$20, $$12).m_193479_($$21).m_5752_();
            $$7.m_5483_($$11, $$20, $$12 + 16.0).m_193479_($$21).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$20, $$12 + 16.0).m_193479_($$21).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$20, $$12).m_193479_($$21).m_5752_();
            $$7.m_5483_($$11, $$20, $$12).m_193479_($$21).m_5752_();
            $$7.m_5483_($$11, $$20, $$12).m_85950_(1.0f, 1.0f, 0.0f, 0.0f).m_5752_();
        }
        $$6.m_85914_();
        RenderSystem.m_69832_(2.0f);
        $$7.m_166779_(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.f_85815_);
        for (int $$22 = 0; $$22 <= 16; $$22 += 16) {
            for (int $$23 = 0; $$23 <= 16; $$23 += 16) {
                $$7.m_5483_($$11 + (double)$$22, $$8, $$12 + (double)$$23).m_85950_(0.25f, 0.25f, 1.0f, 0.0f).m_5752_();
                $$7.m_5483_($$11 + (double)$$22, $$8, $$12 + (double)$$23).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
                $$7.m_5483_($$11 + (double)$$22, $$9, $$12 + (double)$$23).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
                $$7.m_5483_($$11 + (double)$$22, $$9, $$12 + (double)$$23).m_85950_(0.25f, 0.25f, 1.0f, 0.0f).m_5752_();
            }
        }
        for (int $$24 = this.f_113354_.f_91073_.m_141937_(); $$24 <= this.f_113354_.f_91073_.m_151558_(); $$24 += 16) {
            double $$25 = (double)$$24 - p_113361_;
            $$7.m_5483_($$11, $$25, $$12).m_85950_(0.25f, 0.25f, 1.0f, 0.0f).m_5752_();
            $$7.m_5483_($$11, $$25, $$12).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
            $$7.m_5483_($$11, $$25, $$12 + 16.0).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$25, $$12 + 16.0).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
            $$7.m_5483_($$11 + 16.0, $$25, $$12).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
            $$7.m_5483_($$11, $$25, $$12).m_85950_(0.25f, 0.25f, 1.0f, 1.0f).m_5752_();
            $$7.m_5483_($$11, $$25, $$12).m_85950_(0.25f, 0.25f, 1.0f, 0.0f).m_5752_();
        }
        $$6.m_85914_();
        RenderSystem.m_69832_(1.0f);
        RenderSystem.m_69478_();
        RenderSystem.m_69493_();
    }
}

