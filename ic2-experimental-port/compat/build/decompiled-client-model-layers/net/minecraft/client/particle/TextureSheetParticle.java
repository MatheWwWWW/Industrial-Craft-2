/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public abstract class TextureSheetParticle
extends SingleQuadParticle {
    protected TextureAtlasSprite f_108321_;

    protected TextureSheetParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
    }

    protected TextureSheetParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
    }

    protected void m_108337_(TextureAtlasSprite p_108338_) {
        this.f_108321_ = p_108338_;
    }

    @Override
    protected float m_5970_() {
        return this.f_108321_.m_118409_();
    }

    @Override
    protected float m_5952_() {
        return this.f_108321_.m_118410_();
    }

    @Override
    protected float m_5951_() {
        return this.f_108321_.m_118411_();
    }

    @Override
    protected float m_5950_() {
        return this.f_108321_.m_118412_();
    }

    public void m_108335_(SpriteSet p_108336_) {
        this.m_108337_(p_108336_.m_213979_(this.f_107223_));
    }

    public void m_108339_(SpriteSet p_108340_) {
        if (!this.f_107220_) {
            this.m_108337_(p_108340_.m_5819_(this.f_107224_, this.f_107225_));
        }
    }
}

