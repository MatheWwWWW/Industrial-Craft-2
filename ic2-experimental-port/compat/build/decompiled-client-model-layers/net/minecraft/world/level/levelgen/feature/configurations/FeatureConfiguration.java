/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public interface FeatureConfiguration {
    public static final NoneFeatureConfiguration f_67737_ = NoneFeatureConfiguration.f_67816_;

    default public Stream<ConfiguredFeature<?, ?>> m_7817_() {
        return Stream.empty();
    }
}

