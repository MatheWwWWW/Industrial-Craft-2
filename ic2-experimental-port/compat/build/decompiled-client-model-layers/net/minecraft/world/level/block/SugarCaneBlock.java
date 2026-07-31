/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SugarCaneBlock
extends Block {
    public static final IntegerProperty f_57164_ = BlockStateProperties.f_61410_;
    protected static final float f_154735_ = 6.0f;
    protected static final VoxelShape f_57165_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    protected SugarCaneBlock(BlockBehaviour.Properties p_57168_) {
        super(p_57168_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57164_, 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57193_, BlockGetter p_57194_, BlockPos p_57195_, CollisionContext p_57196_) {
        return f_57165_;
    }

    @Override
    public void m_213897_(BlockState p_222543_, ServerLevel p_222544_, BlockPos p_222545_, RandomSource p_222546_) {
        if (!p_222543_.m_60710_(p_222544_, p_222545_)) {
            p_222544_.m_46961_(p_222545_, true);
        }
    }

    @Override
    public void m_213898_(BlockState p_222548_, ServerLevel p_222549_, BlockPos p_222550_, RandomSource p_222551_) {
        if (p_222549_.m_46859_(p_222550_.m_7494_())) {
            int $$4 = 1;
            while (p_222549_.m_8055_(p_222550_.m_6625_($$4)).m_60713_(this)) {
                ++$$4;
            }
            if ($$4 < 3) {
                int $$5 = p_222548_.m_61143_(f_57164_);
                if ($$5 == 15) {
                    p_222549_.m_46597_(p_222550_.m_7494_(), this.m_49966_());
                    p_222549_.m_7731_(p_222550_, (BlockState)p_222548_.m_61124_(f_57164_, 0), 4);
                } else {
                    p_222549_.m_7731_(p_222550_, (BlockState)p_222548_.m_61124_(f_57164_, $$5 + 1), 4);
                }
            }
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_57179_, Direction p_57180_, BlockState p_57181_, LevelAccessor p_57182_, BlockPos p_57183_, BlockPos p_57184_) {
        if (!p_57179_.m_60710_(p_57182_, p_57183_)) {
            p_57182_.m_186460_(p_57183_, this, 1);
        }
        return super.m_7417_(p_57179_, p_57180_, p_57181_, p_57182_, p_57183_, p_57184_);
    }

    @Override
    public boolean m_7898_(BlockState p_57175_, LevelReader p_57176_, BlockPos p_57177_) {
        BlockState $$3 = p_57176_.m_8055_(p_57177_.m_7495_());
        if ($$3.m_60713_(this)) {
            return true;
        }
        if ($$3.m_204336_(BlockTags.f_144274_) || $$3.m_60713_(Blocks.f_49992_) || $$3.m_60713_(Blocks.f_49993_)) {
            BlockPos $$4 = p_57177_.m_7495_();
            for (Direction $$5 : Direction.Plane.HORIZONTAL) {
                BlockState $$6 = p_57176_.m_8055_($$4.m_121945_($$5));
                FluidState $$7 = p_57176_.m_6425_($$4.m_121945_($$5));
                if (!$$7.m_205070_(FluidTags.f_13131_) && !$$6.m_60713_(Blocks.f_50449_)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57186_) {
        p_57186_.m_61104_(f_57164_);
    }
}

