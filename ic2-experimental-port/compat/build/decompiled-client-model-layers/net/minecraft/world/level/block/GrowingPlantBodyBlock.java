/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantBodyBlock
extends GrowingPlantBlock
implements BonemealableBlock {
    protected GrowingPlantBodyBlock(BlockBehaviour.Properties p_53886_, Direction p_53887_, VoxelShape p_53888_, boolean p_53889_) {
        super(p_53886_, p_53887_, p_53888_, p_53889_);
    }

    protected BlockState m_142644_(BlockState p_153326_, BlockState p_153327_) {
        return p_153327_;
    }

    @Override
    public BlockState m_7417_(BlockState p_53913_, Direction p_53914_, BlockState p_53915_, LevelAccessor p_53916_, BlockPos p_53917_, BlockPos p_53918_) {
        if (p_53914_ == this.f_53859_.m_122424_() && !p_53913_.m_60710_(p_53916_, p_53917_)) {
            p_53916_.m_186460_(p_53917_, this, 1);
        }
        GrowingPlantHeadBlock $$6 = this.m_7272_();
        if (p_53914_ == this.f_53859_ && !p_53915_.m_60713_(this) && !p_53915_.m_60713_($$6)) {
            return this.m_142644_(p_53913_, $$6.m_7722_(p_53916_));
        }
        if (this.f_53860_) {
            p_53916_.m_186469_(p_53917_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53916_));
        }
        return super.m_7417_(p_53913_, p_53914_, p_53915_, p_53916_, p_53917_, p_53918_);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_53896_, BlockPos p_53897_, BlockState p_53898_) {
        return new ItemStack(this.m_7272_());
    }

    @Override
    public boolean m_7370_(BlockGetter p_53900_, BlockPos p_53901_, BlockState p_53902_, boolean p_53903_) {
        Optional<BlockPos> $$4 = this.m_153322_(p_53900_, p_53901_, p_53902_.m_60734_());
        return $$4.isPresent() && this.m_7272_().m_5971_(p_53900_.m_8055_($$4.get().m_121945_(this.f_53859_)));
    }

    @Override
    public boolean m_214167_(Level p_221290_, RandomSource p_221291_, BlockPos p_221292_, BlockState p_221293_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221285_, RandomSource p_221286_, BlockPos p_221287_, BlockState p_221288_) {
        Optional<BlockPos> $$4 = this.m_153322_(p_221285_, p_221287_, p_221288_.m_60734_());
        if ($$4.isPresent()) {
            BlockState $$5 = p_221285_.m_8055_($$4.get());
            ((GrowingPlantHeadBlock)$$5.m_60734_()).m_214148_(p_221285_, p_221286_, $$4.get(), $$5);
        }
    }

    private Optional<BlockPos> m_153322_(BlockGetter p_153323_, BlockPos p_153324_, Block p_153325_) {
        return BlockUtil.m_177845_(p_153323_, p_153324_, p_153325_, this.f_53859_, this.m_7272_());
    }

    @Override
    public boolean m_6864_(BlockState p_53910_, BlockPlaceContext p_53911_) {
        boolean $$2 = super.m_6864_(p_53910_, p_53911_);
        if ($$2 && p_53911_.m_43722_().m_150930_(this.m_7272_().m_5456_())) {
            return false;
        }
        return $$2;
    }

    @Override
    protected Block m_7777_() {
        return this;
    }
}

