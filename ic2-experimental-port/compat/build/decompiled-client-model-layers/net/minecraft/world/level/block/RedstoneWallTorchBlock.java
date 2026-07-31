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
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneWallTorchBlock
extends RedstoneTorchBlock {
    public static final DirectionProperty f_55740_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_55741_ = RedstoneTorchBlock.f_55674_;

    protected RedstoneWallTorchBlock(BlockBehaviour.Properties p_55744_) {
        super(p_55744_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55740_, Direction.NORTH)).m_61124_(f_55741_, true));
    }

    @Override
    public String m_7705_() {
        return this.m_5456_().m_5524_();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_55781_, BlockGetter p_55782_, BlockPos p_55783_, CollisionContext p_55784_) {
        return WallTorchBlock.m_58156_(p_55781_);
    }

    @Override
    public boolean m_7898_(BlockState p_55762_, LevelReader p_55763_, BlockPos p_55764_) {
        return Blocks.f_50082_.m_7898_(p_55762_, p_55763_, p_55764_);
    }

    @Override
    public BlockState m_7417_(BlockState p_55772_, Direction p_55773_, BlockState p_55774_, LevelAccessor p_55775_, BlockPos p_55776_, BlockPos p_55777_) {
        return Blocks.f_50082_.m_7417_(p_55772_, p_55773_, p_55774_, p_55775_, p_55776_, p_55777_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_55746_) {
        BlockState $$1 = Blocks.f_50082_.m_5573_(p_55746_);
        return $$1 == null ? null : (BlockState)this.m_49966_().m_61124_(f_55740_, $$1.m_61143_(f_55740_));
    }

    @Override
    public void m_214162_(BlockState p_221959_, Level p_221960_, BlockPos p_221961_, RandomSource p_221962_) {
        if (!p_221959_.m_61143_(f_55741_).booleanValue()) {
            return;
        }
        Direction $$4 = p_221959_.m_61143_(f_55740_).m_122424_();
        double $$5 = 0.27;
        double $$6 = (double)p_221961_.m_123341_() + 0.5 + (p_221962_.m_188500_() - 0.5) * 0.2 + 0.27 * (double)$$4.m_122429_();
        double $$7 = (double)p_221961_.m_123342_() + 0.7 + (p_221962_.m_188500_() - 0.5) * 0.2 + 0.22;
        double $$8 = (double)p_221961_.m_123343_() + 0.5 + (p_221962_.m_188500_() - 0.5) * 0.2 + 0.27 * (double)$$4.m_122431_();
        p_221960_.m_7106_(this.f_57488_, $$6, $$7, $$8, 0.0, 0.0, 0.0);
    }

    @Override
    protected boolean m_6918_(Level p_55748_, BlockPos p_55749_, BlockState p_55750_) {
        Direction $$3 = p_55750_.m_61143_(f_55740_).m_122424_();
        return p_55748_.m_46616_(p_55749_.m_121945_($$3), $$3);
    }

    @Override
    public int m_6378_(BlockState p_55752_, BlockGetter p_55753_, BlockPos p_55754_, Direction p_55755_) {
        if (p_55752_.m_61143_(f_55741_).booleanValue() && p_55752_.m_61143_(f_55740_) != p_55755_) {
            return 15;
        }
        return 0;
    }

    @Override
    public BlockState m_6843_(BlockState p_55769_, Rotation p_55770_) {
        return Blocks.f_50082_.m_6843_(p_55769_, p_55770_);
    }

    @Override
    public BlockState m_6943_(BlockState p_55766_, Mirror p_55767_) {
        return Blocks.f_50082_.m_6943_(p_55766_, p_55767_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55779_) {
        p_55779_.m_61104_(f_55740_, f_55741_);
    }
}

