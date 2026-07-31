/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Locale;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

public class PathfindingRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Map<Integer, Path> f_113607_ = Maps.newHashMap();
    private final Map<Integer, Float> f_113608_ = Maps.newHashMap();
    private final Map<Integer, Long> f_113609_ = Maps.newHashMap();
    private static final long f_173893_ = 5000L;
    private static final float f_173894_ = 80.0f;
    private static final boolean f_173895_ = true;
    private static final boolean f_173896_ = false;
    private static final boolean f_173897_ = false;
    private static final boolean f_173898_ = true;
    private static final boolean f_173899_ = true;
    private static final float f_173900_ = 0.02f;

    public void m_113611_(int p_113612_, Path p_113613_, float p_113614_) {
        this.f_113607_.put(p_113612_, p_113613_);
        this.f_113609_.put(p_113612_, Util.m_137550_());
        this.f_113608_.put(p_113612_, Float.valueOf(p_113614_));
    }

    @Override
    public void m_7790_(PoseStack p_113629_, MultiBufferSource p_113630_, double p_113631_, double p_113632_, double p_113633_) {
        if (this.f_113607_.isEmpty()) {
            return;
        }
        long $$5 = Util.m_137550_();
        for (Integer $$6 : this.f_113607_.keySet()) {
            Path $$7 = this.f_113607_.get($$6);
            float $$8 = this.f_113608_.get($$6).floatValue();
            PathfindingRenderer.m_113620_($$7, $$8, true, true, p_113631_, p_113632_, p_113633_);
        }
        for (Integer $$9 : this.f_113609_.keySet().toArray(new Integer[0])) {
            if ($$5 - this.f_113609_.get($$9) <= 5000L) continue;
            this.f_113607_.remove($$9);
            this.f_113609_.remove($$9);
        }
    }

    public static void m_113620_(Path p_113621_, float p_113622_, boolean p_113623_, boolean p_113624_, double p_113625_, double p_113626_, double p_113627_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        RenderSystem.m_69832_(6.0f);
        PathfindingRenderer.m_113639_(p_113621_, p_113622_, p_113623_, p_113624_, p_113625_, p_113626_, p_113627_);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }

    private static void m_113639_(Path p_113640_, float p_113641_, boolean p_113642_, boolean p_113643_, double p_113644_, double p_113645_, double p_113646_) {
        PathfindingRenderer.m_113615_(p_113640_, p_113644_, p_113645_, p_113646_);
        BlockPos $$7 = p_113640_.m_77406_();
        if (PathfindingRenderer.m_113634_($$7, p_113644_, p_113645_, p_113646_) <= 80.0f) {
            DebugRenderer.m_113451_(new AABB((float)$$7.m_123341_() + 0.25f, (float)$$7.m_123342_() + 0.25f, (double)$$7.m_123343_() + 0.25, (float)$$7.m_123341_() + 0.75f, (float)$$7.m_123342_() + 0.75f, (float)$$7.m_123343_() + 0.75f).m_82386_(-p_113644_, -p_113645_, -p_113646_), 0.0f, 1.0f, 0.0f, 0.5f);
            for (int $$8 = 0; $$8 < p_113640_.m_77398_(); ++$$8) {
                Node $$9 = p_113640_.m_77375_($$8);
                if (!(PathfindingRenderer.m_113634_($$9.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0f)) continue;
                float $$10 = $$8 == p_113640_.m_77399_() ? 1.0f : 0.0f;
                float $$11 = $$8 == p_113640_.m_77399_() ? 0.0f : 1.0f;
                DebugRenderer.m_113451_(new AABB((float)$$9.f_77271_ + 0.5f - p_113641_, (float)$$9.f_77272_ + 0.01f * (float)$$8, (float)$$9.f_77273_ + 0.5f - p_113641_, (float)$$9.f_77271_ + 0.5f + p_113641_, (float)$$9.f_77272_ + 0.25f + 0.01f * (float)$$8, (float)$$9.f_77273_ + 0.5f + p_113641_).m_82386_(-p_113644_, -p_113645_, -p_113646_), $$10, 0.0f, $$11, 0.5f);
            }
        }
        if (p_113642_) {
            for (Node $$12 : p_113640_.m_77405_()) {
                if (!(PathfindingRenderer.m_113634_($$12.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0f)) continue;
                DebugRenderer.m_113451_(new AABB((float)$$12.f_77271_ + 0.5f - p_113641_ / 2.0f, (float)$$12.f_77272_ + 0.01f, (float)$$12.f_77273_ + 0.5f - p_113641_ / 2.0f, (float)$$12.f_77271_ + 0.5f + p_113641_ / 2.0f, (double)$$12.f_77272_ + 0.1, (float)$$12.f_77273_ + 0.5f + p_113641_ / 2.0f).m_82386_(-p_113644_, -p_113645_, -p_113646_), 1.0f, 0.8f, 0.8f, 0.5f);
            }
            for (Node $$13 : p_113640_.m_77404_()) {
                if (!(PathfindingRenderer.m_113634_($$13.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0f)) continue;
                DebugRenderer.m_113451_(new AABB((float)$$13.f_77271_ + 0.5f - p_113641_ / 2.0f, (float)$$13.f_77272_ + 0.01f, (float)$$13.f_77273_ + 0.5f - p_113641_ / 2.0f, (float)$$13.f_77271_ + 0.5f + p_113641_ / 2.0f, (double)$$13.f_77272_ + 0.1, (float)$$13.f_77273_ + 0.5f + p_113641_ / 2.0f).m_82386_(-p_113644_, -p_113645_, -p_113646_), 0.8f, 1.0f, 1.0f, 0.5f);
            }
        }
        if (p_113643_) {
            for (int $$14 = 0; $$14 < p_113640_.m_77398_(); ++$$14) {
                Node $$15 = p_113640_.m_77375_($$14);
                if (!(PathfindingRenderer.m_113634_($$15.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0f)) continue;
                DebugRenderer.m_113490_(String.valueOf((Object)$$15.f_77282_), (double)$$15.f_77271_ + 0.5, (double)$$15.f_77272_ + 0.75, (double)$$15.f_77273_ + 0.5, -1, 0.02f, true, 0.0f, true);
                DebugRenderer.m_113490_(String.format(Locale.ROOT, "%.2f", Float.valueOf($$15.f_77281_)), (double)$$15.f_77271_ + 0.5, (double)$$15.f_77272_ + 0.25, (double)$$15.f_77273_ + 0.5, -1, 0.02f, true, 0.0f, true);
            }
        }
    }

    public static void m_113615_(Path p_113616_, double p_113617_, double p_113618_, double p_113619_) {
        Tesselator $$4 = Tesselator.m_85913_();
        BufferBuilder $$5 = $$4.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        $$5.m_166779_(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.f_85815_);
        for (int $$6 = 0; $$6 < p_113616_.m_77398_(); ++$$6) {
            Node $$7 = p_113616_.m_77375_($$6);
            if (PathfindingRenderer.m_113634_($$7.m_77288_(), p_113617_, p_113618_, p_113619_) > 80.0f) continue;
            float $$8 = (float)$$6 / (float)p_113616_.m_77398_() * 0.33f;
            int $$9 = $$6 == 0 ? 0 : Mth.m_14169_($$8, 0.9f, 0.9f);
            int $$10 = $$9 >> 16 & 0xFF;
            int $$11 = $$9 >> 8 & 0xFF;
            int $$12 = $$9 & 0xFF;
            $$5.m_5483_((double)$$7.f_77271_ - p_113617_ + 0.5, (double)$$7.f_77272_ - p_113618_ + 0.5, (double)$$7.f_77273_ - p_113619_ + 0.5).m_6122_($$10, $$11, $$12, 255).m_5752_();
        }
        $$4.m_85914_();
    }

    private static float m_113634_(BlockPos p_113635_, double p_113636_, double p_113637_, double p_113638_) {
        return (float)(Math.abs((double)p_113635_.m_123341_() - p_113636_) + Math.abs((double)p_113635_.m_123342_() - p_113637_) + Math.abs((double)p_113635_.m_123343_() - p_113638_));
    }
}

