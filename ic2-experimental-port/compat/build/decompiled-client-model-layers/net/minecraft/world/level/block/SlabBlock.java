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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlabBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final EnumProperty<SlabType> f_56353_ = BlockStateProperties.f_61397_;
    public static final BooleanProperty f_56354_ = BlockStateProperties.f_61362_;
    protected static final VoxelShape f_56355_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape f_56356_ = Block.m_49796_(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

    public SlabBlock(BlockBehaviour.Properties p_56359_) {
        super(p_56359_);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(f_56353_, SlabType.BOTTOM)).m_61124_(f_56354_, false));
    }

    @Override
    public boolean m_7923_(BlockState p_56395_) {
        return p_56395_.m_61143_(f_56353_) != SlabType.DOUBLE;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56388_) {
        p_56388_.m_61104_(f_56353_, f_56354_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56390_, BlockGetter p_56391_, BlockPos p_56392_, CollisionContext p_56393_) {
        SlabType $$4 = p_56390_.m_61143_(f_56353_);
        switch ($$4) {
            case DOUBLE: {
                return Shapes.m_83144_();
            }
            case TOP: {
                return f_56356_;
            }
        }
        return f_56355_;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_56361_) {
        BlockPos $$1 = p_56361_.m_8083_();
        BlockState $$2 = p_56361_.m_43725_().m_8055_($$1);
        if ($$2.m_60713_(this)) {
            return (BlockState)((BlockState)$$2.m_61124_(f_56353_, SlabType.DOUBLE)).m_61124_(f_56354_, false);
        }
        FluidState $$3 = p_56361_.m_43725_().m_6425_($$1);
        BlockState $$4 = (BlockState)((BlockState)this.m_49966_().m_61124_(f_56353_, SlabType.BOTTOM)).m_61124_(f_56354_, $$3.m_76152_() == Fluids.f_76193_);
        Direction $$5 = p_56361_.m_43719_();
        if ($$5 == Direction.DOWN || $$5 != Direction.UP && p_56361_.m_43720_().f_82480_ - (double)$$1.m_123342_() > 0.5) {
            return (BlockState)$$4.m_61124_(f_56353_, SlabType.TOP);
        }
        return $$4;
    }

    @Override
    public boolean m_6864_(BlockState p_56373_, BlockPlaceContext p_56374_) {
        ItemStack $$2 = p_56374_.m_43722_();
        SlabType $$3 = p_56373_.m_61143_(f_56353_);
        if ($$3 == SlabType.DOUBLE || !$$2.m_150930_(this.m_5456_())) {
            return false;
        }
        if (p_56374_.m_7058_()) {
            boolean $$4 = p_56374_.m_43720_().f_82480_ - (double)p_56374_.m_8083_().m_123342_() > 0.5;
            Direction $$5 = p_56374_.m_43719_();
            if ($$3 == SlabType.BOTTOM) {
                return $$5 == Direction.UP || $$4 && $$5.m_122434_().m_122479_();
            }
            return $$5 == Direction.DOWN || !$$4 && $$5.m_122434_().m_122479_();
        }
        return true;
    }

    @Override
    public FluidState m_5888_(BlockState p_56397_) {
        if (p_56397_.m_61143_(f_56354_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_56397_);
    }

    @Override
    public boolean m_7361_(LevelAccessor p_56368_, BlockPos p_56369_, BlockState p_56370_, FluidState p_56371_) {
        if (p_56370_.m_61143_(f_56353_) != SlabType.DOUBLE) {
            return SimpleWaterloggedBlock.super.m_7361_(p_56368_, p_56369_, p_56370_, p_56371_);
        }
        return false;
    }

    @Override
    public boolean m_6044_(BlockGetter p_56363_, BlockPos p_56364_, BlockState p_56365_, Fluid p_56366_) {
        if (p_56365_.m_61143_(f_56353_) != SlabType.DOUBLE) {
            return SimpleWaterloggedBlock.super.m_6044_(p_56363_, p_56364_, p_56365_, p_56366_);
        }
        return false;
    }

    @Override
    public BlockState m_7417_(BlockState p_56381_, Direction p_56382_, BlockState p_56383_, LevelAccessor p_56384_, BlockPos p_56385_, BlockPos p_56386_) {
        if (p_56381_.m_61143_(f_56354_).booleanValue()) {
            p_56384_.m_186469_(p_56385_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56384_));
        }
        return super.m_7417_(p_56381_, p_56382_, p_56383_, p_56384_, p_56385_, p_56386_);
    }

    @Override
    public boolean m_7357_(BlockState p_56376_, BlockGetter p_56377_, BlockPos p_56378_, PathComputationType p_56379_) {
        switch (p_56379_) {
            case LAND: {
                return false;
            }
            case WATER: {
                return p_56377_.m_6425_(p_56378_).m_205070_(FluidTags.f_13131_);
            }
            case AIR: {
                return false;
            }
        }
        return false;
    }
}

