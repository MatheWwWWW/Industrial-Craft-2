/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceGateBlock
extends HorizontalDirectionalBlock {
    public static final BooleanProperty f_53341_ = BlockStateProperties.f_61446_;
    public static final BooleanProperty f_53342_ = BlockStateProperties.f_61448_;
    public static final BooleanProperty f_53343_ = BlockStateProperties.f_61442_;
    protected static final VoxelShape f_53344_ = Block.m_49796_(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape f_53345_ = Block.m_49796_(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    protected static final VoxelShape f_53346_ = Block.m_49796_(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
    protected static final VoxelShape f_53347_ = Block.m_49796_(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
    protected static final VoxelShape f_53348_ = Block.m_49796_(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
    protected static final VoxelShape f_53349_ = Block.m_49796_(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
    protected static final VoxelShape f_53350_ = Shapes.m_83110_(Block.m_49796_(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), Block.m_49796_(14.0, 5.0, 7.0, 16.0, 16.0, 9.0));
    protected static final VoxelShape f_53351_ = Shapes.m_83110_(Block.m_49796_(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), Block.m_49796_(7.0, 5.0, 14.0, 9.0, 16.0, 16.0));
    protected static final VoxelShape f_53352_ = Shapes.m_83110_(Block.m_49796_(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), Block.m_49796_(14.0, 2.0, 7.0, 16.0, 13.0, 9.0));
    protected static final VoxelShape f_53353_ = Shapes.m_83110_(Block.m_49796_(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), Block.m_49796_(7.0, 2.0, 14.0, 9.0, 13.0, 16.0));

    public FenceGateBlock(BlockBehaviour.Properties p_53356_) {
        super(p_53356_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53341_, false)).m_61124_(f_53342_, false)).m_61124_(f_53343_, false));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53391_, BlockGetter p_53392_, BlockPos p_53393_, CollisionContext p_53394_) {
        if (p_53391_.m_61143_(f_53343_).booleanValue()) {
            return p_53391_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53347_ : f_53346_;
        }
        return p_53391_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53345_ : f_53344_;
    }

    @Override
    public BlockState m_7417_(BlockState p_53382_, Direction p_53383_, BlockState p_53384_, LevelAccessor p_53385_, BlockPos p_53386_, BlockPos p_53387_) {
        Direction.Axis $$6 = p_53383_.m_122434_();
        if (p_53382_.m_61143_(f_54117_).m_122427_().m_122434_() == $$6) {
            boolean $$7 = this.m_53404_(p_53384_) || this.m_53404_(p_53385_.m_8055_(p_53386_.m_121945_(p_53383_.m_122424_())));
            return (BlockState)p_53382_.m_61124_(f_53343_, $$7);
        }
        return super.m_7417_(p_53382_, p_53383_, p_53384_, p_53385_, p_53386_, p_53387_);
    }

    @Override
    public VoxelShape m_5939_(BlockState p_53396_, BlockGetter p_53397_, BlockPos p_53398_, CollisionContext p_53399_) {
        if (p_53396_.m_61143_(f_53341_).booleanValue()) {
            return Shapes.m_83040_();
        }
        return p_53396_.m_61143_(f_54117_).m_122434_() == Direction.Axis.Z ? f_53348_ : f_53349_;
    }

    @Override
    public VoxelShape m_7952_(BlockState p_53401_, BlockGetter p_53402_, BlockPos p_53403_) {
        if (p_53401_.m_61143_(f_53343_).booleanValue()) {
            return p_53401_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53353_ : f_53352_;
        }
        return p_53401_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53351_ : f_53350_;
    }

    @Override
    public boolean m_7357_(BlockState p_53360_, BlockGetter p_53361_, BlockPos p_53362_, PathComputationType p_53363_) {
        switch (p_53363_) {
            case LAND: {
                return p_53360_.m_61143_(f_53341_);
            }
            case WATER: {
                return false;
            }
            case AIR: {
                return p_53360_.m_61143_(f_53341_);
            }
        }
        return false;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53358_) {
        Level $$1 = p_53358_.m_43725_();
        BlockPos $$2 = p_53358_.m_8083_();
        boolean $$3 = $$1.m_46753_($$2);
        Direction $$4 = p_53358_.m_8125_();
        Direction.Axis $$5 = $$4.m_122434_();
        boolean $$6 = $$5 == Direction.Axis.Z && (this.m_53404_($$1.m_8055_($$2.m_122024_())) || this.m_53404_($$1.m_8055_($$2.m_122029_()))) || $$5 == Direction.Axis.X && (this.m_53404_($$1.m_8055_($$2.m_122012_())) || this.m_53404_($$1.m_8055_($$2.m_122019_())));
        return (BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_54117_, $$4)).m_61124_(f_53341_, $$3)).m_61124_(f_53342_, $$3)).m_61124_(f_53343_, $$6);
    }

    private boolean m_53404_(BlockState p_53405_) {
        return p_53405_.m_204336_(BlockTags.f_13032_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_53365_, Level p_53366_, BlockPos p_53367_, Player p_53368_, InteractionHand p_53369_, BlockHitResult p_53370_) {
        if (p_53365_.m_61143_(f_53341_).booleanValue()) {
            p_53365_ = (BlockState)p_53365_.m_61124_(f_53341_, false);
            p_53366_.m_7731_(p_53367_, p_53365_, 10);
        } else {
            Direction $$6 = p_53368_.m_6350_();
            if (p_53365_.m_61143_(f_54117_) == $$6.m_122424_()) {
                p_53365_ = (BlockState)p_53365_.m_61124_(f_54117_, $$6);
            }
            p_53365_ = (BlockState)p_53365_.m_61124_(f_53341_, true);
            p_53366_.m_7731_(p_53367_, p_53365_, 10);
        }
        boolean $$7 = p_53365_.m_61143_(f_53341_);
        p_53366_.m_5898_(p_53368_, $$7 ? 1008 : 1014, p_53367_, 0);
        p_53366_.m_142346_(p_53368_, $$7 ? GameEvent.f_157796_ : GameEvent.f_157793_, p_53367_);
        return InteractionResult.m_19078_(p_53366_.f_46443_);
    }

    @Override
    public void m_6861_(BlockState p_53372_, Level p_53373_, BlockPos p_53374_, Block p_53375_, BlockPos p_53376_, boolean p_53377_) {
        if (p_53373_.f_46443_) {
            return;
        }
        boolean $$6 = p_53373_.m_46753_(p_53374_);
        if (p_53372_.m_61143_(f_53342_) != $$6) {
            p_53373_.m_7731_(p_53374_, (BlockState)((BlockState)p_53372_.m_61124_(f_53342_, $$6)).m_61124_(f_53341_, $$6), 2);
            if (p_53372_.m_61143_(f_53341_) != $$6) {
                p_53373_.m_5898_(null, $$6 ? 1008 : 1014, p_53374_, 0);
                p_53373_.m_142346_(null, $$6 ? GameEvent.f_157796_ : GameEvent.f_157793_, p_53374_);
            }
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53389_) {
        p_53389_.m_61104_(f_54117_, f_53341_, f_53342_, f_53343_);
    }

    public static boolean m_53378_(BlockState p_53379_, Direction p_53380_) {
        return p_53379_.m_61143_(f_54117_).m_122434_() == p_53380_.m_122427_().m_122434_();
    }
}

