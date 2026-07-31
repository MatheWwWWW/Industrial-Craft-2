/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Markings;

public class HorseMarkingLayer
extends RenderLayer<Horse, HorseModel<Horse>> {
    private static final Map<Markings, ResourceLocation> f_117042_ = Util.m_137469_(Maps.newEnumMap(Markings.class), p_117069_ -> {
        p_117069_.put(Markings.NONE, null);
        p_117069_.put(Markings.WHITE, new ResourceLocation("textures/entity/horse/horse_markings_white.png"));
        p_117069_.put(Markings.WHITE_FIELD, new ResourceLocation("textures/entity/horse/horse_markings_whitefield.png"));
        p_117069_.put(Markings.WHITE_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_whitedots.png"));
        p_117069_.put(Markings.BLACK_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_blackdots.png"));
    });

    public HorseMarkingLayer(RenderLayerParent<Horse, HorseModel<Horse>> p_117045_) {
        super(p_117045_);
    }

    @Override
    public void m_6494_(PoseStack p_117058_, MultiBufferSource p_117059_, int p_117060_, Horse p_117061_, float p_117062_, float p_117063_, float p_117064_, float p_117065_, float p_117066_, float p_117067_) {
        ResourceLocation $$10 = f_117042_.get((Object)p_117061_.m_30724_());
        if ($$10 == null || p_117061_.m_20145_()) {
            return;
        }
        VertexConsumer $$11 = p_117059_.m_6299_(RenderType.m_110473_($$10));
        ((HorseModel)this.m_117386_()).m_7695_(p_117058_, $$11, p_117060_, LivingEntityRenderer.m_115338_(p_117061_, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

