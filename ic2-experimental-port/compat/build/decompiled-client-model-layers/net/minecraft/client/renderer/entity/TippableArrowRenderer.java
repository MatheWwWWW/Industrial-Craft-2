/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;

public class TippableArrowRenderer
extends ArrowRenderer<Arrow> {
    public static final ResourceLocation f_116132_ = new ResourceLocation("textures/entity/projectiles/arrow.png");
    public static final ResourceLocation f_116133_ = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

    public TippableArrowRenderer(EntityRendererProvider.Context p_174422_) {
        super(p_174422_);
    }

    @Override
    public ResourceLocation m_5478_(Arrow p_116140_) {
        return p_116140_.m_36889_() > 0 ? f_116133_ : f_116132_;
    }
}

