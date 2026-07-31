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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;

public class AmbientParticleSettings {
    public static final Codec<AmbientParticleSettings> f_47412_ = RecordCodecBuilder.create(p_47423_ -> p_47423_.group((App)ParticleTypes.f_123791_.fieldOf("options").forGetter(p_151654_ -> p_151654_.f_47413_), (App)Codec.FLOAT.fieldOf("probability").forGetter(p_151652_ -> Float.valueOf(p_151652_.f_47414_))).apply((Applicative)p_47423_, AmbientParticleSettings::new));
    private final ParticleOptions f_47413_;
    private final float f_47414_;

    public AmbientParticleSettings(ParticleOptions p_47417_, float p_47418_) {
        this.f_47413_ = p_47417_;
        this.f_47414_ = p_47418_;
    }

    public ParticleOptions m_47419_() {
        return this.f_47413_;
    }

    public boolean m_220527_(RandomSource p_220528_) {
        return p_220528_.m_188501_() <= this.f_47414_;
    }
}

