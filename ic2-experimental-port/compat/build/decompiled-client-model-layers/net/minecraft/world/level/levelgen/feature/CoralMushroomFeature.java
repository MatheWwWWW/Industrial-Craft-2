/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.CoralFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralMushroomFeature
extends CoralFeature {
    public CoralMushroomFeature(Codec<NoneFeatureConfiguration> p_65452_) {
        super(p_65452_);
    }

    @Override
    protected boolean m_214196_(LevelAccessor p_224982_, RandomSource p_224983_, BlockPos p_224984_, BlockState p_224985_) {
        int $$4 = p_224983_.m_188503_(3) + 3;
        int $$5 = p_224983_.m_188503_(3) + 3;
        int $$6 = p_224983_.m_188503_(3) + 3;
        int $$7 = p_224983_.m_188503_(3) + 1;
        BlockPos.MutableBlockPos $$8 = p_224984_.m_122032_();
        for (int $$9 = 0; $$9 <= $$5; ++$$9) {
            for (int $$10 = 0; $$10 <= $$4; ++$$10) {
                for (int $$11 = 0; $$11 <= $$6; ++$$11) {
                    $$8.m_122178_($$9 + p_224984_.m_123341_(), $$10 + p_224984_.m_123342_(), $$11 + p_224984_.m_123343_());
                    $$8.m_122175_(Direction.DOWN, $$7);
                    if (($$9 != 0 && $$9 != $$5 || $$10 != 0 && $$10 != $$4) && ($$11 != 0 && $$11 != $$6 || $$10 != 0 && $$10 != $$4) && ($$9 != 0 && $$9 != $$5 || $$11 != 0 && $$11 != $$6) && ($$9 == 0 || $$9 == $$5 || $$10 == 0 || $$10 == $$4 || $$11 == 0 || $$11 == $$6) && !(p_224983_.m_188501_() < 0.1f) && this.m_224973_(p_224982_, p_224983_, $$8, p_224985_)) continue;
                }
            }
        }
        return true;
    }
}

