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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantHeadBlock
extends GrowingPlantBlock
implements BonemealableBlock {
    public static final IntegerProperty f_53924_ = BlockStateProperties.f_61411_;
    public static final int f_153328_ = 25;
    private final double f_53925_;

    protected GrowingPlantHeadBlock(BlockBehaviour.Properties p_53928_, Direction p_53929_, VoxelShape p_53930_, boolean p_53931_, double p_53932_) {
        super(p_53928_, p_53929_, p_53930_, p_53931_);
        this.f_53925_ = p_53932_;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53924_, 0));
    }

    @Override
    public BlockState m_7722_(LevelAccessor p_53949_) {
        return (BlockState)this.m_49966_().m_61124_(f_53924_, p_53949_.m_213780_().m_188503_(25));
    }

    @Override
    public boolean m_6724_(BlockState p_53961_) {
        return p_53961_.m_61143_(f_53924_) < 25;
    }

    @Override
    public void m_213898_(BlockState p_221350_, ServerLevel p_221351_, BlockPos p_221352_, RandomSource p_221353_) {
        BlockPos $$4;
        if (p_221350_.m_61143_(f_53924_) < 25 && p_221353_.m_188500_() < this.f_53925_ && this.m_5971_(p_221351_.m_8055_($$4 = p_221352_.m_121945_(this.f_53859_)))) {
            p_221351_.m_46597_($$4, this.m_214070_(p_221350_, p_221351_.f_46441_));
        }
    }

    protected BlockState m_214070_(BlockState p_221347_, RandomSource p_221348_) {
        return (BlockState)p_221347_.m_61122_(f_53924_);
    }

    public BlockState m_187438_(BlockState p_187439_) {
        return (BlockState)p_187439_.m_61124_(f_53924_, 25);
    }

    public boolean m_187440_(BlockState p_187441_) {
        return p_187441_.m_61143_(f_53924_) == 25;
    }

    protected BlockState m_142643_(BlockState p_153329_, BlockState p_153330_) {
        return p_153330_;
    }

    @Override
    public BlockState m_7417_(BlockState p_53951_, Direction p_53952_, BlockState p_53953_, LevelAccessor p_53954_, BlockPos p_53955_, BlockPos p_53956_) {
        if (p_53952_ == this.f_53859_.m_122424_() && !p_53951_.m_60710_(p_53954_, p_53955_)) {
            p_53954_.m_186460_(p_53955_, this, 1);
        }
        if (p_53952_ == this.f_53859_ && (p_53953_.m_60713_(this) || p_53953_.m_60713_(this.m_7777_()))) {
            return this.m_142643_(p_53951_, this.m_7777_().m_49966_());
        }
        if (this.f_53860_) {
            p_53954_.m_186469_(p_53955_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53954_));
        }
        return super.m_7417_(p_53951_, p_53952_, p_53953_, p_53954_, p_53955_, p_53956_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53958_) {
        p_53958_.m_61104_(f_53924_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_53939_, BlockPos p_53940_, BlockState p_53941_, boolean p_53942_) {
        return this.m_5971_(p_53939_.m_8055_(p_53940_.m_121945_(this.f_53859_)));
    }

    @Override
    public boolean m_214167_(Level p_221343_, RandomSource p_221344_, BlockPos p_221345_, BlockState p_221346_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221337_, RandomSource p_221338_, BlockPos p_221339_, BlockState p_221340_) {
        BlockPos $$4 = p_221339_.m_121945_(this.f_53859_);
        int $$5 = Math.min(p_221340_.m_61143_(f_53924_) + 1, 25);
        int $$6 = this.m_213627_(p_221338_);
        for (int $$7 = 0; $$7 < $$6 && this.m_5971_(p_221337_.m_8055_($$4)); ++$$7) {
            p_221337_.m_46597_($$4, (BlockState)p_221340_.m_61124_(f_53924_, $$5));
            $$4 = $$4.m_121945_(this.f_53859_);
            $$5 = Math.min($$5 + 1, 25);
        }
    }

    protected abstract int m_213627_(RandomSource var1);

    protected abstract boolean m_5971_(BlockState var1);

    @Override
    protected GrowingPlantHeadBlock m_7272_() {
        return this;
    }
}

