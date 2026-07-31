/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface RenderLayerParent<T extends Entity, M extends EntityModel<T>> {
    public M m_7200_();

    public ResourceLocation m_5478_(T var1);
}

