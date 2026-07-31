/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class IronGolemCrackinessLayer
extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
    private static final Map<IronGolem.Crackiness, ResourceLocation> f_117132_ = ImmutableMap.of((Object)((Object)IronGolem.Crackiness.LOW), (Object)new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), (Object)((Object)IronGolem.Crackiness.MEDIUM), (Object)new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), (Object)((Object)IronGolem.Crackiness.HIGH), (Object)new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

    public IronGolemCrackinessLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> p_117135_) {
        super(p_117135_);
    }

    @Override
    public void m_6494_(PoseStack p_117148_, MultiBufferSource p_117149_, int p_117150_, IronGolem p_117151_, float p_117152_, float p_117153_, float p_117154_, float p_117155_, float p_117156_, float p_117157_) {
        if (p_117151_.m_20145_()) {
            return;
        }
        IronGolem.Crackiness $$10 = p_117151_.m_28873_();
        if ($$10 == IronGolem.Crackiness.NONE) {
            return;
        }
        ResourceLocation $$11 = f_117132_.get((Object)$$10);
        IronGolemCrackinessLayer.m_117376_(this.m_117386_(), $$11, p_117148_, p_117149_, p_117150_, p_117151_, 1.0f, 1.0f, 1.0f);
    }
}

