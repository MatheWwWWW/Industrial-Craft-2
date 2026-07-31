/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.carver;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class CarvingContext
extends WorldGenerationContext {
    private final RegistryAccess f_190639_;
    private final NoiseChunk f_190640_;
    private final RandomState f_224842_;
    private final SurfaceRules.RuleSource f_224843_;

    public CarvingContext(NoiseBasedChunkGenerator p_224845_, RegistryAccess p_224846_, LevelHeightAccessor p_224847_, NoiseChunk p_224848_, RandomState p_224849_, SurfaceRules.RuleSource p_224850_) {
        super(p_224845_, p_224847_);
        this.f_190639_ = p_224846_;
        this.f_190640_ = p_224848_;
        this.f_224842_ = p_224849_;
        this.f_224843_ = p_224850_;
    }

    @Deprecated
    public Optional<BlockState> m_190646_(Function<BlockPos, Holder<Biome>> p_190647_, ChunkAccess p_190648_, BlockPos p_190649_, boolean p_190650_) {
        return this.f_224842_.m_224580_().m_189971_(this.f_224843_, this, p_190647_, p_190648_, this.f_190640_, p_190649_, p_190650_);
    }

    @Deprecated
    public RegistryAccess m_190651_() {
        return this.f_190639_;
    }

    public RandomState m_224851_() {
        return this.f_224842_;
    }
}

