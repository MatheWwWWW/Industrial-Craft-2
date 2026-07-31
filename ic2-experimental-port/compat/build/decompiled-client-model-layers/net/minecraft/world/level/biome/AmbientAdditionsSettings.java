/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundEvent;

public class AmbientAdditionsSettings {
    public static final Codec<AmbientAdditionsSettings> f_47371_ = RecordCodecBuilder.create(p_47382_ -> p_47382_.group((App)SoundEvent.f_11655_.fieldOf("sound").forGetter(p_151642_ -> p_151642_.f_47372_), (App)Codec.DOUBLE.fieldOf("tick_chance").forGetter(p_151640_ -> p_151640_.f_47373_)).apply((Applicative)p_47382_, AmbientAdditionsSettings::new));
    private final SoundEvent f_47372_;
    private final double f_47373_;

    public AmbientAdditionsSettings(SoundEvent p_47376_, double p_47377_) {
        this.f_47372_ = p_47376_;
        this.f_47373_ = p_47377_;
    }

    public SoundEvent m_47378_() {
        return this.f_47372_;
    }

    public double m_47383_() {
        return this.f_47373_;
    }
}

