/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ProbabilityFeatureConfiguration
implements FeatureConfiguration {
    public static final Codec<ProbabilityFeatureConfiguration> f_67858_ = RecordCodecBuilder.create(p_67866_ -> p_67866_.group((App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").forGetter(p_161045_ -> Float.valueOf(p_161045_.f_67859_))).apply((Applicative)p_67866_, ProbabilityFeatureConfiguration::new));
    public final float f_67859_;

    public ProbabilityFeatureConfiguration(float p_67862_) {
        this.f_67859_ = p_67862_;
    }
}

