/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulSandBlock
extends Block {
    protected static final VoxelShape f_56669_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);
    private static final int f_154652_ = 20;

    public SoulSandBlock(BlockBehaviour.Properties p_56672_) {
        super(p_56672_);
    }

    @Override
    public VoxelShape m_5939_(BlockState p_56702_, BlockGetter p_56703_, BlockPos p_56704_, CollisionContext p_56705_) {
        return f_56669_;
    }

    @Override
    public VoxelShape m_7947_(BlockState p_56707_, BlockGetter p_56708_, BlockPos p_56709_) {
        return Shapes.m_83144_();
    }

    @Override
    public VoxelShape m_5909_(BlockState p_56684_, BlockGetter p_56685_, BlockPos p_56686_, CollisionContext p_56687_) {
        return Shapes.m_83144_();
    }

    @Override
    public void m_213897_(BlockState p_222457_, ServerLevel p_222458_, BlockPos p_222459_, RandomSource p_222460_) {
        BubbleColumnBlock.m_152707_(p_222458_, p_222459_.m_7494_(), p_222457_);
    }

    @Override
    public BlockState m_7417_(BlockState p_56689_, Direction p_56690_, BlockState p_56691_, LevelAccessor p_56692_, BlockPos p_56693_, BlockPos p_56694_) {
        if (p_56690_ == Direction.UP && p_56691_.m_60713_(Blocks.f_49990_)) {
            p_56692_.m_186460_(p_56693_, this, 20);
        }
        return super.m_7417_(p_56689_, p_56690_, p_56691_, p_56692_, p_56693_, p_56694_);
    }

    @Override
    public void m_6807_(BlockState p_56696_, Level p_56697_, BlockPos p_56698_, BlockState p_56699_, boolean p_56700_) {
        p_56697_.m_186460_(p_56698_, this, 20);
    }

    @Override
    public boolean m_7357_(BlockState p_56679_, BlockGetter p_56680_, BlockPos p_56681_, PathComputationType p_56682_) {
        return false;
    }

    @Override
    public float m_7749_(BlockState p_222462_, BlockGetter p_222463_, BlockPos p_222464_) {
        return 0.2f;
    }
}

