/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.base.MoreObjects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TripWireHookBlock
extends Block {
    public static final DirectionProperty f_57667_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_57668_ = BlockStateProperties.f_61448_;
    public static final BooleanProperty f_57669_ = BlockStateProperties.f_61386_;
    protected static final int f_154837_ = 1;
    protected static final int f_154838_ = 42;
    private static final int f_154840_ = 10;
    protected static final int f_154839_ = 3;
    protected static final VoxelShape f_57670_ = Block.m_49796_(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape f_57671_ = Block.m_49796_(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
    protected static final VoxelShape f_57672_ = Block.m_49796_(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape f_57673_ = Block.m_49796_(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

    public TripWireHookBlock(BlockBehaviour.Properties p_57676_) {
        super(p_57676_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57667_, Direction.NORTH)).m_61124_(f_57668_, false)).m_61124_(f_57669_, false));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57740_, BlockGetter p_57741_, BlockPos p_57742_, CollisionContext p_57743_) {
        switch (p_57740_.m_61143_(f_57667_)) {
            default: {
                return f_57673_;
            }
            case WEST: {
                return f_57672_;
            }
            case SOUTH: {
                return f_57671_;
            }
            case NORTH: 
        }
        return f_57670_;
    }

    @Override
    public boolean m_7898_(BlockState p_57721_, LevelReader p_57722_, BlockPos p_57723_) {
        Direction $$3 = p_57721_.m_61143_(f_57667_);
        BlockPos $$4 = p_57723_.m_121945_($$3.m_122424_());
        BlockState $$5 = p_57722_.m_8055_($$4);
        return $$3.m_122434_().m_122479_() && $$5.m_60783_(p_57722_, $$4, $$3);
    }

    @Override
    public BlockState m_7417_(BlockState p_57731_, Direction p_57732_, BlockState p_57733_, LevelAccessor p_57734_, BlockPos p_57735_, BlockPos p_57736_) {
        if (p_57732_.m_122424_() == p_57731_.m_61143_(f_57667_) && !p_57731_.m_60710_(p_57734_, p_57735_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_57731_, p_57732_, p_57733_, p_57734_, p_57735_, p_57736_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_57678_) {
        Direction[] $$4;
        BlockState $$1 = (BlockState)((BlockState)this.m_49966_().m_61124_(f_57668_, false)).m_61124_(f_57669_, false);
        Level $$2 = p_57678_.m_43725_();
        BlockPos $$3 = p_57678_.m_8083_();
        for (Direction $$5 : $$4 = p_57678_.m_6232_()) {
            Direction $$6;
            if (!$$5.m_122434_().m_122479_() || !($$1 = (BlockState)$$1.m_61124_(f_57667_, $$6 = $$5.m_122424_())).m_60710_($$2, $$3)) continue;
            return $$1;
        }
        return null;
    }

    @Override
    public void m_6402_(Level p_57680_, BlockPos p_57681_, BlockState p_57682_, LivingEntity p_57683_, ItemStack p_57684_) {
        this.m_57685_(p_57680_, p_57681_, p_57682_, false, false, -1, null);
    }

    public void m_57685_(Level p_57686_, BlockPos p_57687_, BlockState p_57688_, boolean p_57689_, boolean p_57690_, int p_57691_, @Nullable BlockState p_57692_) {
        Direction $$7 = p_57688_.m_61143_(f_57667_);
        boolean $$8 = p_57688_.m_61143_(f_57669_);
        boolean $$9 = p_57688_.m_61143_(f_57668_);
        boolean $$10 = !p_57689_;
        boolean $$11 = false;
        int $$12 = 0;
        BlockState[] $$13 = new BlockState[42];
        for (int $$14 = 1; $$14 < 42; ++$$14) {
            BlockPos $$15 = p_57687_.m_5484_($$7, $$14);
            BlockState $$16 = p_57686_.m_8055_($$15);
            if ($$16.m_60713_(Blocks.f_50266_)) {
                if ($$16.m_61143_(f_57667_) != $$7.m_122424_()) break;
                $$12 = $$14;
                break;
            }
            if ($$16.m_60713_(Blocks.f_50267_) || $$14 == p_57691_) {
                if ($$14 == p_57691_) {
                    $$16 = (BlockState)MoreObjects.firstNonNull((Object)p_57692_, (Object)$$16);
                }
                boolean $$17 = $$16.m_61143_(TripWireBlock.f_57592_) == false;
                boolean $$18 = $$16.m_61143_(TripWireBlock.f_57590_);
                $$11 |= $$17 && $$18;
                $$13[$$14] = $$16;
                if ($$14 != p_57691_) continue;
                p_57686_.m_186460_(p_57687_, this, 10);
                $$10 &= $$17;
                continue;
            }
            $$13[$$14] = null;
            $$10 = false;
        }
        BlockState $$19 = (BlockState)((BlockState)this.m_49966_().m_61124_(f_57669_, $$10)).m_61124_(f_57668_, $$11 &= ($$10 &= $$12 > 1));
        if ($$12 > 0) {
            BlockPos $$20 = p_57687_.m_5484_($$7, $$12);
            Direction $$21 = $$7.m_122424_();
            p_57686_.m_7731_($$20, (BlockState)$$19.m_61124_(f_57667_, $$21), 3);
            this.m_57693_(p_57686_, $$20, $$21);
            this.m_222602_(p_57686_, $$20, $$10, $$11, $$8, $$9);
        }
        this.m_222602_(p_57686_, p_57687_, $$10, $$11, $$8, $$9);
        if (!p_57689_) {
            p_57686_.m_7731_(p_57687_, (BlockState)$$19.m_61124_(f_57667_, $$7), 3);
            if (p_57690_) {
                this.m_57693_(p_57686_, p_57687_, $$7);
            }
        }
        if ($$8 != $$10) {
            for (int $$22 = 1; $$22 < $$12; ++$$22) {
                BlockPos $$23 = p_57687_.m_5484_($$7, $$22);
                BlockState $$24 = $$13[$$22];
                if ($$24 == null) continue;
                p_57686_.m_7731_($$23, (BlockState)$$24.m_61124_(f_57669_, $$10), 3);
                if (p_57686_.m_8055_($$23).m_60795_()) continue;
            }
        }
    }

    @Override
    public void m_213897_(BlockState p_222610_, ServerLevel p_222611_, BlockPos p_222612_, RandomSource p_222613_) {
        this.m_57685_(p_222611_, p_222612_, p_222610_, false, true, -1, null);
    }

    private void m_222602_(Level p_222603_, BlockPos p_222604_, boolean p_222605_, boolean p_222606_, boolean p_222607_, boolean p_222608_) {
        if (p_222606_ && !p_222608_) {
            p_222603_.m_5594_(null, p_222604_, SoundEvents.f_12524_, SoundSource.BLOCKS, 0.4f, 0.6f);
            p_222603_.m_142346_(null, GameEvent.f_223702_, p_222604_);
        } else if (!p_222606_ && p_222608_) {
            p_222603_.m_5594_(null, p_222604_, SoundEvents.f_12523_, SoundSource.BLOCKS, 0.4f, 0.5f);
            p_222603_.m_142346_(null, GameEvent.f_223703_, p_222604_);
        } else if (p_222605_ && !p_222607_) {
            p_222603_.m_5594_(null, p_222604_, SoundEvents.f_12522_, SoundSource.BLOCKS, 0.4f, 0.7f);
            p_222603_.m_142346_(null, GameEvent.f_157791_, p_222604_);
        } else if (!p_222605_ && p_222607_) {
            p_222603_.m_5594_(null, p_222604_, SoundEvents.f_12525_, SoundSource.BLOCKS, 0.4f, 1.2f / (p_222603_.f_46441_.m_188501_() * 0.2f + 0.9f));
            p_222603_.m_142346_(null, GameEvent.f_157795_, p_222604_);
        }
    }

    private void m_57693_(Level p_57694_, BlockPos p_57695_, Direction p_57696_) {
        p_57694_.m_46672_(p_57695_, this);
        p_57694_.m_46672_(p_57695_.m_121945_(p_57696_.m_122424_()), this);
    }

    @Override
    public void m_6810_(BlockState p_57715_, Level p_57716_, BlockPos p_57717_, BlockState p_57718_, boolean p_57719_) {
        if (p_57719_ || p_57715_.m_60713_(p_57718_.m_60734_())) {
            return;
        }
        boolean $$5 = p_57715_.m_61143_(f_57669_);
        boolean $$6 = p_57715_.m_61143_(f_57668_);
        if ($$5 || $$6) {
            this.m_57685_(p_57716_, p_57717_, p_57715_, true, false, -1, null);
        }
        if ($$6) {
            p_57716_.m_46672_(p_57717_, this);
            p_57716_.m_46672_(p_57717_.m_121945_(p_57715_.m_61143_(f_57667_).m_122424_()), this);
        }
        super.m_6810_(p_57715_, p_57716_, p_57717_, p_57718_, p_57719_);
    }

    @Override
    public int m_6378_(BlockState p_57710_, BlockGetter p_57711_, BlockPos p_57712_, Direction p_57713_) {
        return p_57710_.m_61143_(f_57668_) != false ? 15 : 0;
    }

    @Override
    public int m_6376_(BlockState p_57745_, BlockGetter p_57746_, BlockPos p_57747_, Direction p_57748_) {
        if (!p_57745_.m_61143_(f_57668_).booleanValue()) {
            return 0;
        }
        if (p_57745_.m_61143_(f_57667_) == p_57748_) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean m_7899_(BlockState p_57750_) {
        return true;
    }

    @Override
    public BlockState m_6843_(BlockState p_57728_, Rotation p_57729_) {
        return (BlockState)p_57728_.m_61124_(f_57667_, p_57729_.m_55954_(p_57728_.m_61143_(f_57667_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_57725_, Mirror p_57726_) {
        return p_57725_.m_60717_(p_57726_.m_54846_(p_57725_.m_61143_(f_57667_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57738_) {
        p_57738_.m_61104_(f_57667_, f_57668_, f_57669_);
    }
}

