/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources;

import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingTextureManager
extends TextureAtlasHolder {
    private static final ResourceLocation f_118799_ = new ResourceLocation("back");

    public PaintingTextureManager(TextureManager p_118802_) {
        super(p_118802_, new ResourceLocation("textures/atlas/paintings.png"), "painting");
    }

    @Override
    protected Stream<ResourceLocation> m_7535_() {
        return Stream.concat(Registry.f_235728_.m_6566_().stream(), Stream.of(f_118799_));
    }

    public TextureAtlasSprite m_235033_(PaintingVariant p_235034_) {
        return this.m_118901_(Registry.f_235728_.m_7981_(p_235034_));
    }

    public TextureAtlasSprite m_118806_() {
        return this.m_118901_(f_118799_);
    }
}

