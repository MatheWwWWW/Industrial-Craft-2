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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LanternBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_153459_ = BlockStateProperties.f_61435_;
    public static final BooleanProperty f_153460_ = BlockStateProperties.f_61362_;
    protected static final VoxelShape f_153461_ = Shapes.m_83110_(Block.m_49796_(5.0, 0.0, 5.0, 11.0, 7.0, 11.0), Block.m_49796_(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
    protected static final VoxelShape f_153462_ = Shapes.m_83110_(Block.m_49796_(5.0, 1.0, 5.0, 11.0, 8.0, 11.0), Block.m_49796_(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));

    public LanternBlock(BlockBehaviour.Properties p_153465_) {
        super(p_153465_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_153459_, false)).m_61124_(f_153460_, false));
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_153467_) {
        FluidState $$1 = p_153467_.m_43725_().m_6425_(p_153467_.m_8083_());
        for (Direction $$2 : p_153467_.m_6232_()) {
            BlockState $$3;
            if ($$2.m_122434_() != Direction.Axis.Y || !($$3 = (BlockState)this.m_49966_().m_61124_(f_153459_, $$2 == Direction.UP)).m_60710_(p_153467_.m_43725_(), p_153467_.m_8083_())) continue;
            return (BlockState)$$3.m_61124_(f_153460_, $$1.m_76152_() == Fluids.f_76193_);
        }
        return null;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_153474_, BlockGetter p_153475_, BlockPos p_153476_, CollisionContext p_153477_) {
        return p_153474_.m_61143_(f_153459_) != false ? f_153462_ : f_153461_;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153490_) {
        p_153490_.m_61104_(f_153459_, f_153460_);
    }

    @Override
    public boolean m_7898_(BlockState p_153479_, LevelReader p_153480_, BlockPos p_153481_) {
        Direction $$3 = LanternBlock.m_153495_(p_153479_).m_122424_();
        return Block.m_49863_(p_153480_, p_153481_.m_121945_($$3), $$3.m_122424_());
    }

    protected static Direction m_153495_(BlockState p_153496_) {
        return p_153496_.m_61143_(f_153459_) != false ? Direction.DOWN : Direction.UP;
    }

    @Override
    public PushReaction m_5537_(BlockState p_153494_) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState m_7417_(BlockState p_153483_, Direction p_153484_, BlockState p_153485_, LevelAccessor p_153486_, BlockPos p_153487_, BlockPos p_153488_) {
        if (p_153483_.m_61143_(f_153460_).booleanValue()) {
            p_153486_.m_186469_(p_153487_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153486_));
        }
        if (LanternBlock.m_153495_(p_153483_).m_122424_() == p_153484_ && !p_153483_.m_60710_(p_153486_, p_153487_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_153483_, p_153484_, p_153485_, p_153486_, p_153487_, p_153488_);
    }

    @Override
    public FluidState m_5888_(BlockState p_153492_) {
        if (p_153492_.m_61143_(f_153460_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_153492_);
    }

    @Override
    public boolean m_7357_(BlockState p_153469_, BlockGetter p_153470_, BlockPos p_153471_, PathComputationType p_153472_) {
        return false;
    }
}

