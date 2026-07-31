/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.CoralFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralClawFeature
extends CoralFeature {
    public CoralClawFeature(Codec<NoneFeatureConfiguration> p_65422_) {
        super(p_65422_);
    }

    @Override
    protected boolean m_214196_(LevelAccessor p_224959_, RandomSource p_224960_, BlockPos p_224961_, BlockState p_224962_) {
        if (!this.m_224973_(p_224959_, p_224960_, p_224961_, p_224962_)) {
            return false;
        }
        Direction $$4 = Direction.Plane.HORIZONTAL.m_235690_(p_224960_);
        int $$5 = p_224960_.m_188503_(2) + 2;
        List<Direction> $$6 = Util.m_214661_(Stream.of($$4, $$4.m_122427_(), $$4.m_122428_()), p_224960_);
        List<Direction> $$7 = $$6.subList(0, $$5);
        block0: for (Direction $$8 : $$7) {
            int $$15;
            Direction $$14;
            BlockPos.MutableBlockPos $$9 = p_224961_.m_122032_();
            int $$10 = p_224960_.m_188503_(2) + 1;
            $$9.m_122173_($$8);
            if ($$8 == $$4) {
                Direction $$11 = $$4;
                int $$12 = p_224960_.m_188503_(3) + 2;
            } else {
                $$9.m_122173_(Direction.UP);
                Direction[] $$13 = new Direction[]{$$8, Direction.UP};
                $$14 = Util.m_214670_($$13, p_224960_);
                $$15 = p_224960_.m_188503_(3) + 3;
            }
            for (int $$16 = 0; $$16 < $$10 && this.m_224973_(p_224959_, p_224960_, $$9, p_224962_); ++$$16) {
                $$9.m_122173_($$14);
            }
            $$9.m_122173_($$14.m_122424_());
            $$9.m_122173_(Direction.UP);
            for (int $$17 = 0; $$17 < $$15; ++$$17) {
                $$9.m_122173_($$4);
                if (!this.m_224973_(p_224959_, p_224960_, $$9, p_224962_)) continue block0;
                if (!(p_224960_.m_188501_() < 0.25f)) continue;
                $$9.m_122173_(Direction.UP);
            }
        }
        return true;
    }
}

