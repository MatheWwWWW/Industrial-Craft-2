/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.Entity;

public abstract class ColorableHierarchicalModel<E extends Entity>
extends HierarchicalModel<E> {
    private float f_170497_ = 1.0f;
    private float f_170498_ = 1.0f;
    private float f_170499_ = 1.0f;

    public void m_170501_(float p_170502_, float p_170503_, float p_170504_) {
        this.f_170497_ = p_170502_;
        this.f_170498_ = p_170503_;
        this.f_170499_ = p_170504_;
    }

    @Override
    public void m_7695_(PoseStack p_170506_, VertexConsumer p_170507_, int p_170508_, int p_170509_, float p_170510_, float p_170511_, float p_170512_, float p_170513_) {
        super.m_7695_(p_170506_, p_170507_, p_170508_, p_170509_, this.f_170497_ * p_170510_, this.f_170498_ * p_170511_, this.f_170499_ * p_170512_, p_170513_);
    }
}

