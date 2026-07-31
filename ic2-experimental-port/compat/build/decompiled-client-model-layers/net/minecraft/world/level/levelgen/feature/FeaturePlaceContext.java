/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.feature;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class FeaturePlaceContext<FC extends FeatureConfiguration> {
    private final Optional<ConfiguredFeature<?, ?>> f_190927_;
    private final WorldGenLevel f_159763_;
    private final ChunkGenerator f_159764_;
    private final RandomSource f_159765_;
    private final BlockPos f_159766_;
    private final FC f_159767_;

    public FeaturePlaceContext(Optional<ConfiguredFeature<?, ?>> p_225035_, WorldGenLevel p_225036_, ChunkGenerator p_225037_, RandomSource p_225038_, BlockPos p_225039_, FC p_225040_) {
        this.f_190927_ = p_225035_;
        this.f_159763_ = p_225036_;
        this.f_159764_ = p_225037_;
        this.f_159765_ = p_225038_;
        this.f_159766_ = p_225039_;
        this.f_159767_ = p_225040_;
    }

    public Optional<ConfiguredFeature<?, ?>> m_190935_() {
        return this.f_190927_;
    }

    public WorldGenLevel m_159774_() {
        return this.f_159763_;
    }

    public ChunkGenerator m_159775_() {
        return this.f_159764_;
    }

    public RandomSource m_225041_() {
        return this.f_159765_;
    }

    public BlockPos m_159777_() {
        return this.f_159766_;
    }

    public FC m_159778_() {
        return this.f_159767_;
    }
}

