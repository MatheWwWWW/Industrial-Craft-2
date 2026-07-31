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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoorBlock
extends Block {
    public static final DirectionProperty f_52726_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_52727_ = BlockStateProperties.f_61446_;
    public static final EnumProperty<DoorHingeSide> f_52728_ = BlockStateProperties.f_61394_;
    public static final BooleanProperty f_52729_ = BlockStateProperties.f_61448_;
    public static final EnumProperty<DoubleBlockHalf> f_52730_ = BlockStateProperties.f_61401_;
    protected static final float f_153164_ = 3.0f;
    protected static final VoxelShape f_52731_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape f_52732_ = Block.m_49796_(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape f_52733_ = Block.m_49796_(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape f_52734_ = Block.m_49796_(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    protected DoorBlock(BlockBehaviour.Properties p_52737_) {
        super(p_52737_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52726_, Direction.NORTH)).m_61124_(f_52727_, false)).m_61124_(f_52728_, DoorHingeSide.LEFT)).m_61124_(f_52729_, false)).m_61124_(f_52730_, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52807_, BlockGetter p_52808_, BlockPos p_52809_, CollisionContext p_52810_) {
        Direction $$4 = p_52807_.m_61143_(f_52726_);
        boolean $$5 = p_52807_.m_61143_(f_52727_) == false;
        boolean $$6 = p_52807_.m_61143_(f_52728_) == DoorHingeSide.RIGHT;
        switch ($$4) {
            default: {
                return $$5 ? f_52734_ : ($$6 ? f_52732_ : f_52731_);
            }
            case SOUTH: {
                return $$5 ? f_52731_ : ($$6 ? f_52734_ : f_52733_);
            }
            case WEST: {
                return $$5 ? f_52733_ : ($$6 ? f_52731_ : f_52732_);
            }
            case NORTH: 
        }
        return $$5 ? f_52732_ : ($$6 ? f_52733_ : f_52734_);
    }

    @Override
    public BlockState m_7417_(BlockState p_52796_, Direction p_52797_, BlockState p_52798_, LevelAccessor p_52799_, BlockPos p_52800_, BlockPos p_52801_) {
        DoubleBlockHalf $$6 = p_52796_.m_61143_(f_52730_);
        if (p_52797_.m_122434_() == Direction.Axis.Y && $$6 == DoubleBlockHalf.LOWER == (p_52797_ == Direction.UP)) {
            if (p_52798_.m_60713_(this) && p_52798_.m_61143_(f_52730_) != $$6) {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_52796_.m_61124_(f_52726_, p_52798_.m_61143_(f_52726_))).m_61124_(f_52727_, p_52798_.m_61143_(f_52727_))).m_61124_(f_52728_, p_52798_.m_61143_(f_52728_))).m_61124_(f_52729_, p_52798_.m_61143_(f_52729_));
            }
            return Blocks.f_50016_.m_49966_();
        }
        if ($$6 == DoubleBlockHalf.LOWER && p_52797_ == Direction.DOWN && !p_52796_.m_60710_(p_52799_, p_52800_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_52796_, p_52797_, p_52798_, p_52799_, p_52800_, p_52801_);
    }

    @Override
    public void m_5707_(Level p_52755_, BlockPos p_52756_, BlockState p_52757_, Player p_52758_) {
        if (!p_52755_.f_46443_ && p_52758_.m_7500_()) {
            DoublePlantBlock.m_52903_(p_52755_, p_52756_, p_52757_, p_52758_);
        }
        super.m_5707_(p_52755_, p_52756_, p_52757_, p_52758_);
    }

    @Override
    public boolean m_7357_(BlockState p_52764_, BlockGetter p_52765_, BlockPos p_52766_, PathComputationType p_52767_) {
        switch (p_52767_) {
            case LAND: {
                return p_52764_.m_61143_(f_52727_);
            }
            case WATER: {
                return false;
            }
            case AIR: {
                return p_52764_.m_61143_(f_52727_);
            }
        }
        return false;
    }

    private int m_52811_() {
        return this.f_60442_ == Material.f_76279_ ? 1011 : 1012;
    }

    private int m_52812_() {
        return this.f_60442_ == Material.f_76279_ ? 1005 : 1006;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_52739_) {
        BlockPos $$1 = p_52739_.m_8083_();
        Level $$2 = p_52739_.m_43725_();
        if ($$1.m_123342_() < $$2.m_151558_() - 1 && $$2.m_8055_($$1.m_7494_()).m_60629_(p_52739_)) {
            boolean $$3 = $$2.m_46753_($$1) || $$2.m_46753_($$1.m_7494_());
            return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_52726_, p_52739_.m_8125_())).m_61124_(f_52728_, this.m_52804_(p_52739_))).m_61124_(f_52729_, $$3)).m_61124_(f_52727_, $$3)).m_61124_(f_52730_, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    @Override
    public void m_6402_(Level p_52749_, BlockPos p_52750_, BlockState p_52751_, LivingEntity p_52752_, ItemStack p_52753_) {
        p_52749_.m_7731_(p_52750_.m_7494_(), (BlockState)p_52751_.m_61124_(f_52730_, DoubleBlockHalf.UPPER), 3);
    }

    private DoorHingeSide m_52804_(BlockPlaceContext p_52805_) {
        boolean $$17;
        Level $$1 = p_52805_.m_43725_();
        BlockPos $$2 = p_52805_.m_8083_();
        Direction $$3 = p_52805_.m_8125_();
        BlockPos $$4 = $$2.m_7494_();
        Direction $$5 = $$3.m_122428_();
        BlockPos $$6 = $$2.m_121945_($$5);
        BlockState $$7 = $$1.m_8055_($$6);
        BlockPos $$8 = $$4.m_121945_($$5);
        BlockState $$9 = $$1.m_8055_($$8);
        Direction $$10 = $$3.m_122427_();
        BlockPos $$11 = $$2.m_121945_($$10);
        BlockState $$12 = $$1.m_8055_($$11);
        BlockPos $$13 = $$4.m_121945_($$10);
        BlockState $$14 = $$1.m_8055_($$13);
        int $$15 = ($$7.m_60838_($$1, $$6) ? -1 : 0) + ($$9.m_60838_($$1, $$8) ? -1 : 0) + ($$12.m_60838_($$1, $$11) ? 1 : 0) + ($$14.m_60838_($$1, $$13) ? 1 : 0);
        boolean $$16 = $$7.m_60713_(this) && $$7.m_61143_(f_52730_) == DoubleBlockHalf.LOWER;
        boolean bl = $$17 = $$12.m_60713_(this) && $$12.m_61143_(f_52730_) == DoubleBlockHalf.LOWER;
        if ($$16 && !$$17 || $$15 > 0) {
            return DoorHingeSide.RIGHT;
        }
        if ($$17 && !$$16 || $$15 < 0) {
            return DoorHingeSide.LEFT;
        }
        int $$18 = $$3.m_122429_();
        int $$19 = $$3.m_122431_();
        Vec3 $$20 = p_52805_.m_43720_();
        double $$21 = $$20.f_82479_ - (double)$$2.m_123341_();
        double $$22 = $$20.f_82481_ - (double)$$2.m_123343_();
        return $$18 < 0 && $$22 < 0.5 || $$18 > 0 && $$22 > 0.5 || $$19 < 0 && $$21 > 0.5 || $$19 > 0 && $$21 < 0.5 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_52769_, Level p_52770_, BlockPos p_52771_, Player p_52772_, InteractionHand p_52773_, BlockHitResult p_52774_) {
        if (this.f_60442_ == Material.f_76279_) {
            return InteractionResult.PASS;
        }
        p_52769_ = (BlockState)p_52769_.m_61122_(f_52727_);
        p_52770_.m_7731_(p_52771_, p_52769_, 10);
        p_52770_.m_5898_(p_52772_, p_52769_.m_61143_(f_52727_) != false ? this.m_52812_() : this.m_52811_(), p_52771_, 0);
        p_52770_.m_142346_(p_52772_, this.m_52815_(p_52769_) ? GameEvent.f_157796_ : GameEvent.f_157793_, p_52771_);
        return InteractionResult.m_19078_(p_52770_.f_46443_);
    }

    public boolean m_52815_(BlockState p_52816_) {
        return p_52816_.m_61143_(f_52727_);
    }

    public void m_153165_(@Nullable Entity p_153166_, Level p_153167_, BlockState p_153168_, BlockPos p_153169_, boolean p_153170_) {
        if (!p_153168_.m_60713_(this) || p_153168_.m_61143_(f_52727_) == p_153170_) {
            return;
        }
        p_153167_.m_7731_(p_153169_, (BlockState)p_153168_.m_61124_(f_52727_, p_153170_), 10);
        this.m_52759_(p_153167_, p_153169_, p_153170_);
        p_153167_.m_142346_(p_153166_, p_153170_ ? GameEvent.f_157796_ : GameEvent.f_157793_, p_153169_);
    }

    @Override
    public void m_6861_(BlockState p_52776_, Level p_52777_, BlockPos p_52778_, Block p_52779_, BlockPos p_52780_, boolean p_52781_) {
        boolean $$6;
        boolean bl = p_52777_.m_46753_(p_52778_) || p_52777_.m_46753_(p_52778_.m_121945_(p_52776_.m_61143_(f_52730_) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN)) ? true : ($$6 = false);
        if (!this.m_49966_().m_60713_(p_52779_) && $$6 != p_52776_.m_61143_(f_52729_)) {
            if ($$6 != p_52776_.m_61143_(f_52727_)) {
                this.m_52759_(p_52777_, p_52778_, $$6);
                p_52777_.m_142346_(null, $$6 ? GameEvent.f_157796_ : GameEvent.f_157793_, p_52778_);
            }
            p_52777_.m_7731_(p_52778_, (BlockState)((BlockState)p_52776_.m_61124_(f_52729_, $$6)).m_61124_(f_52727_, $$6), 2);
        }
    }

    @Override
    public boolean m_7898_(BlockState p_52783_, LevelReader p_52784_, BlockPos p_52785_) {
        BlockPos $$3 = p_52785_.m_7495_();
        BlockState $$4 = p_52784_.m_8055_($$3);
        if (p_52783_.m_61143_(f_52730_) == DoubleBlockHalf.LOWER) {
            return $$4.m_60783_(p_52784_, $$3, Direction.UP);
        }
        return $$4.m_60713_(this);
    }

    private void m_52759_(Level p_52760_, BlockPos p_52761_, boolean p_52762_) {
        p_52760_.m_5898_(null, p_52762_ ? this.m_52812_() : this.m_52811_(), p_52761_, 0);
    }

    @Override
    public PushReaction m_5537_(BlockState p_52814_) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState m_6843_(BlockState p_52790_, Rotation p_52791_) {
        return (BlockState)p_52790_.m_61124_(f_52726_, p_52791_.m_55954_(p_52790_.m_61143_(f_52726_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_52787_, Mirror p_52788_) {
        if (p_52788_ == Mirror.NONE) {
            return p_52787_;
        }
        return (BlockState)p_52787_.m_60717_(p_52788_.m_54846_(p_52787_.m_61143_(f_52726_))).m_61122_(f_52728_);
    }

    @Override
    public long m_7799_(BlockState p_52793_, BlockPos p_52794_) {
        return Mth.m_14130_(p_52794_.m_123341_(), p_52794_.m_6625_(p_52793_.m_61143_(f_52730_) == DoubleBlockHalf.LOWER ? 0 : 1).m_123342_(), p_52794_.m_123343_());
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52803_) {
        p_52803_.m_61104_(f_52730_, f_52726_, f_52727_, f_52728_, f_52729_);
    }

    public static boolean m_52745_(Level p_52746_, BlockPos p_52747_) {
        return DoorBlock.m_52817_(p_52746_.m_8055_(p_52747_));
    }

    public static boolean m_52817_(BlockState p_52818_) {
        return p_52818_.m_60734_() instanceof DoorBlock && (p_52818_.m_60767_() == Material.f_76320_ || p_52818_.m_60767_() == Material.f_76321_);
    }
}

