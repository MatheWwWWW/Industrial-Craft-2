/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class HugeMushroomBlock
extends Block {
    public static final BooleanProperty f_54127_ = PipeBlock.f_55148_;
    public static final BooleanProperty f_54128_ = PipeBlock.f_55149_;
    public static final BooleanProperty f_54129_ = PipeBlock.f_55150_;
    public static final BooleanProperty f_54130_ = PipeBlock.f_55151_;
    public static final BooleanProperty f_54131_ = PipeBlock.f_55152_;
    public static final BooleanProperty f_54132_ = PipeBlock.f_55153_;
    private static final Map<Direction, BooleanProperty> f_54133_ = PipeBlock.f_55154_;

    public HugeMushroomBlock(BlockBehaviour.Properties p_54136_) {
        super(p_54136_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54127_, true)).m_61124_(f_54128_, true)).m_61124_(f_54129_, true)).m_61124_(f_54130_, true)).m_61124_(f_54131_, true)).m_61124_(f_54132_, true));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54138_) {
        Level $$1 = p_54138_.m_43725_();
        BlockPos $$2 = p_54138_.m_8083_();
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_54132_, !$$1.m_8055_($$2.m_7495_()).m_60713_(this))).m_61124_(f_54131_, !$$1.m_8055_($$2.m_7494_()).m_60713_(this))).m_61124_(f_54127_, !$$1.m_8055_($$2.m_122012_()).m_60713_(this))).m_61124_(f_54128_, !$$1.m_8055_($$2.m_122029_()).m_60713_(this))).m_61124_(f_54129_, !$$1.m_8055_($$2.m_122019_()).m_60713_(this))).m_61124_(f_54130_, !$$1.m_8055_($$2.m_122024_()).m_60713_(this));
    }

    @Override
    public BlockState m_7417_(BlockState p_54146_, Direction p_54147_, BlockState p_54148_, LevelAccessor p_54149_, BlockPos p_54150_, BlockPos p_54151_) {
        if (p_54148_.m_60713_(this)) {
            return (BlockState)p_54146_.m_61124_(f_54133_.get(p_54147_), false);
        }
        return super.m_7417_(p_54146_, p_54147_, p_54148_, p_54149_, p_54150_, p_54151_);
    }

    @Override
    public BlockState m_6843_(BlockState p_54143_, Rotation p_54144_) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)p_54143_.m_61124_(f_54133_.get(p_54144_.m_55954_(Direction.NORTH)), p_54143_.m_61143_(f_54127_))).m_61124_(f_54133_.get(p_54144_.m_55954_(Direction.SOUTH)), p_54143_.m_61143_(f_54129_))).m_61124_(f_54133_.get(p_54144_.m_55954_(Direction.EAST)), p_54143_.m_61143_(f_54128_))).m_61124_(f_54133_.get(p_54144_.m_55954_(Direction.WEST)), p_54143_.m_61143_(f_54130_))).m_61124_(f_54133_.get(p_54144_.m_55954_(Direction.UP)), p_54143_.m_61143_(f_54131_))).m_61124_(f_54133_.get(p_54144_.m_55954_(Direction.DOWN)), p_54143_.m_61143_(f_54132_));
    }

    @Override
    public BlockState m_6943_(BlockState p_54140_, Mirror p_54141_) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)p_54140_.m_61124_(f_54133_.get(p_54141_.m_54848_(Direction.NORTH)), p_54140_.m_61143_(f_54127_))).m_61124_(f_54133_.get(p_54141_.m_54848_(Direction.SOUTH)), p_54140_.m_61143_(f_54129_))).m_61124_(f_54133_.get(p_54141_.m_54848_(Direction.EAST)), p_54140_.m_61143_(f_54128_))).m_61124_(f_54133_.get(p_54141_.m_54848_(Direction.WEST)), p_54140_.m_61143_(f_54130_))).m_61124_(f_54133_.get(p_54141_.m_54848_(Direction.UP)), p_54140_.m_61143_(f_54131_))).m_61124_(f_54133_.get(p_54141_.m_54848_(Direction.DOWN)), p_54140_.m_61143_(f_54132_));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54153_) {
        p_54153_.m_61104_(f_54131_, f_54132_, f_54127_, f_54128_, f_54129_, f_54130_);
    }
}

