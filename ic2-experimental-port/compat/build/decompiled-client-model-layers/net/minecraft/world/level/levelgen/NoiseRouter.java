/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.world.level.levelgen.DensityFunction;

public record NoiseRouter(DensityFunction f_209378_, DensityFunction f_209379_, DensityFunction f_209380_, DensityFunction f_209381_, DensityFunction f_209384_, DensityFunction f_224392_, DensityFunction f_209386_, DensityFunction f_209387_, DensityFunction f_209388_, DensityFunction f_209389_, DensityFunction f_209390_, DensityFunction f_209391_, DensityFunction f_209392_, DensityFunction f_209393_, DensityFunction f_209394_) {
    public static final Codec<NoiseRouter> f_224391_ = RecordCodecBuilder.create(p_224411_ -> p_224411_.group(NoiseRouter.m_224414_("barrier", NoiseRouter::f_209378_), NoiseRouter.m_224414_("fluid_level_floodedness", NoiseRouter::f_209379_), NoiseRouter.m_224414_("fluid_level_spread", NoiseRouter::f_209380_), NoiseRouter.m_224414_("lava", NoiseRouter::f_209381_), NoiseRouter.m_224414_("temperature", NoiseRouter::f_209384_), NoiseRouter.m_224414_("vegetation", NoiseRouter::f_224392_), NoiseRouter.m_224414_("continents", NoiseRouter::f_209386_), NoiseRouter.m_224414_("erosion", NoiseRouter::f_209387_), NoiseRouter.m_224414_("depth", NoiseRouter::f_209388_), NoiseRouter.m_224414_("ridges", NoiseRouter::f_209389_), NoiseRouter.m_224414_("initial_density_without_jaggedness", NoiseRouter::f_209390_), NoiseRouter.m_224414_("final_density", NoiseRouter::f_209391_), NoiseRouter.m_224414_("vein_toggle", NoiseRouter::f_209392_), NoiseRouter.m_224414_("vein_ridged", NoiseRouter::f_209393_), NoiseRouter.m_224414_("vein_gap", NoiseRouter::f_209394_)).apply((Applicative)p_224411_, NoiseRouter::new));

    private static RecordCodecBuilder<NoiseRouter, DensityFunction> m_224414_(String p_224415_, Function<NoiseRouter, DensityFunction> p_224416_) {
        return DensityFunction.f_208218_.fieldOf(p_224415_).forGetter(p_224416_);
    }

    public NoiseRouter m_224412_(DensityFunction.Visitor p_224413_) {
        return new NoiseRouter(this.f_209378_.m_207456_(p_224413_), this.f_209379_.m_207456_(p_224413_), this.f_209380_.m_207456_(p_224413_), this.f_209381_.m_207456_(p_224413_), this.f_209384_.m_207456_(p_224413_), this.f_224392_.m_207456_(p_224413_), this.f_209386_.m_207456_(p_224413_), this.f_209387_.m_207456_(p_224413_), this.f_209388_.m_207456_(p_224413_), this.f_209389_.m_207456_(p_224413_), this.f_209390_.m_207456_(p_224413_), this.f_209391_.m_207456_(p_224413_), this.f_209392_.m_207456_(p_224413_), this.f_209393_.m_207456_(p_224413_), this.f_209394_.m_207456_(p_224413_));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{NoiseRouter.class, "barrierNoise;fluidLevelFloodednessNoise;fluidLevelSpreadNoise;lavaNoise;temperature;vegetation;continents;erosion;depth;ridges;initialDensityWithoutJaggedness;finalDensity;veinToggle;veinRidged;veinGap", "f_209378_", "f_209379_", "f_209380_", "f_209381_", "f_209384_", "f_224392_", "f_209386_", "f_209387_", "f_209388_", "f_209389_", "f_209390_", "f_209391_", "f_209392_", "f_209393_", "f_209394_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NoiseRouter.class, "barrierNoise;fluidLevelFloodednessNoise;fluidLevelSpreadNoise;lavaNoise;temperature;vegetation;continents;erosion;depth;ridges;initialDensityWithoutJaggedness;finalDensity;veinToggle;veinRidged;veinGap", "f_209378_", "f_209379_", "f_209380_", "f_209381_", "f_209384_", "f_224392_", "f_209386_", "f_209387_", "f_209388_", "f_209389_", "f_209390_", "f_209391_", "f_209392_", "f_209393_", "f_209394_"}, this);
    }

    @Override
    public final boolean equals(Object p_209421_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NoiseRouter.class, "barrierNoise;fluidLevelFloodednessNoise;fluidLevelSpreadNoise;lavaNoise;temperature;vegetation;continents;erosion;depth;ridges;initialDensityWithoutJaggedness;finalDensity;veinToggle;veinRidged;veinGap", "f_209378_", "f_209379_", "f_209380_", "f_209381_", "f_209384_", "f_224392_", "f_209386_", "f_209387_", "f_209388_", "f_209389_", "f_209390_", "f_209391_", "f_209392_", "f_209393_", "f_209394_"}, this, p_209421_);
    }
}

