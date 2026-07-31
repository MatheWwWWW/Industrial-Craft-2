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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ReplaceSphereConfiguration
implements FeatureConfiguration {
    public static final Codec<ReplaceSphereConfiguration> f_68036_ = RecordCodecBuilder.create(p_68048_ -> p_68048_.group((App)BlockState.f_61039_.fieldOf("target").forGetter(p_161100_ -> p_161100_.f_68037_), (App)BlockState.f_61039_.fieldOf("state").forGetter(p_161098_ -> p_161098_.f_68038_), (App)IntProvider.m_146545_(0, 12).fieldOf("radius").forGetter(p_161095_ -> p_161095_.f_68039_)).apply((Applicative)p_68048_, ReplaceSphereConfiguration::new));
    public final BlockState f_68037_;
    public final BlockState f_68038_;
    private final IntProvider f_68039_;

    public ReplaceSphereConfiguration(BlockState p_161091_, BlockState p_161092_, IntProvider p_161093_) {
        this.f_68037_ = p_161091_;
        this.f_68038_ = p_161092_;
        this.f_68039_ = p_161093_;
    }

    public IntProvider m_161096_() {
        return this.f_68039_;
    }
}

