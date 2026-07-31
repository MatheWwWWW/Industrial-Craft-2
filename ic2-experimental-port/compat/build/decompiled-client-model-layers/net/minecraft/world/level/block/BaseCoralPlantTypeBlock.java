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
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCoralPlantTypeBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_49158_ = BlockStateProperties.f_61362_;
    private static final VoxelShape f_49157_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

    protected BaseCoralPlantTypeBlock(BlockBehaviour.Properties p_49161_) {
        super(p_49161_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_49158_, true));
    }

    protected void m_49164_(BlockState p_49165_, LevelAccessor p_49166_, BlockPos p_49167_) {
        if (!BaseCoralPlantTypeBlock.m_49186_(p_49165_, p_49166_, p_49167_)) {
            p_49166_.m_186460_(p_49167_, this, 60 + p_49166_.m_213780_().m_188503_(40));
        }
    }

    protected static boolean m_49186_(BlockState p_49187_, BlockGetter p_49188_, BlockPos p_49189_) {
        if (p_49187_.m_61143_(f_49158_).booleanValue()) {
            return true;
        }
        for (Direction $$3 : Direction.values()) {
            if (!p_49188_.m_6425_(p_49189_.m_121945_($$3)).m_205070_(FluidTags.f_13131_)) continue;
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_49163_) {
        FluidState $$1 = p_49163_.m_43725_().m_6425_(p_49163_.m_8083_());
        return (BlockState)this.m_49966_().m_61124_(f_49158_, $$1.m_205070_(FluidTags.f_13131_) && $$1.m_76186_() == 8);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49182_, BlockGetter p_49183_, BlockPos p_49184_, CollisionContext p_49185_) {
        return f_49157_;
    }

    @Override
    public BlockState m_7417_(BlockState p_49173_, Direction p_49174_, BlockState p_49175_, LevelAccessor p_49176_, BlockPos p_49177_, BlockPos p_49178_) {
        if (p_49173_.m_61143_(f_49158_).booleanValue()) {
            p_49176_.m_186469_(p_49177_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_49176_));
        }
        if (p_49174_ == Direction.DOWN && !this.m_7898_(p_49173_, p_49176_, p_49177_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_49173_, p_49174_, p_49175_, p_49176_, p_49177_, p_49178_);
    }

    @Override
    public boolean m_7898_(BlockState p_49169_, LevelReader p_49170_, BlockPos p_49171_) {
        BlockPos $$3 = p_49171_.m_7495_();
        return p_49170_.m_8055_($$3).m_60783_(p_49170_, $$3, Direction.UP);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49180_) {
        p_49180_.m_61104_(f_49158_);
    }

    @Override
    public FluidState m_5888_(BlockState p_49191_) {
        if (p_49191_.m_61143_(f_49158_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_49191_);
    }
}

