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
import net.minecraft.core.particles.SculkChargeParticleOptions;

public class SculkChargeParticle
extends TextureSheetParticle {
    private final SpriteSet f_233890_;

    SculkChargeParticle(ClientLevel p_233892_, double p_233893_, double p_233894_, double p_233895_, double p_233896_, double p_233897_, double p_233898_, SpriteSet p_233899_) {
        super(p_233892_, p_233893_, p_233894_, p_233895_, p_233896_, p_233897_, p_233898_);
        this.f_172258_ = 0.96f;
        this.f_233890_ = p_233899_;
        this.m_6569_(1.5f);
        this.f_107219_ = false;
        this.m_108339_(p_233899_);
    }

    @Override
    public int m_6355_(float p_233902_) {
        return 240;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_233890_);
    }

    public record Provider(SpriteSet f_233904_) implements ParticleProvider<SculkChargeParticleOptions>
    {
        @Override
        public Particle m_6966_(SculkChargeParticleOptions p_233918_, ClientLevel p_233919_, double p_233920_, double p_233921_, double p_233922_, double p_233923_, double p_233924_, double p_233925_) {
            SculkChargeParticle $$8 = new SculkChargeParticle(p_233919_, p_233920_, p_233921_, p_233922_, p_233923_, p_233924_, p_233925_, this.f_233904_);
            $$8.m_107271_(1.0f);
            $$8.m_172260_(p_233923_, p_233924_, p_233925_);
            $$8.f_107204_ = p_233918_.f_235914_();
            $$8.f_107231_ = p_233918_.f_235914_();
            $$8.m_107257_(p_233919_.f_46441_.m_188503_(12) + 8);
            return $$8;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Provider.class, "sprite", "f_233904_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Provider.class, "sprite", "f_233904_"}, this);
        }

        @Override
        public final boolean equals(Object p_233927_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Provider.class, "sprite", "f_233904_"}, this, p_233927_);
        }
    }
}

