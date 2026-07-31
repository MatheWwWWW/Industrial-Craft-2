/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CollisionBoxRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft f_113400_;
    private double f_113401_ = Double.MIN_VALUE;
    private List<VoxelShape> f_113402_ = Collections.emptyList();

    public CollisionBoxRenderer(Minecraft p_113404_) {
        this.f_113400_ = p_113404_;
    }

    @Override
    public void m_7790_(PoseStack p_113408_, MultiBufferSource p_113409_, double p_113410_, double p_113411_, double p_113412_) {
        double $$5 = Util.m_137569_();
        if ($$5 - this.f_113401_ > 1.0E8) {
            this.f_113401_ = $$5;
            Entity $$6 = this.f_113400_.f_91063_.m_109153_().m_90592_();
            this.f_113402_ = ImmutableList.copyOf($$6.f_19853_.m_186431_($$6, $$6.m_20191_().m_82400_(6.0)));
        }
        VertexConsumer $$7 = p_113409_.m_6299_(RenderType.m_110504_());
        for (VoxelShape $$8 : this.f_113402_) {
            LevelRenderer.m_109654_(p_113408_, $$7, $$8, -p_113410_, -p_113411_, -p_113412_, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

