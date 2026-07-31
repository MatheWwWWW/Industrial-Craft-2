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
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.material.FluidState;

public class SpringConfiguration
implements FeatureConfiguration {
    public static final Codec<SpringConfiguration> f_68123_ = RecordCodecBuilder.create(p_68139_ -> p_68139_.group((App)FluidState.f_76146_.fieldOf("state").forGetter(p_161205_ -> p_161205_.f_68124_), (App)Codec.BOOL.fieldOf("requires_block_below").orElse((Object)true).forGetter(p_161203_ -> p_161203_.f_68125_), (App)Codec.INT.fieldOf("rock_count").orElse((Object)4).forGetter(p_161201_ -> p_161201_.f_68126_), (App)Codec.INT.fieldOf("hole_count").orElse((Object)1).forGetter(p_161199_ -> p_161199_.f_68127_), (App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("valid_blocks").forGetter(p_204854_ -> p_204854_.f_68128_)).apply((Applicative)p_68139_, SpringConfiguration::new));
    public final FluidState f_68124_;
    public final boolean f_68125_;
    public final int f_68126_;
    public final int f_68127_;
    public final HolderSet<Block> f_68128_;

    public SpringConfiguration(FluidState p_204848_, boolean p_204849_, int p_204850_, int p_204851_, HolderSet<Block> p_204852_) {
        this.f_68124_ = p_204848_;
        this.f_68125_ = p_204849_;
        this.f_68126_ = p_204850_;
        this.f_68127_ = p_204851_;
        this.f_68128_ = p_204852_;
    }
}

