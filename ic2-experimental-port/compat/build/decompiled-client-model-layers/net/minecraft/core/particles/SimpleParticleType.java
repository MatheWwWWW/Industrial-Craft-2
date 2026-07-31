/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.serialization.Codec
 */
package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class SimpleParticleType
extends ParticleType<SimpleParticleType>
implements ParticleOptions {
    private static final ParticleOptions.Deserializer<SimpleParticleType> f_123833_ = new ParticleOptions.Deserializer<SimpleParticleType>(){

        @Override
        public SimpleParticleType m_5739_(ParticleType<SimpleParticleType> p_123846_, StringReader p_123847_) {
            return (SimpleParticleType)p_123846_;
        }

        @Override
        public SimpleParticleType m_6507_(ParticleType<SimpleParticleType> p_123849_, FriendlyByteBuf p_123850_) {
            return (SimpleParticleType)p_123849_;
        }

        @Override
        public /* synthetic */ ParticleOptions m_6507_(ParticleType particleType, FriendlyByteBuf friendlyByteBuf) {
            return this.m_6507_(particleType, friendlyByteBuf);
        }

        @Override
        public /* synthetic */ ParticleOptions m_5739_(ParticleType particleType, StringReader stringReader) throws CommandSyntaxException {
            return this.m_5739_(particleType, stringReader);
        }
    };
    private final Codec<SimpleParticleType> f_123834_ = Codec.unit(this::m_6012_);

    protected SimpleParticleType(boolean p_123837_) {
        super(p_123837_, f_123833_);
    }

    public SimpleParticleType m_6012_() {
        return this;
    }

    @Override
    public Codec<SimpleParticleType> m_7652_() {
        return this.f_123834_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_123840_) {
    }

    @Override
    public String m_5942_() {
        return Registry.f_122829_.m_7981_(this).toString();
    }

    public /* synthetic */ ParticleType m_6012_() {
        return this.m_6012_();
    }
}

