/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;

public abstract class ParticleType<T extends ParticleOptions> {
    private final boolean f_123737_;
    private final ParticleOptions.Deserializer<T> f_123738_;

    protected ParticleType(boolean p_123740_, ParticleOptions.Deserializer<T> p_123741_) {
        this.f_123737_ = p_123740_;
        this.f_123738_ = p_123741_;
    }

    public boolean m_123742_() {
        return this.f_123737_;
    }

    public ParticleOptions.Deserializer<T> m_123743_() {
        return this.f_123738_;
    }

    public abstract Codec<T> m_7652_();
}

