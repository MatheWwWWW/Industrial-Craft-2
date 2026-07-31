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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class MangroveRootsBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_221503_ = BlockStateProperties.f_61362_;

    protected MangroveRootsBlock(BlockBehaviour.Properties p_221506_) {
        super(p_221506_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_221503_, false));
    }

    @Override
    public boolean m_6104_(BlockState p_221510_, BlockState p_221511_, Direction p_221512_) {
        return p_221511_.m_60713_(Blocks.f_220833_) && p_221512_.m_122434_() == Direction.Axis.Y;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_221508_) {
        FluidState $$1 = p_221508_.m_43725_().m_6425_(p_221508_.m_8083_());
        boolean $$2 = $$1.m_76152_() == Fluids.f_76193_;
        return (BlockState)super.m_5573_(p_221508_).m_61124_(f_221503_, $$2);
    }

    @Override
    public BlockState m_7417_(BlockState p_221514_, Direction p_221515_, BlockState p_221516_, LevelAccessor p_221517_, BlockPos p_221518_, BlockPos p_221519_) {
        if (p_221514_.m_61143_(f_221503_).booleanValue()) {
            p_221517_.m_186469_(p_221518_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_221517_));
        }
        return super.m_7417_(p_221514_, p_221515_, p_221516_, p_221517_, p_221518_, p_221519_);
    }

    @Override
    public FluidState m_5888_(BlockState p_221523_) {
        if (p_221523_.m_61143_(f_221503_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_221523_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_221521_) {
        p_221521_.m_61104_(f_221503_);
    }
}

