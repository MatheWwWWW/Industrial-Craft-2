/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ConcretePowderBlock
extends FallingBlock {
    private final BlockState f_52058_;

    public ConcretePowderBlock(Block p_52060_, BlockBehaviour.Properties p_52061_) {
        super(p_52061_);
        this.f_52058_ = p_52060_.m_49966_();
    }

    @Override
    public void m_48792_(Level p_52068_, BlockPos p_52069_, BlockState p_52070_, BlockState p_52071_, FallingBlockEntity p_52072_) {
        if (ConcretePowderBlock.m_52080_(p_52068_, p_52069_, p_52071_)) {
            p_52068_.m_7731_(p_52069_, this.f_52058_, 3);
        }
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_52063_) {
        BlockState $$3;
        BlockPos $$2;
        Level $$1 = p_52063_.m_43725_();
        if (ConcretePowderBlock.m_52080_($$1, $$2 = p_52063_.m_8083_(), $$3 = $$1.m_8055_($$2))) {
            return this.f_52058_;
        }
        return super.m_5573_(p_52063_);
    }

    private static boolean m_52080_(BlockGetter p_52081_, BlockPos p_52082_, BlockState p_52083_) {
        return ConcretePowderBlock.m_52088_(p_52083_) || ConcretePowderBlock.m_52064_(p_52081_, p_52082_);
    }

    private static boolean m_52064_(BlockGetter p_52065_, BlockPos p_52066_) {
        boolean $$2 = false;
        BlockPos.MutableBlockPos $$3 = p_52066_.m_122032_();
        for (Direction $$4 : Direction.values()) {
            BlockState $$5 = p_52065_.m_8055_($$3);
            if ($$4 == Direction.DOWN && !ConcretePowderBlock.m_52088_($$5)) continue;
            $$3.m_122159_(p_52066_, $$4);
            $$5 = p_52065_.m_8055_($$3);
            if (!ConcretePowderBlock.m_52088_($$5) || $$5.m_60783_(p_52065_, p_52066_, $$4.m_122424_())) continue;
            $$2 = true;
            break;
        }
        return $$2;
    }

    private static boolean m_52088_(BlockState p_52089_) {
        return p_52089_.m_60819_().m_205070_(FluidTags.f_13131_);
    }

    @Override
    public BlockState m_7417_(BlockState p_52074_, Direction p_52075_, BlockState p_52076_, LevelAccessor p_52077_, BlockPos p_52078_, BlockPos p_52079_) {
        if (ConcretePowderBlock.m_52064_(p_52077_, p_52078_)) {
            return this.f_52058_;
        }
        return super.m_7417_(p_52074_, p_52075_, p_52076_, p_52077_, p_52078_, p_52079_);
    }

    @Override
    public int m_6248_(BlockState p_52085_, BlockGetter p_52086_, BlockPos p_52087_) {
        return p_52085_.m_60780_((BlockGetter)p_52086_, (BlockPos)p_52087_).f_76396_;
    }
}

