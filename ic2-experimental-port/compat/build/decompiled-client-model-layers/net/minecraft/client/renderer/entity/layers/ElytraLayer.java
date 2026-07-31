/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    private static final ResourceLocation f_116934_ = new ResourceLocation("textures/entity/elytra.png");
    private final ElytraModel<T> f_116935_;

    public ElytraLayer(RenderLayerParent<T, M> p_174493_, EntityModelSet p_174494_) {
        super(p_174493_);
        this.f_116935_ = new ElytraModel(p_174494_.m_171103_(ModelLayers.f_171141_));
    }

    @Override
    public void m_6494_(PoseStack p_116951_, MultiBufferSource p_116952_, int p_116953_, T p_116954_, float p_116955_, float p_116956_, float p_116957_, float p_116958_, float p_116959_, float p_116960_) {
        ResourceLocation $$15;
        ItemStack $$10 = ((LivingEntity)p_116954_).m_6844_(EquipmentSlot.CHEST);
        if (!$$10.m_150930_(Items.f_42741_)) {
            return;
        }
        if (p_116954_ instanceof AbstractClientPlayer) {
            AbstractClientPlayer $$11 = (AbstractClientPlayer)p_116954_;
            if ($$11.m_108562_() && $$11.m_108563_() != null) {
                ResourceLocation $$12 = $$11.m_108563_();
            } else if ($$11.m_108555_() && $$11.m_108561_() != null && $$11.m_36170_(PlayerModelPart.CAPE)) {
                ResourceLocation $$13 = $$11.m_108561_();
            } else {
                ResourceLocation $$14 = f_116934_;
            }
        } else {
            $$15 = f_116934_;
        }
        p_116951_.m_85836_();
        p_116951_.m_85837_(0.0, 0.0, 0.125);
        ((EntityModel)this.m_117386_()).m_102624_(this.f_116935_);
        this.f_116935_.m_6973_(p_116954_, p_116955_, p_116956_, p_116958_, p_116959_, p_116960_);
        VertexConsumer $$16 = ItemRenderer.m_115184_(p_116952_, RenderType.m_110431_($$15), false, $$10.m_41790_());
        this.f_116935_.m_7695_(p_116951_, $$16, p_116953_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_116951_.m_85849_();
    }
}

