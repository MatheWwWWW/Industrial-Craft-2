/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class RandomFeatureConfiguration
implements FeatureConfiguration {
    public static final Codec<RandomFeatureConfiguration> f_67881_ = RecordCodecBuilder.create(p_67898_ -> p_67898_.apply2(RandomFeatureConfiguration::new, (App)WeightedPlacedFeature.f_191171_.listOf().fieldOf("features").forGetter(p_161053_ -> p_161053_.f_67882_), (App)PlacedFeature.f_191773_.fieldOf("default").forGetter(p_204816_ -> p_204816_.f_67883_)));
    public final List<WeightedPlacedFeature> f_67882_;
    public final Holder<PlacedFeature> f_67883_;

    public RandomFeatureConfiguration(List<WeightedPlacedFeature> p_204811_, Holder<PlacedFeature> p_204812_) {
        this.f_67882_ = p_204811_;
        this.f_67883_ = p_204812_;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> m_7817_() {
        return Stream.concat(this.f_67882_.stream().flatMap(p_204814_ -> p_204814_.f_191172_.m_203334_().m_191781_()), this.f_67883_.m_203334_().m_191781_());
    }
}

