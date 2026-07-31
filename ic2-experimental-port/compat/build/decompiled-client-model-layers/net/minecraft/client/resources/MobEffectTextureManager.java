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
import net.minecraft.world.effect.MobEffect;

public class MobEffectTextureManager
extends TextureAtlasHolder {
    public MobEffectTextureManager(TextureManager p_118730_) {
        super(p_118730_, new ResourceLocation("textures/atlas/mob_effects.png"), "mob_effect");
    }

    @Override
    protected Stream<ResourceLocation> m_7535_() {
        return Registry.f_122823_.m_6566_().stream();
    }

    public TextureAtlasSprite m_118732_(MobEffect p_118733_) {
        return this.m_118901_(Registry.f_122823_.m_7981_(p_118733_));
    }
}

