/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class OreConfiguration
implements FeatureConfiguration {
    public static final Codec<OreConfiguration> f_67837_ = RecordCodecBuilder.create(p_67849_ -> p_67849_.group((App)Codec.list(TargetBlockState.f_161031_).fieldOf("targets").forGetter(p_161027_ -> p_161027_.f_161005_), (App)Codec.intRange((int)0, (int)64).fieldOf("size").forGetter(p_161025_ -> p_161025_.f_67839_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("discard_chance_on_air_exposure").forGetter(p_161020_ -> Float.valueOf(p_161020_.f_161006_))).apply((Applicative)p_67849_, OreConfiguration::new));
    public final List<TargetBlockState> f_161005_;
    public final int f_67839_;
    public final float f_161006_;

    public OreConfiguration(List<TargetBlockState> p_161016_, int p_161017_, float p_161018_) {
        this.f_67839_ = p_161017_;
        this.f_161005_ = p_161016_;
        this.f_161006_ = p_161018_;
    }

    public OreConfiguration(List<TargetBlockState> p_161013_, int p_161014_) {
        this(p_161013_, p_161014_, 0.0f);
    }

    public OreConfiguration(RuleTest p_161008_, BlockState p_161009_, int p_161010_, float p_161011_) {
        this((List<TargetBlockState>)ImmutableList.of((Object)new TargetBlockState(p_161008_, p_161009_)), p_161010_, p_161011_);
    }

    public OreConfiguration(RuleTest p_67843_, BlockState p_67844_, int p_67845_) {
        this((List<TargetBlockState>)ImmutableList.of((Object)new TargetBlockState(p_67843_, p_67844_)), p_67845_, 0.0f);
    }

    public static TargetBlockState m_161021_(RuleTest p_161022_, BlockState p_161023_) {
        return new TargetBlockState(p_161022_, p_161023_);
    }

    public static class TargetBlockState {
        public static final Codec<TargetBlockState> f_161031_ = RecordCodecBuilder.create(p_161039_ -> p_161039_.group((App)RuleTest.f_74307_.fieldOf("target").forGetter(p_161043_ -> p_161043_.f_161032_), (App)BlockState.f_61039_.fieldOf("state").forGetter(p_161041_ -> p_161041_.f_161033_)).apply((Applicative)p_161039_, TargetBlockState::new));
        public final RuleTest f_161032_;
        public final BlockState f_161033_;

        TargetBlockState(RuleTest p_161036_, BlockState p_161037_) {
            this.f_161032_ = p_161036_;
            this.f_161033_ = p_161037_;
        }
    }
}

