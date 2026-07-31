/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TerrainParticle
extends TextureSheetParticle {
    private final BlockPos f_108280_;
    private final float f_108277_;
    private final float f_108278_;

    public TerrainParticle(ClientLevel p_108282_, double p_108283_, double p_108284_, double p_108285_, double p_108286_, double p_108287_, double p_108288_, BlockState p_108289_) {
        this(p_108282_, p_108283_, p_108284_, p_108285_, p_108286_, p_108287_, p_108288_, p_108289_, new BlockPos(p_108283_, p_108284_, p_108285_));
    }

    public TerrainParticle(ClientLevel p_172451_, double p_172452_, double p_172453_, double p_172454_, double p_172455_, double p_172456_, double p_172457_, BlockState p_172458_, BlockPos p_172459_) {
        super(p_172451_, p_172452_, p_172453_, p_172454_, p_172455_, p_172456_, p_172457_);
        this.f_108280_ = p_172459_;
        this.m_108337_(Minecraft.m_91087_().m_91289_().m_110907_().m_110882_(p_172458_));
        this.f_107226_ = 1.0f;
        this.f_107227_ = 0.6f;
        this.f_107228_ = 0.6f;
        this.f_107229_ = 0.6f;
        if (!p_172458_.m_60713_(Blocks.f_50440_)) {
            int $$9 = Minecraft.m_91087_().m_91298_().m_92577_(p_172458_, p_172451_, p_172459_, 0);
            this.f_107227_ *= (float)($$9 >> 16 & 0xFF) / 255.0f;
            this.f_107228_ *= (float)($$9 >> 8 & 0xFF) / 255.0f;
            this.f_107229_ *= (float)($$9 & 0xFF) / 255.0f;
        }
        this.f_107663_ /= 2.0f;
        this.f_108277_ = this.f_107223_.m_188501_() * 3.0f;
        this.f_108278_ = this.f_107223_.m_188501_() * 3.0f;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107429_;
    }

    @Override
    protected float m_5970_() {
        return this.f_108321_.m_118367_((this.f_108277_ + 1.0f) / 4.0f * 16.0f);
    }

    @Override
    protected float m_5952_() {
        return this.f_108321_.m_118367_(this.f_108277_ / 4.0f * 16.0f);
    }

    @Override
    protected float m_5951_() {
        return this.f_108321_.m_118393_(this.f_108278_ / 4.0f * 16.0f);
    }

    @Override
    protected float m_5950_() {
        return this.f_108321_.m_118393_((this.f_108278_ + 1.0f) / 4.0f * 16.0f);
    }

    @Override
    public int m_6355_(float p_108291_) {
        int $$1 = super.m_6355_(p_108291_);
        if ($$1 == 0 && this.f_107208_.m_46805_(this.f_108280_)) {
            return LevelRenderer.m_109541_(this.f_107208_, this.f_108280_);
        }
        return $$1;
    }

    public static class Provider
    implements ParticleProvider<BlockParticleOption> {
        @Override
        public Particle m_6966_(BlockParticleOption p_108304_, ClientLevel p_108305_, double p_108306_, double p_108307_, double p_108308_, double p_108309_, double p_108310_, double p_108311_) {
            BlockState $$8 = p_108304_.m_123642_();
            if ($$8.m_60795_() || $$8.m_60713_(Blocks.f_50110_)) {
                return null;
            }
            return new TerrainParticle(p_108305_, p_108306_, p_108307_, p_108308_, p_108309_, p_108310_, p_108311_, $$8);
        }
    }
}

