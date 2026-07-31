/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class ParrotOnShoulderLayer<T extends Player>
extends RenderLayer<T, PlayerModel<T>> {
    private final ParrotModel f_117290_;

    public ParrotOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> p_174511_, EntityModelSet p_174512_) {
        super(p_174511_);
        this.f_117290_ = new ParrotModel(p_174512_.m_171103_(ModelLayers.f_171203_));
    }

    @Override
    public void m_6494_(PoseStack p_117307_, MultiBufferSource p_117308_, int p_117309_, T p_117310_, float p_117311_, float p_117312_, float p_117313_, float p_117314_, float p_117315_, float p_117316_) {
        this.m_117317_(p_117307_, p_117308_, p_117309_, p_117310_, p_117311_, p_117312_, p_117315_, p_117316_, true);
        this.m_117317_(p_117307_, p_117308_, p_117309_, p_117310_, p_117311_, p_117312_, p_117315_, p_117316_, false);
    }

    private void m_117317_(PoseStack p_117318_, MultiBufferSource p_117319_, int p_117320_, T p_117321_, float p_117322_, float p_117323_, float p_117324_, float p_117325_, boolean p_117326_) {
        CompoundTag $$9 = p_117326_ ? ((Player)p_117321_).m_36331_() : ((Player)p_117321_).m_36332_();
        EntityType.m_20632_($$9.m_128461_("id")).filter(p_117294_ -> p_117294_ == EntityType.f_20508_).ifPresent(p_117338_ -> {
            p_117318_.m_85836_();
            p_117318_.m_85837_(p_117326_ ? (double)0.4f : (double)-0.4f, p_117321_.m_6047_() ? (double)-1.3f : -1.5, 0.0);
            VertexConsumer $$11 = p_117319_.m_6299_(this.f_117290_.m_103119_(ParrotRenderer.f_115648_[$$9.m_128451_("Variant")]));
            this.f_117290_.m_103223_(p_117318_, $$11, p_117320_, OverlayTexture.f_118083_, p_117322_, p_117323_, p_117324_, p_117325_, p_117330_.f_19797_);
            p_117318_.m_85849_();
        });
    }
}

