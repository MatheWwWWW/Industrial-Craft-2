/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemInHandLayer<T extends LivingEntity, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    private final ItemInHandRenderer f_234844_;

    public ItemInHandLayer(RenderLayerParent<T, M> p_234846_, ItemInHandRenderer p_234847_) {
        super(p_234846_);
        this.f_234844_ = p_234847_;
    }

    @Override
    public void m_6494_(PoseStack p_117204_, MultiBufferSource p_117205_, int p_117206_, T p_117207_, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
        ItemStack $$12;
        boolean $$10 = ((LivingEntity)p_117207_).m_5737_() == HumanoidArm.RIGHT;
        ItemStack $$11 = $$10 ? ((LivingEntity)p_117207_).m_21206_() : ((LivingEntity)p_117207_).m_21205_();
        ItemStack itemStack = $$12 = $$10 ? ((LivingEntity)p_117207_).m_21205_() : ((LivingEntity)p_117207_).m_21206_();
        if ($$11.m_41619_() && $$12.m_41619_()) {
            return;
        }
        p_117204_.m_85836_();
        if (((EntityModel)this.m_117386_()).f_102610_) {
            float $$13 = 0.5f;
            p_117204_.m_85837_(0.0, 0.75, 0.0);
            p_117204_.m_85841_(0.5f, 0.5f, 0.5f);
        }
        this.m_117184_((LivingEntity)p_117207_, $$12, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, p_117204_, p_117205_, p_117206_);
        this.m_117184_((LivingEntity)p_117207_, $$11, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, p_117204_, p_117205_, p_117206_);
        p_117204_.m_85849_();
    }

    protected void m_117184_(LivingEntity p_117185_, ItemStack p_117186_, ItemTransforms.TransformType p_117187_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_) {
        if (p_117186_.m_41619_()) {
            return;
        }
        p_117189_.m_85836_();
        ((ArmedModel)this.m_117386_()).m_6002_(p_117188_, p_117189_);
        p_117189_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0f));
        p_117189_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        boolean $$7 = p_117188_ == HumanoidArm.LEFT;
        p_117189_.m_85837_((float)($$7 ? -1 : 1) / 16.0f, 0.125, -0.625);
        this.f_234844_.m_109322_(p_117185_, p_117186_, p_117187_, $$7, p_117189_, p_117190_, p_117191_);
        p_117189_.m_85849_();
    }
}

