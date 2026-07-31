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
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;

public record SculkChargeParticleOptions(float f_235914_) implements ParticleOptions
{
    public static final Codec<SculkChargeParticleOptions> f_235912_ = RecordCodecBuilder.create(p_235920_ -> p_235920_.group((App)Codec.FLOAT.fieldOf("roll").forGetter(p_235922_ -> Float.valueOf(p_235922_.f_235914_))).apply((Applicative)p_235920_, SculkChargeParticleOptions::new));
    public static final ParticleOptions.Deserializer<SculkChargeParticleOptions> f_235913_ = new ParticleOptions.Deserializer<SculkChargeParticleOptions>(){

        @Override
        public SculkChargeParticleOptions m_5739_(ParticleType<SculkChargeParticleOptions> p_235933_, StringReader p_235934_) throws CommandSyntaxException {
            p_235934_.expect(' ');
            float $$2 = p_235934_.readFloat();
            return new SculkChargeParticleOptions($$2);
        }

        @Override
        public SculkChargeParticleOptions m_6507_(ParticleType<SculkChargeParticleOptions> p_235936_, FriendlyByteBuf p_235937_) {
            return new SculkChargeParticleOptions(p_235937_.readFloat());
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

    public ParticleType<SculkChargeParticleOptions> m_6012_() {
        return ParticleTypes.f_235899_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_235924_) {
        p_235924_.writeFloat(this.f_235914_);
    }

    @Override
    public String m_5942_() {
        return String.format(Locale.ROOT, "%s %.2f", Registry.f_122829_.m_7981_(this.m_6012_()), Float.valueOf(this.f_235914_));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SculkChargeParticleOptions.class, "roll", "f_235914_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SculkChargeParticleOptions.class, "roll", "f_235914_"}, this);
    }

    @Override
    public final boolean equals(Object p_235928_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SculkChargeParticleOptions.class, "roll", "f_235914_"}, this, p_235928_);
    }
}

