/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;

public class RaidDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private static final int f_173901_ = 160;
    private static final float f_173902_ = 0.04f;
    private final Minecraft f_113647_;
    private Collection<BlockPos> f_113648_ = Lists.newArrayList();

    public RaidDebugRenderer(Minecraft p_113650_) {
        this.f_113647_ = p_113650_;
    }

    public void m_113663_(Collection<BlockPos> p_113664_) {
        this.f_113648_ = p_113664_;
    }

    @Override
    public void m_7790_(PoseStack p_113652_, MultiBufferSource p_113653_, double p_113654_, double p_113655_, double p_113656_) {
        BlockPos $$5 = this.m_113665_().m_90588_();
        for (BlockPos $$6 : this.f_113648_) {
            if (!$$5.m_123314_($$6, 160.0)) continue;
            RaidDebugRenderer.m_113657_($$6);
        }
    }

    private static void m_113657_(BlockPos p_113658_) {
        DebugRenderer.m_113470_(p_113658_.m_7637_(-0.5, -0.5, -0.5), p_113658_.m_7637_(1.5, 1.5, 1.5), 1.0f, 0.0f, 0.0f, 0.15f);
        int $$1 = -65536;
        RaidDebugRenderer.m_113659_("Raid center", p_113658_, -65536);
    }

    private static void m_113659_(String p_113660_, BlockPos p_113661_, int p_113662_) {
        double $$3 = (double)p_113661_.m_123341_() + 0.5;
        double $$4 = (double)p_113661_.m_123342_() + 1.3;
        double $$5 = (double)p_113661_.m_123343_() + 0.5;
        DebugRenderer.m_113490_(p_113660_, $$3, $$4, $$5, p_113662_, 0.04f, true, 0.0f, true);
    }

    private Camera m_113665_() {
        return this.f_113647_.f_91063_.m_109153_();
    }
}

