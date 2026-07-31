/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.item.ItemStack;

public class PandaHoldsItemLayer
extends RenderLayer<Panda, PandaModel<Panda>> {
    private final ItemInHandRenderer f_234860_;

    public PandaHoldsItemLayer(RenderLayerParent<Panda, PandaModel<Panda>> p_234862_, ItemInHandRenderer p_234863_) {
        super(p_234862_);
        this.f_234860_ = p_234863_;
    }

    @Override
    public void m_6494_(PoseStack p_117280_, MultiBufferSource p_117281_, int p_117282_, Panda p_117283_, float p_117284_, float p_117285_, float p_117286_, float p_117287_, float p_117288_, float p_117289_) {
        ItemStack $$10 = p_117283_.m_6844_(EquipmentSlot.MAINHAND);
        if (!p_117283_.m_29150_() || p_117283_.m_29165_()) {
            return;
        }
        float $$11 = -0.6f;
        float $$12 = 1.4f;
        if (p_117283_.m_29152_()) {
            $$11 -= 0.2f * Mth.m_14031_(p_117287_ * 0.6f) + 0.2f;
            $$12 -= 0.09f * Mth.m_14031_(p_117287_ * 0.6f);
        }
        p_117280_.m_85836_();
        p_117280_.m_85837_(0.1f, $$12, $$11);
        this.f_234860_.m_109322_(p_117283_, $$10, ItemTransforms.TransformType.GROUND, false, p_117280_, p_117281_, p_117282_);
        p_117280_.m_85849_();
    }
}

