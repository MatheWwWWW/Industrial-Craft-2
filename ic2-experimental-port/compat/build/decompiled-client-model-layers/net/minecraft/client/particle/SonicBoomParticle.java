/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class SonicBoomParticle
extends HugeExplosionParticle {
    protected SonicBoomParticle(ClientLevel p_234028_, double p_234029_, double p_234030_, double p_234031_, double p_234032_, SpriteSet p_234033_) {
        super(p_234028_, p_234029_, p_234030_, p_234031_, p_234032_, p_234033_);
        this.f_107225_ = 16;
        this.f_107663_ = 1.5f;
        this.m_108339_(p_234033_);
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_234034_;

        public Provider(SpriteSet p_234036_) {
            this.f_234034_ = p_234036_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_234047_, ClientLevel p_234048_, double p_234049_, double p_234050_, double p_234051_, double p_234052_, double p_234053_, double p_234054_) {
            return new SonicBoomParticle(p_234048_, p_234049_, p_234050_, p_234051_, p_234052_, this.f_234034_);
        }
    }
}

