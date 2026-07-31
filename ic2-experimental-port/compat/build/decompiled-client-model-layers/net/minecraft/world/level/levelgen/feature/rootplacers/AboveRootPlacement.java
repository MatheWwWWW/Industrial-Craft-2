/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AboveRootPlacement(BlockStateProvider f_225754_, float f_225755_) {
    public static final Codec<AboveRootPlacement> f_225753_ = RecordCodecBuilder.create(p_225762_ -> p_225762_.group((App)BlockStateProvider.f_68747_.fieldOf("above_root_provider").forGetter(p_225767_ -> p_225767_.f_225754_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("above_root_placement_chance").forGetter(p_225764_ -> Float.valueOf(p_225764_.f_225755_))).apply((Applicative)p_225762_, AboveRootPlacement::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{AboveRootPlacement.class, "aboveRootProvider;aboveRootPlacementChance", "f_225754_", "f_225755_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{AboveRootPlacement.class, "aboveRootProvider;aboveRootPlacementChance", "f_225754_", "f_225755_"}, this);
    }

    @Override
    public final boolean equals(Object p_225769_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{AboveRootPlacement.class, "aboveRootProvider;aboveRootPlacementChance", "f_225754_", "f_225755_"}, this, p_225769_);
    }
}

