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
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SculkPatchConfiguration(int f_225426_, int f_225427_, int f_225428_, int f_225429_, int f_225430_, IntProvider f_225431_, float f_225432_) implements FeatureConfiguration
{
    public static final Codec<SculkPatchConfiguration> f_225425_ = RecordCodecBuilder.create(p_225444_ -> p_225444_.group((App)Codec.intRange((int)1, (int)32).fieldOf("charge_count").forGetter(SculkPatchConfiguration::f_225426_), (App)Codec.intRange((int)1, (int)500).fieldOf("amount_per_charge").forGetter(SculkPatchConfiguration::f_225427_), (App)Codec.intRange((int)1, (int)64).fieldOf("spread_attempts").forGetter(SculkPatchConfiguration::f_225428_), (App)Codec.intRange((int)0, (int)8).fieldOf("growth_rounds").forGetter(SculkPatchConfiguration::f_225429_), (App)Codec.intRange((int)0, (int)8).fieldOf("spread_rounds").forGetter(SculkPatchConfiguration::f_225430_), (App)IntProvider.f_146531_.fieldOf("extra_rare_growths").forGetter(SculkPatchConfiguration::f_225431_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("catalyst_chance").forGetter(SculkPatchConfiguration::f_225432_)).apply((Applicative)p_225444_, SculkPatchConfiguration::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SculkPatchConfiguration.class, "chargeCount;amountPerCharge;spreadAttempts;growthRounds;spreadRounds;extraRareGrowths;catalystChance", "f_225426_", "f_225427_", "f_225428_", "f_225429_", "f_225430_", "f_225431_", "f_225432_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SculkPatchConfiguration.class, "chargeCount;amountPerCharge;spreadAttempts;growthRounds;spreadRounds;extraRareGrowths;catalystChance", "f_225426_", "f_225427_", "f_225428_", "f_225429_", "f_225430_", "f_225431_", "f_225432_"}, this);
    }

    @Override
    public final boolean equals(Object p_225449_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SculkPatchConfiguration.class, "chargeCount;amountPerCharge;spreadAttempts;growthRounds;spreadRounds;extraRareGrowths;catalystChance", "f_225426_", "f_225427_", "f_225428_", "f_225429_", "f_225430_", "f_225431_", "f_225432_"}, this, p_225449_);
    }
}

