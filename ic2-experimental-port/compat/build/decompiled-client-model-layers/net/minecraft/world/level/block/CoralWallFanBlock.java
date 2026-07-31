/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class CoralWallFanBlock
extends BaseCoralWallFanBlock {
    private final Block f_52200_;

    protected CoralWallFanBlock(Block p_52202_, BlockBehaviour.Properties p_52203_) {
        super(p_52203_);
        this.f_52200_ = p_52202_;
    }

    @Override
    public void m_6807_(BlockState p_52217_, Level p_52218_, BlockPos p_52219_, BlockState p_52220_, boolean p_52221_) {
        this.m_49164_(p_52217_, p_52218_, p_52219_);
    }

    @Override
    public void m_213897_(BlockState p_221035_, ServerLevel p_221036_, BlockPos p_221037_, RandomSource p_221038_) {
        if (!CoralWallFanBlock.m_49186_(p_221035_, p_221036_, p_221037_)) {
            p_221036_.m_7731_(p_221037_, (BlockState)((BlockState)this.f_52200_.m_49966_().m_61124_(f_49158_, false)).m_61124_(f_49192_, p_221035_.m_61143_(f_49192_)), 2);
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_52210_, Direction p_52211_, BlockState p_52212_, LevelAccessor p_52213_, BlockPos p_52214_, BlockPos p_52215_) {
        if (p_52211_.m_122424_() == p_52210_.m_61143_(f_49192_) && !p_52210_.m_60710_(p_52213_, p_52214_)) {
            return Blocks.f_50016_.m_49966_();
        }
        if (p_52210_.m_61143_(f_49158_).booleanValue()) {
            p_52213_.m_186469_(p_52214_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_52213_));
        }
        this.m_49164_(p_52210_, p_52213_, p_52214_);
        return super.m_7417_(p_52210_, p_52211_, p_52212_, p_52213_, p_52214_, p_52215_);
    }
}

