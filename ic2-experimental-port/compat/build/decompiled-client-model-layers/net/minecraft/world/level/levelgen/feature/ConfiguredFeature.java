/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record ConfiguredFeature<FC extends FeatureConfiguration, F extends Feature<FC>>(F f_65377_, FC f_65378_) {
    public static final Codec<ConfiguredFeature<?, ?>> f_65373_ = Registry.f_122839_.m_194605_().dispatch(p_65391_ -> p_65391_.f_65377_, Feature::m_65787_);
    public static final Codec<Holder<ConfiguredFeature<?, ?>>> f_65374_ = RegistryFileCodec.m_135589_(Registry.f_122881_, f_65373_);
    public static final Codec<HolderSet<ConfiguredFeature<?, ?>>> f_65375_ = RegistryCodecs.m_206279_(Registry.f_122881_, f_65373_);

    public boolean m_224953_(WorldGenLevel p_224954_, ChunkGenerator p_224955_, RandomSource p_224956_, BlockPos p_224957_) {
        return ((Feature)this.f_65377_).m_225028_(this.f_65378_, p_224954_, p_224955_, p_224956_, p_224957_);
    }

    public Stream<ConfiguredFeature<?, ?>> m_65398_() {
        return Stream.concat(Stream.of(this), this.f_65378_.m_7817_());
    }

    @Override
    public String toString() {
        return "Configured: " + this.f_65377_ + ": " + this.f_65378_;
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ConfiguredFeature.class, "feature;config", "f_65377_", "f_65378_"}, this);
    }

    @Override
    public final boolean equals(Object p_204705_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ConfiguredFeature.class, "feature;config", "f_65377_", "f_65378_"}, this, p_204705_);
    }
}

