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
import net.minecraft.world.level.block.grower.AzaleaTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AzaleaBlock
extends BushBlock
implements BonemealableBlock {
    private static final AzaleaTreeGrower f_152063_ = new AzaleaTreeGrower();
    private static final VoxelShape f_152064_ = Shapes.m_83110_(Block.m_49796_(0.0, 8.0, 0.0, 16.0, 16.0, 16.0), Block.m_49796_(6.0, 0.0, 6.0, 10.0, 8.0, 10.0));

    protected AzaleaBlock(BlockBehaviour.Properties p_152067_) {
        super(p_152067_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_152084_, BlockGetter p_152085_, BlockPos p_152086_, CollisionContext p_152087_) {
        return f_152064_;
    }

    @Override
    protected boolean m_6266_(BlockState p_152089_, BlockGetter p_152090_, BlockPos p_152091_) {
        return p_152089_.m_60713_(Blocks.f_50129_) || super.m_6266_(p_152089_, p_152090_, p_152091_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_152074_, BlockPos p_152075_, BlockState p_152076_, boolean p_152077_) {
        return p_152074_.m_6425_(p_152075_.m_7494_()).m_76178_();
    }

    @Override
    public boolean m_214167_(Level p_220712_, RandomSource p_220713_, BlockPos p_220714_, BlockState p_220715_) {
        return (double)p_220712_.f_46441_.m_188501_() < 0.45;
    }

    @Override
    public void m_214148_(ServerLevel p_220707_, RandomSource p_220708_, BlockPos p_220709_, BlockState p_220710_) {
        f_152063_.m_213817_(p_220707_, p_220707_.m_7726_().m_8481_(), p_220709_, p_220710_, p_220708_);
    }
}

