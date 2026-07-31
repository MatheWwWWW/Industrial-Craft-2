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
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record TwistingVinesConfig(int f_191365_, int f_191366_, int f_191367_) implements FeatureConfiguration
{
    public static final Codec<TwistingVinesConfig> f_191364_ = RecordCodecBuilder.create(p_191375_ -> p_191375_.group((App)ExtraCodecs.f_144629_.fieldOf("spread_width").forGetter(TwistingVinesConfig::f_191365_), (App)ExtraCodecs.f_144629_.fieldOf("spread_height").forGetter(TwistingVinesConfig::f_191366_), (App)ExtraCodecs.f_144629_.fieldOf("max_height").forGetter(TwistingVinesConfig::f_191367_)).apply((Applicative)p_191375_, TwistingVinesConfig::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{TwistingVinesConfig.class, "spreadWidth;spreadHeight;maxHeight", "f_191365_", "f_191366_", "f_191367_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TwistingVinesConfig.class, "spreadWidth;spreadHeight;maxHeight", "f_191365_", "f_191366_", "f_191367_"}, this);
    }

    @Override
    public final boolean equals(Object p_191379_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TwistingVinesConfig.class, "spreadWidth;spreadHeight;maxHeight", "f_191365_", "f_191366_", "f_191367_"}, this, p_191379_);
    }
}

