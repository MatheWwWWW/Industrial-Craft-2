/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NoopRenderer<T extends Entity>
extends EntityRenderer<T> {
    public NoopRenderer(EntityRendererProvider.Context p_174326_) {
        super(p_174326_);
    }

    @Override
    public ResourceLocation m_5478_(T p_174328_) {
        return TextureAtlas.f_118259_;
    }
}

