/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.item;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;

public record Instrument(SoundEvent f_220079_, int f_220080_, float f_220081_) {
    public static final Codec<Instrument> f_220078_ = RecordCodecBuilder.create(p_220089_ -> p_220089_.group((App)SoundEvent.f_11655_.fieldOf("sound_event").forGetter(Instrument::f_220079_), (App)ExtraCodecs.f_144629_.fieldOf("use_duration").forGetter(Instrument::f_220080_), (App)ExtraCodecs.f_184349_.fieldOf("range").forGetter(Instrument::f_220081_)).apply((Applicative)p_220089_, Instrument::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{Instrument.class, "soundEvent;useDuration;range", "f_220079_", "f_220080_", "f_220081_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Instrument.class, "soundEvent;useDuration;range", "f_220079_", "f_220080_", "f_220081_"}, this);
    }

    @Override
    public final boolean equals(Object p_220093_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Instrument.class, "soundEvent;useDuration;range", "f_220079_", "f_220080_", "f_220081_"}, this, p_220093_);
    }
}

