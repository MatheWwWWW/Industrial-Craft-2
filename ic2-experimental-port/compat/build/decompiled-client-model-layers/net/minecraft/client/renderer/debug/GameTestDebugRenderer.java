/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;

public class GameTestDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private static final float f_173886_ = 0.02f;
    private final Map<BlockPos, Marker> f_113512_ = Maps.newHashMap();

    public void m_113524_(BlockPos p_113525_, int p_113526_, String p_113527_, int p_113528_) {
        this.f_113512_.put(p_113525_, new Marker(p_113526_, p_113527_, Util.m_137550_() + (long)p_113528_));
    }

    @Override
    public void m_5630_() {
        this.f_113512_.clear();
    }

    @Override
    public void m_7790_(PoseStack p_113519_, MultiBufferSource p_113520_, double p_113521_, double p_113522_, double p_113523_) {
        long $$5 = Util.m_137550_();
        this.f_113512_.entrySet().removeIf(p_113517_ -> $$5 > ((Marker)p_113517_.getValue()).f_113534_);
        this.f_113512_.forEach(this::m_113529_);
    }

    private void m_113529_(BlockPos p_113530_, Marker p_113531_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.m_157429_(0.0f, 1.0f, 0.0f, 0.75f);
        RenderSystem.m_69472_();
        DebugRenderer.m_113463_(p_113530_, 0.02f, p_113531_.m_113539_(), p_113531_.m_113540_(), p_113531_.m_113541_(), p_113531_.m_113542_());
        if (!p_113531_.f_113533_.isEmpty()) {
            double $$2 = (double)p_113530_.m_123341_() + 0.5;
            double $$3 = (double)p_113530_.m_123342_() + 1.2;
            double $$4 = (double)p_113530_.m_123343_() + 0.5;
            DebugRenderer.m_113490_(p_113531_.f_113533_, $$2, $$3, $$4, -1, 0.01f, true, 0.0f, true);
        }
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }

    static class Marker {
        public int f_113532_;
        public String f_113533_;
        public long f_113534_;

        public Marker(int p_113536_, String p_113537_, long p_113538_) {
            this.f_113532_ = p_113536_;
            this.f_113533_ = p_113537_;
            this.f_113534_ = p_113538_;
        }

        public float m_113539_() {
            return (float)(this.f_113532_ >> 16 & 0xFF) / 255.0f;
        }

        public float m_113540_() {
            return (float)(this.f_113532_ >> 8 & 0xFF) / 255.0f;
        }

        public float m_113541_() {
            return (float)(this.f_113532_ & 0xFF) / 255.0f;
        }

        public float m_113542_() {
            return (float)(this.f_113532_ >> 24 & 0xFF) / 255.0f;
        }
    }
}

