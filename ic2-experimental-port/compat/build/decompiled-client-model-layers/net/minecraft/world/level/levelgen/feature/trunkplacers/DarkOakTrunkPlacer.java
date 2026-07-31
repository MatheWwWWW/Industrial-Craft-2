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
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class DarkOakTrunkPlacer
extends TrunkPlacer {
    public static final Codec<DarkOakTrunkPlacer> f_70074_ = RecordCodecBuilder.create(p_70090_ -> DarkOakTrunkPlacer.m_70305_(p_70090_).apply((Applicative)p_70090_, DarkOakTrunkPlacer::new));

    public DarkOakTrunkPlacer(int p_70077_, int p_70078_, int p_70079_) {
        super(p_70077_, p_70078_, p_70079_);
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_70319_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226086_, BiConsumer<BlockPos, BlockState> p_226087_, RandomSource p_226088_, int p_226089_, BlockPos p_226090_, TreeConfiguration p_226091_) {
        ArrayList $$6 = Lists.newArrayList();
        BlockPos $$7 = p_226090_.m_7495_();
        DarkOakTrunkPlacer.m_226169_(p_226086_, p_226087_, p_226088_, $$7, p_226091_);
        DarkOakTrunkPlacer.m_226169_(p_226086_, p_226087_, p_226088_, $$7.m_122029_(), p_226091_);
        DarkOakTrunkPlacer.m_226169_(p_226086_, p_226087_, p_226088_, $$7.m_122019_(), p_226091_);
        DarkOakTrunkPlacer.m_226169_(p_226086_, p_226087_, p_226088_, $$7.m_122019_().m_122029_(), p_226091_);
        Direction $$8 = Direction.Plane.HORIZONTAL.m_235690_(p_226088_);
        int $$9 = p_226089_ - p_226088_.m_188503_(4);
        int $$10 = 2 - p_226088_.m_188503_(3);
        int $$11 = p_226090_.m_123341_();
        int $$12 = p_226090_.m_123342_();
        int $$13 = p_226090_.m_123343_();
        int $$14 = $$11;
        int $$15 = $$13;
        int $$16 = $$12 + p_226089_ - 1;
        for (int $$17 = 0; $$17 < p_226089_; ++$$17) {
            int $$18;
            BlockPos $$19;
            if ($$17 >= $$9 && $$10 > 0) {
                $$14 += $$8.m_122429_();
                $$15 += $$8.m_122431_();
                --$$10;
            }
            if (!TreeFeature.m_67267_(p_226086_, $$19 = new BlockPos($$14, $$18 = $$12 + $$17, $$15))) continue;
            this.m_226187_(p_226086_, p_226087_, p_226088_, $$19, p_226091_);
            this.m_226187_(p_226086_, p_226087_, p_226088_, $$19.m_122029_(), p_226091_);
            this.m_226187_(p_226086_, p_226087_, p_226088_, $$19.m_122019_(), p_226091_);
            this.m_226187_(p_226086_, p_226087_, p_226088_, $$19.m_122029_().m_122019_(), p_226091_);
        }
        $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$14, $$16, $$15), 0, true));
        for (int $$20 = -1; $$20 <= 2; ++$$20) {
            for (int $$21 = -1; $$21 <= 2; ++$$21) {
                if ($$20 >= 0 && $$20 <= 1 && $$21 >= 0 && $$21 <= 1 || p_226088_.m_188503_(3) > 0) continue;
                int $$22 = p_226088_.m_188503_(3) + 2;
                for (int $$23 = 0; $$23 < $$22; ++$$23) {
                    this.m_226187_(p_226086_, p_226087_, p_226088_, new BlockPos($$11 + $$20, $$16 - $$23 - 1, $$13 + $$21), p_226091_);
                }
                $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$14 + $$20, $$16, $$15 + $$21), 0, false));
            }
        }
        return $$6;
    }
}

