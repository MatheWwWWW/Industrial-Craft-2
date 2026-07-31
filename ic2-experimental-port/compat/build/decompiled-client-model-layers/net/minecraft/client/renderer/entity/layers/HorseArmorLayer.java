/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class HorseArmorLayer
extends RenderLayer<Horse, HorseModel<Horse>> {
    private final HorseModel<Horse> f_117017_;

    public HorseArmorLayer(RenderLayerParent<Horse, HorseModel<Horse>> p_174496_, EntityModelSet p_174497_) {
        super(p_174496_);
        this.f_117017_ = new HorseModel(p_174497_.m_171103_(ModelLayers.f_171187_));
    }

    @Override
    public void m_6494_(PoseStack p_117032_, MultiBufferSource p_117033_, int p_117034_, Horse p_117035_, float p_117036_, float p_117037_, float p_117038_, float p_117039_, float p_117040_, float p_117041_) {
        float $$18;
        float $$17;
        float $$16;
        ItemStack $$10 = p_117035_.m_30722_();
        if (!($$10.m_41720_() instanceof HorseArmorItem)) {
            return;
        }
        HorseArmorItem $$11 = (HorseArmorItem)$$10.m_41720_();
        ((HorseModel)this.m_117386_()).m_102624_(this.f_117017_);
        this.f_117017_.m_6839_(p_117035_, p_117036_, p_117037_, p_117038_);
        this.f_117017_.m_6973_(p_117035_, p_117036_, p_117037_, p_117039_, p_117040_, p_117041_);
        if ($$11 instanceof DyeableHorseArmorItem) {
            int $$12 = ((DyeableHorseArmorItem)$$11).m_41121_($$10);
            float $$13 = (float)($$12 >> 16 & 0xFF) / 255.0f;
            float $$14 = (float)($$12 >> 8 & 0xFF) / 255.0f;
            float $$15 = (float)($$12 & 0xFF) / 255.0f;
        } else {
            $$16 = 1.0f;
            $$17 = 1.0f;
            $$18 = 1.0f;
        }
        VertexConsumer $$19 = p_117033_.m_6299_(RenderType.m_110458_($$11.m_41367_()));
        this.f_117017_.m_7695_(p_117032_, $$19, p_117034_, OverlayTexture.f_118083_, $$16, $$17, $$18, 1.0f);
    }
}

