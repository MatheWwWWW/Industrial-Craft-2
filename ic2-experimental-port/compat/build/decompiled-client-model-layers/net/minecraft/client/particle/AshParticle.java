/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class AshParticle
extends BaseAshSmokeParticle {
    protected AshParticle(ClientLevel p_105514_, double p_105515_, double p_105516_, double p_105517_, double p_105518_, double p_105519_, double p_105520_, float p_105521_, SpriteSet p_105522_) {
        super(p_105514_, p_105515_, p_105516_, p_105517_, 0.1f, -0.1f, 0.1f, p_105518_, p_105519_, p_105520_, p_105521_, p_105522_, 0.5f, 20, 0.1f, false);
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_105523_;

        public Provider(SpriteSet p_105525_) {
            this.f_105523_ = p_105525_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_105536_, ClientLevel p_105537_, double p_105538_, double p_105539_, double p_105540_, double p_105541_, double p_105542_, double p_105543_) {
            return new AshParticle(p_105537_, p_105538_, p_105539_, p_105540_, 0.0, 0.0, 0.0, 1.0f, this.f_105523_);
        }
    }
}

