/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record RuleBasedBlockStateProvider(BlockStateProvider f_225925_, List<Rule> f_225926_) {
    public static final Codec<RuleBasedBlockStateProvider> f_225924_ = RecordCodecBuilder.create(p_225939_ -> p_225939_.group((App)BlockStateProvider.f_68747_.fieldOf("fallback").forGetter(RuleBasedBlockStateProvider::f_225925_), (App)Rule.f_225947_.listOf().fieldOf("rules").forGetter(RuleBasedBlockStateProvider::f_225926_)).apply((Applicative)p_225939_, RuleBasedBlockStateProvider::new));

    public static RuleBasedBlockStateProvider m_225940_(BlockStateProvider p_225941_) {
        return new RuleBasedBlockStateProvider(p_225941_, List.of());
    }

    public static RuleBasedBlockStateProvider m_225936_(Block p_225937_) {
        return RuleBasedBlockStateProvider.m_225940_(BlockStateProvider.m_191382_(p_225937_));
    }

    public BlockState m_225932_(WorldGenLevel p_225933_, RandomSource p_225934_, BlockPos p_225935_) {
        for (Rule $$3 : this.f_225926_) {
            if (!$$3.f_225948_().test(p_225933_, p_225935_)) continue;
            return $$3.f_225949_().m_213972_(p_225934_, p_225935_);
        }
        return this.f_225925_.m_213972_(p_225934_, p_225935_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{RuleBasedBlockStateProvider.class, "fallback;rules", "f_225925_", "f_225926_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{RuleBasedBlockStateProvider.class, "fallback;rules", "f_225925_", "f_225926_"}, this);
    }

    @Override
    public final boolean equals(Object p_225944_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{RuleBasedBlockStateProvider.class, "fallback;rules", "f_225925_", "f_225926_"}, this, p_225944_);
    }

    public record Rule(BlockPredicate f_225948_, BlockStateProvider f_225949_) {
        public static final Codec<Rule> f_225947_ = RecordCodecBuilder.create(p_225956_ -> p_225956_.group((App)BlockPredicate.f_190392_.fieldOf("if_true").forGetter(Rule::f_225948_), (App)BlockStateProvider.f_68747_.fieldOf("then").forGetter(Rule::f_225949_)).apply((Applicative)p_225956_, Rule::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Rule.class, "ifTrue;then", "f_225948_", "f_225949_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Rule.class, "ifTrue;then", "f_225948_", "f_225949_"}, this);
        }

        @Override
        public final boolean equals(Object p_225959_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Rule.class, "ifTrue;then", "f_225948_", "f_225949_"}, this, p_225959_);
        }
    }
}

