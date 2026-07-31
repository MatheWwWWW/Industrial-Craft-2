/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SimpleRandomFeatureConfiguration
implements FeatureConfiguration {
    public static final Codec<SimpleRandomFeatureConfiguration> f_68089_ = ExtraCodecs.m_203982_(PlacedFeature.f_191774_).fieldOf("features").xmap(SimpleRandomFeatureConfiguration::new, p_204844_ -> p_204844_.f_68090_).codec();
    public final HolderSet<PlacedFeature> f_68090_;

    public SimpleRandomFeatureConfiguration(HolderSet<PlacedFeature> p_204842_) {
        this.f_68090_ = p_204842_;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> m_7817_() {
        return this.f_68090_.m_203614_().flatMap(p_204846_ -> ((PlacedFeature)p_204846_.m_203334_()).m_191781_());
    }
}

