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
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class RandomBooleanFeatureConfiguration
implements FeatureConfiguration {
    public static final Codec<RandomBooleanFeatureConfiguration> f_67867_ = RecordCodecBuilder.create(p_67877_ -> p_67877_.group((App)PlacedFeature.f_191773_.fieldOf("feature_true").forGetter(p_204809_ -> p_204809_.f_67868_), (App)PlacedFeature.f_191773_.fieldOf("feature_false").forGetter(p_204807_ -> p_204807_.f_67869_)).apply((Applicative)p_67877_, RandomBooleanFeatureConfiguration::new));
    public final Holder<PlacedFeature> f_67868_;
    public final Holder<PlacedFeature> f_67869_;

    public RandomBooleanFeatureConfiguration(Holder<PlacedFeature> p_204804_, Holder<PlacedFeature> p_204805_) {
        this.f_67868_ = p_204804_;
        this.f_67869_ = p_204805_;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> m_7817_() {
        return Stream.concat(this.f_67868_.m_203334_().m_191781_(), this.f_67869_.m_203334_().m_191781_());
    }
}

