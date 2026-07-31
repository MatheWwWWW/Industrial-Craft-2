/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public class VillageSectionsDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private static final int f_173904_ = 60;
    private final Set<SectionPos> f_113693_ = Sets.newHashSet();

    VillageSectionsDebugRenderer() {
    }

    @Override
    public void m_5630_() {
        this.f_113693_.clear();
    }

    public void m_113709_(SectionPos p_113710_) {
        this.f_113693_.add(p_113710_);
    }

    public void m_113711_(SectionPos p_113712_) {
        this.f_113693_.remove(p_113712_);
    }

    @Override
    public void m_7790_(PoseStack p_113701_, MultiBufferSource p_113702_, double p_113703_, double p_113704_, double p_113705_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        this.m_113696_(p_113703_, p_113704_, p_113705_);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }

    private void m_113696_(double p_113697_, double p_113698_, double p_113699_) {
        BlockPos $$3 = new BlockPos(p_113697_, p_113698_, p_113699_);
        this.f_113693_.forEach(p_113708_ -> {
            if ($$3.m_123314_(p_113708_.m_123250_(), 60.0)) {
                VillageSectionsDebugRenderer.m_113713_(p_113708_);
            }
        });
    }

    private static void m_113713_(SectionPos p_113714_) {
        float $$1 = 1.0f;
        BlockPos $$2 = p_113714_.m_123250_();
        BlockPos $$3 = $$2.m_7637_(-1.0, -1.0, -1.0);
        BlockPos $$4 = $$2.m_7637_(1.0, 1.0, 1.0);
        DebugRenderer.m_113470_($$3, $$4, 0.2f, 1.0f, 0.2f, 0.15f);
    }
}

