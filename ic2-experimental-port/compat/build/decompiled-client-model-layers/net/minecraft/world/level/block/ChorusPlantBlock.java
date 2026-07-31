/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class ChorusPlantBlock
extends PipeBlock {
    protected ChorusPlantBlock(BlockBehaviour.Properties p_51707_) {
        super(0.3125f, p_51707_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55148_, false)).m_61124_(f_55149_, false)).m_61124_(f_55150_, false)).m_61124_(f_55151_, false)).m_61124_(f_55152_, false)).m_61124_(f_55153_, false));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_51709_) {
        return this.m_51710_(p_51709_.m_43725_(), p_51709_.m_8083_());
    }

    public BlockState m_51710_(BlockGetter p_51711_, BlockPos p_51712_) {
        BlockState $$2 = p_51711_.m_8055_(p_51712_.m_7495_());
        BlockState $$3 = p_51711_.m_8055_(p_51712_.m_7494_());
        BlockState $$4 = p_51711_.m_8055_(p_51712_.m_122012_());
        BlockState $$5 = p_51711_.m_8055_(p_51712_.m_122029_());
        BlockState $$6 = p_51711_.m_8055_(p_51712_.m_122019_());
        BlockState $$7 = p_51711_.m_8055_(p_51712_.m_122024_());
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_55153_, $$2.m_60713_(this) || $$2.m_60713_(Blocks.f_50491_) || $$2.m_60713_(Blocks.f_50259_))).m_61124_(f_55152_, $$3.m_60713_(this) || $$3.m_60713_(Blocks.f_50491_))).m_61124_(f_55148_, $$4.m_60713_(this) || $$4.m_60713_(Blocks.f_50491_))).m_61124_(f_55149_, $$5.m_60713_(this) || $$5.m_60713_(Blocks.f_50491_))).m_61124_(f_55150_, $$6.m_60713_(this) || $$6.m_60713_(Blocks.f_50491_))).m_61124_(f_55151_, $$7.m_60713_(this) || $$7.m_60713_(Blocks.f_50491_));
    }

    @Override
    public BlockState m_7417_(BlockState p_51728_, Direction p_51729_, BlockState p_51730_, LevelAccessor p_51731_, BlockPos p_51732_, BlockPos p_51733_) {
        if (!p_51728_.m_60710_(p_51731_, p_51732_)) {
            p_51731_.m_186460_(p_51732_, this, 1);
            return super.m_7417_(p_51728_, p_51729_, p_51730_, p_51731_, p_51732_, p_51733_);
        }
        boolean $$6 = p_51730_.m_60713_(this) || p_51730_.m_60713_(Blocks.f_50491_) || p_51729_ == Direction.DOWN && p_51730_.m_60713_(Blocks.f_50259_);
        return (BlockState)p_51728_.m_61124_((Property)f_55154_.get(p_51729_), $$6);
    }

    @Override
    public void m_213897_(BlockState p_220985_, ServerLevel p_220986_, BlockPos p_220987_, RandomSource p_220988_) {
        if (!p_220985_.m_60710_(p_220986_, p_220987_)) {
            p_220986_.m_46961_(p_220987_, true);
        }
    }

    @Override
    public boolean m_7898_(BlockState p_51724_, LevelReader p_51725_, BlockPos p_51726_) {
        BlockState $$3 = p_51725_.m_8055_(p_51726_.m_7495_());
        boolean $$4 = !p_51725_.m_8055_(p_51726_.m_7494_()).m_60795_() && !$$3.m_60795_();
        for (Direction $$5 : Direction.Plane.HORIZONTAL) {
            BlockPos $$6 = p_51726_.m_121945_($$5);
            BlockState $$7 = p_51725_.m_8055_($$6);
            if (!$$7.m_60713_(this)) continue;
            if ($$4) {
                return false;
            }
            BlockState $$8 = p_51725_.m_8055_($$6.m_7495_());
            if (!$$8.m_60713_(this) && !$$8.m_60713_(Blocks.f_50259_)) continue;
            return true;
        }
        return $$3.m_60713_(this) || $$3.m_60713_(Blocks.f_50259_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51735_) {
        p_51735_.m_61104_(f_55148_, f_55149_, f_55150_, f_55151_, f_55152_, f_55153_);
    }

    @Override
    public boolean m_7357_(BlockState p_51719_, BlockGetter p_51720_, BlockPos p_51721_, PathComputationType p_51722_) {
        return false;
    }
}

