/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public class SculkBlock
extends DropExperienceBlock
implements SculkBehaviour {
    public SculkBlock(BlockBehaviour.Properties p_222063_) {
        super(p_222063_, ConstantInt.m_146483_(1));
    }

    @Override
    public int m_213628_(SculkSpreader.ChargeCursor p_222073_, LevelAccessor p_222074_, BlockPos p_222075_, RandomSource p_222076_, SculkSpreader p_222077_, boolean p_222078_) {
        int $$6 = p_222073_.m_222341_();
        if ($$6 == 0 || p_222076_.m_188503_(p_222077_.m_222280_()) != 0) {
            return $$6;
        }
        BlockPos $$7 = p_222073_.m_222304_();
        boolean $$8 = $$7.m_123314_(p_222075_, p_222077_.m_222279_());
        if ($$8 || !SculkBlock.m_222064_(p_222074_, $$7)) {
            if (p_222076_.m_188503_(p_222077_.m_222281_()) != 0) {
                return $$6;
            }
            return $$6 - ($$8 ? 1 : SculkBlock.m_222079_(p_222077_, $$7, p_222075_, $$6));
        }
        int $$9 = p_222077_.m_222278_();
        if (p_222076_.m_188503_($$9) < $$6) {
            BlockPos $$10 = $$7.m_7494_();
            BlockState $$11 = this.m_222067_(p_222074_, $$10, p_222076_, p_222077_.m_222282_());
            p_222074_.m_7731_($$10, $$11, 3);
            p_222074_.m_5594_(null, $$7, $$11.m_60827_().m_56777_(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        return Math.max(0, $$6 - $$9);
    }

    private static int m_222079_(SculkSpreader p_222080_, BlockPos p_222081_, BlockPos p_222082_, int p_222083_) {
        int $$4 = p_222080_.m_222279_();
        float $$5 = Mth.m_14207_((float)Math.sqrt(p_222081_.m_123331_(p_222082_)) - (float)$$4);
        int $$6 = Mth.m_144944_(24 - $$4);
        float $$7 = Math.min(1.0f, $$5 / (float)$$6);
        return Math.max(1, (int)((float)p_222083_ * $$7 * 0.5f));
    }

    private BlockState m_222067_(LevelAccessor p_222068_, BlockPos p_222069_, RandomSource p_222070_, boolean p_222071_) {
        BlockState $$5;
        if (p_222070_.m_188503_(11) == 0) {
            BlockState $$4 = (BlockState)Blocks.f_220858_.m_49966_().m_61124_(SculkShriekerBlock.f_222154_, p_222071_);
        } else {
            $$5 = Blocks.f_152500_.m_49966_();
        }
        if ($$5.m_61138_(BlockStateProperties.f_61362_) && !p_222068_.m_6425_(p_222069_).m_76178_()) {
            return (BlockState)$$5.m_61124_(BlockStateProperties.f_61362_, true);
        }
        return $$5;
    }

    private static boolean m_222064_(LevelAccessor p_222065_, BlockPos p_222066_) {
        BlockState $$2 = p_222065_.m_8055_(p_222066_.m_7494_());
        if (!($$2.m_60795_() || $$2.m_60713_(Blocks.f_49990_) && $$2.m_60819_().m_192917_(Fluids.f_76193_))) {
            return false;
        }
        int $$3 = 0;
        for (BlockPos $$4 : BlockPos.m_121940_(p_222066_.m_7918_(-4, 0, -4), p_222066_.m_7918_(4, 2, 4))) {
            BlockState $$5 = p_222065_.m_8055_($$4);
            if ($$5.m_60713_(Blocks.f_152500_) || $$5.m_60713_(Blocks.f_220858_)) {
                ++$$3;
            }
            if ($$3 <= 2) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean m_213999_() {
        return false;
    }
}

