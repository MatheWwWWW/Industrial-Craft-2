/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class SpitParticle
extends ExplodeParticle {
    SpitParticle(ClientLevel p_107888_, double p_107889_, double p_107890_, double p_107891_, double p_107892_, double p_107893_, double p_107894_, SpriteSet p_107895_) {
        super(p_107888_, p_107889_, p_107890_, p_107891_, p_107892_, p_107893_, p_107894_, p_107895_);
        this.f_107226_ = 0.5f;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107907_;

        public Provider(SpriteSet p_107909_) {
            this.f_107907_ = p_107909_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107920_, ClientLevel p_107921_, double p_107922_, double p_107923_, double p_107924_, double p_107925_, double p_107926_, double p_107927_) {
            return new SpitParticle(p_107921_, p_107922_, p_107923_, p_107924_, p_107925_, p_107926_, p_107927_, this.f_107907_);
        }
    }
}

