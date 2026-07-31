/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaplingBlock
extends BushBlock
implements BonemealableBlock {
    public static final IntegerProperty f_55973_ = BlockStateProperties.f_61387_;
    protected static final float f_154380_ = 6.0f;
    protected static final VoxelShape f_55974_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    private final AbstractTreeGrower f_55975_;

    protected SaplingBlock(AbstractTreeGrower p_55978_, BlockBehaviour.Properties p_55979_) {
        super(p_55979_);
        this.f_55975_ = p_55978_;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55973_, 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56008_, BlockGetter p_56009_, BlockPos p_56010_, CollisionContext p_56011_) {
        return f_55974_;
    }

    @Override
    public void m_213898_(BlockState p_222011_, ServerLevel p_222012_, BlockPos p_222013_, RandomSource p_222014_) {
        if (p_222012_.m_46803_(p_222013_.m_7494_()) >= 9 && p_222014_.m_188503_(7) == 0) {
            this.m_222000_(p_222012_, p_222013_, p_222011_, p_222014_);
        }
    }

    public void m_222000_(ServerLevel p_222001_, BlockPos p_222002_, BlockState p_222003_, RandomSource p_222004_) {
        if (p_222003_.m_61143_(f_55973_) == 0) {
            p_222001_.m_7731_(p_222002_, (BlockState)p_222003_.m_61122_(f_55973_), 4);
        } else {
            this.f_55975_.m_213817_(p_222001_, p_222001_.m_7726_().m_8481_(), p_222002_, p_222003_, p_222004_);
        }
    }

    @Override
    public boolean m_7370_(BlockGetter p_55991_, BlockPos p_55992_, BlockState p_55993_, boolean p_55994_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_222006_, RandomSource p_222007_, BlockPos p_222008_, BlockState p_222009_) {
        return (double)p_222006_.f_46441_.m_188501_() < 0.45;
    }

    @Override
    public void m_214148_(ServerLevel p_221996_, RandomSource p_221997_, BlockPos p_221998_, BlockState p_221999_) {
        this.m_222000_(p_221996_, p_221998_, p_221999_, p_221997_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56001_) {
        p_56001_.m_61104_(f_55973_);
    }
}

