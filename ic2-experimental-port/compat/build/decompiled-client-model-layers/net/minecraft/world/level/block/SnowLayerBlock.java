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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SnowLayerBlock
extends Block {
    public static final int f_154646_ = 8;
    public static final IntegerProperty f_56581_ = BlockStateProperties.f_61417_;
    protected static final VoxelShape[] f_56582_ = new VoxelShape[]{Shapes.m_83040_(), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};
    public static final int f_154647_ = 5;

    protected SnowLayerBlock(BlockBehaviour.Properties p_56585_) {
        super(p_56585_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_56581_, 1));
    }

    @Override
    public boolean m_7357_(BlockState p_56592_, BlockGetter p_56593_, BlockPos p_56594_, PathComputationType p_56595_) {
        switch (p_56595_) {
            case LAND: {
                return p_56592_.m_61143_(f_56581_) < 5;
            }
            case WATER: {
                return false;
            }
            case AIR: {
                return false;
            }
        }
        return false;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_, CollisionContext p_56623_) {
        return f_56582_[p_56620_.m_61143_(f_56581_)];
    }

    @Override
    public VoxelShape m_5939_(BlockState p_56625_, BlockGetter p_56626_, BlockPos p_56627_, CollisionContext p_56628_) {
        return f_56582_[p_56625_.m_61143_(f_56581_) - 1];
    }

    @Override
    public VoxelShape m_7947_(BlockState p_56632_, BlockGetter p_56633_, BlockPos p_56634_) {
        return f_56582_[p_56632_.m_61143_(f_56581_)];
    }

    @Override
    public VoxelShape m_5909_(BlockState p_56597_, BlockGetter p_56598_, BlockPos p_56599_, CollisionContext p_56600_) {
        return f_56582_[p_56597_.m_61143_(f_56581_)];
    }

    @Override
    public boolean m_7923_(BlockState p_56630_) {
        return true;
    }

    @Override
    public float m_7749_(BlockState p_222453_, BlockGetter p_222454_, BlockPos p_222455_) {
        return p_222453_.m_61143_(f_56581_) == 8 ? 0.2f : 1.0f;
    }

    @Override
    public boolean m_7898_(BlockState p_56602_, LevelReader p_56603_, BlockPos p_56604_) {
        BlockState $$3 = p_56603_.m_8055_(p_56604_.m_7495_());
        if ($$3.m_204336_(BlockTags.f_215833_)) {
            return false;
        }
        if ($$3.m_204336_(BlockTags.f_215834_)) {
            return true;
        }
        return Block.m_49918_($$3.m_60812_(p_56603_, p_56604_.m_7495_()), Direction.UP) || $$3.m_60713_(this) && $$3.m_61143_(f_56581_) == 8;
    }

    @Override
    public BlockState m_7417_(BlockState p_56606_, Direction p_56607_, BlockState p_56608_, LevelAccessor p_56609_, BlockPos p_56610_, BlockPos p_56611_) {
        if (!p_56606_.m_60710_(p_56609_, p_56610_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_56606_, p_56607_, p_56608_, p_56609_, p_56610_, p_56611_);
    }

    @Override
    public void m_213898_(BlockState p_222448_, ServerLevel p_222449_, BlockPos p_222450_, RandomSource p_222451_) {
        if (p_222449_.m_45517_(LightLayer.BLOCK, p_222450_) > 11) {
            SnowLayerBlock.m_49950_(p_222448_, p_222449_, p_222450_);
            p_222449_.m_7471_(p_222450_, false);
        }
    }

    @Override
    public boolean m_6864_(BlockState p_56589_, BlockPlaceContext p_56590_) {
        int $$2 = p_56589_.m_61143_(f_56581_);
        if (p_56590_.m_43722_().m_150930_(this.m_5456_()) && $$2 < 8) {
            if (p_56590_.m_7058_()) {
                return p_56590_.m_43719_() == Direction.UP;
            }
            return true;
        }
        return $$2 == 1;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_56587_) {
        BlockState $$1 = p_56587_.m_43725_().m_8055_(p_56587_.m_8083_());
        if ($$1.m_60713_(this)) {
            int $$2 = $$1.m_61143_(f_56581_);
            return (BlockState)$$1.m_61124_(f_56581_, Math.min(8, $$2 + 1));
        }
        return super.m_5573_(p_56587_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56613_) {
        p_56613_.m_61104_(f_56581_);
    }
}

