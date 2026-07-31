/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoralPlantBlock
extends BaseCoralPlantTypeBlock {
    private final Block f_52172_;
    protected static final float f_153100_ = 6.0f;
    protected static final VoxelShape f_52171_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);

    protected CoralPlantBlock(Block p_52175_, BlockBehaviour.Properties p_52176_) {
        super(p_52176_);
        this.f_52172_ = p_52175_;
    }

    @Override
    public void m_6807_(BlockState p_52195_, Level p_52196_, BlockPos p_52197_, BlockState p_52198_, boolean p_52199_) {
        this.m_49164_(p_52195_, p_52196_, p_52197_);
    }

    @Override
    public void m_213897_(BlockState p_221030_, ServerLevel p_221031_, BlockPos p_221032_, RandomSource p_221033_) {
        if (!CoralPlantBlock.m_49186_(p_221030_, p_221031_, p_221032_)) {
            p_221031_.m_7731_(p_221032_, (BlockState)this.f_52172_.m_49966_().m_61124_(f_49158_, false), 2);
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_52183_, Direction p_52184_, BlockState p_52185_, LevelAccessor p_52186_, BlockPos p_52187_, BlockPos p_52188_) {
        if (p_52184_ == Direction.DOWN && !p_52183_.m_60710_(p_52186_, p_52187_)) {
            return Blocks.f_50016_.m_49966_();
        }
        this.m_49164_(p_52183_, p_52186_, p_52187_);
        if (p_52183_.m_61143_(f_49158_).booleanValue()) {
            p_52186_.m_186469_(p_52187_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_52186_));
        }
        return super.m_7417_(p_52183_, p_52184_, p_52185_, p_52186_, p_52187_, p_52188_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52190_, BlockGetter p_52191_, BlockPos p_52192_, CollisionContext p_52193_) {
        return f_52171_;
    }
}

