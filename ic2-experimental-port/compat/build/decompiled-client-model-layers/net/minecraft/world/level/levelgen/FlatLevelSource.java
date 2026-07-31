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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class FlatLevelSource
extends ChunkGenerator {
    public static final Codec<FlatLevelSource> f_64164_ = RecordCodecBuilder.create(p_204551_ -> FlatLevelSource.m_208005_(p_204551_).and((App)FlatLevelGeneratorSettings.f_70347_.fieldOf("settings").forGetter(FlatLevelSource::m_64191_)).apply((Applicative)p_204551_, p_204551_.stable(FlatLevelSource::new)));
    private final FlatLevelGeneratorSettings f_64165_;

    public FlatLevelSource(Registry<StructureSet> p_209099_, FlatLevelGeneratorSettings p_209100_) {
        super(p_209099_, p_209100_.m_209810_(), new FixedBiomeSource(p_209100_.m_204921_()), Util.m_143827_(p_209100_::m_226294_));
        this.f_64165_ = p_209100_;
    }

    @Override
    protected Codec<? extends ChunkGenerator> m_6909_() {
        return f_64164_;
    }

    public FlatLevelGeneratorSettings m_64191_() {
        return this.f_64165_;
    }

    @Override
    public void m_214194_(WorldGenRegion p_224174_, StructureManager p_224175_, RandomState p_224176_, ChunkAccess p_224177_) {
    }

    @Override
    public int m_142051_(LevelHeightAccessor p_158279_) {
        return p_158279_.m_141937_() + Math.min(p_158279_.m_141928_(), this.f_64165_.m_161917_().size());
    }

    @Override
    public CompletableFuture<ChunkAccess> m_213974_(Executor p_224183_, Blender p_224184_, RandomState p_224185_, StructureManager p_224186_, ChunkAccess p_224187_) {
        List<BlockState> $$5 = this.f_64165_.m_161917_();
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        Heightmap $$7 = p_224187_.m_6005_(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap $$8 = p_224187_.m_6005_(Heightmap.Types.WORLD_SURFACE_WG);
        for (int $$9 = 0; $$9 < Math.min(p_224187_.m_141928_(), $$5.size()); ++$$9) {
            BlockState $$10 = $$5.get($$9);
            if ($$10 == null) continue;
            int $$11 = p_224187_.m_141937_() + $$9;
            for (int $$12 = 0; $$12 < 16; ++$$12) {
                for (int $$13 = 0; $$13 < 16; ++$$13) {
                    p_224187_.m_6978_($$6.m_122178_($$12, $$11, $$13), $$10, false);
                    $$7.m_64249_($$12, $$11, $$13, $$10);
                    $$8.m_64249_($$12, $$11, $$13, $$10);
                }
            }
        }
        return CompletableFuture.completedFuture(p_224187_);
    }

    @Override
    public int m_214096_(int p_224160_, int p_224161_, Heightmap.Types p_224162_, LevelHeightAccessor p_224163_, RandomState p_224164_) {
        List<BlockState> $$5 = this.f_64165_.m_161917_();
        for (int $$6 = Math.min($$5.size(), p_224163_.m_151558_()) - 1; $$6 >= 0; --$$6) {
            BlockState $$7 = $$5.get($$6);
            if ($$7 == null || !p_224162_.m_64299_().test($$7)) continue;
            return p_224163_.m_141937_() + $$6 + 1;
        }
        return p_224163_.m_141937_();
    }

    @Override
    public NoiseColumn m_214184_(int p_224155_, int p_224156_, LevelHeightAccessor p_224157_, RandomState p_224158_) {
        return new NoiseColumn(p_224157_.m_141937_(), (BlockState[])this.f_64165_.m_161917_().stream().limit(p_224157_.m_141928_()).map(p_204549_ -> p_204549_ == null ? Blocks.f_50016_.m_49966_() : p_204549_).toArray(BlockState[]::new));
    }

    @Override
    public void m_213600_(List<String> p_224179_, RandomState p_224180_, BlockPos p_224181_) {
    }

    @Override
    public void m_213679_(WorldGenRegion p_224166_, long p_224167_, RandomState p_224168_, BiomeManager p_224169_, StructureManager p_224170_, ChunkAccess p_224171_, GenerationStep.Carving p_224172_) {
    }

    @Override
    public void m_6929_(WorldGenRegion p_188545_) {
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
        return -63;
    }
}

