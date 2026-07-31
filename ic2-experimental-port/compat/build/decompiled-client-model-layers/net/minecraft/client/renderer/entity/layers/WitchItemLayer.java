/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WitchItemLayer<T extends LivingEntity>
extends CrossedArmsItemLayer<T, WitchModel<T>> {
    public WitchItemLayer(RenderLayerParent<T, WitchModel<T>> p_234926_, ItemInHandRenderer p_234927_) {
        super(p_234926_, p_234927_);
    }

    @Override
    public void m_6494_(PoseStack p_117685_, MultiBufferSource p_117686_, int p_117687_, T p_117688_, float p_117689_, float p_117690_, float p_117691_, float p_117692_, float p_117693_, float p_117694_) {
        ItemStack $$10 = ((LivingEntity)p_117688_).m_21205_();
        p_117685_.m_85836_();
        if ($$10.m_150930_(Items.f_42589_)) {
            ((WitchModel)this.m_117386_()).m_5585_().m_104299_(p_117685_);
            ((WitchModel)this.m_117386_()).m_104073_().m_104299_(p_117685_);
            p_117685_.m_85837_(0.0625, 0.25, 0.0);
            p_117685_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
            p_117685_.m_85845_(Vector3f.f_122223_.m_122240_(140.0f));
            p_117685_.m_85845_(Vector3f.f_122227_.m_122240_(10.0f));
            p_117685_.m_85837_(0.0, -0.4f, 0.4f);
        }
        super.m_6494_(p_117685_, p_117686_, p_117687_, p_117688_, p_117689_, p_117690_, p_117691_, p_117692_, p_117693_, p_117694_);
        p_117685_.m_85849_();
    }
}

