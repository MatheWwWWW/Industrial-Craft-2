/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallGrassBlock
extends BushBlock
implements BonemealableBlock {
    protected static final float f_154739_ = 6.0f;
    protected static final VoxelShape f_57315_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    protected TallGrassBlock(BlockBehaviour.Properties p_57318_) {
        super(p_57318_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57336_, BlockGetter p_57337_, BlockPos p_57338_, CollisionContext p_57339_) {
        return f_57315_;
    }

    @Override
    public boolean m_7370_(BlockGetter p_57325_, BlockPos p_57326_, BlockState p_57327_, boolean p_57328_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_222583_, RandomSource p_222584_, BlockPos p_222585_, BlockState p_222586_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_222578_, RandomSource p_222579_, BlockPos p_222580_, BlockState p_222581_) {
        DoublePlantBlock $$4 = (DoublePlantBlock)(p_222581_.m_60713_(Blocks.f_50035_) ? Blocks.f_50360_ : Blocks.f_50359_);
        if ($$4.m_49966_().m_60710_(p_222578_, p_222580_) && p_222578_.m_46859_(p_222580_.m_7494_())) {
            DoublePlantBlock.m_153173_(p_222578_, $$4.m_49966_(), p_222580_, 2);
        }
    }
}

