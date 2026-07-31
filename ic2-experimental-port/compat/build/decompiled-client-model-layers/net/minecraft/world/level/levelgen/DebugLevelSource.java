/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class DebugLevelSource
extends ChunkGenerator {
    public static final Codec<DebugLevelSource> f_64111_ = RecordCodecBuilder.create(p_208215_ -> DebugLevelSource.m_208005_(p_208215_).and((App)RegistryOps.m_206832_(Registry.f_122885_).forGetter(p_208210_ -> p_208210_.f_64117_)).apply((Applicative)p_208215_, p_208215_.stable(DebugLevelSource::new)));
    private static final int f_158227_ = 2;
    private static final List<BlockState> f_64114_ = StreamSupport.stream(Registry.f_122824_.spliterator(), false).flatMap(p_208208_ -> p_208208_.m_49965_().m_61056_().stream()).collect(Collectors.toList());
    private static final int f_64115_ = Mth.m_14167_(Mth.m_14116_(f_64114_.size()));
    private static final int f_64116_ = Mth.m_14167_((float)f_64114_.size() / (float)f_64115_);
    protected static final BlockState f_64112_ = Blocks.f_50016_.m_49966_();
    protected static final BlockState f_64113_ = Blocks.f_50375_.m_49966_();
    public static final int f_158225_ = 70;
    public static final int f_158226_ = 60;
    private final Registry<Biome> f_64117_;

    public DebugLevelSource(Registry<StructureSet> p_208205_, Registry<Biome> p_208206_) {
        super(p_208205_, Optional.empty(), new FixedBiomeSource(p_208206_.m_214121_(Biomes.f_48202_)));
        this.f_64117_ = p_208206_;
    }

    public Registry<Biome> m_64151_() {
        return this.f_64117_;
    }

    @Override
    protected Codec<? extends ChunkGenerator> m_6909_() {
        return f_64111_;
    }

    @Override
    public void m_214194_(WorldGenRegion p_223978_, StructureManager p_223979_, RandomState p_223980_, ChunkAccess p_223981_) {
    }

    @Override
    public void m_213609_(WorldGenLevel p_223983_, ChunkAccess p_223984_, StructureManager p_223985_) {
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        ChunkPos $$4 = p_223984_.m_7697_();
        int $$5 = $$4.f_45578_;
        int $$6 = $$4.f_45579_;
        for (int $$7 = 0; $$7 < 16; ++$$7) {
            for (int $$8 = 0; $$8 < 16; ++$$8) {
                int $$9 = SectionPos.m_175554_($$5, $$7);
                int $$10 = SectionPos.m_175554_($$6, $$8);
                p_223983_.m_7731_($$3.m_122178_($$9, 60, $$10), f_64113_, 2);
                BlockState $$11 = DebugLevelSource.m_64148_($$9, $$10);
                p_223983_.m_7731_($$3.m_122178_($$9, 70, $$10), $$11, 2);
            }
        }
    }

    @Override
    public CompletableFuture<ChunkAccess> m_213974_(Executor p_223991_, Blender p_223992_, RandomState p_223993_, StructureManager p_223994_, ChunkAccess p_223995_) {
        return CompletableFuture.completedFuture(p_223995_);
    }

    @Override
    public int m_214096_(int p_223964_, int p_223965_, Heightmap.Types p_223966_, LevelHeightAccessor p_223967_, RandomState p_223968_) {
        return 0;
    }

    @Override
    public NoiseColumn m_214184_(int p_223959_, int p_223960_, LevelHeightAccessor p_223961_, RandomState p_223962_) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void m_213600_(List<String> p_223987_, RandomState p_223988_, BlockPos p_223989_) {
    }

    public static BlockState m_64148_(int p_64149_, int p_64150_) {
        int $$3;
        BlockState $$2 = f_64112_;
        if (p_64149_ > 0 && p_64150_ > 0 && p_64149_ % 2 != 0 && p_64150_ % 2 != 0 && (p_64149_ /= 2) <= f_64115_ && (p_64150_ /= 2) <= f_64116_ && ($$3 = Mth.m_14040_(p_64149_ * f_64115_ + p_64150_)) < f_64114_.size()) {
            $$2 = f_64114_.get($$3);
        }
        return $$2;
    }

    @Override
    public void m_213679_(WorldGenRegion p_223970_, long p_223971_, RandomState p_223972_, BiomeManager p_223973_, StructureManager p_223974_, ChunkAccess p_223975_, GenerationStep.Carving p_223976_) {
    }

    @Override
    public void m_6929_(WorldGenRegion p_188511_) {
    }

    @Override
    public int m_142062_() {
        return 0;
    }

    @Override
    public int m_6331_() {
        return 384;
    }

    @Override
    public int m_6337_() {
        return 63;
    }
}

