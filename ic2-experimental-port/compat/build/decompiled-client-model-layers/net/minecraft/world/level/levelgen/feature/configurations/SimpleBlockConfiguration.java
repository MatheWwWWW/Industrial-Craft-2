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
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SimpleBlockConfiguration(BlockStateProvider f_68069_) implements FeatureConfiguration
{
    public static final Codec<SimpleBlockConfiguration> f_68068_ = RecordCodecBuilder.create(p_191331_ -> p_191331_.group((App)BlockStateProvider.f_68747_.fieldOf("to_place").forGetter(p_161168_ -> p_161168_.f_68069_)).apply((Applicative)p_191331_, SimpleBlockConfiguration::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SimpleBlockConfiguration.class, "toPlace", "f_68069_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SimpleBlockConfiguration.class, "toPlace", "f_68069_"}, this);
    }

    @Override
    public final boolean equals(Object p_191333_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SimpleBlockConfiguration.class, "toPlace", "f_68069_"}, this, p_191333_);
    }
}

