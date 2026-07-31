/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.world.entity.Entity;

public abstract class ColorableAgeableListModel<E extends Entity>
extends AgeableListModel<E> {
    private float f_102415_ = 1.0f;
    private float f_102416_ = 1.0f;
    private float f_102417_ = 1.0f;

    public void m_102419_(float p_102420_, float p_102421_, float p_102422_) {
        this.f_102415_ = p_102420_;
        this.f_102416_ = p_102421_;
        this.f_102417_ = p_102422_;
    }

    @Override
    public void m_7695_(PoseStack p_102424_, VertexConsumer p_102425_, int p_102426_, int p_102427_, float p_102428_, float p_102429_, float p_102430_, float p_102431_) {
        super.m_7695_(p_102424_, p_102425_, p_102426_, p_102427_, this.f_102415_ * p_102428_, this.f_102416_ * p_102429_, this.f_102417_ * p_102430_, p_102431_);
    }
}

