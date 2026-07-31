/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;

public class FoxHeldItemLayer
extends RenderLayer<Fox, FoxModel<Fox>> {
    private final ItemInHandRenderer f_234836_;

    public FoxHeldItemLayer(RenderLayerParent<Fox, FoxModel<Fox>> p_234838_, ItemInHandRenderer p_234839_) {
        super(p_234838_);
        this.f_234836_ = p_234839_;
    }

    @Override
    public void m_6494_(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, Fox p_117010_, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
        boolean $$10 = p_117010_.m_5803_();
        boolean $$11 = p_117010_.m_6162_();
        p_117007_.m_85836_();
        if ($$11) {
            float $$12 = 0.75f;
            p_117007_.m_85841_(0.75f, 0.75f, 0.75f);
            p_117007_.m_85837_(0.0, 0.5, 0.209375f);
        }
        p_117007_.m_85837_(((FoxModel)this.m_117386_()).f_102638_.f_104200_ / 16.0f, ((FoxModel)this.m_117386_()).f_102638_.f_104201_ / 16.0f, ((FoxModel)this.m_117386_()).f_102638_.f_104202_ / 16.0f);
        float $$13 = p_117010_.m_28620_(p_117013_);
        p_117007_.m_85845_(Vector3f.f_122227_.m_122270_($$13));
        p_117007_.m_85845_(Vector3f.f_122225_.m_122240_(p_117015_));
        p_117007_.m_85845_(Vector3f.f_122223_.m_122240_(p_117016_));
        if (p_117010_.m_6162_()) {
            if ($$10) {
                p_117007_.m_85837_(0.4f, 0.26f, 0.15f);
            } else {
                p_117007_.m_85837_(0.06f, 0.26f, -0.5);
            }
        } else if ($$10) {
            p_117007_.m_85837_(0.46f, 0.26f, 0.22f);
        } else {
            p_117007_.m_85837_(0.06f, 0.27f, -0.5);
        }
        p_117007_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
        if ($$10) {
            p_117007_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
        }
        ItemStack $$14 = p_117010_.m_6844_(EquipmentSlot.MAINHAND);
        this.f_234836_.m_109322_(p_117010_, $$14, ItemTransforms.TransformType.GROUND, false, p_117007_, p_117008_, p_117009_);
        p_117007_.m_85849_();
    }
}

