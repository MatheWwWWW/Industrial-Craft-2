/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.CoralFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralTreeFeature
extends CoralFeature {
    public CoralTreeFeature(Codec<NoneFeatureConfiguration> p_65488_) {
        super(p_65488_);
    }

    @Override
    protected boolean m_214196_(LevelAccessor p_224987_, RandomSource p_224988_, BlockPos p_224989_, BlockState p_224990_) {
        BlockPos.MutableBlockPos $$4 = p_224989_.m_122032_();
        int $$5 = p_224988_.m_188503_(3) + 1;
        for (int $$6 = 0; $$6 < $$5; ++$$6) {
            if (!this.m_224973_(p_224987_, p_224988_, $$4, p_224990_)) {
                return true;
            }
            $$4.m_122173_(Direction.UP);
        }
        BlockPos $$7 = $$4.m_7949_();
        int $$8 = p_224988_.m_188503_(3) + 2;
        List<Direction> $$9 = Direction.Plane.HORIZONTAL.m_235694_(p_224988_);
        List<Direction> $$10 = $$9.subList(0, $$8);
        for (Direction $$11 : $$10) {
            $$4.m_122190_($$7);
            $$4.m_122173_($$11);
            int $$12 = p_224988_.m_188503_(5) + 2;
            int $$13 = 0;
            for (int $$14 = 0; $$14 < $$12 && this.m_224973_(p_224987_, p_224988_, $$4, p_224990_); ++$$14) {
                $$4.m_122173_(Direction.UP);
                if ($$14 != 0 && (++$$13 < 2 || !(p_224988_.m_188501_() < 0.25f))) continue;
                $$4.m_122173_($$11);
                $$13 = 0;
            }
        }
        return true;
    }
}

