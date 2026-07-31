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
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record RandomPatchConfiguration(int f_67907_, int f_191302_, int f_191303_, Holder<PlacedFeature> f_191304_) implements FeatureConfiguration
{
    public static final Codec<RandomPatchConfiguration> f_67902_ = RecordCodecBuilder.create(p_191312_ -> p_191312_.group((App)ExtraCodecs.f_144629_.fieldOf("tries").orElse((Object)128).forGetter(RandomPatchConfiguration::f_67907_), (App)ExtraCodecs.f_144628_.fieldOf("xz_spread").orElse((Object)7).forGetter(RandomPatchConfiguration::f_191302_), (App)ExtraCodecs.f_144628_.fieldOf("y_spread").orElse((Object)3).forGetter(RandomPatchConfiguration::f_191303_), (App)PlacedFeature.f_191773_.fieldOf("feature").forGetter(RandomPatchConfiguration::f_191304_)).apply((Applicative)p_191312_, RandomPatchConfiguration::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{RandomPatchConfiguration.class, "tries;xzSpread;ySpread;feature", "f_67907_", "f_191302_", "f_191303_", "f_191304_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{RandomPatchConfiguration.class, "tries;xzSpread;ySpread;feature", "f_67907_", "f_191302_", "f_191303_", "f_191304_"}, this);
    }

    @Override
    public final boolean equals(Object p_191317_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{RandomPatchConfiguration.class, "tries;xzSpread;ySpread;feature", "f_67907_", "f_191302_", "f_191303_", "f_191304_"}, this, p_191317_);
    }
}

