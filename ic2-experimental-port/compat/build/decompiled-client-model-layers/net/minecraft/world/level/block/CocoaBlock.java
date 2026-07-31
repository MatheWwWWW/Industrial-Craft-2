/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CocoaBlock
extends HorizontalDirectionalBlock
implements BonemealableBlock {
    public static final int f_153068_ = 2;
    public static final IntegerProperty f_51736_ = BlockStateProperties.f_61406_;
    protected static final int f_153069_ = 4;
    protected static final int f_153070_ = 5;
    protected static final int f_153071_ = 2;
    protected static final int f_153072_ = 6;
    protected static final int f_153073_ = 7;
    protected static final int f_153074_ = 3;
    protected static final int f_153075_ = 8;
    protected static final int f_153076_ = 9;
    protected static final int f_153077_ = 4;
    protected static final VoxelShape[] f_51737_ = new VoxelShape[]{Block.m_49796_(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), Block.m_49796_(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), Block.m_49796_(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)};
    protected static final VoxelShape[] f_51738_ = new VoxelShape[]{Block.m_49796_(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), Block.m_49796_(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), Block.m_49796_(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)};
    protected static final VoxelShape[] f_51739_ = new VoxelShape[]{Block.m_49796_(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), Block.m_49796_(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), Block.m_49796_(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)};
    protected static final VoxelShape[] f_51740_ = new VoxelShape[]{Block.m_49796_(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), Block.m_49796_(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), Block.m_49796_(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)};

    public CocoaBlock(BlockBehaviour.Properties p_51743_) {
        super(p_51743_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_51736_, 0));
    }

    @Override
    public boolean m_6724_(BlockState p_51780_) {
        return p_51780_.m_61143_(f_51736_) < 2;
    }

    @Override
    public void m_213898_(BlockState p_221000_, ServerLevel p_221001_, BlockPos p_221002_, RandomSource p_221003_) {
        int $$4;
        if (p_221001_.f_46441_.m_188503_(5) == 0 && ($$4 = p_221000_.m_61143_(f_51736_).intValue()) < 2) {
            p_221001_.m_7731_(p_221002_, (BlockState)p_221000_.m_61124_(f_51736_, $$4 + 1), 2);
        }
    }

    @Override
    public boolean m_7898_(BlockState p_51767_, LevelReader p_51768_, BlockPos p_51769_) {
        BlockState $$3 = p_51768_.m_8055_(p_51769_.m_121945_(p_51767_.m_61143_(f_54117_)));
        return $$3.m_204336_(BlockTags.f_13111_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51787_, BlockGetter p_51788_, BlockPos p_51789_, CollisionContext p_51790_) {
        int $$4 = p_51787_.m_61143_(f_51736_);
        switch (p_51787_.m_61143_(f_54117_)) {
            case SOUTH: {
                return f_51740_[$$4];
            }
            default: {
                return f_51739_[$$4];
            }
            case WEST: {
                return f_51738_[$$4];
            }
            case EAST: 
        }
        return f_51737_[$$4];
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_51750_) {
        BlockState $$1 = this.m_49966_();
        Level $$2 = p_51750_.m_43725_();
        BlockPos $$3 = p_51750_.m_8083_();
        for (Direction $$4 : p_51750_.m_6232_()) {
            if (!$$4.m_122434_().m_122479_() || !($$1 = (BlockState)$$1.m_61124_(f_54117_, $$4)).m_60710_($$2, $$3)) continue;
            return $$1;
        }
        return null;
    }

    @Override
    public BlockState m_7417_(BlockState p_51771_, Direction p_51772_, BlockState p_51773_, LevelAccessor p_51774_, BlockPos p_51775_, BlockPos p_51776_) {
        if (p_51772_ == p_51771_.m_61143_(f_54117_) && !p_51771_.m_60710_(p_51774_, p_51775_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_51771_, p_51772_, p_51773_, p_51774_, p_51775_, p_51776_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_51752_, BlockPos p_51753_, BlockState p_51754_, boolean p_51755_) {
        return p_51754_.m_61143_(f_51736_) < 2;
    }

    @Override
    public boolean m_214167_(Level p_220995_, RandomSource p_220996_, BlockPos p_220997_, BlockState p_220998_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_220990_, RandomSource p_220991_, BlockPos p_220992_, BlockState p_220993_) {
        p_220990_.m_7731_(p_220992_, (BlockState)p_220993_.m_61124_(f_51736_, p_220993_.m_61143_(f_51736_) + 1), 2);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51778_) {
        p_51778_.m_61104_(f_54117_, f_51736_);
    }

    @Override
    public boolean m_7357_(BlockState p_51762_, BlockGetter p_51763_, BlockPos p_51764_, PathComputationType p_51765_) {
        return false;
    }
}

