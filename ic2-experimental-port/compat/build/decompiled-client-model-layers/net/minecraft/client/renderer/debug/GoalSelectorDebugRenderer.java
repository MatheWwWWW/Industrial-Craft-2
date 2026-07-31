/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;

public class GoalSelectorDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private static final int f_173887_ = 160;
    private final Minecraft f_113543_;
    private final Map<Integer, List<DebugGoal>> f_113544_ = Maps.newHashMap();

    @Override
    public void m_5630_() {
        this.f_113544_.clear();
    }

    public void m_113548_(int p_113549_, List<DebugGoal> p_113550_) {
        this.f_113544_.put(p_113549_, p_113550_);
    }

    public void m_173888_(int p_173889_) {
        this.f_113544_.remove(p_173889_);
    }

    public GoalSelectorDebugRenderer(Minecraft p_113546_) {
        this.f_113543_ = p_113546_;
    }

    @Override
    public void m_7790_(PoseStack p_113552_, MultiBufferSource p_113553_, double p_113554_, double p_113555_, double p_113556_) {
        Camera $$5 = this.f_113543_.f_91063_.m_109153_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        BlockPos $$6 = new BlockPos($$5.m_90583_().f_82479_, 0.0, $$5.m_90583_().f_82481_);
        this.f_113544_.forEach((p_113559_, p_113560_) -> {
            for (int $$3 = 0; $$3 < p_113560_.size(); ++$$3) {
                DebugGoal $$4 = (DebugGoal)p_113560_.get($$3);
                if (!$$6.m_123314_($$4.f_113561_, 160.0)) continue;
                double $$5 = (double)$$4.f_113561_.m_123341_() + 0.5;
                double $$6 = (double)$$4.f_113561_.m_123342_() + 2.0 + (double)$$3 * 0.25;
                double $$7 = (double)$$4.f_113561_.m_123343_() + 0.5;
                int $$8 = $$4.f_113564_ ? -16711936 : -3355444;
                DebugRenderer.m_113477_($$4.f_113563_, $$5, $$6, $$7, $$8);
            }
        });
        RenderSystem.m_69482_();
        RenderSystem.m_69493_();
    }

    public static class DebugGoal {
        public final BlockPos f_113561_;
        public final int f_113562_;
        public final String f_113563_;
        public final boolean f_113564_;

        public DebugGoal(BlockPos p_113566_, int p_113567_, String p_113568_, boolean p_113569_) {
            this.f_113561_ = p_113566_;
            this.f_113562_ = p_113567_;
            this.f_113563_ = p_113568_;
            this.f_113564_ = p_113569_;
        }
    }
}

