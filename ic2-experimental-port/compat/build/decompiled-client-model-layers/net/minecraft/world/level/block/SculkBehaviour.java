/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public interface SculkBehaviour {
    public static final SculkBehaviour f_222023_ = new SculkBehaviour(){

        @Override
        public boolean m_214094_(LevelAccessor p_222048_, BlockPos p_222049_, BlockState p_222050_, @Nullable Collection<Direction> p_222051_, boolean p_222052_) {
            if (p_222051_ == null) {
                return ((SculkVeinBlock)Blocks.f_220856_).m_222395_().m_221657_(p_222048_.m_8055_(p_222049_), p_222048_, p_222049_, p_222052_) > 0L;
            }
            if (!p_222051_.isEmpty()) {
                if (p_222050_.m_60795_() || p_222050_.m_60819_().m_192917_(Fluids.f_76193_)) {
                    return SculkVeinBlock.m_222363_(p_222048_, p_222049_, p_222050_, p_222051_);
                }
                return false;
            }
            return SculkBehaviour.super.m_214094_(p_222048_, p_222049_, p_222050_, p_222051_, p_222052_);
        }

        @Override
        public int m_213628_(SculkSpreader.ChargeCursor p_222054_, LevelAccessor p_222055_, BlockPos p_222056_, RandomSource p_222057_, SculkSpreader p_222058_, boolean p_222059_) {
            return p_222054_.m_222344_() > 0 ? p_222054_.m_222341_() : 0;
        }

        @Override
        public int m_213670_(int p_222061_) {
            return Math.max(p_222061_ - 1, 0);
        }
    };

    default public byte m_222025_() {
        return 1;
    }

    default public void m_213805_(LevelAccessor p_222026_, BlockState p_222027_, BlockPos p_222028_, RandomSource p_222029_) {
    }

    default public boolean m_222030_(LevelAccessor p_222031_, BlockPos p_222032_, RandomSource p_222033_) {
        return false;
    }

    default public boolean m_214094_(LevelAccessor p_222034_, BlockPos p_222035_, BlockState p_222036_, @Nullable Collection<Direction> p_222037_, boolean p_222038_) {
        return ((MultifaceBlock)Blocks.f_220856_).m_213612_().m_221657_(p_222036_, p_222034_, p_222035_, p_222038_) > 0L;
    }

    default public boolean m_213999_() {
        return true;
    }

    default public int m_213670_(int p_222045_) {
        return 1;
    }

    public int m_213628_(SculkSpreader.ChargeCursor var1, LevelAccessor var2, BlockPos var3, RandomSource var4, SculkSpreader var5, boolean var6);
}

