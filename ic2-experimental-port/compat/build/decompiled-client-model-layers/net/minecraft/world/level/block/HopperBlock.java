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
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HopperBlock
extends BaseEntityBlock {
    public static final DirectionProperty f_54021_ = BlockStateProperties.f_61373_;
    public static final BooleanProperty f_54022_ = BlockStateProperties.f_61431_;
    private static final VoxelShape f_54023_ = Block.m_49796_(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_54024_ = Block.m_49796_(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
    private static final VoxelShape f_54025_ = Shapes.m_83110_(f_54024_, f_54023_);
    private static final VoxelShape f_54026_ = Shapes.m_83113_(f_54025_, Hopper.f_59296_, BooleanOp.f_82685_);
    private static final VoxelShape f_54027_ = Shapes.m_83110_(f_54026_, Block.m_49796_(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
    private static final VoxelShape f_54028_ = Shapes.m_83110_(f_54026_, Block.m_49796_(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
    private static final VoxelShape f_54029_ = Shapes.m_83110_(f_54026_, Block.m_49796_(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
    private static final VoxelShape f_54030_ = Shapes.m_83110_(f_54026_, Block.m_49796_(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
    private static final VoxelShape f_54031_ = Shapes.m_83110_(f_54026_, Block.m_49796_(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
    private static final VoxelShape f_54032_ = Hopper.f_59296_;
    private static final VoxelShape f_54033_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
    private static final VoxelShape f_54034_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
    private static final VoxelShape f_54035_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
    private static final VoxelShape f_54036_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

    public HopperBlock(BlockBehaviour.Properties p_54039_) {
        super(p_54039_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54021_, Direction.DOWN)).m_61124_(f_54022_, true));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54105_, BlockGetter p_54106_, BlockPos p_54107_, CollisionContext p_54108_) {
        switch (p_54105_.m_61143_(f_54021_)) {
            case DOWN: {
                return f_54027_;
            }
            case NORTH: {
                return f_54029_;
            }
            case SOUTH: {
                return f_54030_;
            }
            case WEST: {
                return f_54031_;
            }
            case EAST: {
                return f_54028_;
            }
        }
        return f_54026_;
    }

    @Override
    public VoxelShape m_6079_(BlockState p_54099_, BlockGetter p_54100_, BlockPos p_54101_) {
        switch (p_54099_.m_61143_(f_54021_)) {
            case DOWN: {
                return f_54032_;
            }
            case NORTH: {
                return f_54034_;
            }
            case SOUTH: {
                return f_54035_;
            }
            case WEST: {
                return f_54036_;
            }
            case EAST: {
                return f_54033_;
            }
        }
        return Hopper.f_59296_;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54041_) {
        Direction $$1 = p_54041_.m_43719_().m_122424_();
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_54021_, $$1.m_122434_() == Direction.Axis.Y ? Direction.DOWN : $$1)).m_61124_(f_54022_, true);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153382_, BlockState p_153383_) {
        return new HopperBlockEntity(p_153382_, p_153383_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153378_, BlockState p_153379_, BlockEntityType<T> p_153380_) {
        return p_153378_.f_46443_ ? null : HopperBlock.m_152132_(p_153380_, BlockEntityType.f_58933_, HopperBlockEntity::m_155573_);
    }

    @Override
    public void m_6402_(Level p_54049_, BlockPos p_54050_, BlockState p_54051_, LivingEntity p_54052_, ItemStack p_54053_) {
        BlockEntity $$5;
        if (p_54053_.m_41788_() && ($$5 = p_54049_.m_7702_(p_54050_)) instanceof HopperBlockEntity) {
            ((HopperBlockEntity)$$5).m_58638_(p_54053_.m_41786_());
        }
    }

    @Override
    public void m_6807_(BlockState p_54110_, Level p_54111_, BlockPos p_54112_, BlockState p_54113_, boolean p_54114_) {
        if (p_54113_.m_60713_(p_54110_.m_60734_())) {
            return;
        }
        this.m_54044_(p_54111_, p_54112_, p_54110_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_54071_, Level p_54072_, BlockPos p_54073_, Player p_54074_, InteractionHand p_54075_, BlockHitResult p_54076_) {
        if (p_54072_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity $$6 = p_54072_.m_7702_(p_54073_);
        if ($$6 instanceof HopperBlockEntity) {
            p_54074_.m_5893_((HopperBlockEntity)$$6);
            p_54074_.m_36220_(Stats.f_12957_);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void m_6861_(BlockState p_54078_, Level p_54079_, BlockPos p_54080_, Block p_54081_, BlockPos p_54082_, boolean p_54083_) {
        this.m_54044_(p_54079_, p_54080_, p_54078_);
    }

    private void m_54044_(Level p_54045_, BlockPos p_54046_, BlockState p_54047_) {
        boolean $$3;
        boolean bl = $$3 = !p_54045_.m_46753_(p_54046_);
        if ($$3 != p_54047_.m_61143_(f_54022_)) {
            p_54045_.m_7731_(p_54046_, (BlockState)p_54047_.m_61124_(f_54022_, $$3), 4);
        }
    }

    @Override
    public void m_6810_(BlockState p_54085_, Level p_54086_, BlockPos p_54087_, BlockState p_54088_, boolean p_54089_) {
        if (p_54085_.m_60713_(p_54088_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_54086_.m_7702_(p_54087_);
        if ($$5 instanceof HopperBlockEntity) {
            Containers.m_19002_(p_54086_, p_54087_, (HopperBlockEntity)$$5);
            p_54086_.m_46717_(p_54087_, this);
        }
        super.m_6810_(p_54085_, p_54086_, p_54087_, p_54088_, p_54089_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_54103_) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean m_7278_(BlockState p_54055_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_54062_, Level p_54063_, BlockPos p_54064_) {
        return AbstractContainerMenu.m_38918_(p_54063_.m_7702_(p_54064_));
    }

    @Override
    public BlockState m_6843_(BlockState p_54094_, Rotation p_54095_) {
        return (BlockState)p_54094_.m_61124_(f_54021_, p_54095_.m_55954_(p_54094_.m_61143_(f_54021_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_54091_, Mirror p_54092_) {
        return p_54091_.m_60717_(p_54092_.m_54846_(p_54091_.m_61143_(f_54021_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54097_) {
        p_54097_.m_61104_(f_54021_, f_54022_);
    }

    @Override
    public void m_7892_(BlockState p_54066_, Level p_54067_, BlockPos p_54068_, Entity p_54069_) {
        BlockEntity $$4 = p_54067_.m_7702_(p_54068_);
        if ($$4 instanceof HopperBlockEntity) {
            HopperBlockEntity.m_155567_(p_54067_, p_54068_, p_54066_, p_54069_, (HopperBlockEntity)$$4);
        }
    }

    @Override
    public boolean m_7357_(BlockState p_54057_, BlockGetter p_54058_, BlockPos p_54059_, PathComputationType p_54060_) {
        return false;
    }
}

