/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CactusBlock
extends Block {
    public static final IntegerProperty f_51131_ = BlockStateProperties.f_61410_;
    public static final int f_152740_ = 15;
    protected static final int f_152741_ = 1;
    protected static final VoxelShape f_51132_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
    protected static final VoxelShape f_51133_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    protected CactusBlock(BlockBehaviour.Properties p_51136_) {
        super(p_51136_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51131_, 0));
    }

    @Override
    public void m_213897_(BlockState p_220908_, ServerLevel p_220909_, BlockPos p_220910_, RandomSource p_220911_) {
        if (!p_220908_.m_60710_(p_220909_, p_220910_)) {
            p_220909_.m_46961_(p_220910_, true);
        }
    }

    @Override
    public void m_213898_(BlockState p_220913_, ServerLevel p_220914_, BlockPos p_220915_, RandomSource p_220916_) {
        BlockPos $$4 = p_220915_.m_7494_();
        if (!p_220914_.m_46859_($$4)) {
            return;
        }
        int $$5 = 1;
        while (p_220914_.m_8055_(p_220915_.m_6625_($$5)).m_60713_(this)) {
            ++$$5;
        }
        if ($$5 >= 3) {
            return;
        }
        int $$6 = p_220913_.m_61143_(f_51131_);
        if ($$6 == 15) {
            p_220914_.m_46597_($$4, this.m_49966_());
            BlockState $$7 = (BlockState)p_220913_.m_61124_(f_51131_, 0);
            p_220914_.m_7731_(p_220915_, $$7, 4);
            p_220914_.m_213960_($$7, $$4, this, p_220915_, false);
        } else {
            p_220914_.m_7731_(p_220915_, (BlockState)p_220913_.m_61124_(f_51131_, $$6 + 1), 4);
        }
    }

    @Override
    public VoxelShape m_5939_(BlockState p_51176_, BlockGetter p_51177_, BlockPos p_51178_, CollisionContext p_51179_) {
        return f_51132_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51171_, BlockGetter p_51172_, BlockPos p_51173_, CollisionContext p_51174_) {
        return f_51133_;
    }

    @Override
    public BlockState m_7417_(BlockState p_51157_, Direction p_51158_, BlockState p_51159_, LevelAccessor p_51160_, BlockPos p_51161_, BlockPos p_51162_) {
        if (!p_51157_.m_60710_(p_51160_, p_51161_)) {
            p_51160_.m_186460_(p_51161_, this, 1);
        }
        return super.m_7417_(p_51157_, p_51158_, p_51159_, p_51160_, p_51161_, p_51162_);
    }

    @Override
    public boolean m_7898_(BlockState p_51153_, LevelReader p_51154_, BlockPos p_51155_) {
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            BlockState $$4 = p_51154_.m_8055_(p_51155_.m_121945_($$3));
            Material $$5 = $$4.m_60767_();
            if (!$$5.m_76333_() && !p_51154_.m_6425_(p_51155_.m_121945_($$3)).m_205070_(FluidTags.f_13132_)) continue;
            return false;
        }
        BlockState $$6 = p_51154_.m_8055_(p_51155_.m_7495_());
        return ($$6.m_60713_(Blocks.f_50128_) || $$6.m_60713_(Blocks.f_49992_) || $$6.m_60713_(Blocks.f_49993_)) && !p_51154_.m_8055_(p_51155_.m_7494_()).m_60767_().m_76332_();
    }

    @Override
    public void m_7892_(BlockState p_51148_, Level p_51149_, BlockPos p_51150_, Entity p_51151_) {
        p_51151_.m_6469_(DamageSource.f_19314_, 1.0f);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51164_) {
        p_51164_.m_61104_(f_51131_);
    }

    @Override
    public boolean m_7357_(BlockState p_51143_, BlockGetter p_51144_, BlockPos p_51145_, PathComputationType p_51146_) {
        return false;
    }
}

