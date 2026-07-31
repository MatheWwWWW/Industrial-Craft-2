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
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMarker
extends TextureSheetParticle {
    BlockMarker(ClientLevel p_194267_, double p_194268_, double p_194269_, double p_194270_, BlockState p_194271_) {
        super(p_194267_, p_194268_, p_194269_, p_194270_);
        this.m_108337_(Minecraft.m_91087_().m_91289_().m_110907_().m_110882_(p_194271_));
        this.f_107226_ = 0.0f;
        this.f_107225_ = 80;
        this.f_107219_ = false;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107429_;
    }

    @Override
    public float m_5902_(float p_194274_) {
        return 0.5f;
    }

    public static class Provider
    implements ParticleProvider<BlockParticleOption> {
        @Override
        public Particle m_6966_(BlockParticleOption p_194277_, ClientLevel p_194278_, double p_194279_, double p_194280_, double p_194281_, double p_194282_, double p_194283_, double p_194284_) {
            return new BlockMarker(p_194278_, p_194279_, p_194280_, p_194281_, p_194277_.m_123642_());
        }
    }
}

