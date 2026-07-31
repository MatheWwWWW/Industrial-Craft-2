/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class MegaJungleTrunkPlacer
extends GiantTrunkPlacer {
    public static final Codec<MegaJungleTrunkPlacer> f_70190_ = RecordCodecBuilder.create(p_70206_ -> MegaJungleTrunkPlacer.m_70305_(p_70206_).apply((Applicative)p_70206_, MegaJungleTrunkPlacer::new));

    public MegaJungleTrunkPlacer(int p_70193_, int p_70194_, int p_70195_) {
        super(p_70193_, p_70194_, p_70195_);
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_70318_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226140_, BiConsumer<BlockPos, BlockState> p_226141_, RandomSource p_226142_, int p_226143_, BlockPos p_226144_, TreeConfiguration p_226145_) {
        ArrayList $$6 = Lists.newArrayList();
        $$6.addAll(super.m_213934_(p_226140_, p_226141_, p_226142_, p_226143_, p_226144_, p_226145_));
        for (int $$7 = p_226143_ - 2 - p_226142_.m_188503_(4); $$7 > p_226143_ / 2; $$7 -= 2 + p_226142_.m_188503_(4)) {
            float $$8 = p_226142_.m_188501_() * ((float)Math.PI * 2);
            int $$9 = 0;
            int $$10 = 0;
            for (int $$11 = 0; $$11 < 5; ++$$11) {
                $$9 = (int)(1.5f + Mth.m_14089_($$8) * (float)$$11);
                $$10 = (int)(1.5f + Mth.m_14031_($$8) * (float)$$11);
                BlockPos $$12 = p_226144_.m_7918_($$9, $$7 - 3 + $$11 / 2, $$10);
                this.m_226187_(p_226140_, p_226141_, p_226142_, $$12, p_226145_);
            }
            $$6.add(new FoliagePlacer.FoliageAttachment(p_226144_.m_7918_($$9, $$7, $$10), -2, false));
        }
        return $$6;
    }
}

