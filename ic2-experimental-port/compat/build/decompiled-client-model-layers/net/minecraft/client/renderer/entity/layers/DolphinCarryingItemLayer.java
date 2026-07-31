/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.item.ItemStack;

public class DolphinCarryingItemLayer
extends RenderLayer<Dolphin, DolphinModel<Dolphin>> {
    private final ItemInHandRenderer f_234832_;

    public DolphinCarryingItemLayer(RenderLayerParent<Dolphin, DolphinModel<Dolphin>> p_234834_, ItemInHandRenderer p_234835_) {
        super(p_234834_);
        this.f_234832_ = p_234835_;
    }

    @Override
    public void m_6494_(PoseStack p_116897_, MultiBufferSource p_116898_, int p_116899_, Dolphin p_116900_, float p_116901_, float p_116902_, float p_116903_, float p_116904_, float p_116905_, float p_116906_) {
        boolean $$10 = p_116900_.m_5737_() == HumanoidArm.RIGHT;
        p_116897_.m_85836_();
        float $$11 = 1.0f;
        float $$12 = -1.0f;
        float $$13 = Mth.m_14154_(p_116900_.m_146909_()) / 60.0f;
        if (p_116900_.m_146909_() < 0.0f) {
            p_116897_.m_85837_(0.0, 1.0f - $$13 * 0.5f, -1.0f + $$13 * 0.5f);
        } else {
            p_116897_.m_85837_(0.0, 1.0f + $$13 * 0.8f, -1.0f + $$13 * 0.2f);
        }
        ItemStack $$14 = $$10 ? p_116900_.m_21205_() : p_116900_.m_21206_();
        this.f_234832_.m_109322_(p_116900_, $$14, ItemTransforms.TransformType.GROUND, false, p_116897_, p_116898_, p_116899_);
        p_116897_.m_85849_();
    }
}

