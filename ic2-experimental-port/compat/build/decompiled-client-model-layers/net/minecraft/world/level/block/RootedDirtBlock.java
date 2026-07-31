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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RootedDirtBlock
extends Block
implements BonemealableBlock {
    public RootedDirtBlock(BlockBehaviour.Properties p_154359_) {
        super(p_154359_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_154366_, BlockPos p_154367_, BlockState p_154368_, boolean p_154369_) {
        return p_154366_.m_8055_(p_154367_.m_7495_()).m_60795_();
    }

    @Override
    public boolean m_214167_(Level p_221979_, RandomSource p_221980_, BlockPos p_221981_, BlockState p_221982_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221974_, RandomSource p_221975_, BlockPos p_221976_, BlockState p_221977_) {
        p_221974_.m_46597_(p_221976_.m_7495_(), Blocks.f_152548_.m_49966_());
    }
}

