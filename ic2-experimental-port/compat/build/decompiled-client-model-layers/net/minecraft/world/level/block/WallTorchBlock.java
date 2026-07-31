/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallTorchBlock
extends TorchBlock {
    public static final DirectionProperty f_58119_ = HorizontalDirectionalBlock.f_54117_;
    protected static final float f_154885_ = 2.5f;
    private static final Map<Direction, VoxelShape> f_58120_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.m_49796_(5.5, 3.0, 11.0, 10.5, 13.0, 16.0), (Object)Direction.SOUTH, (Object)Block.m_49796_(5.5, 3.0, 0.0, 10.5, 13.0, 5.0), (Object)Direction.WEST, (Object)Block.m_49796_(11.0, 3.0, 5.5, 16.0, 13.0, 10.5), (Object)Direction.EAST, (Object)Block.m_49796_(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)));

    protected WallTorchBlock(BlockBehaviour.Properties p_58123_, ParticleOptions p_58124_) {
        super(p_58123_, p_58124_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_58119_, Direction.NORTH));
    }

    @Override
    public String m_7705_() {
        return this.m_5456_().m_5524_();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_58152_, BlockGetter p_58153_, BlockPos p_58154_, CollisionContext p_58155_) {
        return WallTorchBlock.m_58156_(p_58152_);
    }

    public static VoxelShape m_58156_(BlockState p_58157_) {
        return f_58120_.get(p_58157_.m_61143_(f_58119_));
    }

    @Override
    public boolean m_7898_(BlockState p_58133_, LevelReader p_58134_, BlockPos p_58135_) {
        Direction $$3 = p_58133_.m_61143_(f_58119_);
        BlockPos $$4 = p_58135_.m_121945_($$3.m_122424_());
        BlockState $$5 = p_58134_.m_8055_($$4);
        return $$5.m_60783_(p_58134_, $$4, $$3);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_58126_) {
        Direction[] $$4;
        BlockState $$1 = this.m_49966_();
        Level $$2 = p_58126_.m_43725_();
        BlockPos $$3 = p_58126_.m_8083_();
        for (Direction $$5 : $$4 = p_58126_.m_6232_()) {
            Direction $$6;
            if (!$$5.m_122434_().m_122479_() || !($$1 = (BlockState)$$1.m_61124_(f_58119_, $$6 = $$5.m_122424_())).m_60710_($$2, $$3)) continue;
            return $$1;
        }
        return null;
    }

    @Override
    public BlockState m_7417_(BlockState p_58143_, Direction p_58144_, BlockState p_58145_, LevelAccessor p_58146_, BlockPos p_58147_, BlockPos p_58148_) {
        if (p_58144_.m_122424_() == p_58143_.m_61143_(f_58119_) && !p_58143_.m_60710_(p_58146_, p_58147_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return p_58143_;
    }

    @Override
    public void m_214162_(BlockState p_222660_, Level p_222661_, BlockPos p_222662_, RandomSource p_222663_) {
        Direction $$4 = p_222660_.m_61143_(f_58119_);
        double $$5 = (double)p_222662_.m_123341_() + 0.5;
        double $$6 = (double)p_222662_.m_123342_() + 0.7;
        double $$7 = (double)p_222662_.m_123343_() + 0.5;
        double $$8 = 0.22;
        double $$9 = 0.27;
        Direction $$10 = $$4.m_122424_();
        p_222661_.m_7106_(ParticleTypes.f_123762_, $$5 + 0.27 * (double)$$10.m_122429_(), $$6 + 0.22, $$7 + 0.27 * (double)$$10.m_122431_(), 0.0, 0.0, 0.0);
        p_222661_.m_7106_(this.f_57488_, $$5 + 0.27 * (double)$$10.m_122429_(), $$6 + 0.22, $$7 + 0.27 * (double)$$10.m_122431_(), 0.0, 0.0, 0.0);
    }

    @Override
    public BlockState m_6843_(BlockState p_58140_, Rotation p_58141_) {
        return (BlockState)p_58140_.m_61124_(f_58119_, p_58141_.m_55954_(p_58140_.m_61143_(f_58119_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_58137_, Mirror p_58138_) {
        return p_58137_.m_60717_(p_58138_.m_54846_(p_58137_.m_61143_(f_58119_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58150_) {
        p_58150_.m_61104_(f_58119_);
    }
}

