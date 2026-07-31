/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MangroveLeavesBlock
extends LeavesBlock
implements BonemealableBlock {
    public MangroveLeavesBlock(BlockBehaviour.Properties p_221425_) {
        super(p_221425_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_221432_, BlockPos p_221433_, BlockState p_221434_, boolean p_221435_) {
        return p_221432_.m_8055_(p_221433_.m_7495_()).m_60795_();
    }

    @Override
    public boolean m_214167_(Level p_221437_, RandomSource p_221438_, BlockPos p_221439_, BlockState p_221440_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221427_, RandomSource p_221428_, BlockPos p_221429_, BlockState p_221430_) {
        p_221427_.m_7731_(p_221429_.m_7495_(), MangrovePropaguleBlock.m_221492_(), 2);
    }
}

