/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CountConfiguration
implements FeatureConfiguration {
    public static final Codec<CountConfiguration> f_67568_ = IntProvider.m_146545_(0, 256).fieldOf("count").xmap(CountConfiguration::new, CountConfiguration::m_160725_).codec();
    private final IntProvider f_67569_;

    public CountConfiguration(int p_67572_) {
        this.f_67569_ = ConstantInt.m_146483_(p_67572_);
    }

    public CountConfiguration(IntProvider p_160724_) {
        this.f_67569_ = p_160724_;
    }

    public IntProvider m_160725_() {
        return this.f_67569_;
    }
}

