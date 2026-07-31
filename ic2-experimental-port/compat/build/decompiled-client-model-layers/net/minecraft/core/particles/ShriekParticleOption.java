/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;

public class ShriekParticleOption
implements ParticleOptions {
    public static final Codec<ShriekParticleOption> f_235944_ = RecordCodecBuilder.create(p_235952_ -> p_235952_.group((App)Codec.INT.fieldOf("delay").forGetter(p_235954_ -> p_235954_.f_235946_)).apply((Applicative)p_235952_, ShriekParticleOption::new));
    public static final ParticleOptions.Deserializer<ShriekParticleOption> f_235945_ = new ParticleOptions.Deserializer<ShriekParticleOption>(){

        @Override
        public ShriekParticleOption m_5739_(ParticleType<ShriekParticleOption> p_235961_, StringReader p_235962_) throws CommandSyntaxException {
            p_235962_.expect(' ');
            int $$2 = p_235962_.readInt();
            return new ShriekParticleOption($$2);
        }

        @Override
        public ShriekParticleOption m_6507_(ParticleType<ShriekParticleOption> p_235964_, FriendlyByteBuf p_235965_) {
            return new ShriekParticleOption(p_235965_.m_130242_());
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
    private final int f_235946_;

    public ShriekParticleOption(int p_235949_) {
        this.f_235946_ = p_235949_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_235956_) {
        p_235956_.m_130130_(this.f_235946_);
    }

    @Override
    public String m_5942_() {
        return String.format(Locale.ROOT, "%s %d", Registry.f_122829_.m_7981_(this.m_6012_()), this.f_235946_);
    }

    public ParticleType<ShriekParticleOption> m_6012_() {
        return ParticleTypes.f_235901_;
    }

    public int m_235958_() {
        return this.f_235946_;
    }
}

