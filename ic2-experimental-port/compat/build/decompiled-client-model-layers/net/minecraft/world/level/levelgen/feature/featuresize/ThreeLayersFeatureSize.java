/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;

public class ThreeLayersFeatureSize
extends FeatureSize {
    public static final Codec<ThreeLayersFeatureSize> f_68306_ = RecordCodecBuilder.create(p_68326_ -> p_68326_.group((App)Codec.intRange((int)0, (int)80).fieldOf("limit").orElse((Object)1).forGetter(p_161335_ -> p_161335_.f_68307_), (App)Codec.intRange((int)0, (int)80).fieldOf("upper_limit").orElse((Object)1).forGetter(p_161333_ -> p_161333_.f_68308_), (App)Codec.intRange((int)0, (int)16).fieldOf("lower_size").orElse((Object)0).forGetter(p_161331_ -> p_161331_.f_68309_), (App)Codec.intRange((int)0, (int)16).fieldOf("middle_size").orElse((Object)1).forGetter(p_161329_ -> p_161329_.f_68310_), (App)Codec.intRange((int)0, (int)16).fieldOf("upper_size").orElse((Object)1).forGetter(p_161327_ -> p_161327_.f_68311_), ThreeLayersFeatureSize.m_68286_()).apply((Applicative)p_68326_, ThreeLayersFeatureSize::new));
    private final int f_68307_;
    private final int f_68308_;
    private final int f_68309_;
    private final int f_68310_;
    private final int f_68311_;

    public ThreeLayersFeatureSize(int p_68314_, int p_68315_, int p_68316_, int p_68317_, int p_68318_, OptionalInt p_68319_) {
        super(p_68319_);
        this.f_68307_ = p_68314_;
        this.f_68308_ = p_68315_;
        this.f_68309_ = p_68316_;
        this.f_68310_ = p_68317_;
        this.f_68311_ = p_68318_;
    }

    @Override
    protected FeatureSizeType<?> m_7612_() {
        return FeatureSizeType.f_68297_;
    }

    @Override
    public int m_6133_(int p_68321_, int p_68322_) {
        if (p_68322_ < this.f_68307_) {
            return this.f_68309_;
        }
        if (p_68322_ >= p_68321_ - this.f_68308_) {
            return this.f_68311_;
        }
        return this.f_68310_;
    }
}

