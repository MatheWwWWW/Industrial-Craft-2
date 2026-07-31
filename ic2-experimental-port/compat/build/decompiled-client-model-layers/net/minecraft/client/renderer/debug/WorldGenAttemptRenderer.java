/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;

public class WorldGenAttemptRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final List<BlockPos> f_113724_ = Lists.newArrayList();
    private final List<Float> f_113725_ = Lists.newArrayList();
    private final List<Float> f_113726_ = Lists.newArrayList();
    private final List<Float> f_113727_ = Lists.newArrayList();
    private final List<Float> f_113728_ = Lists.newArrayList();
    private final List<Float> f_113729_ = Lists.newArrayList();

    public void m_113737_(BlockPos p_113738_, float p_113739_, float p_113740_, float p_113741_, float p_113742_, float p_113743_) {
        this.f_113724_.add(p_113738_);
        this.f_113725_.add(Float.valueOf(p_113739_));
        this.f_113726_.add(Float.valueOf(p_113743_));
        this.f_113727_.add(Float.valueOf(p_113740_));
        this.f_113728_.add(Float.valueOf(p_113741_));
        this.f_113729_.add(Float.valueOf(p_113742_));
    }

    @Override
    public void m_7790_(PoseStack p_113732_, MultiBufferSource p_113733_, double p_113734_, double p_113735_, double p_113736_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        Tesselator $$5 = Tesselator.m_85913_();
        BufferBuilder $$6 = $$5.m_85915_();
        $$6.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
        for (int $$7 = 0; $$7 < this.f_113724_.size(); ++$$7) {
            BlockPos $$8 = this.f_113724_.get($$7);
            Float $$9 = this.f_113725_.get($$7);
            float $$10 = $$9.floatValue() / 2.0f;
            LevelRenderer.m_109556_($$6, (double)((float)$$8.m_123341_() + 0.5f - $$10) - p_113734_, (double)((float)$$8.m_123342_() + 0.5f - $$10) - p_113735_, (double)((float)$$8.m_123343_() + 0.5f - $$10) - p_113736_, (double)((float)$$8.m_123341_() + 0.5f + $$10) - p_113734_, (double)((float)$$8.m_123342_() + 0.5f + $$10) - p_113735_, (double)((float)$$8.m_123343_() + 0.5f + $$10) - p_113736_, this.f_113727_.get($$7).floatValue(), this.f_113728_.get($$7).floatValue(), this.f_113729_.get($$7).floatValue(), this.f_113726_.get($$7).floatValue());
        }
        $$5.m_85914_();
        RenderSystem.m_69493_();
    }
}

