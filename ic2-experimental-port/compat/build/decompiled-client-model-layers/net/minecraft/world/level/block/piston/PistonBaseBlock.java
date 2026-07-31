/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonBaseBlock
extends DirectionalBlock {
    public static final BooleanProperty f_60153_ = BlockStateProperties.f_61432_;
    public static final int f_155888_ = 0;
    public static final int f_155889_ = 1;
    public static final int f_155890_ = 2;
    public static final float f_155891_ = 4.0f;
    protected static final VoxelShape f_60154_ = Block.m_49796_(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
    protected static final VoxelShape f_60155_ = Block.m_49796_(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape f_60156_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
    protected static final VoxelShape f_60157_ = Block.m_49796_(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape f_60158_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    protected static final VoxelShape f_60159_ = Block.m_49796_(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    private final boolean f_60160_;

    public PistonBaseBlock(boolean p_60163_, BlockBehaviour.Properties p_60164_) {
        super(p_60164_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52588_, Direction.NORTH)).m_61124_(f_60153_, false));
        this.f_60160_ = p_60163_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_60220_, BlockGetter p_60221_, BlockPos p_60222_, CollisionContext p_60223_) {
        if (p_60220_.m_61143_(f_60153_).booleanValue()) {
            switch (p_60220_.m_61143_(f_52588_)) {
                case DOWN: {
                    return f_60159_;
                }
                default: {
                    return f_60158_;
                }
                case NORTH: {
                    return f_60157_;
                }
                case SOUTH: {
                    return f_60156_;
                }
                case WEST: {
                    return f_60155_;
                }
                case EAST: 
            }
            return f_60154_;
        }
        return Shapes.m_83144_();
    }

    @Override
    public void m_6402_(Level p_60172_, BlockPos p_60173_, BlockState p_60174_, LivingEntity p_60175_, ItemStack p_60176_) {
        if (!p_60172_.f_46443_) {
            this.m_60167_(p_60172_, p_60173_, p_60174_);
        }
    }

    @Override
    public void m_6861_(BlockState p_60198_, Level p_60199_, BlockPos p_60200_, Block p_60201_, BlockPos p_60202_, boolean p_60203_) {
        if (!p_60199_.f_46443_) {
            this.m_60167_(p_60199_, p_60200_, p_60198_);
        }
    }

    @Override
    public void m_6807_(BlockState p_60225_, Level p_60226_, BlockPos p_60227_, BlockState p_60228_, boolean p_60229_) {
        if (p_60228_.m_60713_(p_60225_.m_60734_())) {
            return;
        }
        if (!p_60226_.f_46443_ && p_60226_.m_7702_(p_60227_) == null) {
            this.m_60167_(p_60226_, p_60227_, p_60225_);
        }
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_60166_) {
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_52588_, p_60166_.m_7820_().m_122424_())).m_61124_(f_60153_, false);
    }

    private void m_60167_(Level p_60168_, BlockPos p_60169_, BlockState p_60170_) {
        Direction $$3 = p_60170_.m_61143_(f_52588_);
        boolean $$4 = this.m_60177_(p_60168_, p_60169_, $$3);
        if ($$4 && !p_60170_.m_61143_(f_60153_).booleanValue()) {
            if (new PistonStructureResolver(p_60168_, p_60169_, $$3, true).m_60422_()) {
                p_60168_.m_7696_(p_60169_, this, 0, $$3.m_122411_());
            }
        } else if (!$$4 && p_60170_.m_61143_(f_60153_).booleanValue()) {
            PistonMovingBlockEntity $$9;
            BlockEntity $$8;
            BlockPos $$5 = p_60169_.m_5484_($$3, 2);
            BlockState $$6 = p_60168_.m_8055_($$5);
            int $$7 = 1;
            if ($$6.m_60713_(Blocks.f_50110_) && $$6.m_61143_(f_52588_) == $$3 && ($$8 = p_60168_.m_7702_($$5)) instanceof PistonMovingBlockEntity && ($$9 = (PistonMovingBlockEntity)$$8).m_60387_() && ($$9.m_60350_(0.0f) < 0.5f || p_60168_.m_46467_() == $$9.m_60402_() || ((ServerLevel)p_60168_).m_8874_())) {
                $$7 = 2;
            }
            p_60168_.m_7696_(p_60169_, this, $$7, $$3.m_122411_());
        }
    }

    private boolean m_60177_(Level p_60178_, BlockPos p_60179_, Direction p_60180_) {
        for (Direction $$3 : Direction.values()) {
            if ($$3 == p_60180_ || !p_60178_.m_46616_(p_60179_.m_121945_($$3), $$3)) continue;
            return true;
        }
        if (p_60178_.m_46616_(p_60179_, Direction.DOWN)) {
            return true;
        }
        BlockPos $$4 = p_60179_.m_7494_();
        for (Direction $$5 : Direction.values()) {
            if ($$5 == Direction.DOWN || !p_60178_.m_46616_($$4.m_121945_($$5), $$5)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean m_8133_(BlockState p_60192_, Level p_60193_, BlockPos p_60194_, int p_60195_, int p_60196_) {
        Direction $$5 = p_60192_.m_61143_(f_52588_);
        if (!p_60193_.f_46443_) {
            boolean $$6 = this.m_60177_(p_60193_, p_60194_, $$5);
            if ($$6 && (p_60195_ == 1 || p_60195_ == 2)) {
                p_60193_.m_7731_(p_60194_, (BlockState)p_60192_.m_61124_(f_60153_, true), 2);
                return false;
            }
            if (!$$6 && p_60195_ == 0) {
                return false;
            }
        }
        if (p_60195_ == 0) {
            if (!this.m_60181_(p_60193_, p_60194_, $$5, true)) return false;
            p_60193_.m_7731_(p_60194_, (BlockState)p_60192_.m_61124_(f_60153_, true), 67);
            p_60193_.m_5594_(null, p_60194_, SoundEvents.f_12312_, SoundSource.BLOCKS, 0.5f, p_60193_.f_46441_.m_188501_() * 0.25f + 0.6f);
            p_60193_.m_142346_(null, GameEvent.f_157775_, p_60194_);
            return true;
        } else {
            if (p_60195_ != 1 && p_60195_ != 2) return true;
            BlockEntity $$7 = p_60193_.m_7702_(p_60194_.m_121945_($$5));
            if ($$7 instanceof PistonMovingBlockEntity) {
                ((PistonMovingBlockEntity)$$7).m_60401_();
            }
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50110_.m_49966_().m_61124_(MovingPistonBlock.f_60046_, $$5)).m_61124_(MovingPistonBlock.f_60047_, this.f_60160_ ? PistonType.STICKY : PistonType.DEFAULT);
            p_60193_.m_7731_(p_60194_, $$8, 20);
            p_60193_.m_151523_(MovingPistonBlock.m_155881_(p_60194_, $$8, (BlockState)this.m_49966_().m_61124_(f_52588_, Direction.m_122376_(p_60196_ & 7)), $$5, false, true));
            p_60193_.m_6289_(p_60194_, $$8.m_60734_());
            $$8.m_60701_(p_60193_, p_60194_, 2);
            if (this.f_60160_) {
                PistonMovingBlockEntity $$13;
                BlockEntity $$12;
                BlockPos $$9 = p_60194_.m_7918_($$5.m_122429_() * 2, $$5.m_122430_() * 2, $$5.m_122431_() * 2);
                BlockState $$10 = p_60193_.m_8055_($$9);
                boolean $$11 = false;
                if ($$10.m_60713_(Blocks.f_50110_) && ($$12 = p_60193_.m_7702_($$9)) instanceof PistonMovingBlockEntity && ($$13 = (PistonMovingBlockEntity)$$12).m_60392_() == $$5 && $$13.m_60387_()) {
                    $$13.m_60401_();
                    $$11 = true;
                }
                if (!$$11) {
                    if (p_60195_ == 1 && !$$10.m_60795_() && PistonBaseBlock.m_60204_($$10, p_60193_, $$9, $$5.m_122424_(), false, $$5) && ($$10.m_60811_() == PushReaction.NORMAL || $$10.m_60713_(Blocks.f_50039_) || $$10.m_60713_(Blocks.f_50032_))) {
                        this.m_60181_(p_60193_, p_60194_, $$5, false);
                    } else {
                        p_60193_.m_7471_(p_60194_.m_121945_($$5), false);
                    }
                }
            } else {
                p_60193_.m_7471_(p_60194_.m_121945_($$5), false);
            }
            p_60193_.m_5594_(null, p_60194_, SoundEvents.f_12311_, SoundSource.BLOCKS, 0.5f, p_60193_.f_46441_.m_188501_() * 0.15f + 0.6f);
            p_60193_.m_142346_(null, GameEvent.f_157774_, p_60194_);
        }
        return true;
    }

    public static boolean m_60204_(BlockState p_60205_, Level p_60206_, BlockPos p_60207_, Direction p_60208_, boolean p_60209_, Direction p_60210_) {
        if (p_60207_.m_123342_() < p_60206_.m_141937_() || p_60207_.m_123342_() > p_60206_.m_151558_() - 1 || !p_60206_.m_6857_().m_61937_(p_60207_)) {
            return false;
        }
        if (p_60205_.m_60795_()) {
            return true;
        }
        if (p_60205_.m_60713_(Blocks.f_50080_) || p_60205_.m_60713_(Blocks.f_50723_) || p_60205_.m_60713_(Blocks.f_50724_) || p_60205_.m_60713_(Blocks.f_220863_)) {
            return false;
        }
        if (p_60208_ == Direction.DOWN && p_60207_.m_123342_() == p_60206_.m_141937_()) {
            return false;
        }
        if (p_60208_ == Direction.UP && p_60207_.m_123342_() == p_60206_.m_151558_() - 1) {
            return false;
        }
        if (p_60205_.m_60713_(Blocks.f_50039_) || p_60205_.m_60713_(Blocks.f_50032_)) {
            if (p_60205_.m_61143_(f_60153_).booleanValue()) {
                return false;
            }
        } else {
            if (p_60205_.m_60800_(p_60206_, p_60207_) == -1.0f) {
                return false;
            }
            switch (p_60205_.m_60811_()) {
                case BLOCK: {
                    return false;
                }
                case DESTROY: {
                    return p_60209_;
                }
                case PUSH_ONLY: {
                    return p_60208_ == p_60210_;
                }
            }
        }
        return !p_60205_.m_155947_();
    }

    private boolean m_60181_(Level p_60182_, BlockPos p_60183_, Direction p_60184_, boolean p_60185_) {
        PistonStructureResolver $$5;
        BlockPos $$4 = p_60183_.m_121945_(p_60184_);
        if (!p_60185_ && p_60182_.m_8055_($$4).m_60713_(Blocks.f_50040_)) {
            p_60182_.m_7731_($$4, Blocks.f_50016_.m_49966_(), 20);
        }
        if (!($$5 = new PistonStructureResolver(p_60182_, p_60183_, p_60184_, p_60185_)).m_60422_()) {
            return false;
        }
        HashMap $$6 = Maps.newHashMap();
        List<BlockPos> $$7 = $$5.m_60436_();
        ArrayList $$8 = Lists.newArrayList();
        for (int $$9 = 0; $$9 < $$7.size(); ++$$9) {
            BlockPos $$10 = $$7.get($$9);
            BlockState $$11 = p_60182_.m_8055_($$10);
            $$8.add($$11);
            $$6.put($$10, $$11);
        }
        List<BlockPos> $$12 = $$5.m_60437_();
        BlockState[] $$13 = new BlockState[$$7.size() + $$12.size()];
        Direction $$14 = p_60185_ ? p_60184_ : p_60184_.m_122424_();
        int $$15 = 0;
        for (int $$16 = $$12.size() - 1; $$16 >= 0; --$$16) {
            BlockPos $$17 = $$12.get($$16);
            BlockState blockState = p_60182_.m_8055_($$17);
            BlockEntity $$19 = blockState.m_155947_() ? p_60182_.m_7702_($$17) : null;
            PistonBaseBlock.m_49892_(blockState, p_60182_, $$17, $$19);
            p_60182_.m_7731_($$17, Blocks.f_50016_.m_49966_(), 18);
            p_60182_.m_220407_(GameEvent.f_157794_, $$17, GameEvent.Context.m_223722_(blockState));
            if (!blockState.m_204336_(BlockTags.f_13076_)) {
                p_60182_.m_142052_($$17, blockState);
            }
            $$13[$$15++] = blockState;
        }
        for (int $$20 = $$7.size() - 1; $$20 >= 0; --$$20) {
            BlockPos $$21 = $$7.get($$20);
            BlockState blockState = p_60182_.m_8055_($$21);
            $$21 = $$21.m_121945_($$14);
            $$6.remove($$21);
            BlockState $$23 = (BlockState)Blocks.f_50110_.m_49966_().m_61124_(f_52588_, p_60184_);
            p_60182_.m_7731_($$21, $$23, 68);
            p_60182_.m_151523_(MovingPistonBlock.m_155881_($$21, $$23, (BlockState)$$8.get($$20), p_60184_, p_60185_, false));
            $$13[$$15++] = blockState;
        }
        if (p_60185_) {
            PistonType $$24 = this.f_60160_ ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState $$25 = (BlockState)((BlockState)Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_52588_, p_60184_)).m_61124_(PistonHeadBlock.f_60235_, $$24);
            BlockState blockState = (BlockState)((BlockState)Blocks.f_50110_.m_49966_().m_61124_(MovingPistonBlock.f_60046_, p_60184_)).m_61124_(MovingPistonBlock.f_60047_, this.f_60160_ ? PistonType.STICKY : PistonType.DEFAULT);
            $$6.remove($$4);
            p_60182_.m_7731_($$4, blockState, 68);
            p_60182_.m_151523_(MovingPistonBlock.m_155881_($$4, blockState, $$25, p_60184_, true, true));
        }
        BlockState $$27 = Blocks.f_50016_.m_49966_();
        for (BlockPos blockPos : $$6.keySet()) {
            p_60182_.m_7731_(blockPos, $$27, 82);
        }
        for (Map.Entry entry : $$6.entrySet()) {
            BlockPos $$30 = (BlockPos)entry.getKey();
            BlockState $$31 = (BlockState)entry.getValue();
            $$31.m_60758_(p_60182_, $$30, 2);
            $$27.m_60701_(p_60182_, $$30, 2);
            $$27.m_60758_(p_60182_, $$30, 2);
        }
        $$15 = 0;
        for (int $$32 = $$12.size() - 1; $$32 >= 0; --$$32) {
            BlockState blockState = $$13[$$15++];
            BlockPos $$34 = $$12.get($$32);
            blockState.m_60758_(p_60182_, $$34, 2);
            p_60182_.m_46672_($$34, blockState.m_60734_());
        }
        for (int $$35 = $$7.size() - 1; $$35 >= 0; --$$35) {
            p_60182_.m_46672_($$7.get($$35), $$13[$$15++].m_60734_());
        }
        if (p_60185_) {
            p_60182_.m_46672_($$4, Blocks.f_50040_);
        }
        return true;
    }

    @Override
    public BlockState m_6843_(BlockState p_60215_, Rotation p_60216_) {
        return (BlockState)p_60215_.m_61124_(f_52588_, p_60216_.m_55954_(p_60215_.m_61143_(f_52588_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_60212_, Mirror p_60213_) {
        return p_60212_.m_60717_(p_60213_.m_54846_(p_60212_.m_61143_(f_52588_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_60218_) {
        p_60218_.m_61104_(f_52588_, f_60153_);
    }

    @Override
    public boolean m_7923_(BlockState p_60231_) {
        return p_60231_.m_61143_(f_60153_);
    }

    @Override
    public boolean m_7357_(BlockState p_60187_, BlockGetter p_60188_, BlockPos p_60189_, PathComputationType p_60190_) {
        return false;
    }
}

