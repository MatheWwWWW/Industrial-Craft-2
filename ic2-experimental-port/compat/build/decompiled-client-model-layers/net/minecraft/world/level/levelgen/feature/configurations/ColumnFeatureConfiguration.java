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
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ColumnFeatureConfiguration
implements FeatureConfiguration {
    public static final Codec<ColumnFeatureConfiguration> f_67553_ = RecordCodecBuilder.create(p_67563_ -> p_67563_.group((App)IntProvider.m_146545_(0, 3).fieldOf("reach").forGetter(p_160722_ -> p_160722_.f_67554_), (App)IntProvider.m_146545_(1, 10).fieldOf("height").forGetter(p_160719_ -> p_160719_.f_67555_)).apply((Applicative)p_67563_, ColumnFeatureConfiguration::new));
    private final IntProvider f_67554_;
    private final IntProvider f_67555_;

    public ColumnFeatureConfiguration(IntProvider p_160715_, IntProvider p_160716_) {
        this.f_67554_ = p_160715_;
        this.f_67555_ = p_160716_;
    }

    public IntProvider m_160717_() {
        return this.f_67554_;
    }

    public IntProvider m_160720_() {
        return this.f_67555_;
    }
}

