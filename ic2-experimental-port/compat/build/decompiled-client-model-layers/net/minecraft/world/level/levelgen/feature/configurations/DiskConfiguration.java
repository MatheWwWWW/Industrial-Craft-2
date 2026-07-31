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
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

public record DiskConfiguration(RuleBasedBlockStateProvider f_225372_, BlockPredicate f_225373_, IntProvider f_67620_, int f_67621_) implements FeatureConfiguration
{
    public static final Codec<DiskConfiguration> f_67618_ = RecordCodecBuilder.create(p_191250_ -> p_191250_.group((App)RuleBasedBlockStateProvider.f_225924_.fieldOf("state_provider").forGetter(DiskConfiguration::f_225372_), (App)BlockPredicate.f_190392_.fieldOf("target").forGetter(DiskConfiguration::f_225373_), (App)IntProvider.m_146545_(0, 8).fieldOf("radius").forGetter(DiskConfiguration::f_67620_), (App)Codec.intRange((int)0, (int)4).fieldOf("half_height").forGetter(DiskConfiguration::f_67621_)).apply((Applicative)p_191250_, DiskConfiguration::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{DiskConfiguration.class, "stateProvider;target;radius;halfHeight", "f_225372_", "f_225373_", "f_67620_", "f_67621_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{DiskConfiguration.class, "stateProvider;target;radius;halfHeight", "f_225372_", "f_225373_", "f_67620_", "f_67621_"}, this);
    }

    @Override
    public final boolean equals(Object p_191255_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{DiskConfiguration.class, "stateProvider;target;radius;halfHeight", "f_225372_", "f_225373_", "f_67620_", "f_67621_"}, this, p_191255_);
    }
}

