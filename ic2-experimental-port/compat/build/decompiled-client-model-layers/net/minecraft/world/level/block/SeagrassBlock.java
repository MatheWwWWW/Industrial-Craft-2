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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeagrassBlock
extends BushBlock
implements BonemealableBlock,
LiquidBlockContainer {
    protected static final float f_154492_ = 6.0f;
    protected static final VoxelShape f_154493_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    protected SeagrassBlock(BlockBehaviour.Properties p_154496_) {
        super(p_154496_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_154525_, BlockGetter p_154526_, BlockPos p_154527_, CollisionContext p_154528_) {
        return f_154493_;
    }

    @Override
    protected boolean m_6266_(BlockState p_154539_, BlockGetter p_154540_, BlockPos p_154541_) {
        return p_154539_.m_60783_(p_154540_, p_154541_, Direction.UP) && !p_154539_.m_60713_(Blocks.f_50450_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_154503_) {
        FluidState $$1 = p_154503_.m_43725_().m_6425_(p_154503_.m_8083_());
        if ($$1.m_205070_(FluidTags.f_13131_) && $$1.m_76186_() == 8) {
            return super.m_5573_(p_154503_);
        }
        return null;
    }

    @Override
    public BlockState m_7417_(BlockState p_154530_, Direction p_154531_, BlockState p_154532_, LevelAccessor p_154533_, BlockPos p_154534_, BlockPos p_154535_) {
        BlockState $$6 = super.m_7417_(p_154530_, p_154531_, p_154532_, p_154533_, p_154534_, p_154535_);
        if (!$$6.m_60795_()) {
            p_154533_.m_186469_(p_154534_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154533_));
        }
        return $$6;
    }

    @Override
    public boolean m_7370_(BlockGetter p_154510_, BlockPos p_154511_, BlockState p_154512_, boolean p_154513_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_222428_, RandomSource p_222429_, BlockPos p_222430_, BlockState p_222431_) {
        return true;
    }

    @Override
    public FluidState m_5888_(BlockState p_154537_) {
        return Fluids.f_76193_.m_76068_(false);
    }

    @Override
    public void m_214148_(ServerLevel p_222423_, RandomSource p_222424_, BlockPos p_222425_, BlockState p_222426_) {
        BlockState $$4 = Blocks.f_50038_.m_49966_();
        BlockState $$5 = (BlockState)$$4.m_61124_(TallSeagrassBlock.f_154740_, DoubleBlockHalf.UPPER);
        BlockPos $$6 = p_222425_.m_7494_();
        if (p_222423_.m_8055_($$6).m_60713_(Blocks.f_49990_)) {
            p_222423_.m_7731_(p_222425_, $$4, 2);
            p_222423_.m_7731_($$6, $$5, 2);
        }
    }

    @Override
    public boolean m_6044_(BlockGetter p_154505_, BlockPos p_154506_, BlockState p_154507_, Fluid p_154508_) {
        return false;
    }

    @Override
    public boolean m_7361_(LevelAccessor p_154520_, BlockPos p_154521_, BlockState p_154522_, FluidState p_154523_) {
        return false;
    }
}

