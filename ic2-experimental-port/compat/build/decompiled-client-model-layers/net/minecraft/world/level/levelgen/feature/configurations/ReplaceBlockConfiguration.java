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
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;

public class ReplaceBlockConfiguration
implements FeatureConfiguration {
    public static final Codec<ReplaceBlockConfiguration> f_68023_ = RecordCodecBuilder.create(p_161087_ -> p_161087_.group((App)Codec.list(OreConfiguration.TargetBlockState.f_161031_).fieldOf("targets").forGetter(p_161089_ -> p_161089_.f_161083_)).apply((Applicative)p_161087_, ReplaceBlockConfiguration::new));
    public final List<OreConfiguration.TargetBlockState> f_161083_;

    public ReplaceBlockConfiguration(BlockState p_68028_, BlockState p_68029_) {
        this((List<OreConfiguration.TargetBlockState>)ImmutableList.of((Object)OreConfiguration.m_161021_(new BlockStateMatchTest(p_68028_), p_68029_)));
    }

    public ReplaceBlockConfiguration(List<OreConfiguration.TargetBlockState> p_161085_) {
        this.f_161083_ = p_161085_;
    }
}

