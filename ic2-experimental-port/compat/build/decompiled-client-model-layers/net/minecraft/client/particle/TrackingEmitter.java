/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class TrackingEmitter
extends NoRenderParticle {
    private final Entity f_108387_;
    private int f_108388_;
    private final int f_108385_;
    private final ParticleOptions f_108386_;

    public TrackingEmitter(ClientLevel p_108390_, Entity p_108391_, ParticleOptions p_108392_) {
        this(p_108390_, p_108391_, p_108392_, 3);
    }

    public TrackingEmitter(ClientLevel p_108394_, Entity p_108395_, ParticleOptions p_108396_, int p_108397_) {
        this(p_108394_, p_108395_, p_108396_, p_108397_, p_108395_.m_20184_());
    }

    private TrackingEmitter(ClientLevel p_108399_, Entity p_108400_, ParticleOptions p_108401_, int p_108402_, Vec3 p_108403_) {
        super(p_108399_, p_108400_.m_20185_(), p_108400_.m_20227_(0.5), p_108400_.m_20189_(), p_108403_.f_82479_, p_108403_.f_82480_, p_108403_.f_82481_);
        this.f_108387_ = p_108400_;
        this.f_108385_ = p_108402_;
        this.f_108386_ = p_108401_;
        this.m_5989_();
    }

    @Override
    public void m_5989_() {
        for (int $$0 = 0; $$0 < 16; ++$$0) {
            double $$3;
            double $$2;
            double $$1 = this.f_107223_.m_188501_() * 2.0f - 1.0f;
            if ($$1 * $$1 + ($$2 = (double)(this.f_107223_.m_188501_() * 2.0f - 1.0f)) * $$2 + ($$3 = (double)(this.f_107223_.m_188501_() * 2.0f - 1.0f)) * $$3 > 1.0) continue;
            double $$4 = this.f_108387_.m_20165_($$1 / 4.0);
            double $$5 = this.f_108387_.m_20227_(0.5 + $$2 / 4.0);
            double $$6 = this.f_108387_.m_20246_($$3 / 4.0);
            this.f_107208_.m_6493_(this.f_108386_, false, $$4, $$5, $$6, $$1, $$2 + 0.2, $$3);
        }
        ++this.f_108388_;
        if (this.f_108388_ >= this.f_108385_) {
            this.m_107274_();
        }
    }
}

