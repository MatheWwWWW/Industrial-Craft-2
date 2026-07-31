/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

public class WaterDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113715_;

    public WaterDebugRenderer(Minecraft p_113717_) {
        this.f_113715_ = p_113717_;
    }

    @Override
    public void m_7790_(PoseStack p_113719_, MultiBufferSource p_113720_, double p_113721_, double p_113722_, double p_113723_) {
        BlockPos $$5 = this.f_113715_.f_91074_.m_20183_();
        Level $$6 = this.f_113715_.f_91074_.f_19853_;
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_157429_(0.0f, 1.0f, 0.0f, 0.75f);
        RenderSystem.m_69472_();
        RenderSystem.m_69832_(6.0f);
        for (BlockPos $$7 : BlockPos.m_121940_($$5.m_7918_(-10, -10, -10), $$5.m_7918_(10, 10, 10))) {
            FluidState $$8 = $$6.m_6425_($$7);
            if (!$$8.m_205070_(FluidTags.f_13131_)) continue;
            double $$9 = (float)$$7.m_123342_() + $$8.m_76155_($$6, $$7);
            DebugRenderer.m_113451_(new AABB((float)$$7.m_123341_() + 0.01f, (float)$$7.m_123342_() + 0.01f, (float)$$7.m_123343_() + 0.01f, (float)$$7.m_123341_() + 0.99f, $$9, (float)$$7.m_123343_() + 0.99f).m_82386_(-p_113721_, -p_113722_, -p_113723_), 1.0f, 1.0f, 1.0f, 0.2f);
        }
        for (BlockPos $$10 : BlockPos.m_121940_($$5.m_7918_(-10, -10, -10), $$5.m_7918_(10, 10, 10))) {
            FluidState $$11 = $$6.m_6425_($$10);
            if (!$$11.m_205070_(FluidTags.f_13131_)) continue;
            DebugRenderer.m_113477_(String.valueOf($$11.m_76186_()), (double)$$10.m_123341_() + 0.5, (float)$$10.m_123342_() + $$11.m_76155_($$6, $$10), (double)$$10.m_123343_() + 0.5, -16777216);
        }
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }
}

