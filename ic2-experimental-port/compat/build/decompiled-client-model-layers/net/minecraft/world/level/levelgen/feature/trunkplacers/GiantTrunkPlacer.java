/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class GiantTrunkPlacer
extends TrunkPlacer {
    public static final Codec<GiantTrunkPlacer> f_70162_ = RecordCodecBuilder.create(p_70189_ -> GiantTrunkPlacer.m_70305_(p_70189_).apply((Applicative)p_70189_, GiantTrunkPlacer::new));

    public GiantTrunkPlacer(int p_70165_, int p_70166_, int p_70167_) {
        super(p_70165_, p_70166_, p_70167_);
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_70317_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226123_, BiConsumer<BlockPos, BlockState> p_226124_, RandomSource p_226125_, int p_226126_, BlockPos p_226127_, TreeConfiguration p_226128_) {
        BlockPos $$6 = p_226127_.m_7495_();
        GiantTrunkPlacer.m_226169_(p_226123_, p_226124_, p_226125_, $$6, p_226128_);
        GiantTrunkPlacer.m_226169_(p_226123_, p_226124_, p_226125_, $$6.m_122029_(), p_226128_);
        GiantTrunkPlacer.m_226169_(p_226123_, p_226124_, p_226125_, $$6.m_122019_(), p_226128_);
        GiantTrunkPlacer.m_226169_(p_226123_, p_226124_, p_226125_, $$6.m_122019_().m_122029_(), p_226128_);
        BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
        for (int $$8 = 0; $$8 < p_226126_; ++$$8) {
            this.m_226129_(p_226123_, p_226124_, p_226125_, $$7, p_226128_, p_226127_, 0, $$8, 0);
            if ($$8 >= p_226126_ - 1) continue;
            this.m_226129_(p_226123_, p_226124_, p_226125_, $$7, p_226128_, p_226127_, 1, $$8, 0);
            this.m_226129_(p_226123_, p_226124_, p_226125_, $$7, p_226128_, p_226127_, 1, $$8, 1);
            this.m_226129_(p_226123_, p_226124_, p_226125_, $$7, p_226128_, p_226127_, 0, $$8, 1);
        }
        return ImmutableList.of((Object)new FoliagePlacer.FoliageAttachment(p_226127_.m_6630_(p_226126_), 0, true));
    }

    private void m_226129_(LevelSimulatedReader p_226130_, BiConsumer<BlockPos, BlockState> p_226131_, RandomSource p_226132_, BlockPos.MutableBlockPos p_226133_, TreeConfiguration p_226134_, BlockPos p_226135_, int p_226136_, int p_226137_, int p_226138_) {
        p_226133_.m_122154_(p_226135_, p_226136_, p_226137_, p_226138_);
        this.m_226163_(p_226130_, p_226131_, p_226132_, p_226133_, p_226134_);
    }
}

