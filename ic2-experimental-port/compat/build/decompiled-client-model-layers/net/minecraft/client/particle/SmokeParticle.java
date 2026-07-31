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

public class SmokeParticle
extends BaseAshSmokeParticle {
    protected SmokeParticle(ClientLevel p_107685_, double p_107686_, double p_107687_, double p_107688_, double p_107689_, double p_107690_, double p_107691_, float p_107692_, SpriteSet p_107693_) {
        super(p_107685_, p_107686_, p_107687_, p_107688_, 0.1f, 0.1f, 0.1f, p_107689_, p_107690_, p_107691_, p_107692_, p_107693_, 0.3f, 8, -0.1f, true);
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107694_;

        public Provider(SpriteSet p_107696_) {
            this.f_107694_ = p_107696_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107707_, ClientLevel p_107708_, double p_107709_, double p_107710_, double p_107711_, double p_107712_, double p_107713_, double p_107714_) {
            return new SmokeParticle(p_107708_, p_107709_, p_107710_, p_107711_, p_107712_, p_107713_, p_107714_, 1.0f, this.f_107694_);
        }
    }
}

