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
import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FancyTrunkPlacer
extends TrunkPlacer {
    public static final Codec<FancyTrunkPlacer> f_70091_ = RecordCodecBuilder.create(p_70136_ -> FancyTrunkPlacer.m_70305_(p_70136_).apply((Applicative)p_70136_, FancyTrunkPlacer::new));
    private static final double f_161796_ = 0.618;
    private static final double f_161797_ = 1.382;
    private static final double f_161798_ = 0.381;
    private static final double f_161799_ = 0.328;

    public FancyTrunkPlacer(int p_70094_, int p_70095_, int p_70096_) {
        super(p_70094_, p_70095_, p_70096_);
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_70320_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226093_, BiConsumer<BlockPos, BlockState> p_226094_, RandomSource p_226095_, int p_226096_, BlockPos p_226097_, TreeConfiguration p_226098_) {
        int $$12;
        int $$6 = 5;
        int $$7 = p_226096_ + 2;
        int $$8 = Mth.m_14107_((double)$$7 * 0.618);
        FancyTrunkPlacer.m_226169_(p_226093_, p_226094_, p_226095_, p_226097_.m_7495_(), p_226098_);
        double $$9 = 1.0;
        int $$10 = Math.min(1, Mth.m_14107_(1.382 + Math.pow(1.0 * (double)$$7 / 13.0, 2.0)));
        int $$11 = p_226097_.m_123342_() + $$8;
        ArrayList $$13 = Lists.newArrayList();
        $$13.add(new FoliageCoords(p_226097_.m_6630_($$12), $$11));
        for ($$12 = $$7 - 5; $$12 >= 0; --$$12) {
            float $$14 = FancyTrunkPlacer.m_70132_($$7, $$12);
            if ($$14 < 0.0f) continue;
            for (int $$15 = 0; $$15 < $$10; ++$$15) {
                BlockPos $$22;
                double $$20;
                double $$18;
                double $$16 = 1.0;
                double $$17 = 1.0 * (double)$$14 * ((double)p_226095_.m_188501_() + 0.328);
                double $$19 = $$17 * Math.sin($$18 = (double)(p_226095_.m_188501_() * 2.0f) * Math.PI) + 0.5;
                BlockPos $$21 = p_226097_.m_7637_($$19, $$12 - 1, $$20 = $$17 * Math.cos($$18) + 0.5);
                if (!this.m_226107_(p_226093_, p_226094_, p_226095_, $$21, $$22 = $$21.m_6630_(5), false, p_226098_)) continue;
                int $$23 = p_226097_.m_123341_() - $$21.m_123341_();
                int $$24 = p_226097_.m_123343_() - $$21.m_123343_();
                double $$25 = (double)$$21.m_123342_() - Math.sqrt($$23 * $$23 + $$24 * $$24) * 0.381;
                int $$26 = $$25 > (double)$$11 ? $$11 : (int)$$25;
                BlockPos $$27 = new BlockPos(p_226097_.m_123341_(), $$26, p_226097_.m_123343_());
                if (!this.m_226107_(p_226093_, p_226094_, p_226095_, $$27, $$21, false, p_226098_)) continue;
                $$13.add(new FoliageCoords($$21, $$27.m_123342_()));
            }
        }
        this.m_226107_(p_226093_, p_226094_, p_226095_, p_226097_, p_226097_.m_6630_($$8), true, p_226098_);
        this.m_226099_(p_226093_, p_226094_, p_226095_, $$7, p_226097_, $$13, p_226098_);
        ArrayList $$28 = Lists.newArrayList();
        for (FoliageCoords $$29 : $$13) {
            if (!this.m_70098_($$7, $$29.m_70142_() - p_226097_.m_123342_())) continue;
            $$28.add($$29.f_70137_);
        }
        return $$28;
    }

    private boolean m_226107_(LevelSimulatedReader p_226108_, BiConsumer<BlockPos, BlockState> p_226109_, RandomSource p_226110_, BlockPos p_226111_, BlockPos p_226112_, boolean p_226113_, TreeConfiguration p_226114_) {
        if (!p_226113_ && Objects.equals(p_226111_, p_226112_)) {
            return true;
        }
        BlockPos $$7 = p_226112_.m_7918_(-p_226111_.m_123341_(), -p_226111_.m_123342_(), -p_226111_.m_123343_());
        int $$8 = this.m_70127_($$7);
        float $$9 = (float)$$7.m_123341_() / (float)$$8;
        float $$10 = (float)$$7.m_123342_() / (float)$$8;
        float $$11 = (float)$$7.m_123343_() / (float)$$8;
        for (int $$12 = 0; $$12 <= $$8; ++$$12) {
            BlockPos $$13 = p_226111_.m_7637_(0.5f + (float)$$12 * $$9, 0.5f + (float)$$12 * $$10, 0.5f + (float)$$12 * $$11);
            if (p_226113_) {
                this.m_226175_(p_226108_, p_226109_, p_226110_, $$13, p_226114_, p_161826_ -> (BlockState)p_161826_.m_61124_(RotatedPillarBlock.f_55923_, this.m_70129_(p_226111_, $$13)));
                continue;
            }
            if (this.m_226184_(p_226108_, $$13)) continue;
            return false;
        }
        return true;
    }

    private int m_70127_(BlockPos p_70128_) {
        int $$1 = Mth.m_14040_(p_70128_.m_123341_());
        int $$2 = Mth.m_14040_(p_70128_.m_123342_());
        int $$3 = Mth.m_14040_(p_70128_.m_123343_());
        return Math.max($$1, Math.max($$2, $$3));
    }

    private Direction.Axis m_70129_(BlockPos p_70130_, BlockPos p_70131_) {
        int $$4;
        Direction.Axis $$2 = Direction.Axis.Y;
        int $$3 = Math.abs(p_70131_.m_123341_() - p_70130_.m_123341_());
        int $$5 = Math.max($$3, $$4 = Math.abs(p_70131_.m_123343_() - p_70130_.m_123343_()));
        if ($$5 > 0) {
            $$2 = $$3 == $$5 ? Direction.Axis.X : Direction.Axis.Z;
        }
        return $$2;
    }

    private boolean m_70098_(int p_70099_, int p_70100_) {
        return (double)p_70100_ >= (double)p_70099_ * 0.2;
    }

    private void m_226099_(LevelSimulatedReader p_226100_, BiConsumer<BlockPos, BlockState> p_226101_, RandomSource p_226102_, int p_226103_, BlockPos p_226104_, List<FoliageCoords> p_226105_, TreeConfiguration p_226106_) {
        for (FoliageCoords $$7 : p_226105_) {
            int $$8 = $$7.m_70142_();
            BlockPos $$9 = new BlockPos(p_226104_.m_123341_(), $$8, p_226104_.m_123343_());
            if ($$9.equals($$7.f_70137_.m_161451_()) || !this.m_70098_(p_226103_, $$8 - p_226104_.m_123342_())) continue;
            this.m_226107_(p_226100_, p_226101_, p_226102_, $$9, $$7.f_70137_.m_161451_(), true, p_226106_);
        }
    }

    private static float m_70132_(int p_70133_, int p_70134_) {
        if ((float)p_70134_ < (float)p_70133_ * 0.3f) {
            return -1.0f;
        }
        float $$2 = (float)p_70133_ / 2.0f;
        float $$3 = $$2 - (float)p_70134_;
        float $$4 = Mth.m_14116_($$2 * $$2 - $$3 * $$3);
        if ($$3 == 0.0f) {
            $$4 = $$2;
        } else if (Math.abs($$3) >= $$2) {
            return 0.0f;
        }
        return $$4 * 0.5f;
    }

    static class FoliageCoords {
        final FoliagePlacer.FoliageAttachment f_70137_;
        private final int f_70138_;

        public FoliageCoords(BlockPos p_70140_, int p_70141_) {
            this.f_70137_ = new FoliagePlacer.FoliageAttachment(p_70140_, 0, false);
            this.f_70138_ = p_70141_;
        }

        public int m_70142_() {
            return this.f_70138_;
        }
    }
}

