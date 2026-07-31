/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class SoulParticle
extends RisingParticle {
    private final SpriteSet f_107715_;
    protected boolean f_234078_;

    SoulParticle(ClientLevel p_107717_, double p_107718_, double p_107719_, double p_107720_, double p_107721_, double p_107722_, double p_107723_, SpriteSet p_107724_) {
        super(p_107717_, p_107718_, p_107719_, p_107720_, p_107721_, p_107722_, p_107723_);
        this.f_107715_ = p_107724_;
        this.m_6569_(1.5f);
        this.m_108339_(p_107724_);
    }

    @Override
    public int m_6355_(float p_234080_) {
        if (this.f_234078_) {
            return 240;
        }
        return super.m_6355_(p_234080_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_107715_);
    }

    public static class EmissiveProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_234081_;

        public EmissiveProvider(SpriteSet p_234083_) {
            this.f_234081_ = p_234083_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_234094_, ClientLevel p_234095_, double p_234096_, double p_234097_, double p_234098_, double p_234099_, double p_234100_, double p_234101_) {
            SoulParticle $$8 = new SoulParticle(p_234095_, p_234096_, p_234097_, p_234098_, p_234099_, p_234100_, p_234101_, this.f_234081_);
            $$8.m_107271_(1.0f);
            $$8.f_234078_ = true;
            return $$8;
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107737_;

        public Provider(SpriteSet p_107739_) {
            this.f_107737_ = p_107739_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107750_, ClientLevel p_107751_, double p_107752_, double p_107753_, double p_107754_, double p_107755_, double p_107756_, double p_107757_) {
            SoulParticle $$8 = new SoulParticle(p_107751_, p_107752_, p_107753_, p_107754_, p_107755_, p_107756_, p_107757_, this.f_107737_);
            $$8.m_107271_(1.0f);
            return $$8;
        }
    }
}

