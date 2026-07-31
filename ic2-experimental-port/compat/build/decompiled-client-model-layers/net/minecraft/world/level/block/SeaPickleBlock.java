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
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeaPickleBlock
extends BushBlock
implements BonemealableBlock,
SimpleWaterloggedBlock {
    public static final int f_154491_ = 4;
    public static final IntegerProperty f_56074_ = BlockStateProperties.f_61425_;
    public static final BooleanProperty f_56075_ = BlockStateProperties.f_61362_;
    protected static final VoxelShape f_56076_ = Block.m_49796_(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
    protected static final VoxelShape f_56077_ = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
    protected static final VoxelShape f_56078_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
    protected static final VoxelShape f_56079_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

    protected SeaPickleBlock(BlockBehaviour.Properties p_56082_) {
        super(p_56082_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_56074_, 1)).m_61124_(f_56075_, true));
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_56089_) {
        BlockState $$1 = p_56089_.m_43725_().m_8055_(p_56089_.m_8083_());
        if ($$1.m_60713_(this)) {
            return (BlockState)$$1.m_61124_(f_56074_, Math.min(4, $$1.m_61143_(f_56074_) + 1));
        }
        FluidState $$2 = p_56089_.m_43725_().m_6425_(p_56089_.m_8083_());
        boolean $$3 = $$2.m_76152_() == Fluids.f_76193_;
        return (BlockState)super.m_5573_(p_56089_).m_61124_(f_56075_, $$3);
    }

    public static boolean m_56132_(BlockState p_56133_) {
        return p_56133_.m_61143_(f_56075_) == false;
    }

    @Override
    protected boolean m_6266_(BlockState p_56127_, BlockGetter p_56128_, BlockPos p_56129_) {
        return !p_56127_.m_60812_(p_56128_, p_56129_).m_83263_(Direction.UP).m_83281_() || p_56127_.m_60783_(p_56128_, p_56129_, Direction.UP);
    }

    @Override
    public boolean m_7898_(BlockState p_56109_, LevelReader p_56110_, BlockPos p_56111_) {
        BlockPos $$3 = p_56111_.m_7495_();
        return this.m_6266_(p_56110_.m_8055_($$3), p_56110_, $$3);
    }

    @Override
    public BlockState m_7417_(BlockState p_56113_, Direction p_56114_, BlockState p_56115_, LevelAccessor p_56116_, BlockPos p_56117_, BlockPos p_56118_) {
        if (!p_56113_.m_60710_(p_56116_, p_56117_)) {
            return Blocks.f_50016_.m_49966_();
        }
        if (p_56113_.m_61143_(f_56075_).booleanValue()) {
            p_56116_.m_186469_(p_56117_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56116_));
        }
        return super.m_7417_(p_56113_, p_56114_, p_56115_, p_56116_, p_56117_, p_56118_);
    }

    @Override
    public boolean m_6864_(BlockState p_56101_, BlockPlaceContext p_56102_) {
        if (!p_56102_.m_7078_() && p_56102_.m_43722_().m_150930_(this.m_5456_()) && p_56101_.m_61143_(f_56074_) < 4) {
            return true;
        }
        return super.m_6864_(p_56101_, p_56102_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56122_, BlockGetter p_56123_, BlockPos p_56124_, CollisionContext p_56125_) {
        switch (p_56122_.m_61143_(f_56074_)) {
            default: {
                return f_56076_;
            }
            case 2: {
                return f_56077_;
            }
            case 3: {
                return f_56078_;
            }
            case 4: 
        }
        return f_56079_;
    }

    @Override
    public FluidState m_5888_(BlockState p_56131_) {
        if (p_56131_.m_61143_(f_56075_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_56131_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56120_) {
        p_56120_.m_61104_(f_56074_, f_56075_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_56091_, BlockPos p_56092_, BlockState p_56093_, boolean p_56094_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_222418_, RandomSource p_222419_, BlockPos p_222420_, BlockState p_222421_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_222413_, RandomSource p_222414_, BlockPos p_222415_, BlockState p_222416_) {
        if (!SeaPickleBlock.m_56132_(p_222416_) && p_222413_.m_8055_(p_222415_.m_7495_()).m_204336_(BlockTags.f_13051_)) {
            int $$4 = 5;
            int $$5 = 1;
            int $$6 = 2;
            int $$7 = 0;
            int $$8 = p_222415_.m_123341_() - 2;
            int $$9 = 0;
            for (int $$10 = 0; $$10 < 5; ++$$10) {
                for (int $$11 = 0; $$11 < $$5; ++$$11) {
                    int $$12 = 2 + p_222415_.m_123342_() - 1;
                    for (int $$13 = $$12 - 2; $$13 < $$12; ++$$13) {
                        BlockState $$15;
                        BlockPos $$14 = new BlockPos($$8 + $$10, $$13, p_222415_.m_123343_() - $$9 + $$11);
                        if ($$14 == p_222415_ || p_222414_.m_188503_(6) != 0 || !p_222413_.m_8055_($$14).m_60713_(Blocks.f_49990_) || !($$15 = p_222413_.m_8055_($$14.m_7495_())).m_204336_(BlockTags.f_13051_)) continue;
                        p_222413_.m_7731_($$14, (BlockState)Blocks.f_50567_.m_49966_().m_61124_(f_56074_, p_222414_.m_188503_(4) + 1), 3);
                    }
                }
                if ($$7 < 2) {
                    $$5 += 2;
                    ++$$9;
                } else {
                    $$5 -= 2;
                    --$$9;
                }
                ++$$7;
            }
            p_222413_.m_7731_(p_222415_, (BlockState)p_222416_.m_61124_(f_56074_, 4), 2);
        }
    }

    @Override
    public boolean m_7357_(BlockState p_56104_, BlockGetter p_56105_, BlockPos p_56106_, PathComputationType p_56107_) {
        return false;
    }
}

