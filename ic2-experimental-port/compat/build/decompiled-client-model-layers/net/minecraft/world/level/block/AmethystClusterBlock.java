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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AmethystClusterBlock
extends AmethystBlock
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_152005_ = BlockStateProperties.f_61362_;
    public static final DirectionProperty f_152006_ = BlockStateProperties.f_61372_;
    protected final VoxelShape f_152007_;
    protected final VoxelShape f_152008_;
    protected final VoxelShape f_152009_;
    protected final VoxelShape f_152010_;
    protected final VoxelShape f_152011_;
    protected final VoxelShape f_152012_;

    public AmethystClusterBlock(int p_152015_, int p_152016_, BlockBehaviour.Properties p_152017_) {
        super(p_152017_);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(f_152005_, false)).m_61124_(f_152006_, Direction.UP));
        this.f_152011_ = Block.m_49796_(p_152016_, 0.0, p_152016_, 16 - p_152016_, p_152015_, 16 - p_152016_);
        this.f_152012_ = Block.m_49796_(p_152016_, 16 - p_152015_, p_152016_, 16 - p_152016_, 16.0, 16 - p_152016_);
        this.f_152007_ = Block.m_49796_(p_152016_, p_152016_, 16 - p_152015_, 16 - p_152016_, 16 - p_152016_, 16.0);
        this.f_152008_ = Block.m_49796_(p_152016_, p_152016_, 0.0, 16 - p_152016_, 16 - p_152016_, p_152015_);
        this.f_152009_ = Block.m_49796_(0.0, p_152016_, p_152016_, p_152015_, 16 - p_152016_, 16 - p_152016_);
        this.f_152010_ = Block.m_49796_(16 - p_152015_, p_152016_, p_152016_, 16.0, 16 - p_152016_, 16 - p_152016_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_152021_, BlockGetter p_152022_, BlockPos p_152023_, CollisionContext p_152024_) {
        Direction $$4 = p_152021_.m_61143_(f_152006_);
        switch ($$4) {
            case NORTH: {
                return this.f_152007_;
            }
            case SOUTH: {
                return this.f_152008_;
            }
            case EAST: {
                return this.f_152009_;
            }
            case WEST: {
                return this.f_152010_;
            }
            case DOWN: {
                return this.f_152012_;
            }
        }
        return this.f_152011_;
    }

    @Override
    public boolean m_7898_(BlockState p_152026_, LevelReader p_152027_, BlockPos p_152028_) {
        Direction $$3 = p_152026_.m_61143_(f_152006_);
        BlockPos $$4 = p_152028_.m_121945_($$3.m_122424_());
        return p_152027_.m_8055_($$4).m_60783_(p_152027_, $$4, $$3);
    }

    @Override
    public BlockState m_7417_(BlockState p_152036_, Direction p_152037_, BlockState p_152038_, LevelAccessor p_152039_, BlockPos p_152040_, BlockPos p_152041_) {
        if (p_152036_.m_61143_(f_152005_).booleanValue()) {
            p_152039_.m_186469_(p_152040_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152039_));
        }
        if (p_152037_ == p_152036_.m_61143_(f_152006_).m_122424_() && !p_152036_.m_60710_(p_152039_, p_152040_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_152036_, p_152037_, p_152038_, p_152039_, p_152040_, p_152041_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_152019_) {
        Level $$1 = p_152019_.m_43725_();
        BlockPos $$2 = p_152019_.m_8083_();
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_152005_, $$1.m_6425_($$2).m_76152_() == Fluids.f_76193_)).m_61124_(f_152006_, p_152019_.m_43719_());
    }

    @Override
    public BlockState m_6843_(BlockState p_152033_, Rotation p_152034_) {
        return (BlockState)p_152033_.m_61124_(f_152006_, p_152034_.m_55954_(p_152033_.m_61143_(f_152006_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.m_60717_(p_152031_.m_54846_(p_152030_.m_61143_(f_152006_)));
    }

    @Override
    public FluidState m_5888_(BlockState p_152045_) {
        if (p_152045_.m_61143_(f_152005_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_152045_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.m_61104_(f_152005_, f_152006_);
    }

    @Override
    public PushReaction m_5537_(BlockState p_152047_) {
        return PushReaction.DESTROY;
    }
}

