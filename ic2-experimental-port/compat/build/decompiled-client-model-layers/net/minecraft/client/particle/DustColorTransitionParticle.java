/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustParticleBase;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.DustColorTransitionOptions;

public class DustColorTransitionParticle
extends DustParticleBase<DustColorTransitionOptions> {
    private final Vector3f f_172050_;
    private final Vector3f f_172051_;

    protected DustColorTransitionParticle(ClientLevel p_172053_, double p_172054_, double p_172055_, double p_172056_, double p_172057_, double p_172058_, double p_172059_, DustColorTransitionOptions p_172060_, SpriteSet p_172061_) {
        super(p_172053_, p_172054_, p_172055_, p_172056_, p_172057_, p_172058_, p_172059_, p_172060_, p_172061_);
        float $$9 = this.f_107223_.m_188501_() * 0.4f + 0.6f;
        this.f_172050_ = this.m_172066_(p_172060_.m_175771_(), $$9);
        this.f_172051_ = this.m_172066_(p_172060_.m_175774_(), $$9);
    }

    private Vector3f m_172066_(Vector3f p_172067_, float p_172068_) {
        return new Vector3f(this.m_172104_(p_172067_.m_122239_(), p_172068_), this.m_172104_(p_172067_.m_122260_(), p_172068_), this.m_172104_(p_172067_.m_122269_(), p_172068_));
    }

    private void m_172069_(float p_172070_) {
        float $$1 = ((float)this.f_107224_ + p_172070_) / ((float)this.f_107225_ + 1.0f);
        Vector3f $$2 = this.f_172050_.m_122281_();
        $$2.m_122255_(this.f_172051_, $$1);
        this.f_107227_ = $$2.m_122239_();
        this.f_107228_ = $$2.m_122260_();
        this.f_107229_ = $$2.m_122269_();
    }

    @Override
    public void m_5744_(VertexConsumer p_172063_, Camera p_172064_, float p_172065_) {
        this.m_172069_(p_172065_);
        super.m_5744_(p_172063_, p_172064_, p_172065_);
    }

    public static class Provider
    implements ParticleProvider<DustColorTransitionOptions> {
        private final SpriteSet f_172071_;

        public Provider(SpriteSet p_172073_) {
            this.f_172071_ = p_172073_;
        }

        @Override
        public Particle m_6966_(DustColorTransitionOptions p_172075_, ClientLevel p_172076_, double p_172077_, double p_172078_, double p_172079_, double p_172080_, double p_172081_, double p_172082_) {
            return new DustColorTransitionParticle(p_172076_, p_172077_, p_172078_, p_172079_, p_172080_, p_172081_, p_172082_, p_172075_, this.f_172071_);
        }
    }
}

