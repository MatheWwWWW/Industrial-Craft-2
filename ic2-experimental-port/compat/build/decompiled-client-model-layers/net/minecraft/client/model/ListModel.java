/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class ListModel<E extends Entity>
extends EntityModel<E> {
    public ListModel() {
        this(RenderType::m_110458_);
    }

    public ListModel(Function<ResourceLocation, RenderType> p_103011_) {
        super(p_103011_);
    }

    @Override
    public void m_7695_(PoseStack p_103013_, VertexConsumer p_103014_, int p_103015_, int p_103016_, float p_103017_, float p_103018_, float p_103019_, float p_103020_) {
        this.m_6195_().forEach(p_103030_ -> p_103030_.m_104306_(p_103013_, p_103014_, p_103015_, p_103016_, p_103017_, p_103018_, p_103019_, p_103020_));
    }

    public abstract Iterable<ModelPart> m_6195_();
}

