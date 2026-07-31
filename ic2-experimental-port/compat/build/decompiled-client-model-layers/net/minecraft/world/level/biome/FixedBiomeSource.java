/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

public class FixedBiomeSource
extends BiomeSource
implements BiomeManager.NoiseBiomeSource {
    public static final Codec<FixedBiomeSource> f_48251_ = Biome.f_47431_.fieldOf("biome").xmap(FixedBiomeSource::new, p_204259_ -> p_204259_.f_48252_).stable().codec();
    private final Holder<Biome> f_48252_;

    public FixedBiomeSource(Holder<Biome> p_204257_) {
        super((List<Holder<Biome>>)ImmutableList.of(p_204257_));
        this.f_48252_ = p_204257_;
    }

    @Override
    protected Codec<? extends BiomeSource> m_5820_() {
        return f_48251_;
    }

    @Override
    public Holder<Biome> m_203407_(int p_204265_, int p_204266_, int p_204267_, Climate.Sampler p_204268_) {
        return this.f_48252_;
    }

    @Override
    public Holder<Biome> m_203495_(int p_204261_, int p_204262_, int p_204263_) {
        return this.f_48252_;
    }

    @Override
    @Nullable
    public Pair<BlockPos, Holder<Biome>> m_213971_(int p_220640_, int p_220641_, int p_220642_, int p_220643_, int p_220644_, Predicate<Holder<Biome>> p_220645_, RandomSource p_220646_, boolean p_220647_, Climate.Sampler p_220648_) {
        if (p_220645_.test(this.f_48252_)) {
            if (p_220647_) {
                return Pair.of((Object)new BlockPos(p_220640_, p_220641_, p_220642_), this.f_48252_);
            }
            return Pair.of((Object)new BlockPos(p_220640_ - p_220643_ + p_220646_.m_188503_(p_220643_ * 2 + 1), p_220641_, p_220642_ - p_220643_ + p_220646_.m_188503_(p_220643_ * 2 + 1)), this.f_48252_);
        }
        return null;
    }

    @Override
    @Nullable
    public Pair<BlockPos, Holder<Biome>> m_214004_(BlockPos p_220650_, int p_220651_, int p_220652_, int p_220653_, Predicate<Holder<Biome>> p_220654_, Climate.Sampler p_220655_, LevelReader p_220656_) {
        return p_220654_.test(this.f_48252_) ? Pair.of((Object)p_220650_, this.f_48252_) : null;
    }

    @Override
    public Set<Holder<Biome>> m_183399_(int p_187038_, int p_187039_, int p_187040_, int p_187041_, Climate.Sampler p_187042_) {
        return Sets.newHashSet(Set.of(this.f_48252_));
    }
}

