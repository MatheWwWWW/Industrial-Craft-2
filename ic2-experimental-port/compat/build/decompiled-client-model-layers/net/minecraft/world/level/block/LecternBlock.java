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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LecternBlock
extends BaseEntityBlock {
    public static final DirectionProperty f_54465_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_54466_ = BlockStateProperties.f_61448_;
    public static final BooleanProperty f_54467_ = BlockStateProperties.f_61440_;
    public static final VoxelShape f_54468_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    public static final VoxelShape f_54469_ = Block.m_49796_(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
    public static final VoxelShape f_54470_ = Shapes.m_83110_(f_54468_, f_54469_);
    public static final VoxelShape f_54471_ = Block.m_49796_(0.0, 15.0, 0.0, 16.0, 15.0, 16.0);
    public static final VoxelShape f_54472_ = Shapes.m_83110_(f_54470_, f_54471_);
    public static final VoxelShape f_54473_ = Shapes.m_83124_(Block.m_49796_(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0), Block.m_49796_(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0), Block.m_49796_(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0), f_54470_);
    public static final VoxelShape f_54474_ = Shapes.m_83124_(Block.m_49796_(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333), Block.m_49796_(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667), Block.m_49796_(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0), f_54470_);
    public static final VoxelShape f_54475_ = Shapes.m_83124_(Block.m_49796_(10.666667, 10.0, 0.0, 15.0, 14.0, 16.0), Block.m_49796_(6.333333, 12.0, 0.0, 10.666667, 16.0, 16.0), Block.m_49796_(2.0, 14.0, 0.0, 6.333333, 18.0, 16.0), f_54470_);
    public static final VoxelShape f_54476_ = Shapes.m_83124_(Block.m_49796_(0.0, 10.0, 10.666667, 16.0, 14.0, 15.0), Block.m_49796_(0.0, 12.0, 6.333333, 16.0, 16.0, 10.666667), Block.m_49796_(0.0, 14.0, 2.0, 16.0, 18.0, 6.333333), f_54470_);
    private static final int f_153565_ = 2;

    protected LecternBlock(BlockBehaviour.Properties p_54479_) {
        super(p_54479_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54465_, Direction.NORTH)).m_61124_(f_54466_, false)).m_61124_(f_54467_, false));
    }

    @Override
    public RenderShape m_7514_(BlockState p_54559_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape m_7952_(BlockState p_54584_, BlockGetter p_54585_, BlockPos p_54586_) {
        return f_54470_;
    }

    @Override
    public boolean m_7923_(BlockState p_54582_) {
        return true;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54481_) {
        CompoundTag $$5;
        Level $$1 = p_54481_.m_43725_();
        ItemStack $$2 = p_54481_.m_43722_();
        Player $$3 = p_54481_.m_43723_();
        boolean $$4 = false;
        if (!$$1.f_46443_ && $$3 != null && $$3.m_36337_() && ($$5 = BlockItem.m_186336_($$2)) != null && $$5.m_128441_("Book")) {
            $$4 = true;
        }
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_54465_, p_54481_.m_8125_().m_122424_())).m_61124_(f_54467_, $$4);
    }

    @Override
    public VoxelShape m_5939_(BlockState p_54577_, BlockGetter p_54578_, BlockPos p_54579_, CollisionContext p_54580_) {
        return f_54472_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        switch (p_54561_.m_61143_(f_54465_)) {
            case NORTH: {
                return f_54474_;
            }
            case SOUTH: {
                return f_54476_;
            }
            case EAST: {
                return f_54475_;
            }
            case WEST: {
                return f_54473_;
            }
        }
        return f_54470_;
    }

    @Override
    public BlockState m_6843_(BlockState p_54540_, Rotation p_54541_) {
        return (BlockState)p_54540_.m_61124_(f_54465_, p_54541_.m_55954_(p_54540_.m_61143_(f_54465_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_54537_, Mirror p_54538_) {
        return p_54537_.m_60717_(p_54538_.m_54846_(p_54537_.m_61143_(f_54465_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54543_) {
        p_54543_.m_61104_(f_54465_, f_54466_, f_54467_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153573_, BlockState p_153574_) {
        return new LecternBlockEntity(p_153573_, p_153574_);
    }

    public static boolean m_153566_(@Nullable Player p_153567_, Level p_153568_, BlockPos p_153569_, BlockState p_153570_, ItemStack p_153571_) {
        if (!p_153570_.m_61143_(f_54467_).booleanValue()) {
            if (!p_153568_.f_46443_) {
                LecternBlock.m_153575_(p_153567_, p_153568_, p_153569_, p_153570_, p_153571_);
            }
            return true;
        }
        return false;
    }

    private static void m_153575_(@Nullable Player p_153576_, Level p_153577_, BlockPos p_153578_, BlockState p_153579_, ItemStack p_153580_) {
        BlockEntity $$5 = p_153577_.m_7702_(p_153578_);
        if ($$5 instanceof LecternBlockEntity) {
            LecternBlockEntity $$6 = (LecternBlockEntity)$$5;
            $$6.m_59536_(p_153580_.m_41620_(1));
            LecternBlock.m_54497_(p_153577_, p_153578_, p_153579_, true);
            p_153577_.m_5594_(null, p_153578_, SoundEvents.f_11714_, SoundSource.BLOCKS, 1.0f, 1.0f);
            p_153577_.m_142346_(p_153576_, GameEvent.f_157792_, p_153578_);
        }
    }

    public static void m_54497_(Level p_54498_, BlockPos p_54499_, BlockState p_54500_, boolean p_54501_) {
        p_54498_.m_7731_(p_54499_, (BlockState)((BlockState)p_54500_.m_61124_(f_54466_, false)).m_61124_(f_54467_, p_54501_), 3);
        LecternBlock.m_54544_(p_54498_, p_54499_, p_54500_);
    }

    public static void m_54488_(Level p_54489_, BlockPos p_54490_, BlockState p_54491_) {
        LecternBlock.m_54553_(p_54489_, p_54490_, p_54491_, true);
        p_54489_.m_186460_(p_54490_, p_54491_.m_60734_(), 2);
        p_54489_.m_46796_(1043, p_54490_, 0);
    }

    private static void m_54553_(Level p_54554_, BlockPos p_54555_, BlockState p_54556_, boolean p_54557_) {
        p_54554_.m_7731_(p_54555_, (BlockState)p_54556_.m_61124_(f_54466_, p_54557_), 3);
        LecternBlock.m_54544_(p_54554_, p_54555_, p_54556_);
    }

    private static void m_54544_(Level p_54545_, BlockPos p_54546_, BlockState p_54547_) {
        p_54545_.m_46672_(p_54546_.m_7495_(), p_54547_.m_60734_());
    }

    @Override
    public void m_213897_(BlockState p_221388_, ServerLevel p_221389_, BlockPos p_221390_, RandomSource p_221391_) {
        LecternBlock.m_54553_(p_221389_, p_221390_, p_221388_, false);
    }

    @Override
    public void m_6810_(BlockState p_54531_, Level p_54532_, BlockPos p_54533_, BlockState p_54534_, boolean p_54535_) {
        if (p_54531_.m_60713_(p_54534_.m_60734_())) {
            return;
        }
        if (p_54531_.m_61143_(f_54467_).booleanValue()) {
            this.m_54587_(p_54531_, p_54532_, p_54533_);
        }
        if (p_54531_.m_61143_(f_54466_).booleanValue()) {
            p_54532_.m_46672_(p_54533_.m_7495_(), this);
        }
        super.m_6810_(p_54531_, p_54532_, p_54533_, p_54534_, p_54535_);
    }

    private void m_54587_(BlockState p_54588_, Level p_54589_, BlockPos p_54590_) {
        BlockEntity $$3 = p_54589_.m_7702_(p_54590_);
        if ($$3 instanceof LecternBlockEntity) {
            LecternBlockEntity $$4 = (LecternBlockEntity)$$3;
            Direction $$5 = p_54588_.m_61143_(f_54465_);
            ItemStack $$6 = $$4.m_59566_().m_41777_();
            float $$7 = 0.25f * (float)$$5.m_122429_();
            float $$8 = 0.25f * (float)$$5.m_122431_();
            ItemEntity $$9 = new ItemEntity(p_54589_, (double)p_54590_.m_123341_() + 0.5 + (double)$$7, p_54590_.m_123342_() + 1, (double)p_54590_.m_123343_() + 0.5 + (double)$$8, $$6);
            $$9.m_32060_();
            p_54589_.m_7967_($$9);
            $$4.m_6211_();
        }
    }

    @Override
    public boolean m_7899_(BlockState p_54575_) {
        return true;
    }

    @Override
    public int m_6378_(BlockState p_54515_, BlockGetter p_54516_, BlockPos p_54517_, Direction p_54518_) {
        return p_54515_.m_61143_(f_54466_) != false ? 15 : 0;
    }

    @Override
    public int m_6376_(BlockState p_54566_, BlockGetter p_54567_, BlockPos p_54568_, Direction p_54569_) {
        return p_54569_ == Direction.UP && p_54566_.m_61143_(f_54466_) != false ? 15 : 0;
    }

    @Override
    public boolean m_7278_(BlockState p_54503_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_54520_, Level p_54521_, BlockPos p_54522_) {
        BlockEntity $$3;
        if (p_54520_.m_61143_(f_54467_).booleanValue() && ($$3 = p_54521_.m_7702_(p_54522_)) instanceof LecternBlockEntity) {
            return ((LecternBlockEntity)$$3).m_59569_();
        }
        return 0;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_54524_, Level p_54525_, BlockPos p_54526_, Player p_54527_, InteractionHand p_54528_, BlockHitResult p_54529_) {
        if (p_54524_.m_61143_(f_54467_).booleanValue()) {
            if (!p_54525_.f_46443_) {
                this.m_54484_(p_54525_, p_54526_, p_54527_);
            }
            return InteractionResult.m_19078_(p_54525_.f_46443_);
        }
        ItemStack $$6 = p_54527_.m_21120_(p_54528_);
        if ($$6.m_41619_() || $$6.m_204117_(ItemTags.f_13162_)) {
            return InteractionResult.PASS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public MenuProvider m_7246_(BlockState p_54571_, Level p_54572_, BlockPos p_54573_) {
        if (!p_54571_.m_61143_(f_54467_).booleanValue()) {
            return null;
        }
        return super.m_7246_(p_54571_, p_54572_, p_54573_);
    }

    private void m_54484_(Level p_54485_, BlockPos p_54486_, Player p_54487_) {
        BlockEntity $$3 = p_54485_.m_7702_(p_54486_);
        if ($$3 instanceof LecternBlockEntity) {
            p_54487_.m_5893_((LecternBlockEntity)$$3);
            p_54487_.m_36220_(Stats.f_12974_);
        }
    }

    @Override
    public boolean m_7357_(BlockState p_54510_, BlockGetter p_54511_, BlockPos p_54512_, PathComputationType p_54513_) {
        return false;
    }
}

