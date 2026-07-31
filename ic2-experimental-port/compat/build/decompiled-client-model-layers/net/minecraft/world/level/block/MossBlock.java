/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MossBlock
extends Block
implements BonemealableBlock {
    public MossBlock(BlockBehaviour.Properties p_153790_) {
        super(p_153790_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_153797_, BlockPos p_153798_, BlockState p_153799_, boolean p_153800_) {
        return p_153797_.m_8055_(p_153798_.m_7494_()).m_60795_();
    }

    @Override
    public boolean m_214167_(Level p_221538_, RandomSource p_221539_, BlockPos p_221540_, BlockState p_221541_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221533_, RandomSource p_221534_, BlockPos p_221535_, BlockState p_221536_) {
        CaveFeatures.f_194951_.m_203334_().m_224953_(p_221533_, p_221533_.m_7726_().m_8481_(), p_221534_, p_221535_.m_7494_());
    }
}

