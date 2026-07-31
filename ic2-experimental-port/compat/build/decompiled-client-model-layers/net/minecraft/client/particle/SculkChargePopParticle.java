/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class SculkChargePopParticle
extends TextureSheetParticle {
    private final SpriteSet f_233930_;

    SculkChargePopParticle(ClientLevel p_233932_, double p_233933_, double p_233934_, double p_233935_, double p_233936_, double p_233937_, double p_233938_, SpriteSet p_233939_) {
        super(p_233932_, p_233933_, p_233934_, p_233935_, p_233936_, p_233937_, p_233938_);
        this.f_172258_ = 0.96f;
        this.f_233930_ = p_233939_;
        this.m_6569_(1.0f);
        this.f_107219_ = false;
        this.m_108339_(p_233939_);
    }

    @Override
    public int m_6355_(float p_233942_) {
        return 240;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_233930_);
    }

    public record Provider(SpriteSet f_233944_) implements ParticleProvider<SimpleParticleType>
    {
        @Override
        public Particle m_6966_(SimpleParticleType p_233958_, ClientLevel p_233959_, double p_233960_, double p_233961_, double p_233962_, double p_233963_, double p_233964_, double p_233965_) {
            SculkChargePopParticle $$8 = new SculkChargePopParticle(p_233959_, p_233960_, p_233961_, p_233962_, p_233963_, p_233964_, p_233965_, this.f_233944_);
            $$8.m_107271_(1.0f);
            $$8.m_172260_(p_233963_, p_233964_, p_233965_);
            $$8.m_107257_(p_233959_.f_46441_.m_188503_(4) + 6);
            return $$8;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Provider.class, "sprite", "f_233944_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Provider.class, "sprite", "f_233944_"}, this);
        }

        @Override
        public final boolean equals(Object p_233967_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Provider.class, "sprite", "f_233944_"}, this, p_233967_);
        }
    }
}

