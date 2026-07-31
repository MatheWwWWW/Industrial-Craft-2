/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;

public class LightDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113583_;
    private static final int f_173892_ = 10;

    public LightDebugRenderer(Minecraft p_113585_) {
        this.f_113583_ = p_113585_;
    }

    @Override
    public void m_7790_(PoseStack p_113587_, MultiBufferSource p_113588_, double p_113589_, double p_113590_, double p_113591_) {
        ClientLevel $$5 = this.f_113583_.f_91073_;
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        BlockPos $$6 = new BlockPos(p_113589_, p_113590_, p_113591_);
        LongOpenHashSet $$7 = new LongOpenHashSet();
        for (BlockPos $$8 : BlockPos.m_121940_($$6.m_7918_(-10, -10, -10), $$6.m_7918_(10, 10, 10))) {
            int $$9 = $$5.m_45517_(LightLayer.SKY, $$8);
            float $$10 = (float)(15 - $$9) / 15.0f * 0.5f + 0.16f;
            int $$11 = Mth.m_14169_($$10, 0.9f, 0.9f);
            long $$12 = SectionPos.m_123235_($$8.m_121878_());
            if ($$7.add($$12)) {
                DebugRenderer.m_113483_($$5.m_7726_().m_7827_().m_75816_(LightLayer.SKY, SectionPos.m_123184_($$12)), SectionPos.m_175554_(SectionPos.m_123213_($$12), 8), SectionPos.m_175554_(SectionPos.m_123225_($$12), 8), SectionPos.m_175554_(SectionPos.m_123230_($$12), 8), 0xFF0000, 0.3f);
            }
            if ($$9 == 15) continue;
            DebugRenderer.m_113477_(String.valueOf($$9), (double)$$8.m_123341_() + 0.5, (double)$$8.m_123342_() + 0.25, (double)$$8.m_123343_() + 0.5, $$11);
        }
        RenderSystem.m_69493_();
    }
}

