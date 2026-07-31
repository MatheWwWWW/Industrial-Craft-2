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
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ForkingTrunkPlacer
extends TrunkPlacer {
    public static final Codec<ForkingTrunkPlacer> f_70145_ = RecordCodecBuilder.create(p_70161_ -> ForkingTrunkPlacer.m_70305_(p_70161_).apply((Applicative)p_70161_, ForkingTrunkPlacer::new));

    public ForkingTrunkPlacer(int p_70148_, int p_70149_, int p_70150_) {
        super(p_70148_, p_70149_, p_70150_);
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_70316_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226116_, BiConsumer<BlockPos, BlockState> p_226117_, RandomSource p_226118_, int p_226119_, BlockPos p_226120_, TreeConfiguration p_226121_) {
        ForkingTrunkPlacer.m_226169_(p_226116_, p_226117_, p_226118_, p_226120_.m_7495_(), p_226121_);
        ArrayList $$6 = Lists.newArrayList();
        Direction $$7 = Direction.Plane.HORIZONTAL.m_235690_(p_226118_);
        int $$8 = p_226119_ - p_226118_.m_188503_(4) - 1;
        int $$9 = 3 - p_226118_.m_188503_(3);
        BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
        int $$11 = p_226120_.m_123341_();
        int $$12 = p_226120_.m_123343_();
        OptionalInt $$13 = OptionalInt.empty();
        for (int $$14 = 0; $$14 < p_226119_; ++$$14) {
            int $$15 = p_226120_.m_123342_() + $$14;
            if ($$14 >= $$8 && $$9 > 0) {
                $$11 += $$7.m_122429_();
                $$12 += $$7.m_122431_();
                --$$9;
            }
            if (!this.m_226187_(p_226116_, p_226117_, p_226118_, $$10.m_122178_($$11, $$15, $$12), p_226121_)) continue;
            $$13 = OptionalInt.of($$15 + 1);
        }
        if ($$13.isPresent()) {
            $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$11, $$13.getAsInt(), $$12), 1, false));
        }
        $$11 = p_226120_.m_123341_();
        $$12 = p_226120_.m_123343_();
        Direction $$16 = Direction.Plane.HORIZONTAL.m_235690_(p_226118_);
        if ($$16 != $$7) {
            int $$17 = $$8 - p_226118_.m_188503_(2) - 1;
            int $$18 = 1 + p_226118_.m_188503_(3);
            $$13 = OptionalInt.empty();
            for (int $$19 = $$17; $$19 < p_226119_ && $$18 > 0; ++$$19, --$$18) {
                if ($$19 < 1) continue;
                int $$20 = p_226120_.m_123342_() + $$19;
                if (!this.m_226187_(p_226116_, p_226117_, p_226118_, $$10.m_122178_($$11 += $$16.m_122429_(), $$20, $$12 += $$16.m_122431_()), p_226121_)) continue;
                $$13 = OptionalInt.of($$20 + 1);
            }
            if ($$13.isPresent()) {
                $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$11, $$13.getAsInt(), $$12), 0, false));
            }
        }
        return $$6;
    }
}

