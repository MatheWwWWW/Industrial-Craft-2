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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class DoublePlantBlock
extends BushBlock {
    public static final EnumProperty<DoubleBlockHalf> f_52858_ = BlockStateProperties.f_61401_;

    public DoublePlantBlock(BlockBehaviour.Properties p_52861_) {
        super(p_52861_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52858_, DoubleBlockHalf.LOWER));
    }

    @Override
    public BlockState m_7417_(BlockState p_52894_, Direction p_52895_, BlockState p_52896_, LevelAccessor p_52897_, BlockPos p_52898_, BlockPos p_52899_) {
        DoubleBlockHalf $$6 = p_52894_.m_61143_(f_52858_);
        if (!(p_52895_.m_122434_() != Direction.Axis.Y || $$6 == DoubleBlockHalf.LOWER != (p_52895_ == Direction.UP) || p_52896_.m_60713_(this) && p_52896_.m_61143_(f_52858_) != $$6)) {
            return Blocks.f_50016_.m_49966_();
        }
        if ($$6 == DoubleBlockHalf.LOWER && p_52895_ == Direction.DOWN && !p_52894_.m_60710_(p_52897_, p_52898_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_52894_, p_52895_, p_52896_, p_52897_, p_52898_, p_52899_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_52863_) {
        BlockPos $$1 = p_52863_.m_8083_();
        Level $$2 = p_52863_.m_43725_();
        if ($$1.m_123342_() < $$2.m_151558_() - 1 && $$2.m_8055_($$1.m_7494_()).m_60629_(p_52863_)) {
            return super.m_5573_(p_52863_);
        }
        return null;
    }

    @Override
    public void m_6402_(Level p_52872_, BlockPos p_52873_, BlockState p_52874_, LivingEntity p_52875_, ItemStack p_52876_) {
        BlockPos $$5 = p_52873_.m_7494_();
        p_52872_.m_7731_($$5, DoublePlantBlock.m_182453_(p_52872_, $$5, (BlockState)this.m_49966_().m_61124_(f_52858_, DoubleBlockHalf.UPPER)), 3);
    }

    @Override
    public boolean m_7898_(BlockState p_52887_, LevelReader p_52888_, BlockPos p_52889_) {
        if (p_52887_.m_61143_(f_52858_) == DoubleBlockHalf.UPPER) {
            BlockState $$3 = p_52888_.m_8055_(p_52889_.m_7495_());
            return $$3.m_60713_(this) && $$3.m_61143_(f_52858_) == DoubleBlockHalf.LOWER;
        }
        return super.m_7898_(p_52887_, p_52888_, p_52889_);
    }

    public static void m_153173_(LevelAccessor p_153174_, BlockState p_153175_, BlockPos p_153176_, int p_153177_) {
        BlockPos $$4 = p_153176_.m_7494_();
        p_153174_.m_7731_(p_153176_, DoublePlantBlock.m_182453_(p_153174_, p_153176_, (BlockState)p_153175_.m_61124_(f_52858_, DoubleBlockHalf.LOWER)), p_153177_);
        p_153174_.m_7731_($$4, DoublePlantBlock.m_182453_(p_153174_, $$4, (BlockState)p_153175_.m_61124_(f_52858_, DoubleBlockHalf.UPPER)), p_153177_);
    }

    public static BlockState m_182453_(LevelReader p_182454_, BlockPos p_182455_, BlockState p_182456_) {
        if (p_182456_.m_61138_(BlockStateProperties.f_61362_)) {
            return (BlockState)p_182456_.m_61124_(BlockStateProperties.f_61362_, p_182454_.m_46801_(p_182455_));
        }
        return p_182456_;
    }

    @Override
    public void m_5707_(Level p_52878_, BlockPos p_52879_, BlockState p_52880_, Player p_52881_) {
        if (!p_52878_.f_46443_) {
            if (p_52881_.m_7500_()) {
                DoublePlantBlock.m_52903_(p_52878_, p_52879_, p_52880_, p_52881_);
            } else {
                DoublePlantBlock.m_49881_(p_52880_, p_52878_, p_52879_, null, p_52881_, p_52881_.m_21205_());
            }
        }
        super.m_5707_(p_52878_, p_52879_, p_52880_, p_52881_);
    }

    @Override
    public void m_6240_(Level p_52865_, Player p_52866_, BlockPos p_52867_, BlockState p_52868_, @Nullable BlockEntity p_52869_, ItemStack p_52870_) {
        super.m_6240_(p_52865_, p_52866_, p_52867_, Blocks.f_50016_.m_49966_(), p_52869_, p_52870_);
    }

    protected static void m_52903_(Level p_52904_, BlockPos p_52905_, BlockState p_52906_, Player p_52907_) {
        BlockPos $$5;
        BlockState $$6;
        DoubleBlockHalf $$4 = p_52906_.m_61143_(f_52858_);
        if ($$4 == DoubleBlockHalf.UPPER && ($$6 = p_52904_.m_8055_($$5 = p_52905_.m_7495_())).m_60713_(p_52906_.m_60734_()) && $$6.m_61143_(f_52858_) == DoubleBlockHalf.LOWER) {
            BlockState $$7 = $$6.m_61138_(BlockStateProperties.f_61362_) && $$6.m_61143_(BlockStateProperties.f_61362_) != false ? Blocks.f_49990_.m_49966_() : Blocks.f_50016_.m_49966_();
            p_52904_.m_7731_($$5, $$7, 35);
            p_52904_.m_5898_(p_52907_, 2001, $$5, Block.m_49956_($$6));
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52901_) {
        p_52901_.m_61104_(f_52858_);
    }

    @Override
    public long m_7799_(BlockState p_52891_, BlockPos p_52892_) {
        return Mth.m_14130_(p_52892_.m_123341_(), p_52892_.m_6625_(p_52891_.m_61143_(f_52858_) == DoubleBlockHalf.LOWER ? 0 : 1).m_123342_(), p_52892_.m_123343_());
    }
}

