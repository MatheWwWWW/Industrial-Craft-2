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

public class StraightTrunkPlacer
extends TrunkPlacer {
    public static final Codec<StraightTrunkPlacer> f_70245_ = RecordCodecBuilder.create(p_70261_ -> StraightTrunkPlacer.m_70305_(p_70261_).apply((Applicative)p_70261_, StraightTrunkPlacer::new));

    public StraightTrunkPlacer(int p_70248_, int p_70249_, int p_70250_) {
        super(p_70248_, p_70249_, p_70250_);
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_70315_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226147_, BiConsumer<BlockPos, BlockState> p_226148_, RandomSource p_226149_, int p_226150_, BlockPos p_226151_, TreeConfiguration p_226152_) {
        StraightTrunkPlacer.m_226169_(p_226147_, p_226148_, p_226149_, p_226151_.m_7495_(), p_226152_);
        for (int $$6 = 0; $$6 < p_226150_; ++$$6) {
            this.m_226187_(p_226147_, p_226148_, p_226149_, p_226151_.m_6630_($$6), p_226152_);
        }
        return ImmutableList.of((Object)new FoliagePlacer.FoliageAttachment(p_226151_.m_6630_(p_226150_), 0, false));
    }
}

