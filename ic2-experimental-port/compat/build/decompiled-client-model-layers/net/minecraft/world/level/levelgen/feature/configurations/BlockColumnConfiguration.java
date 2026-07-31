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
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BlockColumnConfiguration(List<Layer> f_191207_, Direction f_191208_, BlockPredicate f_191209_, boolean f_191210_) implements FeatureConfiguration
{
    public static final Codec<BlockColumnConfiguration> f_191206_ = RecordCodecBuilder.create(p_191222_ -> p_191222_.group((App)Layer.f_191233_.listOf().fieldOf("layers").forGetter(BlockColumnConfiguration::f_191207_), (App)Direction.f_175356_.fieldOf("direction").forGetter(BlockColumnConfiguration::f_191208_), (App)BlockPredicate.f_190392_.fieldOf("allowed_placement").forGetter(BlockColumnConfiguration::f_191209_), (App)Codec.BOOL.fieldOf("prioritize_tip").forGetter(BlockColumnConfiguration::f_191210_)).apply((Applicative)p_191222_, BlockColumnConfiguration::new));

    public static Layer m_191218_(IntProvider p_191219_, BlockStateProvider p_191220_) {
        return new Layer(p_191219_, p_191220_);
    }

    public static BlockColumnConfiguration m_191224_(IntProvider p_191225_, BlockStateProvider p_191226_) {
        return new BlockColumnConfiguration(List.of(BlockColumnConfiguration.m_191218_(p_191225_, p_191226_)), Direction.UP, BlockPredicate.f_190393_, false);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{BlockColumnConfiguration.class, "layers;direction;allowedPlacement;prioritizeTip", "f_191207_", "f_191208_", "f_191209_", "f_191210_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BlockColumnConfiguration.class, "layers;direction;allowedPlacement;prioritizeTip", "f_191207_", "f_191208_", "f_191209_", "f_191210_"}, this);
    }

    @Override
    public final boolean equals(Object p_191230_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BlockColumnConfiguration.class, "layers;direction;allowedPlacement;prioritizeTip", "f_191207_", "f_191208_", "f_191209_", "f_191210_"}, this, p_191230_);
    }

    public record Layer(IntProvider f_191234_, BlockStateProvider f_191235_) {
        public static final Codec<Layer> f_191233_ = RecordCodecBuilder.create(p_191242_ -> p_191242_.group((App)IntProvider.f_146532_.fieldOf("height").forGetter(Layer::f_191234_), (App)BlockStateProvider.f_68747_.fieldOf("provider").forGetter(Layer::f_191235_)).apply((Applicative)p_191242_, Layer::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Layer.class, "height;state", "f_191234_", "f_191235_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Layer.class, "height;state", "f_191234_", "f_191235_"}, this);
        }

        @Override
        public final boolean equals(Object p_191245_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Layer.class, "height;state", "f_191234_", "f_191235_"}, this, p_191245_);
        }
    }
}

